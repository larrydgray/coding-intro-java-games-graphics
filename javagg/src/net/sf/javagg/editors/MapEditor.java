package net.sf.javagg.editors;

import net.sf.javagg.imagetool.ImageStrip;
import net.sf.javagg.gsc.Tile;
import net.sf.javagg.gsc.TileMap;
import net.sf.javagg.gsc.TileMapLayer;
import net.sf.javagg.debug.StateViewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class MapEditor extends JFrame {

	/**
	 * This is for selection of tiles which are used in the map layers. A tile
	 * is selected and is used as the draw tile. Currently this PalletPanel has
	 * only one layer itself which contains all the tiles from a single Image
	 * Strip. Later we may add multiple layers and load from Multiple Image
	 * Strips. For example each layer may have different sets of tiles.
	 * 
	 * @author Larry Gray(caverdude)
	 * 
	 */
	public class PalletPanel extends JPanel {

		/** The map codes associated with given tile images. */
		public ArrayList<String> mapCodes;

		/** The image strip containing tile images. */
		ImageStrip palletTiles = null;

		/** Tiles uses on this pallet. */
		Iterator<Tile> tiles = null;

		/** TileMap containing the selection tiles. */
		TileMap palletMap = null;

		/** Column of the selected tile. */
		public int selectedCol;

		/** Row of the selected tile. */
		public int selectedRow;

		/**
		 * Sets a tile map to be used for the pallet.
		 * 
		 * @param aTileMap
		 */
		public void setTileMap(TileMap aTileMap) {
			palletMap = aTileMap;
		}

		/**
		 * Sets the tiles to be used for this pallet.
		 * 
		 * @param tiles
		 */
		public void setTiles(Iterator<Tile> tiles) {
			this.tiles = tiles;
		}

		/**
		 * Sets the Image Strip to be used for this Pallet.
		 * 
		 * @param anImageStrip
		 */
		public void setImageStrip(ImageStrip anImageStrip) {
			this.palletTiles = anImageStrip;
		}

		/**
		 * This is for Debugging for viewing the state of some variables.
		 */
		StateViewer aStateViewer;

		/**
		 * Plots a grid of tiles for selection.
		 */
		public PalletPanel() {

			// aStateViewer = new StateViewer();

			this.addMouseMotionListener(new MouseMotionListener() {

				@Override
				/**
				 * Not used.
				 */
				public void mouseDragged(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				/**
				 * Not used yet. May be used for debugging.
				 */
				public void mouseMoved(MouseEvent me) {

					int col = me.getX() / tileX;
					int row = me.getY() / tileY;
					selectedCol = col;
					selectedRow = row;
				}

			}); // mouse motion listener
			this.addMouseListener(new MouseAdapter() {
				/**
				 * Use for selecting a tile or moving the selector.
				 */
				public void mouseClicked(MouseEvent me) {

					int mouseButton = me.getButton();
					int x = (me.getX() - 3) / tileX;
					int y = (me.getY() - 3) / tileY;
					if (mouseButton == me.BUTTON1) { // left button
						selectX = x * tileX;
						selectY = y * tileY;
						selectedMapCode = palletMap.getMapCode(0, x, y);
						repaint();
					} else {// any other button
						repaint();
					} // if else

				} // method mouseClicked
			}); // mouse listener
		} // PalletPanel constructor

		/** A map layer containing pallet tiles. */
		TileMapLayer palletLayer = null;

		/**
		 * Extra construction. This may be used by the constructor later.
		 */
		public void init() {
			if (tiles == null)
				tiles = mapBoard.getTiles();
			if (palletMap == null)
				palletMap = new TileMap();
			if (palletTiles == null)
				palletTiles = mapBoard.getImageStrip();
			ArrayList<String> mapCodes = makeMapCodes();
			int numberMapCodes = mapCodes.size();
			int root = getRootSize(numberMapCodes);
			palletLayer = new TileMapLayer(root, root);
			palletLayer.setMapLayerName("Pallet");
			palletMap.addMapLayer(palletLayer);
			setPallet(palletLayer, mapCodes, root);
			palletMap.setViewPort(0, 0);
			palletMap.setViewPortSize(root, root);
			palletMap.setTileSizeX(palletTiles.tileSize.width);
			palletMap.setTileSizeY(palletTiles.tileSize.height);
			palletMap.initScreenImage();
			// aStateViewer.tileMap = this.palletMap;
			// aStateViewer.tileMapLayer = this.palletLayer;
			// aStateViewer.palletPanel = this;
			// aStateViewer.init();
		} // init()

		/**
		 * Used by init(); More construction.
		 * 
		 * @return a set of map codes.
		 */
		ArrayList<String> makeMapCodes() {

			mapCodes = new ArrayList<>();

			while (tiles.hasNext()) {

				Tile aTile = tiles.next();

				palletMap.addTile(aTile);

				mapCodes.add(aTile.getMapCode());

			} // while
			return mapCodes;
		} // makeMapCodes()

		/**
		 * More construction. Used by init() Sets up the layout of the pallet
		 * tiles on the pallet map layer.
		 * 
		 * @param palletLayer
		 * @param mapCodes
		 * @param root
		 */
		void setPallet(TileMapLayer palletLayer, ArrayList<String> mapCodes,
				int root) {
			int col = 0;
			int row = 0;
			String mapCode = null;
			for (int i = 0; i < mapCodes.size(); i++) {
				col = i - (i / root) * root;
				row = i / root;
				mapCode = mapCodes.get(i);
				palletLayer.setMapCode(mapCode, col, row);
			} // for
		} // setPallet

		/**
		 * Calculates the size of the pallet grid.
		 * 
		 * @param numberMapCodes
		 * @return
		 */
		int getRootSize(int numberMapCodes) {
			return (int) Math.sqrt(numberMapCodes) + 1;
		} // getRootSize

		/** location of selector */
		private int selectX;

		/** location of selector */
		private int selectY;

		/**
		 * The panels main painting method.
		 */
		public void paintComponent(Graphics g) {
			g.drawImage(palletMap.getMapImage(), 3, 3, null);
			g.setColor(Color.white);
			g.drawRect(0 + selectX, 0 + selectY, 45, 45);
			g.setColor(Color.black);
			g.drawRect(1 + selectX, 1 + selectY, 43, 43);
			g.setColor(Color.white);
			g.drawRect(2 + selectX, 2 + selectY, 41, 41);

		} // paintComponent
	} // inner class PalletPanel

	/**
	 * Panel which will display the game map to be edited. Will aid in the
	 * editing of the map. Will allow selection of layer to be edited. Will also
	 * allow user to hide layers.
	 * 
	 * @author Larry Gray (caverdude)
	 * 
	 */
	public class MapPanel extends JPanel {

		/** */
		public int selectedCol;

		/** */
		public int selectedRow;

		/** */
		public MapPanel() {

			mapBoard = new TileMap("chessBoard1.xml", this);
			tileX = mapBoard.getImageStrip().tileSize.width;
			tileY = mapBoard.getImageStrip().tileSize.height;
			this.addMouseMotionListener(new MouseMotionListener() {

				@Override
				/** not used */
				public void mouseDragged(MouseEvent arg0) {
					// TODO Auto-generated method stub

				} // mouseDragged

				@Override
				/** may be used for debuggin */
				public void mouseMoved(MouseEvent me) {

					int col = me.getX() / tileX;
					int row = me.getY() / tileY;
					selectedCol = col;
					selectedRow = row;
				} // mouseMoved

			}); // mouse motion listener

			this.addMouseListener(new MouseAdapter() {

				/**
				 * Will aid the user in selecting a tile location and on the map
				 * and changing it to the desired tile image.
				 */
				public void mouseClicked(MouseEvent me) {

					int mouseButton = me.getButton();
					int x = (me.getX() - 3) / tileX;
					int y = (me.getY() - 3) / tileY;

					if (mouseButton == me.BUTTON1) {
						selectX = x * tileX;
						selectY = y * tileY;
						mapBoard.setMapCode(selectedMapCode, editLayer, x, y);
						repaint();
					} else { // any other button.

						repaint();
					} // if else

				} // method mouseClicked
			}); // mouse listener
		} // MapPanel constructor

		/** selector position. */
		private int selectX;

		/** selector position */
		private int selectY;

		/**
		 * Map Editor panel's main painting method.
		 */
		public void paintComponent(Graphics g) {
			g.drawImage(mapBoard.getMapImage(), 3, 3, null);
			g.setColor(Color.white);
			g.drawRect(0 + selectX, 0 + selectY, 45, 45);
			g.setColor(Color.black);
			g.drawRect(1 + selectX, 1 + selectY, 43, 43);
			g.setColor(Color.white);
			g.drawRect(2 + selectX, 2 + selectY, 41, 41);

		} // paintComponent
	} // inner class MapEditor panel

	private int editLayer = 1;

	private String selectedMapCode = "  ";

	/** Tile map used for the map to be edited. */
	TileMap mapBoard = null;

	/** */
	public int tileX = 1;

	/** */
	public int tileY = 1;

	/**
	 * Sets the title of the JFrame to Map Editor. Sets up menu.
	 */
	public MapEditor() {
		this.setTitle("Map Editor");
		JMenu fileMenu = new JMenu("File");
		JMenuItem load = new JMenuItem("Load");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem newMap = new JMenuItem("New");
		JMenuBar mapEditorMenuBar = new JMenuBar();
		JMenu optionsMenu = new JMenu("Options");
		JMenuItem workspace = new JMenuItem("Workspace...");
		JMenuItem imageSize = new JMenuItem("Map Size...");
		JMenuItem imageTileSize = new JMenuItem("Tile Pallet...");
		mapEditorMenuBar.add(fileMenu);
		mapEditorMenuBar.add(optionsMenu);
		fileMenu.add(workspace);
		fileMenu.add(newMap);
		fileMenu.add(load);
		fileMenu.add(save);
		optionsMenu.add(imageSize);
		optionsMenu.add(imageTileSize);
		optionsMenu.addSeparator();
		ButtonGroup group = new ButtonGroup();
		JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem("Layer 0",
				false);
		rbMenuItem.setSelected(false);
		// rbMenuItem.setMnemonic(KeyEvent.VK_R);
		group.add(rbMenuItem);
		optionsMenu.add(rbMenuItem);

		JRadioButtonMenuItem rbMenuItem2 = new JRadioButtonMenuItem("Layer 1",
				true);
		rbMenuItem2.setSelected(true);
		// rbMenuItem2.setMnemonic(KeyEvent.VK_O);
		group.add(rbMenuItem2);
		optionsMenu.add(rbMenuItem2);
		optionsMenu.addSeparator();
		this.setJMenuBar(mapEditorMenuBar);
		rbMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				editLayer = 0;

			} // actionPerformed
		});// ActionListener, addActionListener
		rbMenuItem2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				editLayer = 1;

			} // actionPerformed
		});// ActionListener, addActionListener
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

			} // actionPerformed
		});// ActionListener, addActionListener
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				mapBoard.saveMap("chessBoard1.xml");

			} // actionPerformed
		});// ActionListener, addActionListener
		this.setJMenuBar(mapEditorMenuBar);
	} // constructor

	/**
	 * Does most of the construction of the Map Editor.
	 */
	public void init() {
		this.setSize(500, 500);
		this.setLayout(new GridLayout(0, 2));
		this.add(new MapPanel());
		PalletPanel aPalletPanel = new PalletPanel();
		aPalletPanel.init();
		this.add(aPalletPanel);
		this.setVisible(true);
	} // init()

	/**
	 * Entry point for starting up a Map Editor.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MapEditor aMapEditor = new MapEditor();
		aMapEditor.init();

	} // main

} // class MapEditor
