package game.states.impl;


import com.sun.glass.events.KeyEvent;

import game.DrawEngine;
import game.GameContext;
import game.GameInput;
import game.ShooterGame;
import game.states.GameState;
import objs.characters.AmbushCharacter;
import objs.characters.BasicChaseCharacter;
import objs.characters.Character;
import objs.characters.CircleCharacter;
import objs.characters.PatrolCharacter;
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
	public GameState update(int mouseX, int mouseY) {
		updateStep(mouseX, mouseY);

//		if (random.nextInt(50) == 0) context.enemies.add(
//				new CircleCharacter(parent.random(ShooterGame.SCREEN_X), 
//										parent.random(ShooterGame.SCREEN_Y), 
//										15, 5, 
//										context.player));
		
		if (random.nextInt(150) == 0) context.enemies.add(
				new AmbushCharacter(parent.random(ShooterGame.SCREEN_X), 
										parent.random(ShooterGame.SCREEN_Y), 
										15,10,
										context.player));
		
		if (random.nextInt(150) == 0) context.enemies.add(
				new BasicChaseCharacter(parent.random(ShooterGame.SCREEN_X), 
										parent.random(ShooterGame.SCREEN_Y), 
										15,10,
										context.player));
		
		if (random.nextInt(250) == 0) context.enemies.add(
				new PatrolCharacter(parent.random(ShooterGame.SCREEN_X), 
									parent.random(ShooterGame.SCREEN_Y), 
									15, 15, 100f, 200f, context.player));
		
		if (random.nextInt(250) == 0) context.pickups.add(
				new HpPickup(parent.random(ShooterGame.SCREEN_X), 
							 parent.random(ShooterGame.SCREEN_Y), 
							 5));
		
		return this;
	}

	@Override
	public GameState handleInput(GameInput input) {
		if (input.keyDown) {
			context.player.directionPress(input.keyCode);
			
			if (input.keyCode == KeyEvent.VK_R) {
				context.player.currentWeapon.reload();
			}
			
			if (input.keyCode == KeyEvent.VK_1) {
				context.player.currentWeapon = context.player.weapons.get(0);
			}
			if (input.keyCode == KeyEvent.VK_2) {
				context.player.currentWeapon = context.player.weapons.get(1);
			}
		}
		else context.player.directionRelease(input.keyCode);
		
		if (input.mouseButton == PConstants.LEFT) {
			Particle bullet = context.player.attack(input.mouseX, input.mouseY);
			if (bullet != null) {
				context.particles.add(bullet);
			}
		}
		
		
		
		return this;
	}

}
