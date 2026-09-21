package net.sourceforge.javagg.rpg;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 * This is the parrent class of all Tile Factories. 
 * Tile factories are HardCoded classes.
 */
public abstract class AbstractTileFactory {
	
    ArrayList tiles = new ArrayList();
    
    public static final boolean TRANSPARENT=true;
    
    public static final boolean SOLID=false;
    
    /**
     *
     */
    public AbstractTileFactory(){

    }
    
    /**
     * Loads tiles from disk.
     */
    public abstract void loadTiles();
    
    /**
     * Adds a tile given filename(for the image), mapcode for this tile and sets
     * it to SOLID or TRANSPARENT.
     */
    public void addTile(String filename,String mapCode,boolean transparency){

        Tile tile = new Tile();
        
        String name = filename;
        
        tile.setName(name);

        // Icon icnNeedsSave = new ImageIcon(getClass().getResource("/blah.gif"));
        System.out.println(filename);
        Image image = new ImageIcon(getClass().getResource(filename)).getImage();
        
        tile.setImage(image);
        
        if (transparency) tile.setTransparent();
        
        tile.setMapCode(mapCode); // two char map code read from text files
        
        this.tiles.add(tile);

    } // end addTile method
    
    /**
     * Gets the Tile object associated with the given mapcode.
     */    
    public Tile getTile(String mapCode){ 
        
        ListIterator tilesIterator = this.tiles.listIterator();
        
        Tile tempTile;
    
        while(tilesIterator.hasNext()){
            tempTile = (Tile)tilesIterator.next();
	
	     if (mapCode.equals(tempTile.getMapCode())) return tempTile;
        
        } // end while
        
        return null;
    
    } // end getTile method

} // end Class AbstractTileFactory
