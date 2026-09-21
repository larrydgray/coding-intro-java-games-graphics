// Course example © 2026 Larry D. Gray.
// For enrolled-student educational use; see README.md.

import javax.swing.Timer;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.Random;

/**
 * The classic Snake (Nibbles): steer with the arrows or WASD, eat the
 * $ to grow and score, don't run into a wall or your own tail.
 * <p>
 * Unlike WalkAndCollect, the snake moves on its own every tick in
 * whatever direction you last chose — you don't have to hold a key to
 * keep moving, and a direct 180-degree reversal is ignored so you can't
 * accidentally steer straight into your own neck.
 */
public class Snake {

    private static final int COLS = 40;
    private static final int ROWS = 20;

    private final GameScreen screen = new GameScreen(COLS, ROWS);
    private final ScreenBuffer buffer = screen.getBuffer();
    private final InputHandler input = new InputHandler();
    private final Random random = new Random();

    private final LinkedList<int[]> snake = new LinkedList<>();
    private int dx = 1;
    private int dy = 0;
    private int[] food;
    private int score = 0;
    private boolean gameOver = false;

    public static void main(String[] args) {
        new Snake().start();
    }

    private void start() {
        int startX = COLS / 2;
        int startY = ROWS / 2;
        snake.add(new int[]{startX, startY});
        snake.add(new int[]{startX - 1, startY});
        snake.add(new int[]{startX - 2, startY});
        placeFood();

        Frame frame = new Frame("Snake");
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

        Timer timer = new Timer(130, e -> tick());
        timer.start();
    }

    private void tick() {
        if (!gameOver) {
            handleInput();
            moveSnake();
        }
        draw();
        screen.repaint();
    }

    private void handleInput() {
        int newDx = dx;
        int newDy = dy;

        if (input.isDown(KeyEvent.VK_LEFT) || input.isDown(KeyEvent.VK_A)) {
            newDx = -1;
            newDy = 0;
        } else if (input.isDown(KeyEvent.VK_RIGHT) || input.isDown(KeyEvent.VK_D)) {
            newDx = 1;
            newDy = 0;
        } else if (input.isDown(KeyEvent.VK_UP) || input.isDown(KeyEvent.VK_W)) {
            newDx = 0;
            newDy = -1;
        } else if (input.isDown(KeyEvent.VK_DOWN) || input.isDown(KeyEvent.VK_S)) {
            newDx = 0;
            newDy = 1;
        }

        // Ignore a direct reversal so you can't turn straight into your own neck.
        if (!(newDx == -dx && newDy == -dy)) {
            dx = newDx;
            dy = newDy;
        }
    }

    private void moveSnake() {
        int[] head = snake.getFirst();
        int newX = head[0] + dx;
        int newY = head[1] + dy;

        if (newX < 0 || newX >= COLS || newY < 1 || newY >= ROWS) {
            gameOver = true;
            return;
        }

        boolean willGrow = (newX == food[0] && newY == food[1]);
        int[] tail = snake.getLast();

        for (int[] cell : snake) {
            if (!willGrow && cell == tail) continue; // the tail is about to move away
            if (cell[0] == newX && cell[1] == newY) {
                gameOver = true;
                return;
            }
        }

        snake.addFirst(new int[]{newX, newY});
        if (willGrow) {
            score++;
            placeFood();
        } else {
            snake.removeLast();
        }
    }

    private void placeFood() {
        int x, y;
        boolean onSnake;
        do {
            x = random.nextInt(COLS);
            y = 1 + random.nextInt(ROWS - 1);
            onSnake = false;
            for (int[] cell : snake) {
                if (cell[0] == x && cell[1] == y) {
                    onSnake = true;
                    break;
                }
            }
        } while (onSnake);
        food = new int[]{x, y};
    }

    private void draw() {
        buffer.clear(Color.BLACK);
        buffer.write(1, 0, "Score: " + score + "   Arrows/WASD to steer", Color.LIGHT_GRAY, Color.BLACK);

        MiniGraphics g = screen.getMiniGraphics();
        g.clearGraphics();

        drawFood(g);
        drawSnakeBody(g);
        drawHead(g);

        if (gameOver) {
            buffer.write(1, ROWS - 1, "Game over! Final score: " + score, Color.RED, Color.BLACK);
        }
    }

    private double cellCenterX(int col) {
        return (col + 0.5) * screen.getCellWidth();
    }

    private double cellCenterY(int row) {
        return (row + 0.5) * screen.getCellHeight();
    }

    /** Traces the turtle from the tail through every body cell to the head — the trail it leaves behind IS the snake. */
    private void drawSnakeBody(MiniGraphics g) {
        int cellWidth = screen.getCellWidth();
        int cellHeight = screen.getCellHeight();
        int bodyWidth = (int) (Math.min(cellWidth, cellHeight) * 0.75);

        int[] tail = snake.getLast();
        g.turtleColor(Color.GREEN);
        g.penWidth(bodyWidth);
        g.jumpTo(cellCenterX(tail[0]), cellCenterY(tail[1]));

        // Walk from the tail to the head, one grid step (and one forward()) at a time.
        for (int i = snake.size() - 2; i >= 0; i--) {
            int[] from = snake.get(i + 1);
            int[] to = snake.get(i);
            int stepDx = to[0] - from[0];
            int stepDy = to[1] - from[1];

            double angle = Math.toDegrees(Math.atan2(stepDy, stepDx));
            double distance = (stepDx != 0) ? cellWidth : cellHeight;

            g.setAngle(angle);
            g.penDown();
            g.forward(distance);
            g.penUp();
        }
    }

    /** A small arrowhead pointing the current direction of travel — turns a hard 90 degrees at each corner, like the Asteroids ship. */
    private void drawHead(MiniGraphics g) {
        int[] head = snake.getFirst();
        double cx = cellCenterX(head[0]);
        double cy = cellCenterY(head[1]);
        double size = Math.min(screen.getCellWidth(), screen.getCellHeight()) * 0.9;

        double rad = Math.atan2(dy, dx);
        double dirX = Math.cos(rad);
        double dirY = Math.sin(rad);
        double perpX = -dirY;
        double perpY = dirX;

        double noseX = cx + dirX * size;
        double noseY = cy + dirY * size;
        double rearCX = cx - dirX * size * 0.5;
        double rearCY = cy - dirY * size * 0.5;
        double leftX = rearCX + perpX * size * 0.45;
        double leftY = rearCY + perpY * size * 0.45;
        double rightX = rearCX - perpX * size * 0.45;
        double rightY = rearCY - perpY * size * 0.45;

        line(g, noseX, noseY, leftX, leftY, Color.YELLOW);
        line(g, leftX, leftY, rightX, rightY, Color.YELLOW);
        line(g, rightX, rightY, noseX, noseY, Color.YELLOW);
    }

    private static void line(MiniGraphics g, double x1, double y1, double x2, double y2, Color color) {
        g.line((int) Math.round(x1), (int) Math.round(y1), (int) Math.round(x2), (int) Math.round(y2), color);
    }

    private void drawFood(MiniGraphics g) {
        int radius = (int) (Math.min(screen.getCellWidth(), screen.getCellHeight()) * 0.4);
        g.fillCircle((int) cellCenterX(food[0]), (int) cellCenterY(food[1]), radius, Color.RED);
    }
}
