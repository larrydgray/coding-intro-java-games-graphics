package net.sf.javagg.mud.data.universe;

import java.util.Iterator;

/**
 * A container is any game world object which 
 * wraps any other conainer objects or objects, so
 * that the entire context can be sets of containers
 * the PC can move in containers by changing the containers.
 * Items can move around by changing their containers.
 * 
 * For example a World can contain a Realm which contains
 * a SubRealm which contains a Room which contains a 
 * PC,NPC,Item   Items can contain SubRealms a Single Room or Items.
 * A PC can contain Items and possibly NPC.  NPC can contain
 * items and possibly NPC.
 * An entire game is wrapped around the PC object.
 * Anything within the tree around the PC is kindof within
 * the PC's context or view.
 * @author XP
 *
 */
public interface Container {
     public String getName();
     public void setName(String name);
     public String getID();
     public void setID(String id);
     public String getDescription();
     public void setDescription(String description);
     public void addExit(Exit exit);
     public Iterator<Exit> getExits();
     public String getWeather();
     public void setWeather(String weather);
     
}
