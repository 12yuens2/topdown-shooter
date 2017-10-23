package characters;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import game.DrawEngine;
import game.ShooterGame;
import particles.Particle;
import processing.core.PApplet;
import processing.core.PVector;

public class PlayerCharacter extends Character {

	public static final float SPEED = 5.0f;

	public PVector facing;
	
	private float orientation;
	private float up, down, left, right;
	
	public PlayerCharacter(float xPos, float yPos) {
		super(xPos, yPos);
		this.orientation = 0f;
		this.facing = new PVector(xPos + 10 * PApplet.cos(orientation), yPos + 10 * PApplet.sin(orientation));
	}
	
	@Override
	public void move() {
		position.x = Math.min(ShooterGame.SCREEN_X, Math.max(0, position.x + (right - left) * SPEED));
		position.y = Math.min(ShooterGame.SCREEN_Y, Math.max(0, position.y + (down - up) * SPEED));
		
		facing.x = position.x + 10 * PApplet.cos(orientation);
		facing.y = position.y + 10 * PApplet.sin(orientation);
	}
	

	@Override
	public void display(DrawEngine drawEngine) {
		drawEngine.drawEllipse(drawEngine.parent.color(255, 0, 0), position.x, position.y, 30, 30);
		drawEngine.drawEllipse(drawEngine.parent.color(0, 255, 0), facing.x, facing.y, 10, 10);		
	}
	
	
	
	public void directionPress(int keyCode) {
		changeDirection(1, keyCode);
	}
	
	public void directionRelease(int keyCode) {
		changeDirection(0, keyCode);
	}
	
	private void changeDirection(int direction, int keyCode) {
		switch(keyCode) {
			case KeyEvent.VK_W:
				up = direction;
				break;
			case KeyEvent.VK_S:
				down = direction;
				break;
			case KeyEvent.VK_A:
				left = direction;
				break;
			case KeyEvent.VK_D:
				right = direction;
				break;
		}
	}

	public void facingDirection(int mouseX, int mouseY) {
		orientation = PApplet.atan2(mouseY - position.y , mouseX - position.x);
		
	}


	
}
	
