package objs.characters;

import java.util.ArrayList;

import game.GameObject;
import game.ShooterGame;
import processing.core.PVector;

public abstract class Character extends GameObject {
	
	public boolean friendly;
	
	public Character(float xPos, float yPos, float radius) {
		super(xPos, yPos, radius);
		this.friendly = false;
	}
	
	public abstract void move();
	
	public void checkCollisions(ArrayList<Character> otherCharacters) {
		for (Character other : otherCharacters) {
			float collideDistance = radius + other.radius;
			float distance = PVector.dist(position, other.position);
			
			if (distance < collideDistance) {
				PVector normal = PVector.sub(position, other.position).normalize();
				position.add(normal.mult(1));
			}
		}
	}
	
	
	protected static float moveX(float xPos) {
		return Math.min(ShooterGame.SCREEN_X, Math.max(0, xPos));
	}
	
	protected static float moveY(float yPos) {
		return Math.min(ShooterGame.SCREEN_Y, Math.max(0, yPos));
	}
}
