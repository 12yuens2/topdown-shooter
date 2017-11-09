package objs.characters.enemies.impl;

import java.util.ArrayList;

import game.DrawEngine;
import game.factories.parameters.EnemySpawnParameter;
import objs.characters.PlayerCharacter;
import objs.characters.enemies.Enemy;
import processing.core.PVector;

/**
 * Basic enemy that just follows the player, always trying to reach the player's position.
 * @author sy35
 *
 */
public class BasicChaseEnemy extends Enemy {

	public BasicChaseEnemy(float xPos, float yPos, EnemySpawnParameter spawnParam, ArrayList<PlayerCharacter> targets) {
		super(xPos, yPos, spawnParam, targets);
	}

	@Override
	public void display(DrawEngine drawEngine) {
		drawCircularObject(drawEngine.parent.color(0, 255, 0), drawEngine);
	}

	@Override
	public void integrate() {
		PVector targetPosition = getClosestTargetPosition();
		
		if (targetPosition != null) {
			PVector velocity = getVelocityToTarget(targetPosition);
			move(velocity);
		}
	}

}
