package net.sf.javagg.bitmapedit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A simple color selection Component.
 * 
 * @author koala_man and Caverdude
 * @version 1.1
 */
public class BitmapPalette extends JComponent implements MouseListener {
	/**
	 * Colors of the pallete.
	 */
	Color[] colors;
	/** The editor that uses this pallete */
	BitmapEditor editor;
	/** orientation of the pallete to the editor */
	boolean left;
	/**
	 * number of rows and cols and width and height of the pallete selection
	 * area
	 */
	int rows, cols, w, h;
	/** selected color */
	int selected = 0;
    public void setSelectedColor(Color aColor){
    	colors[selected]=aColor;
    	repaint();
    }
	/** Create a BitmapPalette for the specified BitmapEditor. */
	public BitmapPalette(BitmapEditor ed) {
		colors = new Color[16];
		editor = ed;

		colors[0] = new Color(0, 0, 0);
		colors[1] = new Color(0xFF, 0xFF, 0xFF);
		colors[2] = new Color(0x80, 0x80, 0x80);
		colors[3] = new Color(0xC0, 0xC0, 0xC0);
		colors[4] = new Color(0x80, 0, 0);
		colors[5] = new Color(0xFF, 0, 0);
		colors[6] = new Color(0, 0x80, 0);
		colors[7] = new Color(0, 0xFF, 0);
		colors[8] = new Color(0, 0, 0x80);
		colors[9] = new Color(0, 0, 0xFF);
		colors[10] = new Color(0x80, 0, 0x80);
		colors[11] = new Color(0xFF, 0, 0xFF);
		colors[12] = new Color(0x80, 0x80, 0);
		colors[13] = new Color(0xFF, 0xFF, 0);
		colors[14] = new Color(0, 0x80, 0x80);
		colors[15] = new Color(0, 0xFF, 0xFF);
		addMouseListener(this);
	}

	/** gets the size of this pallete for the layout */
	public Dimension getPreferredSize() {
		return new Dimension(40, 200);
	}

	/** here we process the color selection */
	public void mouseClicked(MouseEvent e) {
		int c = (e.getY() / h) * cols + (e.getX() / w);
		if (e.getClickCount() == 2) {
			if (left) {
				if (e.getX() > getWidth() - 20)
					c = selected;
			} else {
				if (e.getY() > getHeight() - 20)
					c = selected;
			}
			if (c < colors.length)
				colors[c] = JColorChooser.showDialog(this,
						"Select color #" + c, colors[c]);
			repaint();
		}
		if (editor != null && c < colors.length) {
			editor.setColor(colors[c]);
			selected = c;
			repaint();
		}
	}

	/** Not used */
	public void mouseEntered(MouseEvent e) {
	}

	/** Not used */
	public void mouseExited(MouseEvent e) {
	}

	/** Not used */
	public void mousePressed(MouseEvent e) {
	}

	/** Not used */
	public void mouseReleased(MouseEvent e) {
	}

	/** Paint the pallete */
	public void paint(Graphics g) {
		final double flob = 1.6; // flobbyness
		// int rows, cols, h,w,x=0,y=0;
		// boolean left;
		int x = 0, y = 0;
		double ratio = (double) getHeight() / getWidth();
		left = ratio < 1;
		g.clearRect(0, 0, getWidth(), getHeight());
		if (left) {
			x = 20;
			ratio = (double) (getHeight() - y) / (getWidth() - x);
			rows = Math.max(1, (int) (4 * ratio + flob));
			cols = (int) Math.ceil((double) 16 / rows);
		} else {
			y = 20;
			ratio = (double) (getHeight() - y) / (getWidth() - x);
			cols = Math.max(1, (int) (4 / ratio + flob));
			rows = (int) Math.ceil((double) 16 / cols);
		} // else
		w = (getWidth() - x) / cols;
		h = (getHeight() - y) / rows;

		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++) {
				int c = i * cols + j;
				if (c < colors.length)
					g.setColor(colors[c]);
				else
					g.setColor(getBackground());
				g.fillRect(j * w, i * h, (j + 1) * w, (i + 1) * h);
			} // for
		g.setColor(colors[selected]);
		if (left) {
			g.fillRect(getWidth() - 20, 0, 20, getHeight());
			g.setColor(Color.white);
			g.drawRect(getWidth() - 19, 0, 19, getHeight() - 2);
		} else {
			g.fillRect(0, getHeight() - 20, getWidth(), 20);
			g.setColor(Color.white);
			g.drawRect(0, getHeight() - 19, getWidth() - 2, 19);

		} // else
	} // end paint method

} // BitmapPalette

