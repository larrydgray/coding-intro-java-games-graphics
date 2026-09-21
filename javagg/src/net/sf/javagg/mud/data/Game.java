package net.sf.javagg.mud.data;
/**
 * This class will contain game state
 * information and data.
 * 
 * It will also keep all the game world
 * data for items, worlds, realms, rooms etd.
 * 
 * This will be for an individual instance of a game.
 * 
 * 
 * @author Larry Gray(caverdude)
 *
 */
public class Game {
	private String path="c:/java/workspace/jgg-mud/data/";
	public WorldsParser worlds = new WorldsParser(path+"worldss.xml");
	public RealmsParser realmss = new RealmsParser(path+"realms.xml");
	public SubRealmsParser subRealms = new SubRealmsParser(path+"subrealms.xml");
	public RoomsParser rooms = new RoomsParser(path+"rooms.xml");
    public Game(){
    	
    }
}
