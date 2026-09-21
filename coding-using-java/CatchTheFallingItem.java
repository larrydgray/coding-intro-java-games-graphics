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
 * A very simple "catch the falling item" game.
 * <p>
 * An o falls down the screen one row at a time. Slide the = paddle left
 * and right with the arrow keys or A/D to catch it before it hits the
 * bottom row. Each catch scores a point and a new item drops.
 */
public class CatchTheFallingItem {

    private static final int COLS = 20;
    private static final int ROWS = 20;
    private static final int PADDLE_ROW = ROWS - 1;

    // The item falls one row every DROP_EVERY_TICKS ticks, so it moves
    // slower than the paddle, which moves every tick.
    private static final int DROP_EVERY_TICKS = 3;

    private final GameScreen screen = new GameScreen(COLS, ROWS);
    private final ScreenBuffer buffer = screen.getBuffer();
    private final InputHandler input = new InputHandler();
    private final Random random = new Random();

    private int paddleX = COLS / 2;
    private int itemX;
    private int itemY = 1;
    private int dropCounter = 0;
    private int score = 0;

    public static void main(String[] args) {
        new CatchTheFallingItem().start();
    }

    private void start() {
        itemX = random.nextInt(COLS);

        Frame frame = new Frame("Catch the Falling Item");
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

        Timer timer = new Timer(100, e -> tick());
        timer.start();
    }

    private void tick() {
        movePaddle();
        dropItem();
        draw();
        screen.repaint();
    }

    private void movePaddle() {
        if (input.isDown(KeyEvent.VK_LEFT) || input.isDown(KeyEvent.VK_A)) {
            paddleX = Math.max(0, paddleX - 1);
        }
        if (input.isDown(KeyEvent.VK_RIGHT) || input.isDown(KeyEvent.VK_D)) {
            paddleX = Math.min(COLS - 1, paddleX + 1);
        }
    }

    private void dropItem() {
        dropCounter++;
        if (dropCounter < DROP_EVERY_TICKS) {
            return;
        }
        dropCounter = 0;

        itemY++;
        if (itemY >= PADDLE_ROW) {
            if (itemX == paddleX) {
                score++;
            }
            respawnItem();
        }
    }

    private void respawnItem() {
        itemX = random.nextInt(COLS);
        itemY = 1;
    }

    private void draw() {
        buffer.clear(Color.BLACK);
        buffer.write(1, 0, "Score: " + score + "   Arrows/A-D to move", Color.LIGHT_GRAY, Color.BLACK);

        buffer.put(itemX, itemY, 'o', Color.ORANGE, Color.BLACK);
        buffer.put(paddleX, PADDLE_ROW, '=', Color.CYAN, Color.BLACK);
    }
}
