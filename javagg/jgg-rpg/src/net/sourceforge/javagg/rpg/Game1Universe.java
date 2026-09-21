package net.sourceforge.javagg.rpg;

import net.sourceforge.javagg.rpg.Universe;
import java.util.*;
import java.awt.*;

/**
 * This is a Universe object that defines Game1 
 * A demo game. Note that this is a hardcoded hack. Later
 * we may need to load everything from config files.
 */
public class Game1Universe extends Universe {
	
	/**
	 * Game1Universe constructor which begins the building of the 
	 * entire game. This constructs a game.
	 */
	public Game1Universe() {
	   
	    
	    // Build the maps
	    // Maps have names
	    this.maps = new Maps(this);
		
		this.maps.addMap(new World1Map(this));
		this.maps.addMap(new Cave1Map(this));
		this.maps.addMap(new Dungeon1Map(this));
		this.maps.addMap(new Mine1Map(this));
		
		
		// Build portals
		// Portals have Names
		// Here again we do some hardcoding.
		// we are adding 3 portals for our beginning test.
		
		this.entrances = new Entrances(this);
		Entrance anEntrance1 = 
		 	new Entrance( "World1", // map name
		 	              "Dungeon1", // portal name
		 	              new Point(7,1),
		 	              1,
		 	              true,
		 	              false,
		 	              false);
        this.entrances.addEntrance(anEntrance1);
        Entrance anEntrance2 = 
		 	new Entrance( "Dungeon1", // map name
		 	              "Dungeon1", // portal name
		 	              new Point(1,5),
		 	              2,
		 	              true,
		 	              false,
		 	              false);
        this.entrances.addEntrance(anEntrance2);          		 	              
        this.portals = new Portals(this);
        this.portals.addPortal(new Portal("Dungeon1",anEntrance1,anEntrance2,this));
		
		// next portal
		
		anEntrance1 = 
		 	new Entrance( "World1", // map name
		 	              "Cave1", // portal name
		 	              new Point(7,8),
		 	              1,
		 	              true,
		 	              false,
		 	              false);
        this.entrances.addEntrance(anEntrance1);
        anEntrance2 = 
		 	new Entrance( "Cave1", // map name
		 	              "Cave1", // portal name
		 	              new Point(4,1),
		 	              2,
		 	              true,
		 	              false,
		 	              false);
        this.entrances.addEntrance(anEntrance2);          		 	              
		this.portals.addPortal(new Portal("Cave1",anEntrance1,anEntrance2,this));

        // next portal

        anEntrance1 = 
		 	new Entrance( "World1", // map name
		 	              "Mine1", // portal name
		 	              new Point(9,13),
		 	              1,
		 	              true,
		 	              false,
		 	              false);
        this.entrances.addEntrance(anEntrance1);
        anEntrance2 = 
		 	new Entrance( "Mine1", // map name
		 	              "Mine1", // portal name
		 	              new Point(1,1),
		 	              2,
		 	              true,
		 	              false,
		 	              false);
        this.entrances.addEntrance(anEntrance2);          		 	              
		this.portals.addPortal(new Portal("Mine1",anEntrance1,anEntrance2,this));
  
   } // end Game1Universe constructor						
		
} // end class Game1Universe
