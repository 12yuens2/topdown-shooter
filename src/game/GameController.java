package game;

import game.states.GameState;
import game.states.impl.PlayingState;
import processing.core.PApplet;

public class GameController {

	public PApplet parent;
	
	public DrawEngine drawEngine; 
	
	public GameContext context;
	public GameState state;

	
	public GameController(PApplet parent) {
		this.parent = parent;
		this.drawEngine = new DrawEngine(parent);
		this.context = new GameContext();

		this.state = new PlayingState(context, drawEngine);
		
	}

	public void step(int mouseX, int mouseY) {
		state.display();
		state = state.update(mouseX, mouseY);

	}
	
	public GameInput getInput(int mouseX, int mouseY, int mouseButton, int keyCode, boolean keyDown) {
		return new GameInput(mouseX, mouseY, mouseButton, keyCode, keyDown);
	}
	
	public void handleInput(int mouseX, int mouseY, int mouseButton, int keyCode, boolean keyDown) {
		GameInput input = getInput(mouseX, mouseY, mouseButton, keyCode, keyDown);
		handleInput(input);
	}
	
	public void handleInput(GameInput input) {
		state.handleInput(input);
	}
}
