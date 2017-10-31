package game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import hypermedia.net.UDP;
import objs.characters.PlayerCharacter;
import processing.core.PApplet;

public class ShooterGame extends PApplet{
	
	public static int SCREEN_X = 1600;
	public static int SCREEN_Y = 900;
	
	public static final int PORT = 18238;
	
	public GameController gameController;
	
	public ByteArrayOutputStream bos;
	public ObjectOutput out;
	
	public UDP server;
	
	public void settings() {
		size(SCREEN_X, SCREEN_Y);
	}
	
	public void setup() {
	   gameController = new GameController(this);
	   
	   bos = new ByteArrayOutputStream();
	   
	   server = new UDP(this, PORT);
	   server.listen(true);
	}

	
	public void draw() {
		gameController.step(mouseX, mouseY);
		
		try {
			bos = new ByteArrayOutputStream();
			out = new ObjectOutputStream(bos);
			out.writeObject(gameController.context.players);
			out.flush();
			byte[] contextBytes = bos.toByteArray();
						
			server.send(contextBytes, "127.0.0.1", PORT + 1);
			
//			bos.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	void receive(byte[] data, String ip, int port) {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		
		try {
			ObjectInput in = new ObjectInputStream(bis);
			ArrayList<PlayerCharacter> players = (ArrayList<PlayerCharacter>) in.readObject();
			System.out.println(players.get(0).position.x + " " + players.get(1).position.y);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void mousePressed() {
		gameController.handleInput(mouseX, mouseY, mouseButton, 0, false);
	}
	
	public void keyPressed() {
		gameController.handleInput(mouseX, mouseY, 0, keyCode, true);
	}
	
	public void keyReleased() {
		gameController.handleInput(mouseX, mouseY, 0, keyCode, false);
	}
	
	public static void main(String[] args) {
		PApplet.main("game.ShooterGame");
	}
	
}
