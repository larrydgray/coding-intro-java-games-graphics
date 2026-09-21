
package net.sourceforge.javagg.rpg;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class CaveMineTileFactory extends AbstractTileFactory {
    public CaveMineTileFactory(){
        loadTiles();
    }
    public void loadTiles(){
	addTile("cavemine/solid.gif","so",SOLID);
        addTile("cavemine/floor.gif","--",SOLID);
	addTile("cavemine/solid1.gif","s1",SOLID);
        addTile("cavemine/solid2.gif","s2",SOLID);
	addTile("cavemine/solid3.gif","s3",SOLID);
        addTile("cavemine/solid4.gif","s4",SOLID);
        addTile("cavemine/mites.gif","mi",TRANSPARENT);
        addTile("cavemine/water.gif","wa",SOLID);
        addTile("cavemine/water1.gif","w1",TRANSPARENT);
        addTile("cavemine/water2.gif","w2",TRANSPARENT);
        addTile("cavemine/water3.gif","w3",TRANSPARENT);
        addTile("cavemine/water4.gif","w4",TRANSPARENT);
        addTile("cavemine/blocks.gif","bl",TRANSPARENT);
        addTile("cavemine/blocks1.gif","b1",TRANSPARENT);
        addTile("cavemine/blocks2.gif","b2",TRANSPARENT);
        addTile("cavemine/blocks3.gif","b3",TRANSPARENT);
        addTile("cavemine/blocks4.gif","b4",TRANSPARENT);
        addTile("cavemine/vd1.gif","v1",TRANSPARENT);
        addTile("cavemine/vd2.gif","v2",TRANSPARENT);
        addTile("cavemine/vd3.gif","v3",TRANSPARENT);
        addTile("cavemine/vd4.gif","v4",TRANSPARENT);
        addTile("cavemine/vd5.gif","v5",TRANSPARENT);
        addTile("cavemine/vd6.gif","v6",TRANSPARENT);
        addTile("cavemine/vd7.gif","v7",TRANSPARENT);
        addTile("cavemine/vd8.gif","v8",TRANSPARENT);
        addTile("cavemine/slope1.gif","p1",TRANSPARENT);
        addTile("cavemine/slope2.gif","p2",TRANSPARENT);
        addTile("cavemine/slope3.gif","p3",TRANSPARENT);
        addTile("cavemine/slope4.gif","p4",TRANSPARENT);
        addTile("cavemine/trackew.gif","te",TRANSPARENT);
        addTile("cavemine/trackns.gif","tn",TRANSPARENT);
        addTile("cavemine/track1.gif","t1",TRANSPARENT);
        addTile("cavemine/track2.gif","t2",TRANSPARENT);
        addTile("cavemine/track3.gif","t3",TRANSPARENT);
        addTile("cavemine/track4.gif","t4",TRANSPARENT);
        addTile("cavemine/cart.gif","ca",TRANSPARENT);
        addTile("cavemine/shaft.gif","sh",TRANSPARENT);
        addTile("cavemine/upe.gif","ue",TRANSPARENT);
        addTile("cavemine/upw.gif","uw",TRANSPARENT);
        addTile("cavemine/downe.gif","de",TRANSPARENT);
        addTile("cavemine/downw.gif","dw",TRANSPARENT);
        addTile("cavemine/logs.gif","lo",TRANSPARENT);
        addTile("cavemine/piller.gif","pi",TRANSPARENT);
        addTile("cavemine/ore.gif","or",TRANSPARENT);
        addTile("cavemine/nowalk.gif","xx",TRANSPARENT);
        addTile("cavemine/shroud.gif","**",SOLID);
        addTile("cavemine/bounds.gif","##",SOLID);
        addTile("cavemine/tites.gif","ti",TRANSPARENT);
        addTile("cavemine/mitetite.gif","mt",TRANSPARENT);
        addTile("cavemine/column.gif","co",TRANSPARENT);
        addTile("cavemine/guano.gif","gu",TRANSPARENT);
        addTile("cavemine/straws.gif","st",TRANSPARENT);
        addTile("cavemine/gypsim.gif","gy",TRANSPARENT);
        addTile("cavemine/helec.gif","he",TRANSPARENT);
        addTile("cavemine/waters.gif","ws",TRANSPARENT);
        addTile("cavemine/waters1.gif","w5",TRANSPARENT);
        addTile("cavemine/waters2.gif","w6",TRANSPARENT);
        addTile("cavemine/waters3.gif","w7",TRANSPARENT);
        addTile("cavemine/waters4.gif","w8",TRANSPARENT);
        addTile("cavemine/flow.gif","fl",TRANSPARENT);
        addTile("cavemine/flow1.gif","f1",TRANSPARENT);
        addTile("cavemine/flow2.gif","f2",TRANSPARENT);
        addTile("cavemine/flow3.gif","f3",TRANSPARENT);
        addTile("cavemine/flow4.gif","f4",TRANSPARENT);
        addTile("cavemine/flow5.gif","f5",TRANSPARENT);
        addTile("cavemine/flow6.gif","f6",TRANSPARENT);
        addTile("cavemine/flow7.gif","f7",TRANSPARENT);
        addTile("cavemine/flow8.gif","f8",TRANSPARENT);
        addTile("cavemine/lrgblks.gif","l1",TRANSPARENT);
        addTile("cavemine/lrgblks2.gif","l2",TRANSPARENT);
        addTile("cavemine/dome.gif","dm",TRANSPARENT);
        addTile("cavemine/pit.gif","pt",TRANSPARENT);
        addTile("cavemine/vshaft.gif","vs",TRANSPARENT);
    }
}
