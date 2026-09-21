/*
 * Created on Jun 8, 2004
 *
 * 
 */
package net.sf.javagg.gsc;

import java.util.Iterator;
import java.util.List;

import sourceforge.jgg.gsc.debug.StateViewer;

import org.jdom2.Element;


/**
 * This object will wrap an Array of Arrays of MapCell objects. This is so that
 * a TileMapLayer may be of any great size and a TileMap may draw a view of
 * itself by getting random access to its data.
 * 
 * @author Larry Gray
 * @version 1.2
 */
public class TileMapLayer {
	
	
	/** A map of the layer using MapCodes */
	private String[][] map = new String[8][8];

	/** Map Layer Name */
	private String mapLayerName;

	public String getMapLayerName() {
		return mapLayerName;
	}
	public void setMapLayerName(String mapLayerName) {
		this.mapLayerName = mapLayerName;
	}

	/** Reference to the Element that contains the rows of the layer in the loaded xml dom.*/
	Element mapLayerElement;
	/**
	 * 
	 */
    public TileMapLayer(int x, int y){
    	map = new String[y][x];
    }
	/**
	 * Makes a tile map layer using xml element for the layer and of a given
	 * dimension.  
	 * @param layer
	 * @param cols
	 * @param rows
	 */
	public TileMapLayer(Element layer, int cols, int rows) {
		
		this.mapLayerName=layer.getAttributeValue("name");
		map = new String[rows][cols];
		loadMapLayer(layer);
		this.mapLayerElement=layer;
	}

	/**
	 * Returns the map code.
	 * 
	 * @param x
	 * @param y
	 * @return map[x][y]
	 * @see gsc.TileMapLayer#getMapCode(int, int)
	 */
	public String getMapCode(int x, int y) {
		//System.out.println("cols:"+map[0].length+" rows:"+
		//		map.length);
		return map[y][x];
	} // getMapCode()
	
	
	/**
	 * Gets a view of the map which is a rectangular area of tile squares.
	 * 
	 * @param x
	 *            starting column.
	 * @param y
	 *            starting row.
	 * @param width
	 *            in number of tiles.
	 * @param height
	 *            in number of tiles.
	 * @see gsc.TileMapLayer#getView(int, int, int, int)
	 */
	public TileMapLayer getView(int x, int y, int width, int height) {
		TileMapLayer temp = new TileMapLayer(width,height);
		for (int xx = x; xx < x + width; xx++) {
			for (int yy = y; yy < y + height; yy++) {
				String tempCode = this.getMapCode(xx, yy);
				temp.setMapCode(tempCode, xx - x, yy - y);
			} // inner for
		} // outer for

		return temp;
	} // getView()

	/**
	 *  Uses the xml dom Element to record the layer data into from the arrays. Then the TileMap class will
	 *  write all layers at once to disk. The Element object is the same one that was created when loading the
	 *  file from disk.
	 */
    public void saveMapLayer(){
    	String rowString="";
    	StateViewer viewer = new StateViewer();
		viewer.init2();
		String s="";
    	for (int row=0;row<map.length;row++){
    		rowString+="|";
    		for (int col=0;col<map[0].length;col++){
    			s=map[row][col];
    			rowString+=""+s+"|";
    		} // for
    		Iterator<Element> rowIterator = mapLayerElement.getChildren("row").iterator();
    		int n=0;
    		
    		viewer.append("row "+row+"["+rowString+"]\n");
    		while(rowIterator.hasNext()){
    			
    			Element rowElement =rowIterator.next();
    			//viewer.append("row "+row+"["+rowString+"]\n");
    			try{
    			viewer.append("rowElement"+rowElement.getAttributes().size()+" "+rowElement.getAttributes());
    			String numS=rowElement.getAttributeValue("number");
    			
    			viewer.append("number="+numS+" len:"+numS.length());
    			if(Integer.parseInt(numS)==row){
    			    rowElement.setText(rowString);
    			    viewer.append(mapLayerName+"row number"+numS+rowString+"\n");
    		    } // if
    			}catch(NumberFormatException nfe){
    				viewer.append("Number Format Exception on row:"+n+"\n");
    			}
    			n++;
    		} // while
    		
    		rowString="";
    		
    	} // for
    } // saveMapLayer
    
	/**
	 * Loads the Map Layer.
	 * 
	 * @param layername
	 * @see gsc.TileMapLayer#loadMapLayer(java.lang.String)
	 */
	public void loadMapLayer(Element layer) {
		    
    		
    		List rows = layer.getChildren("row");
    		Element row = null;
    		for(int i=0;i<rows.size();i++){
    			row=(Element)rows.get(i);
    			//System.out.println("row num:"+row.getAttributeValue("number"));
    			String rowString=row.getText();
    			String[] rowCells=rowString.split("\\|");
    			//System.out.print(""+i+":");
    			//for(int a=0;a<rowCells.length;a++){
    				//System.out.print("["+rowCells[a]+"]");
    			//}
    			//System.out.println();
    			//System.out.println((rowCells.length-1)+" 0:"+rowCells[0].length()+" "+(rowCells.length-1)+":"+rowCells[rowCells.length-1]);
    			//*********************/
    			String[] rowCells2= new String[rowCells.length-1];
    			for(int a=0;a<rowCells.length-1;a++){
    				rowCells2[a]=rowCells[a+1];
    			}
    			map[i]=rowCells2;
    		}
    		
    	  
	} // loadMapLayer(String layerName)

	/**
	 * Sets the map code.
	 * 
	 * @param x
	 * @param y
	 * @see gsc.TileMapLayer#setMapCode(java.lang.String, int, int)
	 */
	public void setMapCode(String mapCode, int x, int y) {
		map[y][x] = mapCode;

	} // setMapCode()
	
	public String toString(){
		return mapLayerName;
	}
} // TileMapLayer Abstract Class
