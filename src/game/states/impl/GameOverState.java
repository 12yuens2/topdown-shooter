package game.states.impl;

import java.awt.event.KeyEvent;
import java.util.Random;

import game.DrawEngine;
import game.GameContext;
import game.GameInput;
import game.ShooterGame;
import game.states.GameState;
import objs.characters.PlayerCharacter;

public class GameOverState extends GameState {

	public GameOverState(GameContext context) {
		super(context);
	}

	@Override
	public void display(DrawEngine drawEngine, PlayerCharacter player) {
		displayGame(drawEngine);
		ui.display(drawEngine, player);

		drawEngine.drawText(64, "Game Over", ShooterGame.SCREEN_X/2, ShooterGame.SCREEN_Y/2, 255);
		
	}

	@Override
	public GameState update(int mouseX, int mouseY, PlayerCharacter player) {
		updateStep(mouseX, mouseY, player);
		
		return this;
	}

	@Override
	public GameState handleInput(GameInput input, PlayerCharacter player) {
		if (input.keyCode == KeyEvent.VK_ENTER && player != null) {
			Random r = new Random();
			player.resetPlayer(r.nextInt(ShooterGame.SCREEN_X), r.nextInt(ShooterGame.SCREEN_Y));
			
			return new StartState(new GameContext(player));
		}
		
		return this;
	}

}
