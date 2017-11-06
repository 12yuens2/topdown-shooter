package game.states.impl;


import com.sun.glass.events.KeyEvent;

import game.DrawEngine;
import game.GameContext;
import game.GameInput;
import game.ShooterServer;
import game.states.GameState;
import objs.characters.Character;
import objs.characters.PlayerCharacter;
import objs.characters.enemies.Enemy;
import objs.characters.enemies.impl.AmbushEnemy;
import objs.characters.enemies.impl.BasicChaseEnemy;
import objs.characters.enemies.impl.CircleEnemy;
import objs.characters.enemies.impl.FlockEnemy;
import objs.characters.enemies.impl.PatrolEnemy;
import objs.characters.enemies.impl.ShootEnemy;
import objs.particles.Particle;
import objs.pickups.impl.AmmoPickup;
import objs.pickups.impl.BombPickup;
import objs.pickups.impl.HealthPickup;
import objs.pickups.impl.SpeedPickup;
import processing.core.PConstants;
import processing.core.PVector;

public class PlayingState extends GameState {
	
	public PlayingState(GameContext context, DrawEngine drawEngine) {
		super(context, drawEngine);
	}

	@Override
	public void display(PlayerCharacter player) {
		displayGame();
		ui.display(player);
	}

	@Override
	public GameState update(int mouseX, int mouseY, PlayerCharacter player) {
		updateStep(mouseX, mouseY, player);

		int enemyHealth = Enemy.BASE_HP;
		int enemyDamage = Enemy.BASE_DMG;
		int enemyScore = Enemy.BASE_SCORE;
		int size = 15;
		
		if (random.nextInt(15) == 0) {
			FlockEnemy boid = new FlockEnemy(
					parent.random(ShooterServer.SCREEN_X),
					parent.random(ShooterServer.SCREEN_Y),
					5, enemyHealth, enemyDamage, enemyScore, context.players);
			
			context.enemies.add(boid);
			context.flockEnemies.add(boid);
		}
		
		if (random.nextInt(200) == 0) context.enemies.add(
				new CircleEnemy(parent.random(ShooterServer.SCREEN_X), 
										parent.random(ShooterServer.SCREEN_Y), 
										size, enemyHealth, enemyDamage, enemyScore, 
										context.players));
		
		if (random.nextInt(200) == 0) context.enemies.add(
				new AmbushEnemy(parent.random(ShooterServer.SCREEN_X), 
										parent.random(ShooterServer.SCREEN_Y), 
										size, enemyHealth, enemyDamage, enemyScore,
										context.players));
		
		if (random.nextInt(200) == 0) context.enemies.add(
				new BasicChaseEnemy(parent.random(ShooterServer.SCREEN_X), 
										parent.random(ShooterServer.SCREEN_Y), 
										size, enemyHealth, enemyDamage, enemyScore,
										context.players));
		
		if (random.nextInt(200) == 0) context.enemies.add(
				new ShootEnemy(parent.random(ShooterServer.SCREEN_X), 
								parent.random(ShooterServer.SCREEN_Y), 
								size, enemyHealth, enemyDamage, enemyScore,
								context));
		
		if (random.nextInt(250) == 0) {
			SpeedPickup pickup = new SpeedPickup(parent.random(ShooterServer.SCREEN_X), parent.random(ShooterServer.SCREEN_Y), 5, 300);
			if (random.nextInt(10) == 0) {
				spawnPatrolEnemy(pickup.position);
			}
			context.pickups.add(pickup);
		}
		
		if (random.nextInt(250) == 0) {
			AmmoPickup pickup = new AmmoPickup(parent.random(ShooterServer.SCREEN_X), parent.random(ShooterServer.SCREEN_Y), 10, 300);
			if (random.nextInt(10) == 0) {
				spawnPatrolEnemy(pickup.position);
			}
			context.pickups.add(pickup);
		}
			
		if (random.nextInt(250) == 0) {
			BombPickup pickup = new BombPickup(parent.random(ShooterServer.SCREEN_X), parent.random(ShooterServer.SCREEN_Y), 10, 300, context);
			if (random.nextInt(10) == 0) {
				spawnPatrolEnemy(pickup.position);
			}
			context.pickups.add(pickup);
		}
		if (random.nextInt(250) == 0) {
			HealthPickup pickup = new HealthPickup(parent.random(ShooterServer.SCREEN_X), parent.random(ShooterServer.SCREEN_Y), 10, 300);
			if (random.nextInt(10) == 0) {
				spawnPatrolEnemy(pickup.position);
			}
			context.pickups.add(pickup);
		}
		
		return this;
	}

	@Override
	public GameState handleInput(GameInput input, PlayerCharacter player) {
		player.facingDirection((int)input.mouseX, (int)input.mouseY);
		
		if (input.keyDown) {
			player.directionPress(input.keyCode);
			
			if (input.keyCode == KeyEvent.VK_R) {
				player.currentWeapon.reload();
			}
			
			if (input.keyCode == KeyEvent.VK_1) {
				player.currentWeapon = player.weapons.get(0);
			}
			if (input.keyCode == KeyEvent.VK_2) {
				player.currentWeapon = player.weapons.get(1);
			}
			if (input.keyCode == KeyEvent.VK_3) {
				player.currentWeapon = player.weapons.get(2);
			}
		}
		else {
			player.directionRelease(input.keyCode);
		}
		
		if (input.mouseButton == PConstants.LEFT) {
			player.currentWeapon.firing = 0;
			player.attacking = input.mouseDown;
		}
		
		return this;
	}
	
	private void spawnPatrolEnemy(PVector position) {
		context.enemies.add(new PatrolEnemy(position.x, position.y,
											15, Enemy.BASE_HP, Enemy.BASE_DMG, Enemy.BASE_SCORE,
											context.players));
		}
}
