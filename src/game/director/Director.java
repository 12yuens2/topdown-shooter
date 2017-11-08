package game.director;

import game.GameContext;
import game.director.states.DirectorState;
import game.director.states.impl.BuildupState;
import objs.characters.PlayerCharacter;

public class Director {

	public int difficulty, timer;
	public float intensity;
	
	public GameContext context;
		
	public DirectorState state;
	
	public Director(GameContext context) {
		this.timer = 0;
		this.difficulty = 0;
		this.intensity = 0f;
		
		this.context = context;
		
		this.state = new BuildupState(difficulty, intensity, context);
	}
	
	public void step() {
		intensity *= 0.995f;
		state.intensity = intensity;
		state = state.update();
		
		/* After every interval, sample and increase or decrease difficulty */
		if (++timer > 1000) {
			updateDifficulty();
			state.setDifficulty(difficulty);
			timer = 0;
		}
		
		System.out.println("inenstiy: " + intensity + " difficulty: " + difficulty + " -> " + state);
	}
	
	public void increaseIntensityOnDamage(float damageTaken) {
		intensity += 3 * damageTaken;
		
	}
	
	public void increaseIntensityOnKill(float score) {
		intensity += score;
	}
	
	private void updateDifficulty() {
		difficulty++;
		
		for (PlayerCharacter player : context.players) {
			if (player.health < PlayerCharacter.HEALTH/2) difficulty--;
			if (player.health > PlayerCharacter.HEALTH*2) difficulty++;
		}
	}
	
}
