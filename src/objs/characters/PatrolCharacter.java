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
		this.target = target;
		this.detectRadius = detectRadius;
		this.chase = false;
		
		this.startingPosition = position.copy();
		
		patrolPositions = new ArrayList<>();
		patrolPositions.add(new PVector(getX(position.x + patrolDistance), getY(position.y + patrolDistance)));
		patrolPositions.add(new PVector(getX(position.x - patrolDistance), getY(position.y + patrolDistance)));
		patrolPositions.add(new PVector(getX(position.x - patrolDistance), getY(position.y - patrolDistance)));
		patrolPositions.add(new PVector(getX(position.x + patrolDistance), getY(position.y - patrolDistance)));
		
		this.currentPatrol = 0;
	}

	@Override
	public void display(DrawEngine drawEngine) {
		float size = radius * 2;
//		drawEngine.drawEllipse(drawEngine.parent.color(255), position.x, position.y, detectRadius * 2, detectRadius * 2);
		drawEngine.drawEllipse(drawEngine.parent.color(200, 180, 20), position.x, position.y, size, size);
		
	}
	
	@Override
	public void integrate() {
		if (chase) {
			PVector velocity = new PVector((target.position.x - position.x), (target.position.y - position.y)).normalize().mult(1.75f);
			position.x += velocity.x;
			position.y += velocity.y;
			
			if (tooFarAway() && !detectPlayer()) {
				chase = false;
			}
		}
		else {
			if (detectPlayer()) {
				chase = true;
			} 
			else {
				PVector targetPosition = patrolPositions.get(currentPatrol);
				PVector velocity = new PVector(targetPosition.x - position.x, targetPosition.y - position.y);

				
				if (velocity.mag() < 1f) {
					currentPatrol = (currentPatrol + 1) % patrolPositions.size();
					integrate();
				} else {
					position.add(velocity.normalize().mult(1f));
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



}
