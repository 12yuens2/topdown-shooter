package game;

import java.io.Serializable;
import java.util.ArrayList;

import objs.characters.Character;
import objs.characters.FlockCharacter;
import objs.characters.PlayerCharacter;
import objs.particles.Explosion;
import objs.particles.Particle;
import objs.pickups.Pickup;

/**
 * 
 * @author sy35
 *
 */
public class GameContext implements Serializable {

	public ArrayList<PlayerCharacter> players;
	
	public ArrayList<Character> enemies;
	public ArrayList<FlockCharacter> flockEnemies;
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
		
//		players.add(new PlayerCharacter(
//				(float) Math.random() * ShooterGame.SCREEN_X, 
//				(float) Math.random() * ShooterGame.SCREEN_Y, 
//				15, 100, false));
//		
//		players.add(new PlayerCharacter(
//				(float) Math.random() * ShooterGame.SCREEN_X, 
//				(float) Math.random() * ShooterGame.SCREEN_Y, 
//				15, 100, true));
//		
//		enemies.add(new PatrolCharacter(500, 500, 15, 100f, 150f, player));
	}

	public GameContext(PlayerCharacter player) {
		this();
		
		players.add(player);
	}
}
