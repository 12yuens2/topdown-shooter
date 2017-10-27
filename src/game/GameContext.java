package game;

import java.util.ArrayList;

import objs.characters.Character;
import objs.characters.PlayerCharacter;
import objs.particles.Explosion;
import objs.particles.Particle;
import objs.pickups.Pickup;

public class GameContext {

	public PlayerCharacter player;
	
	public ArrayList<Character> enemies;
	public ArrayList<Particle> particles;
	public ArrayList<Pickup> pickups;
	public ArrayList<Explosion> explosions;
	
	public GameContext() {
		player = new PlayerCharacter((float) Math.random() * ShooterGame.SCREEN_X, (float) Math.random() * ShooterGame.SCREEN_Y, 15, 3);
		
		enemies = new ArrayList<>();
		particles = new ArrayList<>();
		pickups = new ArrayList<>();
		explosions = new ArrayList<>();
		
//		enemies.add(new PatrolCharacter(500, 500, 15, 100f, 150f, player));
	}
}
