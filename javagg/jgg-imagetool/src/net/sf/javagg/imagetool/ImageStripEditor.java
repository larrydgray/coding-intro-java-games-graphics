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
	 * storage. 
	 * @param x
	 * @param y
	 * @param cols
	 * @param rows
	 */
	
	private void setupUI(int x, int y, int cols, int rows){
		Toolkit toolkit = Toolkit.getDefaultToolkit();

		// Get the current screen size
		Dimension scrnsize = toolkit.getScreenSize();
	    int screenWidth=scrnsize.width;
	    int screenHeight=scrnsize.height;
		/*BitmapEditor.editorPanel = aBitmapEditor.createEditor(x,
				y);
		aBitmapEditor.jf.getContentPane().removeAll();
		aBitmapEditor.jf.getContentPane()
				.add(BitmapEditor.editorPanel);
		aBitmapEditor.jf.getContentPane().validate();
		aBitmapEditor.jf.getContentPane().repaint();*/
		aBitmapEditor.makeNew(x,y);
		int frameX=cols*x;
		int frameY=rows*y;
		if(frameX>(screenWidth-50))frameX=screenWidth-50;
		if(frameY>(screenHeight-50))frameX=screenHeight-50;
		setSize(new Dimension(frameX, frameY));
		imagePanel
				.setPreferredSize(new Dimension(cols*x, rows*y));
		scrollPane.setSize(cols*x, rows*y);
		imagePanel.repaint();
		
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
	/** */
	private int x = 16;
	/** */
	private int y = 16;
	/** */
	private int tileX = 8;
	/** */
	private int tileY = 8;
	
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
				tileX = 0;
				tileY = 0;
				try {
				    tileX = Integer.parseInt(sx);
					tileY = Integer.parseInt(sy);
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
				} // catch
				sizeLabels.setSize(tileX, tileY, x, y);
			} // actionPerformed
		});// ActionListener, addActionListener
		newImageStrip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				imageStrip.cols = x;
				imageStrip.rows = y;
				imageStrip.tileSize = new Dimension(tileX, tileY);
				BufferedImage aBufferedImage = new BufferedImage(tileX * x,
						tileY * y, BufferedImage.TYPE_INT_RGB);
				imageStrip.anImage = aBufferedImage;
				setupUI(imageStrip.tileSize.width,imageStrip.tileSize.height,imageStrip.cols,imageStrip.rows);
				

			} // actionPerformed
		});// ActionListener, addActionListener
		imageSize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String sx = JOptionPane.showInputDialog(null, "Columns?");
				String sy = JOptionPane.showInputDialog(null, "Rows?");
				x = 0;
				y = 0;
				try {
					x = Integer.parseInt(sx);
					y = Integer.parseInt(sy);
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
				} // catch
				sizeLabels.setSize(tileX, tileY, x, y);
			} // actionPerformed
		}); // ActionListener, addActionListener

		((ImagePanel) imagePanel).setImageStrip(imageStrip);
		imageStrip.cols = 16;
		imageStrip.rows = 16;
		imageStrip.tileSize = new Dimension(8, 8);
		BufferedImage aBufferedImage = new BufferedImage(8 * 16, 8 * 16,
				BufferedImage.TYPE_INT_RGB);
		imageStrip.anImage = aBufferedImage;
		this.setSize(new Dimension(5 * 40, 5 * 40));
		imagePanel.setPreferredSize(new Dimension(16 * 8, 16 * 8));
		scrollPane.setSize(50, 50);
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
		this.sizeLabels.setSize(8, 8, 16, 16);
		upperPanel.add(this.sizeLabels);
		upperPanel.add(gridButton);
		this.add(upperPanel, BorderLayout.NORTH);
		this.add(this.scrollPane, BorderLayout.SOUTH);
		this.setTitle("Image Strip Editor");
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
				int number = col + y * row;
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
		aBitmapEditor = new BitmapEditor(8, 8);
		imageStrip.setFrameComponent(this);
	} // constructor ImageSelector()

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ImageStripEditor();
	} // main method

} // class ImageSelector
