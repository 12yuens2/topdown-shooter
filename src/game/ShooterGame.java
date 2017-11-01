package game;

import processing.core.PApplet;

public class ShooterGame extends PApplet{
	
	public static int SCREEN_X = 1600;
	public static int SCREEN_Y = 900;
	
	public static final String SERVER_IP = "127.0.0.1";
	
	public static final int SERVER_PORT = 18238;
	public static final int CLIENT_PORT = SERVER_PORT + 1;
	
	public GameController gameController;
	
	public UDPSocket server;
	
	public void settings() {
		size(SCREEN_X, SCREEN_Y);
	}
	
	public void setup() {
	   gameController = new GameController(this);
	   
	   server = new UDPSocket(this, SERVER_IP, SERVER_PORT);
	   server.listen(true);
	}

	
	public void draw() {
		gameController.step(mouseX, mouseY);
		
		server.sendObject(gameController.context, SERVER_IP, CLIENT_PORT);		
	}
	
	public void receive(byte[] data, String ip, int port) {
		GameInput input = (GameInput) server.getObjectFromBytes(data);
		System.out.println(input.keyCode);
		if (input != null) {
			gameController.handleInput(input);
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
