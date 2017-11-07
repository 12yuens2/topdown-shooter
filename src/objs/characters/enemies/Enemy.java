package objs.characters.enemies;

import java.util.ArrayList;
import java.util.Random;

import game.factories.parameters.EnemySpawnParameter;
import objs.characters.Character;
import objs.characters.PlayerCharacter;
import objs.weapons.Weapon;
import processing.core.PVector;

public abstract class Enemy extends Character {

	public static final int BASE_HP = Weapon.BASE_DMG;
	public static final int BASE_DMG = 1;
	public static final int BASE_SCORE = 2;
	
	public static final int SPAWN_RATE = 300;
	
	public ArrayList<PlayerCharacter> targets;
	public Random random;
	
	public int damage, score;
	
	public Enemy(float xPos, float yPos, float radius, int health, int damage, int score, ArrayList<PlayerCharacter> targets) {
		super(xPos, yPos, radius, health);
		
		this.damage = damage;
		this.score = score;
		this.targets = targets;
		this.random = new Random();
	}
	
	public Enemy(float xPos, float yPos, EnemySpawnParameter spawnParam, ArrayList<PlayerCharacter> targets) {
		this(xPos, yPos, spawnParam.radius, spawnParam.health, spawnParam.damage, spawnParam.score, targets);
	}

	/**
	 * Get the closest target player to this enemy from the list of player targets.
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
	 * Get the position of the closest target to this enemy.
	 * @return Position of the closest target.
	 */
	protected PVector getClosestTargetPosition() {
		return getClosestTarget().position.copy();
	}

}
