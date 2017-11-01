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

public class UDPSocket extends UDP {

	public UDPSocket(Object owner, int port) {
		super(owner, port);
	}
	
	public UDPSocket(Object owner, String ip, int port) {
		super(owner, port, ip);
	}
	
	public void sendClientMessage(MessageType messageType, Object o, PlayerCharacter player, String ip, int port) {
		Message message = new Message(messageType, o, player);
		sendObject(message, ip, port);
	}
	
	public void sendMessage(MessageType messageType, Object o, String ip, int port) {
		Message message = new Message(messageType, o);
		sendObject(message, ip, port);
	}
	
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
