package game;

import java.awt.event.KeyEvent;

import game.states.GameState;
import game.states.impl.GameOverState;
import game.states.impl.StartState;
import network.MessageType;
import objs.characters.PlayerCharacter;
import processing.core.PApplet;


public class ShooterClient extends ShooterGame {

	public boolean started;
	
	public void setup() {
		super.setup();
		
		started = false;
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
	protected void handleStateMessage(GameState state) {
		if (started) {
			gameController.state = state;
			
			/* Update the game controller's player object */
			PlayerCharacter player = getPlayer(gameController.player);
			if (player != null) gameController.player = player;
			else gameController.player.health = 0;
			
			if (gameController.state instanceof GameOverState) started = false;
		}
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


		/* Send this new player to server */
		if (input.keyCode == KeyEvent.VK_ENTER && !started && !(gameController.state instanceof GameOverState)) {
			socket.sendMessage(MessageType.PLAYER, gameController.player, SERVER_IP, PORT);
			started = true;
		}

		gameController.handleInput(input, gameController.player);

		
		
		socket.sendClientMessage(MessageType.INPUT, input, gameController.player, SERVER_IP, PORT);
	}
	
	public static void main(String[] args) {
		PApplet.main("game.ShooterClient", args);
	}

}
