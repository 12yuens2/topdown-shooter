package game;

import java.awt.event.KeyEvent;

import game.states.impl.StartState;
import network.MessageType;
import objs.characters.PlayerCharacter;
import processing.core.PApplet;


public class ShooterClient extends ShooterGame {
	
	public void setup() {
		super.setup();
	}

	
	public void draw() {
		super.draw();
		
		/* Send mouse coordinates to server */
		sendInput(mouseButton, mousePressed, 0, false);
	}

	@Override
	protected void handlePlayerMessage(PlayerCharacter player) {
		gameController.state.context.players.add(player);		
	}

	@Override
	protected void handleInputMessage(GameInput input, PlayerCharacter player) {
		/* 
		 * No implementation needed.
		 * Client does not update its own game when input is received.
		 */
	}

	@Override
	protected void handleContextMessage(GameContext context) {
		gameController.state.context = context;
		gameController.state.ui.context = context;
		
		/* Update the game controller's player object */
		PlayerCharacter player = getPlayer(gameController.player);
		if (player != null) gameController.player = player;
		
		System.out.println(gameController.state.context.score);
	}

	@Override
	public void mousePressed() {
		sendInput(mouseButton, true, 0, false);
	}
	
	@Override
	public void mouseReleased() {
		sendInput(mouseButton, false, 0, false);
	}
	
	@Override
	public void keyPressed() {
		sendInput(0, false, keyCode, true);
	}
	
	@Override
	public void keyReleased() {
		sendInput(0, false, keyCode, false);
	}
	
	/**
	 * Helper function to send an input message to the server.
	 * @param mouseButton - Mousebutton that was pressed.
	 * @param keyCode - Code for active key.
	 * @param keyDown - Differentiate between a key press and key release.
	 */
	private void sendInput(int mouseButton, boolean mouseDown, int keyCode, boolean keyDown) {
		GameInput input = gameController.getInput(mouseX, mouseY, mouseButton, mouseDown, keyCode, keyDown);

		if (input.keyCode == KeyEvent.VK_ENTER && gameController.state instanceof StartState) {
			
			/* Send this new player to server */
			System.out.println(gameController.player);
			socket.sendMessage(MessageType.PLAYER, gameController.player, SERVER_IP, PORT);
		}
		
		gameController.handleInput(input, gameController.player);
		
		socket.sendClientMessage(MessageType.INPUT, input, gameController.player, SERVER_IP, PORT);
	}
	
	public static void main(String[] args) {
		PApplet.main("game.ShooterClient", args);
	}

}
