package net.sf.javagg.island3d;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ColorPropertiesControlPanel extends JPanel {
	
	private MapColorModel aMapColorModel;
	
	private static ElevationColorModel[] anElevationColorModel;
	public class NumberOfColorLevelsActionListener implements ActionListener{
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
	}public class CurrentColorLevelActionListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			
		}
	}public class HighElevationActionListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			
		}
	}public class LowElevationListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			
		}
	}public class ColorActionListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			
		}
	}
	public ColorPropertiesControlPanel(){
		this.setLayout(new GridLayout(10,2));
		JLabel[] labels = new JLabel[10];
		JTextField[] fields = new JTextField[10];
		labels[0]= new JLabel("Number of Color Levels");
		labels[1]= new JLabel("Current Color Level");
		labels[2]= new JLabel("High Elevation");
		labels[3]= new JLabel("Low Elevation");
		labels[4]= new JLabel("Color");
		labels[5]= new JLabel("Label2");
		labels[6]= new JLabel("Label2");
		labels[7]= new JLabel("Label2");
		labels[8]= new JLabel("Label2");
		labels[9]= new JLabel("Label2");
		fields[0]= new JTextField();
		fields[0].addActionListener(new NumberOfColorLevelsActionListener());
		fields[1]= new JTextField();
		fields[1].addActionListener(new CurrentColorLevelActionListener());
		fields[2]= new JTextField();
		fields[2].addActionListener(new HighElevationActionListener());
		fields[3]= new JTextField();
		fields[3].addActionListener(new LowElevationListener());
		fields[4]= new JTextField();
		fields[4].addActionListener(new ColorActionListener());
		fields[5]= new JTextField();
		fields[6]= new JTextField();
		fields[7]= new JTextField();
		fields[8]= new JTextField();
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
	 * @return Returns the aMapColorModel.
	 */
	public MapColorModel getAMapColorModel() {
		return aMapColorModel;
	}
	/**
	 * @param mapColorModel The aMapColorModel to set.
	 */
	public void setAMapColorModel(MapColorModel mapColorModel) {
		aMapColorModel = mapColorModel;
	}
	/**
	 * @return Returns the anElevationColorModel.
	 */
	public static ElevationColorModel[] getAnElevationColorModel() {
		return anElevationColorModel;
	}
	/**
	 * @param anElevationColorModel The anElevationColorModel to set.
	 */
	public static void setAnElevationColorModel(ElevationColorModel[] anElevationColorModel2) {
		anElevationColorModel = anElevationColorModel2;
	}
}
