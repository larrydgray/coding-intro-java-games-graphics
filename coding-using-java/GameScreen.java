// Course example © 2026 Larry D. Gray.
// For enrolled-student educational use; see README.md.

import java.awt.*;

/**
 * An AWT canvas that renders a character-cell {@link ScreenBuffer} as a
 * grid of colored text, like a retro text-mode game screen. Add it to a
 * {@link Frame}, write into {@link #getBuffer()}, then call
 * {@link #repaint()} to draw the latest contents.
 */
public class GameScreen extends Canvas {

    private Font font;
    private FontType fontType = FontType.CONSOLAS;
    private FontMetrics metrics;
    private int cellWidth;
    private int cellHeight;
    private int fontSize = 20;
    private Cell[][] screen;
    private int cols;
    private int rows;

    private int xAdjust = 0;
    private int yAdjust = 3;

    private ScreenBuffer buffer;
    private MiniGraphics miniGraphics = new MiniGraphics();

    public MiniGraphics getMiniGraphics(){
        return miniGraphics;
    }

    public GameScreen(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        buffer = new ScreenBuffer(cols, rows);
        setScreenFont(FontType.MONOSPACED);
        switch(this.fontType) {
            case CONSOLAS:
                yAdjust = 3;
                break;

            case COURIER:
                yAdjust = 3;
                break;

            case MONOSPACED:
                yAdjust = 4;
                break;
        }
        setPreferredSize(new Dimension(cols * cellWidth, rows * cellHeight));
    }

    public ScreenBuffer getBuffer() {
        return buffer;
    }

    /** Canvas width in pixels — the coordinate space MiniGraphics draws in. */
    public int getPixelWidth() {
        return cols * cellWidth;
    }

    /** Canvas height in pixels — the coordinate space MiniGraphics draws in. */
    public int getPixelHeight() {
        return rows * cellHeight;
    }

    /** Width in pixels of one character cell — for converting a grid column into a MiniGraphics x coordinate. */
    public int getCellWidth() {
        return cellWidth;
    }

    /** Height in pixels of one character cell — for converting a grid row into a MiniGraphics y coordinate. */
    public int getCellHeight() {
        return cellHeight;
    }

    public GameScreen() {
        setScreenFont(FontType.CONSOLAS);
    }

    public void setScreenFont(FontType type) {
        fontType = type;

        switch(type) {
            case MONOSPACED:
                font = new Font("Monospaced", Font.PLAIN, fontSize);
                break;

            case COURIER:
                font = new Font("Courier New", Font.PLAIN, fontSize);
                break;

            case CONSOLAS:
                font = new Font("Consolas", Font.PLAIN, fontSize);

                if(!font.getFamily().equalsIgnoreCase("Consolas")) {
                    font = new Font("Monospaced", Font.PLAIN, fontSize);
                }
                break;
        }


        setFont(font);

        metrics = getFontMetrics(font);
        cellWidth = metrics.charWidth('W');
        cellHeight = metrics.getHeight();
    }

    // Off-screen buffer: each frame is drawn here first, then blitted to
    // the screen in one shot via paint(). Without this, drawing straight
    // to the on-screen canvas can flicker or tear on busy/fast-updating
    // scenes, since the viewer can catch it mid-draw.
    private Image offscreen;

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        int width = getPixelWidth();
        int height = getPixelHeight();
        if (width <= 0 || height <= 0) {
            return;
        }

        if (offscreen == null || offscreen.getWidth(null) != width || offscreen.getHeight(null) != height) {
            offscreen = createImage(width, height);
        }

        Graphics offscreenGraphics = offscreen.getGraphics();
        render(offscreenGraphics);
        offscreenGraphics.dispose();

        g.drawImage(offscreen, 0, 0, null);
    }

    private void render(Graphics g) {
        g.setFont(font);

        Cell[][] cells = buffer.getCells();

        for(int y = 0; y < cells.length; y++) {
            for(int x = 0; x < cells[y].length; x++) {
                Cell cell = cells[y][x];

                int px = x * cellWidth;
                int py = y * cellHeight;

                // background
                g.setColor(cell.bg);
                g.fillRect(px, py, cellWidth, cellHeight);

                // centered character
                g.setColor(cell.fg);

                int textX = px+xAdjust;
                int textY = py + metrics.getAscent()+yAdjust;

                g.drawString(String.valueOf(cell.ch), textX, textY);
            }
        }

        miniGraphics.render(g);
    }
}
