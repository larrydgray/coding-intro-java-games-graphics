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
 * A very simple side-scrolling flying shooter, in the spirit of old
 * side-scrollers like Moon Buggy or classic arcade fighters.
 * <p>
 * The world is much wider than the screen. Fly the &gt; right/left/up/down
 * with the arrows or WASD; the camera follows you and keeps you roughly
 * centered on screen. Press SPACE to fire straight ahead.
 * <p>
 * There are two kinds of enemies: the red &lt; always flies one way
 * (straight toward you), while the magenta W patrols back and forth
 * between two points.
 */
public class SideScrollerShooter {

    private static final int COLS = 50;
    private static final int ROWS = 20;
    private static final int WORLD_WIDTH = 240;

    private static final int MIN_Y = 1;
    private static final int MAX_Y = ROWS - 2;

    // Enemies only take a step every ENEMY_MOVE_EVERY_TICKS ticks, so
    // they drift instead of racing across the screen.
    private static final int ENEMY_MOVE_EVERY_TICKS = 3;

    private final GameScreen screen = new GameScreen(COLS, ROWS);
    private final ScreenBuffer buffer = screen.getBuffer();
    private final InputHandler input = new InputHandler();
    private final Random random = new Random();

    private int playerWorldX = 5;
    private int playerY = ROWS / 2;

    private boolean bulletActive = false;
    private int bulletWorldX;
    private int bulletY;

    private enum EnemyType { STRAIGHT, PATROL }

    private static class Enemy {
        EnemyType type;
        int worldX;
        int y;
        int dir;          // -1 or 1
        int patrolMinX;   // only used by PATROL enemies
        int patrolMaxX;
    }

    private final Enemy[] enemies = new Enemy[6];

    private int enemyMoveCounter = 0;
    private int score = 0;

    public static void main(String[] args) {
        new SideScrollerShooter().start();
    }

    private void start() {
        for (int i = 0; i < enemies.length; i++) {
            Enemy enemy = new Enemy();
            enemy.type = (i % 3 == 0) ? EnemyType.PATROL : EnemyType.STRAIGHT;
            spawnEnemy(enemy, true);
            enemies[i] = enemy;
        }

        Frame frame = new Frame("Side Scroller Shooter");
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
        moveEnemies();
        checkHits();
        draw();
        screen.repaint();
    }

    /** World column shown at the left edge of the screen right now. */
    private int cameraX() {
        int camera = playerWorldX - COLS / 2;
        return clamp(camera, 0, WORLD_WIDTH - COLS);
    }

    private void movePlayer() {
        int dx = 0;
        int dy = 0;

        if (input.isDown(KeyEvent.VK_LEFT) || input.isDown(KeyEvent.VK_A)) dx = -1;
        if (input.isDown(KeyEvent.VK_RIGHT) || input.isDown(KeyEvent.VK_D)) dx = 1;
        if (input.isDown(KeyEvent.VK_UP) || input.isDown(KeyEvent.VK_W)) dy = -1;
        if (input.isDown(KeyEvent.VK_DOWN) || input.isDown(KeyEvent.VK_S)) dy = 1;

        playerWorldX = clamp(playerWorldX + dx, 1, WORLD_WIDTH - 2);
        playerY = clamp(playerY + dy, MIN_Y, MAX_Y);
    }

    private void fireBullet() {
        if (!bulletActive && input.isDown(KeyEvent.VK_SPACE)) {
            bulletActive = true;
            bulletWorldX = playerWorldX + 1;
            bulletY = playerY;
        }
    }

    private void moveBullet() {
        if (!bulletActive) {
            return;
        }
        bulletWorldX += 2;
        if (bulletWorldX > cameraX() + COLS) {
            bulletActive = false;
        }
    }

    private void moveEnemies() {
        enemyMoveCounter++;
        if (enemyMoveCounter < ENEMY_MOVE_EVERY_TICKS) {
            return;
        }
        enemyMoveCounter = 0;

        int camera = cameraX();
        for (Enemy enemy : enemies) {
            enemy.worldX += enemy.dir;

            if (enemy.type == EnemyType.PATROL) {
                if (enemy.worldX <= enemy.patrolMinX) {
                    enemy.worldX = enemy.patrolMinX;
                    enemy.dir = 1;
                } else if (enemy.worldX >= enemy.patrolMaxX) {
                    enemy.worldX = enemy.patrolMaxX;
                    enemy.dir = -1;
                }
            } else if (enemy.worldX < camera - 2) {
                // Flew off the left side of the screen — bring it back
                // in ahead of the player so the action keeps coming.
                spawnEnemy(enemy, false);
            }
        }
    }

    private void spawnEnemy(Enemy enemy, boolean initial) {
        int camera = cameraX();
        enemy.y = MIN_Y + random.nextInt(MAX_Y - MIN_Y + 1);

        if (enemy.type == EnemyType.STRAIGHT) {
            int aheadStart = initial
                    ? camera + 10 + random.nextInt(WORLD_WIDTH - camera - 12)
                    : camera + COLS + random.nextInt(20);
            enemy.worldX = Math.min(WORLD_WIDTH - 2, aheadStart);
            enemy.dir = -1;
        } else {
            int rangeStart = initial
                    ? 10 + random.nextInt(WORLD_WIDTH - 20)
                    : Math.min(WORLD_WIDTH - 12, camera + COLS / 2 + random.nextInt(COLS));
            enemy.patrolMinX = rangeStart;
            enemy.patrolMaxX = Math.min(WORLD_WIDTH - 2, rangeStart + 6 + random.nextInt(6));
            enemy.worldX = enemy.patrolMinX;
            enemy.dir = 1;
        }
    }

    private void checkHits() {
        if (!bulletActive) {
            return;
        }
        for (Enemy enemy : enemies) {
            // Tolerance of 1 since the bullet moves 2 world-columns a tick.
            if (Math.abs(enemy.worldX - bulletWorldX) <= 1 && enemy.y == bulletY) {
                score++;
                spawnEnemy(enemy, false);
                bulletActive = false;
                break;
            }
        }
    }

    private void draw() {
        int camera = cameraX();

        buffer.clear(Color.BLACK);
        buffer.write(1, 0, "Score: " + score + "   Arrows/WASD to fly, Space to shoot", Color.LIGHT_GRAY, Color.BLACK);

        // A sparse scrolling starfield so you can see the world moving.
        for (int screenX = 0; screenX < COLS; screenX++) {
            int worldCol = camera + screenX;
            if (worldCol % 8 == 0) {
                buffer.put(screenX, 4, '.', Color.DARK_GRAY, Color.BLACK);
                buffer.put(screenX, 15, '.', Color.DARK_GRAY, Color.BLACK);
            }
        }

        for (Enemy enemy : enemies) {
            int screenX = enemy.worldX - camera;
            if (screenX < 0 || screenX >= COLS) continue;
            char ch = (enemy.type == EnemyType.STRAIGHT) ? '<' : 'W';
            Color color = (enemy.type == EnemyType.STRAIGHT) ? Color.RED : Color.MAGENTA;
            buffer.put(screenX, enemy.y, ch, color, Color.BLACK);
        }

        if (bulletActive) {
            int bulletScreenX = bulletWorldX - camera;
            if (bulletScreenX >= 0 && bulletScreenX < COLS) {
                buffer.put(bulletScreenX, bulletY, '-', Color.YELLOW, Color.BLACK);
            }
        }

        int playerScreenX = playerWorldX - camera;
        buffer.put(playerScreenX, playerY, '>', Color.CYAN, Color.BLACK);
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }
}
