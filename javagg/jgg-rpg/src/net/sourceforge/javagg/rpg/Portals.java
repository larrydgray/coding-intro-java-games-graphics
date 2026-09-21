package net.sourceforge.javagg.rpg;

import java.util.*;

/**
 * This is a set of Portals for the whole game or Universe.
 * Later this may be a set of Portals for individual worlds.
 */
public class Portals extends HashSet {
	
	/**
	 * A set of Portals keeps a reference to the universe that it 
	 * lives in.
	 */
	Universe universe;
	
	/**
	 * A set of portals is build with a reference to the universe
	 * it lives in.
	 */
	public Portals(Universe universe){
		
		this.universe=universe;
		
	} // end constructor Portals
	
	/**
	 * Adding portals to this set.
	 */
	public void addPortal(Portal portal){
		
		super.add(portal);
		
	} // end addPortal
		
	/**
	 * If give the name of a portal will return the Object for that
	 * portal.
	 */
	public Portal getPortal(String portalName) {
		
		Iterator iterator = this.iterator();
		
		while(iterator.hasNext()){
			
			Portal tempPortal=(Portal)iterator.next();
			
			if((tempPortal.portalName).equals(portalName))return tempPortal;
			
		} // end while
			
		return null;
		
	} // end getPortal
		
} // end class Portals
