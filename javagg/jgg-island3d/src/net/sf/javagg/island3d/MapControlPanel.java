package net.sf.javagg.island3d;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 
 * A command to generate the map. However later we may need further controls.
 * 
 * @author Larry Gray
 * @version 1.1
 */

public class MapControlPanel extends JPanel {
	/** The map panel to conrol. This is the view of the map. */
	private MapPanel mapPanel = new MapPanel();

	public MapControlPanel() {

		this.setLayout(new BorderLayout());
		JButton jbutton = new JButton("Generate Map");
		jbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				mapPanel.repaint();
				// regenerate the drawing
			} // actionPerformed
		}); // ActionListener, addActionListener
		this.add(jbutton, BorderLayout.NORTH);
		this.add(mapPanel, BorderLayout.CENTER);
		
	} // MapControlPanel constructor

	/**
	 * Gets the map panel, a view of the map.
	 * 
	 * @return MapPanel the map view.
	 */
	public MapPanel getMapPanel() {
		return this.mapPanel;
	} // getMapPanel

	/**
	 * @param mapPanel The mapPanel to set.
	 */
	public void setMapPanel(MapPanel mapPanel) {
		this.mapPanel = mapPanel;
	}
} // MapControlPanel class
