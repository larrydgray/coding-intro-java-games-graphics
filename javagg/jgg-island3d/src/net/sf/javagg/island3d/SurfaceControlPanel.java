package net.sf.javagg.island3d;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 * A command to redraw the 3d Surface. Later we may add options for drawing the
 * surface to this. Such as adjusting angle of view.
 * 
 * @author Larry Gray
 * @version 1.1
 */

public class SurfaceControlPanel extends JPanel {
	/** The map view this panel reads elevation data from. Wired via
	 * setMapPanel(), the same pattern OptionsControlPanel uses, so this
	 * shows the SAME map the user generated in the Map Generator tab. */
	private MapPanel mapPanel;

	/** Elevation data associated with map. */
	private MeshSurfaceModel meshSurfaceModel;

	/** A view of the 3d mesh suface. */
	private MeshSurfacePanel meshSurfacePanel = new MeshSurfacePanel();

	/**
	 * Builds the control panel which redraws the 3d surface model.
	 *  
	 */
	public SurfaceControlPanel() {
		meshSurfacePanel.setMeshSurfaceModel(new MeshSurfaceModel());
		this.setLayout(new BorderLayout());
		JButton drawButton = new JButton("Draw 3D Surface");
		drawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				meshSurfaceModel = new MeshSurfaceModel();
				meshSurfaceModel.setElevationData(mapPanel.getMap());
				//meshSurfacePanel.setGridSize(5);
				meshSurfacePanel.setNumberSquares(49);
				meshSurfacePanel.setMeshSurfaceModel(meshSurfaceModel);
				meshSurfacePanel.repaint();
				// regenerate the drawing
			} // actionPerformed
		}); // ActionListener, addActionListener
		JButton smoothButton = new JButton("Smooth Surface");
		smoothButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				meshSurfaceModel = meshSurfacePanel.getMeshSurfaceModel();
				meshSurfaceModel.setElevationData(meshSurfaceModel.smoothSurface(meshSurfaceModel.getElevationData()));
				//meshSurfacePanel.setGridSize(5);
				meshSurfacePanel.setNumberSquares(49);
				meshSurfacePanel.setMeshSurfaceModel(meshSurfaceModel);
				meshSurfacePanel.repaint();
				// regenerate the drawing
			} // actionPerformed
		}); // ActionListener, addActionListener
		JPanel commandPanel=new JPanel();
		commandPanel.setLayout(new GridLayout(2,1));
		commandPanel.add(drawButton);
		commandPanel.add(smoothButton);
		this.add(commandPanel, BorderLayout.NORTH);
		JScrollPane jScrollPane = new JScrollPane();
		JViewport jViewport = new JViewport();
		jViewport.setView(meshSurfacePanel);
		jScrollPane.setViewport(jViewport);
		this.add(jScrollPane, BorderLayout.CENTER);
	} // SurfaceControlPanel constructor

	/**
	 * @return Returns the mapPanel.
	 */
	public MapPanel getMapPanel() {
		return mapPanel;
	}
	/**
	 * @param mapPanel The mapPanel to set.
	 */
	public void setMapPanel(MapPanel mapPanel) {
		this.mapPanel = mapPanel;
	}
} // SurfaceControlPanel class
