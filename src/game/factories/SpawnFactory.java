package game.factories;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.Callable;

import game.GameContext;
import game.GameObject;
import game.ShooterGame;

/**
 * Factory pattern to create GameObjects. 
 * Implementations of this class should directly add spawned entities into the game context.
 * @author sy35
 *
 */
public abstract class SpawnFactory<T extends GameObject> {

	public int difficulty, spawnRate;
	
	protected GameContext context;
	protected Random random;
	
	public HashMap<Callable<T>, Integer> spawnMap;
	
	public SpawnFactory(int difficulty, GameContext context) {
		this.context = context;
		this.random = new Random();
		this.spawnMap = new HashMap<>();
		
		setDifficulty(difficulty);
		setSpawnFunctions();
	}
	
	/**
	 * Change the difficulty which changes parameters entities being spawned.
	 * @param difficulty - The new difficulty.
	 */
	public abstract void setDifficulty(int difficulty);
	
	/**
	 * Add all spawn functions into the spawnFunctions array list. 
	 */
	public abstract void setSpawnFunctions();
	
	
	/**
	 * Spawn all entities this factory is responsible for.
	 * Spawned entities should be directly added into the game context.
	 */
	public abstract void spawnEntities();
	
	
	/**
	 * Spawn a random entity with spawnRate using a random spawn function. 
	 * @return
	 */
	protected T spawnRandomEntity() {
		for (Entry<Callable<T>, Integer> entry : spawnMap.entrySet()) {
			if (random.nextInt(Math.max(1, entry.getValue())) == 0) {
				try {
					T t = entry.getKey().call();
					return t;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}

	
	/**
	 * Get a random value from 0 to ShooterGame.SCREEN_X.
	 * @return - The random value.
	 */
	protected int randomX() {
		return random.nextInt(ShooterGame.SCREEN_X);
	}
	
	/**
	 * Get a random value from 0 to ShooterGame.SCREEN_Y.
	 * @return - The random value.
	 */
	protected int randomY() {
		return random.nextInt(ShooterGame.SCREEN_Y);
	}
}
