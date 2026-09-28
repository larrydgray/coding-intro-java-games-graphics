// Author Larry Gray CPL Common Public License  Software Developer Zone
package net.sf.sdz.sprites;

import java.awt.image.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.imageio.*;
import javax.imageio.stream.*;

/**
 * Holds a fixed set of image buffers (each with its own grid/selection
 * state), and the operations for loading/saving them and copying, moving,
 * clearing, or pasting sprite cells within and between buffers.
 */
public class ImageBuffers {
    // debug methods
    public void log(String s) {
        System.out.println(s);
    }

    // inner classes
    public class ImageBuffer {
        public BufferedImage buffer = null;
        public int cols = 0;
        public int rows = 0;
        public boolean grid = false;
        public int selectedCol = 0;
        public int selectedRow = 0;
        public boolean select = false;

        public void setSelectedColRow(int col, int row) {
            this.selectedCol = col;
            this.selectedRow = row;
        }
    }

    public class Tile {
        private String name = "";
        private Image tile = null;

        public Tile(String name, Image anImage) {
            this.name = name;
            this.tile = anImage;
        }

        public Image getTile() {
            return tile;
        }

        public String getName() {
            return name;
        }
    }

    // constructors
    public ImageBuffers(int numBuffers) {
        buffers = new ImageBuffer[numBuffers];
        for (int i = 0; i < numBuffers; i++) {
            buffers[i] = new ImageBuffer();
        }
    }

    // fields
    public ImageBuffer[] buffers = null;
    public int currentBuffer = 0;

    // methods
    public void setSelectedColRow(int col, int row) {
        buffers[currentBuffer].setSelectedColRow(col, row);
    }

    public int getSelectedCol() {
        return buffers[currentBuffer].selectedCol;
    }

    public int getSelectedRow() {
        return buffers[currentBuffer].selectedRow;
    }

    public boolean select() {
        return buffers[currentBuffer].select;
    }

    public void setSelect(boolean select) {
        buffers[currentBuffer].select = select;
    }

    public void gridOn() {
        buffers[currentBuffer].grid = true;
    }

    public void gridOff() {
        buffers[currentBuffer].grid = false;
    }

    public void flipGrid() {
        if (grid()) gridOff();
        else gridOn();
    }

    public boolean grid() {
        return buffers[currentBuffer].grid;
    }

    public void setBuffer(BufferedImage image) {
        buffers[currentBuffer].buffer = image;
    }

    public void setBuffer(int buffer) {
        currentBuffer = buffer;
    }

    public void incBuffer() {
        currentBuffer++;
    }

    public void decBuffer() {
        currentBuffer--;
    }

    public BufferedImage buffer() {
        return buffers[currentBuffer].buffer;
    }

    public BufferedImage buffer(int buffer) {
        return buffers[buffer].buffer;
    }

    public void setCols(int cols) {
        buffers[currentBuffer].cols = cols;
    }

    public void setRows(int rows) {
        buffers[currentBuffer].rows = rows;
    }

    public int cols() {
        return buffers[currentBuffer].cols;
    }

    public int cols(int buffer) {
        return buffers[buffer].cols;
    }

    public int rows() {
        return buffers[currentBuffer].rows;
    }

    public int rows(int buffer) {
        return buffers[buffer].rows;
    }

    public int width() {
        if (buffers[currentBuffer].buffer == null) return 0;
        else return buffers[currentBuffer].buffer.getWidth();
    }

    public int width(int buffer) {
        if (buffers[buffer].buffer == null) return 0;
        else return buffers[buffer].buffer.getWidth();
    }

    public int height() {
        if (buffers[currentBuffer].buffer == null) return 0;
        else return buffers[currentBuffer].buffer.getHeight();
    }

    public int height(int buffer) {
        if (buffers[buffer].buffer == null) return 0;
        else return buffers[buffer].buffer.getHeight();
    }

    public int spriteWidth() {
        return width() / buffers[currentBuffer].cols;
    }

    public int spriteWidth(int buffer) {
        return width(buffer) / buffers[buffer].cols;
    }

    public int spriteHeight() {
        return height() / buffers[currentBuffer].rows;
    }

    public int spriteHeight(int buffer) {
        return height(buffer) / buffers[buffer].rows;
    }

    // image loader saver
    public void loadImage(String anImageFileName, int bufferNumber, Component component) {
        currentBuffer = bufferNumber;
        loadImage(anImageFileName, component);
    }

    public void loadImage(String anImageFileName, Component component) {
        File anImageFile = new File(anImageFileName);
        Image image = null;
        try {
            MediaTracker mt = new MediaTracker(component);
            image = Toolkit.getDefaultToolkit().getImage(
                    anImageFileName);
            mt.addImage(image, 0);
            mt.waitForAll();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } // catch
        buffers[currentBuffer].buffer = getBufferedImage(image);
    } // loadImg

    /**
     * Saves an image to a png file.
     */
    protected void saveImage(String anImageFileName) {
        File anImageFile = new File(anImageFileName);
        Iterator it = ImageIO.getImageWritersBySuffix("png");
        try {
            ImageWriter iw = (ImageWriter) it.next();
            iw.setOutput(new FileImageOutputStream(anImageFile));
            iw.write((RenderedImage) buffer());
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } // catch
    } // saveImg

    // editing methods
    public void newBuffer(int x, int y) {
        BufferedImage bimage = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bimage.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, x, y);
        setBuffer(bimage);
    }

    public BufferedImage getBufferedImage(Image image) {
        BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(image, 0, 0, null);
        bGr.dispose();
        return bimage;
    }

    // guard helpers - every sprite-cell operation below divides a buffer's
    // pixel size by its cols/rows to get the cell size, which throws
    // ArithmeticException: / by zero on a buffer that's empty or was never
    // split into a grid with 'g'. Check first and say why instead of crashing.
    public void requireImage(int bufferIndex) {
        if (buffers[bufferIndex].buffer == null) {
            throw new IllegalStateException("Buffer " + (bufferIndex + 1)
                    + " is empty - press 'n' for a new blank image or 'l' to load one first.");
        }
    }

    public void requireGrid(int bufferIndex) {
        requireImage(bufferIndex);
        if (buffers[bufferIndex].cols == 0 || buffers[bufferIndex].rows == 0) {
            throw new IllegalStateException("Buffer " + (bufferIndex + 1)
                    + " has no grid yet - press 'g' to split it into cells first.");
        }
    }

    public void clearSprite(int c, int r) {
        requireGrid(currentBuffer);
        BufferedImage bi = buffer();
        Graphics g = bi.getGraphics();
        int bufc = cols();
        int bufr = rows();
        int sprw = spriteWidth();
        int sprh = spriteHeight();
        g.fillRect(c * sprw, r * sprh, sprw, sprh);
    }

    public void moveSprite(int tc, int tr) {
        moveSprite(getSelectedCol(), getSelectedRow(), tc, tr);
    }

    public void moveSprite(int fc, int fr, int tc, int tr) {
        requireGrid(currentBuffer);
        int sprw = spriteWidth();
        int sprh = spriteHeight();
        BufferedImage bi = buffer();
        BufferedImage stampImage = bi.getSubimage(fc * sprw, fr * sprh, sprw, sprh);
        Graphics g = bi.getGraphics();
        g.drawImage(stampImage, tc * sprw, tr * sprh, null);
        g.fillRect(fc * sprw, fr * sprh, sprw, sprh);
    }

    public void copySprite(int tc, int tr) {
        requireGrid(currentBuffer);
        int fc = getSelectedCol();
        int fr = getSelectedRow();
        int sprw = spriteWidth();
        int sprh = spriteHeight();
        BufferedImage bi = buffer();
        BufferedImage stampImage = bi.getSubimage(fc * sprw, fr * sprh, sprw, sprh);
        Graphics g = bi.getGraphics();
        g.drawImage(stampImage, tc * sprw, tr * sprh, null);
    }

    public void pasteToBuffer(int buffer, int col, int row) {
        requireImage(currentBuffer);
        requireGrid(buffer);
        int sprw = spriteWidth(buffer);
        int sprh = spriteHeight(buffer);
        BufferedImage bi = buffer();
        BufferedImage stampImage = bi.getSubimage(0, 0, sprw, sprh);
        BufferedImage destBuffer = buffer(buffer);
        Graphics g = destBuffer.getGraphics();
        g.drawImage(stampImage, col * sprw, row * sprh, null);
    }
}
