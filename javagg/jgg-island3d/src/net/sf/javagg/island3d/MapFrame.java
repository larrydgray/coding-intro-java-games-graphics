package net.sf.javagg.island3d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Contains and draws the map elevation data represented by colored squares.
 * 
 * @author Larry Gray
 * @version 1.1
 */

public class MapFrame extends JFrame {

	/**
	 * A bootstrap tester main method.
	 * 
	 * @param args[]
	 *            not used.
	 */

	public static void main(String args[]) {
		MapFrame mapFrame = new MapFrame();
		mapFrame.setSize(new Dimension(400, 400));
		mapFrame.validate();

	} // end main method

	/** For controlling the map elevation data generator. */

	private MapControlPanel mapControlPanel = new MapControlPanel();

	/**
	 * Builds this ui for displaying the map elevation data.
	 */

	public MapFrame() {
//		this.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				System.exit(0);
//			}//windowClosing
//		});//windowAdapter addWindowListener
//
//		//ConsoleWindow.init();
//
//		this.getContentPane().add(mapControlPanel);
//		this.setVisible(true);
//		this.setSize(new Dimension(400, 400));
//		this.validate();

	} //end constructor MapFrame

	/**
	 * Gets the map data.
	 * 
	 * @returns int[][] the two dimensional array of elevation data.
	 */

	public int[][] getMap() {
		return (mapControlPanel.getMapPanel()).getMap();
	} // end method getMap

	/**
	 * Gets the prefered size of this frame.
	 * 
	 * @return A Dimension.
	 */
	public Dimension getPreferedSize() {
		return new Dimension(400, 400);
	} // getPrefredSize
} // MapFrame class
