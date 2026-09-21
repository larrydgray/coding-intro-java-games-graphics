package net.sourceforge.javagg.rpg;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
/**
 * This is a hardcoded factory for World Map Tiles
 */
public class WorldTileFactory extends AbstractTileFactory {
	
	
	
	/**
	 * Builds a World Tile Factory
	 */
    public WorldTileFactory(){
        loadTiles();
    }
    
    /**
     * Loads The Tiles that this Factory uses
     */
    public void loadTiles(){
    	
        addTile("world/arid.gif","ar",SOLID);
        addTile("world/city.gif","ci",TRANSPARENT);
        addTile("world/desert.gif","de",SOLID);
        addTile("world/forest.gif","fo",SOLID);
        addTile("world/grass.gif","gr",SOLID);
        addTile("world/hills.gif","hi",TRANSPARENT);
        addTile("world/mount.gif","mo",TRANSPARENT);
        addTile("world/riverns.gif","rn",TRANSPARENT);
        addTile("world/riverew.gif","re",TRANSPARENT);
        addTile("world/riversw.gif","r1",TRANSPARENT);
        addTile("world/rivernw.gif","r2",TRANSPARENT);
        addTile("world/riverne.gif","r3",TRANSPARENT);
        addTile("world/riverse.gif","r4",TRANSPARENT);
        addTile("world/swamp.gif","sw",TRANSPARENT);
        addTile("world/water.gif","wa",TRANSPARENT);
        addTile("world/waters.gif","w1",TRANSPARENT);
        addTile("world/watersw.gif","w2",TRANSPARENT);
        addTile("world/waterw.gif","w3",TRANSPARENT);
        addTile("world/waternw.gif","w4",TRANSPARENT);
        addTile("world/watern.gif","w5",TRANSPARENT);
        addTile("world/waterne.gif","w6",TRANSPARENT);
        addTile("world/watere.gif","w7",TRANSPARENT);
        addTile("world/waterse.gif","w8",TRANSPARENT);
        addTile("world/roadns.gif","hn",TRANSPARENT);
        addTile("world/roadew.gif","he",TRANSPARENT);
        addTile("world/roadne.gif","h1",TRANSPARENT);
        addTile("world/roadse.gif","h2",TRANSPARENT);
        addTile("world/roadsw.gif","h3",TRANSPARENT);
        addTile("world/roadnw.gif","h4",TRANSPARENT);
        addTile("world/mine.gif","mi",TRANSPARENT);
        addTile("world/cave.gif","ca",TRANSPARENT);
        addTile("world/dungeon.gif","du",TRANSPARENT);
        addTile("world/riverewb.gif","rw",TRANSPARENT);
        addTile("world/rivernsb.gif","rs",TRANSPARENT);
        addTile("world/nowalk.gif","xx",TRANSPARENT);
        addTile("world/shroud.gif","**",SOLID);
        addTile("world/bounds.gif","##",SOLID);
    } // end loadTiles
    
} // end Class WorldTileFactory





