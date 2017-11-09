package game.factories;

import java.util.concurrent.Callable;

import game.GameContext;
import objs.characters.enemies.Enemy;
import objs.characters.enemies.impl.PatrolEnemy;
import objs.pickups.Pickup;
import objs.pickups.impl.AmmoPickup;
import objs.pickups.impl.BombPickup;
import objs.pickups.impl.HealthPickup;
import objs.pickups.impl.PermanentBulletRadiusPickup;
import objs.pickups.impl.PermanentDamagePickup;
import objs.pickups.impl.PiercePickup;
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
		
		/* Adjust spawn rate of pickups and patrols with difficulty */
		this.spawnRate = Math.max(1, Pickup.SPAWN_RATE + (difficulty*3));
		this.patrolSpawnRate = Math.max(1, Pickup.PATROL_SPAWN_RATE - (difficulty/3));
		this.pickupLifespan = Math.max(Pickup.MIN_LIFESPAN, Pickup.LIFESPAN - (difficulty*3));
		
		setSpawnFunctions();
	}
	
	
	@Override
	public void setSpawnFunctions() {
		/* Each pickup has a different spawn rate, making some more common and others rare */
		spawnMap.put(this :: spawnHealthPickup, spawnRate/3);
		spawnMap.put(this :: spawnAmmoPickup, spawnRate/2);
		spawnMap.put(this :: spawnSpeedPickup, spawnRate);
		spawnMap.put(this :: spawnPiercePickup, spawnRate*2);
		spawnMap.put(this :: spawnPermanentDamagePickup, spawnRate*2);
		spawnMap.put(this :: spawnPermanentBulletRadiusPickup, spawnRate*3);
		spawnMap.put(this :: spawnBombPickup, spawnRate*5);
	}

	@Override
	public void spawnEntities() {
		Pickup p = spawnRandomEntity();
		
		if (p != null) {
			context.pickups.add(p);
			
			/* Spawn an enemy to patrol around this pickup */
			if (random.nextInt(patrolSpawnRate) == 0) {
				Enemy e = enemySpawnFactory.spawnPatrolEnemy(enemySpawnFactory.basicEnemySpawnParameter, p.position);
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

	private PermanentDamagePickup spawnPermanentDamagePickup() {
		return new PermanentDamagePickup(randomX(), randomY(), SIZE, pickupLifespan);
	}
	
	private PermanentBulletRadiusPickup spawnPermanentBulletRadiusPickup() {
		return new PermanentBulletRadiusPickup(randomX(), randomY(), SIZE, pickupLifespan);
	}
	
	private PiercePickup spawnPiercePickup() {
		return new PiercePickup(randomX(), randomY(), SIZE, pickupLifespan);
	}
	

}
