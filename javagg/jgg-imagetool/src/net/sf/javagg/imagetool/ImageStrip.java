package net.sf.javagg.imagetool;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



/**
 * This is a utility class that will aid the developer in the storing and
 * retrieving of images from an image strip. An image strip might be a single
 * row or multiple rows in a grid form. Once the image strip is loaded all the
 * developer need to do to get images from the image strip is to call a get and
 * put methods. The developer may give the get and put methods a single int
 * representing the image number beginning from 0 for the first image. The
 * developer also may specify row and col for the image when the images strip is
 * a grid of images.
 * 
 * An image strip is stored in a png file with and associated
 * blah.png.properties.xml file. This file contains values for rows and cols for
 * the image strip and width and height of the tiles.
 * 
 * @author Larry Gray(caverdude)
 * 
 */
public class ImageStrip {
	private class DisplayPanel extends JPanel {
		public DisplayPanel() {
			this.setPreferredSize(new Dimension(anImage.getHeight(this),
					anImage.getWidth(this)));
		}

		public void paintComponent(Graphics g) {
			g.drawImage(anImage, 0, 0, null);
		}
	}

	public JPanel getDisplay() {
		return new DisplayPanel();
	}

	/**
	 * 
	 */
	public ImageStrip() {

	}

	/**
	 * 
	 * @param fileName
	 */
	public ImageStrip(String fileName, Component mediaTrackerComponent) {
		aComponent = mediaTrackerComponent;
		File file = null;
		file = new File(fileName);
		loadImage(file);
	}

	public ImageStrip(File file, Component mediaTrackerComponent) {
		aComponent = mediaTrackerComponent;
		loadImage(file);
	}

	/**
	 * 
	 * @param file
	 */
	public ImageStrip(File file) {
		loadImage(file);
	}

	/** A reference to a component for the file chooser dialog. */
	private Component aComponent;

	/**
	 * Sets a reference to a component for the file chooser dialog.
	 * 
	 * @param aComponent
	 *            The frame or component.
	 */
	public void setFrameComponent(Component aComponent) {
		this.aComponent = aComponent;
	}

	/**
	 * The image containing the strip of tiles which is actually a buffered
	 * image.
	 */
	public Image anImage;
	/** Actual number of rows in the image strip. */
	public int rows;
	/** Actual number of columns in the image strip. */
	public int cols;
	/** Size of the tiles in pixels in the image strip. */
	public Dimension tileSize;

	/**
	 * Used by the other getImage methods. Returns and image from an exact
	 * rectangular area of the image strip.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	private Image getImage(int x, int y, int width, int height) {
		BufferedImage imageCopy = new BufferedImage(anImage.getWidth(null),
				anImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics g = imageCopy.getGraphics();
		g.drawImage(anImage, 0, 0, null);
		// System.out.println("x:"+x+" y:"+y+" width:"+width+" height"+height);
		imageCopy = imageCopy.getSubimage(x, y, width, height);

		return imageCopy;
	} // method getImage(x,y,w,h)

	/**
	 * Used by the other put image methods. Using fields for x,y it puts an
	 * image in an exact location in the image strip.
	 * 
	 * @param theImage
	 */
	private void putImage(Image theImage) {
		Graphics g = ((BufferedImage) anImage).getGraphics();
		g.drawImage(theImage, this.x, this.y, null);
	}

	/** Used by the put image methods */
	private int x = 0;
	/** Used by the put image methods. */
	private int y = 0;

	/**
	 * Puts a tile image in an image strip locations based on col and row
	 * position.
	 * 
	 * @param theImage
	 * @param col
	 *            0 to number of actual cols-1
	 * @param row
	 *            0 to number of actual rows-1
	 */
	public void putImage(Image theImage, int col, int row) {
		this.x = col * tileSize.width;
		this.y = row * tileSize.height;
		this.putImage(theImage);
	}

	/**
	 * Puts a tile image in an image strip (either single row or grid) according
	 * to its number. 0 is the beginning image number.
	 * 
	 * @param theImage
	 *            a tile image.
	 * @param number
	 *            0 to the actual number of images-1
	 */
	public void putImage(Image theImage, int number) {
		int row = (int) number / cols;
		int col = number - ((int) number / cols);
		this.x = col * tileSize.width;
		this.y = row * tileSize.height;
		this.putImage(theImage);

	} // method putImage(Image, number);

	/**
	 * Gets a tile image by column and row number from the image strip.
	 * 
	 * @param col
	 *            0 to actual number of columns-1
	 * @param row
	 *            0 to actual number of rows-1
	 * @return a tile image
	 */
	public Image getImage(int col, int row) {
		int x = col * tileSize.width;
		int y = row * tileSize.height;
		int width = tileSize.width;
		int height = tileSize.height;
		// System.out.println("1 x:"+x+" y:"+y+" width:"+width+" height"+height);
		return this.getImage(x, y, width, height);
	} // method getImage(col,row)

	/**
	 * Gets the image by image number, works for both single row image strips
	 * and grid image strips.
	 * 
	 * @param number
	 *            0 to actual number of images-1
	 * @return a tile image.
	 */
	public Image getImage(int number) {
		int row = (int) number / cols;
		int col = number - row * cols;
		int x = col * tileSize.width;
		int y = row * tileSize.height;
		int width = tileSize.width;
		int height = tileSize.height;
		// System.out.println("getImage(num) x:"+x+" y:"+y+" width:"+width+" height"+height+"number:"+
		// number);
		return this.getImage(x, y, width, height);
	} // method getImage(number)

	/**
	 * Returns the actual number of images in the image strip for either single
	 * row or grid.
	 * 
	 * @return number of images
	 */
	public int getNumberOfImages() {
		return cols * rows;
	} // method getNumberOfImages()

	/**
	 * A default working directory for the file chooser dialog.
	 */
	public String defaultDir = "c:/java/images"; // FIXME

	/**
	 * Saves the image strip to a png file with associated properties file which
	 * is in xml format.
	 */
	public void saveImage() {
		JFileChooser fc = new JFileChooser();
		File currentDir = new File(defaultDir);
		fc.setCurrentDirectory(currentDir);
		int ret = fc.showSaveDialog(aComponent);
		if (ret == fc.APPROVE_OPTION) {
			File f = fc.getSelectedFile();
			String fileName = f.getName();
			String filePath = f.getPath();
			File propertiesFile = new File("" + filePath + ".properties.xml");
			this.saveImage(f);
			try {
				FileOutputStream aFileOutputStream = new FileOutputStream(
						propertiesFile);
				Properties properties = new Properties();
				properties.setProperty("tileX", "" + tileSize.width);
				properties.setProperty("tileY", "" + tileSize.height);
				properties.setProperty("cols", "" + cols);
				properties.setProperty("rows", "" + rows);
				properties.storeToXML(aFileOutputStream,
						"Image Strip Properties");
			} catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		} // if
	} // method saveImage()

	/**
	 * Loads the image strip given a png file.
	 * 
	 * @param f
	 */
	public void loadImage(File f) {
		try {
			MediaTracker mt = new MediaTracker(aComponent);
			Image m = Toolkit.getDefaultToolkit().getImage(f.getAbsolutePath());
			mt.addImage(m, 0);
			mt.waitForAll();
			int imageSizeX = m.getWidth(null);
			int imageSizeY = m.getHeight(null);
			BufferedImage bufferedImage = new BufferedImage(imageSizeX,
					imageSizeY, BufferedImage.TYPE_INT_RGB);
			Graphics g = bufferedImage.getGraphics();
			g.drawImage(m, 0, 0, null);
			anImage = bufferedImage;
		} catch (Exception e) {
			e.printStackTrace(System.err);
		} // catch
		String fileName = f.getName();
		String filePath = f.getPath();

		File propertiesFile = new File(""
				+ filePath.substring(0, filePath.indexOf('.'))
				+ ".properties.xml");
		try {
			FileInputStream aFileInputStream = new FileInputStream(
					propertiesFile);
			Properties properties = new Properties();
			properties.loadFromXML(aFileInputStream);
			this.cols = Integer.parseInt((String) properties.get("cols"));
			this.rows = Integer.parseInt((String) properties.get("rows"));
			this.tileSize = new Dimension(Integer.parseInt((String) properties
					.getProperty("tileX")),
					Integer.parseInt((String) properties.getProperty("tileY")));
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (InvalidPropertiesFormatException ipfe) {
			ipfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	} // method loadImage(File)

	/**
	 * Loads the Image from a png file and loads the image strip properties of
	 * columns, rows, tile size from an associated properties file in xml
	 * format.
	 */
	public void loadImage() {
		JFileChooser fc = new JFileChooser();
		File currentDir = new File(defaultDir);
		fc.setCurrentDirectory(currentDir);
		File f = null;
		int ret = fc.showOpenDialog(aComponent);
		if (ret == fc.APPROVE_OPTION) {
			f = fc.getSelectedFile();

			this.loadImage(f);
		} // if

	} // method loadImage()

	/**
	 * Saves the image strip to the given png file.
	 * 
	 * @param f
	 */
	public void saveImage(File f) {
		String ext = f.getName();
		int n = ext.indexOf(".");
		if (n == -1) {
			ext = "png"; // default
			f = new File(f.getParentFile(), f.getName() + "." + ext);
		} else
			ext = ext.substring(ext.indexOf(".") + 1);
		Iterator it = ImageIO.getImageWritersBySuffix(ext);
		try {
			if (!it.hasNext()) {
				String s = "Can't save, unknown format \"" + ext
						+ "\".\nSupported: ";
				String[] formats = ImageIO.getWriterFormatNames();
				for (int i = 0; i < formats.length; i++)
					s += formats[i] + ", ";
				JOptionPane.showMessageDialog(null, s, "Unknown format",
						JOptionPane.ERROR_MESSAGE);

			} else {
				ImageWriter iw = (ImageWriter) it.next();
				iw.setOutput(new FileImageOutputStream(f));
				iw.write((RenderedImage) anImage);
			} // else
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Can't save to file: " + e.toString()
							+ ". Check stack trace.", "Error saving",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace(System.err);
		} // catch

	} // method saveImage(File)

}// class ImageStrip
