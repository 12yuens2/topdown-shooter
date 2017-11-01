package network;

import java.io.Serializable;

import objs.characters.PlayerCharacter;

/**
 * Message to be sent and received for a networked game.
 * Used to identify the type of the message and contains different data depending on the message type.
 * @author sy35
 *
 */
public class Message implements Serializable {

	public MessageType type;
	public Object data;
	public PlayerCharacter player;
	
	public Message(MessageType type, Object data) {
		this.type = type;
		this.data = data;
	}
	
	public Message(MessageType type, Object data, PlayerCharacter player) {
		this(type, data);
		this.player = player;
	}
	
}
