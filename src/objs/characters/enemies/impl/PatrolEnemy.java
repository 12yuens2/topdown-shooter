package objs.characters.enemies.impl;

import java.util.ArrayList;

import game.DrawEngine;
import game.factories.parameters.EnemySpawnParameter;
import objs.characters.PlayerCharacter;
import objs.characters.enemies.Enemy;
import processing.core.PVector;

/**
 * Enemy that patrols around a given point. Typically around a pickup.
 * @author sy35
 *
 */
public class PatrolEnemy extends Enemy {

	public static final float DETECT_RADIUS = 200f;
	public static final float PATROL_DISTANCE = 100f;
	
	public ArrayList<PVector> patrolPositions;
	public PVector startingPosition;
	
	public float detectRadius;
	public boolean chase;
	public int currentPatrol;
	
	public PatrolEnemy(float xPos, float yPos, EnemySpawnParameter spawnParam, ArrayList<PlayerCharacter> targets)  {
		super(xPos, yPos, spawnParam, targets);
		this.speedMultiplier *= 1.5f;
		this.detectRadius = DETECT_RADIUS;
		this.chase = false;
		
		this.startingPosition = position.copy();
		
		this.patrolPositions = new ArrayList<>();
		addPatrolPositions(PATROL_DISTANCE);
		
		this.currentPatrol = 0;
	}

	@Override
	public void display(DrawEngine drawEngine) {
		drawCircularObject(drawEngine.parent.color(200, 180, 20), drawEngine);
	}
	
	@Override
	public void integrate() {
		PlayerCharacter target = getClosestTarget();
		
		/* Run directly towards the player */
		if (chase && target != null) {
			PVector velocity = getVelocityToTarget(target.position);
			move(velocity);
			
			/* Stop chasing if we've gone too far or lost the player */
			if (tooFarAway() && !detectPlayer(target)) chase = false;
		}
		
		/* Patrol */
		else {
			if (target != null && detectPlayer(target)) {
				chase = true; 
			}
			else {
				PVector targetPosition = patrolPositions.get(currentPatrol);
				PVector velocity = getVelocityToTarget(targetPosition);
				
				/* Update to next patrol location */
				if (velocity.mag() < 2f) {
					currentPatrol = (currentPatrol + 1) % patrolPositions.size();
					integrate();
				} 
				else {
					move(velocity);
				}
			}
		}
	}
	
	private boolean tooFarAway() {
		return PVector.dist(position, startingPosition) > detectRadius * 1.5;
	}
	
	private boolean detectPlayer(PlayerCharacter target) {
		/* Player detection is simply collision detection with a large radius */
		float collide = target.radius + detectRadius;
		float distance = PVector.dist(target.position, position);
		
		return distance < collide;
	}
	
	private void addPatrolPositions(float patrolDistance) {
		patrolPositions.add(new PVector(getX(position.x + patrolDistance), getY(position.y + patrolDistance)));
		patrolPositions.add(new PVector(getX(position.x - patrolDistance), getY(position.y + patrolDistance)));
		patrolPositions.add(new PVector(getX(position.x - patrolDistance), getY(position.y - patrolDistance)));
		patrolPositions.add(new PVector(getX(position.x + patrolDistance), getY(position.y - patrolDistance)));
	}



}
