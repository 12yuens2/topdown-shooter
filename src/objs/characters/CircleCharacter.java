package objs.characters;

import java.util.Random;

import game.DrawEngine;
import processing.core.PVector;

public class CircleCharacter extends Character {

	public PlayerCharacter target;
	public PVector velocity;
	
	private float linearMag;
	
	public CircleCharacter(float xPos, float yPos, float radius, int health, PlayerCharacter target) {
		super(xPos, yPos, radius, health);
		this.target = target;
		this.velocity = new PVector(0, 0);
		
		Random r = new Random();
		this.linearMag = r.nextFloat() * 0.08f + 0.01f;
	}

	@Override
	public void display(DrawEngine drawEngine) {
		float size = radius * 2;
		drawEngine.drawEllipse(drawEngine.parent.color(183,91,12), position.x, position.y, size, size);
	}

	@Override
	public void integrate() {
//		PVector velocity = new PVector((target.position.x - position.x), (target.position.y - position.y));
		
		
//		if (PVector.dist(position, target.position) < 300) {
//			velocity = new PVector(-velocity.y, velocity.x);
//		}
//		
//		position.add(velocity.normalize().mult(3f));
		
//		
		PVector linear = new PVector((target.position.x - position.x), (target.position.y - position.y));
		linear.normalize().mult(linearMag);
		velocity.add(linear);
		
		if (velocity.mag() > 2f) {
			velocity.normalize().mult(2f);
		}
		
		position.add(velocity);
	}

}
