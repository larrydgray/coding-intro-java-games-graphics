package net.sf.javagg.debug;
import net.sf.javagg.gsc.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StateViewer extends JFrame {
    public TileMap tileMap;
    public TileMapLayer tileMapLayer;
    public PalletPanel palletPanel;
	public JTextArea stateViewArea;
	public void append(String s){
		stateViewArea.append(s);
	}
	JButton update = new JButton("Update Buffer View");
	public StateViewer(){
		
	}
	public void init(){
		update.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				if(palletPanel!=null){
					stateViewArea.append("PalletPanel mapCodes");
					for(int i=0;i<palletPanel.mapCodes.size();i++){
						stateViewArea.append("["+
					palletPanel.mapCodes.get(i)+"]\n");
					}
				}
				if(tileMap!=null){
					stateViewArea.append("TileMap map layer 0");
					for(int r=0;r<4;r++){
						for(int c=0;c<4;c++){
					
							stateViewArea.append("["+tileMap.getMapCode(0, c, r)+"]");
						}
						stateViewArea.append("\n");
					}
				}
				if(tileMapLayer!=null){
					stateViewArea.append("TileMapLayer");
					for(int r=0;r<4;r++){
						for(int c=0;c<4;c++){
					
							stateViewArea.append("["+tileMapLayer.getMapCode(c, r)+"]");
						
						}
						stateViewArea.append("\n");
					}
				}
			}
		});
		init2();
	}
	public void init2(){
		update.setPreferredSize(new Dimension(500,50));
		JPanel aPanel = new JPanel();
		aPanel.setLayout(new BorderLayout());
		
		stateViewArea = new JTextArea();
		stateViewArea.setPreferredSize(new Dimension(500,400));
		
		stateViewArea.setBackground(Color.blue);
		stateViewArea.setForeground(Color.cyan);
		stateViewArea.append("State Viewer\n");
		
		JScrollPane scrollPane=new JScrollPane(stateViewArea);
		scrollPane.setPreferredSize(new Dimension(500,400));
		aPanel.add(update,BorderLayout.NORTH);
		aPanel.add(scrollPane,BorderLayout.CENTER);
		this.setTitle("State Viewer");
		this.setSize(500,500);
		this.add(aPanel);
		this.setVisible(true);
	}
}
