package net.sourceforge.javagg.rpg;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 * Gets Dungeon Tile Images for the objects that have to display maps.
 */
public class DungeonTileFactory extends AbstractTileFactory {

    public DungeonTileFactory(){
    	
       loadTiles();
       
    } // end DungeonTileFactory constructor

    public void loadTiles(){
    	
        addTile("dungeon/wall.gif","wa",SOLID);
        addTile("dungeon/floor.gif","--",SOLID);
        addTile("dungeon/door.gif","dc",TRANSPARENT);
        addTile("dungeon/dooro.gif","do",TRANSPARENT);
        addTile("dungeon/upw.gif","uw",TRANSPARENT);
        addTile("dungeon/upe.gif","ue",TRANSPARENT);
        addTile("dungeon/downe.gif","de",TRANSPARENT);
        addTile("dungeon/downw.gif","dw",TRANSPARENT);
        addTile("dungeon/cofin.gif","cf",TRANSPARENT);
        addTile("dungeon/lamp.gif","la",TRANSPARENT);
        addTile("dungeon/books.gif","bo",TRANSPARENT);
        addTile("dungeon/pain.gif","pa",TRANSPARENT);
        addTile("dungeon/chest.gif","ch",TRANSPARENT);
        addTile("dungeon/chesto.gif","co",TRANSPARENT);
        addTile("dungeon/tools.gif","to",TRANSPARENT);
        addTile("dungeon/nowalk.gif","xx",TRANSPARENT);
        addTile("dungeon/shroud.gif","**",SOLID);
        addTile("dungeon/bounds.gif","##",SOLID);
        
    } // loadTiles
    
} // end class DungeonTileFactory
