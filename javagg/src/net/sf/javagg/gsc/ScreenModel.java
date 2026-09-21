package net.sf.javagg.gsc;

import java.awt.Color;
import java.util.Arrays;

import sourceforge.jgg.gsc.debug.ScreenBuffer;
/**
 * 
 */
public class ScreenModel {
	/** */
	public ImageStrip font;
	private Color defaultForeColor = Color.BLACK;
	private Color defaultBackColor = Color.WHITE;
	private char defaultCharacter = ' ';
	protected int rows;
	protected int cols;
	private CharacterCell[] defaultRow;
	protected ScreenBuffer buffer;

	public void setBuffer(ScreenBuffer buffer) {
		this.buffer = buffer;
	}

	/** */
	private CharacterCell[][] screen;

	public int getRows() {
		return this.rows;
	}

	public int getCols() {
		return this.cols;
	}

	public ScreenModel(int cols, int rows) {
		this.buffer = new ScreenBuffer(cols,rows);
		this.screen = new CharacterCell[rows][cols];
		this.rows = rows;
		this.cols = cols;

		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {

				this.setChar(this.defaultCharacter, x, y);

			}
		}
		this.defaultRow = new CharacterCell[cols];
		Arrays.fill(this.defaultRow, new CharacterCell(this.defaultCharacter,this.defaultForeColor,this.defaultBackColor));
		
	}

	private int viewPos;

	public void scrollDown() {
		if (viewPos < buffer.size() - rows)
			this.screen = this.buffer.getScreen(this.viewPos++);

	}
    public void updateScreenModel(int viewPos){
    	this.viewPos=viewPos;
    	this.screen=buffer.getScreen(viewPos);
    }
	public void scrollUp() {
		if (viewPos >= 0)
			this.screen = this.buffer.getScreen(this.viewPos--);
		else this.screen = this.buffer.getScreen(0);
	}

	public void setChar(char aChar, int x, int y)
			throws RuntimeException {
		if (x > getCols())
			throw new RuntimeException(
					"x is greater than number of columns in ScreenModel setChar(char,x,y)");
		if (x < 0)
			throw new RuntimeException(
					"x can not be less than 0 in ScreenModel setChar(char,x,y)");
		if (y > getRows())
			throw new RuntimeException(
					"y is greater than number of rows in ScreenModel setChar(char,x,y)");
		if (y < 0)
			throw new RuntimeException(
					"x can not be less than 0 in ScreenModel setChar(char,x,y)");
		this.screen[y][x] = new CharacterCell(aChar,this.defaultForeColor,this.defaultBackColor);
	}

	public char getChar(int x, int y) throws RuntimeException {
		if(x<0||x>this.cols)throw new RuntimeException("X is out of bounds. cols="+this.cols+" x="+x);
		if(y<0||y>this.rows)throw new RuntimeException("Y is out of bounds. rows="+this.rows+" y="+y);
		return this.screen[y][x].getChar();
	}

	

}
