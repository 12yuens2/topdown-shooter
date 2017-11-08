package game.states.impl;


import com.sun.glass.events.KeyEvent;

import game.DrawEngine;
import game.GameContext;
import game.GameInput;
import game.ShooterServer;
import game.factories.EnemySpawnFactory;
import game.factories.PickupSpawnFactory;
import game.states.GameState;
import javafx.util.converter.PercentageStringConverter;
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
import objs.pickups.Pickup;
import objs.pickups.effects.impl.PermanentDamageEffect;
import objs.pickups.impl.AmmoPickup;
import objs.pickups.impl.BombPickup;
import objs.pickups.impl.HealthPickup;
import objs.pickups.impl.PermanentDamagePickup;
import objs.pickups.impl.SpeedPickup;
import processing.core.PConstants;
import processing.core.PVector;

public class PlayingState extends GameState {
	
	public int difficulty;
	public int timer;
	
	public transient EnemySpawnFactory enemySpawnFactory;
	public transient PickupSpawnFactory pickupSpawnFactory;
	
	public PlayingState(GameContext context) {
		super(context);
		
		this.difficulty = 0;
		this.timer = 0;
		this.enemySpawnFactory = new EnemySpawnFactory(difficulty, context);
		this.pickupSpawnFactory = new PickupSpawnFactory(difficulty, context, enemySpawnFactory);
	}

	@Override
	public void display(DrawEngine drawEngine, PlayerCharacter player) {
		displayGame(drawEngine);
		ui.display(drawEngine, player);
	}

	@Override
	public GameState update(int mouseX, int mouseY, PlayerCharacter player) {
		updateStep(mouseX, mouseY, player);
		
		enemySpawnFactory.spawnEntities();
		pickupSpawnFactory.spawnEntities();
		
		if (++timer > 1000) {
			difficulty++;
			System.out.println(difficulty);
			timer = 0;
		}
		
		if (timer == 500) {
			int entities = context.enemies.size();
			if (entities < 10) difficulty += 3;
			if (entities >= 10 && entities < 30) difficulty += 1;
			if (entities > 50) difficulty -= 1;
		}
		
		
		return context.players.size() > 0 ? this : new GameOverState(context);
	}

	@Override
	public GameState handleInput(GameInput input, PlayerCharacter player) {
		if (player != null) {
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
				player.attacking = input.mouseDown;
				if (player.attacking) {
					Particle bullet = player.attack(input.mouseX, input.mouseY);
					if (bullet != null) {
						context.particles.add(bullet);
					}
				}
				
			}
		}
		
		
		return this;
	}
	
//	private void spawnPatrolEnemy(PVector position) {
//		context.enemies.add(new PatrolEnemy(position.x, position.y,
//											15, Enemy.BASE_HP, Enemy.BASE_DMG, Enemy.BASE_SCORE,
//											context.players));
//		}
}
