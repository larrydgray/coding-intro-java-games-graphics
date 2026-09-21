package net.sourceforge.javagg.rpg;

import java.awt.*;

/**
 * A hardcoded concrete Dungeon Map object.
 */
class Dungeon1Map extends Map {
	
	/**
	 * Gets a subMap of this map.
	 */
	public net.sourceforge.javagg.rpg.Map getSubMap(int x1,int y1,int x2, int y2){
    
    	return super.getSubMap(super.getMainMap(),x1,y1,x2,y2);
	
	} // end getSubMap method 
	
	/**
	 * Gets the name of this map.
	 */
	public String getMapName(){
		
		return this.mapName;
		
	} // end getMapName method
	
	/**
	 * Hardcoding that constrcuts a Dungeon Map
	 */
	Dungeon1Map(Universe universe){
		super(universe);
		super.setMainMap(this);	
		super.setMapType(2);
		super.mapName="Dungeon1";
		
		this.add(1,"map/dun.mp1");
		this.add(2,"map/dun.mp2");
		// walkzones
		this.add(4,"map/dun.mp4");
		// shroud
		this.add(5,"map/dun.mp5");
		
		
	}// end Dungeon Map Constrcutor
	
}// End Class Dungeon1Map 
