package net.sf.javagg.island3d;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Main application frame. This starts itself and the other frame. This is not
 * how I want to continue to do this, I'd rather use a Split pane and one frame.
 * 
 * @author Larry Gray
 * @version 1.1
 */

public class MeshSurface3D extends JFrame {

	/**
	 * Launches the Application.
	 */

	public static void main(String args[]) {

		MeshSurface3D meshSurface3D = new MeshSurface3D();
		meshSurface3D.setSize(new Dimension(400, 400));
		meshSurface3D.validate();
	} // end main method

	//MeshSurface3dPanel meshPanel;
	/** Displays map. */
	MapFrame mapFrame;

	/** Builds the application which draws a 3d surface. */
	public MeshSurface3D() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}//windowClosing
		});//windowAdapter addWindowListener
		/** A default set of elevation color models. */
		ElevationColorModel[] defaultColorModel = new ElevationColorModel[] {
				new ElevationColorModel(0, 3, Color.blue),
				new ElevationColorModel(4, 8, Color.cyan),
				new ElevationColorModel(9, 30, Color.green),
				new ElevationColorModel(31, 35, Color.darkGray),
				new ElevationColorModel(36, 40, Color.lightGray),
				new ElevationColorModel(41, 50, Color.white),
				new ElevationColorModel(51, 55, Color.red),
				new ElevationColorModel(56, 60, Color.yellow) }; // end array
        MeshSurfaceModel.setElevatoinColorModel(defaultColorModel);
        MapColorModel.setColorModel(defaultColorModel);
        ColorPropertiesControlPanel.setAnElevationColorModel(defaultColorModel);
		JMenu jMenu = new JMenu("Help");
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(jMenu);
		this.setJMenuBar(menuBar);
		
        JTabbedPane aTabbedPane = new JTabbedPane();
       
		SurfaceControlPanel surfaceControlPanel = new SurfaceControlPanel();
		MapControlPanel mapControlPanel = new MapControlPanel();
		OptionsControlPanel optionsControlPanel = new OptionsControlPanel();
		optionsControlPanel.setMapPanel(mapControlPanel.getMapPanel());
		surfaceControlPanel.setMapPanel(mapControlPanel.getMapPanel());
		ColorPropertiesControlPanel colorPropertiesControlPanel = new ColorPropertiesControlPanel();
		//MapColorModel aMapColorModel = mapControlPanel.getMapPanel()
		//colorPropertiesControlPanel.setAMapColorModel()
		aTabbedPane.add("Map Generator",mapControlPanel);
		aTabbedPane.add("Surface",surfaceControlPanel);
		aTabbedPane.add("Options1",optionsControlPanel);
		aTabbedPane.add("Layer Colors",colorPropertiesControlPanel);
		
		this.getContentPane().add(aTabbedPane);
		
		this.pack();
		this.setVisible(true);

	} //constructor MeshSurface3D

} // MeshSurface3D class
