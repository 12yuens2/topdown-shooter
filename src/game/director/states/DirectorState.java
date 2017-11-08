package game.director.states;

import game.GameContext;
import game.factories.EnemySpawnFactory;
import game.factories.PickupSpawnFactory;
import objs.characters.enemies.Enemy;
import objs.pickups.Pickup;

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

	
	public abstract DirectorState update();
	
	
	
	protected void minThreatSpawn() {
		enemySpawnFactory.spawnRate =  (4 * Enemy.SPAWN_RATE) - (difficulty*3);
		pickupSpawnFactory.spawnRate = (Pickup.SPAWN_RATE / 4) + (difficulty * 3);
		
		enemySpawnFactory.setEasySpawns();
		spawnEntities();
	}
	
	protected void maxThreatSpawn() {
		enemySpawnFactory.spawnRate = (Enemy.SPAWN_RATE/3) - (difficulty*3);		
		pickupSpawnFactory.spawnRate = (3 * Pickup.SPAWN_RATE) + (difficulty * 3);
		
		if (intensity > MAX_INTENSITY) enemySpawnFactory.setEasySpawns();
		
		spawnEntities();
	}
	
	private void spawnEntities() {
		enemySpawnFactory.spawnEntities();
		pickupSpawnFactory.spawnEntities();
	}


	public void setDifficulty(int difficulty) {
		enemySpawnFactory.setDifficulty(difficulty);
		pickupSpawnFactory.setDifficulty(difficulty);
		
	}

}
