package net.sf.javagg.gsc;

import java.awt.Color;
import java.util.Arrays;

import sourceforge.jgg.gsc.debug.ScreenBuffer;

/**
 * A model for a screen that has methods for printing to it line by line.
 * 
 * @author Larry Gray (caverdude)
 * 
 */
public class PrintScreenModel extends ScreenModel {

	/**
	 * The Image containing rows and cols for this screen model.
	 */
	public ImageStrip screen;
	
	/** A print screen model. */
	public PrintScreenModel screenModel;
	
	/** A Glyph Factory */
	public GlyphFactory glyphFactory;

	/**
	 * Makes a print screen model using a given font, screen image, and given
	 * number of columns and rows on this screen.
	 * 
	 * @param font ImageStrip containing a set of glyphs.
	 * @param screen ImageStrip containing rows and cols of cells.
	 * @param cols Number of Columns in this screen
	 * @param rows Number of Rows in this screen
	 */
	public PrintScreenModel(ImageStrip font, ImageStrip screen, int cols,
			int rows) {
		super(cols, rows);
		this.font = font;
		this.screen = screen;

	} // constructor (font,screen,cols,rows)

	/**
	 * Returns a blank line for editing and adding to buffer.
	 * 
	 * @return blank line- char cell array.
	 */
	private CharacterCell[] getBlankLine() {
		CharacterCell[] blankLine = new CharacterCell[cols];
		Arrays.fill(blankLine, new CharacterCell(' ', Color.BLACK, Color.WHITE));
		return blankLine;
	} // getBlankLine

	/**
	 * Adds a line to the screen's buffer.
	 * 
	 * @param line
	 *            - char cell array
	 */
	private void addLine(CharacterCell[] line) {
		buffer.addLine(line);
		if (buffer.size() >= super.getRows()) {
			this.scrollDown();
		} // if
	} // addLIne

	/** last char position on the line being edited */
	private int lastChar = 0;

	///** first row (top of screen) of the buffer view */
	//private int bufferViewPosition;

	/**
	 * Makes a print screen model using only number of cols and number of rows.
	 * 
	 * @param cols Number of columns in this screen.
	 * @param rows Number of rows in this screen.
	 */
	public PrintScreenModel(int cols, int rows) {
		super(cols, rows);
		buffer = new ScreenBuffer(cols, rows);
		this.setBuffer(buffer);

	} // constructor(cols,rows)

	/**
	 * Prints a single character to the end of the last line in the buffer.
	 * 
	 * @param c char
	 */
	private void print(char c) {
		if (lastChar >= getCols()) {

			if (lastChar >= getCols()) {
				this.addLine(getBlankLine());
				lastChar = 0;
			} // if

			buffer.getLastRow()[lastChar] = new CharacterCell(c, Color.BLACK,
					Color.WHITE);
			lastChar++;
		} else {
			CharacterCell[] lastRow = buffer.getLastRow();
			lastRow[lastChar] = new CharacterCell(c, Color.BLACK, Color.WHITE);
			lastChar++;
		} // else

	} // method print(char)

	/**
	 * Prints a word, printing one
	 * char of the word at a time to the
	 * last line or edit line of the buffer. We
	 * print one word at a time because of line
	 * wrapping.
	 * 
	 * @param word Any string which was surrounded by
	 * whitespace.
	 */
	private void printWord(String word) {
		// now we get each character in the word to be printed one by one.
		char[] text = word.toCharArray();
		// print each character
		for (int i = 0; i < text.length; i++) {
			print(text[i]);
		} // for
		// append a space to separate words.
		print(' ');
	} // printWord

	/**
	 * Prints an entire string by dividing it into
	 * words which are then printed char by char into
	 * the edit line or last line in the buffer.
	 * 
	 * @param aString A string of any length, can be more than one row 
	 *  on the screen. 
	 */
	public void print(String aString) throws RuntimeException {
		// printing a string of words to the buffer which may wrap lines
		String[] words = aString.split(" ");
		// Take care of each word in the string. For line wrapping.
		for (int i = 0; i < words.length; i++) {

			int length = words[i].length(); // get number of words in this
											// string.
			// if this word is too long for the line then we add the line to the
			// buffer and start a new line. print the word to the new line
			// else we just print the word to the current line.
			if (lastChar + length > getCols()) {
				lastChar = 0;
				int buffersize = buffer.size();
				this.addLine(getBlankLine());
				int buffersize2 = buffer.size();
				if (buffersize + 1 != buffersize2)
					throw new RuntimeException(
							"Buffer size difference too large after addition of"
									+ "new Line. Difference is "
									+ (buffersize2 - buffersize));
			} // if
			printWord(words[i]);
		} // for
		viewPos = buffer.size() - getRows();
		updateScreenModel();
	} // print (String)
	
	/** view position of the first line or row
	* of buffering being viewed (at top of screen) */
	private int viewPos;

	/**
	 * Updates the screenModel based on new
	 * view position to reflect current
	 * view of the buffer.
	 */
	public void updateScreenModel() {
		screenModel.updateScreenModel(viewPos);
	} // updateScreenModel

	/**
	 * Adds the current edit line to the buffer then begins
	 * a new blank line for editing.
	 */
	public void newLine() {
		lastChar = 0;
		this.addLine(getBlankLine());
		viewPos = buffer.size() - getRows();
		updateScreenModel();
	} // newLine

	/**
	 * Adds a tabb of 5 spaces to the end of the buffer
	 * at current edit position.
	 */
	public void tab() {
		lastChar += 5;
	} // tab

	/**
	 * Does the same thing as tab but used to
	 * start lines of new paragraphs.
	 */
	public void indent() {
		tab();
	} // indent

} // class PrintScreenModel