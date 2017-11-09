package game.states.impl;

import java.awt.event.KeyEvent;

import game.DrawEngine;
import game.GameContext;
import game.GameInput;
import game.ShooterGame;
import game.states.GameState;
import objs.characters.PlayerCharacter;

public class StartState extends GameState {

	public StartState(GameContext context) {
		super(context);
	}

	@Override
	public void display(DrawEngine drawEngine, PlayerCharacter player) {
		drawEngine.parent.background(0);
		drawEngine.drawText(64, "Press Enter to start.", ShooterGame.SCREEN_X/2, ShooterGame.SCREEN_Y/2, 255);
	}

	@Override
	public GameState update(int mouseX, int mouseY, PlayerCharacter player) {
		return this;
	}

	@Override
	public GameState handleInput(GameInput input, PlayerCharacter player) {
		if (input.keyCode == KeyEvent.VK_ENTER) {
			return new PlayingState(context);
		}
		
		return this;
	}

}
