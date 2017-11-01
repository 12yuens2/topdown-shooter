package game;

import java.io.Serializable;

public class GameInput implements Serializable {

	public boolean keyDown;
	
	public int mouseButton, keyCode;
	public float mouseX, mouseY;
	
	
	public GameInput(float mouseX, float mouseY, int mouseButton, int keyCode, boolean keyDown) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.mouseButton = mouseButton;
		this.keyCode = keyCode;
		this.keyDown = keyDown;
	}
}
