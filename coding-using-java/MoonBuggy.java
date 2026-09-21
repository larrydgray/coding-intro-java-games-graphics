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
 * A very simple Moon Buggy style game.
 * <p>
 * Unlike the other side-scrollers, you don't steer left/right here — the
 * buggy drives forward across the moon automatically. Your only job is
 * timing: press SPACE to jump over craters in the ground. Land in one
 * while grounded and it's game over. Reach the far side and you win.
 */
public class MoonBuggy {

    private static final int COLS = 50;
    private static final int ROWS = 20;
    private static final int WORLD_WIDTH = 300;

    private static final int GROUND_Y = 15;
    private static final int BUGGY_SCREEN_X = 8;
    private static final int SCROLL_SPEED = 1; // world columns per tick

    // How high (in rows) the buggy is above the ground on each tick of a
    // jump. The jump lasts as long as this array, so it covers
    // JUMP_OFFSETS.length world columns of scroll — craters need to be
    // narrower than that to be jumpable.
    private static final int[] JUMP_OFFSETS = {0, 1, 2, 3, 3, 2, 1, 0};

    private final GameScreen screen = new GameScreen(COLS, ROWS);
    private final ScreenBuffer buffer = screen.getBuffer();
    private final InputHandler input = new InputHandler();
    private final Random random = new Random();

    private final boolean[] crater = new boolean[WORLD_WIDTH];

    private int cameraX = 0;
    private boolean airborne = false;
    private int jumpProgress = 0;

    private boolean gameOver = false;
    private boolean won = false;

    public static void main(String[] args) {
        new MoonBuggy().start();
    }

    private void start() {
        generateTrack();

        Frame frame = new Frame("Moon Buggy");
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

        Timer timer = new Timer(90, e -> tick());
        timer.start();
    }

    /** Lays out craters along the track, leaving safe stretches at both ends. */
    private void generateTrack() {
        int x = 20;
        while (x < WORLD_WIDTH - 20) {
            x += 10 + random.nextInt(15);
            int craterWidth = 2 + random.nextInt(2); // 2 or 3 wide
            for (int i = 0; i < craterWidth && x + i < WORLD_WIDTH; i++) {
                crater[x + i] = true;
            }
            x += craterWidth;
        }
    }

    private void tick() {
        if (!gameOver && !won) {
            handleJump();
            cameraX += SCROLL_SPEED;
            checkTrack();
        }
        draw();
        screen.repaint();
    }

    private void handleJump() {
        if (!airborne && input.isDown(KeyEvent.VK_SPACE)) {
            airborne = true;
            jumpProgress = 0;
            return;
        }
        if (airborne) {
            jumpProgress++;
            if (jumpProgress >= JUMP_OFFSETS.length) {
                airborne = false;
                jumpProgress = 0;
            }
        }
    }

    private void checkTrack() {
        int buggyWorldX = cameraX + BUGGY_SCREEN_X;

        if (buggyWorldX + 2 >= WORLD_WIDTH - 1) {
            won = true;
            return;
        }

        if (!airborne) {
            for (int i = 0; i < 3; i++) {
                if (isCrater(buggyWorldX + i)) {
                    gameOver = true;
                    return;
                }
            }
        }
    }

    private boolean isCrater(int worldX) {
        return worldX >= 0 && worldX < WORLD_WIDTH && crater[worldX];
    }

    private void draw() {
        buffer.clear(Color.BLACK);
        buffer.write(1, 0, "Distance: " + cameraX + "   SPACE to jump craters", Color.LIGHT_GRAY, Color.BLACK);

        // Sparse stars in the sky for a sense of motion.
        for (int screenX = 0; screenX < COLS; screenX++) {
            int worldCol = cameraX + screenX;
            if (worldCol % 9 == 0) {
                buffer.put(screenX, 3, '.', Color.DARK_GRAY, Color.BLACK);
            }
        }

        // Ground surface and the moon rock underneath it, with gaps for craters.
        for (int screenX = 0; screenX < COLS; screenX++) {
            int worldCol = cameraX + screenX;
            boolean hole = isCrater(worldCol);

            if (!hole) {
                buffer.put(screenX, GROUND_Y, '_', Color.LIGHT_GRAY, Color.BLACK);
                for (int y = GROUND_Y + 1; y < ROWS; y++) {
                    buffer.put(screenX, y, '#', Color.GRAY, Color.BLACK);
                }
            }
        }

        int buggyY = GROUND_Y - (airborne ? JUMP_OFFSETS[jumpProgress] : 0);
        buffer.put(BUGGY_SCREEN_X, buggyY, 'o', Color.YELLOW, Color.BLACK);
        buffer.put(BUGGY_SCREEN_X + 1, buggyY, '^', Color.CYAN, Color.BLACK);
        buffer.put(BUGGY_SCREEN_X + 2, buggyY, 'o', Color.YELLOW, Color.BLACK);

        if (gameOver) {
            buffer.write(1, ROWS - 1, "The buggy fell into a crater! Game over.", Color.RED, Color.BLACK);
        } else if (won) {
            buffer.write(1, ROWS - 1, "You made it across the moon! You win!", Color.GREEN, Color.BLACK);
        }
    }
}
