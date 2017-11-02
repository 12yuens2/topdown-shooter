package game;

import game.states.impl.PlayingState;
import network.Message;
import network.MessageType;
import network.UDPSocket;
import objs.characters.PlayerCharacter;
import processing.core.PApplet;


public class ShooterClient extends ShooterGame {
	
	public void setup() {
		super.setup();
		
		/* Send this new player to server */
		socket.sendMessage(MessageType.PLAYER, gameController.player, SERVER_IP, PORT);
	}

	
	public void draw() {
		super.draw();
		
		/* Send mouse coordinates to server */
		sendInput(0, 0, false);
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
		gameController.state = new PlayingState(context, gameController.drawEngine);
		PlayerCharacter player = getPlayer(gameController.player);
		
		if (player != null) gameController.player = player;
	}

	@Override
	public void mousePressed() {
		sendInput(mouseButton, 0, false);
	}
	
	@Override
	public void keyPressed() {
		sendInput(0, keyCode, true);
	}
	
	@Override
	public void keyReleased() {
		sendInput(0, keyCode, false);
	}
	
	private void sendInput(int mouseButton, int keyCode, boolean keyDown) {
		GameInput input = gameController.getInput(mouseX, mouseY, mouseButton, keyCode, keyDown);
		
		socket.sendClientMessage(MessageType.INPUT, input, gameController.player, SERVER_IP, PORT);
	}
	
	public static void main(String[] args) {
		PApplet.main("game.ShooterClient", args);
	}

}
