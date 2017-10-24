package game;

import java.util.ArrayList;

import characters.Character;
import characters.PlayerCharacter;
import particles.Particle;

public class GameContext {

	public PlayerCharacter player;
	
	public ArrayList<Character> enemies;
	public ArrayList<Particle> particles;
	
	public GameContext() {
		player = new PlayerCharacter((float) Math.random() * ShooterGame.SCREEN_X, (float) Math.random() * ShooterGame.SCREEN_Y, 15);
		
		enemies = new ArrayList<>();
		particles = new ArrayList<>();
	}
}
