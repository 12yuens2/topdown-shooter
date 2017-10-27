package objs.characters;

import game.GameObject;
import game.ShooterGame;

public abstract class Character extends GameObject {
	
	public boolean friendly;
	public int health;
	
	public Character(float xPos, float yPos, float radius, int health) {
		super(xPos, yPos, radius);
		this.friendly = false;
		this.health = health;
	}	
	
	protected static float getX(float xPos) {
		return Math.min(ShooterGame.SCREEN_X, Math.max(0, xPos));
	}
	
	protected static float getY(float yPos) {
		return Math.min(ShooterGame.SCREEN_Y, Math.max(0, yPos));
	}
}
