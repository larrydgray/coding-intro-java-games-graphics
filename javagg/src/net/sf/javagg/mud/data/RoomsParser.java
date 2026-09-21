package net.sf.javagg.mud.data;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import net.sourceforge.javagg.mud.data.universe.Exit;
import net.sourceforge.javagg.mud.data.universe.Room;

import org.jdom2.Element;
public class RoomsParser extends ParserXml {
    private ArrayList<Room> rooms = new ArrayList<>();
	public RoomsParser(String fileName) {
		super(fileName);
		Element roomsElement = (Element)doc.getContent(0);
		List<Element> roomElements=roomsElement.getChildren("room");
		ListIterator<Element> roomsIterator = roomElements.listIterator();
		while(roomsIterator.hasNext()){
			Element room = roomsIterator.next();
			rooms.add(getRoom(room));
		}		
	}
	private Room getRoom(Element aRoom){
		Room room= new Room();
		Element roomDescription = (Element)aRoom.getChild("descript");
		Element roomWeather = (Element)aRoom.getChild("weather)");
		String roomId=aRoom.getAttributeValue("id");
		String roomName=aRoom.getAttributeValue("name");
		String roomSubRealm=aRoom.getAttributeValue("subrealm");
		List<Element> roomExits = aRoom.getChildren("exit");
		ListIterator<Element> exitsIterator = roomExits.listIterator();
		while(exitsIterator.hasNext()){
			Element exit= exitsIterator.next();
			room.addExit(getExit(exit));
		}
		room.setDescription(roomDescription.getTextTrim());
		room.setWeather(roomWeather.getTextTrim());
		room.setID(roomId);
		room.setName(roomName);
		room.setSubRealm(roomSubRealm);
		
		return room;
	}
	private Exit getExit(Element anExit){
		Exit exit=new Exit();
		
		exit.setCommand( anExit.getAttributeValue("command"));
		exit.setRoom(anExit.getAttributeValue("room"));
		exit.setDescription(anExit.getTextTrim());
		
		return exit;
	}

}
