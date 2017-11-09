package objs.characters;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Objects;

import game.DrawEngine;
import objs.particles.Particle;
import objs.pickups.effects.Effect;
import objs.weapons.Weapon;
import objs.weapons.impl.Gun;
import objs.weapons.impl.MachineGun;
import objs.weapons.impl.Rocket;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Class representing a character the player controls. 
 * @author sy35
 *
 */
public class PlayerCharacter extends Character {

	public static final float SPEED = 5.0f;
	public static final int DAMAGE = 5;
	public static final int HEALTH = 100;

	public final String name;
	
	public PVector facing;
	
	private float orientation;
	public float up, down, left, right;
	
	public boolean attacking;
	public int damage;
	public int damaged;
	
	public Weapon currentWeapon;
	public ArrayList<Weapon> weapons;
	
	public ArrayList<Effect> powerups;
	
	public PlayerCharacter(String name, float xPos, float yPos, float radius) {
		super(xPos, yPos, radius, HEALTH);
		this.name = name;
		
		resetPlayer(xPos, yPos);
	}
	
	/**
	 * Reset player attributes to start.
	 * @param xPos - x position of the player.
	 * @param yPos - y position of the player.
	 */
	public void resetPlayer(float xPos, float yPos) {
		/* Reset all movement directions */
		this.up = 0;
		this.down = 0;
		this.left = 0;
		this.right = 0;
		
		this.health = HEALTH;
		this.position = new PVector(xPos, yPos);
		
		this.friendly = true;
		this.attacking = false;
		
		this.speedMultiplier = SPEED;
		this.damage = DAMAGE;
		this.damaged = 0;
		this.orientation = 0f;
		this.facing = new PVector(xPos + 10 * PApplet.cos(orientation), yPos + 10 * PApplet.sin(orientation));
		
		/* Create weapons */
		this.weapons = new ArrayList<>();
		weapons.add(new Gun(facing.x, facing.y, radius/2f, 96, damage));
		weapons.add(new Rocket(facing.x, facing.y, radius/2f, 16, damage*10));
		weapons.add(new MachineGun(facing.x, facing.y, radius/2f, 180, damage));
		
		currentWeapon = weapons.get(0);
		
		this.powerups = new ArrayList<>();
	}

	@Override
	public void display(DrawEngine drawEngine) {
		/* Draw player */
		drawCircularObject(drawEngine.parent.color(255, 0, 0), drawEngine);
		
		/* Draw weapon */
		currentWeapon.display(drawEngine);	
	}
	
	
	@Override
	public void integrate() {
		/* Limit maximum and minimum player speed */
		if (speedMultiplier > 10f) speedMultiplier = 10f;
		if (speedMultiplier < SPEED) speedMultiplier = SPEED;
		
		/* Update position */
		position.x = getX(position.x + (right - left) * speedMultiplier);
		position.y = getY(position.y + (down - up) * speedMultiplier);
		
		/* Update facing, code from lectures */
		facing.x = position.x + 10 * PApplet.cos(orientation);
		facing.y = position.y + 10 * PApplet.sin(orientation);
		
		/* Set weapon positions to be facing properly */
		for (Weapon w : weapons) {
			w.position = facing.copy();
		}
		
		damaged--;

	}
	
	public void takeDamage(int damage) {
		health -= damage;
		damaged = 100;
	}
	
	/**
	 * Key press for changing direction.
	 * @param keyCode
	 */
	public void directionPress(int keyCode) {
		changeDirection(1, keyCode);
	}
	
	/**
	 * Key release for changing direction.
	 * In other words, no long moving in that direction.
	 * @param keyCode
	 */
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

	/**
	 * Update player orientation following lecture code.
	 * @param mouseX - x position of the mouse cursor.
	 * @param mouseY - y position of the mouse cursor.
	 */
	public void facingDirection(int mouseX, int mouseY) {
		orientation = PApplet.atan2(mouseY - position.y , mouseX - position.x);
	}

	/**
	 * Attack with the current weapon.
	 * @param targetX - x position of the target.
	 * @param targetY - y position of the target.
	 * @return the particle that was fired from the weapon. Null if unable to fire.
	 */
	public Particle attack(float targetX, float targetY) {
		if (currentWeapon.firing <= 0) {
			currentWeapon.firing = currentWeapon.fireRate;
			Particle bullet = currentWeapon.shoot(targetX, targetY);
			
			/* Reload for the player if they tried to attack with no ammo */
			if (bullet == null) currentWeapon.reload();
			
			return bullet;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Override equals so that players with the same name are the same.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof PlayerCharacter)) return false;
		
		PlayerCharacter player = (PlayerCharacter) o;
		
		return this.name.equals(player.name);
	}
	
	/**
	 * Override hashcode so that players with the same name are the same.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
	
}
	
