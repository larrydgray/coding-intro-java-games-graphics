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
	/** The frame which controls the generation of the map elevation data. */
	private MapFrame mapFrame;

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
		
		 
		mapFrame = new MapFrame();
		//mapFrame.setSize(new Dimension(400,400));
		//mapFrame.validate();
		this.setLayout(new BorderLayout());
		JButton drawButton = new JButton("Draw 3D Surface");
		drawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				meshSurfaceModel = new MeshSurfaceModel();
				meshSurfaceModel.setElevationData(mapFrame.getMap());
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
} // SurfaceControlPanel class
