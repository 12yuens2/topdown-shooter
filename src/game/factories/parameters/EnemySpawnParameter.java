package game.factories.parameters;

/**
 * Wrapper class for all parameters needed to create an enemy.
 * @author sy35
 *
 */
public class EnemySpawnParameter extends Parameter {

	public int health, damage, score;
	public float radius;
	
	public EnemySpawnParameter(float radius, int health, int damage, int score) {
		this.radius = radius;
		this.health = health;
		this.damage = damage;
		this.score = score;
	}
}
