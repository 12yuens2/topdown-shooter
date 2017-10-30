package objs.characters;

import java.util.ArrayList;

import game.DrawEngine;
import processing.core.PApplet;
import processing.core.PVector;


/**
 * Enemy type that implements flocking behaviour.
 * A lot of the code in this class is taken or follows from the Processing demo on flocking
 * https://processing.org/examples/flocking.html
 * 
 * @author sy35
 *
 */
public class FlockCharacter extends Character {

	public static final float MAX_SPEED = 2f;
	public static final float MAX_FORCE = 0.04f;
	
	PlayerCharacter target;
	PVector velocity, acceleration;
	
	public FlockCharacter(float xPos, float yPos, float radius, int health, PlayerCharacter target) {
		super(xPos, yPos, radius, health);
		this.target = target;
		this.velocity = PVector.sub(target.position, position).normalize().mult(MAX_SPEED);
		this.acceleration = new PVector(0, 0);
	}

	@Override
	public void display(DrawEngine drawEngine) {
		
		PApplet parent = drawEngine.parent;
		
		float theta = velocity.heading() + parent.radians(90);
	    // heading2D() above is now heading() but leaving old syntax until Processing.js catches up
	    
	    parent.fill(200, 100);
	    parent.stroke(255);
	    parent.pushMatrix();
	    parent.translate(position.x, position.y);
	    parent.rotate(theta);
	    parent.beginShape(parent.TRIANGLES);
	    parent.vertex(0, -radius*2);
	    parent.vertex(-radius, radius*2);
	    parent.vertex(radius, radius*2);
	    parent.endShape();
	    parent.popMatrix();
//		float size = radius * 2;
//		drawEngine.drawEllipse(drawEngine.parent.color(250, 10, 250), position.x, position.y, size, size);
	}

	@Override
	public void integrate() {
		velocity.add(acceleration);
		velocity.limit(MAX_SPEED);
		position.add(velocity);
		
		/* Reset acceleration to 0 */
		acceleration.mult(0);
		
	}
	
	public void flock(ArrayList<FlockCharacter> flock) {
		acceleration.add(seperate(flock));
		acceleration.add(align(flock));
		acceleration.add(cohese(flock));
	}

	private PVector seperate(ArrayList<FlockCharacter> flock) {
		float sep = radius * 5;
		PVector direction = new PVector(0, 0);
		
		int count = 0;
		for (FlockCharacter boid : flock) {
			float distance = PVector.dist(boid.position, position);
			
			if (distance > 0 && distance < sep) {
				PVector normal = PVector.sub(position, boid.position).normalize().div(distance);
				direction.add(normal);
				count++;
			}
		}
		
		if (count > 0) direction.div(count);

		if (direction.mag() > 0) {
			direction.setMag(MAX_SPEED);
			direction.sub(velocity);
			direction.limit(MAX_FORCE);
		}
		
		return direction;
	}
	
	private PVector align(ArrayList<FlockCharacter> flock) {
		float neighbourDistance = radius * 10;
		PVector sum = new PVector(0, 0);
		
		int count = 0;
		for (FlockCharacter boid : flock) {
			float distance = PVector.dist(boid.position, position);
			if ((distance > 0) && distance < neighbourDistance) {
				sum.add(boid.velocity);
				count++;
			}
		}
		
		if (count > 0) {
			sum.div(count);
			
			sum.normalize().mult(MAX_SPEED);
			PVector direction = PVector.sub(sum, velocity);
			direction.limit(MAX_FORCE);
			return direction;
		}
		else {
			return seekPlayer();
		}
	}

	
	private PVector cohese(ArrayList<FlockCharacter> flock) {
		float neighourDistance = radius * 10;
		PVector sum = new PVector(0, 0);
		
		int count = 0;
		for (FlockCharacter boid : flock) {
			float distance = PVector.dist(boid.position, position);
			if (distance > 0 && distance < neighourDistance) {
				sum.add(boid.position);
				count++;
			}
		}
		
		if (count > 0) {
			sum.div(count);
			PVector target = PVector.sub(position, sum);
			target.normalize().mult(MAX_SPEED);
			
			PVector direction = PVector.sub(target, velocity);
			direction.limit(MAX_FORCE);
			return direction;
		}
		else {
			return seekPlayer();
		}
	}
	
	private PVector seekPlayer() {
//		PVector direction = PVector.sub(target.position, position);
//		
//		direction.normalize().mult(MAX_SPEED);
//		
//		return direction;
		
		return new PVector(0,0);
		
	}





	
}
