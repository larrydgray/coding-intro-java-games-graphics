package net.sf.javagg.island3d;

import java.awt.Color;

/**
 * Models the data needed for all the points of elevation on a isometric 3d mesh
 * surface.
 * 
 * @author Larry Gray
 * @version 1.1
 */
public class MeshSurfaceModel {
	/** A default set of elevation color models. */
	private static ElevationColorModel[] colorModel = null; // end array

	// definition
	// colorModel

	/** Default elevation data for a small surface for initial display. */

	private int elevationData[][] = { { 1, 0, 5, 15, 5, 2 },
			{ 2, 0, 5, 5, 5, 4 }, { 3, 0, 0, 0, 0, 6 }, { 10, 0, 0, 0, 0, 5 },
			{ 6, 0, 0, 0, 0, 5 }, { 3, 0, 0, 12, 0, 2 } }; // end array

	// definition
	// elevationData

	/** Number of elevation colors. */
	private int numberElevations = 8;

	/**
	 * gets a color for a given elevation.
	 * 
	 * @param elevation
	 *            to check against range of elevations.
	 * 
	 * @return Color the color for an elevation.
	 */
	public Color getColor(int elevation) {

		for (int i = 0; i < this.numberElevations; i++) {

			Color tempColor;
			tempColor = colorModel[i].getColor(elevation);
			if (tempColor != null)
				return tempColor;

		} // for
		// debug
		//System.out.println("Error! Returning null for color");
		//System.exit(0);
		return Color.white;
	} // getColor

	/**
	 * Gets an elevation from a given location.
	 * 
	 * @param x
	 *            grid intersection.
	 * @param y
	 *            grid intersection.
	 * @return int elevation.
	 */
	public int getElevation(int x, int y) {
		return elevationData[y-1][x-1];
	} // getElevation

	/**
	 * Makes a random surface. NOTE: This method may not be used.
	 */
	public void makeRandomSurface() {
		int[][] islandSurface;
		MapFrame mapFrame = new MapFrame();
		islandSurface = mapFrame.getMap();

	} // makeRandomSurface

	/**
	 * Sets a value for an elevation at a given grid intersection.
	 * 
	 * @param x
	 *            grid intersection.
	 * @param y
	 *            grid intersection.
	 * @param value
	 *            elevation.
	 */
	public void setElevation(int x, int y, int value) {
		elevationData[y][x] = value;
	} // setElevation

	/**
	 * Sets the entire set of elevation data for this surface model.
	 * 
	 * @param elevationData
	 *            int[][]
	 */
	public void setElevationData(int[][] elevationData) {
		this.elevationData = elevationData;
	} // setElevationData

	/**
	 * Sets the elevation color model set for this mesh surface model.
	 * 
	 * @param colorModel
	 */
	public static void setElevatoinColorModel(ElevationColorModel[] aColorModel) {
		colorModel = aColorModel;
	} // setElevationColorModel
	private int getSmoothValue(int[][] map, int x, int y){
		int minx = 0;
		int miny = 0;
		int maxx = map.length;
	    int maxy = map[0].length;
	    if(x==minx&&y==miny)return map[x][y];
	    if(x==minx&&y==maxy)return map[x][y];
	    if(x==maxx&&y==miny)return map[x][y];
	    if(x==maxx&&y==maxy)return map[x][y];
		if(x==minx) return xMinEdgeTotal(map,x,y)/6;
		else
			if(x==maxx) return xMaxEdgeTotal(map,x,y)/6;
			else
				if(y==miny) return yMinEdgeTotal(map,x,y)/6;
				else
					if(y==maxy) return yMaxEdgeTotal(map,x,y)/6;
					else
						return nonEdgeTotal(map,x,y)/9;
						
		
		
		
	}
	private int nonEdgeTotal(int[][] map, int x, int y){
		int total = 0;
		total+=map[x-1][y];
		total+=map[x+1][y];
		total+=map[x][y-1];
		total+=map[x][y+1];
		total+=map[x-1][y-1];
		total+=map[x+1][y+1];
		total+=map[x-1][y+1];
		total+=map[x+1][y-1];
		total+=map[x][y];
		return total;
	}
	private int xMinEdgeTotal(int[][] map, int x, int y){
		int total = 0;
		//total+=map[x-1][y];
		total+=map[x+1][y];
		total+=map[x][y-1];
		total+=map[x][y+1];
		//total+=map[x-1][y-1];
		total+=map[x+1][y+1];
		//total+=map[x-1][y+1];
		total+=map[x+1][y-1];
		total+=map[x][y];
		return total;
	}
	private int yMinEdgeTotal(int[][] map, int x, int y){
		int total = 0;
		total+=map[x-1][y];
		total+=map[x+1][y];
		//total+=map[x][y-1];
		total+=map[x][y+1];
		//total+=map[x-1][y-1];
		total+=map[x+1][y+1];
		total+=map[x-1][y+1];
		//total+=map[x+1][y-1];
		total+=map[x][y];
		return total;
	}
	private int xMaxEdgeTotal(int[][] map, int x, int y){
		int total = 0;
		total+=map[x-1][y];
		//total+=map[x+1][y];
		total+=map[x][y-1];
		total+=map[x][y+1];
		total+=map[x-1][y-1];
		//total+=map[x+1][y+1];
		total+=map[x-1][y+1];
		//total+=map[x+1][y-1];
		total+=map[x][y];
		return total;
	}
	private int yMaxEdgeTotal(int[][] map, int x, int y){
		int total = 0;
		total+=map[x-1][y];
		total+=map[x+1][y];
		total+=map[x][y-1];
		//total+=map[x][y+1];
		total+=map[x-1][y-1];
		//total+=map[x+1][y+1];
		//total+=map[x-1][y+1];
		total+=map[x+1][y-1];
		total+=map[x][y];
		return total;
	}
	public int[][] getElevationData(){
		return elevationData;
	}
	
    public int[][] smoothSurface(int[][] oldMap){
    	
    	int height=oldMap.length;
    	int width=(oldMap[0]).length;
    	System.out.println("width:"+width+" height:"+height);
    	int[][] newMap = new int[width][height];
    	
    	for(int x=1;x<(width-1);x++){
    		for(int y=1;y<(height-1);y++){
    	             		newMap[x][y]=this.getSmoothValue(oldMap,x,y);
    		}
    	}
    	return newMap;
    }

	/**
	 * @return Returns the colorModel.
	 */
	public static ElevationColorModel[] getColorModel() {
		return colorModel;
	}

	/**
	 * @param colorModel The colorModel to set.
	 */
	public void setColorModel(ElevationColorModel[] colorModel) {
		this.colorModel = colorModel;
	}

	/**
	 * @return Returns the numberElevations.
	 */
	public int getNumberElevations() {
		return numberElevations;
	}

	/**
	 * @param numberElevations The numberElevations to set.
	 */
	public void setNumberElevations(int numberElevations) {
		this.numberElevations = numberElevations;
	}
} // class MeshSurfaceModel
