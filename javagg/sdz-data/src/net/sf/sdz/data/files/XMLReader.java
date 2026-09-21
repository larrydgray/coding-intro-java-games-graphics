package net.sf.sdz.data.files;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 * Loads a small text-adventure "world" described in adventure.xml (rooms with
 * descriptions and doors between them) using the XML DOM API, then runs a
 * simple console game loop to navigate it.
 */
public class XMLReader {

    public class Room {

        public void cleanDescriptions() {
            description = cleanString(description);
            brief = cleanString(brief);
        }

        /**
         * Collapses a multi-line XML text node's content into a single trimmed line.
         */
        String cleanString(String aString) {
            String[] lines = aString.split("\n");
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                sb.append(line.trim() + " ");
            }
            return sb.toString();
        }
        boolean visited = false;
        String name = "";
        String id = "";
        String description = "";
        String brief = "";
        String n = "";
        String e = "";
        String s = "";
        String w = "";
        String enter = "";
        String l = "";
        String r = "";
    }
    ArrayList<Room> rooms = new ArrayList<Room>();

    public XMLReader() {
        File adventureXMLFile = new File("adventure.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        NodeList roomNodes = null;
        NodeList roomChildNodes = null;
        Room room = null;
        Node roomNode = null;
        Node roomChildNode = null;
        Node attributeNode = null;
        NamedNodeMap attributes = null;
        try {
            db = dbf.newDocumentBuilder();
            Document doc = db.parse(adventureXMLFile);
            roomNodes = doc.getElementsByTagName("room");
            for (int i = 0; i < roomNodes.getLength(); i++) {
                roomNode = roomNodes.item(i);
                room = new Room();
                attributes = roomNode.getAttributes();
                attributeNode = attributes.getNamedItem("name");
                room.name = attributeNode.getNodeValue();
                attributeNode = attributes.getNamedItem("id");
                room.id = attributeNode.getNodeValue();
                roomChildNodes = roomNode.getChildNodes();
                for (int c = 0; c < roomChildNodes.getLength(); c++) {
                    roomChildNode = roomChildNodes.item(c);
                    if (roomChildNode.getNodeName().equals("description")) {
                        room.description = roomChildNode.getTextContent();
                    } else if (roomChildNode.getNodeName().equals("brief")) {
                        room.brief = roomChildNode.getTextContent();
                    } else if (roomChildNode.getNodeName().equals("door")) {
                        attributes = roomChildNode.getAttributes();
                        if (attributes.getNamedItem("dir").getNodeValue().equals("n")) {
                            room.n = attributes.getNamedItem("room").getNodeValue();
                        }
                        if (attributes.getNamedItem("dir").getNodeValue().equals("e")) {
                            room.e = attributes.getNamedItem("room").getNodeValue();
                        }
                        if (attributes.getNamedItem("dir").getNodeValue().equals("s")) {
                            room.s = attributes.getNamedItem("room").getNodeValue();
                        }
                        if (attributes.getNamedItem("dir").getNodeValue().equals("w")) {
                            room.w = attributes.getNamedItem("room").getNodeValue();
                        }
                        if (attributes.getNamedItem("dir").getNodeValue().equals("enter")) {
                            room.enter = attributes.getNamedItem("room").getNodeValue();
                        }
                        if (attributes.getNamedItem("dir").getNodeValue().equals("l")) {
                            room.l = attributes.getNamedItem("room").getNodeValue();
                        }
                        if (attributes.getNamedItem("dir").getNodeValue().equals("r")) {
                            room.r = attributes.getNamedItem("room").getNodeValue();
                        }
                    }
                }
                room.cleanDescriptions();
                rooms.add(room);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        gameLoop();
    }

    void gameLoop() {
        boolean hideBrief = true;
        Scanner scan = new Scanner(System.in);
        System.out.println("\nWelcome to the game Portals Adventure. "
                + "If you need help just ask (by typing help command).\n");
        String command = "";
        Room currentRoom = findRoom("void");
        while (!command.equals("quit")) {
            if (currentRoom != null) {
                if (currentRoom.visited == false) {
                    System.out.println("\n" + currentRoom.name + "\n");
                    System.out.println(currentRoom.description + "\n");
                    currentRoom.visited = true;
                } else {
                    if (!hideBrief) {
                        System.out.println("\n" + currentRoom.name + "\n");
                        System.out.println(currentRoom.brief + "\n");
                        hideBrief = false;
                    }
                }
            }
            System.out.println("What do you want to do?");
            command = scan.nextLine();
            if (command.equals("help")) {
                System.out.println("n goes north");
                System.out.println("e goes east");
                System.out.println("s goes south");
                System.out.println("w goes west");
                System.out.println("enter goes through portal or into something.");
                System.out.println("l goes left");
                System.out.println("r goes right");
                System.out.println("look displays long description");
                System.out.println("examine examines an item");
                System.out.println("get item puts item in your backpack");
                System.out.println("debug shows debug info about the current room");
                System.out.println("quit exits to system");
            } else if (command.equals("debug")) {
                debugRoom(currentRoom);
            } else if (command.equals("enter")) {
                if (currentRoom.enter.equals("")) {
                    System.out.println("\nThere is nothing to enter.\n");
                    hideBrief = true;
                } else {
                    currentRoom = findRoom(currentRoom.enter);
                    if (currentRoom.visited) {
                        hideBrief = false;
                    }
                }
                continue;
            } else if (command.equals("n")) {
                if (currentRoom.n.equals("")) {
                    System.out.println("\nYou can't go that way.\n");
                    hideBrief = true;
                } else {
                    currentRoom = findRoom(currentRoom.n);
                    if (currentRoom.visited) {
                        hideBrief = false;
                    }
                }
                continue;
            } else if (command.equals("e")) {
                if (currentRoom.e.equals("")) {
                    System.out.println("\nYou can't go that way.\n");
                    hideBrief = true;
                } else {
                    currentRoom = findRoom(currentRoom.e);
                    if (currentRoom.visited) {
                        hideBrief = false;
                    }
                }
                continue;
            } else if (command.equals("s")) {
                if (currentRoom.s.equals("")) {
                    System.out.println("\nYou can't go that way.\n");
                    hideBrief = true;
                } else {
                    currentRoom = findRoom(currentRoom.s);
                    if (currentRoom.visited) {
                        hideBrief = false;
                    }
                }
                continue;
            } else if (command.equals("w")) {
                if (currentRoom.w.equals("")) {
                    System.out.println("\nYou can't go that way.\n");
                    hideBrief = true;
                } else {
                    currentRoom = findRoom(currentRoom.w);
                    if (currentRoom.visited) {
                        hideBrief = false;
                    }
                }
                continue;
            } else if (command.equals("l")) {
                if (currentRoom.l.equals("")) {
                    System.out.println("\nYou can't go that way.\n");
                    hideBrief = true;
                } else {
                    currentRoom = findRoom(currentRoom.l);
                    if (currentRoom.visited) {
                        hideBrief = false;
                    }
                }
                continue;
            } else if (command.equals("r")) {
                if (currentRoom.r.equals("")) {
                    System.out.println("\nYou can't go that way.\n");
                    hideBrief = true;
                } else {
                    currentRoom = findRoom(currentRoom.r);
                    if (currentRoom.visited) {
                        hideBrief = false;
                    }
                }
                continue;
            } else if (command.equals("look")) {
                System.out.println("\n" + currentRoom.name + "\n");
                System.out.println(currentRoom.description + "\n");
                hideBrief = true;
                continue;
            } else if ((command.length() >= 7) && (command.substring(0, 7).equals("examine"))) {
                System.out.println("\nYou see nothing special about " + command.substring(8, command.length()));
                hideBrief = true;
                continue;
            } else if ((command.length() >= 3) && (command.substring(0, 3).equals("get"))) {
                System.out.println("\nYou don't feel like carrying " + command.substring(4, command.length()) + " today.");
                hideBrief = true;
                continue;
            } else if (command.equals("quit")) {
                System.out.println("\nYes stop bingeing on text based adventure games and go get some stuff done. Goodbye!");
                break;
            }
            System.out.println("\nWTF? I did not understand that command.");
        }
    }

    Room findRoom(String id) {
        Iterator<Room> roomsIterator = rooms.iterator();
        Room aRoom = null;
        while (roomsIterator.hasNext()) {
            aRoom = roomsIterator.next();
            if (aRoom.id.equals(id)) {
                return aRoom;
            }
        }
        return null;
    }

    void debugRoom(Room aRoom) {
        System.out.println("Room Debug Info");
        System.out.println("Name:" + aRoom.name);
        System.out.println("Visited:" + aRoom.visited);
        System.out.println("Description:" + aRoom.description);
        System.out.println("Brief:" + aRoom.brief);
        System.out.println("n:" + aRoom.n);
        System.out.println("e:" + aRoom.e);
        System.out.println("s:" + aRoom.s);
        System.out.println("w:" + aRoom.w);
        System.out.println("enter:" + aRoom.enter);
        System.out.println("l:" + aRoom.l);
        System.out.println("r:" + aRoom.r);
    }

    public static void main(String args[]) {
        new XMLReader();
    }
}
