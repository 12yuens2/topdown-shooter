package game.states.impl;


import com.sun.glass.events.KeyEvent;

import game.DrawEngine;
import game.GameContext;
import game.GameInput;
import game.ShooterGame;
import game.states.GameState;
import objs.characters.Character;
import objs.characters.FlockCharacter;
import objs.characters.PlayerCharacter;
import objs.characters.enemies.AmbushEnemy;
import objs.characters.enemies.BasicChaseEnemy;
import objs.characters.enemies.CircleEnemy;
import objs.characters.enemies.PatrolEnemy;
import objs.particles.Particle;
import objs.pickups.HpPickup;
import processing.core.PConstants;

public class PlayingState extends GameState {
	
	public PlayingState(GameContext context, DrawEngine drawEngine) {
		super(context, drawEngine);
	}

	@Override
	public void display() {
		displayGame();		
	}

	@Override
	public GameState update(int mouseX, int mouseY, PlayerCharacter player) {
		updateStep(mouseX, mouseY, player);

		if (random.nextInt(15) == 0) {
			FlockCharacter boid = new FlockCharacter(
					parent.random(ShooterGame.SCREEN_X),
					parent.random(ShooterGame.SCREEN_Y),
					5, 5, context.players);
			
			context.enemies.add(boid);
			context.flockEnemies.add(boid);
		}
		
		if (random.nextInt(200) == 0) context.enemies.add(
				new CircleEnemy(parent.random(ShooterGame.SCREEN_X), 
										parent.random(ShooterGame.SCREEN_Y), 
										15, 5, 
										context.players));
		
		if (random.nextInt(200) == 0) context.enemies.add(
				new AmbushEnemy(parent.random(ShooterGame.SCREEN_X), 
										parent.random(ShooterGame.SCREEN_Y), 
										15,10,
										context.players));
		
		if (random.nextInt(200) == 0) context.enemies.add(
				new BasicChaseEnemy(parent.random(ShooterGame.SCREEN_X), 
										parent.random(ShooterGame.SCREEN_Y), 
										15,10,
										context.players));
		
		if (random.nextInt(250) == 0) {
			HpPickup pickup = new HpPickup(parent.random(ShooterGame.SCREEN_X), parent.random(ShooterGame.SCREEN_Y), 5);
			if (random.nextInt(10) == 0) {
				context.enemies.add(new PatrolEnemy(pickup.position.x, pickup.position.y,
														15, 15, 100f, 200f, context.players));
			}
			context.pickups.add(pickup);
		}
		
		return this;
	}

	@Override
	public GameState handleInput(GameInput input, PlayerCharacter player) {
		player.facingDirection((int)input.mouseX, (int)input.mouseY);
		
		if (input.keyDown) {
//			context.players.get(0).directionPress(input.keyCode);
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
		}
		else {
//			context.players.get(0).directionRelease(input.keyCode);
			player.directionRelease(input.keyCode);
		}
		
		if (input.mouseButton == PConstants.LEFT) {
			Particle bullet = player.attack(input.mouseX, input.mouseY);
			if (bullet != null) {
				context.particles.add(bullet);
			}
		}
		
		return this;
	}

}
