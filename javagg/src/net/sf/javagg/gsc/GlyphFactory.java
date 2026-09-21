package net.sf.javagg.gsc;

/**
 * Makes and returns a glyph for each ascii code given as needed by the screen
 * renderer.
 * 
 * @author Larry Gray (caverdude)
 * 
 */
public class GlyphFactory {

	/**
	 * An array containing a set of glyphs for all ascii codes for quick
	 * retrieval.
	 */
	private Glyph font[] = new Glyph[256];

	/**
	 * An image strip containing the glyphs for the entire font.
	 */
	private ImageStrip fontImages;

	/**
	 * Makes a glyph factory given a font in the form of an image strip.
	 */
	public GlyphFactory(ImageStrip fontImages) {
		this.fontImages = fontImages;
		for (int i = 0; i < 256; i++) {
			font[i] = new Glyph(i, fontImages);
		} // for
	} // constructor

	/**
	 * Gets a glyph for a given ascii code.
	 * 
	 * @param c
	 * @return
	 */
	public Glyph getGlyph(char c) {
		if (c > 255)
			c = 255;
		return font[c];
	} // method getGlyph(char)

} // class GlyphFactory
