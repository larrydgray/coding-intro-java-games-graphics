package net.sourceforge.javagg.rpg;

import java.util.*;
import java.awt.*;

/**
 * Don't confuse this with a Java Collections Map. 
 * This is an RPG Map, a Set of TileMap layers. 
 * 
 * This object returns a SubMap of any
 * Rectangular area on it.
 */
public class Map extends HashSet implements Comparable {
	
	/**
	 * Not used.
	 */
	protected net.sourceforge.javagg.rpg.Map subMap;
	
	
	/**
	 *
	 */
	int mapType=2;
	/**
	 * Maps have names.
	 */
	public String mapName = "default";
	
	/**
	 * World Map Type ID
	 */
	public static final int WORLD=1;
    
    /**
     * Dungeon Map Type ID
     */
    public static final int DUNGEON=2;
    
    /**
     * Cave Map Type ID
     */
    public static final int CAVE=3;
    
    /**
     * Mine Map Type ID
     */
    public static final int MINE=4;
    
    /**
     * This constructor is never used but is mysteriously called somehow.
     */
    public Universe universe;
    
    /**
     *
     */
    public Map(Universe universe){
    	this.universe=universe;
    	this.setMapName("default map");
    	// put code here for given map
    	// that calls add to build 
    	// a particular map
    	
	} // end Map() constructor
	
	
	/**
	 * Map Constructor that builds a subMap Map of the given map, the submap
	 * are the MapCodes in the rectangular area x1,y1 x2,y2.
	 */
	public Map(net.sourceforge.javagg.rpg.Map map,int x1, int y1, int x2, int y2){
		
		this.mapName=map.mapName;
		
		this.mapType=map.mapType;
		
		this.setMainMap(map.getMainMap());
		
		Iterator mapIterator = map.getMainMap().iterator();
		
		TileMap tileMapLayer;
		
		//System.out.println("about to make tile maps");
		
		while(mapIterator.hasNext()){
			//System.out.println("makingTileMaps");
		
			tileMapLayer=(TileMap)mapIterator.next();
		
			this.add(new TileMap(tileMapLayer,x1,y1,x2,y2));
		
		} // end while
		
	} // end Map
	
	/*
	public ArrayList getPortalEntrances(String portalName){
		
		ArrayList portalEntrances = new ArrayList();
		
		Iterator entranceIterator=this.entranceIterator();
		
		String tempPortalName="";
		
		PortalEntrance tempPortalEntrance;
		
		while(entranceIterator.hasNext()){
		
			tempPortalEntrance=(PortalEntrance)entranceIterator.next();
		
			tempPortalName=tempPortalEntrance.portalName;
		
			if(tempPortalName.equals(portalName)){
		
				portalEntrances.add(tempPortalEntrance);
			
			} // end if
		
		} // end while
		
		return portalEntrances;
		
	} // end getPortalEntrances
	*/
	
	
	/**
	 * I belive this was for Collection puposes. 
	 */
	public boolean equals(Object o){
	
		return mapName.equals(((Map)o).mapName);
	
	} // end equals	
	
	/**
	 *
	 */
	public void setMapType(int mapType){
		this.mapType=mapType;
	}
	
	/**
	 *
	 */
	public int getMapType(){
		return this.mapType;
	}

	/**
	 * A sorter method.
	 */
	public int compareTo(Object o){
		
		return this.mapName.compareTo(((Map)o).mapName);
		
	} // end compareTo
	
	/**
	 * Gets the MapCode count for this map.
	 */	
	public int getSize(){ 
	
	    int temp = 0;
	    
	    Iterator mapIterator = this.iterator();
	    
	    while(mapIterator.hasNext())
	    	temp+=((TileMap)mapIterator.next()).size();
	    	
	    return temp;	
	    
	} // end getSize()
	
	/**
	 * Returns the Entrance object associate with this point on the map 
	 * if there is one, else null.
	 */
	public Entrance getEntrance(int x, int y){
		
		// this is wrong but is ok for now.. we
		// need to iterate map entrances not 
		// universe entrances
		if(this.universe==null)System.out.println("unverse is null!"); // debug
		//if(this.universe.entrances==null)System.out.println("universe.entrances is null!");//debug
		Map map = this.getMainMap();
		
		Iterator iterator = ((map.universe).entrances).iterator();
		
		Point point=new Point(x,y);
		
		while(iterator.hasNext()){
			
			Entrance tempEntrance = (Entrance) iterator.next();
			
			if((tempEntrance.location).equals(point))return tempEntrance;
			
		} // end getEntrance
		
		return null;
		
	}// end getEntrance
	
	 
	
	/**
	 *  This add's a TileMap given its layer and disk location.
	 */		
    public void add(int layer, String location){
    	System.out.println("Adding a map layer");
    	TileMap temp = new TileMap(layer,location);
    	
    	this.add(temp);
    	
    } // end add
    
    /**
     * Sets the Maps name
     */
    public void setMapName(String mapName){
    	
    	this.mapName=mapName;
	}
    
    /** 
     * Gets the maps name
     */
    public String getMapName(){
    	
    	return mapName;
    	
	} // end getMapName;
    /**
     * Is a link to the 1st map used for retrieving all submaps.
     */
    private static net.sourceforge.javagg.rpg.Map mainMap;
    
    /**
     * Sets the top map used for retrieving all submaps.
     */
    protected static void setMainMap(net.sourceforge.javagg.rpg.Map mainMap){
    	
    	net.sourceforge.javagg.rpg.Map.mainMap=mainMap;
    
	} // end setMainMap
	
	/**
	 * Gets the top map used for retrieving submaps.
	 */
	public static net.sourceforge.javagg.rpg.Map getMainMap(){
		
		if(net.sourceforge.javagg.rpg.Map.mainMap==null){ 
		 
			System.out.println("!!!mainMap of Map is set to null!");
		 
		}
		
		return net.sourceforge.javagg.rpg.Map.mainMap;
	
	}	
    
    /**
     * Gets a submap of the given map of the rectangular area x1 y1  x2 y2
     */
	public Map getSubMap(net.sourceforge.javagg.rpg.Map map,int x1, int y1, int x2, int y2) {
		
		if(map==null){
			
			System.out.println("Error map of getSubMap:Map is null!");
			
		}
		
		net.sourceforge.javagg.rpg.Map map2 =
		  new net.sourceforge.javagg.rpg.Map(map, x1, y1, x2 ,y2);
		
		return map2;
		
	} // getSubMap
    	
} // end class Map
