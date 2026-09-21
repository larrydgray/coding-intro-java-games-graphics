/*
 * Created on Jun 8, 2004
 *
 */
package net.sf.javagg.gsc;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * A class which generates an image view of a tile
 * map. A tile map is a grid of squares, or hex based
 * or diamond based maps. They are generally viewed as top
 * down but could be side or profile view as well. Tile
 * maps usually consist of layers but not always. Simple
 * games may only use one layer. Complex games may use
 * 6 or more layers. Some layers are visible and others
 * are not. 
 * 
 * @author Larry Gray
 * @version 1.5
 */
public class TileMap {

	/** */
	protected ImageStrip imageStrip;
	
	
	public ImageStrip getImageStrip(){
		return imageStrip;
	}

	private void setMediaTrackerComponent(Component aComponent) {
		this.mediaTrackerComponent = aComponent;
	}

	/**
	 * Loads a map specified by a given xml file.
	 * 
	 * @param mapFileName
	 */
	public TileMap(String mapFileName, Component aComponent) {
		this.setMediaTrackerComponent(aComponent);
		loadMap(mapFileName);

	}

	/**
	 * Will load a template xml file.
	 */
	public TileMap(Component aComponent) {
		this.setMediaTrackerComponent(aComponent);
		loadMap("mapTemplate.xml");

	}
	
	public TileMap(){
		
	}
	public void addMapLayer(TileMapLayer tileMapLayer){
		this.mapLayers.add(tileMapLayer);
	}

	/** The layers of the map */
	protected ArrayList<TileMapLayer> mapLayers = new ArrayList<>();

	/** Set of tiles for this map taken from the image strip. */
	protected HashMap<String, Tile> tiles = new HashMap<>();

	public Iterator<Tile> getTiles(){
		return tiles.values().iterator();
	}
	
	/** number of columns on the map. */
	protected int mapSizeX;

	/** number of rows on the map. */
	protected int mapSizeY;

	/** width of image in pixels for tiles used on this map. */
	protected int tileSizeX;

	/** height of image in pixels for tiles used on this map. */
	protected int tileSizeY;

	/** Height of the view in rows */
	protected int viewHeight;

	/** Width of the view in columns. */
	protected int viewWidth;

	/** Top left corner of the view column. */
	protected int viewX;

	/** Top left corner of the view row. */
	protected int viewY;

	/** */
	protected String mapName;

	/** */
	BufferedImage screenImage = null;

	/**
	 * Adds a tile.
	 * 
	 * @param tile
	 * @see gsc.TileMap#addTile(gsc.Tile)
	 */
	public void addTile(Tile tile) {
		//System.out.println("addTile:" + tile.getMapCode());
		tiles.put(tile.getMapCode(), tile);
	} // addTile(DefaultTile tile)

	/**
	 * Draws a layer view.
	 * 
	 * @param tileMapLayer
	 * @see gsc.TileMap#drawLayerView(gsc.abstractions.TileMapLayer)
	 */
	private void drawLayerView(TileMapLayer tileMapLayer) {
		TileMapLayer view = tileMapLayer.getView(viewX, viewY, viewWidth,
				viewHeight);
		for (int y = 0; y < viewHeight; y++) {
			for (int x = 0; x < viewWidth; x++) {
				String mapCode = view.getMapCode(x, y);
				if(mapCode!=null)
				if (!mapCode.equals("  "))
					drawTile(mapCode, x, y);
			} // for x
		} // for y
	} // drawLayerView(TileMapLayer tileMapLayer)

	/**
	 * Draws a map view.
	 */
	private void drawMapView() {
		drawMapView(mapLayers);
	}// drawMapView()

	/**
	 * Draws map view. A map is combined layers.
	 * 
	 * @see gsc.TileMap#drawMapView(java.util.ArrayList)
	 * @param layers
	 */
	private void drawMapView(ArrayList<TileMapLayer> layers) {
		Iterator<TileMapLayer> layersIterator = layers.iterator();

		while (layersIterator.hasNext()) {
			TileMapLayer mapLayer = (TileMapLayer) layersIterator.next();
			System.out.println("Map:"+mapName+"Layer:"+mapLayer);
			drawLayerView(mapLayer);
		} // while layer iterator

	} // drawMapView(ArrayList layers)

	/**
	 * Draws a tile.
	 * 
	 * @param mapCode
	 *            a two or three character cryptic code used in delimtied text
	 *            map files.
	 * @param x
	 *            column
	 * @param y
	 *            row
	 * @see gsc.TileMap#drawTile(java.lang.String, int, int)
	 */
	private void drawTile(String mapCode, int x, int y) {
		//System.out.println("map code:" + mapCode);
		if (mapCode.startsWith("  "))
			; // do nothing

		else {
			Tile aTile = getTile(mapCode.trim());
			if(aTile==null) aTile=getTile("bn");
			//System.out.println("["+mapCode+"]");
			
			Image anImage = aTile.getImage();
			screenImage.getGraphics().drawImage(anImage, x * this.tileSizeX,
					y * tileSizeY, null);
		}

	} // drawTile(String mapCode, int x, int y)

	public void setTileSizeX(int tileSizeX) {
		this.tileSizeX = tileSizeX;
	}

	public void setTileSizeY(int tileSizeY) {
		this.tileSizeY = tileSizeY;
	}

	/**
	 * Gets a map code which represents a tile from a TileMap object from a
	 * given location and layer.
	 * 
	 * @param layer
	 *            Map Layer number.
	 * @param x
	 *            column of tile being queried.
	 * @param y
	 *            row of tile being queried.
	 * @return the usual two character string identifier for the tile.
	 */
	public String getMapCode(int layer, int x, int y) {
		return (mapLayers.get(layer)).getMapCode(x, y);
	} // getMapCode(int layer, int x, int y)

	/** Keeping a reference to the loaded xml document for use when saving **/
	private Document doc;
	private String mapLocation = "data/";
	private String imageStripLocation = "images/game/";

	/**
	 * Loads a set of mapLayers from disk into a TileMapLayer array.
	 * 
	 * @see gsc.TileMap#loadMap(java.lang.String[])
	 */
	private void loadMap(String mapFileName) {

		try {

			SAXBuilder builder = new SAXBuilder();

			doc = (Document) builder.build(mapLocation + mapFileName);
			// new XMLTreeViewer(doc);
			Element rootNode = doc.getRootElement();
			this.mapSizeX = Integer
					.parseInt(rootNode.getAttributeValue("cols"));
			this.mapSizeY = Integer
					.parseInt(rootNode.getAttributeValue("rows"));
			this.viewHeight = mapSizeY;
			this.viewWidth = mapSizeX;
			this.viewX = 0;
			this.viewY = 0;
			this.mapName = rootNode.getAttributeValue("name");
			//System.out.println("root:" + rootNode);
			Element mapElement = rootNode.getChild("map");
			//System.out.println("map:" + mapElement);
			Element imageStripElement = rootNode.getChild("image_strip");
			//System.out.println("imageStrip:" + imageStripElement);
			loadImageStrip(imageStripElement);
			List<Element> layers = rootNode.getChildren("layer");
			//System.out.println("Loading layers:"+layers.size()+" layers to be loaded.");
			loadLayers(layers);

		} catch (IOException io) {
			io.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		}

		initScreenImage();

	} // loadMap(String[] mapLayers)
	
	public void initScreenImage(){
		screenImage = new BufferedImage(tileSizeX * viewWidth, tileSizeY
				* viewHeight, BufferedImage.TYPE_INT_RGB);
	}

	private Component mediaTrackerComponent;

	private void loadImageStrip(Element imageStripElement) {
		String imageStripFile = imageStripElement.getAttributeValue("name");
		imageStrip = new ImageStrip(imageStripLocation + imageStripFile,
				mediaTrackerComponent);
		this.tileSizeX = imageStrip.tileSize.width;
		System.out.println("tileSizeX:" + this.tileSizeX);
		this.tileSizeY = imageStrip.tileSize.height;
		System.out.println("tileSizeY:" + this.tileSizeY);
		List<Element> tiles = imageStripElement.getChildren("tile");
		loadTiles(tiles);
	}

	private void loadTiles(List<Element> tiles) {
		Iterator<Element> tileIterator = tiles.iterator();
		String mapCode = null;
		int col = 0;
		int row = 0;
		while (tileIterator.hasNext()) {
			Element tileElement = tileIterator.next();
			//System.out.print("" + tileElement);
			mapCode = tileElement.getAttributeValue("mapCode");
			//System.out.print(" " + mapCode);
			col = Integer.parseInt(tileElement.getAttributeValue("col"));
			//System.out.print(" " + col);
			row = Integer.parseInt(tileElement.getAttributeValue("row"));
			//System.out.println(" " + row + "\n");
			Tile aTile = new Tile(imageStrip, mapCode, col, row, true);
			this.addTile(aTile);
		}
	}

	
	private void loadLayers(List<Element> layers) {
		Iterator<Element> layersIterator = layers.iterator();

		while (layersIterator.hasNext()) {
			Element layerElement = layersIterator.next();
			mapLayers.add(new TileMapLayer(layerElement, this.mapSizeX,
					this.mapSizeY));
		}
	}

	/**
	 * Saves the map data to disk.
	 * 
	 * @param mapFileName
	 */
	public void saveMap(String mapFileName) {
		try {

			File xmlFile = new File(this.mapLocation+mapFileName);

			Iterator<TileMapLayer> mapLayersIterator = mapLayers.iterator();
			while (mapLayersIterator.hasNext()) {
				TileMapLayer aTileMapLayer = mapLayersIterator.next();
				aTileMapLayer.saveMapLayer();
			}
			
			XMLOutputter xmlOutput = new XMLOutputter();

			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(xmlFile));

		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	/**
	 * Moves the view down.
	 * 
	 * @see gsc.TileMap#moveViewDown(int)
	 * @param numberTiles
	 */
	public void moveViewDown(int numberTiles) {
		viewY = viewY + numberTiles;

	}// moveViewDown(int numberTiles)

	/**
	 * Moves the view to the left.
	 * 
	 * @see gsc.TileMap#moveViewLeft(int)
	 * @param numberTiles
	 */
	public void moveViewLeft(int numberTiles) {
		viewX = viewX - numberTiles;

	} // moveViewLeft(int numberTiles)

	/**
	 * Moves the view to the right.
	 * 
	 * @see gsc.TileMap#moveViewRight(int)
	 * @param numberTiles
	 */
	public void moveViewRight(int numberTiles) {
		viewX = viewX - numberTiles;

	}// moveViewRight(int numberTiles)

	/**
	 * Moves the view up.
	 * 
	 * @see gsc.TileMap#moveViewUp(int)
	 * @param numberTiles
	 */
	public void moveViewUp(int numberTiles) {
		viewY = viewY - numberTiles;

	}// moveViewUp(int numberTiles)

	/*
	 * Sets map layers.
	 * 
	 * @param mapLayers
	 * 
	 * public void setMap(TileMapLayer[] mapLayersArray) { mapLayers = new
	 * ArrayList(); for (int i = 0; i < mapLayersArray.length; i++) {
	 * mapLayers.add(mapLayersArray[i]); }//for i }// setMap(TileMapLayer[]
	 * mapLayers)
	 */

	/**
	 * Sets the usual two character map code which identifies the tile.
	 * 
	 * @param mapCode
	 *            usually a cryptic two character string identifier for a tile
	 *            image.
	 * @param layer
	 *            layer number.
	 * @param x
	 *            column.
	 * @param y
	 *            row.
	 */
	public void setMapCode(String mapCode, int layer, int x, int y) {
		TileMapLayer tileMapLayer = this.mapLayers.get(layer);
		tileMapLayer.setMapCode(mapCode, x, y);
	} // setMapCode(String mapCode, int layer, int x, int y)

	/**
	 * Sets port view.
	 * 
	 * @see gsc.TileMap#setViewPort(int, int)
	 * @param x
	 * @param y
	 */
	public void setViewPort(int x, int y) {
		viewX = x;
		viewY = y;

	}// setViewPort(int x, int y)

	/**
	 * Sets the Port view size.
	 * 
	 * @see gsc.TileMap#setViewPortSize(int, int)
	 * @param width
	 * @param height
	 */
	public void setViewPortSize(int width, int height) {
		this.viewWidth = width;
		this.viewHeight = height;

	}// setViewPortSize(int width, int height)

	/**
	 * Turns a layer off so that it will not be viewed.
	 * 
	 * @param layerNumber
	 * @param off
	 */
	public void setLayerOff(int layerNumber, boolean off) {
		
	}

	public Image getMapImage() {
		initScreenImage();
		drawMapView();
		return screenImage;
	}

	public Tile getTile(String mapCode) {
		System.out.println("map code:" + mapCode);
		return (Tile) tiles.get(mapCode);
	}
} // TileMap Interface
