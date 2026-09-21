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

/**
 * A simple Othello (Reversi) against a computer opponent.
 * <p>
 * You play black, moving first. Move the selection with the arrows or
 * WASD and press SPACE to place a disk on a legal square (any square
 * where you'd bracket at least one line of white disks between your new
 * disk and another black one, in any of the 8 directions). Bracketed
 * disks flip to your color.
 * <p>
 * The computer's "AI" isn't a real search — it's a one-move-ahead
 * heuristic: for each of its legal moves it scores flip count plus a
 * fixed positional value per square (corners are great, the squares
 * next to a corner are traps, edges are good), and plays the
 * highest-scoring move. That's usually enough to be a real opponent
 * without needing minimax or lookahead.
 */
public class Othello {

    private static final int BOARD_SIZE = 8;
    private static final int EMPTY = 0;
    private static final int BLACK = 1; // human
    private static final int WHITE = 2; // computer

    private static final int CELL_W = 4;   // one board square, in characters
    private static final int CELL_H = 2;
    private static final int BOARD_CHAR_W = BOARD_SIZE * CELL_W;
    private static final int BOARD_CHAR_H = BOARD_SIZE * CELL_H;

    private static final int BOARD_ORIGIN_X = 6;
    private static final int BOARD_ORIGIN_Y = 6;

    private static final int COLS = BOARD_ORIGIN_X + BOARD_CHAR_W + 3;
    private static final int ROWS = BOARD_ORIGIN_Y + BOARD_CHAR_H + 4;

    // Grid lines are drawn as thin MiniGraphics pixel lines rather than
    // whole brown character cells, since a full character is too thick
    // to read as a "line".
    private static final Color BOARD_LINE_COLOR = new Color(101, 67, 33); // wood brown
    private static final int AI_DELAY_TICKS = 6;

    // Classic simple positional weight table: corners are great, the
    // squares diagonally next to a corner are traps (they hand the
    // opponent the corner), edges are solid, center is unremarkable.
    private static final int[][] WEIGHTS = {
            {100, -20, 10, 5, 5, 10, -20, 100},
            {-20, -50, -2, -2, -2, -2, -50, -20},
            {10, -2, -1, -1, -1, -1, -2, 10},
            {5, -2, -1, -1, -1, -1, -2, 5},
            {5, -2, -1, -1, -1, -1, -2, 5},
            {10, -2, -1, -1, -1, -1, -2, 10},
            {-20, -50, -2, -2, -2, -2, -50, -20},
            {100, -20, 10, 5, 5, 10, -20, 100}
    };

    private static final int[][] DIRECTIONS = {
            {-1, -1}, {0, -1}, {1, -1},
            {-1, 0}, {1, 0},
            {-1, 1}, {0, 1}, {1, 1}
    };

    private enum Phase { PLAYER_TURN, AI_TURN, GAME_OVER }

    private final GameScreen screen = new GameScreen(COLS, ROWS);
    private final ScreenBuffer buffer = screen.getBuffer();
    private final InputHandler input = new InputHandler();

    private final int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
    private int cursorX = 3;
    private int cursorY = 2;

    private Phase phase = Phase.PLAYER_TURN;
    private int aiDelay = 0;
    private String message = "";

    public static void main(String[] args) {
        new Othello().start();
    }

    private void start() {
        board[3][3] = WHITE;
        board[3][4] = BLACK;
        board[4][3] = BLACK;
        board[4][4] = WHITE;

        Frame frame = new Frame("Othello");
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

        Timer timer = new Timer(120, e -> tick());
        timer.start();
    }

    private void tick() {
        if (phase != Phase.GAME_OVER && !hasLegalMoves(BLACK) && !hasLegalMoves(WHITE)) {
            phase = Phase.GAME_OVER;
        }

        if (phase == Phase.PLAYER_TURN) {
            if (!hasLegalMoves(BLACK)) {
                message = "You have no legal moves. Passing to the computer.";
                phase = Phase.AI_TURN;
                aiDelay = AI_DELAY_TICKS;
            } else {
                moveCursor();
                if (input.wasPressed(KeyEvent.VK_SPACE)) {
                    if (isLegalMove(board, BLACK, cursorX, cursorY)) {
                        applyMove(board, BLACK, cursorX, cursorY);
                        message = "";
                        phase = Phase.AI_TURN;
                        aiDelay = AI_DELAY_TICKS;
                    } else {
                        message = "You can't place a disk there.";
                    }
                }
            }
        } else if (phase == Phase.AI_TURN) {
            if (aiDelay > 0) {
                aiDelay--;
            } else if (!hasLegalMoves(WHITE)) {
                message = "The computer has no legal moves. Passing to you.";
                phase = Phase.PLAYER_TURN;
            } else {
                int[] move = pickAiMove(board);
                applyMove(board, WHITE, move[0], move[1]);
                message = "";
                phase = Phase.PLAYER_TURN;
            }
        }

        draw();
        screen.repaint();
    }

    private void moveCursor() {
        int dx = 0;
        int dy = 0;
        if (input.isDown(KeyEvent.VK_LEFT) || input.isDown(KeyEvent.VK_A)) dx = -1;
        if (input.isDown(KeyEvent.VK_RIGHT) || input.isDown(KeyEvent.VK_D)) dx = 1;
        if (input.isDown(KeyEvent.VK_UP) || input.isDown(KeyEvent.VK_W)) dy = -1;
        if (input.isDown(KeyEvent.VK_DOWN) || input.isDown(KeyEvent.VK_S)) dy = 1;

        cursorX = clamp(cursorX + dx, 0, BOARD_SIZE - 1);
        cursorY = clamp(cursorY + dy, 0, BOARD_SIZE - 1);
    }

    // ── Othello rules ──────────────────────────────

    private boolean isLegalMove(int[][] b, int player, int x, int y) {
        if (b[y][x] != EMPTY) return false;
        int opponent = (player == BLACK) ? WHITE : BLACK;

        for (int[] dir : DIRECTIONS) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            boolean sawOpponent = false;
            while (inBounds(nx, ny) && b[ny][nx] == opponent) {
                sawOpponent = true;
                nx += dir[0];
                ny += dir[1];
            }
            if (sawOpponent && inBounds(nx, ny) && b[ny][nx] == player) {
                return true;
            }
        }
        return false;
    }

    private List<int[]> computeFlips(int[][] b, int player, int x, int y) {
        List<int[]> flips = new ArrayList<>();
        int opponent = (player == BLACK) ? WHITE : BLACK;

        for (int[] dir : DIRECTIONS) {
            List<int[]> line = new ArrayList<>();
            int nx = x + dir[0];
            int ny = y + dir[1];
            while (inBounds(nx, ny) && b[ny][nx] == opponent) {
                line.add(new int[]{nx, ny});
                nx += dir[0];
                ny += dir[1];
            }
            if (!line.isEmpty() && inBounds(nx, ny) && b[ny][nx] == player) {
                flips.addAll(line);
            }
        }
        return flips;
    }

    private void applyMove(int[][] b, int player, int x, int y) {
        b[y][x] = player;
        for (int[] flip : computeFlips(b, player, x, y)) {
            b[flip[1]][flip[0]] = player;
        }
    }

    private boolean hasLegalMoves(int player) {
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (isLegalMove(board, player, x, y)) return true;
            }
        }
        return false;
    }

    private static boolean inBounds(int x, int y) {
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE;
    }

    private int[] countDisks() {
        int black = 0;
        int white = 0;
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (board[y][x] == BLACK) black++;
                else if (board[y][x] == WHITE) white++;
            }
        }
        return new int[]{black, white};
    }

    // ── Computer opponent ──────────────────────────

    private int[] pickAiMove(int[][] b) {
        int[] best = null;
        int bestScore = Integer.MIN_VALUE;

        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (!isLegalMove(b, WHITE, x, y)) continue;

                int flips = computeFlips(b, WHITE, x, y).size();
                int score = WEIGHTS[y][x] * 3 + flips;
                if (score > bestScore) {
                    bestScore = score;
                    best = new int[]{x, y};
                }
            }
        }
        return best;
    }

    // ── Drawing ────────────────────────────────────

    private void draw() {
        buffer.clear(Color.BLACK);

        buffer.write(1, 0, "OTHELLO", Color.YELLOW, Color.BLACK);

        int[] counts = countDisks();
        buffer.write(1, 1, "Black (you): " + counts[0] + "    White (computer): " + counts[1],
                Color.LIGHT_GRAY, Color.BLACK);

        String status;
        if (phase == Phase.GAME_OVER) {
            status = gameOverMessage(counts);
        } else if (phase == Phase.PLAYER_TURN) {
            status = "Your turn - arrows/WASD to move, SPACE to place.";
        } else {
            status = "Computer is thinking...";
        }
        buffer.write(1, 2, status, phase == Phase.GAME_OVER ? Color.YELLOW : Color.CYAN, Color.BLACK);

        if (!message.isEmpty()) {
            buffer.write(1, 3, message, Color.RED, Color.BLACK);
        }

        drawBoard();

        if (phase != Phase.GAME_OVER) {
            int colPointerX = BOARD_ORIGIN_X + cursorX * CELL_W + CELL_W / 2;
            buffer.put(colPointerX, BOARD_ORIGIN_Y - 1, 'v', Color.YELLOW, Color.BLACK);

            int rowPointerY = BOARD_ORIGIN_Y + cursorY * CELL_H;
            buffer.put(BOARD_ORIGIN_X - 2, rowPointerY, '>', Color.YELLOW, Color.BLACK);
        }
    }

    private void drawBoard() {
        // Every square is green felt in the character layer — the grid
        // lines and stones are both drawn as thin/round MiniGraphics
        // shapes on top, in real pixel coordinates.
        for (int y = 0; y < BOARD_CHAR_H; y++) {
            for (int x = 0; x < BOARD_CHAR_W; x++) {
                buffer.put(BOARD_ORIGIN_X + x, BOARD_ORIGIN_Y + y, ' ', Color.GREEN, Color.GREEN);
            }
        }

        for (int by = 0; by < BOARD_SIZE; by++) {
            for (int bx = 0; bx < BOARD_SIZE; bx++) {
                if (phase == Phase.PLAYER_TURN && board[by][bx] == EMPTY
                        && isLegalMove(board, BLACK, bx, by)) {
                    int cellX = BOARD_ORIGIN_X + bx * CELL_W;
                    int cellY = BOARD_ORIGIN_Y + by * CELL_H;
                    buffer.put(cellX + CELL_W / 2, cellY, '.', Color.CYAN, Color.GREEN);
                }
            }
        }

        drawGridLines();
        drawStones();
    }

    private void drawGridLines() {
        MiniGraphics g = screen.getMiniGraphics();
        g.clearGraphics();

        int cellPixelW = screen.getCellWidth() * CELL_W;
        int cellPixelH = screen.getCellHeight() * CELL_H;
        int originPxX = BOARD_ORIGIN_X * screen.getCellWidth();
        int originPxY = BOARD_ORIGIN_Y * screen.getCellHeight();
        int boardPixelW = cellPixelW * BOARD_SIZE;
        int boardPixelH = cellPixelH * BOARD_SIZE;

        for (int i = 0; i <= BOARD_SIZE; i++) {
            int x = originPxX + i * cellPixelW;
            g.line(x, originPxY, x, originPxY + boardPixelH, BOARD_LINE_COLOR);

            int y = originPxY + i * cellPixelH;
            g.line(originPxX, y, originPxX + boardPixelW, y, BOARD_LINE_COLOR);
        }
    }

    private void drawStones() {
        MiniGraphics g = screen.getMiniGraphics();
        int cellPixelW = screen.getCellWidth() * CELL_W;
        int cellPixelH = screen.getCellHeight() * CELL_H;
        int originPxX = BOARD_ORIGIN_X * screen.getCellWidth();
        int originPxY = BOARD_ORIGIN_Y * screen.getCellHeight();
        int radius = (int) (Math.min(cellPixelW, cellPixelH) * 0.38);

        for (int by = 0; by < BOARD_SIZE; by++) {
            for (int bx = 0; bx < BOARD_SIZE; bx++) {
                if (board[by][bx] == EMPTY) continue;

                Color color = (board[by][bx] == BLACK) ? Color.BLACK : Color.WHITE;
                int cx = originPxX + bx * cellPixelW + cellPixelW / 2;
                int cy = originPxY + by * cellPixelH + cellPixelH / 2;
                g.fillCircle(cx, cy, radius, color);
            }
        }
    }

    private String gameOverMessage(int[] counts) {
        if (counts[0] > counts[1]) {
            return "You win, " + counts[0] + " to " + counts[1] + "!";
        } else if (counts[1] > counts[0]) {
            return "The computer wins, " + counts[1] + " to " + counts[0] + ".";
        } else {
            return "It's a tie, " + counts[0] + " to " + counts[1] + ".";
        }
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }
}
