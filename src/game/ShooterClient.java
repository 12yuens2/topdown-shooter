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
import processing.net.Client;
import processing.net.Server;

public class ShooterClient extends PApplet {
	public static int SCREEN_X = 1600;
	public static int SCREEN_Y = 900;
	
	public static final int PORT = 18238;
	
	public GameController gameController;
	
	public ByteArrayInputStream bis;
	public ObjectInput in;
	
	public UDP client;
	
	
	public void settings() {
		size(SCREEN_X, SCREEN_Y);
	}
	
	public void setup() {
	   gameController = new GameController(this);
	   	  
	   client = new UDP(this, ShooterGame.PORT+1);
	   client.listen(true);
	   client.setReceiveHandler("receive");
//	   client.log(true);
	}

	
	public void draw() {
		gameController.step(mouseX, mouseY);
		
//		if (context != null) {
//			System.out.println(context.score);
//		}
	}

	public void receive(byte[] data, String ip, int port) {
		bis = new ByteArrayInputStream(data);
		
		try {
			in = new ObjectInputStream(bis);
			ArrayList<PlayerCharacter> players = (ArrayList<PlayerCharacter>) in.readObject();
			gameController.context.players = players;
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
		PApplet.main("game.ShooterClient");
	}
}
