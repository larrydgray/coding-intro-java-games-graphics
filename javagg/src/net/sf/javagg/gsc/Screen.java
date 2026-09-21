package net.sf.javagg.gsc;

 
public interface Screen {
	/**
	 * Sets the zoom size(scale) for the screen view. A screen can be magnified
	 * or reduced to a 1:1 ratio. This sets the ratio. For example an old
	 * resolution on an 8 bit machine might have been 320x200 or even 160x200.
	 * On windows screen of 1024x768 this would be the size of a postage stamp
	 * at 1 to 1. So you would give it a 2:1 or 3:1 and it would show up nicely
	 * and emulate this old resolution. This is a very positive effect. It
	 * speeds up your graphics work. Imagine animating space invaders on a
	 * resolution of 320x200? instead of 640x480 or 1024x768.. Interpreted code
	 * run on a jvm would perform nicely under these conditions. The drawback of
	 * course is that the game can old and cheap. However consider this, we
	 * don't have disk limitations or memory limitations or Color limitations to
	 * deal with. And we have the net. So an old game that was cool for its time
	 * is more complex and very cool in an Interpreter and JVM.
	 * 
	 * 
	 * 
	 * @see "GraphicsScreenComponent(later)"
	 * @see "TextScreenComponent(later)"
	 * @see "GameScreenComponent(later)"
	 * @see ReportScreenComponent a Screen for displaying text and images and
	 *      graphics. Uses typewriter like interface.
	 * @see MapScreenComponent a Screen for displaying multilayer tile graphics
	 *      maps. Also includes behavior for panning the view of the maps.
	 * 
	 * @param zoomSize
	 *            an <code>int</code> scale factor usually 1,2,3,4 etc.
	 *            Probably not much larger than 4.
	 */
	void setScale(int zoomSize);
	/**
	 * Increases zoom size by one.
	 *  
	 */
	void bigger();
	/**
	 * Decreases zoom size by one.
	 *  
	 */
	void smaller();
	
	/**
	 * Clears the screen image (or buffer).
	 *  
	 */
	void clearScreen();
	/**
	 * Sets the resolution for this screen to any arbitrary size.
	 * 
	 * @param x horizontal resolution.
	 * @param y vertical resolution.
	 */

	void setResolution(int x, int y);

	

	
	
	
}
