package game;

import game.states.GameState;
import game.states.impl.PlayingState;
import objs.characters.PlayerCharacter;
import processing.core.PApplet;

public class GameController {

	public PApplet parent;
	
	public DrawEngine drawEngine; 
	
	public PlayerCharacter player;
	
	public GameState state;

	
	public GameController(PApplet parent, String name) {
		this.parent = parent;
		this.drawEngine = new DrawEngine(parent);
		
		this.player = new PlayerCharacter(name, 
										  (float) Math.random() * ShooterGame.SCREEN_X, 
										  (float) Math.random() * ShooterGame.SCREEN_Y, 
										  15, 100, false);
		
		this.state = new PlayingState(new GameContext(player), drawEngine);
		
	}

	public void step(int mouseX, int mouseY) {
		state.display();
		state = state.update(mouseX, mouseY, player);

	}
	
	public GameInput getInput(int mouseX, int mouseY, int mouseButton, int keyCode, boolean keyDown) {
		return new GameInput(mouseX, mouseY, mouseButton, keyCode, keyDown);
	}
	
	public void handleInput(int mouseX, int mouseY, int mouseButton, int keyCode, boolean keyDown) {
		GameInput input = getInput(mouseX, mouseY, mouseButton, keyCode, keyDown);
		handleInput(input, this.player);
	}
	
	public void handleInput(GameInput input, PlayerCharacter player) {
		state.handleInput(input, player);
	}
}
