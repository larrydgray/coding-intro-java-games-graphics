// Author Larry Gray CPL Common Public License  Software Developer Zone
package net.sf.sdz.sprites;

import javax.swing.*;
import java.awt.image.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Viewing/editing panel for the image grid in the currently selected
 * buffer: draws the buffer's image, an optional gridline overlay, and the
 * current selection box; wires up keyboard and mouse input.
 */
public class ImagePanel extends JPanel {
    // fields
    ImageBuffers buffers = null;
    public SpriteOrganizer.StatsPanel statsPanel = null;

    // setup methods
    public void setStatsPanel(SpriteOrganizer.StatsPanel statsPanel) {
        this.statsPanel = statsPanel;
    }

    public boolean isFocusTraversable() {
        return true;
    }

    // painting methods
    public void paintComponent(Graphics g) {
        g.fillRect(0, 0, 500, 500);
        if (buffers.buffer() == null) ;
        else g.drawImage(buffers.buffer(), 0, 0, null);
        //graphics=g;
        g.setColor(Color.black);
        if (buffers.grid()) drawGrid(g);
        if (buffers.select()) {
            drawSelectedSquare(g, buffers.getSelectedCol(), buffers.getSelectedRow());
        }
    }

    public void drawGrid(Graphics g) {
        for (int c = 0; c < buffers.cols(); c++) {
            for (int r = 0; r < buffers.rows(); r++) {
                drawSquare(g, c, r);
            }
        }

    }

    public void drawSquare(Graphics g, int col, int row) {
        g.drawRect(col * buffers.spriteWidth(), row * buffers.spriteHeight(),
                buffers.spriteWidth(), buffers.spriteHeight());
    }

    public void drawSelectedSquare(Graphics g, int col, int row) {
        g.setColor(Color.black);
        g.drawRect(col * buffers.spriteWidth() + 2, row * buffers.spriteHeight() + 2,
                buffers.spriteWidth() - 4, buffers.spriteHeight() - 4);
        g.setColor(Color.white);
        g.drawRect(col * buffers.spriteWidth() + 2 + 1, row * buffers.spriteHeight() + 2 + 1,
                buffers.spriteWidth() - 4, buffers.spriteHeight() - 4);
    }

    // constructor
    public ImagePanel(ImageBuffers buffers) {
        this.buffers = buffers;
        ImagePanel thisPanel = this;
        this.addKeyListener(new ImagePanelKeyListener(buffers, this));
        this.addMouseListener(new ImagePanelMouseListener(buffers, this));
    }

    //input methods
    public int inputX() {
        String sizeX = JOptionPane.showInputDialog("Size X in Pixels?");
        return Integer.parseInt(sizeX);
    }

    public int inputY() {
        String sizeY = JOptionPane.showInputDialog("Size Y in Pixels?");
        return Integer.parseInt(sizeY);
    }

    public int inputCols() {
        String sizeCols = JOptionPane.showInputDialog("Cols?");
        return Integer.parseInt(sizeCols);
    }

    public int inputRows() {
        String sizeRows = JOptionPane.showInputDialog("Rows?");
        return Integer.parseInt(sizeRows);
    }

    public int inputBuffer() {
        String bufferNum = JOptionPane.showInputDialog("Buffer#?");
        return Integer.parseInt(bufferNum);
    }

    public int inputCol() {
        String sizeCols = JOptionPane.showInputDialog("Col?");
        return Integer.parseInt(sizeCols);
    }

    public int inputRow() {
        String sizeRows = JOptionPane.showInputDialog("Row?");
        return Integer.parseInt(sizeRows);
    }
}
