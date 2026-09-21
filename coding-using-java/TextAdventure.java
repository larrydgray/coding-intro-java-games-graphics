// Course example © 2026 Larry D. Gray.
// For enrolled-student educational use; see README.md.

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A very simple text adventure, loading its rooms from adventure.xml (a
 * set of &lt;room&gt; elements with a description, a brief summary, and
 * &lt;door dir="..." room="..."/&gt; exits).
 * <p>
 * Unlike the other examples, this one doesn't run on a Timer — it's
 * event-driven: nothing happens until you type a command and press
 * ENTER. It's also the first example to use ScreenBuffer's terminal-style
 * print/println (the same ones behind Demo.java's original design)
 * instead of drawing everything from scratch every tick.
 */
public class TextAdventure {

    private static final int COLS = 70;
    private static final int ROWS = 24;

    private final GameScreen screen = new GameScreen(COLS, ROWS);
    private final ScreenBuffer buffer = screen.getBuffer();

    private static class Room {
        String id = "";
        String name = "";
        String description = "";
        String brief = "";
        boolean visited = false;
        final Map<String, String> exits = new LinkedHashMap<>();
    }

    private final List<Room> rooms = new ArrayList<>();
    private Room currentRoom;

    private final StringBuilder inputLine = new StringBuilder();
    private int inputRow;
    private boolean gameOver = false;

    public static void main(String[] args) {
        new TextAdventure().start();
    }

    private void start() {
        Frame frame = new Frame("Portals Adventure");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        screen.setFocusable(true);
        screen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPressed(e);
            }

            @Override
            public void keyTyped(KeyEvent e) {
                handleKeyTyped(e);
            }
        });

        frame.add(screen);
        frame.pack();
        frame.setVisible(true);
        screen.requestFocusInWindow();

        buffer.clear(Color.BLACK);

        try {
            loadWorld("adventure.xml");
        } catch (Exception e) {
            buffer.println("Could not load adventure.xml. Make sure it's in this folder.", Color.RED);
            screen.repaint();
            return;
        }

        currentRoom = findRoom("void");
        if (currentRoom == null) {
            buffer.println("adventure.xml has no room with id \"void\" to start in.", Color.RED);
            screen.repaint();
            return;
        }

        buffer.println("Welcome to Portals Adventure.", Color.CYAN);
        buffer.println("Type 'help' any time to see the list of commands.", Color.CYAN);
        enterRoom(currentRoom);
        showPrompt();
        screen.repaint();
    }

    // ── Loading the world ─────────────────────────

    private void loadWorld(String fileName) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(fileName));

        NodeList roomNodes = doc.getElementsByTagName("room");
        for (int i = 0; i < roomNodes.getLength(); i++) {
            Element roomElement = (Element) roomNodes.item(i);
            Room room = new Room();
            room.id = roomElement.getAttribute("id");
            room.name = roomElement.getAttribute("name");

            NodeList children = roomElement.getChildNodes();
            for (int c = 0; c < children.getLength(); c++) {
                Node child = children.item(c);
                if (!(child instanceof Element)) continue;
                Element childElement = (Element) child;

                switch (childElement.getTagName()) {
                    case "description":
                        room.description = cleanText(childElement.getTextContent());
                        break;
                    case "brief":
                        room.brief = cleanText(childElement.getTextContent());
                        break;
                    case "door":
                        room.exits.put(childElement.getAttribute("dir"), childElement.getAttribute("room"));
                        break;
                    default:
                        break;
                }
            }
            rooms.add(room);
        }
    }

    /** Collapses a multi-line, indented XML text node into a single trimmed line. */
    private static String cleanText(String text) {
        StringBuilder sb = new StringBuilder();
        for (String line : text.split("\n")) {
            sb.append(line.trim()).append(' ');
        }
        return sb.toString().trim();
    }

    private Room findRoom(String id) {
        for (Room room : rooms) {
            if (room.id.equals(id)) {
                return room;
            }
        }
        return null;
    }

    // ── Input line (typed command, not a held key) ────

    private void handleKeyTyped(KeyEvent e) {
        if (gameOver) return;

        char c = e.getKeyChar();
        if (c >= 32 && c < 127 && inputLine.length() < COLS - 3) {
            inputLine.append(c);
            redrawInputLine();
            screen.repaint();
        }
    }

    private void handleKeyPressed(KeyEvent e) {
        if (gameOver) return;

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            submitCommand();
        } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            if (inputLine.length() > 0) {
                inputLine.deleteCharAt(inputLine.length() - 1);
                redrawInputLine();
                screen.repaint();
            }
        }
    }

    private void showPrompt() {
        inputLine.setLength(0);
        inputRow = buffer.getCursorY();
        redrawInputLine();
    }

    private void redrawInputLine() {
        StringBuilder line = new StringBuilder("> ").append(inputLine);
        while (line.length() < COLS) {
            line.append(' ');
        }
        buffer.write(0, inputRow, line.substring(0, COLS), Color.WHITE, Color.BLACK);
    }

    // ── Commands ───────────────────────────────────

    private void submitCommand() {
        String command = inputLine.toString().trim();

        buffer.setCursor(0, inputRow);
        buffer.println("> " + command, Color.GRAY);

        if (!command.isEmpty()) {
            runCommand(command);
        }

        if (!gameOver) {
            buffer.println();
            showPrompt();
        }
        screen.repaint();
    }

    private void runCommand(String command) {
        int space = command.indexOf(' ');
        String verb = (space == -1 ? command : command.substring(0, space)).toLowerCase();
        String rest = (space == -1 ? "" : command.substring(space + 1).trim());

        switch (verb) {
            case "help":
                printHelp();
                break;
            case "debug":
                printDebug();
                break;
            case "look":
                buffer.println(currentRoom.name, Color.YELLOW);
                buffer.println(currentRoom.description, Color.WHITE);
                break;
            case "examine":
                if (rest.isEmpty()) {
                    buffer.println("Examine what?", Color.RED);
                } else {
                    buffer.println("You see nothing special about " + rest + ".", Color.WHITE);
                }
                break;
            case "get":
                if (rest.isEmpty()) {
                    buffer.println("Get what?", Color.RED);
                } else {
                    buffer.println("You don't feel like carrying " + rest + " today.", Color.WHITE);
                }
                break;
            case "quit":
                buffer.println("Thanks for playing. Close the window whenever you're ready.", Color.GREEN);
                gameOver = true;
                break;
            default:
                if (currentRoom.exits.containsKey(verb)) {
                    tryMove(verb);
                } else {
                    buffer.println("I don't understand that. Try 'help'.", Color.RED);
                }
                break;
        }
    }

    private void tryMove(String direction) {
        String targetId = currentRoom.exits.get(direction);
        if (targetId == null || targetId.isEmpty()) {
            buffer.println("You can't go that way.", Color.RED);
            return;
        }

        Room target = findRoom(targetId);
        if (target == null) {
            buffer.println("That path leads nowhere (yet).", Color.RED);
            return;
        }

        currentRoom = target;
        enterRoom(currentRoom);
    }

    private void enterRoom(Room room) {
        buffer.println();
        buffer.println(room.name, Color.YELLOW);
        buffer.println(room.visited ? room.brief : room.description, Color.WHITE);
        room.visited = true;
    }

    private void printHelp() {
        buffer.println("Directions: n s e w enter l r (only some work in each room)", Color.CYAN);
        buffer.println("look             - show the full description again", Color.CYAN);
        buffer.println("examine <thing>  - look closer at something", Color.CYAN);
        buffer.println("get <thing>      - try to pick something up", Color.CYAN);
        buffer.println("debug            - show this room's raw data", Color.CYAN);
        buffer.println("quit             - end the game", Color.CYAN);
    }

    private void printDebug() {
        buffer.println("id=" + currentRoom.id + "  visited=" + currentRoom.visited, Color.GRAY);
        for (Map.Entry<String, String> exit : currentRoom.exits.entrySet()) {
            buffer.println("  " + exit.getKey() + " -> " + exit.getValue(), Color.GRAY);
        }
    }
}
