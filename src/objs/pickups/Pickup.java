package objs.pickups;

import game.IDrawable;
import processing.core.PVector;

public abstract class Pickup implements IDrawable{

	public PVector position;
	public float radius;
	
	public Pickup(float xPos, float yPos, float radius) {
		this.position = new PVector(xPos, yPos);
		this.radius = radius;
	}
}
