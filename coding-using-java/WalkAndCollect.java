// Course example © 2026 Larry D. Gray.
// For enrolled-student educational use; see README.md.

import javax.swing.Timer;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

/**
 * A very simple "walk around and collect treasure" game.
 * <p>
 * Move the @ with the arrow keys or WASD. Reach the $ to win.
 * Touch the g (goblin) and it's game over.
 */
public class WalkAndCollect {

    private static final int COLS = 40;
    private static final int ROWS = 20;

    private final GameScreen screen = new GameScreen(COLS, ROWS);
    private final ScreenBuffer buffer = screen.getBuffer();
    private final InputHandler input = new InputHandler();

    private int playerX = 5;
    private int playerY = 10;

    private final int treasureX = 35;
    private final int treasureY = 5;

    private int goblinX = 20;
    private int goblinY = 10;

    // The goblin only takes a step every GOBLIN_MOVE_EVERY_TICKS ticks,
    // so it wanders slower than the player moves.
    private static final int GOBLIN_MOVE_EVERY_TICKS = 3;
    private int goblinMoveCounter = 0;

    private final Random random = new Random();

    private boolean gameOver = false;

    public static void main(String[] args) {
        new WalkAndCollect().start();
    }

    private void start() {
        Frame frame = new Frame("Walk and Collect");
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

        // Game loop: check input and redraw about 6 times a second.
        Timer timer = new Timer(160, e -> tick());
        timer.start();
    }

    private void tick() {
        if (!gameOver) {
            handleInput();
            moveGoblin();
            checkCollisions();
        }
        draw();
        screen.repaint();
    }

    private void handleInput() {
        int dx = 0;
        int dy = 0;

        if (input.isDown(KeyEvent.VK_LEFT) || input.isDown(KeyEvent.VK_A)) dx = -1;
        if (input.isDown(KeyEvent.VK_RIGHT) || input.isDown(KeyEvent.VK_D)) dx = 1;
        if (input.isDown(KeyEvent.VK_UP) || input.isDown(KeyEvent.VK_W)) dy = -1;
        if (input.isDown(KeyEvent.VK_DOWN) || input.isDown(KeyEvent.VK_S)) dy = 1;

        int newX = playerX + dx;
        int newY = playerY + dy;

        if (newX >= 0 && newX < COLS && newY >= 1 && newY < ROWS - 1) {
            playerX = newX;
            playerY = newY;
        }
    }

    private void moveGoblin() {
        goblinMoveCounter++;
        if (goblinMoveCounter < GOBLIN_MOVE_EVERY_TICKS) {
            return;
        }
        goblinMoveCounter = 0;

        // Pick a random direction: left, right, up, down, or stay put.
        int direction = random.nextInt(5);
        int dx = 0;
        int dy = 0;
        switch (direction) {
            case 0: dx = -1; break;
            case 1: dx = 1; break;
            case 2: dy = -1; break;
            case 3: dy = 1; break;
            default: break; // stand still
        }

        int newX = goblinX + dx;
        int newY = goblinY + dy;

        if (newX >= 0 && newX < COLS && newY >= 1 && newY < ROWS - 1) {
            goblinX = newX;
            goblinY = newY;
        }
    }

    private void checkCollisions() {
        if (playerX == treasureX && playerY == treasureY) {
            gameOver = true;
        } else if (playerX == goblinX && playerY == goblinY) {
            gameOver = true;
        }
    }

    private void draw() {
        buffer.clear(Color.BLACK);
        buffer.write(1, 0, "Arrows/WASD to move. Grab the $ before the g gets you!", Color.LIGHT_GRAY, Color.BLACK);

        buffer.put(treasureX, treasureY, '$', Color.ORANGE, Color.BLACK);
        buffer.put(goblinX, goblinY, 'g', Color.GREEN, Color.BLACK);
        buffer.put(playerX, playerY, '@', Color.WHITE, Color.BLACK);

        if (gameOver) {
            String message = (playerX == treasureX && playerY == treasureY)
                    ? "You found the treasure! You win!"
                    : "The goblin got you! Game over.";
            buffer.write(1, ROWS - 1, message, Color.YELLOW, Color.BLACK);
        }
    }
}
