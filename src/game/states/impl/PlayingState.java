package game.states.impl;


import com.sun.glass.events.KeyEvent;

import game.DrawEngine;
import game.GameContext;
import game.GameInput;
import game.ShooterServer;
import game.director.Director;
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
	
	public PlayingState(GameContext context) {
		super(context);
	}

	@Override
	public void display(DrawEngine drawEngine, PlayerCharacter player) {
		displayGame(drawEngine);
		ui.display(drawEngine, player);
	}

	@Override
	public GameState update(int mouseX, int mouseY, PlayerCharacter player) {
		updateStep(mouseX, mouseY, player);
		aiDirector.step();
		
		/* Game over if no players alive */
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
				
				/* Continue attacking if the player is holding down left click */
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
}
