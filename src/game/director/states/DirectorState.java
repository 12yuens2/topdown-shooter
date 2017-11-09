package game.director.states;

import game.GameContext;
import game.factories.EnemySpawnFactory;
import game.factories.PickupSpawnFactory;
import objs.characters.enemies.Enemy;
import objs.pickups.Pickup;

/**
 * Represents a state of the AI director.
 * The state controls the spawning rate of enemies.
 * @author sy35
 *
 */
public abstract class DirectorState {

	public static final float THRESHOLD = 40f;
	public static final float MAX_INTENSITY = THRESHOLD * 2;
	
	public int difficulty;
	public float intensity, threshold;
	
	public GameContext context;
	
	public EnemySpawnFactory enemySpawnFactory;
	public PickupSpawnFactory pickupSpawnFactory;
	
	public DirectorState(int difficulty, float intensity, GameContext context) {
		this.difficulty = difficulty;
		this.intensity = intensity;
		
		this.threshold = THRESHOLD;
		
		this.context = context;
		
		this.enemySpawnFactory = new EnemySpawnFactory(difficulty, context);
		this.pickupSpawnFactory = new PickupSpawnFactory(difficulty, context, enemySpawnFactory);
	}

	/**
	 * Update function for each state.
	 * An enemy spawning function should be called on each update step.
	 * The state should also check if it should transition to another state or stay in this state.
	 * @return - The next state.
	 */
	public abstract DirectorState update();
	
	
	/**
	 * Minimum threat spawning.
	 * Decreased enemy spawn rates and increased pickup spawn rates.
	 * No shooter enemies will spawn.
	 */
	protected void minThreatSpawn() {
		enemySpawnFactory.spawnRate =  (3 * Enemy.SPAWN_RATE) - (difficulty * 2);
		pickupSpawnFactory.spawnRate = (Pickup.SPAWN_RATE / 3) + (difficulty * 2);
		
		enemySpawnFactory.setEasySpawns();
		spawnEntities();
	}
	
	/**
	 * Maximum threat spawning.
	 * Increased enemy spawn rates and decreased pickup spawn rates.
	 */
	protected void maxThreatSpawn() {
		enemySpawnFactory.spawnRate = (Enemy.SPAWN_RATE/3) - (difficulty * 2);		
		pickupSpawnFactory.spawnRate = (3 * Pickup.SPAWN_RATE) + (difficulty * 2);
		
		/* Set easy spawns if intensity is too high */
		if (intensity > MAX_INTENSITY + (difficulty * 10)) enemySpawnFactory.setEasySpawns();
		
		spawnEntities();
	}
	
	private void spawnEntities() {
		enemySpawnFactory.spawnEntities();
		pickupSpawnFactory.spawnEntities();
	}

	/**
	 * Set the difficulty of the state's factories.
	 * @param difficulty
	 */
	public void setDifficulty(int difficulty) {
		enemySpawnFactory.setDifficulty(difficulty);
		pickupSpawnFactory.setDifficulty(difficulty);		
	}

}
