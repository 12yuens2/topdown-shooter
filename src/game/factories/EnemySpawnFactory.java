package game.factories;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;

import game.GameContext;
import game.GameObject;
import game.ShooterServer;
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
	
	public EnemySpawnFactory(int difficulty, GameContext context) {
		super(difficulty, context);
	}
	
	@Override
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
		this.spawnRate = Enemy.SPAWN_RATE - (difficulty*3);
		
		this.enemyHealth = Enemy.BASE_HP + (difficulty);
		this.enemyDamage = Enemy.BASE_DMG + (difficulty/3);
		this.enemyScore = Enemy.BASE_SCORE + (difficulty/2);
	}
	
	@Override
	public void setSpawnFunctions() {
		spawnFunctions.add(() -> spawnChaseEnemy());
		spawnFunctions.add(() -> spawnCircleEnemy());
		spawnFunctions.add(() -> spawnAmbushEnemy());
		spawnFunctions.add(() -> spawnShootEnemy());
	}

	@Override
	public void spawnEntities() {
		Enemy e = spawnRandomEntity();
		if (e != null) context.enemies.add(e);
		
		spawnFlockEnemy();
	}

	
	
	private BasicChaseEnemy spawnChaseEnemy() {
		return new BasicChaseEnemy(random.nextInt(ShooterServer.SCREEN_X), random.nextInt(ShooterServer.SCREEN_Y), 
				ENEMY_RADIUS, enemyHealth, enemyDamage, enemyScore, context.players);
	}
	
	private CircleEnemy spawnCircleEnemy() {
		return new CircleEnemy(random.nextInt(ShooterServer.SCREEN_X), random.nextInt(ShooterServer.SCREEN_Y), 
				ENEMY_RADIUS, enemyHealth, enemyDamage, enemyScore, context.players);
	}
	
	private AmbushEnemy spawnAmbushEnemy() {
		return new AmbushEnemy(random.nextInt(ShooterServer.SCREEN_X), random.nextInt(ShooterServer.SCREEN_Y), 
				ENEMY_RADIUS, enemyHealth, enemyDamage, enemyScore, context.players);
	}
	
	private ShootEnemy spawnShootEnemy() {
		return new ShootEnemy(random.nextInt(ShooterServer.SCREEN_X), random.nextInt(ShooterServer.SCREEN_Y), 
				ENEMY_RADIUS, enemyHealth, enemyDamage, enemyScore, context);
	}
	
	public PatrolEnemy spawnPatrolEnemy(PVector position) {
		return new PatrolEnemy(position.x, position.y, 
				ENEMY_RADIUS, enemyHealth, enemyDamage, enemyScore, context.players);
	}

	private void spawnFlockEnemy() {
		if (random.nextInt(spawnRate/12) == 0) {
			FlockEnemy boid = new FlockEnemy(randomX(), randomY(), 5, enemyHealth, enemyDamage, enemyScore, context.players);
			context.enemies.add(boid);
			context.flockEnemies.add(boid);
		}
	}


	
	
}
