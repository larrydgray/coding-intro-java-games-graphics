package net.sf.javagg.gsc;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;

/**
 * A small pictorial symbolic image representing a letter or symbol or number in
 * some alphabet.
 * 
 * @author Larry Gray(caverdude)
 * 
 */
public class Glyph {

	/** Is this glpyh's background transparent */
	private boolean transparent;

	private Image image;

	/**
	 * Sets this glyph to have transparent background.
	 * 
	 * @param transparentColor
	 */
	public void setTransparent(Color transparentColor) {
		if (image != null)
			image = this.makeColorTransparent(image, transparentColor);
		else
			System.out.println("null pointer for image in Tile!");
		transparent = true;
	} // setTransparent

	/**
	 * This can set the background or the foreground color of the glypgh to
	 * transparent. Glyphs are created using black as foreground and white as
	 * background.
	 * 
	 * @param im
	 * @param color
	 * @return
	 */
	public Image makeColorTransparent(Image im, final Color color) {
		ImageFilter filter = new RGBImageFilter() {
			public int markerRGB = color.getRGB() | 0xff000000;

			public final int filterRGB(int x, int y, int rgb) {
				if ((rgb | 0xff000000) == markerRGB) {
					return 0x00FFFFFF & rgb;
				} else {
					return rgb;
				} // if
			} // filterRGB
		}; // new RGBImageFilter
		ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
		return Toolkit.getDefaultToolkit().createImage(ip);
	} // makeColorTRansparent

	/**
	 * The small glyph image. Usually these are anywhere from 8x8 pixels to
	 * 16x16 pixels in size. For a single font they will all be the same size.
	 */
	private Image glyph = null;

	/** ASCII code for this glyph image. */
	private int asciiCode;

	/**
	 * Makes a glyph given ascii code and font image strip.
	 * 
	 * @param code
	 * @param font
	 *            ImageStrip containing the font.
	 */
	public Glyph(int code, ImageStrip font) {
		this.font = font;
		asciiCode = code;
	}

	/** Font image strip. */
	private ImageStrip font;

	/**
	 * Returns the small glyph image for use by the text screen renderer.
	 * 
	 * @return Image the gyph image.
	 */
	public Image getGlyphImage() {
		if (glyph == null) {
			glyph = font.getImage(asciiCode);
		} // if
		return glyph;
	} // method getGlyphImage()
} // class Glyph
