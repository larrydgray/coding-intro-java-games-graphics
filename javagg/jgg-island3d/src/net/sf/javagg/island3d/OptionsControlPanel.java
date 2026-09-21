package net.sf.javagg.island3d;

import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

public class OptionsControlPanel extends JPanel {
	private MapPanel mapPanel;
	
	public class HighElevActionListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			int value;
			JTextField field = (JTextField)ae.getSource();
			try {
				value = Integer.parseInt(field.getText());
				if (value >= 5 && value <= 50) { // Minimum and maximum values (5 - 50)
					RandomTurtle.setHighestElevation(value);
				} else {
					JOptionPane.showMessageDialog(
						field.getParent(),
						"Number out of range in 'Highest Elevation' field.",
						"Input error",
						JOptionPane.ERROR_MESSAGE
					);
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(
					field.getParent(),
					"You must enter number in 'Highest Elevation' field.",
					"Input error",
					JOptionPane.ERROR_MESSAGE
				);
			}
		}
	}
	public class NumOfBranchesActionListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			int value;
			JTextField field = (JTextField)ae.getSource();
			try {
				value = Integer.parseInt(field.getText());
				if (value >= 1 && value <= 50) { // Minimum and maximum values (1 - 50)
					RandomTurtle.setNumberBranches(value);
				} else {
					JOptionPane.showMessageDialog(
						field.getParent(),
						"Number out of range in 'Number of Branches' field.",
						"Input error",
						JOptionPane.ERROR_MESSAGE
					);
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(
					field.getParent(),
					"You must enter number in 'Number of Branches' field.",
					"Input error",
					JOptionPane.ERROR_MESSAGE
				);
			}
		}
	}
	public class LengthActionListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			int value;
			JTextField field = (JTextField)ae.getSource();
			try {
				value = Integer.parseInt(field.getText());
				if (value >= 10 && value <= 250) { // Minimum and maximum values (10 - 250)
					RandomTurtle.setMapLength(value);
				} else {
					JOptionPane.showMessageDialog(
						field.getParent(),
						"Number out of range in 'Map Length' field.",
						"Input error",
						JOptionPane.ERROR_MESSAGE
					);
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(
					field.getParent(),
					"You must enter number in 'Map Length' field.",
					"Input error",
					JOptionPane.ERROR_MESSAGE
				);
			}
		}
	}
	public class PanelSizeXActionListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			int value;
			JTextField field = (JTextField)ae.getSource();
			try {
				value = Integer.parseInt(field.getText());
				if (value >= 10 && value <= 250) { // Minimum and maximum values (10 - 250)
					RandomTurtle.setMapLength(value);
				} else {
					JOptionPane.showMessageDialog(
						field.getParent(),
						"Number out of range in 'Map Length' field.",
						"Input error",
						JOptionPane.ERROR_MESSAGE
					);
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(
					field.getParent(),
					"You must enter number in 'Map Length' field.",
					"Input error",
					JOptionPane.ERROR_MESSAGE
				);
			}
		}
	}
	public class PanelSizeYActionListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			int value;
			JTextField field = (JTextField)ae.getSource();
			try {
				value = Integer.parseInt(field.getText());
				if (value >= 10 && value <= 250) { // Minimum and maximum values (10 - 250)
					RandomTurtle.setMapLength(value);
				} else {
					JOptionPane.showMessageDialog(
						field.getParent(),
						"Number out of range in 'Map Length' field.",
						"Input error",
						JOptionPane.ERROR_MESSAGE
					);
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(
					field.getParent(),
					"You must enter number in 'Map Length' field.",
					"Input error",
					JOptionPane.ERROR_MESSAGE
				);
			}
		}
	}
	public class StartXActionListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			int value;
			JTextField field = (JTextField)ae.getSource();
			try {
				value = Integer.parseInt(field.getText());
				if (value >= 10 && value <= 250) { // Minimum and maximum values (10 - 250)
					RandomTurtle.setMapLength(value);
				} else {
					JOptionPane.showMessageDialog(
						field.getParent(),
						"Number out of range in 'Map Length' field.",
						"Input error",
						JOptionPane.ERROR_MESSAGE
					);
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(
					field.getParent(),
					"You must enter number in 'Map Length' field.",
					"Input error",
					JOptionPane.ERROR_MESSAGE
				);
			}
		}
	}
	public class StartYActionListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			int value;
			JTextField field = (JTextField)ae.getSource();
			try {
				value = Integer.parseInt(field.getText());
				if (value >= 10 && value <= 250) { // Minimum and maximum values (10 - 250)
					RandomTurtle.setMapLength(value);
				} else {
					JOptionPane.showMessageDialog(
						field.getParent(),
						"Number out of range in 'Map Length' field.",
						"Input error",
						JOptionPane.ERROR_MESSAGE
					);
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(
					field.getParent(),
					"You must enter number in 'Map Length' field.",
					"Input error",
					JOptionPane.ERROR_MESSAGE
				);
			}
		}
	}
	public class StartElevActionListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			int value;
			JTextField field = (JTextField)ae.getSource();
			try {
				value = Integer.parseInt(field.getText());
				if (value >= 10 && value <= 100) { // Minimum and maximum values (10 - 100)
					mapPanel.setStartElev(value);
				} else {
					JOptionPane.showMessageDialog(
						field.getParent(),
						"Number out of range in 'Starting Elevation' field.",
						"Input error",
						JOptionPane.ERROR_MESSAGE
					);
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(
					field.getParent(),
					"You must enter number in 'Starting Elevation' field.",
					"Input error",
					JOptionPane.ERROR_MESSAGE
				);
			}
		}
	}
	
	public class WidthActionListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			int value;
			JTextField field = (JTextField)ae.getSource();
			try {
				value = Integer.parseInt(field.getText());
				if (value >= 10 && value <= 250) { // Minimum and maximum values (10 - 250)
					RandomTurtle.setMapWidth(value);
				} else {
					JOptionPane.showMessageDialog(
						field.getParent(),
						"Number out of range in 'Map Width' field.",
						"Input error",
						JOptionPane.ERROR_MESSAGE
					);
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(
					field.getParent(),
					"You must enter number in 'Map Width' field.",
					"Input error",
					JOptionPane.ERROR_MESSAGE
				);
			}
		}
	}
	public OptionsControlPanel(){
		this.setLayout(new GridLayout(10,2));
		JLabel[] labels = new JLabel[10];
		JTextField[] fields = new JTextField[10];
		labels[0]= new JLabel("Highest Elevation");
		labels[1]= new JLabel("Number of Branches");
		labels[2]= new JLabel("Map Length");
		labels[3]= new JLabel("Map Width");
		labels[4]= new JLabel("Panel Size X");
		labels[5]= new JLabel("Panel Size Y");
		labels[6]= new JLabel("Start X");
		labels[7]= new JLabel("Start Y");
		labels[8]= new JLabel("Starting Elevation");
		labels[9]= new JLabel("Label9");
		fields[0]= new JTextField();
		fields[0].addActionListener(new HighElevActionListener());
		fields[1]= new JTextField();
		fields[1].addActionListener(new NumOfBranchesActionListener());
		fields[2]= new JTextField();
		fields[2].addActionListener(new LengthActionListener());
		fields[3]= new JTextField();
		fields[3].addActionListener(new WidthActionListener());
		fields[4]= new JTextField();
		fields[5]= new JTextField();
		fields[6]= new JTextField();
		fields[7]= new JTextField();
		fields[8]= new JTextField();
		fields[8].addActionListener(new StartElevActionListener());
		fields[9]= new JTextField();
		this.add(labels[0]);
		this.add(fields[0]);
		this.add(labels[1]);
		this.add(fields[1]);
		
		this.add(labels[2]);
		this.add(fields[2]);
		
		this.add(labels[3]);
		this.add(fields[3]);
		
		this.add(labels[4]);
		this.add(fields[4]);
		
		this.add(labels[5]);
		this.add(fields[5]);
		
		this.add(labels[6]);
		this.add(fields[6]);
		
		this.add(labels[7]);
		this.add(fields[7]);
		
		this.add(labels[8]);
		this.add(fields[8]);
		
		this.add(labels[9]);
		this.add(fields[9]);
	}
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
}
