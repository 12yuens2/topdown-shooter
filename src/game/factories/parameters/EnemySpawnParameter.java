package game.factories.parameters;

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
