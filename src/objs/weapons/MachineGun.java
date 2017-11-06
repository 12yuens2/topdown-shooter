package objs.weapons;

import game.DrawEngine;

public class MachineGun extends Gun {
	
	public static final int BASE_FIRERATE = 5;
	
	public MachineGun(float xPos, float yPos, float radius, int clipSize, int ammo, int maxAmmo, int reloadTime) {
		super(xPos, yPos, radius, clipSize, ammo, maxAmmo, reloadTime);
		this.fireRate = BASE_FIRERATE;
	}
	
	public MachineGun(float xPos, float yPos, float radius, int clipSize, int ammo, int maxAmmo, int reloadTime, boolean friendly) {
		this(xPos, yPos, radius, clipSize, ammo, maxAmmo, reloadTime);
	}
	
	@Override
	public void display(DrawEngine drawEngine) {
		float size = radius * 2;
		drawEngine.drawEllipse(drawEngine.parent.color(0,255,0), position.x, position.y, size, size);
	}
}
