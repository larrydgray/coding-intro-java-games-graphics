package net.sf.javagg.debug;

import sourceforge.jgg.gsc.CharacterCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

public class ScreenBufferViewer extends JFrame {
    ScreenBuffer buffer;
	JTextArea bufferViewArea;
	JButton update = new JButton("Update Buffer View");
	public ScreenBufferViewer(ScreenBuffer abuffer){
		this.buffer=abuffer;
		update.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				Iterator bufferIterator = buffer.iterator();
				bufferViewArea.setText("");
				bufferViewArea.setRows(1000);
				int a=0;
				while(bufferIterator.hasNext()){
					CharacterCell[] row=(CharacterCell[])bufferIterator.next();
					String rowString="";
					for(int i=0;i<row.length;i++){
						rowString+=row[i].toString();
					}
					bufferViewArea.append(""+(a++)+rowString+"\n");
				}
			}
		});
		update.setPreferredSize(new Dimension(500,50));
		JPanel aPanel = new JPanel();
		aPanel.setLayout(new BorderLayout());
		
		bufferViewArea = new JTextArea();
		bufferViewArea.setPreferredSize(new Dimension(500,400));
		bufferViewArea.setBackground(Color.yellow);
		bufferViewArea.setForeground(Color.red);
		bufferViewArea.append("Hello\n");
		bufferViewArea.setBackground(Color.blue);
		bufferViewArea.setForeground(Color.cyan);
		bufferViewArea.append("Hello\n");
		
		JScrollPane scrollPane=new JScrollPane(bufferViewArea);
		scrollPane.setPreferredSize(new Dimension(500,400));
		aPanel.add(update,BorderLayout.NORTH);
		aPanel.add(scrollPane,BorderLayout.CENTER);
		this.setSize(500,500);
		this.add(aPanel);
		this.setVisible(true);
	}
}
