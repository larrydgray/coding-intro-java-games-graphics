// Course example © 2026 Larry D. Gray.
// For enrolled-student educational use; see README.md.

import javax.swing.Timer;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Snake, but with continuous free-angle steering instead of the classic
 * grid's 90-degree turns — closer to Slither.io or Tron than to
 * Nibbles. See Snake.java for the grid version; this is a genuinely
 * different architecture, not just a different look.
 * <p>
 * This is the course's clearest example of what turtle graphics is
 * actually for: steering. LEFT/RIGHT (or A/D) call the turtle's own
 * {@code left(degrees)}/{@code right(degrees)} directly, turning its
 * real internal heading; {@code forward(speed)} advances it every tick,
 * with UP/DOWN (or W/S) raising or lowering that speed within a range.
 * The game reads the turtle's own position and angle back out
 * (MiniGraphics.getTurtleX/Y/Angle) as the authoritative state for
 * collision checks and the head marker — genuinely driven by the
 * turtle's angle, not a hand-rolled copy of it.
 * <p>
 * The body itself is drawn with plain thick line segments between
 * recorded positions, not through the turtle's own pen: retracing old
 * history with the same turtle used for steering would jumpTo/setAngle
 * right over the heading that steer() needs intact for the next turn —
 * one pen can't be both "the live steering state" and "a stateless
 * replay of the past" at once. Collision against your own body is a
 * distance check against trail points, not a cell match, and skips the
 * stretch of trail closest to the head so a normal turn doesn't trigger
 * a false "hit."
 * <p>
 * Touching the water is still an instant death (unlike Asteroids, which
 * wraps around instead of dying at an edge) — food just never spawns
 * close enough to the water to force you into a turn tighter than the
 * snake can actually make, and the play field's top edge sits below the
 * HUD text instead of underneath it.
 */
public class Snake2 {

    private static final int COLS = 60;
    private static final int ROWS = 30;

    private static final double BASE_SPEED = 2.0;   // slower default than the original fixed 3.0
    private static final double MIN_SPEED = 1.2;
    private static final double MAX_SPEED = 7.0;
    private static final double SPEED_ACCEL = 0.15; // change per tick while up/down is held
    private static final double TURN_SPEED = 4.0;   // degrees per tick

    private static final double BODY_RADIUS = 6.0;
    private static final int BODY_WIDTH = 11;
    private static final double PIXELS_PER_SEGMENT = 14.0;
    private static final int STARTING_SEGMENTS = 6;
    private static final int FOOD_GROWTH = 2;        // segments (and points) gained per food
    private static final double SAFE_ZONE_DISTANCE = 72.0; // trail nearest the head, in pixels, ignored for self-collision
    private static final double FOOD_RADIUS = 6.0;
    private static final double FOOD_WALL_MARGIN = 50.0; // keep food this far from the water — several snake-widths, enough turning room to reach it safely

    private static final double WATER_THICKNESS = 16.0;
    private static final Color WATER_COLOR = new Color(30, 100, 190);

    private final GameScreen screen = new GameScreen(COLS, ROWS);
    private final ScreenBuffer buffer = screen.getBuffer();
    private final InputHandler input = new InputHandler();
    private final Random random = new Random();

    private int pixelWidth;
    private int pixelHeight;
    private MiniGraphics turtle;

    // The playable area sits inset from the canvas: below the HUD text
    // row up top, and inside the water band drawn around the other
    // three edges. Touching outside these bounds is death.
    private double topHudHeight;
    private double playLeft, playRight, playTop, playBottom;

    // Synced from the turtle every tick after it moves — the turtle
    // itself is the authoritative steering state, these are just a
    // convenient local copy for collision checks, HUD text, etc.
    private double headX, headY;
    private double heading = 0;
    private double speed = BASE_SPEED;
    private int segmentCount = STARTING_SEGMENTS;

    // Every recorded head position, oldest first, trimmed to what the
    // current body length actually needs.
    private final List<double[]> trail = new ArrayList<>();

    private double foodX, foodY;
    private int score = 0;
    private boolean gameOver = false;

    public static void main(String[] args) {
        new Snake2().start();
    }

    private void start() {
        Frame frame = new Frame("Snake 2 (Free Turning)");
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

        pixelWidth = screen.getPixelWidth();
        pixelHeight = screen.getPixelHeight();
        turtle = screen.getMiniGraphics();

        topHudHeight = screen.getCellHeight();
        playLeft = WATER_THICKNESS;
        playRight = pixelWidth - WATER_THICKNESS;
        playTop = topHudHeight + WATER_THICKNESS;
        playBottom = pixelHeight - WATER_THICKNESS;

        headX = (playLeft + playRight) / 2.0;
        headY = (playTop + playBottom) / 2.0;
        turtle.jumpTo(headX, headY);
        turtle.setAngle(heading);

        placeFood();

        Timer timer = new Timer(33, e -> tick());
        timer.start();
    }

    private void tick() {
        if (!gameOver) {
            steer();
            moveHead();
            checkCollisions();
        }
        draw();
        screen.repaint();
    }

    /** The actual turtle-graphics steering: turn the turtle's own heading left/right, in degrees. */
    private void steer() {
        if (input.isDown(KeyEvent.VK_LEFT) || input.isDown(KeyEvent.VK_A)) {
            turtle.left(TURN_SPEED);
        }
        if (input.isDown(KeyEvent.VK_RIGHT) || input.isDown(KeyEvent.VK_D)) {
            turtle.right(TURN_SPEED);
        }

        if (input.isDown(KeyEvent.VK_UP) || input.isDown(KeyEvent.VK_W)) {
            speed = Math.min(MAX_SPEED, speed + SPEED_ACCEL);
        }
        if (input.isDown(KeyEvent.VK_DOWN) || input.isDown(KeyEvent.VK_S)) {
            speed = Math.max(MIN_SPEED, speed - SPEED_ACCEL);
        }
    }

    private void moveHead() {
        // penUp: this forward() is moving the turtle to track the head's
        // real position, not drawing — the actual trail is redrawn from
        // the recorded history in draw() instead. (Retracing history
        // with this same turtle would also overwrite its heading, which
        // has to stay put as the source of truth for the next turn.)
        turtle.penUp();
        turtle.forward(speed);

        headX = turtle.getTurtleX();
        headY = turtle.getTurtleY();
        heading = turtle.getTurtleAngle();

        trail.add(new double[]{headX, headY});
        trimTrail();
    }

    /**
     * Keeps only as much trail as the current body (plus the
     * self-collision safe zone) actually needs, measured by real path
     * distance rather than a point count — necessary now that speed
     * varies, so consecutive trail points aren't a fixed distance apart.
     */
    private void trimTrail() {
        double neededDistance = segmentCount * PIXELS_PER_SEGMENT + SAFE_ZONE_DISTANCE;
        int cutoff = indexAtDistanceFromHead(neededDistance);
        if (cutoff > 0) {
            trail.subList(0, cutoff).clear();
        }
    }

    /** Walks backward from the newest trail point until it has covered targetDistance, returning that point's index. */
    private int indexAtDistanceFromHead(double targetDistance) {
        double accumulated = 0;
        for (int i = trail.size() - 1; i > 0; i--) {
            double[] a = trail.get(i);
            double[] b = trail.get(i - 1);
            accumulated += Math.hypot(a[0] - b[0], a[1] - b[1]);
            if (accumulated >= targetDistance) {
                return i - 1;
            }
        }
        return 0;
    }

    private void checkCollisions() {
        if (headX < playLeft || headX > playRight || headY < playTop || headY > playBottom) {
            gameOver = true;
            return;
        }

        int checkUpTo = indexAtDistanceFromHead(SAFE_ZONE_DISTANCE);
        for (int i = 0; i < checkUpTo; i++) {
            double[] p = trail.get(i);
            if (Math.hypot(headX - p[0], headY - p[1]) < BODY_RADIUS * 1.5) {
                gameOver = true;
                return;
            }
        }

        if (Math.hypot(headX - foodX, headY - foodY) < BODY_RADIUS + FOOD_RADIUS) {
            segmentCount += FOOD_GROWTH;
            score += FOOD_GROWTH;
            placeFood();
        }
    }

    /** Food always spawns FOOD_WALL_MARGIN or more from the water, so reaching it never forces a turn tighter than the snake can make. */
    private void placeFood() {
        do {
            foodX = playLeft + FOOD_WALL_MARGIN + random.nextDouble() * (playRight - playLeft - 2 * FOOD_WALL_MARGIN);
            foodY = playTop + FOOD_WALL_MARGIN + random.nextDouble() * (playBottom - playTop - 2 * FOOD_WALL_MARGIN);
        } while (Math.hypot(foodX - headX, foodY - headY) < 60);
    }

    // ── Drawing ────────────────────────────────────

    private void draw() {
        buffer.clear(Color.BLACK);
        buffer.write(1, 0, "Score: " + score + "   Speed: " + String.format("%.1f", speed)
                        + "   Left/Right turn, Up/Down speed up/slow down",
                Color.LIGHT_GRAY, Color.BLACK);

        MiniGraphics g = screen.getMiniGraphics();
        g.clearGraphics();

        drawWater(g);
        g.fillCircle((int) foodX, (int) foodY, (int) FOOD_RADIUS, Color.RED);
        drawBody(g);
        if (!gameOver) {
            drawHead(g);
        }

        if (gameOver) {
            buffer.write(1, ROWS - 1, "Game over! Final score: " + score, Color.RED, Color.BLACK);
        }
    }

    /** A thick blue band around the play field — touch it and you die, same as any other wall. */
    private void drawWater(MiniGraphics g) {
        int thickness = (int) WATER_THICKNESS;
        int top = (int) topHudHeight;

        g.fillRect(0, top, pixelWidth, thickness, WATER_COLOR);
        g.fillRect(0, pixelHeight - thickness, pixelWidth, thickness, WATER_COLOR);
        g.fillRect(0, top + thickness, thickness, pixelHeight - top - 2 * thickness, WATER_COLOR);
        g.fillRect(pixelWidth - thickness, top + thickness, thickness, pixelHeight - top - 2 * thickness, WATER_COLOR);
    }

    /**
     * Draws the body as plain thick line segments between recorded
     * points, not through the turtle's own pen — reusing the movement
     * turtle here to retrace history would jumpTo/setAngle right over
     * its current heading, which steer() needs intact for the next turn.
     */
    private void drawBody(MiniGraphics g) {
        for (int i = 1; i < trail.size(); i++) {
            double[] from = trail.get(i - 1);
            double[] to = trail.get(i);
            line(g, from[0], from[1], to[0], to[1], BODY_WIDTH, Color.GREEN);
        }
    }

    /** A small triangle pointing along the current heading — turns smoothly, same technique as the Asteroids ship. */
    private void drawHead(MiniGraphics g) {
        double size = BODY_RADIUS * 2.2;
        double rad = Math.toRadians(heading);
        double dirX = Math.cos(rad);
        double dirY = Math.sin(rad);
        double perpX = -dirY;
        double perpY = dirX;

        double noseX = headX + dirX * size;
        double noseY = headY + dirY * size;
        double rearCX = headX - dirX * size * 0.5;
        double rearCY = headY - dirY * size * 0.5;
        double leftX = rearCX + perpX * size * 0.45;
        double leftY = rearCY + perpY * size * 0.45;
        double rightX = rearCX - perpX * size * 0.45;
        double rightY = rearCY - perpY * size * 0.45;

        line(g, noseX, noseY, leftX, leftY, Color.YELLOW);
        line(g, leftX, leftY, rightX, rightY, Color.YELLOW);
        line(g, rightX, rightY, noseX, noseY, Color.YELLOW);
    }

    private static void line(MiniGraphics g, double x1, double y1, double x2, double y2, Color color) {
        line(g, x1, y1, x2, y2, 1, color);
    }

    private static void line(MiniGraphics g, double x1, double y1, double x2, double y2, int width, Color color) {
        g.line((int) Math.round(x1), (int) Math.round(y1), (int) Math.round(x2), (int) Math.round(y2), width, color);
    }
}
