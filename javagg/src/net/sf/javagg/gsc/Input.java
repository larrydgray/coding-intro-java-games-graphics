
package net.sf.javagg.gsc;


/**
 * This will provide some methods that handle keyboard input which resembles the retro console
 * input. It will also define some basic actions to take for certain key events or mouse events. 
 *
 * 
 * @author Larry Gray
 * @version 1
 * 

 */
public interface Input {
	
	String input();
	void keyTyped();
	void mouseClick();
	void doubleMouseClick();
	
	

} // Input Interface
