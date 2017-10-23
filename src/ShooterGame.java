import java.awt.event.KeyEvent;

import processing.core.PApplet;
import processing.core.PConstants;

public class ShooterGame extends PApplet{
	
	float xPos = 100;
	float yPos = 100;
	
	float left, right, up, down;
	float speed = 8.0f;
	
	public void settings() {
		size(600, 600);
	}
	
	public void setup() {
	   
	}

	
	public void draw() {
		background(0);
		
		xPos += (right - left) * speed;
		yPos += (down - up) * speed;
		
		ellipseMode(PConstants.CENTER);
		fill(255);
		ellipse(xPos, yPos, 50, 50);
	}


	public void mousePressed() {
		
	}
	
	public void keyPressed() {

		switch(keyCode) {
		case KeyEvent.VK_W:
			up = 1;
			break;
		case KeyEvent.VK_S:
			down = 1;
			break;
		case KeyEvent.VK_A:
			left = 1;
			break;
		case KeyEvent.VK_D:
			right = 1;
			break;
		}
	}
	
	public void keyReleased() {
		switch(keyCode) {
		case KeyEvent.VK_W:
			up = 0;
			break;
		case KeyEvent.VK_S:
			down = 0;
			break;
		case KeyEvent.VK_A:
			left = 0;
			break;
		case KeyEvent.VK_D:
			right = 0;
			break;
		}
	}
	
	public static void main(String[] args) {
		PApplet.main("ShooterGame");
	}
	
}
