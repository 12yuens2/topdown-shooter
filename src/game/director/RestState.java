package game.director;

import game.GameContext;

public class RestState extends DirectorState {

	public static final int LIFESPAN = 600;
	
	public int lifespan;
	
	public RestState(int difficulty, float intensity, GameContext context) {
		super(difficulty, intensity, context);
		this.lifespan = LIFESPAN;
	}

	@Override
	public DirectorState update() {
		minThreatSpawn();
		
		lifespan--;
		if (lifespan <= 0) {
			return new BuildupState(difficulty, intensity, context);
		}
		return this;
	}

}
