package game;

import processing.core.PApplet;

public class ShooterGame extends PApplet{
	
	public static int SCREEN_X = 1600;
	public static int SCREEN_Y = 900;
	
	float xPos = 100;
	float yPos = 100;
	
	float left, right, up, down;
	float speed = 8.0f;
	
	public GameController gameController;
	
	public void settings() {
		size(SCREEN_X, SCREEN_Y);
	}
	
	public void setup() {
	   gameController = new GameController(this);
	}

	
	public void draw() {
		gameController.step(mouseX, mouseY);
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
