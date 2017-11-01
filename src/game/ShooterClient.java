package game;

import game.states.impl.PlayingState;

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
	   gameController = new GameController(this);
	   	  
	   client = new UDPSocket(this, "127.0.0.1", ShooterGame.CLIENT_PORT);
	   client.listen(true);
	}

	
	public void draw() {
		gameController.step(mouseX, mouseY);
	}

	public void receive(byte[] data, String ip, int port) {
		GameContext context = (GameContext) client.getObjectFromBytes(data);
		gameController.state = new PlayingState(context, gameController.drawEngine);
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
		
		client.sendObject(input, ShooterGame.SERVER_IP, ShooterGame.SERVER_PORT);
	}
	
	public static void main(String[] args) {
		PApplet.main("game.ShooterClient");
	}
}
