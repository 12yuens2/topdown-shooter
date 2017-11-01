package game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import hypermedia.net.UDP;

public class UDPSocket extends UDP {

	public UDPSocket(Object owner, int port) {
		super(owner, port);
	}
	
	public UDPSocket(Object owner, String ip, int port) {
		super(owner, port, ip);
	}
	
	public void sendObject(Object o, String ip, int port) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(o);
			out.flush();
			
			byte[] objectBytes = bos.toByteArray();
			
			send(objectBytes, ip, port);
						
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
	
	public Object getObjectFromBytes(byte[] data) {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		ObjectInput in = null;
		
		try {
			in = new ObjectInputStream(bis);
			return in.readObject();
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


}
