package net.sourceforge.javagg.rpg;
import java.awt.*;

class Mine1Map extends Map {
	 /**
     * This returns a submap.
     */
    public net.sourceforge.javagg.rpg.Map getSubMap(int x1,int y1,int x2, int y2){
    
    	return super.getSubMap(super.getMainMap(),x1,y1,x2,y2);
	
	} // end getSubMap
	
	/**
	 * Maps have names.
	 */
	public String mapName="Mine1";
	
	/**
	 * Gets this Maps name.
	 */
    public String getMapName(){
    	
		return super.mapName;
		
	}
	
	Mine1Map(Universe universe){
		super(universe);
			super.setMainMap(this);
		super.setMapType(4);
		super.mapName="Mine1";
		
		this.add(1,"map/min.mp1");
		this.add(2,"map/min.mp2");
		this.add(3,"map/min.mp3");
		// walkzones
	    this.add(4,"map/min.mp4");
	    // shroud
	    this.add(5,"map/min.mp5");
	}	
	
}
