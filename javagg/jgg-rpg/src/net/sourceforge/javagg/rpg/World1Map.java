package net.sourceforge.javagg.rpg;

import java.util.*;
import java.awt.*;

/**
 * This is a hard coded Map class for the main World Map.
 */
class World1Map extends net.sourceforge.javagg.rpg.Map {
	
	
    /**
     * This returns a submap.
     */
    public net.sourceforge.javagg.rpg.Map getSubMap(int x1,int y1,int x2, int y2){
    
    	return super.getSubMap(super.getMainMap(),x1,y1,x2,y2);
	
	} // end getSubMap
	
	/**
	 * Maps have names.
	 */
	public String mapName="World1";
	
	/**
	 * Gets this Maps name.
	 */
    public String getMapName(){
    	
		return super.mapName;
		
	}
	
	World1Map(Universe universe){
		super(universe);
		super.setMainMap(this);
		super.setMapType(1);
		
         //Keeps an original reference to itself.
         
		super.mapName="World1";
		
		this.add(1,"map/map.mp1");
		this.add(2,"map/map.mp2");
		this.add(3,"map/map.mp3");
		// walkzones
		this.add(4,"map/map.mp4");
		// shroud
		this.add(5,"map/map.mp5");
	} // end constructor World1Map	
	
} // end class World1Map
