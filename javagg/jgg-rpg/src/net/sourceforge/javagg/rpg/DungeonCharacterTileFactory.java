
package net.sourceforge.javagg.rpg;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class DungeonCharacterTileFactory extends AbstractTileFactory {
    public DungeonCharacterTileFactory(){
        loadTiles();
    }
    public void loadTiles(){
        addTile("characters/fighterd.gif","fi",TRANSPARENT);
        addTile("characters/wizardd.gif","wi",TRANSPARENT);
        addTile("characters/priestd.gif","pr",TRANSPARENT);
        addTile("characters/skeld.gif","sk",TRANSPARENT);
        addTile("characters/spiderd.gif","sp",TRANSPARENT);
        addTile("characters/orcd.gif","or",TRANSPARENT);
        addTile("characters/ratd.gif","ra",TRANSPARENT);
  
    }
}

