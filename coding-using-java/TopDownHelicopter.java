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
 * A very simple top-down helicopter shooter.
 * <p>
 * The world extends in all four directions, further than the screen can
 * show at once. Fly the H with the arrows or WASD (all 8 directions
 * work) and the camera follows you, keeping you roughly centered both
 * horizontally and vertically. Press SPACE to fire in whichever
 * direction you last moved.
 * <p>
 * There are two kinds of targets: orange B buildings sit still on the
 * ground, while magenta X enemy choppers patrol back and forth along a
 * fixed line, some horizontal and some vertical.
 */
public class TopDownHelicopter {

    private static final int COLS = 50;
    private static final int ROWS = 20;
    private static final int WORLD_WIDTH = 90;
    private static final int WORLD_HEIGHT = 50;

    private static final int BULLET_SPEED = 2;
    private static final int ENEMY_MOVE_EVERY_TICKS = 3;

    private final GameScreen screen = new GameScreen(COLS, ROWS);
    private final ScreenBuffer buffer = screen.getBuffer();
    private final InputHandler input = new InputHandler();
    private final Random random = new Random();

    private int playerWorldX = WORLD_WIDTH / 2;
    private int playerWorldY = WORLD_HEIGHT / 2;

    // Which way the helicopter last moved; bullets fire this direction.
    private int facingDx = 0;
    private int facingDy = -1;

    private boolean bulletActive = false;
    private int bulletWorldX;
    private int bulletWorldY;
    private int bulletDx;
    private int bulletDy;

    private static class Building {
        static final int WIDTH = 3;
        static final int HEIGHT = 2;

        int x, y;

        boolean hitBy(int px, int py) {
            return px >= x - 1 && px <= x + WIDTH && py >= y - 1 && py <= y + HEIGHT;
        }

        void respawn(Random random) {
            x = random.nextInt(WORLD_WIDTH - WIDTH);
            y = random.nextInt(WORLD_HEIGHT - HEIGHT);
        }
    }

    private enum Axis { HORIZONTAL, VERTICAL }

    private static class AirEnemy {
        Axis axis;
        int worldX, worldY;
        int dir;
        int minCoord, maxCoord;
        int fixedCoord;

        void step() {
            if (axis == Axis.HORIZONTAL) {
                worldX += dir;
                if (worldX <= minCoord) { worldX = minCoord; dir = 1; }
                else if (worldX >= maxCoord) { worldX = maxCoord; dir = -1; }
                worldY = fixedCoord;
            } else {
                worldY += dir;
                if (worldY <= minCoord) { worldY = minCoord; dir = 1; }
                else if (worldY >= maxCoord) { worldY = maxCoord; dir = -1; }
                worldX = fixedCoord;
            }
        }

        void respawn(Random random) {
            axis = random.nextBoolean() ? Axis.HORIZONTAL : Axis.VERTICAL;
            dir = 1;

            if (axis == Axis.HORIZONTAL) {
                minCoord = random.nextInt(WORLD_WIDTH - 15);
                maxCoord = minCoord + 8 + random.nextInt(8);
                fixedCoord = random.nextInt(WORLD_HEIGHT);
                worldX = minCoord;
                worldY = fixedCoord;
            } else {
                minCoord = random.nextInt(WORLD_HEIGHT - 15);
                maxCoord = minCoord + 8 + random.nextInt(8);
                fixedCoord = random.nextInt(WORLD_WIDTH);
                worldY = minCoord;
                worldX = fixedCoord;
            }
        }
    }

    private final Building[] buildings = new Building[5];
    private final AirEnemy[] airEnemies = new AirEnemy[4];

    private int enemyMoveCounter = 0;
    private int score = 0;

    public static void main(String[] args) {
        new TopDownHelicopter().start();
    }

    private void start() {
        for (int i = 0; i < buildings.length; i++) {
            buildings[i] = new Building();
            buildings[i].respawn(random);
        }
        for (int i = 0; i < airEnemies.length; i++) {
            airEnemies[i] = new AirEnemy();
            airEnemies[i].respawn(random);
        }

        Frame frame = new Frame("Top-Down Helicopter");
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

    private void tick() {
        movePlayer();
        fireBullet();
        moveBullet();
        moveEnemies();
        checkHits();
        draw();
        screen.repaint();
    }

    private int cameraX() {
        return clamp(playerWorldX - COLS / 2, 0, WORLD_WIDTH - COLS);
    }

    private int cameraY() {
        return clamp(playerWorldY - ROWS / 2, 0, WORLD_HEIGHT - ROWS);
    }

    private void movePlayer() {
        int dx = 0;
        int dy = 0;

        if (input.isDown(KeyEvent.VK_LEFT) || input.isDown(KeyEvent.VK_A)) dx = -1;
        if (input.isDown(KeyEvent.VK_RIGHT) || input.isDown(KeyEvent.VK_D)) dx = 1;
        if (input.isDown(KeyEvent.VK_UP) || input.isDown(KeyEvent.VK_W)) dy = -1;
        if (input.isDown(KeyEvent.VK_DOWN) || input.isDown(KeyEvent.VK_S)) dy = 1;

        if (dx != 0 || dy != 0) {
            facingDx = dx;
            facingDy = dy;
        }

        playerWorldX = clamp(playerWorldX + dx, 0, WORLD_WIDTH - 1);
        playerWorldY = clamp(playerWorldY + dy, 0, WORLD_HEIGHT - 1);
    }

    private void fireBullet() {
        if (!bulletActive && input.isDown(KeyEvent.VK_SPACE)) {
            bulletActive = true;
            bulletDx = facingDx;
            bulletDy = facingDy;
            bulletWorldX = playerWorldX + facingDx;
            bulletWorldY = playerWorldY + facingDy;
        }
    }

    private void moveBullet() {
        if (!bulletActive) {
            return;
        }
        bulletWorldX += bulletDx * BULLET_SPEED;
        bulletWorldY += bulletDy * BULLET_SPEED;

        int screenX = bulletWorldX - cameraX();
        int screenY = bulletWorldY - cameraY();
        if (screenX < 0 || screenX >= COLS || screenY < 0 || screenY >= ROWS) {
            bulletActive = false;
        }
    }

    private void moveEnemies() {
        enemyMoveCounter++;
        if (enemyMoveCounter < ENEMY_MOVE_EVERY_TICKS) {
            return;
        }
        enemyMoveCounter = 0;

        for (AirEnemy enemy : airEnemies) {
            enemy.step();
        }
    }

    private void checkHits() {
        if (!bulletActive) {
            return;
        }

        for (Building building : buildings) {
            if (building.hitBy(bulletWorldX, bulletWorldY)) {
                score++;
                building.respawn(random);
                bulletActive = false;
                return;
            }
        }

        for (AirEnemy enemy : airEnemies) {
            if (Math.abs(enemy.worldX - bulletWorldX) <= 1 && Math.abs(enemy.worldY - bulletWorldY) <= 1) {
                score++;
                enemy.respawn(random);
                bulletActive = false;
                return;
            }
        }
    }

    private void draw() {
        int camX = cameraX();
        int camY = cameraY();

        buffer.clear(Color.BLACK);

        // Sparse ground texture so you can see the world scrolling.
        for (int screenX = 0; screenX < COLS; screenX++) {
            int worldX = camX + screenX;
            for (int screenY = 0; screenY < ROWS; screenY++) {
                int worldY = camY + screenY;
                if (worldX % 6 == 0 && worldY % 6 == 0) {
                    buffer.put(screenX, screenY, '.', Color.GREEN, Color.BLACK);
                }
            }
        }

        for (Building building : buildings) {
            for (int by = 0; by < Building.HEIGHT; by++) {
                for (int bx = 0; bx < Building.WIDTH; bx++) {
                    int screenX = building.x + bx - camX;
                    int screenY = building.y + by - camY;
                    if (screenX >= 0 && screenX < COLS && screenY >= 0 && screenY < ROWS) {
                        buffer.put(screenX, screenY, 'B', Color.ORANGE, Color.BLACK);
                    }
                }
            }
        }

        for (AirEnemy enemy : airEnemies) {
            int screenX = enemy.worldX - camX;
            int screenY = enemy.worldY - camY;
            if (screenX >= 0 && screenX < COLS && screenY >= 0 && screenY < ROWS) {
                buffer.put(screenX, screenY, 'X', Color.MAGENTA, Color.BLACK);
            }
        }

        if (bulletActive) {
            int screenX = bulletWorldX - camX;
            int screenY = bulletWorldY - camY;
            if (screenX >= 0 && screenX < COLS && screenY >= 0 && screenY < ROWS) {
                buffer.put(screenX, screenY, '*', Color.YELLOW, Color.BLACK);
            }
        }

        int playerScreenX = playerWorldX - camX;
        int playerScreenY = playerWorldY - camY;
        buffer.put(playerScreenX, playerScreenY, 'H', Color.WHITE, Color.BLACK);

        buffer.write(1, 0, "Score: " + score + "   Arrows/WASD to fly, Space to shoot", Color.LIGHT_GRAY, Color.BLACK);
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }
}
