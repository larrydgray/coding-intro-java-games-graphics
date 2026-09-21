package net.sf.javagg.gamescreen;

import java.awt.*;

/**
 * A character-cell screen buffer that models a simple text terminal.
 * <p>
 * The buffer holds a grid of {@link Cell} objects (character, foreground
 * color, and background color) along with cursor position/visibility and
 * the current print colors. Text can be written either directly to
 * arbitrary coordinates via {@link #put(int, int, char, Color, Color)} /
 * {@link #write(int, int, String, Color, Color)}, or streamed at the
 * cursor position via the {@code print}/{@code println} family of
 * methods, which wrap and scroll the buffer like a terminal.
 */
public class ScreenBuffer {
    private Cell[][] cells;
    private int cols;
    private int rows;

    // Terminal cursor position
    private int cursorX = 0;
    private int cursorY = 0;

    // Current print colors
    private Color printFg = Color.WHITE;
    private Color printBg = Color.BLACK;

    // Cursor visibility
    private boolean cursorVisible = true;
    private char cursorChar = '_';

    /**
     * Returns the underlying cell grid, indexed as {@code cells[row][col]}.
     *
     * @return the raw grid of cells backing this buffer
     */
    public Cell[][] getCells() {
        return cells;
    }

    /**
     * Creates a screen buffer of the given size, filled with blank cells.
     *
     * @param cols number of columns
     * @param rows number of rows
     */
    public ScreenBuffer(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;

        cells = new Cell[rows][cols];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                cells[y][x] = new Cell();
            }
        }
    }

    // ── Clear ────────────────────────────────────

    /**
     * Clears every cell to a blank space on the given background,
     * resets the foreground of every cell to white, and moves the
     * cursor back to the origin (0, 0).
     *
     * @param bg the background color to fill the buffer with
     */
    public void clear(Color bg) {
        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[y].length; x++) {
                cells[y][x].ch = ' ';
                cells[y][x].fg = Color.WHITE;
                cells[y][x].bg = bg;
            }
        }
        cursorX = 0;
        cursorY = 0;
    }

    /**
     * Clears the buffer to a black background.
     *
     * @see #clear(Color)
     */
    public void clear() {
        clear(Color.BLACK);
    }

    /**
     * Blanks every cell's character and sets its background color,
     * leaving each cell's foreground color and the cursor position
     * unchanged.
     *
     * @param bg the background color to fill the buffer with
     */
    public void clearBg(Color bg) {
        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[y].length; x++) {
                cells[y][x].ch = ' ';
                cells[y][x].bg = bg;
            }
        }
    }

    // ── Direct cell access ───────────────────────

    /**
     * Sets the character and colors of a single cell. Coordinates
     * outside the buffer bounds are silently ignored.
     *
     * @param x  column of the cell
     * @param y  row of the cell
     * @param ch character to place
     * @param fg foreground color
     * @param bg background color
     */
    public void put(int x, int y, char ch, Color fg, Color bg) {
        if (x < 0 || x >= cols || y < 0 || y >= rows) return;
        cells[y][x].ch = ch;
        cells[y][x].fg = fg;
        cells[y][x].bg = bg;
    }

    /**
     * Writes a string starting at the given coordinates, one character
     * per cell along the row. Writing stops if it would run past the
     * right edge of the buffer; it does not wrap or scroll.
     *
     * @param x    starting column
     * @param y    row to write into
     * @param text text to write
     * @param fg   foreground color for the text
     * @param bg   background color for the text
     */
    public void write(int x, int y, String text, Color fg, Color bg) {
        for (int i = 0; i < text.length(); i++) {
            if (x + i >= cols) break;
            put(x + i, y, text.charAt(i), fg, bg);
        }
    }

    /**
     * Writes a string using white-on-black coloring.
     *
     * @param x    starting column
     * @param y    row to write into
     * @param text text to write
     * @see #write(int, int, String, Color, Color)
     */
    public void write(int x, int y, String text) {
        write(x, y, text, Color.WHITE, Color.BLACK);
    }

    // ── Color control ────────────────────────────

    /**
     * Sets the foreground and background colors used by subsequent
     * {@code print}/{@code println} calls.
     *
     * @param fg new print foreground color
     * @param bg new print background color
     */
    public void setColors(Color fg, Color bg) {
        this.printFg = fg;
        this.printBg = bg;
    }

    /**
     * Sets the foreground color used by subsequent {@code print}/{@code println} calls.
     *
     * @param fg new print foreground color
     */
    public void setFg(Color fg) {
        this.printFg = fg;
    }

    /**
     * Sets the background color used by subsequent {@code print}/{@code println} calls.
     *
     * @param bg new print background color
     */
    public void setBg(Color bg) {
        this.printBg = bg;
    }

    /** @return the current print foreground color */
    public Color getFg() { return printFg; }

    /** @return the current print background color */
    public Color getBg() { return printBg; }

    // ── Cursor control ───────────────────────────

    /**
     * Moves the cursor to the given position, clamping to the buffer's bounds.
     *
     * @param x target column
     * @param y target row
     */
    public void setCursor(int x, int y) {
        cursorX = Math.max(0, Math.min(x, cols - 1));
        cursorY = Math.max(0, Math.min(y, rows - 1));
    }

    /** @return the cursor's current column */
    public int getCursorX() { return cursorX; }

    /** @return the cursor's current row */
    public int getCursorY() { return cursorY; }

    /**
     * Sets whether the cursor should be rendered.
     *
     * @param visible {@code true} to show the cursor, {@code false} to hide it
     */
    public void setCursorVisible(boolean visible) {
        this.cursorVisible = visible;
    }

    /**
     * Sets the character used to render the cursor.
     *
     * @param ch new cursor character
     */
    public void setCursorChar(char ch) {
        this.cursorChar = ch;
    }

    /** @return {@code true} if the cursor is currently visible */
    public boolean isCursorVisible() { return cursorVisible; }

    /** @return the character used to render the cursor */
    public char getCursorChar() { return cursorChar; }

    // ── Terminal print ───────────────────────────

    /**
     * Prints text at the cursor position using the given colors, then
     * restores the previous print colors.
     *
     * @param text text to print
     * @param fg   foreground color to use for this text
     * @param bg   background color to use for this text
     * @see #print(String)
     */
    public void print(String text, Color fg, Color bg) {
        Color savedFg = printFg;
        Color savedBg = printBg;
        printFg = fg;
        printBg = bg;
        print(text);
        printFg = savedFg;
        printBg = savedBg;
    }

    /**
     * Prints text at the cursor position using the current print colors,
     * advancing the cursor as it goes. {@code '\n'} starts a new line
     * (scrolling the buffer if needed), {@code '\r'} returns the cursor
     * to column 0, {@code '\t'} advances to the next 4-column tab stop,
     * and any other character is written to the current cell. Text wraps
     * to the next line automatically when it reaches the right edge.
     *
     * @param text text to print
     */
    public void print(String text) {
        for (char c : text.toCharArray()) {
            if (c == '\n') {
                newline();
            } else if (c == '\r') {
                cursorX = 0;
            } else if (c == '\t') {
                // Tab to next 4-char boundary
                int nextTab = (cursorX / 4 + 1) * 4;
                while (cursorX < nextTab && cursorX < cols) {
                    put(cursorX, cursorY, ' ', printFg, printBg);
                    cursorX++;
                }
                if (cursorX >= cols) newline();
            } else {
                put(cursorX, cursorY, c, printFg, printBg);
                cursorX++;
                if (cursorX >= cols) newline();
            }
        }
    }

    /**
     * Prints text at the cursor position using the current print colors,
     * then starts a new line.
     *
     * @param text text to print
     * @see #print(String)
     */
    public void println(String text) {
        print(text);
        newline();
    }

    /** Starts a new line without printing anything. */
    public void println() {
        newline();
    }

    /**
     * Prints text at the cursor position using the given foreground
     * color and the current print background color.
     *
     * @param text text to print
     * @param fg   foreground color to use for this text
     */
    public void print(String text, Color fg) {
        print(text, fg, printBg);
    }

    /**
     * Prints text using the given foreground color and the current
     * print background color, then starts a new line.
     *
     * @param text text to print
     * @param fg   foreground color to use for this text
     */
    public void println(String text, Color fg) {
        print(text, fg);
        newline();
    }

    /**
     * Prints text using the given colors, then starts a new line.
     *
     * @param text text to print
     * @param fg   foreground color to use for this text
     * @param bg   background color to use for this text
     */
    public void println(String text, Color fg, Color bg) {
        print(text, fg, bg);
        newline();
    }

    // ── Scrolling ────────────────────────────────

    /**
     * Moves the cursor to the start of the next line, scrolling the
     * buffer up by one row if the cursor would move past the last row.
     */
    private void newline() {
        cursorX = 0;
        cursorY++;
        if (cursorY >= rows) scroll();
    }

    /**
     * Shifts every row up by one, discarding the top row, and clears
     * the newly exposed bottom row using the current print colors.
     * Leaves the cursor on the bottom row.
     */
    private void scroll() {
        // Shift all rows up by one
        for (int y = 0; y < rows - 1; y++) {
            for (int x = 0; x < cols; x++) {
                cells[y][x].ch  = cells[y + 1][x].ch;
                cells[y][x].fg  = cells[y + 1][x].fg;
                cells[y][x].bg  = cells[y + 1][x].bg;
            }
        }
        // Clear the bottom row
        for (int x = 0; x < cols; x++) {
            cells[rows - 1][x].ch = ' ';
            cells[rows - 1][x].fg = printFg;
            cells[rows - 1][x].bg = printBg;
        }
        cursorY = rows - 1;
    }

    // ── Utility ──────────────────────────────────

    /** @return the number of columns in this buffer */
    public int getCols() { return cols; }

    /** @return the number of rows in this buffer */
    public int getRows() { return rows; }

    /**
     * Draws a horizontal line of one repeated character.
     *
     * @param x      starting column
     * @param y      row to draw on
     * @param length number of characters to draw
     * @param ch     character to repeat
     * @param fg     foreground color
     * @param bg     background color
     */
    public void hline(int x, int y, int length, char ch, Color fg, Color bg) {
        for (int i = 0; i < length; i++) {
            put(x + i, y, ch, fg, bg);
        }
    }

    /**
     * Draws a vertical line of one repeated character.
     *
     * @param x      column to draw on
     * @param y      starting row
     * @param length number of characters to draw
     * @param ch     character to repeat
     * @param fg     foreground color
     * @param bg     background color
     */
    public void vline(int x, int y, int length, char ch, Color fg, Color bg) {
        for (int i = 0; i < length; i++) {
            put(x, y + i, ch, fg, bg);
        }
    }

    /**
     * Draws a rectangular box border using {@code '+'} corners and
     * {@code '-'}/{@code '|'} edges, similar to an ASCII text box.
     *
     * @param x  column of the top-left corner
     * @param y  row of the top-left corner
     * @param w  width of the box, including both border edges
     * @param h  height of the box, including both border edges
     * @param fg foreground color
     * @param bg background color
     */
    public void box(int x, int y, int w, int h, Color fg, Color bg) {
        put(x,         y,         '+', fg, bg);
        put(x + w - 1, y,         '+', fg, bg);
        put(x,         y + h - 1, '+', fg, bg);
        put(x + w - 1, y + h - 1, '+', fg, bg);
        hline(x + 1, y,         w - 2, '-', fg, bg);
        hline(x + 1, y + h - 1, w - 2, '-', fg, bg);
        vline(x,         y + 1, h - 2, '|', fg, bg);
        vline(x + w - 1, y + 1, h - 2, '|', fg, bg);
    }
}
