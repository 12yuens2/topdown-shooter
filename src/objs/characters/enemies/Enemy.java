package objs.characters.enemies;

import java.util.ArrayList;
import java.util.Random;

import objs.characters.Character;
import objs.characters.PlayerCharacter;
import processing.core.PVector;

public abstract class Enemy extends Character {

	public ArrayList<PlayerCharacter> targets;
	public Random random;
	
	public Enemy(float xPos, float yPos, float radius, int health, ArrayList<PlayerCharacter> targets) {
		super(xPos, yPos, radius, health);
		
		this.targets = targets;
		this.random = new Random();
	}
	
	/**
	 * Get the closest target player from the list of player targets.
	 * @return the closest player character.
	 */
	protected PlayerCharacter getClosestTarget() {
		PlayerCharacter targetPlayer = null;
		float closestDistance = Integer.MAX_VALUE;
		
		for (PlayerCharacter player : targets) {
			float distance = PVector.dist(position, player.position);
			if (targetPlayer == null || distance < closestDistance) {
				targetPlayer = player;
				closestDistance = distance;
			}
		}
		
		return targetPlayer;
	}
	
	/**
	 * 
	 * @return
	 */
	protected PVector getClosestTargetPosition() {
		return getClosestTarget().position.copy();
	}

}
