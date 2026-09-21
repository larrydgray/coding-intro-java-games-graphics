package net.sourceforge.javagg.rpg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * This is the Frame for the Map Walker Demo.
 * 
 */
public class MapWalkerUI extends JFrame {
    
    /**
     * A debug reference for asserts.
     */
    public static JFrame frame;
	/**
	 * Constructor
	 */
	 
	public static Universe universe;
	public MapWalkerUI() {
	
		this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent evt){
                System.exit(0);
            }
        });
        setTitle("RPG/MUD Map Walker Demo");
		
		ConsoleWindow.init();
		this.universe = new Game1Universe();
		if (this.universe==null)System.exit(0);
		Map firstMap;
		
		firstMap = this.universe.maps.getMap("World1"); // change this to change maps
		
		
		
		//System.out.println("mapsize:"+map1.size());
		int mapType = 1; // change this to change maptypes
		
		GameWalkPanel gameWalkPanel = 
		    new GameWalkPanel(mapType,firstMap,universe.maps);
        gameWalkPanel.position = new Point(2,2);
        gameWalkPanel.removeShroud();
        // adding a game walker panel in scroll pane to the south end of this Frame	
		this.getContentPane().add(new JScrollPane(gameWalkPanel), BorderLayout.CENTER);
		
		
		CommandPanel commandPanel = new CommandPanel(gameWalkPanel);
		
		// put the command panel on the north of this frame
		this.getContentPane().add(commandPanel,BorderLayout.NORTH);
		
		this.setVisible(true);
		
		this.pack();
		
	
		
	} // end constructor MapWalkerUI
	
	/**
	 * Nothing to do here yet.
	 */
	private void load() {
		
		// TODO: Add your code here
		
	} // end load method
	
    /**
     * Nothing to do here yet.
     */
	private void save() {
		
		// TODO: Add your code here
		
	} // end save method

    /**
     * It all begins here.
     */
	public static void main(String[] args) {
		
		MapWalkerUI ui = new MapWalkerUI();
		
		MapWalkerUI.frame = ui;
	} // end main method
		
} // end Class MapWalkerUI
