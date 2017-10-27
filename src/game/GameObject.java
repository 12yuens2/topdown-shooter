package game;

import java.util.Iterator;
import java.util.function.Function;

import processing.core.PVector;

public abstract class GameObject {

	public PVector position;
	public float radius;
	
	public GameObject(float xPos, float yPos, float radius) {
		this.position = new PVector(xPos, yPos);
		this.radius = radius;
	}
	
	public abstract void display(DrawEngine drawEngine);
	
	public abstract void integrate();
	
	public boolean hasCollided(GameObject other) {
		float collide = radius + other.radius;
		float distance = PVector.dist(position, other.position);
		
		return distance < collide;
	}
	
	public <T extends GameObject> void collideResult(Iterator<T> others, Function<T, Boolean> result) {
		while (others.hasNext()) {
			T other = others.next();
			
			if (hasCollided(other)) {
				if (result.apply(other)) others.remove();
			}
		}
	}
}
