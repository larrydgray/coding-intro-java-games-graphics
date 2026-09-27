
package net.sourceforge.javagg.rpg;

import java.util.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.*;

/**
 * This is the panel for the map walker demo. Here is were we pan the map and
 * move through portals on the map.
 */
public class GameWalkPanel extends JPanel {

    /**
     *  View of map.
     */
    public Map mapView;
    
    /**
     * Entire Map
     */
    public Map map;
    
    /**
     * Mouse Adapter
     */
    MouseAdapter ma;
    
    /**
     * A reference to all the maps in this universe.
     */
    private Maps universeOfMaps;
    
    /**
     * The center point on the maps for centering the view.
     */
    Point position = new Point(5,5);
    
    /**
     * Produces Tile objects for the world map.
     */
    WorldTileFactory worldTileFactory;
    /**
     * Produces Tile objects for the dungeon map.
     */     
    DungeonTileFactory dungeonTileFactory;
    /**
     * Produces Tile objects for the cave/mine maps.
     */     
    CaveMineTileFactory caveMineTileFactory;
    
    /**
     * Produces Tile objects for the NPC,PC characters.
     */     
    DungeonCharacterTileFactory dungeonCharacterTileFactory;
    
    /**
     * The type of map currently being displayed.
     */
    protected int type = 0;

    /**
     *  Constructor that sets the type of map being viewed and
     *  adds a mouseadapter that reloads the map on mouse click.
     *
     */ 	
    GameWalkPanel(){
    	
    	worldTileFactory = 
         new WorldTileFactory();

	    dungeonTileFactory = 
         new DungeonTileFactory();

    	caveMineTileFactory = 
         new CaveMineTileFactory();

    	dungeonCharacterTileFactory = 
         new DungeonCharacterTileFactory();
    	
    	try{
    		Thread.currentThread().sleep(1000);
	    }
	    catch(InterruptedException ex){
	       	ex.printStackTrace();
	    }	 
        
        ma = new MouseAdapter(){ 
            
            public void mouseClicked(MouseEvent e){
                
                reload();
            
            }
        
        };
        
        this.addMouseListener(ma);
        
    } // end constructor GameWalkPanel()

    /**
     * Constructor which takes map and a set of maps as
     * its paramters.
     */
    GameWalkPanel(int type,Map map,Maps maps){
    			this();
    		
    	this.setType(type);
    	
    	universeOfMaps=maps;
    	
    	this.map=map;
    	
    	this.mapView=map;
    	
    } // end constructor GameWalkPanel(Map,Maps)
    

    /**
     *
     */
    public boolean isValidateRoot(){

    	return true;

	}

    /**
     * Used to set the type of map currently being displayed.
     */
    public void setType(int t){
    	
        type = t;
        
    } // end setType method
    
    /**
     * Is the current map being viewed of type World?
     */
    public boolean typeWorld(){ 
    
        if (type==Map.WORLD) return true;
        
        else return false;
        
    } // end typeWorld method
    
    /**
     *  Is the current map being viewed of type Dungeon?
     */
    public boolean typeDungeon(){ 
    
        if (type==Map.DUNGEON) return true;
        
        else return false;
        
    } // end typeDungeon
    
    /**
     *  Is the current map being viewed of type CaveMine?
     */
    public boolean typeCaveMine(){ 
    
        if ((type==Map.CAVE)||(type==Map.MINE)) return true;
        
        else return false;
        
    } // end typeCaveMine
    
    /**
     * Is the current map being viewed of type Cave?
     */
    public boolean typeCave(){
    	
        if (type==Map.CAVE) return true; 
        
        else return false;
        
    } // end typeCave
    
    /**
     * Is the current map being viewed of type Mine?
     */
    public boolean typeMine(){ 
    
        if (type==Map.MINE) return true; 
        
        else return false;
        
    } // end typeMine
    
    /**
     * reloads the panel with the given map object.
     */
    public void reload(Map map){
    	
    	this.map = map;
    	
    	reload();
    	
    } // end reload(Map)
    
    /**
     * reloads this panel.
     */
    public void reload(){
    	
    	removeShroud();
    	
		Graphics g = this.getGraphics();
	
		paintComponent(g);
		
	
	} // end reload()

	/**
	 *
	 */
	public Dimension getPreferredSize(){

		// Was 200,200 - stale relative to the 400x400 image this panel
		// actually paints even before doubling it for modern screens; see
		// the matching scale-up in paintComponent below.
	 	return new Dimension(800,800);

	}

	/**
	 * Sets the viewport position for current view.
	 */
	public void setPosition(Point p){
		
		position=p;
		
	} // end setPosition
    
    
    
    /**
     * This changes the map for the 
     * portals.
     *
     */
    public void changeMap(int type,Map map,Point position){
    	
        this.setType(type);
        
        this.map=map;
        
        this.mapView=map;
        
        this.position=position;
        
        this.reload();
       
    	
    } 
    
    
    /**
     * I'm not sure If this is used or where.
     */
    public void setMapView(net.sourceforge.javagg.rpg.Map map){
    	
    	this.mapView = map;
    	
    	
    } // end setMap(Map)
    
    /**
     * Sets the portion of the map to be displayed in the
     * panel.
     */
    public void setMapView(int x1, int y1, int x2, int y2){
        
        net.sourceforge.javagg.rpg.Map map = 
           	(this.mapView).getSubMap((this.mapView).getMainMap(),x1, y1, x2, y2);
        
        if(map==null){
        	System.out.println("Error Map of setMapView  null!");
        	System.exit(0);
    	}
    	
    	this.mapView = map;
    	
    	reload();
	
	} // end setMapView(int,int,int,int)
    
    /**
     * This is where map painting effort begins that pains the 
     * current view of the map.
     */
    public void paintComponent(Graphics g){
        
        BufferedImage bi = new BufferedImage(400,400,1);
		
		Graphics g2 = bi.getGraphics();
        
        g2.setPaintMode();
        
        Object tileMapLevelObjects[] = (this.map).toArray();
        
        int numberLevels = tileMapLevelObjects.length; 
        
        // I use arrays for the sorting of the levels
        TileMap tileMapLevels[] = new TileMap[numberLevels];
        
        // objects converted to TileMaps
        for (int i=0;i<numberLevels;i++){
        	
        	System.out.println("bounds:"+i);
        	
        	tileMapLevels[i] = (TileMap)tileMapLevelObjects[i];
        	
    	}
        	
        // the maps must be drawing in oder of bottom to top.	
        Arrays.sort(tileMapLevels);
        
        for(int i=0;i<numberLevels;i++){
        	
        	System.out.println("Plotting Level "+((TileMap)tileMapLevels[i]).getLayerNumber());
        	
        	g2=drawLayer((TileMap)tileMapLevels[i],g2);
        	
        }	
        
    	
    	//System.out.println("Number of layers:"+layerCount);
		
		// Doubled from the original 400x400 (this map was drawn for late-90s/
        // early-2000s screen resolutions). SCALE_REPLICATE keeps the tile art
        // crisp/blocky on upscale instead of blurring it.
        g.drawImage(bi.getScaledInstance(800,800,Image.SCALE_REPLICATE),0,0,null);

 		try{
	       	Thread.currentThread().sleep(50);
	    }
	    catch(InterruptedException ex){
	       	ex.printStackTrace();
	    }	    	

    } // end paintComponent
    
    public boolean walk(int x,int y){
        
        TileMap tileMap=null;
        
        Iterator mapIterator = this.map.iterator();
        while(mapIterator.hasNext()){
        	tileMap=(TileMap)mapIterator.next();
        	if(tileMap.getLayerNumber()==4)break;
        }
        
        Iterator tileMapIterator = tileMap.iterator();
        MapCode mapCode=null;
        
        while(tileMapIterator.hasNext()){
        	mapCode=(MapCode)tileMapIterator.next();
            if (mapCode.getX()==x)
              if(mapCode.getY()==y)
                if((mapCode.getMapCode()).equals("xx")){ 
                   return false;
                }
        
        }	

        return true;
	}
	public void removeShroud(){
        
        TileMap tileMap=null;
        
        Iterator mapIterator = this.map.iterator();
        
        while(mapIterator.hasNext()){
        
        	tileMap=(TileMap)mapIterator.next();
        
        	if(tileMap.getLayerNumber()==5)break;
        
        }
        
        Iterator tileMapIterator = tileMap.iterator();
        
        MapCode mapCode=null;
        
        while(tileMapIterator.hasNext()){
        	
        	int x=(int)this.position.getX();
        	int y=(int)this.position.getY();
        	
        	
        	mapCode=(MapCode)tileMapIterator.next();
            if (mapCode.getX()==x)
              if(mapCode.getY()==y)
                if ((mapCode.getMapCode()).equals("**"))
                     mapCode.setMapCode("  "); 
            if(mapCode.getX()==x-1)
              if(mapCode.getY()==y-1)
            	  if ((mapCode.getMapCode()).equals("**"))
                      mapCode.setMapCode("  "); 
            if(mapCode.getX()==x-1)
              if(mapCode.getY()==y)
            	  if ((mapCode.getMapCode()).equals("**"))
                      mapCode.setMapCode("  "); 
            if(mapCode.getX()==x-1)
              if(mapCode.getY()==y+1)
            	  if ((mapCode.getMapCode()).equals("**"))
                      mapCode.setMapCode("  "); 
            if(mapCode.getX()==x)
              if(mapCode.getY()==y+1)
            	  if ((mapCode.getMapCode()).equals("**"))
                      mapCode.setMapCode("  "); 
            if(mapCode.getX()==x+1)
              if(mapCode.getY()==y+1)
            	  if ((mapCode.getMapCode()).equals("**"))
                      mapCode.setMapCode("  "); 
            if(mapCode.getX()==x+1)
              if(mapCode.getY()==y)
            	  if ((mapCode.getMapCode()).equals("**"))
                      mapCode.setMapCode("  "); 
            if(mapCode.getX()==x+1)
              if(mapCode.getY()==y-1)
            	  if ((mapCode.getMapCode()).equals("**"))
                      mapCode.setMapCode("  "); 
            if(mapCode.getX()==x)
              if(mapCode.getY()==y-1)
            	  if ((mapCode.getMapCode()).equals("**"))
                      mapCode.setMapCode("  "); 
        
        }	

	}
    
    public Graphics drawLayer(TileMap tileMap,Graphics g){
    	if (tileMap.getLayerNumber()==4) return g;
        int count=0;
        
        ListIterator mapIterator = tileMap.listIterator();
        	
        	int x1=(int)position.getX()-5;
			
			int x2=(int)position.getX()+5;
			
			int y1=(int)position.getY()-5;
			
			int y2=(int)position.getY()+5;
			
        
        
        while(mapIterator.hasNext()){
            
        
            MapCode mapCodeObject = (MapCode)mapIterator.next();
        
            int y = mapCodeObject.getY()-(int)position.getY();
        
            int x = mapCodeObject.getX()-(int)position.getX();
        
            String mapCode = mapCodeObject.getMapCode();
        
            Tile mapTile=this.getTile(mapCode);
            
            g.setColor(Color.white);
            
		    if (!mapCode.equals("  "))
                if (mapTile!=null) {
                
                if (((mapCodeObject.getX()>=x1)
           	      &(mapCodeObject.getX()<x2))
           	      &((mapCodeObject.getY()>=y1)
           	      &(mapCodeObject.getY()<y2))){	
                
                count++;	

                	///System.out.println("MapCode:"+mapCode+":");

                	//System.out.println("ImageInfo"+mapTile.getImage());

	            // Tile size doubled 20->40 to fill the 400x400 buffer
	            // properly (it only used to fill about a quarter of it).
	            // Offset kept as 4 times tileSize, same ratio the original
	            // 20px version used, so layout/centering is unchanged, just
	            // bigger, matching the cursor rect below.
	            g.drawImage(mapTile.getImage(),x*40+(4*40),y*40+(4*40),40,40,null);

	            /*  Do not remove this comment.
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
	            //g.setColor(Color.white);
	            //g.drawLine(x*20,y*20,x*20+20,y*20+20);
	            //System.out.println("MapTileDrawn"+count++);
	       		}
	         } // end if mapTile not null
        
        }// end if not mapCode equal to "  "
        
        // draws a cursor(to simulate character position in middle of map)
        g.drawRect(4*40,4*40,19,19);
        
       System.out.println("GameWalkPanel:drawLayer:tilecount:"+count); 
		
       return g;   
       
    } // end drawLayer method
    
    /**
     * This gets a single tile based on a mapCode.
     */
    public Tile getTile(String mapCode){
    	
        Tile tempTile  = null;
        
        if(typeWorld()) 
            return worldTileFactory.getTile(mapCode);
            
        if(typeDungeon()) {
        	
            tempTile = dungeonTileFactory.getTile(mapCode);
            
	    	if (tempTile!=null) return tempTile;
	    	
	        else return dungeonCharacterTileFactory.getTile(
	        	        mapCode);
	        	        
        } // end if
            
		if(typeCaveMine()) return caveMineTileFactory.getTile(mapCode);
		
		System.out.println("No type!");

		System.exit(0);

		return null;
		
    } // end getTile
    
    /**
     * A Debugging method
     */
    public void myassert(boolean isTrue,String message){
    	
    	if (isTrue);
    		//empty! do nothing!
    	else // else its false	
    		JOptionPane.showMessageDialog(MapWalkerUI.frame,message);
    
    } // debug method myassert
 	    
    
} // end GameWalkPanel
