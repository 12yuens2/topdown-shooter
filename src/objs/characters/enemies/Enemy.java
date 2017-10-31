package objs.characters.enemies;

import java.util.ArrayList;

import objs.characters.Character;
import objs.characters.PlayerCharacter;
import processing.core.PVector;

public abstract class Enemy extends Character {

	public ArrayList<PlayerCharacter> targets;
	
	public Enemy(float xPos, float yPos, float radius, int health, ArrayList<PlayerCharacter> targets) {
		super(xPos, yPos, radius, health);
		
		this.targets = targets;
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
