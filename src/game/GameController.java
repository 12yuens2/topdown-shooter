package game;

import game.states.GameState;
import game.states.impl.StartState;
import objs.characters.PlayerCharacter;
import processing.core.PApplet;

/**
 * Controller for the game. Interfaces between the main class and game state.
 * @author sy35
 *
 */
public class GameController {

	public PApplet parent;
	public DrawEngine drawEngine; 
	public PlayerCharacter player;
	public GameState state;

	
	public GameController(PApplet parent, String name) {
		this.parent = parent;
		this.drawEngine = new DrawEngine(parent);
		
		this.player = new PlayerCharacter(name, 
										  (float) Math.random() * ShooterServer.SCREEN_X, 
										  (float) Math.random() * ShooterServer.SCREEN_Y, 
										  15);
		
		this.state = new StartState(new GameContext(player));
	}

	public void step(int mouseX, int mouseY) {
		state.display(drawEngine, player);
		if (player.name.equals("Server")) state = state.update(mouseX, mouseY, player);
	}
	
	public GameInput getInput(int mouseX, int mouseY, int mouseButton, boolean mouseDown, int keyCode, boolean keyDown) {
		return new GameInput(mouseX, mouseY, mouseButton, mouseDown, keyCode, keyDown);
	}
	
	public void handleInput(int mouseX, int mouseY, int mouseButton, boolean mouseDown, int keyCode, boolean keyDown) {
		GameInput input = getInput(mouseX, mouseY, mouseButton, mouseDown, keyCode, keyDown);
		handleInput(input, this.player);
	}
	
	public void handleInput(GameInput input, PlayerCharacter player) {
		state = state.handleInput(input, player);
	}
}
