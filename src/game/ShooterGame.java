package game;

import network.Message;
import network.MessageType;
import network.UDPSocket;
import objs.characters.PlayerCharacter;
import processing.core.PApplet;

public class ShooterGame extends PApplet{
	
	public static int SCREEN_X = 1600;
	public static int SCREEN_Y = 900;
	
	public static final String SERVER_IP = "224.0.0.1";
	
	public static final int SERVER_PORT = 18238;
//	public static final int CLIENT_PORT = SERVER_PORT + 1;
	
	public GameController gameController;
	
	public UDPSocket server;
	
	public void settings() {
		size(SCREEN_X, SCREEN_Y);
	}
	
	public void setup() {
	   gameController = new GameController(this, "Server");
	   
	   server = new UDPSocket(this, SERVER_IP, SERVER_PORT);
	   server.listen(true);
	}

	
	public void draw() {
		gameController.step(mouseX, mouseY);
		
		server.sendMessage(MessageType.CONTEXT, gameController.state.context, SERVER_IP, SERVER_PORT);		
	}
	
	public void receive(byte[] data, String ip, int port) {
		Message message = server.getMessageFromBytes(data);
		
		if (message != null) {
			switch (message.type) {
				case PLAYER:
					gameController.state.context.players.add((PlayerCharacter) message.data);
					break;
					
				case INPUT:
					gameController.handleInput((GameInput) message.data, gameController.state.context.players.get(gameController.state.context.players.indexOf(message.player)));
					break;
					
				case CONTEXT:
//					System.out.println("server got context");
					break;
			}
		}
	}


	public void mousePressed() {
		gameController.handleInput(mouseX, mouseY, mouseButton, 0, false);
	}
	
	public void keyPressed() {
		gameController.handleInput(mouseX, mouseY, 0, keyCode, true);
	}
	
	public void keyReleased() {
		gameController.handleInput(mouseX, mouseY, 0, keyCode, false);
	}
	
	public static void main(String[] args) {
		PApplet.main("game.ShooterGame");
	}
	
}
