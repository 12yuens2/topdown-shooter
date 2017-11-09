package game.director;

import java.util.Random;

import game.GameContext;
import game.director.states.DirectorState;
import game.director.states.impl.BuildupState;
import objs.characters.PlayerCharacter;

/**
 * AI director of the game who controls the difficulty and pacing.
 * @author sy35
 *
 */
public class Director {

	public static final int SAMPLE_INTERVAL = 600;
	
	public int difficulty, timer, scoreIncrease;
	public float intensity;
	
	public GameContext context;
	public Random random;
	public DirectorState state;
	
	public Director(GameContext context) {
		this.timer = 0;
		this.difficulty = 0;
		this.scoreIncrease = 0;
		this.intensity = 0f;
		
		this.context = context;
		this.random = new Random();
		this.state = new BuildupState(difficulty, intensity, context);
	}
	
	public void step() {
		/* Lower intensity over time */
		intensity *= 0.995f;
		
		state.intensity = intensity;
		state = state.update();
		
		/* After every random interval, sample and increase or decrease difficulty */
		if (++timer > random.nextInt(SAMPLE_INTERVAL) + SAMPLE_INTERVAL) {
			updateDifficulty();
			state.setDifficulty(difficulty);
			timer = 0;
		}
		
//		System.out.println("inenstiy: " + intensity + " difficulty: " + difficulty + " -> " + state);
	}
	
	/**
	 * Increase the intensity proportional to the damage taken.
	 * @param damageTaken
	 */
	public void increaseIntensityOnDamage(float damageTaken) {
		intensity += 3 * damageTaken;
	}
	
	/**
	 * Increase the intensity proportional the score of a killed enemy.
	 * @param score
	 */
	public void increaseIntensityOnKill(float score) {
		scoreIncrease += score;
		intensity += score;
	}
	
	/**
	 * Update the difficulty of the game.
	 * By default the difficulty always increases, making the game progressively harder.
	 */
	private void updateDifficulty() {
		difficulty++;
		
		if (context.enemies.size() < 10) difficulty++;
		
		/* Adjust difficulty based on how much score was gained since last difficulty update */
		System.out.println("increase: " + scoreIncrease + " diff: " + difficulty);
//		if (scoreIncrease > 6 * difficulty) difficulty++;
//		if (scoreIncrease < 3 * difficulty) difficulty--;
//		scoreIncrease = 0;
		
		/* Adjust difficulty based on player health */
		for (PlayerCharacter player : context.players) {
			if (player.health < PlayerCharacter.HEALTH/2) difficulty--;
			if (player.health > PlayerCharacter.HEALTH) difficulty += 1 + (player.health / (PlayerCharacter.HEALTH));
		}
	}
	
}
