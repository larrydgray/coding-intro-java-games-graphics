package net.sf.javagg.gsc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;



/**
 * This will emulate a text screen in a very simple manner. It will plot a grid
 * of images (characters of a given font). This grid will be any number of
 * columns or rows.
 * 
 * 
 * 
 * @author Larry Gray(caverdude)
 * 
 */
/*
 * For plotting colored glyphs and backgrounds I will find the source where I
 * make one color transparent. For example to get a red font I would make black
 * transparent on the glyph, I would plot an 8x8 block in red then draw the font
 * image on top replacing red with white around the font. Next I would make
 * white transparent on the new font in the new color and draw that image on the
 * given background color. The Glyph class should handle this. I can get some of
 * this source from the rpg demo for working with transparent colors.
 */
public class TextScreenPanel extends JPanel {
	PrintScreenModel screenModel = new PrintScreenModel(new ImageStrip(),
			new ImageStrip(),50,30);

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 
     */
	public TextScreenPanel(int cols, int rows, int fontx, int fonty,
			String fontFileName) {
		screenModel.screen.cols = cols;
		screenModel.screen.rows = rows;
		screenModel.screen.tileSize = new Dimension(fontx, fonty);
		BufferedImage screenImage = new BufferedImage(cols * fontx, rows
				* fonty, BufferedImage.TYPE_INT_RGB);
		screenModel.screen.anImage = screenImage;
		screenModel.font.setFrameComponent(this);

		File f = new File(fontFileName);
		if (!f.exists()) {
			System.out.println(fontFileName + " does not exist!");
		}
		screenModel.font.loadImage(f);
		screenModel.glyphFactory = new GlyphFactory(screenModel.font);
		screenModel.screenModel = new PrintScreenModel(screenModel.screen.cols, screenModel.screen.rows);
		
		// screenModel.writeScreen();

		this.repaint();
		screenModel.buffer.initBufferViewer();

	} // constructor TextScreenPanel()

	
	
	
	/**
	 * 
	 * @param s
	 */
	public void print(String s) {
		screenModel.print(s);
		repaint();
	}

	public void newLine() {
		screenModel.newLine();
	}

	public void indent() {
		screenModel.indent();
	}

	public void tab() {
		screenModel.tab();
	}
    public void update(){
    	screenModel.updateScreenModel();
    	repaint();
    }
	/**
	 * 
	 */
	public void scrollUp() {
		screenModel.scrollUp();
		repaint();
	}

	/**
	 * 
	 */
	public void scrollDown() {
		screenModel.scrollDown();
		repaint();
	}

	
	/** */
	public void paintComponent(Graphics g) {
		for (int r = 0; r < screenModel.getRows(); r++) {
			for (int c = 0; c < screenModel.getCols(); c++) {
				char ch = screenModel.getChar(c, r);

				Image theImage = screenModel.glyphFactory.getGlyph(ch).getGlyphImage();
				screenModel.screen.putImage(theImage, c, r);
			}

		}

		g.drawImage(
				screenModel.screen.anImage.getScaledInstance(screenModel.screen.cols
						* screenModel.screen.tileSize.width * 2, screenModel.screen.rows
						* screenModel.screen.tileSize.height * 2, Image.SCALE_DEFAULT), 0,
				0, null);
	} // method paintComponent

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame aFrame = new JFrame("Text Screen Panel Test");
		JPanel scrollButtonPanel = new JPanel();
		JButton up = new JButton("Up");
		JButton down = new JButton("Down");

		scrollButtonPanel.setLayout(new GridLayout(2, 1));
		scrollButtonPanel.add(up);
		scrollButtonPanel.add(down);
		scrollButtonPanel.setPreferredSize(new Dimension(80, 100));
		JPanel combinedPanel = new JPanel();
		combinedPanel.setLayout(new BorderLayout());
		String fileName = "c:/java/workspace/jgg-mud/data/font.png";
		final TextScreenPanel textScreenPanel = new TextScreenPanel(40, 30, 8,
				8, fileName);
		textScreenPanel.setPreferredSize(new Dimension(500, 500));
		up.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				textScreenPanel.scrollUp();
			}
		});
		down.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				textScreenPanel.scrollDown();
			}
		});
		combinedPanel.add(textScreenPanel, BorderLayout.CENTER);
		combinedPanel.add(scrollButtonPanel, BorderLayout.EAST);
		aFrame.setSize(500, 500);
		aFrame.add(combinedPanel);
		aFrame.setVisible(true);

	} // main method

} // class TextScreenPanel
