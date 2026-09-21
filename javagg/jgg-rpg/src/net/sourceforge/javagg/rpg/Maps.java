package net.sourceforge.javagg.rpg;

import java.util.*;

/**
 * This Class is a container for RPG Tile Maps which are set objects. This 
 * object iteself is a Set fo RPG Maps. This object gets its maps, sets its maps.
 * for now it a set of maps for the entired Universe. Later it might be a set of 
 * maps for single world. Where there may be multiple worlds. In the case of a
 * Star Trek RPG.
 */
public class Maps extends HashSet {
    
    /**
     * A Map set is created with a reference to the universe that it lives in.
     */ 
    public Maps(Universe u){
    	
    	super();
        
        this.universe=u;
    
    } // end constructor Maps
    
    /*
     
    public ArrayList getPortalEntrances(String portalName){
    	
    	ArrayList tempPortalEntrances = new ArrayList();
    	
    	Iterator mapIterator = iterator();
    	
    	Map tempMap;
    	
    	while(mapIterator.hasNext()){
    		
    		 tempMap=(Map) mapIterator.next();
    		 
    	     tempPortalEntrances.addAll(
    	     	tempMap.getPortalEntrances(portalName));
    	     	
    	} // end while

    	return tempPortalEntrances;     

    } // end getPortalEntrances	     	
    
    */

    /**
     * A set of Maps keeps a reference to the Universe that it lives in.
     */
    Universe universe;

    /**
     * If given a map name, returns the Map object for that name.
     */
	public Map getMap(String mapName) {
		
		Map map = null;
		
		Iterator iterator = this.iterator();
		
		while(iterator.hasNext()){
		
			map = (Map)iterator.next();
			
			System.out.println("tempMap.mapName:"+map.mapName);
		
			if((map.mapName).equals(mapName))return map;
		
		} // end while
		if (map == null){ 
		
		  System.out.println("Did not find map "+mapName);
		
		  System.exit(0);
		
		}  
		
		return null;
	
	} // end getMap method

	/**
	 * Adds a Map to this set of maps.
	 */
	public void addMap(Map map) {
		
		((HashSet)this).add(map);
		
	} // end setMap
	
} // end class Maps
