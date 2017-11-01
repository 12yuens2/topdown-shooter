package network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import hypermedia.net.UDP;
import objs.characters.PlayerCharacter;

/**
 * Represents a UDP socket to send and receive data directly related to this game.
 * UDP is a class from the UDP Processing library at http://ubaa.net/shared/processing/udp/
 * @author sy35
 *
 */
public class UDPSocket extends UDP {

	public UDPSocket(Object owner, int port) {
		super(owner, port);
	}
	
	public UDPSocket(Object owner, String ip, int port) {
		super(owner, port, ip);
	}
	
	/**
	 * Send a message to the given IP address. This function requires an associated player who is sending this message.
	 * @param messageType - Type of the message being sent.
	 * @param data - Object to be sent.
	 * @param player - Player who sent this message.
	 * @param ip - Destination IP address.
	 * @param port - Destination port.
	 */
	public void sendClientMessage(MessageType messageType, Object data, PlayerCharacter player, String ip, int port) {
		Message message = new Message(messageType, data, player);
		sendObject(message, ip, port);
	}
	
	
	/**
	 * Send a message to the given IP address without specifying who sent it.
	 * @param messageType - Type of the message being sent.
	 * @param data - Object to be sent.
	 * @param ip - Destination IP address.
	 * @param port - Destination port.
	 */
	public void sendMessage(MessageType messageType, Object data, String ip, int port) {
		Message message = new Message(messageType, data);
		sendObject(message, ip, port);
	}
	
	/**
	 * Process received bytes into a Message.
	 * @param data - Received data bytes.
	 * @return Received Message.
	 */
	public Message getMessageFromBytes(byte[] data) {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		ObjectInput in = null;
		
		try {
			in = new ObjectInputStream(bis);
			return (Message) in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Write an object as an array of bytes and send it down this UDP socket.
	 * @param o - Object to be sent.
	 * @param ip - Destination IP address.
	 * @param port - Destination port.
	 */
	private void sendObject(Object o, String ip, int port) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(o);
			out.flush();
			
			byte[] objectBytes = bos.toByteArray();
			
			send(objectBytes);
						
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


}
