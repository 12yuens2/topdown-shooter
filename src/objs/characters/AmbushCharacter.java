package objs.characters;

import java.util.Random;

import game.DrawEngine;
import processing.core.PVector;

/**
 * Enemy that moves towards where it guesses the player will be.
 * On every update step, there is a chance it will update its target position.
 * @author sy35
 *
 */
public class AmbushCharacter extends Character {

	public static final float DISTANCE = 200f;
	public static final int DELAY = 75;
	
	
	public PVector targetPosition;
	public PlayerCharacter targetPlayer;
	
	public AmbushCharacter(float xPos, float yPos, float radius, int health, PlayerCharacter target) {
		super(xPos, yPos, radius, health);
		this.targetPlayer = target;
		this.targetPosition = new PVector(target.position.x, target.position.y);
	}

	@Override
	public void display(DrawEngine drawEngine) {		
		drawCircularObject(drawEngine.parent.color(0, 180, 180), drawEngine);
		
		//draw target
//		drawEngine.drawEllipse(drawEngine.parent.color(255), targetPosition.x, targetPosition.y, 5, 5);
	}

	@Override
	public void integrate() {
		PVector velocity = getVelocityToTarget(targetPosition);
		
		/* Random chance to update target position*/
		Random r = new Random();
		if (r.nextInt(DELAY) == 0 || velocity.mag() < 1f) {
			
			/* Target position is based on the direction the player is currently heading */
			float targetX = getX(targetPlayer.position.x + (targetPlayer.right - targetPlayer.left) * DISTANCE);
			float targetY = getY(targetPlayer.position.y + (targetPlayer.down - targetPlayer.up) * DISTANCE);
			
			targetPosition = new PVector(targetX, targetY);
		}

		move(velocity);
	}

}
