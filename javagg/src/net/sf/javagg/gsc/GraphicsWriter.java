/*
 * Created on May 3, 2004
 *  
 */
package net.sf.javagg.gsc;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Shape;

/**
 * An interface that defines behavior needed for writing text to a graphics
 * screen/panel. This will mimic typewriter in behavior a very familiar
 * interface for most people. This Interface will be almost identical to the
 * {@link TextPrinter}interface except for the fact that an implementation is
 * not bound to columns and rows and fixed width fonts. Future implementations
 * of this will lead to ReportWriterComponents. The methods of this interface
 * are meant to be simple and practical as an easy way to write text to
 * graphics.
 * <p>
 * This varies from the <code>TextPrinter</code>
 * object in that this does not print single
 * characters, but Strings only. This also uses X position, instead of column.
 * When using fonts of varying widths using columns does not make sense. We
 * still try to calculate rows based on a base position and font height.
 * <b>Note: This Enhancer is dependent on the Imagry Enhancer</b>
 * 
 * @author (Larry Gray)
 * @version 1.6
 * 
 * @see Graphics
 * @see TextPrinter
 * @see Imagry
 *  
 */
public interface GraphicsWriter{
	/** Bold */
	public static final int BOLD = Font.BOLD;
	/** Courier font */
	public static final String COURIER = "Courier";
	/** Italic */
	public static final int ITALIC = Font.ITALIC;
	/** MS Sans Serif font */
	public static final String MS_SANS_SERIF = "MS Sans Serif";
	/** MS Serif font */
	public static final String MS_SERIF = "MS Serif";
	/** Plain */
	public static final int PLAIN = Font.PLAIN;
	/** Small font */
	public static final String SMALL_FONTS = "Small Fonts";
	/** West English font */
	public static final String WST_ENGL = "WST_Engl";
	/**
	 * 
	 * Gets the cursor's x position.
	 * 
	 * @return cursor's x position
	 */
	int getCursorX();

	/**
	 * Gets the cursor's y position.
	 * 
	 * @return cursor's y position
	 */
	int getCursorY();
	/**
	 * Prints a shape object in the current cursor location. This is very convienent
	 * way to get some icons on your reports. The Shapes can be no taller than fonts
	 * or no longer than a line.
	 * 
	 * @param shape
	 */
	void printShape(Shape shape);
	/**
	 * Prints an image to the screen at the current cursor location, moving the cursor to
	 * the right by the width of the image printed. 
	 * 
	 * @param id a String representing some name for the image.
	 */
	
	
	void printImage(String id);
	/**
	 * Prints a string without a carriage return so that you may have multiple fonts and images
	 * on a single line. This method should ensure that the value of the cursorY will be
	 * below the descent of the lowest text or image. Also if text is enlarged, it must
	 * be lowered so that it will not overwrite text above it.
	 * 
	 * @param string
	 */
    void print(String string);
	/**
	 * Prints a set of lines, such as from a file, or another source. One
	 * string per line.
	 * 
	 * @param lines
	 */
	void printLines(String[] lines);

	/**
	 * prints a string at the current cursor postion followed by a carriage
	 * return.
	 * 
	 * @param s
	 */
	void println(String line);

	/**
	 * prints a string at a the last row and x position followed by a carriage
	 * return.
	 * 
	 * @param s
	 * @param row
	 * @param x
	 */
	void println(String line, int x, int y);

	/**
	 * sets the cursor position both x and row.
	 * 
	 * @param x
	 * @param row
	 */
	void setCursorPosition(int x, int row);
	/**
	 * Sets the current font for the text.
	 * 
	 * @param font
	 *            A System Font.
	 */
	void setScreenFont(Font font);

	/**
	 * sets the x position for the cursor
	 * 
	 * @param x
	 */
	void setX(int x);
	/**
	 * Sets the y position for the cursor.
	 * 
	 * @param y
	 */
	void setY(int y);
} // Interface GrahpicsWriter
