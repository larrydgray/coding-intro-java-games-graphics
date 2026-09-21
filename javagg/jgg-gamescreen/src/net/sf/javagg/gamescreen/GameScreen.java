package net.sf.javagg.gamescreen;

import java.awt.*;

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

    @Override
    public void update(Graphics g) {
        paint(g);
    }
    @Override
    public void paint(Graphics g) {
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
