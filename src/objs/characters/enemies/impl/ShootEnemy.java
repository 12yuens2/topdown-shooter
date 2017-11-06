package objs.characters.enemies.impl;

import java.util.ArrayList;

import game.DrawEngine;
import game.GameContext;
import objs.characters.PlayerCharacter;
import objs.characters.enemies.Enemy;
import objs.particles.Particle;
import objs.weapons.Gun;
import processing.core.PVector;

public class ShootEnemy extends Enemy {
	
	public static final int INTERVAL = 80;
	
	public int shootInterval;

	public GameContext context;
	public PVector targetPosition;
	public Gun gun;
	
	public ShootEnemy(float xPos, float yPos, float radius, int health, GameContext context) {
		super(xPos, yPos, radius, health, context.players);
		
		this.shootInterval = 20;
		
		this.context = context;
		this.targetPosition = PVector.random2D();
		this.gun = new Gun(position.x, position.y, 5, 10, 50, 50, 60, false);	
	}

	@Override
	public void display(DrawEngine drawEngine) {
		float size = radius * 2;
		drawEngine.drawEllipse(drawEngine.parent.color(200, 10, 200), position.x, position.y, size, size);
		
	}

	@Override
	public void integrate() {
		if (random.nextInt(100) == 0) targetPosition = PVector.random2D();
		
		PlayerCharacter target = getClosestTarget();
		shoot(target);
		move(targetPosition);
		
		gun.position = position.copy();
	}
	
//	private void move(PlayerCharacter target) {
////		PVector velocity = getVelocityToTarget(PVector.add(PVector.random2D(), position));
//		move(PVector.random2D());
//	}
	
	private void shoot(PlayerCharacter target) {
		if (shootInterval <= 0) {
			shootInterval = INTERVAL;
			
			Particle bullet = gun.shoot(target.position.x, target.position.y);
			if (bullet != null) context.particles.add(bullet);
			
		}
		else {
			shootInterval--;
		}
	}

}
