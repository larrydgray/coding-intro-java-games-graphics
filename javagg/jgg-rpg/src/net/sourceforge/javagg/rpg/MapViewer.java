package net.sourceforge.javagg.rpg;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class MapViewer extends JFrame{
    public static int type=0;
    TilePanel tilePanel;
    public MapViewer(){
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent evt){
                System.exit(0);
            }
        });
        setTitle("RPG/MUD Tile Map Viewer");
	JMenuBar menuBar = new JMenuBar();
	JMenu maptypemenu = new JMenu("Map Types");
	JMenu menu = new JMenu("Help");
	menuBar.add(maptypemenu);
	menuBar.add(menu);
	JMenuItem item=new JMenuItem("About RPG/MUD MapViewer");
	
	item.addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
	        JOptionPane.showMessageDialog(null, 
	            "<html><body><center>"+
	            "Javagg project RPG/MUD Mapviewer<br>"+
	            "<br>visit javagg.sourceforge.net<br>"+
	            "<br><br>Credits<br><br>"+
	            "<br>caverdude Project Admin and primary developer<br>"+
	            "</body></html>");
	    }
	});
	menu.add(item);
	item=new JMenuItem("Load World Map");
	
	item.addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
	        tilePanel.setType(TilePanel.WORLD);
		tilePanel.repaint();
		tilePanel.reload();
	    }
	});    
	maptypemenu.add(item);   
	
	item=new JMenuItem("Load Cave Map");
	
	item.addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
	        tilePanel.setType(TilePanel.CAVE); 
		tilePanel.repaint();
		tilePanel.reload();
	    }
	});    
	maptypemenu.add(item);

	item=new JMenuItem("Load Dungeon Map");
	
	item.addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
	        tilePanel.setType(TilePanel.DUNGEON); 
		tilePanel.repaint();
		tilePanel.reload();
	    }
	});    
	maptypemenu.add(item);

	item=new JMenuItem("Load Mine Map");
	
	item.addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
	        tilePanel.setType(TilePanel.MINE); 
		tilePanel.repaint();
		tilePanel.reload();
	    }
	});    
	maptypemenu.add(item);
	this.setJMenuBar(menuBar);
        tilePanel = new TilePanel(this.type);
        this.getContentPane().add(
	    new JScrollPane(tilePanel),BorderLayout.CENTER);
        this.setVisible(true);
        this.pack();
    }
    public static void main(String args[]){
        if (args.length>0){
            if (args[0].equals("-c")) ConsoleWindow.init();
            if (args[0].equals("-t")) {

                if (args[1].equals("cave")) MapViewer.type=TilePanel.CAVE;
                else if (args[1].equals("mine")) MapViewer.type=TilePanel.MINE;
                    else if (args[1].equals("dungeon")) 
	    	            MapViewer.type=TilePanel.DUNGEON;
                        else MapViewer.type = TilePanel.WORLD;
            }
            MapViewer mv = new MapViewer();
        }else {
            MapViewer.type=TilePanel.WORLD;
            MapViewer mv = new MapViewer();
        }
    }
}

