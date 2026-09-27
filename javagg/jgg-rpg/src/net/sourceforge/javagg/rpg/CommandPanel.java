package net.sourceforge.javagg.rpg;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * This is the main UI component for the 
 * MapNavigational buttons. Including Directional and
 * Portal Entry.
 */
public class CommandPanel extends JPanel {
	
	/**
	 * debug variable
	 */
	public  int mapCycle;
	 
	/**
	 * This keeps a reference to the map viewing panel
	 * so that it can change the map when buttons are
	 * clicked.
	 */
	public GameWalkPanel gameWalkPanel;
	
	/**
	 * This keeps a reference tot the Universe that
	 * it panning maps for.
	 */
	private Universe universe;
	
	/**
	 * Constructs a panel for controlling a game 
	 * walker panel.
	 */
    public CommandPanel(GameWalkPanel gwp){
    	
    	this();
    	
    	gameWalkPanel = gwp;
    	
	} // end constructor CommandPanel(GameWalkPanel)
	
	/**
	 * Constructs a generic CommandPanel.
	 */
	public CommandPanel() {
		
		super();
		
		this.setLayout(new GridLayout(3,5));
		
	    JButton jb = new JButton("NW");
	    
	    jb.addActionListener(new ActionListener() {
	    	
	    	public void actionPerformed(ActionEvent e){
	    		
	    		northWest();
	    		
	    	}
	    	
	    });
	    
	    this.add(jb);
	    
	    jb = new JButton("N");
	    
	    jb.addActionListener(new ActionListener() {
	    	
	    	public void actionPerformed(ActionEvent e){
	    		
	    		north();
	    		
	    	}
	    	
	    });
	    
	    this.add(jb);
	    
	    jb = new JButton("NE");
	    
	    jb.addActionListener(new ActionListener() {
	    	
	    	public void actionPerformed(ActionEvent e){
	    		
	    		northEast();
	    		
	    	}
	    	
	    });
	    
	    this.add(jb);
	    
	    jb = new JButton("Enter Up");
	    
	    jb.addActionListener(new ActionListener() {
	    	
	    	public void actionPerformed(ActionEvent e){
	    		
	    		//enterUp();
	    		
	    	}
	    	
	    });
	    
	    this.add(jb);
	    
	    
	     jb = new JButton("Debug Assert1");
	    
	    jb.addActionListener(new ActionListener() {
	    	
	    	public void actionPerformed(ActionEvent e){
	    		
	    		myassert(false,"Position is x="
	    		         +gameWalkPanel.position.getX()+" y="
	    		         +gameWalkPanel.position.getY());
	    		
	    	}
	    	
	    });
	    
	    this.add(jb);
	    
	    jb = new JButton("W");
	    
	    jb.addActionListener(new ActionListener() {
	    	
	    	public void actionPerformed(ActionEvent e){
	    		
	    		west();
	    		
	    	}
	    	
	    });
	    
	    this.add(jb);
	    
	    jb = new JButton("");
	    
	    jb.addActionListener(new ActionListener() {
	    	
	    	public void actionPerformed(ActionEvent e){
	    		
	    		
	    	}
	    	
	    });
	    
	    this.add(jb);
	    
	    jb = new JButton("E");
	    
	    jb.addActionListener(new ActionListener() {
	    	
	    	public void actionPerformed(ActionEvent e){
	    		
	    		east();
	    		
	    	}
	    	
	    });
	    
	    this.add(jb);
	    
	    jb = new JButton("Enter/Exit");
	    
	    jb.addActionListener(new ActionListener() {
	    	
	    	public void actionPerformed(ActionEvent e){
	    		
	    		enter();
	    		
	    	}
	    	
	    });
	    
	     this.add(jb);
	     
	     jb = new JButton("Debug Maps");
	    
	    jb.addActionListener(new ActionListener() {
	    	
	    	public void actionPerformed(ActionEvent e){
	    		
	    		mapCycle++;
	    		if (mapCycle==5)mapCycle=1;
	    		{
	    			if (mapCycle==1){
	    				Map map=new Map(new Dungeon1Map(universe),0,0,10,10);
	    		   		gameWalkPanel.changeMap(2,map,new Point(0,5));
	    		   		gameWalkPanel.paintImmediately(0,0,800,800);
	    		   	}	
	    		    if (mapCycle==2){
	    		    	Map map=new Map(new Cave1Map(universe),0,0,10,10);
	    		   		gameWalkPanel.changeMap(3,map,new Point(4,0));
	    		   		gameWalkPanel.paintImmediately(0,0,800,800);
	    		   	}	
	    		    if (mapCycle==3){
	    		    	Map map=new Map(new Mine1Map(universe),0,0,10,10);
	    		   		gameWalkPanel.changeMap(4,map,new Point(0,1));
	    		   		gameWalkPanel.paintImmediately(0,0,800,800);
	    		   	}	
	    		    if (mapCycle==4){
	    		    	Map map=new Map(new World1Map(universe),0,0,10,10);
	    		   		gameWalkPanel.changeMap(1,map,new Point(2,2));
	    		   		gameWalkPanel.paintImmediately(0,0,800,800);
	    		   	}	
	    		    try{
	    		    	Thread.currentThread().sleep(200);
	    		    }
	    		    catch(InterruptedException ex){
	    		    	ex.printStackTrace();
	    		    }
	    		   	gameWalkPanel.reload();
	    	    }
	    	}
	    	
	    });
	    
	   
	    
	    
	    this.add(jb);
	    
	    jb = new JButton("SW");
	    
	    jb.addActionListener(new ActionListener() {
	    	
	    	public void actionPerformed(ActionEvent e){
	    		
	    		southWest();
	    		
	    	}
	    	
	    });
	    
	    this.add(jb);
	    
	    jb = new JButton("S");
	    
	    jb.addActionListener(new ActionListener() {
	    	
	    	public void actionPerformed(ActionEvent e){
	    		
	    		south();
	    		
	    	}
	    	
	    });
	    
	    this.add(jb);
	    
	    jb = new JButton("SE");
	    
	    jb.addActionListener(new ActionListener() {
	    	
	    	public void actionPerformed(ActionEvent e){
	    		
	    		southEast();
	    		
	    	}
	    	
	    });
	    
	    this.add(jb);
	    
	    jb = new JButton("Enter Down");
	    
	    jb.addActionListener(new ActionListener() {
	    	
	    	public void actionPerformed(ActionEvent e){
	    		
	    		//enterDown();
	    		
	    	}
	    	
	    });
	    
	    this.add(jb);
	    
	    jb = new JButton("Debug Assert3");
	    
	    jb.addActionListener(new ActionListener() {
	    	
	    	public void actionPerformed(ActionEvent e){
	    		
	    		myassert(false,"Debugging");
	    		
	    	}
	    	
	    });
	    
	    this.add(jb);
	    
	} // end constructor CommandPanel()

	/**
     * A Debugging method
     */
    public void myassert(boolean isTrue,String message){
    	
    	if (isTrue);
    		//empty! do nothing!
    	else // else its false	
    		JOptionPane.showMessageDialog(MapWalkerUI.frame,message);
    
    }
	/**
	 * Paint the map
	 */
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
	} // end paintComponent
    
    /**
     *  Moves the map oposite of northWest so that the player moves to the
     *  northWest.
     */
	public void northWest() {
		
		
		
		int x = (int)(gameWalkPanel.position).getX();
		
        int y = (int)(gameWalkPanel.position).getY();
        
        y--;
        
        x--;
        
        if(gameWalkPanel.walk(x,y))
                	gameWalkPanel.position = new Point(x,y);
                	
        gameWalkPanel.reload();        	
        
	} // end northWest
    
    /**
     * Moves the map oposite of north so that the player moves to the north.
     */
	public void north() {
		
		int x = (int)(gameWalkPanel.position).getX();
		
        int y = (int)(gameWalkPanel.position).getY();
        
        y--;
        
        if (gameWalkPanel.walk(x,y))        
        	gameWalkPanel.position = new Point(x,y);
        
        gameWalkPanel.reload();	
        
	} // end north
    
    /**
     * Moves the map oposite of northEast so that the player moves to the northEast.
     */
	public void northEast() {

		int x = (int)(gameWalkPanel.position).getX();

        int y = (int)(gameWalkPanel.position).getY();

        y--;
        
        x++;
        
        if (gameWalkPanel.walk(x,y))        
        	gameWalkPanel.position = new Point(x,y);
        	
        gameWalkPanel.reload();	
        
	} // end northEast
    
    /**
     * Enters a portal that has a choice of UP or Down as in Mine Shafts
     */
	public void enterUp() {
		
		// get position
		int x = (int)(gameWalkPanel.position).getX();
		
        int y = (int)(gameWalkPanel.position).getY();
		
		// get current map	
		Map tempMap = gameWalkPanel.map;
		
		// get the entrance for current location
		Entrance tempEntrance = tempMap.getEntrance(x,y);
		
		// get portal for this entrance
		Portal portal = (universe.portals).getPortal(tempEntrance.portalName);
		
		// I'm not sure whey I did this
		// make sure this is an entrance in this portal
		if(portal.isPortalEntrance(x,y)){
			
			
			Entrance enterEntrance = portal.getUpEntrance(tempEntrance);
			
			Map enterMap = (universe.maps).getMap(enterEntrance.mapName);
			
			int x1=(int)enterEntrance.location.getX()-5;
			
			int x2=(int)enterEntrance.location.getX()+5;
			
			int y1=(int)enterEntrance.location.getY()-5;
			
			int y2=(int)enterEntrance.location.getY()+5;
			
			gameWalkPanel.setMapView(new Map(enterMap,x1,y1,x2,y2));
			
			gameWalkPanel.setPosition(enterEntrance.location);     
			
		} // end if

	} // end enterUp
    
    /**
     * Moves the map oposite of west so that the player moves to the west.
     */
	public void west() {
	
		int x = (int)(gameWalkPanel.position).getX();
    
        int y = (int)(gameWalkPanel.position).getY();
    
        x--;
        
		if (gameWalkPanel.walk(x,y))            
        	gameWalkPanel.position = new Point(x,y);
        
        gameWalkPanel.reload();	
	
	} // end enter west
    
    /**
     * Moves the map oposite of east so that the player moves to the east.
     */
	public void east() {
		
		int x = (int)(gameWalkPanel.position).getX();
        
        int y = (int)(gameWalkPanel.position).getY();
        
        x++;
		if (gameWalkPanel.walk(x,y))                
        	gameWalkPanel.position = new Point(x,y);
        
        gameWalkPanel.reload();
	} // end enter east
	
	/**
	 * This enters a portal that has only two entrances.
	 */
	public void enter() {
		
		// get a local copy of the current x position
		int x = (int)(gameWalkPanel.position).getX();
		// get a local copy of the current y postition
        int y = (int)(gameWalkPanel.position).getY();
		
		Map tempMap = gameWalkPanel.mapView;
		this.universe = MapWalkerUI.universe;
		Entrance tempEntrance = (Entrance)tempMap.getEntrance(x,y);
		if(MapWalkerUI.universe==null)System.out.println("CommandPanel.univers is null!");
		Portal portal = universe.portals.getPortal(tempEntrance.portalName);
		
		if(portal.isPortalEntrance(x,y)){
			if(tempEntrance==null)System.out.println("tempEntrance is NULL!");
			Entrance enterEntrance = portal.getEnterEntrance(tempEntrance);
			if(MapWalkerUI.universe==null)System.out.println("MapWalkerUI.universe is null!");
			else if(MapWalkerUI.universe.maps==null)System.out.println("universe.maps is null!");
			else if(enterEntrance==null)System.out.println("enterEntrance is null!");
			else if(enterEntrance.mapName==null)System.out.println("enterEntrance.mapName is null!");
	     	
			Map enterMap = ((MapWalkerUI.universe).maps).getMap(enterEntrance.mapName);
			
			int x1=(int)enterEntrance.location.getX()-5;
			
			int x2=(int)enterEntrance.location.getX()+5;
			
			int y1=(int)enterEntrance.location.getY()-5;
			
			int y2=(int)enterEntrance.location.getY()+5;
			
			//Map map=new Map(new World1Map(),0,0,10,10);
			System.out.println("enterEntrance map name:"+enterEntrance.mapName);
		    Map map=((MapWalkerUI.universe).maps).getMap(enterEntrance.mapName);
	        int mapType=map.getMapType();        
	                // This next code is a hack.. will be
	                // changed later. if we get
	                // an already created map it craps out on 2nd display of the map
	                if (mapType==2){
	    				map=MapWalkerUI.universe.maps.getMap("Dungeon1");
	    		   		gameWalkPanel.changeMap(2,map,enterEntrance.location);
	    		   		gameWalkPanel.paintImmediately(0,0,800,800);
	    		   	}	
	    		    if (mapType==3){
	    		    	map=MapWalkerUI.universe.maps.getMap("Cave1");
	    		   		gameWalkPanel.changeMap(3,map,enterEntrance.location);
	    		   		gameWalkPanel.paintImmediately(0,0,800,800);
	    		   	}	
	    		    if (mapType==4){
	    		    	map=MapWalkerUI.universe.maps.getMap("Mine1");
	    		   		gameWalkPanel.changeMap(4,map,enterEntrance.location);
	    		   		gameWalkPanel.paintImmediately(0,0,800,800);
	    		   	}	
	    		    if (mapType==1){
	    		    	map=MapWalkerUI.universe.maps.getMap("World1");
	    		   		gameWalkPanel.changeMap(1,map,enterEntrance.location);
	    		   		gameWalkPanel.paintImmediately(0,0,800,800);
	    		   	}	
	        //gameWalkPanel.changeMap(map.getMapType(),
	        //                        new Map(map,x1,y1,x2,y2),
	        //                        enterEntrance.location);
	    	
	    	gameWalkPanel.paintImmediately(0,0,800,800); 
			
		} // end if
		
	} // end enter method
	
	/**
	 * Moves the map oposite of southWest so that the player moves to the southWest.
	 */
	public void southWest() {
	
		int x = (int)(gameWalkPanel.position).getX();
    
        int y = (int)(gameWalkPanel.position).getY();
    
        x--;
    
        y++;
    	
    	if (gameWalkPanel.walk(x,y))        
        	gameWalkPanel.position = new Point(x,y);
    
        gameWalkPanel.reload();
        
	} // end southWest
		
	/**
	 * Moves the map oposite of south so that the player moves to the south.
	 */	
	public void south() {
		
		int x = (int)(gameWalkPanel.position).getX();
		
        int y = (int)(gameWalkPanel.position).getY();

        y++;
		
		if (gameWalkPanel.walk(x,y))        
        	gameWalkPanel.position = new Point(x,y);
        	
        gameWalkPanel.reload();	

	} // end south
	
	/**
	 * Moves the map oposite of north so that the player moves to the north.
	 */
	public void southEast(){
		
		int x = (int)(gameWalkPanel.position).getX();
		
        int y = (int)(gameWalkPanel.position).getY();
        
        x++;
        
        y++;
		
		if (gameWalkPanel.walk(x,y))                
        	gameWalkPanel.position = new Point(x,y);
        	
        gameWalkPanel.reload();	
        
	} // end southEast
	
	/**
	 * Enters a portal that has a choice of UP or Down as in Mine Shafts
	 */
	public void enterDown(){
		
		int x = (int)(gameWalkPanel.position).getX();
		
        int y = (int)(gameWalkPanel.position).getY();

		Map tempMap = gameWalkPanel.map;
		
		Entrance tempEntrance = (Entrance)tempMap.getEntrance(x,y);
		
		Portal portal = universe.portals.getPortal(tempEntrance.portalName);
		
		if(portal.isPortalEntrance(x,y)){
			
			Entrance enterEntrance = portal.getDownEntrance(tempEntrance);
			
			Map enterMap = (universe.maps).getMap(enterEntrance.mapName);
			
			int x1=(int)enterEntrance.location.getX()-5;
			
			int x2=(int)enterEntrance.location.getX()+5;
			
			int y1=(int)enterEntrance.location.getY()-5;
			
			int y2=(int)enterEntrance.location.getY()+5;
			
			gameWalkPanel.setMapView(new Map(enterMap,x1,y1,x2,y2));
			
			gameWalkPanel.setPosition(enterEntrance.location);     
			
		} // end if
		
	} // end enterDown
	
} //end Class CommandPanel
