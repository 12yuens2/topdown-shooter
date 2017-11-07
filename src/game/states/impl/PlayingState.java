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
	
	public EnemySpawnFactory enemySpawnFactory;
	public PickupSpawnFactory pickupSpawnFactory;
	
	public PlayingState(GameContext context, DrawEngine drawEngine) {
		super(context, drawEngine);
		
		this.difficulty = 20;
		this.enemySpawnFactory = new EnemySpawnFactory(difficulty, context);
		this.pickupSpawnFactory = new PickupSpawnFactory(difficulty, context, enemySpawnFactory);
	}

	@Override
	public void display(PlayerCharacter player) {
		displayGame();
		ui.display(player);
	}

	@Override
	public GameState update(int mouseX, int mouseY, PlayerCharacter player) {
		updateStep(mouseX, mouseY, player);
		
		enemySpawnFactory.spawnEntities();
		pickupSpawnFactory.spawnEntities();
		
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
			player.attacking = input.mouseDown;
			if (player.attacking) {
				Particle bullet = player.attack(input.mouseX, input.mouseY);
				if (bullet != null) {
					context.particles.add(bullet);
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
