package game.states;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.function.Function;

import game.DrawEngine;
import game.GameContext;
import game.GameInput;
import game.GameObject;
import game.GameUI;
import game.ShooterGame;
import objs.characters.Character;
import objs.characters.PlayerCharacter;
import objs.characters.enemies.Enemy;
import objs.characters.enemies.impl.FlockEnemy;
import objs.particles.Explosion;
import objs.particles.Missile;
import objs.particles.Particle;
import objs.pickups.Pickup;
import objs.pickups.effects.Effect;
import objs.weapons.Weapon;
import processing.core.PApplet;
import processing.core.PVector;

public abstract class GameState {

	public Random random;
	public PApplet parent;
	
	public GameContext context;
	public GameUI ui;
	public DrawEngine drawEngine;
	
	public GameState(GameContext context, DrawEngine drawEngine) {
		this.random = new Random();
		this.parent = drawEngine.parent;
		
		this.context = context;
		this.drawEngine = drawEngine;

		this.ui = new GameUI(context, drawEngine);
	}
	
	/**
	 * Display this GameState to the screen.
	 * @param player - The character that this client controls.
	 */
	public abstract void display(PlayerCharacter player);
	
	
	/**
	 * Update the game state for each frame of the game.
	 * @param mouseX - x position of the mouse.
	 * @param mouseY - y position of the mouse.
	 * @param player - The character that this client controls.
	 * @return the next state of the game.
	 */
	public abstract GameState update(int mouseX, int mouseY, PlayerCharacter player);

	
	/**
	 * Handle player input.
	 * @param input - The player input which involves mouse position, mousebutton and keybutton pressed.
	 * @param player - The player character which this input affects.
	 * @return the next state of the game.
	 */
	public abstract GameState handleInput(GameInput input, PlayerCharacter player);

	
	/**
	 * Display all drawable game objects in the game.
	 */
	public void displayGame() {
		parent.background(0);
		
		drawEngine.displayDrawables(context.players, context.enemies, context.particles, context.pickups, context.explosions);
	}
	
	/**
	 * Main update step of the game loop. Goes through all GameObjects and updates their properties such as position and health.
	 * The position of the mouse is used to draw the direction the player is facing.
	 * @param mouseX - x position of the mouse.
	 * @param mouseY - y position of the mouse.
	 * @param player - The character that this client controls.
	 */
	public void updateStep(int mouseX, int mouseY, PlayerCharacter player) {
		updatePlayerStep(mouseX, mouseY, player);
		updateEnemyStep();
		updatePickups();
		
		/* Integrate step for all other game objects */
		updateGameObjects(context.particles, context.explosions);
	}
	
	/**
	 * Update step for the player. Updates the player's position, direction and checks for pickup collisions.
	 * @param mouseX - x position of the mouse
	 * @param mouseY - y position of the mouse
	 * @param playerChar - The character that this client controls.
	 */
	private void updatePlayerStep(int mouseX, int mouseY, PlayerCharacter playerChar) {
		Iterator<PlayerCharacter> playerIt = context.players.iterator();
		while (playerIt.hasNext()) {
			PlayerCharacter player = playerIt.next();
			
			player.integrate();
			if (player.equals(playerChar)) player.facingDirection(mouseX, mouseY);
			
			if (player.attacking) {
				Particle bullet = player.attack(mouseX, mouseY);
				if (bullet != null) {
					context.particles.add(bullet);
				}
			}
			
			/* Player pickups */
			player.collideResult(context.pickups.iterator(), new Function<Pickup, Boolean>() {

				@Override
				public Boolean apply(Pickup p) {
					p.effect.apply(player);
					player.powerups.add(p.effect);
					return true;
				}
				
			});
			
			/* Collision with enemies to take damage */
			player.collideResult(context.enemies.iterator(), new Function<Enemy, Boolean>() {

				@Override
				public Boolean apply(Enemy enemy) {
					player.health -= enemy.damage;
					if (enemy instanceof FlockEnemy) {
						context.flockEnemies.remove(enemy);
					}
					
					return enemy instanceof FlockEnemy;
				}
				
			});
			
			/* Collision with enemy particles to take damage */
			player.collideResult(context.particles.iterator(), new Function<Particle, Boolean>() {

				@Override
				public Boolean apply(Particle p) {
					if (p.friendly) {
						return false;
					}
					else {
						player.health -= p.damage;
						return true;
					}
				}
				
			});
			
			/* Count down and remove any powerup effects */
			Iterator<Effect> effectIt = player.powerups.iterator();
			while(effectIt.hasNext()) {
				Effect effect = effectIt.next();
				effect.lifespan--;
				
				if (effect.lifespan <= 0) {
					effect.cease(player);
					effectIt.remove();
				}
			}
			
			updateGameObjects(player.weapons);
		}
		
	}
	
	/**
	 * Update step for all enemies in the game. 
	 * Updates their movements and interaction with other objects in the game.
	 */
	private void updateEnemyStep() {
		Iterator<Enemy> enemyIt = context.enemies.iterator();
		
		while(enemyIt.hasNext()) {
			Enemy enemy = enemyIt.next();
			
			/* Flock if enemy implements flocking behaviour. */
			if (enemy instanceof FlockEnemy) {
				((FlockEnemy) enemy).flock(context.flockEnemies);
				
				float x = ((FlockEnemy) enemy).position.x;
				float y = ((FlockEnemy) enemy).position.y;
				
				/* Remove flocking enemies when they leave the screen */
				if (x > ShooterGame.SCREEN_X || x < 0  || y > ShooterGame.SCREEN_Y || y < 0) {
					enemyIt.remove();
					context.flockEnemies.remove(enemy);
				}
				
			}
			
			/* Individual enemy update step */
			enemy.integrate();
			
			/* Remove enemies with no health */
			if (enemy.health <= 0) {
				enemyIt.remove();
				context.flockEnemies.remove(enemy);

				context.score += enemy.score;
			}
			
			/* Collision with other enemies to resolve overlapping */
			enemy.collideResult(context.enemies.iterator(), new Function<Enemy, Boolean>() {

				@Override
				public Boolean apply(Enemy e) {
					PVector normal = PVector.sub(enemy.position, e.position).normalize();
					enemy.position.add(normal.mult(1));
					
					return false;
				}
				
			});
			
			/* Collision with particles for damage */
			enemy.collideResult(context.particles.iterator(), new Function<Particle, Boolean>() {

				@Override
				public Boolean apply(Particle p) {
					if (!p.friendly) {
						return false;
					}
					else {
						enemy.health -= p.damage;
						if (p instanceof Missile) context.explosions.add(((Missile)p).explode());
						
						return true;
					}

				}
				
			});
			
			/* Collision with explosions for damage */
			enemy.collideResult(context.explosions.iterator(), new Function<Explosion, Boolean>() {

				@Override
				public Boolean apply(Explosion e) {
					enemy.health -= e.damage;
					
					return e.lifespan <= 0;
				}
				
			});
		}
	}
	
	/**
	 * Updates all pickups to remove them after their lifespan times out.
	 */
	private void updatePickups() {
		Iterator<Pickup> pickupIt = context.pickups.iterator();
		while(pickupIt.hasNext()) {
			Pickup pickup = pickupIt.next();
			
			pickup.integrate();
			
			if (pickup.lifespan <= 0) pickupIt.remove();
		}
	}
	
	/**
	 * Updates all game objects that do not require additional interaction with other objects.
	 * @param objectLists - List of game object lists. 
	 */
	private void updateGameObjects(ArrayList<? extends GameObject>... objectLists) {
		for (ArrayList<? extends GameObject> gameObjects : objectLists) {
			for (GameObject object : gameObjects) {
				object.integrate();
			}
		}
	}
	
}
