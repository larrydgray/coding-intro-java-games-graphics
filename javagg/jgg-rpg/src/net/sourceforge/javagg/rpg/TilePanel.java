
package net.sourceforge.javagg.rpg;
import java.util.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.*;
public class TilePanel extends JPanel {
    public static final int WORLD=1;
    public static final int DUNGEON=2;
    public static final int CAVE=3;
    public static final int MINE=4;
    Graphics tileg;
    TileMap tileMap = new TileMap("net/sourceforge/javagg/rpg/map/map.mp1");
    TileMap overlayMap = new TileMap("net/sourceforge/javagg/rpg/map/map.mp2");
    TileMap overlay2Map = new TileMap("net/sourceforge/javagg/rpg/map/map.mp3");
    TileMap overlay3Map = new TileMap("net/sourceforge/javagg/rpg/map/map.mp4");
    WorldTileFactory worldTileFactory = new WorldTileFactory();
    DungeonTileFactory dungeonTileFactory = new DungeonTileFactory();
    CaveMineTileFactory caveMineTileFactory = new CaveMineTileFactory();
    DungeonCharacterTileFactory dungeonCharacterTileFactory 
                            = new DungeonCharacterTileFactory();
    protected int type = 0;
    public void setType(int t){
        type = t;
    }
    public boolean typeWorld(){ 
        if (type==WORLD) return true;
        else return false;
    }
    public boolean typeDungeon(){ 
        if (type==DUNGEON) return true;
        else return false;
    }
    public boolean typeCaveMine(){ 
        if ((type==CAVE)||(type==MINE)) return true;
        else return false;
    }
    public boolean typeCave(){
        if (type==CAVE) return true; 
        else return false;
    }
    public boolean typeMine(){ 
        if (type==MINE) return true; 
        else return false;
    }

    public void reload(){ 
        setTileMaps();
	Graphics g = this.getGraphics();
	//g=g.create();
	//g.clearRect(0,0,800,600);
	
        //drawLayer(tileMap,g);
	//drawLayer(overlayMap,g);
	//drawLayer(overlay2Map,g);
	//this.setOpaque(false);
	//this.paint(g);
	//this.update(g);
	this.paintComponent(g);
	//this.update(g);
	//this.paintImmediately(0,0,800,600);
	//this.revalidate();
    }
    MouseAdapter ma;
    TilePanel(int t){
        //System.out.println("TilePanel:"+isDoubleBuffered());        
        type=t;
        setTileMaps();
        //this.setDebugGraphicsOptions(DebugGraphics.FLASH_OPTION);
        ma = new MouseAdapter(){ 
            public void mouseClicked(MouseEvent e){
                reload();
            }
        };
        this.addMouseListener(ma);
    }
    public void setTileMaps(){
        
        if (typeWorld()){
            tileMap = new TileMap("net/sourceforge/javagg/rpg/map/map.mp1");
            overlayMap = new TileMap("net/sourceforge/javagg/rpg/map/map.mp2");
            overlay2Map = new TileMap("net/sourceforge/javagg/rpg/map/map.mp3");
            overlay3Map = new TileMap("net/sourceforge/javagg/rpg/map/map.mp4");
        }
        if (typeDungeon()){
            tileMap = new TileMap("net/sourceforge/javagg/rpg/map/dun.mp1");
            overlayMap = new TileMap("net/sourceforge/javagg/rpg/map/dun.mp2");
            overlay2Map = new TileMap("net/sourceforge/javagg/rpg/map/dun.mp3");
            overlay3Map = new TileMap("net/sourceforge/javagg/rpg/map/dun.mp4");
        }
        if (typeCave()){
            tileMap = new TileMap("net/sourceforge/javagg/rpg/map/cav.mp1");
            overlayMap = new TileMap("net/sourceforge/javagg/rpg/map/cav.mp2");
            overlay2Map = new TileMap("net/sourceforge/javagg/rpg/map/cav.mp3");
            overlay3Map = new TileMap("net/sourceforge/javagg/rpg/map/cav.mp4");
        }
        if (typeMine()){
            tileMap = new TileMap("net/sourceforge/javagg/rpg/map/min.mp1");
            overlayMap = new TileMap("net/sourceforge/javagg/rpg/map/min.mp2");
            overlay2Map = new TileMap("net/sourceforge/javagg/rpg/map/min.mp3");
            overlay3Map = new TileMap("net/sourceforge/javagg/rpg/map/min.mp4");
        }
    }
    public void paintComponent(Graphics g){
        BufferedImage bi = new BufferedImage(800,600,1);
		Graphics g2 = bi.getGraphics();
        g2.setPaintMode();
        drawLayer(tileMap,g2);
		drawLayer(overlayMap,g2);
		drawLayer(overlay2Map,g2);
		drawLayer(overlay3Map,g2);
		g.drawImage(bi.getScaledInstance(800,600,Image.SCALE_REPLICATE),0,0,null);
    }
    public void drawLayer(TileMap tileMap,Graphics g){
        ListIterator mapIterator = tileMap.listIterator();
        while(mapIterator.hasNext()){
            MapCode mapCodeObject = (MapCode)mapIterator.next();
            int y= mapCodeObject.getY();
            int x= mapCodeObject.getX();
            String mapCode = mapCodeObject.getMapCode();
            Tile mapTile=this.getTile(mapCode);
            /** Do not remove this comment.
	         *  If java does not find the image file with the
	         *  drawImage method no exceptions are thrown 
	         *  as in FileNotFound.. simple nothing is
	         *  drawn. This will drive you nuts.
	         *  I had put all images in zip files for the
	         *  repository and forgotten about it..
	         *  so for hours I had a blank screen
	         *  when of course something should have
	         *  been drawn.
	         *  unzipping the .zip files fixed 
	         *  the bug.
	         */
	    if (!mapCode.equals("  "))
                if (mapTile!=null) 
	            g.drawImage(mapTile.getImage(),x*20,y*20,null);
        }   
    }
     
    public Tile getTile(String mapCode){
        Tile tempTile  = null;
        if(typeWorld()) return worldTileFactory.getTile(mapCode);
        if(typeDungeon()) {
            tempTile = dungeonTileFactory.getTile(mapCode);
	    if (tempTile!=null)
	        return tempTile;
	    else    
            return dungeonCharacterTileFactory.getTile(mapCode);
        }    
	
	if(typeCaveMine()) return caveMineTileFactory.getTile(mapCode);
		
		return null;
    
    }	    

}
