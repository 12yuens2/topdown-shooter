package network;

public enum MessageType {
	/**
	 * Indicates the message being sent contains a 'PlayerCharacter' as data. 
	 */
	PLAYER, 
	
	/**
	 * Indicates the message being sent contains a 'GameContext' as data. 
	 */
	CONTEXT, 
	
	/**
	 * Indicates the message being sent contains a 'GameInput' as data.
	 * This message type also includes a PlayerCharacter associated with this message.
	 */
	INPUT
}
