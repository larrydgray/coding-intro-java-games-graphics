package net.sourceforge.javagg.rpg;

import java.util.*;
import java.awt.*;

/**
 * This Class will be a portal link between entrances of multiple maps.
 * Portals have Levels. In the case of Mine shafts, Cave chimney climbs, 
 * Pits or Cliffs, and in the case of stair cases in Cities and Dungeons.
 * 
 * 
 * 
 */
public class Portal implements Comparable {
	
	/**
	 * Portals have entrances which are sides on BiDirectional
	 * and Levels on MultiDirectional.
	 */ 
	public Entrances entrances;
	
	/**
	 * Portals have names
	 */
	public String portalName;
	
	/**
	 * A Portal keeps a reference to the universe that it exist in.
	 */
	Universe universe;
	
	/**
	 * Constructs a generic portal for multi-level constructions
	 * where portal.addEntrance will be used more than 2 times.
	 */
	public Portal(String portalName,Universe universe){
		this.universe=universe;
		this.entrances = new Entrances(universe);
		this.portalName=portalName;
		
	} //end Portal(String) constructor
	
	/**
	 * Constructs a single level portal.
	 */ 
	public Portal(String portalName,Entrance entrance1,Entrance entrance2,Universe universe){
	    this.universe=universe;	
		this.entrances=new Entrances(universe);
		this.portalName=portalName;
		
		this.entrances.addEntrance(entrance1);
		this.entrances.addEntrance(entrance2);
		
	} // end Portal Constructor
	
	public void addEntrance(Entrance entrance){
		
		this.entrances.addEntrance(entrance);
		
	} // end addEntrance
	
	
	/*
	   
	 
	public Map getMap(String mapName){
		
		Iterator iterator = this.iterator();
		
		while(iterator.hasNext()){
			
			Map temp = (Map)iterator.next();
			
			if ((temp.mapName).equals(mapName))return temp;
			
	    } // end while
	    
	    return null;
	    
	} // end getMap
	*/
	
	/**
	 * Is this a portal that has two entrance only(in/out)?
	 */
	public boolean isUniDirectional(){
		
		if (((HashSet)entrances).size()==2)	return true;
		
		else return false;
	
	} // end isUniDirectional
	
	/**
	 * Is this a portal that has levels and many entrances?
	 */
	public boolean isBiDirectional(){
	
		if (((HashSet)entrances).size()>2) return true;
	
		else return false;
	
	} // end isBiDirectional
	
	/**
	 * A Portal equals Method
	 */
	public boolean equals(Object o){
		
		return portalName.equals(((Portal)o).portalName);
		
	} // end equals
	
	/**
	 * A Portal compareTo method
	 */	
	public int compareTo(Object o){
		
		return portalName.compareTo(((Portal)o).portalName);
		
	} // end compareTo
	
	/**
	 * Return the Entrance to the map that is in the up direction. I.e. the 
	 * set of stairs the next level up in the dungeon that corrispond to this
	 * portal.
	 */	
	public Entrance getUpEntrance(Entrance entrance) {
		
		
		return (Entrance)entrances.getEntrance(entrance.level+1);
		
	} // end getUpPortalEntrance
	
	/**
	 * Return the entrance to the map that is in the down direction. I.e. the
	 * mine shaft entrance in the mine map one level down.
	 */
	public Entrance getDownEntrance(Entrance entrance) {
		
	    return (Entrance)entrances.getEntrance(entrance.level-1);	
	    
	} // end getDownPortalEntrance
	
	/**
	 * Is this postion a Entrance?
	 */
	public boolean isPortalEntrance(int x, int y){
		
		Point p = new Point(x,y);
		
		Entrance tempEntrance;
		
		Iterator iterator = entrances.iterator();
		
		while(iterator.hasNext()){
			
			tempEntrance = (Entrance)iterator.next();
			
			if((tempEntrance.location).equals(p))return true;
			
		} // end while		
		
		return false;
		
	} // end isPortalEnrance
	
	/**
	 * 
	 */
	public Entrance getEnterEntrance(Entrance entrance) {
	    	
		if(entrance.isUniDirectional){
			
			if(entrance.level==1)
				return (Entrance)entrances.getEntrance(2);
				
			else return (Entrance)entrances.getEntrance(1);
			
		} // end if	
		else { System.out.println("Entance is not Uni Directional!");
		       System.exit(0);
		} // debug	
		System.out.println("getEnterEntrance is returning NULL!");	
		return null;		
		
	} // end getEnterEntrance
		
} // end class Portal
