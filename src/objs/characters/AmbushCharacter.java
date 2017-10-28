package objs.characters;

import java.util.Random;

import game.DrawEngine;
import processing.core.PVector;

public class AmbushCharacter extends Character {

	public static final float DISTANCE = 200f;
	public static final int DELAY = 90;
	
	
	public PVector targetPosition;
	public PlayerCharacter targetPlayer;
	
	public AmbushCharacter(float xPos, float yPos, float radius, int health, PlayerCharacter target) {
		super(xPos, yPos, radius, health);
		this.targetPlayer = target;
		this.targetPosition = new PVector(target.position.x, target.position.y);
	}

	@Override
	public void display(DrawEngine drawEngine) {
		float size = radius * 2;
		drawEngine.drawEllipse(drawEngine.parent.color(0,180,180), position.x, position.y, size, size);
		
		//draw target
//		drawEngine.drawEllipse(drawEngine.parent.color(255), targetPosition.x, targetPosition.y, 5, 5);
	}

	@Override
	public void integrate() {
		PVector velocity = new PVector((targetPosition.x - position.x), (targetPosition.y - position.y));
		
		/* Move to new target position */
		Random r = new Random();
		if (r.nextInt(DELAY) == 0 || velocity.mag() < 1f) {
			float targetX = getX(targetPlayer.position.x + (targetPlayer.right - targetPlayer.left) * DISTANCE);
			float targetY = getY(targetPlayer.position.y + (targetPlayer.down - targetPlayer.up) * DISTANCE);
			
			targetPosition = new PVector(targetX, targetY);
		}

		position.add(velocity.normalize().mult(speedMultiplier));
		
	}

}
