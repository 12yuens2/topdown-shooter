package objs.characters;

import game.DrawEngine;
import processing.core.PVector;

public class BasicChaseCharacter extends Character {

	public PlayerCharacter target;
	
	public BasicChaseCharacter(float xPos, float yPos, float radius, PlayerCharacter target) {
		super(xPos, yPos, radius);
		this.target = target;
	}

	@Override
	public void display(DrawEngine drawEngine) {
		float size = radius * 2;
		drawEngine.drawEllipse(drawEngine.parent.color(0,255,0), position.x, position.y, size, size);
	}

	@Override
	public void move() {
		PVector direction = new PVector((target.position.x - position.x), (target.position.y - position.y)).normalize().mult(1f);
		position.x += direction.x;
		position.y += direction.y;
	}

}
