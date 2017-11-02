package game;

import network.Message;
import network.UDPSocket;
import objs.characters.PlayerCharacter;
import processing.core.PApplet;

public abstract class ShooterGame extends PApplet {

	
	public static final int SCREEN_X = 1600;
	public static final int SCREEN_Y = 900;
	
	public static final String SERVER_IP = "224.0.0.1";
	public static final int PORT = 18238;
	
	public GameController gameController;
	public UDPSocket socket;
	
	
	public void settings() {
		size(SCREEN_X, SCREEN_Y);
	}
	
	public void setup() {
		gameController = new GameController(this, args[0]);
		
		socket = new UDPSocket(this, SERVER_IP, PORT);
		socket.listen(true);
	}
	
	public void draw() {
		gameController.step(mouseX, mouseY);
	}
	
	public void receive(byte[] data, String ip, int port) {
		Message message = socket.getMessageFromBytes(data);
		
		if (message != null) {
			switch (message.type){
				case PLAYER:
					handlePlayerMessage((PlayerCharacter) message.data);
					break;
					
				case INPUT:
					handleInputMessage((GameInput) message.data, message.player);
					break;
					
				case CONTEXT:
					handleContextMessage((GameContext) message.data);
			}
		}
	}
	
	protected abstract void handlePlayerMessage(PlayerCharacter player);
	
	protected abstract void handleInputMessage(GameInput input, PlayerCharacter player);
	
	protected abstract void handleContextMessage(GameContext context);
	
	
	public abstract void mousePressed();
	
	public abstract void keyPressed();
	
	public abstract void keyReleased();
	
	
	protected PlayerCharacter getPlayer(PlayerCharacter player) {
		int index = gameController.state.context.players.indexOf(player);
		
		return index > 0 ? gameController.state.context.players.get(index) : null;
	}
}
