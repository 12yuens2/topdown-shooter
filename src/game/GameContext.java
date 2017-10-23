package game;

import java.util.ArrayList;


import characters.PlayerCharacter;
import particles.Particle;

public class GameContext {

	public PlayerCharacter player;
	
	public ArrayList<Character> enemies;
	public ArrayList<Particle> particles;
	
	public GameContext() {
		player = new PlayerCharacter(ShooterGame.SCREEN_X, ShooterGame.SCREEN_Y);
		
		enemies = new ArrayList<>();
		particles = new ArrayList<>();
	}
}
