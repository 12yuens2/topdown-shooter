package objs.characters.enemies.impl;

import java.util.ArrayList;

import game.DrawEngine;
import objs.characters.Character;
import objs.characters.PlayerCharacter;
import objs.characters.enemies.Enemy;
import processing.core.PVector;

/**
 * Basic enemy that just follows the player, always trying to reach the player's position.
 * @author sy35
 *
 */
public class BasicChaseEnemy extends Enemy {

	
	public BasicChaseEnemy(float xPos, float yPos, float radius, int health, ArrayList<PlayerCharacter> targets) {
		super(xPos, yPos, radius, health, targets);
	}

	@Override
	public void display(DrawEngine drawEngine) {
		drawCircularObject(drawEngine.parent.color(0, 255, 0), drawEngine);
	}

	@Override
	public void integrate() {
		PVector targetPosition = getClosestTargetPosition();
		PVector velocity = getVelocityToTarget(targetPosition);
		move(velocity);
	}

}
