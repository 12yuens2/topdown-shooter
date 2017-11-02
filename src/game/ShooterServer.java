package game;

import network.MessageType;
import objs.characters.PlayerCharacter;
import processing.core.PApplet;

public class ShooterServer extends ShooterGame {

	@Override
	public void draw() {
		super.draw();
		
		/* Multicast the new game context to all clients */
		socket.sendMessage(MessageType.CONTEXT, gameController.state.context, SERVER_IP, PORT);		
	}

	@Override
	protected void handlePlayerMessage(PlayerCharacter player) {
		gameController.state.context.players.add(player);		
	}

	@Override
	protected void handleInputMessage(GameInput input, PlayerCharacter player) {
		PlayerCharacter clientPlayer = getPlayer(player);
		
		gameController.handleInput(input, clientPlayer);		
	}

	@Override
	protected void handleContextMessage(GameContext context) {
		/* 
		 * No implementation needed.
		 * Server does not update its own context even if it receives a context message.
		 */
	}
	
	@Override
	public void mousePressed() {
		gameController.handleInput(mouseX, mouseY, mouseButton, 0, false);
	}
	
	@Override
	public void keyPressed() {
		gameController.handleInput(mouseX, mouseY, 0, keyCode, true);
	}
	
	@Override
	public void keyReleased() {
		gameController.handleInput(mouseX, mouseY, 0, keyCode, false);
	}
	
	public static void main(String[] args) {
		PApplet.main("game.ShooterServer", args);
	}

	
}
