package net.sourceforge.javagg.rpg;

import java.util.*;

/**
 *  A base class for Universe Objects. Later we might even have 
 *  world Objects. A Universe is basically one Game. You can have multiple
 *  games with mulitple rules and story lines and everything. A universe therefore
 *  contains every Object and Code needed to play a given game.
 */
public abstract class Universe {
	
	/**
	 * A set of Maps for this Universe
	 */
	Maps maps; // a HashSet
	
	/**
	 * A set of Portals for this Universe
	 */
	Portals portals; // a HashSet
	
	/**
	 * A set of Entrances for this Universe
	 * The fact that I want multi-level portals that have up or down not just
	 * in and out means a more complex api and design. Therefore a Portal has 
	 * many Entrances. A Portal and A Map share an Instance of a given Entrance.
	 */
	Entrances entrances; // a HashSet
	
	/**
	 * A set of NPC's for this Universe
	 */
	HashSet npc; // not used yet
	
	/**
	 * A set of PC's for this Universe
	 */
	HashSet pc; // not used yet
	
	/**
	 * A set of Items for this Universe
	 */
	HashSet items; // not used yet
	
} // end class Universe
