package game;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

public class DrawEngine {
    
	public PApplet parent;
	
    public DrawEngine(PApplet parent) {
    	this.parent = parent;
    }
    
//    /**
//     * Draw the main game with the background, ground and all game objects.
//     * @param context - contextual information of the game to draw
//     */
//    public void displayGame(GameContext context) {
//        parent.background(255);
//        drawGround();
//        
//        displayDrawables(context.meteors, 
//        		context.missiles, 
//        		context.explosions,
//        		context.bombers,
//        		context.bhms, 
//        		context.blackholes, 
//        		context.forcefields, 
//        		context.cities, 
//        		context.cannons);
//    }
    
    private void displayDrawables(ArrayList<? extends IDrawable>... drawables) {
        for (ArrayList<? extends IDrawable> drawList : drawables) {
        	for (IDrawable drawable : drawList) {
        		drawable.display(this);
        	}
        }
    }
    
//    private void drawGround() {
//        parent.fill(128);
//        parent.beginShape();
//        parent.vertex(0, GameConfig.GROUND_HEIGHT);
//        parent.vertex(GameConfig.SCREEN_X, GameConfig.GROUND_HEIGHT);
//        parent.vertex(GameConfig.SCREEN_X, GameConfig.SCREEN_Y);
//        parent.vertex(0, GameConfig.SCREEN_Y);
//        parent.endShape(PConstants.CLOSE);
//    }

    /**
     * Generic draw text function for other classes to draw text to the screen
     * @param textSize - size of the text
     * @param text - text to be drawn
     * @param posX - x position of the text
     * @param posY - y position of the text
     * @param col - colour of the text
     */
	public void drawText(int textSize, String text, int posX, int posY, int col) {
		PFont font = parent.createFont("Arial", textSize, true);
		
		parent.textFont(font, textSize);
		parent.fill(col);
		parent.text(text, posX, posY);
		parent.textAlign(PConstants.CENTER, PConstants.CENTER);
	}
	
	/**
	 * Draw an ellipse based on given parameters.
	 * @param col - colour of the circle
	 * @param xPos - x coordinate of the circle 
	 * @param yPos - y coordinate of the circle
	 * @param width - width of the circle
	 * @param height - height of the circle 
	 * @param i 
	 */
	public void drawEllipse(int col, float xPos, float yPos, float width, float height) {
		parent.ellipseMode(PConstants.CENTER);
		parent.fill(col);
		parent.ellipse(xPos, yPos, width, height);
	}
	
	
	/**
	 * Draw a rectangle based on given parameters.
	 * @param col - colour of the rectangle
	 * @param xPos - x coordinate of the circle
	 * @param yPos - y coordinate of the circle
	 * @param width - width of the rectangle
	 * @param height - height of the rectangle
	 */
	public void drawRectangle(int col, float xPos, float yPos, float width, float height) {
		parent.rectMode(PConstants.CENTER);
		parent.fill(col);
		parent.rect(xPos, yPos, width, height);
	}

	/**
	 * Draw information for the player. This includes:
	 * 	- score
	 * 	- number of missiles
	 * 	- number of blackholes
	 * 	- number of forcefields
	 * @param info - player information of the game
	 */
//	public void displayInfo(GameInfo info, Level level) {
//		drawText(16, "Level: " + level.levelNumber, 100, 25, 0);
//		drawText(16, "Score: " + info.score, 100, 50, 0);
//		drawText(16, "Missiles: " + info.missilesLeft, 100, 75, 0);
//		drawText(16, "Blackholes: " + info.blackholesLeft, 600, 50, 0);
//		drawText(16, "Forcefields: " + info.forcefieldsLeft, 600, 75, 0);
//		
//	}




    
}