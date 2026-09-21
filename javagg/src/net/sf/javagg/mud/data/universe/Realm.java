
package net.sf.javagg.mud.data.universe;
/**
 * Realms are levels of dynamically linked rooms
 * and SubRealms.  SubRealms may contain special sets of
 * rooms which have generic descriptions. SubRealms may
 * be static or dynamic or a combination. SubRealms may
 * also generate rooms rather than loading them.
 * 
 * Subrealms have sub realm exits. or some room exits
 * will be subrealm exits.  Subrealms therefore have entry
 * rooms. 
 * 
 * Realms have weather and subrealms can have special weather.
 * weather can be based on time of day and season.
 * 
 * 
 * @author XP
 *
 */
public class Realm extends BasicContainer {
	private String world;
	public String getWorld() {
		return world;
	}
	public void setWorld(String world) {
		this.world = world;
	}
	
}
