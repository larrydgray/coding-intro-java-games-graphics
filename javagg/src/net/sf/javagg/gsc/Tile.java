/*
 * Created on May 7, 2004
 *
 *  I'm not sure we needed this, but here it is.
 */
package net.sf.javagg.gsc;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;

/**
 * This will define the responsibilities of a game tile. A tile can be a map
 * tile or can be used for other purposes. A tile can be square, or hexegonal. A
 * tile may be transparent or solid, meaning usually white is set as a
 * transparency color therefor the tile can overlay other tiles.
 * 
 * @author Larry Gray
 * @version 1.4
 * 
 * @see <code>TileMap</code> In which an implementation will act as a factory
 *      for tiles.
 * @see <code>MapScreenComponent</code> a screen for displaying tile maps. You
 *      will add <code>Tiles</code> to the <code>MapScreenComponent</code>.
 */
public class Tile {
	
	/** This is a reference to the image strip that contains the tile image*/
	private ImageStrip imageStrip;
	
	/**
	 * A tile must be instantiated with an image strip reference. 
	 * 
	 * @param imageStrip
	 */
	public void setImageStrip(ImageStrip imageStrip){
		this.imageStrip=imageStrip;
	}
	
	/**  The image taken from the image strip for this tile.*/
	private Image tileImage;
	
	/** Is this tile hexigon? FUTURE
	private boolean isHex;*/

	/** Is this tile square?  FUTURE
	private boolean isSquare; */
	
	/** Is this tile diamond? FUTURE
	private boolean isDiamond; */

	/** a cryptic two or three character string. */
	private String mapCode;

	/** Name of tile. */
	private String tileName;
	
    /**  an id for this tile */
	private String id;
    
	/** Does this tile have a transparent color?*/
	private boolean transparent;
	
	
	/**
	 * Makes a Tile using an image taken from the image strip in the given col, row position 
	 * on the image strip.
	 * 
	 * @param imageStrip
	 * @param x
	 * @param y
	 */
	public Tile(ImageStrip imageStrip, String mapCode, int col, int row, boolean transparent){
		this.imageStrip=imageStrip;
		tileImage=imageStrip.getImage(col, row);
		this.mapCode=mapCode;
		if(tileImage==null)throw new NullPointerException("getImage() of ImageStrip returned null!");
		if(transparent)this.setTransparent();
	}
	
	/**
	 * Makes a Tile using an image taken from the image strip in the given position 
	 * on the image strip.
	 * 
	 * @param imageStrip
	 * @param number
	 */
	public Tile(ImageStrip imageStrip, int number){
		this.imageStrip=imageStrip;
		tileImage=imageStrip.getImage(number);
		if(tileImage==null)throw new NullPointerException("getImage() of ImageStrip returned null!");
	}
	
	
	/**
	 * @see gsc.Tile#getID()
	 * 
	 * Gets the ID of the tile.
	 */
	public String getID() {

		return id;
	} //getID()

	/**
	 * Gets the image object of this Tile.
	 * 
	 * @see gsc.Tile#getImage()
	 */
	public Image getImage() {

		return tileImage;
	}// getImage()

	/**
	 * 
	 * @see gsc.Tile#getMapCode()
	 * 
	 * Gets the map code of the tile.
	 */
	public String getMapCode() {

		return mapCode;
	} //getMapCode()

	/**
	 * Gets the name of the tile.
	 * 
	 * @see gsc.Tile#getName()
	 */
	public String getName() {

		return tileName;
	}// getName()

	/**
	 * Is this tile hexigon?
	 * 
	 * @see gsc.Tile#isHex()
	 * @return <code>true</code> If this form is a hex. <code>false</code>
	 *         otherwise
	 */
	/*FUTURE
	public boolean isHex() {
		return isHex;

	}//isHex() */

	/**
	 * Is this tile square?
	 * 
	 * @return <code>true</code> If form is a square. <code>false</code>
	 *         otherwise
	 * @see gsc.Tile#isSquare()
	 */
	/* FUTURE
	public boolean isSquare() {
		return isSquare;

	}//isSquare() */

	/**
	 * Sets the Tile's white color to clear.
	 * 
	 * @return <code>true</code> If the form is transparent.
	 *         <code>false</code> otherwise
	 */
	public boolean isTransparent() {

		return transparent;
	}//isTransparent()

	/**
	 * Sets the tile to hexigon not square.
	 * 
	 * @see gsc.Tile#setHex()
	 */
    /* FUTURE
	public void setHex() {
		isSquare = false;
		isHex = true;

	}//setHex() */

	/**
	 * Sets the ID for the tile.
	 * 
	 * @see gsc.Tile#setID(java.lang.String)
	 * @param id
	 *            a cryptic string id.
	 */
	public void setID(String id) {
		this.id=id;

	}//setID(String id)

	/**
	 * Sets the image object for this tile. Used by setTransparent();
	 * 
	 * @see gsc.Tile#setImage(java.awt.Image)
	 * @param image
	 *            the image to set.
	 */
	public void setImage(BufferedImage image) {
		tileImage=image;
	} // setImage(Image image)

	/**
	 * 
	 * Sets a cryptic code for use in map files.
	 * 
	 * @param mapCode
	 *            usually two to three characters.
	 */
	public void setMapCode(String mapCode) {
		this.mapCode = mapCode;

	}//setMapCode(String mapCode)

	/**
	 * Sets the Tile's name.
	 * 
	 * @see gsc.Tile#setName(java.lang.String)
	 * @param name
	 *            the name for the tile.
	 */
	public void setName(String name) {
		tileName = name;

	}//setName(String name)

	/**
	 * Sets the tile to square not hexigon.
	 * 
	 * @see gsc.Tile#setSquare()
	 */
	/* FUTURE
	public void setSquare() {
		isSquare = true;
		//isHex = false; FUTURE
	}//setSquare() */
    private Image nonTransparentImage=null;
	/** sets the Transparency */
	public void setTransparent() {
		this.transparent=true;
		nonTransparentImage=tileImage;
		tileImage=makeColorTransparent(this.getImage(), Color.white);
	} // setTransparent()
	
	public void setNotTransparent(){
		this.transparent=false;
		tileImage=nonTransparentImage;
	}

	/**
	 * Makes a given color of this tile transparent.
	 * 
	 * @param im
	 *            Image to be made transparent.
	 * @param color
	 *            a Color for which you wish to be used as the transparent
	 *            color.
	 * @returns Image changed into an image with a color made to be transparent
	 *          (Usually <code>Color.white</code>).
	 */
	public Image makeColorTransparent(Image im, final Color color) {
			ImageFilter filter = new RGBImageFilter() {
			public int markerRGB = color.getRGB() | 0xff000000;

			public final int filterRGB(int x, int y, int rgb) {
				if ((rgb | 0xff000000) == markerRGB) {
					return 0x00FFFFFF & rgb;
				} else {
					return rgb;
				}
			}
		};
		ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
		return Toolkit.getDefaultToolkit().createImage(ip);
	} // end makeColorTransparent method
	
	
} // Tile interface

