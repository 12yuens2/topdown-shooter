package game.states;

import game.DrawEngine;
import game.GameContext;
import game.GameInput;
import processing.core.PApplet;

public abstract class GameState {

	public GameContext context;
	public DrawEngine drawEngine;
	
	public PApplet parent;
	
	public GameState(GameContext context, DrawEngine drawEngine) {
		this.context = context;
		this.drawEngine = drawEngine;
		
		this.parent = drawEngine.parent;
	}
	
	public abstract void display();
	
	public abstract GameState update(int mouseX, int mouseY);

	public abstract GameState handleInput(GameInput input);

	
}
