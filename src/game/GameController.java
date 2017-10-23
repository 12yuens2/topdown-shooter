package game;
import characters.PlayerCharacter;
import processing.core.PApplet;
import processing.core.PConstants;

public class GameController {

	public PApplet parent;
	
	public DrawEngine drawEngine; 
	
	PlayerCharacter player;
	
	public GameController(PApplet parent) {
		this.parent = parent;
		this.drawEngine = new DrawEngine(parent);
		
		this.player = new PlayerCharacter(100, 100);
	}

	public void step(int mouseX, int mouseY) {
		parent.background(0);

		player.display(drawEngine);
		player.move();
		player.facingDirection(mouseX, mouseY);
	}
	
	public void handleInput(int mouseX, int mouseY, int mouseButton, int keyCode, boolean keyDown) {
		if (keyDown) player.directionPress(keyCode);
		else player.directionRelease(keyCode);
		

	}
}
