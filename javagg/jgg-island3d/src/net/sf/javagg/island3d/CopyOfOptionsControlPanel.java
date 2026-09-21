package net.sf.javagg.island3d;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CopyOfOptionsControlPanel extends JPanel {

	
	public CopyOfOptionsControlPanel(){
		this.setLayout(new GridLayout(10,2));
		JLabel[] labels = new JLabel[10];
		JTextField[] fields = new JTextField[10];
		labels[0]= new JLabel("Label1");
		labels[1]= new JLabel("Label2");
		labels[2]= new JLabel("Label2");
		labels[3]= new JLabel("Label2");
		labels[4]= new JLabel("Label2");
		labels[5]= new JLabel("Label2");
		labels[6]= new JLabel("Label2");
		labels[7]= new JLabel("Label2");
		labels[8]= new JLabel("Label2");
		labels[9]= new JLabel("Label2");
		fields[0]= new JTextField();
		fields[1]= new JTextField();
		fields[2]= new JTextField();
		fields[3]= new JTextField();
		fields[4]= new JTextField();
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
}
