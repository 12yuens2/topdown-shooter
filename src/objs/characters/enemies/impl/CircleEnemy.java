package objs.characters.enemies.impl;

import java.util.ArrayList;
import java.util.Random;

import game.DrawEngine;
import game.factories.parameters.EnemySpawnParameter;
import objs.characters.Character;
import objs.characters.PlayerCharacter;
import objs.characters.enemies.Enemy;
import processing.core.PVector;

public class CircleEnemy extends Enemy {

	public PVector velocity;
	
	private float linearMag;
	
	public CircleEnemy(float xPos, float yPos, EnemySpawnParameter spawnParam, ArrayList<PlayerCharacter> targets) {
		super(xPos, yPos, spawnParam, targets);
		this.velocity = new PVector(0, 0);
		
		Random r = new Random();
		this.linearMag = r.nextFloat() * 0.08f + 0.01f;
	}

	@Override
	public void display(DrawEngine drawEngine) {
		drawCircularObject(drawEngine.parent.color(183, 91, 12), drawEngine);
	}

	@Override
	public void integrate() {
		PVector targetPosition = getClosestTargetPosition();
		
		if (targetPosition != null) {
			PVector linear = PVector.sub(targetPosition, position);
			linear.normalize().mult(linearMag);
			velocity.add(linear);
			
			if (velocity.mag() > 2f) {
				velocity.normalize().mult(2f);
			}
		}
		
		position.add(velocity);
	}

}
