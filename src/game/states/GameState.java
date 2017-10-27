package game.states;

import java.util.Iterator;
import java.util.Random;

import game.DrawEngine;
import game.GameContext;
import game.GameInput;
import objs.characters.Character;
import objs.particles.Explosion;
import objs.particles.Missile;
import objs.particles.Particle;
import objs.pickups.Pickup;
import processing.core.PApplet;
import processing.core.PVector;

public abstract class GameState {

	public Random random;
	public PApplet parent;
	
	public GameContext context;
	public DrawEngine drawEngine;
	
	
	public GameState(GameContext context, DrawEngine drawEngine) {
		this.random = new Random();
		this.parent = drawEngine.parent;
		
		this.context = context;
		this.drawEngine = drawEngine;
	}
	
	public abstract void display();
	
	public abstract GameState update(int mouseX, int mouseY);

	public abstract GameState handleInput(GameInput input);

	
	public void displayGame() {
		parent.background(0);
		
		context.player.display(drawEngine);
		
		drawEngine.displayDrawables(context.enemies, context.particles, context.pickups, context.explosions);
	}
	
	public void updateStep(int mouseX, int mouseY) {
		context.player.move();
		context.player.facingDirection(mouseX, mouseY);
		
		for (Character enemy : context.enemies) {
			enemy.move();
			enemy.checkCollisions(context.enemies);
		}
		
		Iterator<Character> enemyIt = context.enemies.iterator();
		
		while(enemyIt.hasNext()) {
			Character enemy = enemyIt.next();
			enemy.move();
			enemy.checkCollisions(context.enemies);
			if (enemy.health <= 0) enemyIt.remove();
			
			Iterator<Particle> particleIt = context.particles.iterator();
			while(particleIt.hasNext()) {
				Particle p = particleIt.next();
				float collide = p.radius + enemy.radius;
				float distance = PVector.dist(p.position, enemy.position);
				
				if (distance < collide) {
					enemy.health -= p.damage;
					
					if (p instanceof Missile) {
						context.explosions.add(((Missile)p).explode());
					}
					particleIt.remove();
				}
			}
			
			Iterator<Explosion> explosionIt = context.explosions.iterator();
			while(explosionIt.hasNext()) {
				Explosion explosion = explosionIt.next();
				float collide = explosion.radius + enemy.radius;
				float distance = PVector.dist(explosion.position, enemy.position);
				
				if (distance <= collide) enemy.health -= explosion.damage;
				if (explosion.lifespan <= 0) explosionIt.remove();
			}
			
		}
		
		
		Iterator<Pickup> pickupIt = context.pickups.iterator();
		while(pickupIt.hasNext()) {
			Pickup pickup = pickupIt.next();
			float collide = context.player.radius + pickup.radius;
			float distance = PVector.dist(context.player.position, pickup.position);
			
			if (distance < collide) {
				pickupIt.remove();
			}
		}
		
		for (Particle particle : context.particles) particle.integrate();
		for (Explosion explosion : context.explosions) explosion.integrate();
		
	}
	
}
