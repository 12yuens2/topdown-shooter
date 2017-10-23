package game;
import java.util.ArrayList;

import characters.BasicChaseCharacter;
import characters.PlayerCharacter;
import particles.Particle;
import processing.core.PApplet;
import processing.core.PConstants;

public class GameController {

	public PApplet parent;
	
	public DrawEngine drawEngine; 
	
	PlayerCharacter player;
	BasicChaseCharacter enemy;
	
	ArrayList<Particle> particles;
	
	public GameController(PApplet parent) {
		this.parent = parent;
		this.drawEngine = new DrawEngine(parent);
		
		particles = new ArrayList<>();
		
		this.player = new PlayerCharacter(100, 100);
		this.enemy = new BasicChaseCharacter(500, 500, player);
		
	}

	public void step(int mouseX, int mouseY) {
		parent.background(0);

		player.display(drawEngine);
		enemy.display(drawEngine);
		player.move();
		enemy.move();
		player.facingDirection(mouseX, mouseY);
		
		for (Particle p : particles) {
			p.display(drawEngine);
			p.integrate();
		}
	}
	
	public void handleInput(int mouseX, int mouseY, int mouseButton, int keyCode, boolean keyDown) {
		if (keyDown) player.directionPress(keyCode);
		else player.directionRelease(keyCode);
		
		if (mouseButton == parent.LEFT) {
			particles.add(new Particle(player.facing.copy(), mouseX, mouseY));
		}

	}
}
