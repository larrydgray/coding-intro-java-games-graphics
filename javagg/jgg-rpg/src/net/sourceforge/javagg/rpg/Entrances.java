package net.sourceforge.javagg.rpg;

import java.util.*;
/**
 * This is a set of Entrances. This RPG will keep 3 such sets.
 * Each Map may have a set. Each Portal May have a set. And the Universe will
 * have a very large set.
 */
public class Entrances extends HashSet{
    private Universe universe;
    public Entrances(Universe universe){
    	this.universe=universe;
	}
    /**
     * Keeping a set of Entrances.
     */	
    public void addEntrance(Entrance entrance){
  	
  		((HashSet)this).add(entrance);
  	
    }// end addEntrance method
  
  	public Entrance getEntrance(int level){
  	
  		Iterator iterator = super.iterator();
  	
  		Entrance entrance;
  	
  		while (iterator.hasNext()){
  	
  			entrance = (Entrance)iterator.next();
  		
  			if (entrance.level==level) return entrance;
  		
  		} // end while
  		
  		System.out.println("Entrances.getEntrance is returning null!");
  		
  		return null;
  		
	} // end getEntrance

} // end class Entrances
