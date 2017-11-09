package game;

import java.io.Serializable;
import java.util.ArrayList;

import objs.characters.Character;
import objs.characters.PlayerCharacter;
import objs.characters.enemies.Enemy;
import objs.characters.enemies.impl.FlockEnemy;
import objs.particles.Explosion;
import objs.particles.Particle;
import objs.pickups.Pickup;

/**
 * Context of the game, containing all game objects.
 * @author sy35
 *
 */
public class GameContext implements Serializable {

	public ArrayList<PlayerCharacter> players;
	
	public ArrayList<Enemy> enemies;
	public ArrayList<FlockEnemy> flockEnemies;
	public ArrayList<Particle> particles;
	public ArrayList<Pickup> pickups;
	public ArrayList<Explosion> explosions;
	
	public int score;
	
	public GameContext() {
		this.score = 0;
		
		players = new ArrayList<>();
		enemies = new ArrayList<>();
		flockEnemies = new ArrayList<>();
		particles = new ArrayList<>();
		pickups = new ArrayList<>();
		explosions = new ArrayList<>();
	}

	public GameContext(PlayerCharacter player) {
		this();
		players.add(player);
	}
}
