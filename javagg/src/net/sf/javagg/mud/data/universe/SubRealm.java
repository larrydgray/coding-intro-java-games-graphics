package net.sf.javagg.mud.data.universe;
/**
 * A SubRealm is a special group of rooms.
 * They can be dynamic or statically laid out or use
 * some combination. Rooms can be genrated using generic
 * desciptions or loaded. 
 * SubRealms may have their own overriding weather. 
 * Certain rooms are entry points into the SubRealms 
 * and have subrealm exits.
 * 
 * A SubRealm may be within a Realm or within an Item.
 * 
 * Examples of subRealms are "Labarynth,  Woods,  Swamps, 
 * Mountains, Caves, Dungeons, Castles, Houses, Large vessels'
 * like Ships.  A subrealm that is within an Item can move
 * because the Item can move.
 * 
 * @author XP
 *
 */
public class SubRealm extends BasicContainer{
    
	
	private String realm;
	public String getRealm() {
		return realm;
	}
	public void setRealm(String realm) {
		this.realm = realm;
	}
}
