package net.sf.javagg.gsc;

import java.awt.Color;
import java.awt.Font;

/**
 * A screen needs to deal with text, and a good way is in rows and columns.
 * This will provide a time honored familiar way to throw text out on a
 * graphics screen. These methods will encourage quick reporting formatted or
 * not. It will also have some behavior reminicent of typewriting. But a text
 * screen needs to do one thing well which is to plot characters in coloumns
 * and rows in one forground color and with given background color per cell.
 * This is were we begin our first functional text screen, plotting characters
 * and cells.
 * 
 * @author Larry Gray
 * @version 1
 * 
 * @see TextPrinter
 * @see Screen
 * @see "TextScreenComponent(later)"
 */
public interface Text {
	/**
	 * Height of the basic font.
	 * 
	 * @author Larry Gray
	 * @version 1
	 *  
	 */
	public static final int CELL_HEIGHT = 10; // size of Courier 12 point
	/**
	 * Width of the basic font.
	 * 
	 * @author Larry Gray
	 * @version 1
	 * 
	 *  
	 */
	public static final int CELL_WIDTH = 8; // size of Courier 12 point
	/**
	 * A basic fixed width font to use.
	 * 
	 * @author Larry Gray
	 * @version 1
	 * 
	 *  
	 */
	public static final Font COURIER = new Font("Courier", Font.PLAIN, 12);
	/**
	 * Gets charactger cell height.
	 * 
	 * @author Larry Gray
	 * @version 1
	 * 
	 * 
	 * @return character cell height
	 */
	public abstract int getCellHeight();
	/**
	 * Gets the character cell width.
	 * 
	 * @author Larry Gray
	 * @version 1
	 * 
	 * 
	 * @return character cell width
	 */
	public abstract int getCellWidth();
	/**
	 * Gets the number of columns on this screen.
	 * 
	 * @author Larry Gray
	 * @version 1
	 * 
	 * 
	 * @param cols
	 */
	public abstract int getNumberColumns();
	/**
	 * Gets the number of Rows on this screen.
	 * 
	 * @author Larry Gray
	 * @version 1
	 * 
	 * 
	 * @param cols
	 */
	public abstract int getNumberRows();
	/**
	 * plots a solid square background under text, this is to make text more
	 * readable.
	 * 
	 * @author Larry Gray
	 * @version 1
	 * 
	 * 
	 * @param col
	 * @param row
	 */

	public abstract void plotCell(int col, int row);
	/**
	 * Plots the outline of the cell, just inside the outter edges of the cell.
	 * This will more or less be a debug method, but may have other uses.
	 * 
	 * @author Larry Gray
	 * @version 1
	 * 
	 * 
	 * @param col
	 * @param row
	 */
	public abstract void plotCellBoundery(int col, int row);
	/**
	 * plots a character to a col row position of a hypothetical mock text
	 * screen.
	 * 
	 * @author Larry Gray
	 * @version 1
	 * 
	 * 
	 * @param col
	 * @param row
	 * @param character
	 */
	public abstract void plotChar(int col, int row, char character);
	/**
	 * Sets the color for the background in a cell.
	 * 
	 * @author Larry Gray
	 * @version 1
	 * 
	 * 
	 * @param color
	 */
	public abstract void setBackgroundColor(Color color);
	/**
	 * This sets the height for characters in each cell of the screen.
	 * 
	 * @author Larry Gray
	 * @version 1
	 * 
	 * 
	 * @param height
	 */
	public abstract void setCellHeight(int height);
	/**
	 * Sets the cell width for characters on this screen.
	 * 
	 * @author Larry Gray
	 * @version 1
	 * 
	 * 
	 * @param width
	 */
	public abstract void setCellWidth(int width);
	/**
	 * Sets the color for text plotted in a cell.
	 * 
	 * @author Larry Gray
	 * @version 1
	 * 
	 * 
	 * 
	 * @param color
	 */
	public abstract void setForegroundColor(Color color);
    /**
     * 
     */
	public abstract void loadFont(String fontName);
} // Text Interface
