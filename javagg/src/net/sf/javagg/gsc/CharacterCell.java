package net.sf.javagg.gsc;

import java.awt.Color;

/**
 * A char cell contains a char code, a back color and a fore color. It is a
 * given cell on a screen which contains rows and columns of cells.
 * 
 * @author Larry Gray(caverdude)
 * 
 */
public class CharacterCell {

	/** the char code for this cell */
	private char theChar;
	/** the background color for this cell */
	private Color backColor;
	/**
	 * the glphy color for this cell which is called a foreground color
	 */
	private Color foreColor;

	/**
	 * Gets the char code in this cell.
	 * 
	 * @return char code
	 */
	public char getChar() {
		return theChar;
	}

	/**
	 * Sets the char code in this cell.
	 * 
	 * @param theChar
	 */
	public void setChar(char theChar) {
		this.theChar = theChar;
	}

	/**
	 * Gets the background color of this cell.
	 * 
	 * @return background color
	 */
	public Color getBackColor() {
		return backColor;
	}

	/**
	 * Sets the background color for this cell.
	 * 
	 * @param backColor
	 */
	public void setBackColor(Color backColor) {
		this.backColor = backColor;
	}

	/**
	 * Gets the glyph color for this cell.
	 * 
	 * @return foreground color.
	 */
	public Color getForeColor() {
		return foreColor;
	}

	/**
	 * Sets the glyph color for this cell.
	 * 
	 * @param foreground
	 *            color
	 */
	public void setForeColor(Color foreColor) {
		this.foreColor = foreColor;
	}

	/**
	 * Makes a new Cell giving char code, glyph color and background color.
	 * 
	 * @param character
	 * @param foreColor
	 * @param backColor
	 */
	public CharacterCell(char character, Color foreColor, Color backColor) {
		this.theChar = character;
		this.foreColor = foreColor;
		this.backColor = backColor;
	}

	/**
	 * Returns the char in this cell as a String.
	 */
	public String toString() {
		return "" + this.theChar;
	}

} // class CharacterCell
