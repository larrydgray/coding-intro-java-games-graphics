package net.sf.javagg.mud.data.universe;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * A room is the basic context imidiately within a 
 * PC's view. A PC mainly interacts with only what is
 * within a room. Rooms have exits. Rooms can contain
 * Items and NPC's. A Room can have its own
 * special weather. Rooms can have descriptions that 
 * vary based on time of day, season and weather.
 * A room can be contained by a Realm, SubRealm or Item.
 * If the room is contained by an Item it moves with the Item. 
 * If it is contained by a SubRealm and the SubRealm is 
 * contained by an Item. It will also move when the SubRealm
 * moves.
 * 
 * @author XP
 *
 */
public class Room extends BasicContainer {
	
	
	
    public Room(){
    	
    }
    private String subRealm;
	public String getSubRealm() {
		return subRealm;
	}
	public void setSubRealm(String subRealm) {
		this.subRealm= subRealm;
	}
}
