package game.factories;

import java.util.concurrent.Callable;

import game.GameContext;
import objs.characters.enemies.Enemy;
import objs.characters.enemies.impl.PatrolEnemy;
import objs.pickups.Pickup;
import objs.pickups.impl.AmmoPickup;
import objs.pickups.impl.BombPickup;
import objs.pickups.impl.HealthPickup;
import objs.pickups.impl.SpeedPickup;

/**
 * Factory class to spawn pickups.
 * @author sy35
 *
 */
public class PickupSpawnFactory extends SpawnFactory<Pickup> {

	public static final int SIZE = 10;
	
	public int pickupLifespan, patrolSpawnRate;
	public EnemySpawnFactory enemySpawnFactory;
	
	public PickupSpawnFactory(int difficulty, GameContext context, EnemySpawnFactory enemySpawnFactory) {
		super(difficulty, context);
		this.enemySpawnFactory = enemySpawnFactory;
	}

	@Override
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
		this.spawnRate = Pickup.SPAWN_RATE + (difficulty*3);
		this.patrolSpawnRate = Pickup.PATROL_SPAWN_RATE - (difficulty/2);
		this.pickupLifespan = Pickup.LIFESPAN - (difficulty*3);
	}
	

	@Override
	public void setSpawnFunctions() {
		spawnFunctions.add(() -> spawnAmmoPickup());
		spawnFunctions.add(() -> spawnSpeedPickup());
		spawnFunctions.add(() -> spawnHealthPickup());
		spawnFunctions.add(() -> spawnBombPickup());
	}

	@Override
	public void spawnEntities() {
		Pickup p = spawnRandomEntity();
		
		if (p != null) {
			context.pickups.add(p);
			
			/* Spawn an enemy to patrol around this pickup */
			if (random.nextInt(patrolSpawnRate) == 0) {
				Enemy e = enemySpawnFactory.spawnPatrolEnemy(p.position);
				if (e != null) context.enemies.add(e);
			}
		}
	}
	
	private AmmoPickup spawnAmmoPickup() {
		return new AmmoPickup(randomX(), randomY(),	SIZE, pickupLifespan);
	}
	
	private SpeedPickup spawnSpeedPickup() {
		return new SpeedPickup(randomX(), randomY(), SIZE, pickupLifespan);
	}
	
	private HealthPickup spawnHealthPickup() {
		return new HealthPickup(randomX(), randomY(), SIZE, pickupLifespan);
	}
	
	private BombPickup spawnBombPickup() {
		return new BombPickup(randomX(), randomY(), SIZE, pickupLifespan, context);
	}

	

}