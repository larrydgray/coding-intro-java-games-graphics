/*
 * Created on May 3, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package net.sf.javagg.gsc;

/**
 * This notes behavior reminicent of line printers and text screen printing. Implementations of the
 * Text and Screen interface will also implement this interface to provide familiar methods
 * related to the display of text on a grid. This interface deals with text at the 
 * character row and column level. This will be used to generate quick reports that
 * happen to look nice in columns and rows. This will be used in Game coding as a
 * text screen effect.
 *  
 * 
 * @author Larry Gray
 * @version 1
 * 
 * @see GraphicsWriter
 * @see Text
 * 
 */
public interface TextPrinter {
	/**
	 * Prints a set of lines, such as from a file, or another source.
	 * One string per line.
	 * 
	 * @author Larry Gray
	 * @version 1
	 *  
	 * @param lines
	 */
	public abstract void printLines(String[] lines);

	/**
	 * Gets the cursor's x position.
	 * 
	 *  @author Larry Gray
	 * @version 1
	 *
	 * 
	 * @return
	 */
	public abstract int getCursorX();

	/**
	 * Gets the cursor's y position.
	 * 
	 *  @author Larry Gray
	 * @version 1
	 *
	 * 
	 * @return
	 */
	public abstract int getCursorY();

	/**
	 * prints a string at the current cursor position with no carriage return.
	 * 
	 *  @author Larry Gray
	 * @version 1
	 *
	 * 
	 * @param s
	 */
	public abstract void print(String s);

	/**
	 * prints a string at a specific screen position with no carriage return.
	 * 
	 *  @author Larry Gray
	 * @version 1
	 *
	 * 
	 * @param s
	 * @param row
	 * @param col
	 */
	public abstract void print(String s, int row, int col);

	/**
	 * prints a character at the current cursor position.
	 * 
	 *  @author Larry Gray
	 * @version 1
	 *
	 * 
	 * @param character
	 */
	public abstract void printChar(char character);

	/**
	 * a carriage return
	 * 
	 *  @author Larry Gray
	 * @version 1
	 *
	 *  
	 */
	public abstract void println();

	/**
	 * prints a string at the current cursor postion followed by a carriage
	 * return.
	 * 
	 *  @author Larry Gray
	 * @version 1
	 *
	 * 
	 * @param s
	 */
	public abstract void println(String s);

	/**
	 * prints a string at a given x,y position followed by a carriage return.
	 * 
	 *  @author Larry Gray
	 * @version 1
	 *
	 * 
	 * @param s
	 * @param row
	 * @param col
	 */
	public abstract void println(String s, int row, int col);

	/**
	 * sets the col for the cursor
	 * 
	 *  @author Larry Gray
	 * @version 1
	 *
	 * 
	 * @param col
	 */
	public abstract void setCol(int col);

	/**
	 * sets the cursor position both col and row.
	 * 
	 *  @author Larry Gray
	 * @version 1
	 *
	 * 
	 * @param col
	 * @param row
	 */
	public abstract void setCursorPosition(int col, int row);

	/**
	 * sets the row for the cursor
	 * 
	 *  @author Larry Gray
	 * @version 1
	 *
	 * 
	 * @param row
	 */
	public abstract void setRow(int row);
} // TextPrinter Interface