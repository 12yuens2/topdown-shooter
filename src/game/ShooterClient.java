package game;

import game.states.impl.PlayingState;
import network.Message;
import network.MessageType;
import network.UDPSocket;
import objs.characters.PlayerCharacter;
import processing.core.PApplet;


public class ShooterClient extends PApplet {
	public static int SCREEN_X = 1600;
	public static int SCREEN_Y = 900;
	
	public static final int PORT = 18238;
	
	public GameController gameController;
	
	public UDPSocket client;
	
	
	public void settings() {
		size(SCREEN_X, SCREEN_Y);
	}
	
	public void setup() {
	   gameController = new GameController(this, "Client");
	   	  
	   client = new UDPSocket(this, ShooterGame.SERVER_IP, ShooterGame.SERVER_PORT);
	   client.listen(true);
	   
	   client.sendMessage(MessageType.PLAYER, gameController.player, ShooterGame.SERVER_IP, ShooterGame.SERVER_PORT);
	}

	
	public void draw() {
		gameController.step(mouseX, mouseY);
		sendInput(0, 0, false);
	}

	public void receive(byte[] data, String ip, int port) {
		Message message = client.getMessageFromBytes(data);
		
		if (message != null) {
			switch (message.type) {
				case PLAYER:
					gameController.state.context.players.add((PlayerCharacter) message.data);
					break;

				case CONTEXT:
					gameController.state = new PlayingState((GameContext) message.data, gameController.drawEngine);
					break;
					
				default:
					break;
			}
		}
	}

	public void mousePressed() {
		sendInput(mouseButton, 0, false);
	}
	
	public void keyPressed() {
		sendInput(0, keyCode, true);
	}
	
	public void keyReleased() {
		sendInput(0, keyCode, false);
	}
	
	private void sendInput(int mouseButton, int keyCode, boolean keyDown) {
		GameInput input = gameController.getInput(mouseX, mouseY, mouseButton, keyCode, keyDown);
		
		client.sendClientMessage(MessageType.INPUT, input, gameController.player, ShooterGame.SERVER_IP, ShooterGame.SERVER_PORT);
	}
	
	public static void main(String[] args) {
		PApplet.main("game.ShooterClient");
	}
}
