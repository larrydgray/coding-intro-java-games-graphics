package net.sf.javagg.island3d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * View of the 3d isometric surface, generates this view by skewing a graph and
 * moving points on the graph upwards.
 * 
 * @author Larry Gray
 * @version 1.1
 *  
 */
public class MeshSurfacePanel extends JPanel {

	/** 2nd off screen buffer. */
	private static BufferedImage bufferedImage;

	/** Default grid size. */
	private int gridSize = 10;

	/** Elevation data for surface. */
	MeshSurfaceModel meshSurfaceModel;

	/** Default number of squares wide and deep to display. */
	private int numberSquares = 5;

	/** Distance to skew bottom of grid to the right. */
	private int skew3D = 12;

	/** Starting X position. */
	private int startX = 20;

	/** Starting Y position */
	private int startY = 20;

	/**
	 * Builds a starting surface with 1000,1000 squares.
	 */
	public MeshSurfacePanel() {
		this.setSize(1000, 1000);
	} // MeshSurfacePanel no args constructor.

	/**
	 * Draws a single square on the surface.
	 * 
	 * @param g
	 *            a Graphics context.
	 * @param x
	 *            corner.
	 * @param y
	 *            corner.
	 * @param e1
	 *            top left
	 * @param e2
	 *            top right
	 * @param e3
	 *            bottom right
	 * @param e4
	 *            bottom left
	 */
	public void drawSquare(Graphics g, int x, int y, int e1, int e2, int e3,
			int e4) {

		int xPoints[] = { x + 0, x + 0 + gridSize, x + 0 + gridSize + skew3D,
				x + 0 + skew3D };

		int yPoints[] = { y + 0 - e1, y + 0 - e2, y + 0 + gridSize - e3,
				y + 0 + gridSize - e4 };

		g.setColor(meshSurfaceModel.getColor((e1 + e2 + e3 + e4) / 4));
		g.drawPolygon(xPoints, yPoints, 4);

	} // end method drawSquare

	/**
	 * Erases a single square.
	 * 
	 * @param g
	 *            graphics context.
	 * @param x
	 *            corner.
	 * @param y
	 *            corner.
	 * @param e1
	 *            top left.
	 * @param e2
	 *            top right.
	 * @param e3
	 *            bottom right.
	 * @param e4
	 *            bottom left.
	 */
	public void eraseSquare(Graphics g, int x, int y, int e1, int e2, int e3,
			int e4) {
		// add code here
		int[] xPoints = { x + 0, x + 0 + gridSize, x + 0 + gridSize + skew3D,
				x + 0 + skew3D };

		int[] yPoints = { y + 0 - e1, y + 0 - e2, y + 0 + gridSize - e3,
				y + 0 + gridSize - e4 };

		g.setColor(Color.lightGray);
		g.fillPolygon(xPoints, yPoints, 4);

	} // end method eraseSquare

	/**
	 * Fills a single square.
	 * 
	 * @param g
	 *            graphics context.
	 * @param x
	 *            corner.
	 * @param y
	 *            corner.
	 * @param e1
	 *            top left.
	 * @param e2
	 *            top right.
	 * @param e3
	 *            bottom right.
	 * @param e4
	 *            bottom left.
	 */
	public void fillSquare(Graphics g, int x, int y, int e1, int e2, int e3,
			int e4) {
		// put code here
		int a = 1;
	} // end method fillSquare

	/**
	 * gets the prefered size of this Panel.
	 * 
	 * @return Dimension size.
	 */
	public Dimension getPreferredSize() {
		return new Dimension(1000, 1000);
	} // getPreferedSize

	/**
	 * Draws the isometric 3d mesh surface using the Island 3d data.
	 * 
	 * @param Graphics
	 *            context for the JPanel.
	 */
	public void paintComponent(Graphics g) {

		bufferedImage = new BufferedImage(1000, 1000, 1);

		Graphics gg = bufferedImage.getGraphics();

		//meshSurfaceModel=new MeshSurfaceModel();

		for (int x = 1; x < numberSquares + 1; x++) {

			for (int y = numberSquares; y > 0; y--) {

				int e1 = meshSurfaceModel.getElevation(x, y);
				int e2 = meshSurfaceModel.getElevation(x + 1, y);
				int e3 = meshSurfaceModel.getElevation(x + 1, y + 1);
				int e4 = meshSurfaceModel.getElevation(x, y + 1);

				this.eraseSquare(gg, x * gridSize + y * skew3D + startX, y
						* gridSize + startY, e1, e2, e3, e4);
				this.drawSquare(gg, x * gridSize + y * skew3D + startY, y
						* gridSize + startY, e1, e2, e3, e4);

			} // end for y

		}// end for x

		g.drawImage(bufferedImage.getScaledInstance(1000, 1000,
				Image.SCALE_REPLICATE), 0, 0, null);

	} // end method paintComponent

	/**
	 * Sets the grid's size in number of squares.
	 * 
	 * @param int
	 *            a grid size.
	 */
	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	} // setGridSize

	/**
	 * Sets the mesh surface elevation data model.
	 */
	public void setMeshSurfaceModel(MeshSurfaceModel meshSurfaceModel) {
		this.meshSurfaceModel = meshSurfaceModel;
	} // setMeshSurfaceModel

	/**
	 * 
	 */
	public MeshSurfaceModel getMeshSurfaceModel(){
		return this.meshSurfaceModel;
	}
	
	/**
	 * Sets the number of squares for the display.
	 */
	public void setNumberSquares(int numberSquares) {
		this.numberSquares = numberSquares;
	} // setNumberSquares

	/**
	 * Sets the skew value for the 3d effect.
	 */
	public void setSkew3D(int skew3D) {
		this.skew3D = skew3D;
	} // setSkew3D

	/**
	 * Sets the Starting X position.
	 */
	public void setStartX(int startX) {
		this.startX = startX;
	} // setStartX

	/**
	 * Sets the starting Y position.
	 */
	public void setStartY(int startY) {
		this.startY = startY;
	} // setStartY

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
		MeshSurfacePanel.bufferedImage = bufferedImage;
	}

	/**
	 * @return Returns the gridSize.
	 */
	public int getGridSize() {
		return gridSize;
	}

	/**
	 * @return Returns the numberSquares.
	 */
	public int getNumberSquares() {
		return numberSquares;
	}

	/**
	 * @return Returns the skew3D.
	 */
	public int getSkew3D() {
		return skew3D;
	}

	/**
	 * @return Returns the startX.
	 */
	public int getStartX() {
		return startX;
	}

	/**
	 * @return Returns the startY.
	 */
	public int getStartY() {
		return startY;
	}

} // end class MeshSurfacePanel

