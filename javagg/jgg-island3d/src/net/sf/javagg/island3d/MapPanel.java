package net.sf.javagg.island3d;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * View of the map elevation data. Shows colors for various elevations.
 * 
 * @author Larry Gray
 * @version 1.1
 */

public class MapPanel extends JPanel {

	/** A 2nd off screen buffer for the map image. */
	private static BufferedImage bufferedImage;

	/** A random map generator which makes a map reminicent of a volcanic island. */
	private RandomTurtle randomTurtle;

	/**
	 * Gets map elevation data from a RandomTurtle object.
	 * 
	 * @returns int[][] map elevation data.
	 */
	public int[][] getMap() {
		return RandomTurtle.getMap();
	} // getmap

	/**
	 * Gets prefered size for this JPanel.
	 * 
	 * @return Dimension size.
	 */
	public Dimension getPreferedSize() {
		return new Dimension(sizeX, sizeY);
	} // getPreferedSize
    private int sizeX=400;
    private int sizeY=400;
	
    private int startX=25;
    private int startY=25;
    private int startElev=25;
    /**
	 * Draws the map from map elevation data.
	 */
   
	public void paintComponent(Graphics g) {
		bufferedImage = new BufferedImage(sizeX, sizeY, 1);
		Graphics gg = bufferedImage.getGraphics();
		MapPoint startingPoint = new MapPoint(startX, startY, startElev);
		RandomTurtle.clearMap();
		this.randomTurtle = new RandomTurtle(startingPoint, gg);

		g.drawImage(bufferedImage.getScaledInstance(sizeX, sizeY,
				Image.SCALE_REPLICATE), 0, 0, null);

	} // method paintComponent

	/**
	 * @return Returns the bufferedImage.
	 */
	public static BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	/**
	 * @param bufferedImage The bufferedImage to set.
	 */
	public static void setBufferedImage(BufferedImage bufferedImage) {
		MapPanel.bufferedImage = bufferedImage;
	}

	/**
	 * @return Returns the randomTurtle.
	 */
	public RandomTurtle getRandomTurtle() {
		return randomTurtle;
	}

	/**
	 * @param randomTurtle The randomTurtle to set.
	 */
	public void setRandomTurtle(RandomTurtle randomTurtle) {
		this.randomTurtle = randomTurtle;
	}

	/**
	 * @return Returns the sizeX.
	 */
	public int getSizeX() {
		return sizeX;
	}

	/**
	 * @param sizeX The sizeX to set.
	 */
	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	/**
	 * @return Returns the sizeY.
	 */
	public int getSizeY() {
		return sizeY;
	}

	/**
	 * @param sizeY The sizeY to set.
	 */
	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	/**
	 * @return Returns the startElev.
	 */
	public int getStartElev() {
		return startElev;
	}

	/**
	 * @param startElev The startElev to set.
	 */
	public void setStartElev(int startElev) {
		this.startElev = startElev;
	}

	/**
	 * @return Returns the startX.
	 */
	public int getStartX() {
		return startX;
	}

	/**
	 * @param startX The startX to set.
	 */
	public void setStartX(int startX) {
		this.startX = startX;
	}

	/**
	 * @return Returns the startY.
	 */
	public int getStartY() {
		return startY;
	}

	/**
	 * @param startY The startY to set.
	 */
	public void setStartY(int startY) {
		this.startY = startY;
	}

} // class MapPanel
