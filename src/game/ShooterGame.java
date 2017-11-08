package game;

import game.states.GameState;
import network.Message;
import network.UDPSocket;
import objs.characters.PlayerCharacter;
import processing.core.PApplet;

public abstract class ShooterGame extends PApplet {
	
	public static final int SCREEN_X = 1600;
	public static final int SCREEN_Y = 900;
	
	public static final String SERVER_IP = "224.0.0.1";
	public static final int PORT = 18238;
	
	public GameController gameController;
	public UDPSocket socket;
	
	public void settings() {
		size(SCREEN_X, SCREEN_Y);
	}
	
	public void setup() {
		gameController = new GameController(this, args[0]);
		
		socket = new UDPSocket(this, SERVER_IP, PORT);
		socket.listen(true);
	}
	
	public void draw() {
		gameController.step(mouseX, mouseY);
	}
	
	/**
	 * Program receive handler used by the UDP library that must be implemented to handle datagram reception.
	 * The function will be called by the UDP object when a nonnull message is received.
	 * The type of the message will be parsed and the corresponding handlers for each type of message will be called.
	 * @param data - The received message as bytes.
	 * @param ip - IP address.
	 * @param port - Port number.
	 */
	public void receive(byte[] data, String ip, int port) {
		Message message = socket.getMessageFromBytes(data);
		
		if (message != null) {
			switch (message.type){
				case PLAYER:
					handlePlayerMessage((PlayerCharacter) message.data);
					break;
					
				case INPUT:
					handleInputMessage((GameInput) message.data, message.player);
					break;
					
				case STATE:
					handleStateMessage((GameState) message.data);
			}
		}
	}
	
	/**
	 * Handler for receiving a player message.
	 * @param player - PlayerCharacter object received in the message.
	 */
	protected abstract void handlePlayerMessage(PlayerCharacter player);
	
	/**
	 * Handler for receiving a game input message.
	 * @param input - GameInput object received in the message.
	 * @param player - Player corresponding to the received input. 
	 */
	protected abstract void handleInputMessage(GameInput input, PlayerCharacter player);
	
	/**
	 * Handler for receiving a game context message.
	 * @param data - GameContext object received in the message.
	 */
	protected abstract void handleStateMessage(GameState state);
	
	/**
	 * Processing handler for behaviour when a mouse button is pressed.
	 */
	public abstract void mousePressed();
	
	/**
	 * Processing handler for behaviour when a mouse button is released.
	 */
	public abstract void mouseReleased();
	
	/**
	 * Processing handler for behaviour when a key is pressed.
	 */
	public abstract void keyPressed();
	
	/**
	 * Processing handler for behaviour when a key is released.
	 */
	public abstract void keyReleased();
	
	
	/**
	 * Helper function to translate between a given PlayerCharacter to a PlayerCharacter in the game's list of players.
	 * @param player - Player to find in the game's list.
	 * @return - Player found in the game's list if the two players are the same. Null if the player was not found.
	 */
	protected PlayerCharacter getPlayer(PlayerCharacter player) {
		int index = gameController.state.context.players.indexOf(player);
		
		return index >= 0 ? gameController.state.context.players.get(index) : null;
	}
}
