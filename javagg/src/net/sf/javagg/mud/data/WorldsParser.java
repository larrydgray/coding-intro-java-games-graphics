package net.sf.javagg.mud.data;

import net.sourceforge.javagg.mud.data.universe.World;

import org.jdom2.Content;
import org.jdom2.Element;
import org.jdom2.util.IteratorIterable;

public class WorldsParser extends ParserXml {

	public WorldsParser(String fileName) {
		super(fileName);
		
		Element worldsElement = (Element)doc.getContent(0);
		Element worldElement = (Element)worldsElement.getChild("world");
		Element descript = (Element)worldElement.getChild("descript");
		String name=worldElement.getAttributeValue("name");
		String id=worldElement.getAttributeValue("id");
		String description=descript.getTextTrim();
		world.setName(name);
		world.setID(id);
		world.setDescription(description);
		
	
	}
    
	private World world = new World();

	public World getWorld() {
		return world;
	}
	public static void main(String args[]){
		WorldsParser parser = new WorldsParser(dataPath+"worlds.xml");
		
	}
}
