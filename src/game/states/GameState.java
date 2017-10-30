package game.states;

import java.util.Iterator;
import java.util.Random;
import java.util.function.Function;

import game.DrawEngine;
import game.GameContext;
import game.GameInput;
import objs.characters.Character;
import objs.characters.FlockCharacter;
import objs.particles.Explosion;
import objs.particles.Missile;
import objs.particles.Particle;
import objs.pickups.Pickup;
import objs.weapons.Weapon;
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
		context.player.integrate();
		context.player.facingDirection(mouseX, mouseY);
		
		Iterator<Character> enemyIt = context.enemies.iterator();
		while(enemyIt.hasNext()) {
			Character enemy = enemyIt.next();
			
			if (enemy instanceof FlockCharacter) {
				((FlockCharacter) enemy).flock(context.flockEnemies);
			}
			
			enemy.integrate();
			if (enemy.health <= 0) {
				enemyIt.remove();
				context.flockEnemies.remove(enemy);
			}
			
			/* Collision with other enemies to resolve overlapping */
			enemy.collideResult(context.enemies.iterator(), new Function<Character, Boolean>() {

				@Override
				public Boolean apply(Character c) {
//					PVector normal = PVector.sub(enemy.position, c.position).normalize();
//					enemy.position.add(normal.mult(1));
					
					return false;
				}
				
			});
			
			/* Collision with particles */
			enemy.collideResult(context.particles.iterator(), new Function<Particle, Boolean>() {

				@Override
				public Boolean apply(Particle p) {
					enemy.health -= p.damage;
					if (p instanceof Missile) context.explosions.add(((Missile)p).explode());
					
					return true;
				}
				
			});
			
			/* Collision with explosions*/
			enemy.collideResult(context.explosions.iterator(), new Function<Explosion, Boolean>() {

				@Override
				public Boolean apply(Explosion e) {
					enemy.health -= e.damage;
					
					return e.lifespan <= 0;
				}
				
			});
			
		}

		/* Player pickups */
		context.player.collideResult(context.pickups.iterator(), new Function<Pickup, Boolean>() {

			@Override
			public Boolean apply(Pickup p) {
				p.effect.apply(context.player);
				context.player.powerups.add(p.effect);
				return true;
			}
			
		});

		for (Particle particle : context.particles) particle.integrate();
		for (Explosion explosion : context.explosions) explosion.integrate();
		for (Weapon weapon : context.player.weapons) weapon.integrate();
	}
	
}
