package net.sf.javagg.imagetool;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


import net.sf.javagg.bitmapedit.BitmapEditor;
/**
 * Editor which displays the image strip as a
 * single row or grid of images.
 * Allows for editing of individual images from the strip.
 * Saves and loads image strips.
 *
 * @author Larry Gray(caverdude)
 *
 */
public class ImageStripEditor extends JFrame {
	/** */
	public BitmapEditor aBitmapEditor = null;

	/**
	 * This is used on two occasions. One is
	 * when a new image strip is begun.
	 * The other is when an image strip is loaded from
	 * storage. It resizes the frame, the scrollable image
	 * area, and the bitmap editor to match, then repaints.
	 * Uses pack() rather than a hand-computed frame size so the
	 * menu bar and the control rows above the image always have
	 * room, no matter how large or small the strip is.
	 * @param x tile width in pixels
	 * @param y tile height in pixels
	 * @param cols number of columns in the strip
	 * @param rows number of rows in the strip
	 */

	private void setupUI(int x, int y, int cols, int rows){
		Toolkit toolkit = Toolkit.getDefaultToolkit();

		// Get the current screen size
		Dimension scrnsize = toolkit.getScreenSize();
	    int screenWidth=scrnsize.width;
	    int screenHeight=scrnsize.height;
		aBitmapEditor.makeNew(x,y);
		// Leave room on screen for the menu bar and the control rows above
		// the image, and let the scroll pane show scrollbars instead of
		// growing the window past the screen for a large strip.
		int viewWidth = Math.min(cols * x, screenWidth - 100);
		int viewHeight = Math.min(rows * y, screenHeight - 150);
		imagePanel.setPreferredSize(new Dimension(cols * x, rows * y));
		scrollPane.setPreferredSize(new Dimension(viewWidth, viewHeight));
		pack();
		imagePanel.repaint();

	}

	/**
	 * Rebuilds the image strip using the current column/row count and
	 * tile size (the x, y, tileX, tileY fields), as a fresh blank strip,
	 * then resizes the UI to match. Shared by "New" and by the Options
	 * menu items, so changing either setting actually takes effect
	 * immediately instead of only updating the label text.
	 */
	private void rebuildStrip() {
		imageStrip.cols = x;
		imageStrip.rows = y;
		imageStrip.tileSize = new Dimension(tileX, tileY);
		BufferedImage aBufferedImage = new BufferedImage(tileX * x,
				tileY * y, BufferedImage.TYPE_INT_RGB);
		imageStrip.anImage = aBufferedImage;
		setupUI(imageStrip.tileSize.width, imageStrip.tileSize.height,
				imageStrip.cols, imageStrip.rows);
	}

    /**
     * Shows which image tile the mouse
     * is currently hovering over. If an
     * image is left clicked the image in the editor is transfered to
     * the tile in the image strip which was clicked. If an image is
     * right clicked then the image is loaded into the editor for
     * editing.
     *
     * @author Larry Gray (caverdude)
     *
     *
     */

	public class SelectionLabels extends JPanel {
		/** */
		private JLabel col = new JLabel("Column:");
		/** */
		private JLabel row = new JLabel("Row:");
		/** */
		private JLabel number = new JLabel("Number:");
        /**
         *
         * @param col
         * @param row
         * @param number
         */
		public void setPos(int col, int row, int number) {
			this.col.setText("Column:" + col);
			this.row.setText("Row:" + row);
			this.number.setText("Number:" + number);
		}
        /**
         *
         */
		public SelectionLabels() {
			this.setLayout(new FlowLayout());
			this.add(col);
			this.add(row);
			this.add(number);

		} // constructor SelectionLabels()
	} // inner class SelectionLabels

	/**
	 * This panel shows the size of the image strip in rows and columns. It
	 * also shows the tile size in pixels for image tiles.
	 *
	 * @author Larry Gray(caverdude)
	 *
	 */
	public class SizeLabels extends JPanel {
		/** */
		private JLabel tileXlabel = new JLabel("Tile X:");
		/** */
		private JLabel tileYlabel = new JLabel("Tile Y:");
		/** */
		private JLabel rows = new JLabel("Rows:");
		/** */
		private JLabel cols = new JLabel("Columns:");
        /**
         *
         * @param tileX
         * @param tileY
         * @param rows
         * @param cols
         */
		public void setSize(int tileX, int tileY, int rows, int cols) {
			this.tileXlabel.setText("Tile X:" + tileX);
			this.tileYlabel.setText("Tile Y:" + tileY);
			this.rows.setText("Rows:" + rows);
			this.cols.setText("Columns:" + cols);
		}
        /**
         *
         */
		public SizeLabels() {
			this.setLayout(new FlowLayout());
			this.add(tileXlabel);
			this.add(tileYlabel);
			this.add(rows);
			this.add(cols);

		} // constructor SizeLabels()
	} // inner class SizeLabels

    /**
     * Displays the image strip and an optional grid.
     *
     * @author Larry Gray(caverdude)
     *
     */
	public class ImagePanel extends JPanel {
		/** */
		private ImageStrip imageStrip;
        /**
         *
         * @param anImageStrip
         */
		public void setImageStrip(ImageStrip anImageStrip) {
			this.imageStrip = anImageStrip;
		}
        /** */
		public boolean showGrid = true;
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
        /**
         *
         */
		public void paintComponent(Graphics g) {

			int cols = imageStrip.cols;
			int rows = imageStrip.rows;
			int xSize = imageStrip.tileSize.width;
			int ySize = imageStrip.tileSize.height;
			g.clearRect(0, 0, cols * xSize, rows * ySize);
			g.drawImage(imageStrip.anImage, 0, 0, this);
			if (showGrid)
				for (int x = 0; x < cols; x++) {
					for (int y = 0; y < rows; y++) {
						g.drawRect(x * xSize, y * ySize, xSize, ySize);
					} // for
				} // for
		} // method paintComponent(Graphics)
	} // inner class ImagePanel
    /** */
	private ImagePanel imagePanel = new ImagePanel();
    /** */
	private JScrollPane scrollPane = new JScrollPane(imagePanel);
	/** */
	ImageStrip imageStrip = new ImageStrip();
	/** */
	SelectionLabels selectionLabels = new SelectionLabels();
	/** */
	SizeLabels sizeLabels = new SizeLabels();
	/** */
	private String defaultDir = "";
	/** Default number of columns (across) in a new image strip. */
	private int x = 5;
	/** Default number of rows (down) in a new image strip. */
	private int y = 3;
	/** Default tile width in pixels. */
	private int tileX = 50;
	/** Default tile height in pixels. */
	private int tileY = 50;

    /**
     * A very long constructor which we need to reduce in size somehow.
     * This mainly sets up the entire UI and contains controller source.
     *
     */
	public ImageStripEditor() {
		JMenu fileMenu = new JMenu("File");
		JMenuItem load = new JMenuItem("Load");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem newImageStrip = new JMenuItem("New");
		JMenuBar imageSelectorMenuBar = new JMenuBar();
		JMenu optionsMenu = new JMenu("Options");
		JMenuItem workspace = new JMenuItem("Workspace...");
		JMenuItem imageSize = new JMenuItem("Image Strip Size...");
		JMenuItem imageTileSize = new JMenuItem("Image Tile Size...");
		imageSelectorMenuBar.add(fileMenu);
		imageSelectorMenuBar.add(optionsMenu);
		fileMenu.add(workspace);
		fileMenu.add(newImageStrip);
		fileMenu.add(load);
		fileMenu.add(save);
		optionsMenu.add(imageSize);
		optionsMenu.add(imageTileSize);
		this.setJMenuBar(imageSelectorMenuBar);

		workspace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				imageStrip.defaultDir = JOptionPane.showInputDialog(null,
						"Working directory?");
			} // actionPerformed
		});// ActionListener, addActionListener
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				imageStrip.loadImage();
				setupUI(imageStrip.tileSize.width,imageStrip.tileSize.height,imageStrip.cols,imageStrip.rows);
				x=imageStrip.cols;
				y=imageStrip.rows;
				tileX=imageStrip.tileSize.width;
				tileY=imageStrip.tileSize.height;
				sizeLabels.setSize(tileX, tileY, x, y);

			} // actionPerformed
		});// ActionListener, addActionListener
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				imageStrip.saveImage();
			} // actionPerformed
		});// ActionListener, addActionListener
		imageTileSize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String sx = JOptionPane.showInputDialog(null,
						"Tile Horizontal Size?");
				String sy = JOptionPane.showInputDialog(null,
						"Tile Vertical Size?");
				try {
				    tileX = Integer.parseInt(sx);
					tileY = Integer.parseInt(sy);
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					return;
				} // catch
				sizeLabels.setSize(tileX, tileY, x, y);
				rebuildStrip();
			} // actionPerformed
		});// ActionListener, addActionListener
		newImageStrip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				rebuildStrip();
			} // actionPerformed
		});// ActionListener, addActionListener
		imageSize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String sx = JOptionPane.showInputDialog(null, "Columns?");
				String sy = JOptionPane.showInputDialog(null, "Rows?");
				try {
					x = Integer.parseInt(sx);
					y = Integer.parseInt(sy);
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					return;
				} // catch
				sizeLabels.setSize(tileX, tileY, x, y);
				rebuildStrip();
			} // actionPerformed
		}); // ActionListener, addActionListener

		((ImagePanel) imagePanel).setImageStrip(imageStrip);
		imageStrip.cols = x;
		imageStrip.rows = y;
		imageStrip.tileSize = new Dimension(tileX, tileY);
		BufferedImage aBufferedImage = new BufferedImage(tileX * x, tileY * y,
				BufferedImage.TYPE_INT_RGB);
		imageStrip.anImage = aBufferedImage;
		aBitmapEditor = new BitmapEditor(tileX, tileY);
		JPanel upperPanel = new JPanel();
		JButton gridButton = new JButton("Grid ON/OFF");
		gridButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (imagePanel.showGrid == true)
					imagePanel.showGrid = false;
				else
					imagePanel.showGrid = true;
				imagePanel.repaint();
			} // actionPerformed
		});// ActionListener, addActionListener
		upperPanel.setLayout(new GridLayout(3, 0));
		upperPanel.add(this.selectionLabels);
		this.sizeLabels.setSize(tileX, tileY, x, y);
		upperPanel.add(this.sizeLabels);
		upperPanel.add(gridButton);
		this.add(upperPanel, BorderLayout.NORTH);
		this.add(this.scrollPane, BorderLayout.SOUTH);
		this.setTitle("Image Strip Editor");
		setupUI(tileX, tileY, x, y);
		this.setVisible(true);
		imagePanel.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseMoved(MouseEvent me) {

				int col = me.getX() / tileX;
				int row = me.getY() / tileY;
				int number = col + x * row;
				selectionLabels.setPos(col, row, number);
			}

		}); // mouse motion listener
		imagePanel.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent me) {

				int mouseButton = me.getButton();
				int x = me.getX() / tileX;
				int y = me.getY() / tileY;
				if (mouseButton == me.BUTTON1) {
					Image anImage = imageStrip.getImage(x, y);
					aBitmapEditor.setImage(anImage);
				} else {
					BufferedImage anImage = (BufferedImage)aBitmapEditor.getImage();
					imageStrip.putImage(anImage, x, y);
					imagePanel.repaint();
				} // if else

			} // method mouseClicked
		}); // mouse listener
		imageStrip.setFrameComponent(this);
	} // constructor ImageSelector()

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ImageStripEditor();
	} // main method

} // class ImageSelector
