package objs.characters;

import game.DrawEngine;
import processing.core.PVector;

/**
 * Basic enemy that just follows the player, always trying to reach the player's position.
 * @author sy35
 *
 */
public class BasicChaseCharacter extends Character {

	public PlayerCharacter target;
	
	public BasicChaseCharacter(float xPos, float yPos, float radius, int health, PlayerCharacter target) {
		super(xPos, yPos, radius, health);
		this.target = target;
	}

	@Override
	public void display(DrawEngine drawEngine) {
		drawCircularObject(drawEngine.parent.color(0, 255, 0), drawEngine);
	}

	@Override
	public void integrate() {
		PVector velocity = getVelocityToTarget(target.position);
		move(velocity);
	}

}
