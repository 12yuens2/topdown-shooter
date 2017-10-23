package characters;

import game.IDrawable;
import processing.core.PVector;

public abstract class Character implements IDrawable {

	public PVector position;
	
	public Character(float xPos, float yPos) {
		this.position = new PVector(xPos, yPos);
	}
	
	public abstract void move();
}
