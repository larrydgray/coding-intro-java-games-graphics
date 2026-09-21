package net.sourceforge.javagg.rpg;
import java.awt.*;

class Cave1Map extends Map {
	 /**
     * This returns a submap.
     */
    public net.sourceforge.javagg.rpg.Map getSubMap(int x1,int y1,int x2, int y2){
    
    	return super.getSubMap(super.getMainMap(),x1,y1,x2,y2);
	
	} // end getSubMap
	
	/**
	 * Maps have names.
	 */
	public String mapName="Cave1";
	
	/**
	 * Gets this Maps name.
	 */
    public String getMapName(){
    	
		return super.mapName;
		
	}
	
	Cave1Map(Universe universe){
		super(universe);
		super.setMainMap(this);	
		super.setMapType(3);
		super.mapName="Cave1";
		this.add(1,"map/cav.mp1");
		this.add(2,"map/cav.mp2");
		// walkzones
		this.add(4,"map/cav.mp4");
		// shroud
		this.add(5,"map/cav.mp5");
	}	
	
}
