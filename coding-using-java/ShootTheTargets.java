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
 * A very simple shooting-gallery game.
 * <p>
 * Slide the ^ turret left/right with the arrow keys or A/D. Press SPACE
 * to fire a single bullet straight up. Hit a big X target to score a
 * point and make it respawn somewhere new.
 */
public class ShootTheTargets {

    private static final int COLS = 30;
    private static final int ROWS = 20;
    private static final int PLAYER_ROW = ROWS - 1;

    // Targets only take a step every TARGET_MOVE_EVERY_TICKS ticks, so
    // they drift side to side instead of racing across the screen.
    private static final int TARGET_MOVE_EVERY_TICKS = 4;

    private final GameScreen screen = new GameScreen(COLS, ROWS);
    private final ScreenBuffer buffer = screen.getBuffer();
    private final InputHandler input = new InputHandler();
    private final Random random = new Random();

    private int playerX = COLS / 2;

    private boolean bulletActive = false;
    private int bulletX;
    private int bulletY;

    private final Target[] targets = {
            new Target(2, 2, 1),
            new Target(20, 5, -1)
    };

    private int targetMoveCounter = 0;
    private int score = 0;

    /** A big target made of a block of cells, TARGET_WIDTH x TARGET_HEIGHT. */
    private static class Target {
        static final int WIDTH = 4;
        static final int HEIGHT = 2;

        int x;
        int y;
        int dx;

        Target(int x, int y, int dx) {
            this.x = x;
            this.y = y;
            this.dx = dx;
        }

        boolean contains(int px, int py) {
            return px >= x && px < x + WIDTH && py >= y && py < y + HEIGHT;
        }

        void step() {
            x += dx;
            if (x <= 0) {
                x = 0;
                dx = 1;
            } else if (x + WIDTH >= COLS) {
                x = COLS - WIDTH;
                dx = -1;
            }
        }

        void respawn(Random random) {
            x = random.nextInt(COLS - WIDTH);
            y = 1 + random.nextInt(8);
            dx = random.nextBoolean() ? 1 : -1;
        }
    }

    public static void main(String[] args) {
        new ShootTheTargets().start();
    }

    private void start() {
        Frame frame = new Frame("Shoot the Targets");
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

        Timer timer = new Timer(80, e -> tick());
        timer.start();
    }

    private void tick() {
        movePlayer();
        fireBullet();
        moveBullet();
        moveTargets();
        checkHits();
        draw();
        screen.repaint();
    }

    private void movePlayer() {
        if (input.isDown(KeyEvent.VK_LEFT) || input.isDown(KeyEvent.VK_A)) {
            playerX = Math.max(0, playerX - 1);
        }
        if (input.isDown(KeyEvent.VK_RIGHT) || input.isDown(KeyEvent.VK_D)) {
            playerX = Math.min(COLS - 1, playerX + 1);
        }
    }

    private void fireBullet() {
        if (!bulletActive && input.isDown(KeyEvent.VK_SPACE)) {
            bulletActive = true;
            bulletX = playerX;
            bulletY = PLAYER_ROW - 1;
        }
    }

    private void moveBullet() {
        if (!bulletActive) {
            return;
        }
        bulletY--;
        if (bulletY < 1) {
            bulletActive = false;
        }
    }

    private void moveTargets() {
        targetMoveCounter++;
        if (targetMoveCounter < TARGET_MOVE_EVERY_TICKS) {
            return;
        }
        targetMoveCounter = 0;

        for (Target target : targets) {
            target.step();
        }
    }

    private void checkHits() {
        if (!bulletActive) {
            return;
        }
        for (Target target : targets) {
            if (target.contains(bulletX, bulletY)) {
                score++;
                target.respawn(random);
                bulletActive = false;
                break;
            }
        }
    }

    private void draw() {
        buffer.clear(Color.BLACK);
        buffer.write(1, 0, "Score: " + score + "   Arrows/A-D move, Space to shoot", Color.LIGHT_GRAY, Color.BLACK);

        for (Target target : targets) {
            for (int ty = 0; ty < Target.HEIGHT; ty++) {
                for (int tx = 0; tx < Target.WIDTH; tx++) {
                    buffer.put(target.x + tx, target.y + ty, 'X', Color.RED, Color.BLACK);
                }
            }
        }

        if (bulletActive) {
            buffer.put(bulletX, bulletY, '|', Color.YELLOW, Color.BLACK);
        }

        buffer.put(playerX, PLAYER_ROW, '^', Color.WHITE, Color.BLACK);
    }
}
