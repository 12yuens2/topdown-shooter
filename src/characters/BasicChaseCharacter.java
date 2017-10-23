package characters;

import game.DrawEngine;
import processing.core.PVector;

public class BasicChaseCharacter extends Character {

	public PlayerCharacter target;
	
	public BasicChaseCharacter(float xPos, float yPos, PlayerCharacter target) {
		super(xPos, yPos);
		this.target = target;
	}

	@Override
	public void display(DrawEngine drawEngine) {
		drawEngine.drawEllipse(drawEngine.parent.color(0,255,0), position.x, position.y, 30, 30);
		
	}

	@Override
	public void move() {
		PVector direction = new PVector((target.position.x - position.x), (target.position.y - position.y)).normalize().mult(2f);
		position.x += direction.x;
		position.y += direction.y;
	}

}
