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
 * A very simple 2-player turn-based artillery duel (Scorched Earth
 * style) on destructible, randomly generated hills. Each tank stays put
 * in one spot for the whole game — there's no walking, jumping, or
 * weapon variety, just aim, power, and one shot per turn — so this is
 * deliberately the "artillery" half of that genre, not a Worms clone.
 * <p>
 * This is the course's example of turtle graphics doing real work, not
 * just a novelty: the thick aim/power line and the explosion burst are
 * both drawn with MiniGraphics's turtle API (jumpTo/setAngle/forward),
 * which is a natural fit for "point in a direction and draw a line from
 * here." The small rotating cannon on the active tank uses the same
 * direct rotated-vertex technique as Asteroids.java instead — turtle
 * mode is awkward for many independently-moving/rotating shapes sharing
 * one pen, since you'd have to reposition it before drawing each one.
 * <p>
 * Player 1 (pink, left) and Player 2 (orange, right) alternate turns on
 * the same keyboard. LEFT/RIGHT (or A/D) adjust aim angle, UP/DOWN (or
 * W/S) adjust power, SPACE fires. A hit craters the terrain and damages
 * any tank caught in the blast, tapering off with distance.
 */
public class Artillery {

    private static final int COLS = 72;
    private static final int ROWS = 30;

    private static final double GRAVITY = 0.15;
    private static final double POWER_TO_SPEED = 0.12;
    private static final int POWER_MIN = 10;
    private static final int POWER_MAX = 100;
    private static final int ANGLE_MIN = 0;
    private static final int ANGLE_MAX = 180;

    private static final double TANK_RADIUS = 9;
    private static final double CANNON_SIZE = 14;
    private static final double BLAST_RADIUS = 48;
    private static final int MAX_DAMAGE = 60;
    private static final int STARTING_HEALTH = 100;
    private static final int EXPLOSION_TICKS = 10;

    private static final Color SKY_COLOR = new Color(140, 197, 235);
    private static final Color GROUND_COLOR = new Color(70, 140, 70);
    private static final Color[] TANK_COLOR = {Color.PINK, Color.ORANGE};

    private enum Phase { AIMING, FLYING, EXPLODING, GAME_OVER }

    private final GameScreen screen = new GameScreen(COLS, ROWS);
    private final ScreenBuffer buffer = screen.getBuffer();
    private final InputHandler input = new InputHandler();
    private final Random random = new Random();

    private int pixelWidth;
    private int pixelHeight;
    private int[] groundHeight;

    private final int[] tankX = new int[2];
    private final int[] tankHealth = {STARTING_HEALTH, STARTING_HEALTH};
    private final double[] aimAngle = {60, 120};
    private final double[] power = {55, 55};

    private int currentTank = 0;
    private Phase phase = Phase.AIMING;

    private boolean projectileActive = false;
    private double projectileX, projectileY, projectileVX, projectileVY;

    private double explosionX, explosionY;
    private int explosionTimer = 0;

    public static void main(String[] args) {
        new Artillery().start();
    }

    private void start() {
        Frame frame = new Frame("Artillery");
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

        generateTerrain();
        tankX[0] = pixelWidth / 4;
        tankX[1] = pixelWidth * 3 / 4;

        Timer timer = new Timer(33, e -> tick());
        timer.start();
    }

    /** Random-walk-with-momentum terrain: a natural rolling skyline, cheap to carve craters out of later. */
    private void generateTerrain() {
        groundHeight = new int[pixelWidth];
        double height = pixelHeight * 0.55;
        double velocity = 0;
        double minHeight = pixelHeight * 0.35;
        double maxHeight = pixelHeight * 0.85;

        for (int x = 0; x < pixelWidth; x++) {
            velocity += (random.nextDouble() - 0.5) * 0.6;
            velocity = clamp(velocity, -2, 2);
            height = clamp(height + velocity, minHeight, maxHeight);
            groundHeight[x] = (int) height;
        }
    }

    // ── Turn loop ──────────────────────────────────

    private void tick() {
        if (phase == Phase.AIMING) {
            handleAiming();
        } else if (phase == Phase.FLYING) {
            moveProjectile();
        } else if (phase == Phase.EXPLODING) {
            explosionTimer--;
            if (explosionTimer <= 0) {
                resolveTurn();
            }
        }

        draw();
        screen.repaint();
    }

    private void handleAiming() {
        int active = currentTank;

        if (input.isDown(KeyEvent.VK_LEFT) || input.isDown(KeyEvent.VK_A)) {
            aimAngle[active] = clamp(aimAngle[active] - 1, ANGLE_MIN, ANGLE_MAX);
        }
        if (input.isDown(KeyEvent.VK_RIGHT) || input.isDown(KeyEvent.VK_D)) {
            aimAngle[active] = clamp(aimAngle[active] + 1, ANGLE_MIN, ANGLE_MAX);
        }
        if (input.isDown(KeyEvent.VK_UP) || input.isDown(KeyEvent.VK_W)) {
            power[active] = clamp(power[active] + 1, POWER_MIN, POWER_MAX);
        }
        if (input.isDown(KeyEvent.VK_DOWN) || input.isDown(KeyEvent.VK_S)) {
            power[active] = clamp(power[active] - 1, POWER_MIN, POWER_MAX);
        }

        if (input.wasPressed(KeyEvent.VK_SPACE)) {
            fireProjectile(active);
        }
    }

    private void fireProjectile(int active) {
        double[] dir = aimDirection(aimAngle[active]);
        double speed = power[active] * POWER_TO_SPEED;

        double cannonX = tankX[active] + dir[0] * CANNON_SIZE;
        double cannonY = groundHeight[tankX[active]] - TANK_RADIUS + dir[1] * CANNON_SIZE;

        projectileActive = true;
        projectileX = cannonX;
        projectileY = cannonY;
        projectileVX = dir[0] * speed;
        projectileVY = dir[1] * speed;

        phase = Phase.FLYING;
    }

    private void moveProjectile() {
        projectileVY += GRAVITY;
        projectileX += projectileVX;
        projectileY += projectileVY;

        if (projectileX < 0 || projectileX >= pixelWidth || projectileY > pixelHeight) {
            projectileActive = false;
            nextTurn();
            return;
        }

        int gx = (int) projectileX;
        if (projectileY >= groundHeight[gx]) {
            explode(projectileX, projectileY);
            return;
        }

        int other = 1 - currentTank;
        double otherY = groundHeight[tankX[other]];
        if (Math.hypot(projectileX - tankX[other], projectileY - otherY) < TANK_RADIUS + 4) {
            explode(projectileX, projectileY);
        }
    }

    private void explode(double x, double y) {
        projectileActive = false;
        explosionX = x;
        explosionY = y;
        explosionTimer = EXPLOSION_TICKS;
        phase = Phase.EXPLODING;

        carveCrater(x, y);
        applyDamage(x, y);
    }

    private void carveCrater(double x, double y) {
        int minX = (int) Math.max(0, x - BLAST_RADIUS);
        int maxX = (int) Math.min(pixelWidth - 1, x + BLAST_RADIUS);

        for (int gx = minX; gx <= maxX; gx++) {
            double dx = gx - x;
            double underRoot = BLAST_RADIUS * BLAST_RADIUS - dx * dx;
            if (underRoot < 0) continue;

            double craterFloor = y + Math.sqrt(underRoot);
            if (craterFloor > groundHeight[gx]) {
                groundHeight[gx] = (int) Math.min(craterFloor, pixelHeight);
            }
        }
    }

    private void applyDamage(double x, double y) {
        for (int i = 0; i < 2; i++) {
            double tankSurfaceY = groundHeight[tankX[i]];
            double dist = Math.hypot(x - tankX[i], y - tankSurfaceY);
            if (dist < BLAST_RADIUS) {
                int damage = (int) (MAX_DAMAGE * (1 - dist / BLAST_RADIUS));
                tankHealth[i] = Math.max(0, tankHealth[i] - damage);
            }
        }
    }

    private void resolveTurn() {
        if (tankHealth[0] <= 0 || tankHealth[1] <= 0) {
            phase = Phase.GAME_OVER;
        } else {
            nextTurn();
        }
    }

    private void nextTurn() {
        currentTank = 1 - currentTank;
        phase = Phase.AIMING;
    }

    /** Direction a shot travels for a given aim angle: 0 = right, 90 = straight up, 180 = left. */
    private static double[] aimDirection(double angleDeg) {
        double rad = Math.toRadians(angleDeg);
        return new double[]{Math.cos(rad), -Math.sin(rad)};
    }

    // ── Drawing ────────────────────────────────────

    private void draw() {
        buffer.clear(SKY_COLOR);

        MiniGraphics g = screen.getMiniGraphics();
        g.clearGraphics();

        drawTerrain(g);
        drawTank(g, 0);
        drawTank(g, 1);

        if (phase == Phase.AIMING) {
            drawCannon(g, currentTank);
            drawAimLine(g, currentTank);
        } else if (phase == Phase.FLYING && projectileActive) {
            g.fillCircle((int) projectileX, (int) projectileY, 3, Color.YELLOW);
        } else if (phase == Phase.EXPLODING) {
            drawExplosion(g);
        }

        drawHud();
    }

    private void drawTerrain(MiniGraphics g) {
        int step = 3;
        for (int x = 0; x < pixelWidth; x += step) {
            g.fillRect(x, groundHeight[x], step, pixelHeight - groundHeight[x], GROUND_COLOR);
        }
    }

    private void drawTank(MiniGraphics g, int index) {
        if (tankHealth[index] <= 0) return;
        int cx = tankX[index];
        int cy = groundHeight[cx] - (int) TANK_RADIUS;
        g.fillCircle(cx, cy, (int) TANK_RADIUS, TANK_COLOR[index]);
    }

    private void drawCannon(MiniGraphics g, int index) {
        double[] dir = aimDirection(aimAngle[index]);
        double[] perp = {-dir[1], dir[0]};

        double cx = tankX[index];
        double cy = groundHeight[tankX[index]] - TANK_RADIUS;

        double noseX = cx + dir[0] * CANNON_SIZE;
        double noseY = cy + dir[1] * CANNON_SIZE;
        double rearCX = cx - dir[0] * CANNON_SIZE * 0.4;
        double rearCY = cy - dir[1] * CANNON_SIZE * 0.4;
        double leftX = rearCX + perp[0] * CANNON_SIZE * 0.35;
        double leftY = rearCY + perp[1] * CANNON_SIZE * 0.35;
        double rightX = rearCX - perp[0] * CANNON_SIZE * 0.35;
        double rightY = rearCY - perp[1] * CANNON_SIZE * 0.35;

        line(g, noseX, noseY, leftX, leftY, Color.YELLOW);
        line(g, leftX, leftY, rightX, rightY, Color.YELLOW);
        line(g, rightX, rightY, noseX, noseY, Color.YELLOW);
    }

    /** Turtle-drawn aim/power preview: point the turtle at the aim angle and walk forward. */
    private void drawAimLine(MiniGraphics g, int index) {
        double cx = tankX[index];
        double cy = groundHeight[tankX[index]] - TANK_RADIUS;

        g.turtleColor(Color.YELLOW);
        g.penWidth(3);
        g.jumpTo(cx, cy);
        g.setAngle(-aimAngle[index]);
        g.penDown();
        g.forward(CANNON_SIZE + power[index] * 0.7);
        g.penUp();
        g.penWidth(1);
    }

    /** Turtle-drawn explosion: a radiating starburst of lines, growing over the explosion's brief lifetime. */
    private void drawExplosion(MiniGraphics g) {
        double grownSoFar = EXPLOSION_TICKS - explosionTimer;
        double flareLength = Math.min(10 + grownSoFar * 4, 34);

        g.turtleColor(Color.ORANGE);
        g.penWidth(2);
        for (int i = 0; i < 8; i++) {
            g.jumpTo(explosionX, explosionY);
            g.setAngle(i * 45);
            g.penDown();
            g.forward(flareLength);
            g.penUp();
        }
        g.penWidth(1);
    }

    private void drawHud() {
        buffer.write(1, 0, "ARTILLERY", Color.WHITE, SKY_COLOR);

        buffer.write(1, 1, "P1 (pink) HP: " + Math.max(tankHealth[0], 0), Color.WHITE, SKY_COLOR);
        buffer.write(COLS - 24, 1, "P2 (orange) HP: " + Math.max(tankHealth[1], 0), Color.WHITE, SKY_COLOR);

        String status;
        if (phase == Phase.GAME_OVER) {
            int winner = tankHealth[0] <= 0 ? 1 : 0;
            status = "Player " + (winner + 1) + " wins!";
        } else if (phase == Phase.AIMING) {
            status = "Player " + (currentTank + 1) + ": angle " + (int) aimAngle[currentTank]
                    + " power " + (int) power[currentTank]
                    + "  (arrows/WASD to aim, SPACE to fire)";
        } else {
            status = "";
        }
        buffer.write(1, 2, status, Color.WHITE, SKY_COLOR);
    }

    private static void line(MiniGraphics g, double x1, double y1, double x2, double y2, Color color) {
        g.line((int) Math.round(x1), (int) Math.round(y1), (int) Math.round(x2), (int) Math.round(y2), color);
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(value, max));
    }
}
