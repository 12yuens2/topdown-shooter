package game.states.impl;


import game.DrawEngine;
import game.GameContext;
import game.GameInput;
import game.ShooterGame;
import game.states.GameState;
import objs.characters.AmbushCharacter;
import objs.characters.BasicChaseCharacter;
import objs.characters.Character;
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

		if (random.nextInt(150) == 0) context.enemies.add(
				new BasicChaseCharacter(parent.random(ShooterGame.SCREEN_X), 
										parent.random(ShooterGame.SCREEN_Y), 
										15, 5, 
										context.player));
		
		if (random.nextInt(50) == 0) context.enemies.add(
				new AmbushCharacter(parent.random(ShooterGame.SCREEN_X), 
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
		if (input.keyDown) context.player.directionPress(input.keyCode);
		else context.player.directionRelease(input.keyCode);
		
		if (input.mouseButton == PConstants.LEFT) {
			context.particles.add(new Particle(context.player.facing.copy(), input.mouseX, input.mouseY, 5, 5));
		}
		
		return this;
	}

}
