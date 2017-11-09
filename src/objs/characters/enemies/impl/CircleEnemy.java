package objs.characters.enemies.impl;

import java.util.ArrayList;

import game.DrawEngine;
import game.factories.parameters.EnemySpawnParameter;
import objs.characters.PlayerCharacter;
import objs.characters.enemies.Enemy;
import processing.core.PVector;

/**
 * Enemy based on lecture code with acceleration.
 * @author sy35
 *
 */
public class CircleEnemy extends Enemy {

	public PVector velocity;
	
	private float linearMag;
	
	public CircleEnemy(float xPos, float yPos, EnemySpawnParameter spawnParam, ArrayList<PlayerCharacter> targets) {
		super(xPos, yPos, spawnParam, targets);
		this.velocity = new PVector(0, 0);
		
		this.linearMag = random.nextFloat() * 0.08f + 0.01f;
	}

	@Override
	public void display(DrawEngine drawEngine) {
		drawCircularObject(drawEngine.parent.color(183, 91, 12), drawEngine);
	}

	@Override
	public void integrate() {
		PVector targetPosition = getClosestTargetPosition();
		
		if (targetPosition != null) {
			/* Update velocity based on linearMag */
			PVector linear = PVector.sub(targetPosition, position);
			linear.normalize().mult(linearMag);
			velocity.add(linear);
			
			/* Limit maximum velocity */
			if (velocity.mag() > 2f) {
				velocity.normalize().mult(2f);
			}
		}
		
		position.add(velocity);
	}

}
