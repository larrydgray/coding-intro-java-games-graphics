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
 * A very simple old-style Asteroids, drawn entirely with straight-line
 * vectors (MiniGraphics.line — no fills) instead of the character grid
 * the other examples use. This is the first example to use
 * GameScreen.getPixelWidth()/getPixelHeight() rather than columns/rows,
 * since MiniGraphics draws in raw pixel coordinates.
 * <p>
 * Rotate with the arrows or A/D, thrust with up/W, fire with SPACE.
 * Everything wraps around the edges of the screen.
 */
public class Asteroids {

    private static final int COLS = 60;
    private static final int ROWS = 30;

    private static final double SHIP_SIZE = 12;
    private static final double ROTATION_SPEED = 5;   // degrees per tick
    private static final double THRUST_ACCEL = 0.25;
    private static final double DRAG = 0.99;
    private static final double MAX_SPEED = 6;
    private static final int INVULNERABLE_TICKS = 90;
    private static final int STARTING_LIVES = 3;

    private static final int MAX_BULLETS = 6;
    private static final double BULLET_SPEED = 8;
    private static final int BULLET_LIFESPAN_TICKS = 45;
    private static final int FIRE_COOLDOWN_TICKS = 10;

    private static final int VERTEX_COUNT = 10;
    private static final double[] TIER_RADIUS = {40, 24, 14};
    private static final int[] TIER_POINTS = {20, 50, 100};
    private static final double SAFE_SPAWN_DISTANCE = 160;

    private final GameScreen screen = new GameScreen(COLS, ROWS);
    private final ScreenBuffer buffer = screen.getBuffer();
    private final InputHandler input = new InputHandler();
    private final Random random = new Random();

    private int pixelWidth;
    private int pixelHeight;

    private double shipX, shipY;
    private double shipVX, shipVY;
    private double shipAngle = 0;
    private int invulnerableTicks = INVULNERABLE_TICKS;
    private int lives = STARTING_LIVES;
    private int fireCooldown = 0;

    private static class Bullet {
        double x, y, vx, vy;
        int life;
        boolean active;
    }

    private static class Asteroid {
        double x, y, vx, vy;
        int tier; // 0 = large, 1 = medium, 2 = small
        double rotation;
        double rotationSpeed;
        double[] vertexScale;
    }

    private final Bullet[] bullets = new Bullet[MAX_BULLETS];
    private final List<Asteroid> asteroids = new ArrayList<>();

    private int score = 0;
    private int wave = 0;
    private boolean gameOver = false;

    public static void main(String[] args) {
        new Asteroids().start();
    }

    private void start() {
        for (int i = 0; i < bullets.length; i++) {
            bullets[i] = new Bullet();
        }

        Frame frame = new Frame("Asteroids");
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

        respawnShip();
        startNextWave();

        Timer timer = new Timer(33, e -> tick());
        timer.start();
    }

    private void tick() {
        if (!gameOver) {
            moveShip();
            moveBullets();
            moveAsteroids();
            checkCollisions();
            if (asteroids.isEmpty()) {
                startNextWave();
            }
        }
        draw();
        screen.repaint();
    }

    // ── Ship ───────────────────────────────────────

    private void moveShip() {
        if (input.isDown(KeyEvent.VK_LEFT) || input.isDown(KeyEvent.VK_A)) shipAngle -= ROTATION_SPEED;
        if (input.isDown(KeyEvent.VK_RIGHT) || input.isDown(KeyEvent.VK_D)) shipAngle += ROTATION_SPEED;
        shipAngle = ((shipAngle % 360) + 360) % 360;

        if (input.isDown(KeyEvent.VK_UP) || input.isDown(KeyEvent.VK_W)) {
            double rad = Math.toRadians(shipAngle);
            shipVX += Math.sin(rad) * THRUST_ACCEL;
            shipVY += -Math.cos(rad) * THRUST_ACCEL;
        }

        shipVX *= DRAG;
        shipVY *= DRAG;
        double speed = Math.hypot(shipVX, shipVY);
        if (speed > MAX_SPEED) {
            shipVX = shipVX / speed * MAX_SPEED;
            shipVY = shipVY / speed * MAX_SPEED;
        }

        shipX = wrap(shipX + shipVX, pixelWidth);
        shipY = wrap(shipY + shipVY, pixelHeight);

        if (fireCooldown > 0) fireCooldown--;
        if (input.isDown(KeyEvent.VK_SPACE) && fireCooldown <= 0) {
            fireBullet();
            fireCooldown = FIRE_COOLDOWN_TICKS;
        }

        if (invulnerableTicks > 0) invulnerableTicks--;
    }

    private void respawnShip() {
        shipX = pixelWidth / 2.0;
        shipY = pixelHeight / 2.0;
        shipVX = 0;
        shipVY = 0;
        shipAngle = 0;
        invulnerableTicks = INVULNERABLE_TICKS;
    }

    // ── Bullets ────────────────────────────────────

    private void fireBullet() {
        for (Bullet bullet : bullets) {
            if (!bullet.active) {
                double rad = Math.toRadians(shipAngle);
                bullet.active = true;
                bullet.x = shipX + Math.sin(rad) * SHIP_SIZE;
                bullet.y = shipY - Math.cos(rad) * SHIP_SIZE;
                bullet.vx = shipVX + Math.sin(rad) * BULLET_SPEED;
                bullet.vy = shipVY - Math.cos(rad) * BULLET_SPEED;
                bullet.life = BULLET_LIFESPAN_TICKS;
                return;
            }
        }
    }

    private void moveBullets() {
        for (Bullet bullet : bullets) {
            if (!bullet.active) continue;
            bullet.x = wrap(bullet.x + bullet.vx, pixelWidth);
            bullet.y = wrap(bullet.y + bullet.vy, pixelHeight);
            bullet.life--;
            if (bullet.life <= 0) {
                bullet.active = false;
            }
        }
    }

    // ── Asteroids ──────────────────────────────────

    private void startNextWave() {
        wave++;
        int count = 2 + wave;
        for (int i = 0; i < count; i++) {
            asteroids.add(spawnAsteroid(0, null));
        }
    }

    private Asteroid spawnAsteroid(int tier, double[] atPosition) {
        Asteroid a = new Asteroid();
        a.tier = tier;

        if (atPosition != null) {
            a.x = atPosition[0];
            a.y = atPosition[1];
        } else {
            double[] pos = randomFarPosition();
            a.x = pos[0];
            a.y = pos[1];
        }

        double angle = random.nextDouble() * Math.PI * 2;
        double speed = 0.6 + random.nextDouble() * 1.4;
        a.vx = Math.cos(angle) * speed;
        a.vy = Math.sin(angle) * speed;
        a.rotationSpeed = (random.nextDouble() - 0.5) * 4;

        a.vertexScale = new double[VERTEX_COUNT];
        for (int i = 0; i < VERTEX_COUNT; i++) {
            a.vertexScale[i] = 0.75 + random.nextDouble() * 0.45;
        }

        return a;
    }

    private double[] randomFarPosition() {
        for (int attempt = 0; attempt < 20; attempt++) {
            double x = random.nextDouble() * pixelWidth;
            double y = random.nextDouble() * pixelHeight;
            if (Math.hypot(x - shipX, y - shipY) > SAFE_SPAWN_DISTANCE) {
                return new double[]{x, y};
            }
        }
        return new double[]{0, 0};
    }

    private void moveAsteroids() {
        for (Asteroid a : asteroids) {
            a.x = wrap(a.x + a.vx, pixelWidth);
            a.y = wrap(a.y + a.vy, pixelHeight);
            a.rotation = (a.rotation + a.rotationSpeed) % 360;
        }
    }

    // ── Collisions ─────────────────────────────────

    private void checkCollisions() {
        List<Asteroid> toAdd = new ArrayList<>();

        for (Bullet bullet : bullets) {
            if (!bullet.active) continue;

            for (int i = asteroids.size() - 1; i >= 0; i--) {
                Asteroid a = asteroids.get(i);
                if (Math.hypot(bullet.x - a.x, bullet.y - a.y) < TIER_RADIUS[a.tier]) {
                    bullet.active = false;
                    score += TIER_POINTS[a.tier];
                    asteroids.remove(i);

                    if (a.tier < TIER_RADIUS.length - 1) {
                        toAdd.add(spawnAsteroid(a.tier + 1, new double[]{a.x, a.y}));
                        toAdd.add(spawnAsteroid(a.tier + 1, new double[]{a.x, a.y}));
                    }
                    break;
                }
            }
        }

        asteroids.addAll(toAdd);

        if (invulnerableTicks <= 0) {
            for (Asteroid a : asteroids) {
                if (Math.hypot(shipX - a.x, shipY - a.y) < TIER_RADIUS[a.tier] + SHIP_SIZE * 0.6) {
                    lives--;
                    if (lives <= 0) {
                        gameOver = true;
                    } else {
                        respawnShip();
                    }
                    break;
                }
            }
        }
    }

    private static double wrap(double value, int max) {
        value %= max;
        if (value < 0) value += max;
        return value;
    }

    // ── Drawing ────────────────────────────────────

    private void draw() {
        buffer.clear(Color.BLACK);

        MiniGraphics g = screen.getMiniGraphics();
        g.clearGraphics();

        for (Asteroid a : asteroids) {
            drawAsteroid(g, a);
        }

        for (Bullet bullet : bullets) {
            if (bullet.active) {
                drawBullet(g, bullet);
            }
        }

        boolean blinkVisible = invulnerableTicks <= 0 || (invulnerableTicks / 6) % 2 == 0;
        if (!gameOver && blinkVisible) {
            drawShip(g);
        }

        buffer.write(1, 0, "Score: " + score + "   Lives: " + Math.max(lives, 0) + "   Wave: " + wave,
                Color.LIGHT_GRAY, Color.BLACK);

        if (gameOver) {
            buffer.write(1, ROWS - 1, "GAME OVER - Final Score: " + score, Color.RED, Color.BLACK);
        }
    }

    private void drawShip(MiniGraphics g) {
        double[] nose = rotate(0, -SHIP_SIZE, shipAngle);
        double[] rearLeft = rotate(-SHIP_SIZE * 0.6, SHIP_SIZE * 0.6, shipAngle);
        double[] rearRight = rotate(SHIP_SIZE * 0.6, SHIP_SIZE * 0.6, shipAngle);

        line(g, shipX + nose[0], shipY + nose[1], shipX + rearLeft[0], shipY + rearLeft[1], Color.WHITE);
        line(g, shipX + rearLeft[0], shipY + rearLeft[1], shipX + rearRight[0], shipY + rearRight[1], Color.WHITE);
        line(g, shipX + rearRight[0], shipY + rearRight[1], shipX + nose[0], shipY + nose[1], Color.WHITE);

        if (input.isDown(KeyEvent.VK_UP) || input.isDown(KeyEvent.VK_W)) {
            double[] flame = rotate(0, SHIP_SIZE * 1.4, shipAngle);
            double[] rearCenter = rotate(0, SHIP_SIZE * 0.6, shipAngle);
            line(g, shipX + rearCenter[0], shipY + rearCenter[1], shipX + flame[0], shipY + flame[1], Color.ORANGE);
        }
    }

    private void drawAsteroid(MiniGraphics g, Asteroid a) {
        double radius = TIER_RADIUS[a.tier];
        double prevX = 0, prevY = 0;

        for (int i = 0; i <= VERTEX_COUNT; i++) {
            int index = i % VERTEX_COUNT;
            double angleDeg = a.rotation + index * (360.0 / VERTEX_COUNT);
            double rad = Math.toRadians(angleDeg);
            double r = radius * a.vertexScale[index];
            double px = a.x + Math.cos(rad) * r;
            double py = a.y + Math.sin(rad) * r;

            if (i > 0) {
                line(g, prevX, prevY, px, py, Color.LIGHT_GRAY);
            }
            prevX = px;
            prevY = py;
        }
    }

    private void drawBullet(MiniGraphics g, Bullet bullet) {
        line(g, bullet.x - 2, bullet.y, bullet.x + 2, bullet.y, Color.YELLOW);
        line(g, bullet.x, bullet.y - 2, bullet.x, bullet.y + 2, Color.YELLOW);
    }

    private static double[] rotate(double localX, double localY, double angleDeg) {
        double rad = Math.toRadians(angleDeg);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);
        return new double[]{localX * cos - localY * sin, localX * sin + localY * cos};
    }

    private static void line(MiniGraphics g, double x1, double y1, double x2, double y2, Color color) {
        g.line((int) Math.round(x1), (int) Math.round(y1), (int) Math.round(x2), (int) Math.round(y2), color);
    }
}
