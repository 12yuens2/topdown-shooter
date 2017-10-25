package objs.characters;

import java.awt.event.KeyEvent;

import game.DrawEngine;
import game.ShooterGame;

import processing.core.PApplet;
import processing.core.PVector;

public class PlayerCharacter extends Character {

	public static final float SPEED = 5.0f;

	public PVector facing;
	public PVector destination;
	
	private float orientation;
	public float up, down, left, right;
	
	public PlayerCharacter(float xPos, float yPos, float radius) {
		super(xPos, yPos, radius);
		this.friendly = true;
		this.orientation = 0f;
		this.facing = new PVector(xPos + 10 * PApplet.cos(orientation), yPos + 10 * PApplet.sin(orientation));
		
		this.destination = position;
	}
	
	@Override
	public void move() {
		position.x = moveX(position.x + (right - left) * SPEED);
		position.y = moveY(position.y + (down - up) * SPEED);

		if (PVector.sub(destination, position).mag() > 2f) {
			PVector velocity = new PVector((destination.x - position.x), (destination.y - position.y)).normalize().mult(3f);
			position.add(velocity);
		}
		
		facing.x = position.x + 10 * PApplet.cos(orientation);
		facing.y = position.y + 10 * PApplet.sin(orientation);
	}
	

	@Override
	public void display(DrawEngine drawEngine) {
		float size = radius * 2;
		drawEngine.drawEllipse(drawEngine.parent.color(255, 0, 0), position.x, position.y, size, size);
		drawEngine.drawEllipse(drawEngine.parent.color(0, 255, 0), facing.x, facing.y, size/3, size/3);		
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

	public void moveTo(float mouseX, float mouseY) {
		destination = new PVector(mouseX, mouseY);
	}

	public void stopMoving() {
		destination = position;		
	}


	
}
	
