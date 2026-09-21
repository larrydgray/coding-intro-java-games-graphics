// Course example © 2026 Larry D. Gray.
// For enrolled-student educational use; see README.md.

import javax.swing.Timer;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * A very simple "room to room" game, like the classic overworld screens
 * in old Zelda-style games.
 * <p>
 * The world is a 2x2 grid of rooms. Walk the @ off the edge of one room
 * and you'll step into the next one, appearing on the opposite edge.
 * Walking off an edge with no room beyond it just bumps into the wall.
 */
public class RoomToRoom {

    private static final int COLS = 40;
    private static final int ROWS = 21;

    // The box border occupies rows BOX_TOP..BOX_TOP+BOX_HEIGHT-1.
    private static final int BOX_TOP = 1;
    private static final int BOX_HEIGHT = 20;

    // Walkable interior of the box.
    private static final int MIN_X = 1;
    private static final int MAX_X = COLS - 2;
    private static final int MIN_Y = BOX_TOP + 1;
    private static final int MAX_Y = BOX_TOP + BOX_HEIGHT - 2;

    private static final int ROOM_GRID_W = 2;
    private static final int ROOM_GRID_H = 2;

    private final GameScreen screen = new GameScreen(COLS, ROWS);
    private final ScreenBuffer buffer = screen.getBuffer();
    private final InputHandler input = new InputHandler();

    /** One decoration drawn in the interior of a room. */
    private static class Decoration {
        final int x, y;
        final char ch;
        final Color color;

        Decoration(int x, int y, char ch, Color color) {
            this.x = x;
            this.y = y;
            this.ch = ch;
            this.color = color;
        }
    }

    /** One room: a name for its label, an accent color, and some decorations. */
    private static class Room {
        final String name;
        final Color accent;
        final Decoration[] decorations;

        Room(String name, Color accent, Decoration... decorations) {
            this.name = name;
            this.accent = accent;
            this.decorations = decorations;
        }
    }

    // rooms[roomY][roomX]
    private final Room[][] rooms = {
            {
                    new Room("Meadow", Color.GREEN,
                            new Decoration(6, 5, 'T', Color.GREEN),
                            new Decoration(15, 10, 'T', Color.GREEN),
                            new Decoration(30, 16, 'T', Color.GREEN)),
                    new Room("Desert", Color.ORANGE,
                            new Decoration(10, 6, '#', Color.ORANGE),
                            new Decoration(22, 12, '#', Color.ORANGE),
                            new Decoration(33, 8, '#', Color.ORANGE))
            },
            {
                    new Room("Cave", Color.GRAY,
                            new Decoration(8, 7, '*', Color.GRAY),
                            new Decoration(18, 14, '*', Color.GRAY),
                            new Decoration(28, 5, '*', Color.GRAY)),
                    new Room("Lake", Color.CYAN,
                            new Decoration(12, 9, '~', Color.CYAN),
                            new Decoration(13, 9, '~', Color.CYAN),
                            new Decoration(25, 13, '~', Color.CYAN),
                            new Decoration(26, 13, '~', Color.CYAN))
            }
    };

    private int roomX = 0;
    private int roomY = 0;

    private int playerX = COLS / 2;
    private int playerY = MIN_Y + (MAX_Y - MIN_Y) / 2;

    public static void main(String[] args) {
        new RoomToRoom().start();
    }

    private void start() {
        Frame frame = new Frame("Room to Room");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        screen.addKeyListener(input);
        screen.setFocusable(true);

        frame.add(screen);
        frame.pack();
        frame.setVisible(true);
        screen.requestFocusInWindow();

        Timer timer = new Timer(160, e -> tick());
        timer.start();
    }

    private void tick() {
        int dx = 0;
        int dy = 0;

        if (input.isDown(KeyEvent.VK_LEFT) || input.isDown(KeyEvent.VK_A)) dx = -1;
        if (input.isDown(KeyEvent.VK_RIGHT) || input.isDown(KeyEvent.VK_D)) dx = 1;
        if (input.isDown(KeyEvent.VK_UP) || input.isDown(KeyEvent.VK_W)) dy = -1;
        if (input.isDown(KeyEvent.VK_DOWN) || input.isDown(KeyEvent.VK_S)) dy = 1;

        tryMoveHorizontal(dx);
        tryMoveVertical(dy);

        draw();
        screen.repaint();
    }

    private void tryMoveHorizontal(int dx) {
        if (dx == 0) return;

        int newX = playerX + dx;
        if (newX < MIN_X) {
            if (roomX > 0) {
                roomX--;
                playerX = MAX_X;
            }
        } else if (newX > MAX_X) {
            if (roomX < ROOM_GRID_W - 1) {
                roomX++;
                playerX = MIN_X;
            }
        } else {
            playerX = newX;
        }
    }

    private void tryMoveVertical(int dy) {
        if (dy == 0) return;

        int newY = playerY + dy;
        if (newY < MIN_Y) {
            if (roomY > 0) {
                roomY--;
                playerY = MAX_Y;
            }
        } else if (newY > MAX_Y) {
            if (roomY < ROOM_GRID_H - 1) {
                roomY++;
                playerY = MIN_Y;
            }
        } else {
            playerY = newY;
        }
    }

    private void draw() {
        Room room = rooms[roomY][roomX];

        buffer.clear(Color.BLACK);
        buffer.write(1, 0, "Arrows/WASD to walk. Reach the edge to change rooms.", Color.LIGHT_GRAY, Color.BLACK);

        buffer.box(0, BOX_TOP, COLS, BOX_HEIGHT, room.accent, Color.BLACK);
        buffer.write(2, BOX_TOP, " " + room.name + " ", Color.BLACK, room.accent);

        for (Decoration d : room.decorations) {
            buffer.put(d.x, d.y, d.ch, d.color, Color.BLACK);
        }

        buffer.put(playerX, playerY, '@', Color.WHITE, Color.BLACK);
    }
}
