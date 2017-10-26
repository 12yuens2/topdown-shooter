package game;

import processing.core.PVector;

public abstract class GameObject {

	public PVector position;
	public float radius;
	
	public GameObject(float xPos, float yPos, float radius) {
		this.position = new PVector(xPos, yPos);
		this.radius = radius;
	}
	
	public abstract void display(DrawEngine drawEngine);
}
