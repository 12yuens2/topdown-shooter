package game.factories;

import game.GameContext;
import game.ShooterServer;
import game.factories.parameters.EnemySpawnParameter;
import objs.characters.enemies.Enemy;
import objs.characters.enemies.impl.AmbushEnemy;
import objs.characters.enemies.impl.BasicChaseEnemy;
import objs.characters.enemies.impl.CircleEnemy;
import objs.characters.enemies.impl.FlockEnemy;
import objs.characters.enemies.impl.PatrolEnemy;
import objs.characters.enemies.impl.ShootEnemy;
import processing.core.PVector;

/**
 * Factory class to spawn enemies.
 * @author sy35
 *
 */
public class EnemySpawnFactory extends SpawnFactory<Enemy> {

	public static final int ENEMY_RADIUS = 15;
	
	public int enemyHealth, enemyDamage, enemyScore;
	public EnemySpawnParameter basicEnemySpawnParameter, flockEnemySpawnParameter;
	
	public EnemySpawnFactory(int difficulty, GameContext context) {
		super(difficulty, context);
	}
	
	@Override
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
		
		/* Adjust spawn rate and enemy stats with difficulty */
		this.spawnRate = Math.max(1, Enemy.SPAWN_RATE - (difficulty*2));
		this.enemyHealth = Math.max(Enemy.BASE_HP, Enemy.BASE_HP + (difficulty*2));
		this.enemyDamage = (int) Math.max(1, Enemy.BASE_DMG + (difficulty/7f));
		this.enemyScore = (int) Math.max(Enemy.BASE_SCORE, Enemy.BASE_SCORE + (difficulty/2f));
		
		this.basicEnemySpawnParameter = new EnemySpawnParameter(ENEMY_RADIUS, enemyHealth, enemyDamage, enemyScore);
		
		/* Less damage and health on flocking enemies because of their large numbers */
		this.flockEnemySpawnParameter = new EnemySpawnParameter(5, enemyHealth/2, enemyDamage, Math.max(1, enemyScore/2));
		
		setSpawnFunctions();
	}
	
	@Override
	public void setSpawnFunctions() {
		setEasySpawns();
		
		/* Spawn increasingly more shooter enemies as difficulty increases */
		int spawn = spawnRate*(5 / Math.max(1, difficulty % 3));
		spawnMap.put(() -> spawnShootEnemy(basicEnemySpawnParameter), spawn);
	}

	@Override
	public void spawnEntities() {
		Enemy e = spawnRandomEntity();
		if (e != null) context.enemies.add(e);
		
		spawnFlockEnemy(flockEnemySpawnParameter);
	}

	/**
	 * Do not spawn any shooter enemies.
	 */
	public void setEasySpawns() {
		spawnMap.clear();
		
		spawnMap.put(() -> spawnChaseEnemy(basicEnemySpawnParameter), spawnRate/2);
		spawnMap.put(() -> spawnCircleEnemy(basicEnemySpawnParameter), spawnRate);
		spawnMap.put(() -> spawnAmbushEnemy(basicEnemySpawnParameter), spawnRate*2);
	}
	
	private BasicChaseEnemy spawnChaseEnemy(EnemySpawnParameter spawnParam) {
		return new BasicChaseEnemy(random.nextInt(ShooterServer.SCREEN_X), random.nextInt(ShooterServer.SCREEN_Y), 
				spawnParam, context.players);
	}
	
	private CircleEnemy spawnCircleEnemy(EnemySpawnParameter spawnParam) {
		return new CircleEnemy(random.nextInt(ShooterServer.SCREEN_X), random.nextInt(ShooterServer.SCREEN_Y), 
				spawnParam, context.players);
	}
	
	private AmbushEnemy spawnAmbushEnemy(EnemySpawnParameter spawnParam) {
		return new AmbushEnemy(random.nextInt(ShooterServer.SCREEN_X), random.nextInt(ShooterServer.SCREEN_Y), 
				spawnParam, context.players);
	}
	
	private ShootEnemy spawnShootEnemy(EnemySpawnParameter spawnParam) {
		return new ShootEnemy(random.nextInt(ShooterServer.SCREEN_X), random.nextInt(ShooterServer.SCREEN_Y), 
				spawnParam, context);
	}
	
	public PatrolEnemy spawnPatrolEnemy(EnemySpawnParameter spawnParam, PVector position) {
		return new PatrolEnemy(position.x, position.y, spawnParam, context.players);
	}

	private void spawnFlockEnemy(EnemySpawnParameter spawnParam) {
		if (random.nextInt(spawnRate/5) == 0) {
			FlockEnemy boid = new FlockEnemy(randomX(), randomY(), spawnParam, context.players);
			context.enemies.add(boid);
			context.flockEnemies.add(boid);
		}
	}

}
