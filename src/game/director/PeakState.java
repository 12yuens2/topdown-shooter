package game.director;

import game.GameContext;

public class PeakState extends DirectorState {

	public static final int LIFESPAN = 200;
	
	public int lifespan;
	
	public PeakState(int difficulty, float intensity, GameContext context) {
		super(difficulty, intensity, context);
		this.lifespan = LIFESPAN;
	}

	@Override
	public DirectorState update() {
		maxThreatSpawn();
		
		lifespan--;
		if (lifespan <= 0) {
			return new FadeState(difficulty, intensity, context);
		}
		return this;
	}

}
