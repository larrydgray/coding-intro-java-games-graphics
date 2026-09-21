package net.sourceforge.javagg.rpg;

import java.awt.*;

/**
 * You have two kinds of entrances.  One kind enters one way(in). And one kind
 * that gives where you have a choice of up or down. Maps and Portals share 
 * Entrance Objects. 
 */
class Entrance {
	
	/**
	 * An all in one constructor to build 
	 * this Entrance object.
	 */
	public Entrance(String mapName, 
	                String portalName, 
	                Point location,
	                int level,
	                boolean isUniDirectional,
	                boolean isTop,
	                boolean isBottom){

    	this.mapName = mapName;
    	this.portalName = portalName;
    	this.location = location;
    	this.level = level;	                	
		this.isUniDirectional = isUniDirectional;
		this.isBiDirectional = !isUniDirectional;
		this.isTop = isTop;
		this.isBottom = isBottom;
	
	} // end constructor 
	
	/**
	 * Name of the Map this entrance belongs to.
	 */
	public String mapName = "";
	
	/**
	 * Name of the Portal that this entrance belongs to.
	 */
	public String portalName = "";
	
	/**
	 * The location of the tile for this entrance on this map.
	 */
	public Point location;
	
	/**
	 * This entrances level in the Portal. For example on a building with 5 floors
	 * this Entrance might be floor 3, where the portal is in this case an Elevator. 
	 * Or a set of stairs.
	 */
	public int level;
	
	/**
	 * Is this a two way entrance (up/down)?
	 */
	boolean isBiDirectional;
	
	/**
	 * Is this a one way entrance (in)?
	 */
	boolean isUniDirectional;	
	
	/**
	 * Is this a Top level entrance?
	 */
	boolean isTop;
	
	/**
	 * Is this a BottomLevel entrance?
	 */
	boolean isBottom;

} // end class Entrance
