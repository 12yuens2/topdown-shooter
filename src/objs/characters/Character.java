package objs.characters;

import game.DrawEngine;
import game.GameObject;
import game.ShooterGame;
import processing.core.PVector;

/**
 * Abstract class that represents a character in the game. This includes both player characters and enemies.
 * @author sy35
 *
 */
public abstract class Character extends GameObject {
	
	public boolean friendly;
	public int health;
	public float speedMultiplier;
	
	public Character(float xPos, float yPos, float radius, int health) {
		super(xPos, yPos, radius);
		this.health = health;
		
		/* Characters are unfriendly by default. Player characters should set this field to be true in their constructor. */
		this.friendly = false;
		
		/* Default speed of characters */
		this.speedMultiplier = 2f;
	}
	
	
	/**
	 * Get the velocity vector towards a target position.
	 * @param target - Target position to head towards.
	 * @return Velocity vector towards the target.
	 */
	protected PVector getVelocityToTarget(PVector targetPosition) {
		return new PVector(targetPosition.x - position.x, targetPosition.y - position.y);
	}
	
	/**
	 * Update the character's position with given velocity based on the character's speed. 
	 * @param velocity - Velocity vector to indicate the direction the character should move to.
	 */
	protected void move(PVector velocity) {
		position.add(velocity.setMag(speedMultiplier));
	}
	
	/**
	 * Helper function to return an x position that is within the game screen.
	 * @param xPos - x position that can be out of bounds of the game.
	 * @return - An x position that is in bounds of the game. If the given xPos is out of bounds, the x position at the boundary is returned.
	 */
	protected static float getX(float xPos) {
		return Math.min(ShooterGame.SCREEN_X, Math.max(0, xPos));
	}
	
	/**
	 * Helper function to return a y position that is within the game screen.
	 * @param yPos - y position that can be out of bounds of the game.
	 * @return - A y position that is in bounds of the game. If the given yPos is out of bounds, the y position at the boundary is returned.
	 */
	protected static float getY(float yPos) {
		return Math.min(ShooterGame.SCREEN_Y, Math.max(0, yPos));
	}
}
