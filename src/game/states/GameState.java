package game.states;

import java.util.Random;

import characters.Character;
import game.DrawEngine;
import game.GameContext;
import game.GameInput;
import particles.Particle;
import processing.core.PApplet;

public abstract class GameState {

	public Random random;
	public PApplet parent;
	
	public GameContext context;
	public DrawEngine drawEngine;
	
	
	public GameState(GameContext context, DrawEngine drawEngine) {
		this.random = new Random();
		this.parent = drawEngine.parent;
		
		this.context = context;
		this.drawEngine = drawEngine;
	}
	
	public abstract void display();
	
	public abstract GameState update(int mouseX, int mouseY);

	public abstract GameState handleInput(GameInput input);

	
	public void displayGame() {
		parent.background(0);
		
		context.player.display(drawEngine);
		
		for (Character enemy : context.enemies) enemy.display(drawEngine);
		for (Particle particle : context.particles) particle.display(drawEngine);		
	}
	
	public void updateStep(int mouseX, int mouseY) {
		context.player.move();
		context.player.facingDirection(mouseX, mouseY);
		
		for (Character enemy : context.enemies) {
			enemy.move();
			enemy.checkCollisions(context.enemies);
		}
		for (Particle particle : context.particles) particle.integrate();
	}
	
}
