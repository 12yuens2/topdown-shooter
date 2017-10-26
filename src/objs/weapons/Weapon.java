package objs.weapons;

import game.GameObject;
import objs.particles.Particle;

public abstract class Weapon extends GameObject {

	public int clipAmmo, ammo;
	public final int clipSize, maxAmmo;
	
	public Weapon(float xPos, float yPos, float radius, int clipSize, int clipAmmo, int ammo, int maxAmmo) {
		super(xPos, yPos, radius);
		this.clipAmmo = clipAmmo;
		this.ammo = ammo;
		this.clipSize = clipSize;
		this.maxAmmo = maxAmmo;
	}


	public abstract Particle shoot(float targetX, float targetY);
	
	public abstract void reload();
	
		
}
