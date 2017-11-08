package game;

import game.states.GameState;
import network.MessageType;
import objs.characters.PlayerCharacter;
import processing.core.PApplet;

public class ShooterServer extends ShooterGame {

	boolean lock;
	
	@Override
	public void draw() {
		super.draw();
		
		gameController.handleInput(mouseX, mouseY, mouseButton, mousePressed, 0, false);
		
		/* Multicast the new game context to all clients atomically. */
		synchronized(this) {
			socket.sendMessage(MessageType.STATE, gameController.state, SERVER_IP, PORT);
		}
	}

	@Override
	protected void handlePlayerMessage(PlayerCharacter player) {
		gameController.state.context.players.add(player);
	}

	@Override
	protected void handleInputMessage(GameInput input, PlayerCharacter player) {
		PlayerCharacter clientPlayer = getPlayer(player);
		
		synchronized(this) {
			gameController.handleInput(input, clientPlayer);
		}
	}

	@Override
	protected void handleStateMessage(GameState state) {
		/* 
		 * No implementation needed.
		 * Server does not update its own context even if it receives a context message.
		 */
	}
	
	@Override
	public void mousePressed() {
		gameController.handleInput(mouseX, mouseY, mouseButton, true, 0, false);
	}
	
	@Override
	public void mouseReleased() {
		gameController.handleInput(mouseX, mouseY, mouseButton, false, 0, false);
	}
	
	@Override
	public void keyPressed() {
		gameController.handleInput(mouseX, mouseY, 0, false, keyCode, true);
	}
	
	@Override
	public void keyReleased() {
		gameController.handleInput(mouseX, mouseY, 0, false, keyCode, false);
	}
	
	public static void main(String[] args) {
		PApplet.main("game.ShooterServer", args);
	}

	
}
