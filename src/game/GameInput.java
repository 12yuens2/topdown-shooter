package game;

import java.io.Serializable;

/**
 * Wrapper for all the input to the game.
 * @author sy35
 *
 */
public class GameInput implements Serializable {

	public boolean keyDown, mouseDown;
	
	public int mouseButton, keyCode;
	public float mouseX, mouseY;
	
	public GameInput(float mouseX, float mouseY, int mouseButton, boolean mouseDown, int keyCode, boolean keyDown) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.mouseButton = mouseButton;
		this.mouseDown = mouseDown;
		this.keyCode = keyCode;
		this.keyDown = keyDown;
	}
}
