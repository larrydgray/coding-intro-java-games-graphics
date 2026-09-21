package net.sourceforge.javagg.rpg;

import java.util.*;

/**
 * A model for a Map Code. (The code that is used in the .mp pipe delimited
 * files. And for retrieving map Tiles.
 */
public class MapCode implements Comparable {
	
	/**
	 *
	 */
    MapCode(){
    
    }
    
    /**
     * Constructor that builds this code using and ID, and a Point.
     */
    MapCode(String mc,int x,int y){
    	
        this.setX(x);
        
		this.setY(y);
		
		this.setMapCode(mc);
		
    } // end MapCode constructor
    
    /**
     * For sorting.
     */ 
    public int compareTo(Object o){
    	
        MapCode other = (MapCode)o;
        
        if (this.y<other.y) return -1;
        if (this.y>other.y) return +1;
        if (this.x<other.x) return -1;
        if (this.x>other.x) return +1;
        
        return 0;
        
    }
    
    /**
     * This is the x position on the map for this MapCode.
     * Later we will recode using a Point objects
     */
    int x;
    
    /**
     * This is the y position on the map for this MapCode
     */
    int y;
    
    /** 
     * The mapCode for this map location
     */
    String mapCode;

    /**
     * Gets this mapcodes X map position.
     */    
    int getX(){
    	
        return x;
        
    } // end getX
    
    /**
     * Gets this mapcodes Y map position
     */
    int getY(){
    	
        return y;
        
    } // end getY
    
    /**
     * Gets the map code for this map position.
     */
    String getMapCode(){
    	
        return mapCode;
        
    } // end getMapCode
    
    /**
     * Sets the x position for this map point.
     */
    void setX(int xx){
    	
        this.x=xx;
        
    } // end setX
    
    /**
     * Sets the y position for this map point.
     */
    void setY(int yy){
    	
        this.y=yy;
        
    } // end setY
    
    /**
     * Sets the map code for this map point.
     */
    void setMapCode(String mc){
    	
        this.mapCode=mc;
        
    } // end setMapCode
    
} // end Class MapCode   
