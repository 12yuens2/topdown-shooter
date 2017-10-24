package characters;

import java.util.ArrayList;

import game.IDrawable;
import processing.core.PVector;

public abstract class Character implements IDrawable {

	public PVector position;
	
	public float radius;
	
	public boolean friendly;
	
	public Character(float xPos, float yPos, float radius) {
		this.position = new PVector(xPos, yPos);
		this.radius = radius;
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
}
