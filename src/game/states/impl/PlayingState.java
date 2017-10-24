package game.states.impl;


import characters.BasicChaseCharacter;
import characters.Character;
import game.DrawEngine;
import game.GameContext;
import game.GameInput;
import game.ShooterGame;
import game.states.GameState;
import particles.Particle;
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

		if (random.nextInt(200) == 0) context.enemies.add(
				new BasicChaseCharacter(parent.random(ShooterGame.SCREEN_X), 
										parent.random(ShooterGame.SCREEN_Y), 
										15, 
										context.player));
		
		return this;
	}

	@Override
	public GameState handleInput(GameInput input) {
		if (input.keyDown) context.player.directionPress(input.keyCode);
		else context.player.directionRelease(input.keyCode);
		
		if (input.mouseButton == PConstants.LEFT) {
			context.particles.add(new Particle(context.player.facing.copy(), input.mouseX, input.mouseY));
		}
		
		return this;
	}

}
