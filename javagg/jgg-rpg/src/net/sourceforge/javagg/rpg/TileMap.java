package net.sourceforge.javagg.rpg;

import java.io.*;
import java.util.*;
import javax.swing.*;
/**
 *  This is a set of Tiles used for a given map layer.
 */
public class TileMap extends ArrayList implements Comparable {
    
    /**
     * Which layer in a may is this TileMap?
     */
    private int layerNumber;
    
    /**
     * TileMaps are loaded from .mp files
     */
    protected String filename="";
    
    /**
     * Constructor that takes file name only.
     */
    TileMap(String fn){
    	
    	super();
    	
    	filename=fn;
    	
    	loadMap(); 
    	
    	System.out.println("TileMapSize:"+this.size());
    	
    } // end constructor TileMap(String)
    
     /**
     * Constructor sets layer number 1 to 3 or more and file name.
     */       
    TileMap(int layerNumber, String fn){
    	
    	super();
    	
    	this.setLayerNumber(layerNumber);
    	
    	filename=fn;
    	
    	loadMap(); 
    	
    	System.out.println("TileMapSize:"+this.size());
    	
    } // end constructor TileMap(int,String)
    
    /**
     * This constructor builds a submap, or subset of tiles.
     */
    TileMap(TileMap tileMap,int x1,int y1, int x2, int y2){
    	
    	   this.setLayerNumber(tileMap.getLayerNumber());
    	   
           int count=0; // debuging
    
           Iterator tileIterator = tileMap.iterator();

           MapCode mapCode;
           
           while(tileIterator.hasNext()){
           	    //System.out.println("now building submap");
           	    
           	    mapCode = (MapCode)tileIterator.next();
           	    
           	    if (((mapCode.getX()>=x1)
           	      &(mapCode.getX()<x2))
           	      &((mapCode.getY()>=y1)
           	      &(mapCode.getY()<y2))){

           	    	this.add(mapCode);

           	    	//System.out.println("building submap");

           	    count++; // debugging

           		} // end if

           } // end while
           
           //System.out.println("TileMap:TileMapConstructor:MapCodes:"+count); // debugging
	
	} // end constructor TileMap(Map,int,int,int,int)
	
    /**
     * TileMaps may be sorted.
     */
    public int compareTo(Object o){
      
      if(this.layerNumber>((TileMap)o).layerNumber)return 1;
      
      if(this.layerNumber<((TileMap)o).layerNumber)return -1;
      
      else return 0;
    
    } 
    
   
    
    
    
    // a subMap will be a wrapper for
    // the parrent map.
    
    
    
    /**
     * Sets the layer number.
     */
    private void setLayerNumber(int layerNumber){
    	
    	this.layerNumber=layerNumber;
    	
	} // end setLayerNumber method
	
	/**
	 * Gets the layer number.
	 */
	public int getLayerNumber(){
		
		return this.layerNumber;
		
	} // end getLayerNumber method	
    
    
               	      
    /**
     * A Debugging method
     */
    public void myassert(boolean isTrue,String message){
    	
    	if (isTrue);
    		//empty! do nothing!
    	else // else its false	
    		JOptionPane.showMessageDialog(MapWalkerUI.frame,message);
    
    } // debug method myassert
 	           	    	
    /**
     * loads the TileMap from a disk file.
     */	
    public void loadMap() { 
        
        try{
        	
            InputStreamReader isr = new InputStreamReader(getClass().getResourceAsStream(this.filename));
            
            BufferedReader in = new BufferedReader(isr);
            
            String row;
            
            int y=0;
            
            while((row=in.readLine())!=null){
            	
                StringTokenizer st = new StringTokenizer(row,"|");
                
	        	int x=0;
	        	
	        	while(st.hasMoreTokens()){
	        		
	            	String token = st.nextToken();
	            	
	            	this.add(new MapCode(token,x,y));
                    
                    x++;
                    
	        	} // end while 
	        	
	        y++;
	        
            } // end while
            
        } // end try  input streams
        catch(IOException e){
        	
        	System.out.println(""+e);
        
        }// end catch
        
    }// end method loadMap
    
    /**
     * saves a map to a disk file for use with map editor
     */
    public void saveMap(){
    	
        this.sort();
        
        try{
        	
            FileWriter out = new FileWriter("map.dat");
            
            MapCode mapCode;
            
            int prevY=0;
            
            ListIterator thisIterator = this.listIterator();
            
            while(thisIterator.hasNext()){
            	
                mapCode = (MapCode)thisIterator.next();
                
                out.write(mapCode.getMapCode()+"|");
                
	        	if (mapCode.y!=prevY) out.write("\r\n"); 
	        	
	        	prevY=mapCode.y;
	        	
            } // end while
            
        } // end try
        catch(IOException e){
        
        } // end try
    
    } // end method saveMap
    /**
     * A tilemap mapcode sorting method.
     */
    public void sort(){
    	
        MapCode[] mapCodes = (MapCode[])this.toArray();
        
        Arrays.sort(mapCodes);
        
        this.clear();
        
        for (int i=0; i<mapCodes.length; i++) this.add(mapCodes[i]);
        
    } // end sort
    
} // end Class TileMap
 
