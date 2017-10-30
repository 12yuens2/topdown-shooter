package objs.characters;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import game.DrawEngine;
import game.ShooterGame;
import objs.particles.Particle;
import objs.pickups.effects.Effect;
import objs.weapons.Gun;
import objs.weapons.Rocket;
import objs.weapons.Weapon;
import processing.core.PApplet;
import processing.core.PVector;

public class PlayerCharacter extends Character {

	public static final float SPEED = 5.0f;

	public PVector facing;
	
	private float orientation;
	public float up, down, left, right;
	
	
	public Weapon currentWeapon;
	public ArrayList<Weapon> weapons;
	
	public ArrayList<Effect> powerups;
	
	public PlayerCharacter(float xPos, float yPos, float radius, int health) {
		super(xPos, yPos, radius, health);
		this.friendly = true;
		
		this.speedMultiplier = SPEED;
		this.orientation = 0f;
		this.facing = new PVector(xPos + 10 * PApplet.cos(orientation), yPos + 10 * PApplet.sin(orientation));
		
		this.weapons = new ArrayList<>();
		weapons.add(new Gun(facing.x, facing.y, radius/2f, 12, 12, 120, 300, 120));
		weapons.add(new Rocket(facing.x, facing.y, radius/2f, 12, 12, 120, 300, 240));
		
		currentWeapon = weapons.get(0);
		
		this.powerups = new ArrayList<>();
		
	}	

	@Override
	public void display(DrawEngine drawEngine) {
		float size = radius * 2;
		drawEngine.drawEllipse(drawEngine.parent.color(255, 0, 0), position.x, position.y, size, size);
		
		currentWeapon.display(drawEngine);	
	}
	
	
	@Override
	public void integrate() {
		if (speedMultiplier > 10f) speedMultiplier = 10f;
		if (speedMultiplier < SPEED) speedMultiplier = SPEED;
		position.x = getX(position.x + (right - left) * speedMultiplier);
		position.y = getY(position.y + (down - up) * speedMultiplier);
		
		facing.x = position.x + 10 * PApplet.cos(orientation);
		facing.y = position.y + 10 * PApplet.sin(orientation);
		currentWeapon.position = facing.copy();
		
		Iterator<Effect> effectIt = powerups.iterator();
		while(effectIt.hasNext()) {
			Effect effect = effectIt.next();
			effect.lifespan--;
			
			if (effect.lifespan <= 0) {
				effect.cease(this);
				effectIt.remove();
			}
		}
	}
	
	
	public void directionPress(int keyCode) {
		changeDirection(1, keyCode);
	}
	
	public void directionRelease(int keyCode) {
		changeDirection(0, keyCode);
	}
	
	private void changeDirection(int direction, int keyCode) {
		switch(keyCode) {
			case KeyEvent.VK_W:
				up = direction;
				break;
			case KeyEvent.VK_S:
				down = direction;
				break;
			case KeyEvent.VK_A:
				left = direction;
				break;
			case KeyEvent.VK_D:
				right = direction;
				break;
		}
	}

	public void facingDirection(int mouseX, int mouseY) {
		orientation = PApplet.atan2(mouseY - position.y , mouseX - position.x);
	}

	public Particle attack(float targetX, float targetY) {
		return currentWeapon.shoot(targetX, targetY);
	}
	
	
}
	
