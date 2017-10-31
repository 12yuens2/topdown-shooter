package objs.characters;

import java.util.ArrayList;

import game.DrawEngine;
import processing.core.PVector;

public class PatrolCharacter extends Character {

	public ArrayList<PVector> patrolPositions;
	public PVector startingPosition;
	public PlayerCharacter target;
	
	public float detectRadius;
	public boolean chase;
	public int currentPatrol;
	
	public PatrolCharacter(float xPos, float yPos, float radius, int health, float patrolDistance, float detectRadius, PlayerCharacter target) {
		super(xPos, yPos, radius, health);
		this.speedMultiplier *= 1.5f;
		this.target = target;
		this.detectRadius = detectRadius;
		this.chase = false;
		
		this.startingPosition = position.copy();
		
		this.patrolPositions = new ArrayList<>();
		addPatrolPositions(patrolDistance);
		
		this.currentPatrol = 0;
	}

	@Override
	public void display(DrawEngine drawEngine) {
		drawCircularObject(drawEngine.parent.color(200, 180, 20), drawEngine);
	}
	
	@Override
	public void integrate() {
		/* Run directly towards the player */
		if (chase) {
			PVector velocity = getVelocityToTarget(target.position);
			move(velocity);
			
			/* Stop chasing if we've gone too far or lost the player */
			if (tooFarAway() && !detectPlayer()) chase = false;
		}
		
		/* Patrol */
		else {
			if (detectPlayer()) {
				chase = true; 
			}
			else {
				PVector targetPosition = patrolPositions.get(currentPatrol);
				PVector velocity = getVelocityToTarget(targetPosition);
				
				/* Update to next patrol location */
				if (velocity.mag() < 1f) {
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
	
	private boolean detectPlayer() {
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
