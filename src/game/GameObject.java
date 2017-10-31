package game;

import java.io.Serializable;
import java.util.Iterator;
import java.util.function.Function;

import processing.core.PVector;

public abstract class GameObject implements Serializable {

	public PVector position;
	public float radius;
	
	public GameObject(float xPos, float yPos, float radius) {
		this.position = new PVector(xPos, yPos);
		this.radius = radius;
	}
	
	/**
	 * How a game object should be displayed in the game. 
	 * @param drawEngine
	 */
	public abstract void display(DrawEngine drawEngine);
	
	/**
	 * Update function called every frame.
	 */
	public abstract void integrate();
	
	
	/**
	 * Helper function to draw circular game objects to reduce code duplication.
	 * @param colour - Colour of the game object.
	 * @param drawEngine - Engine for drawing the object.
	 */
	protected void drawCircularObject(int colour, DrawEngine drawEngine) {
		float size = radius * 2;
		drawEngine.drawEllipse(colour, position.x, position.y, size, size);	
	}
	
	/**
	 * Check if 'this' game object as collided with another.
	 * @param other - Other object to check collision with.
	 * @return true if 'this' object has collided with the 'other' object.
	 */
	public boolean hasCollided(GameObject other) {
		float collide = radius + other.radius;
		float distance = PVector.dist(position, other.position);
		
		return distance < collide;
	}
	
	/**
	 * Helper function for checking collision with a list of other objects and resolving the collision.
	 * @param others - Iterator for a list of other game objects to check collision against.
	 * @param result - Function that defines what happens if there is a collision. 'result' returns whether or not the other game object should be removed from the iterator.
	 */
	public <T extends GameObject> void collideResult(Iterator<T> others, Function<T, Boolean> result) {
		while (others.hasNext()) {
			T other = others.next();
			
			if (hasCollided(other)) {
				if (result.apply(other)) others.remove();
			}
		}
	}
}
