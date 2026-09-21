// Author Larry Gray CPL Common Public License  Software Developer Zone
package net.sf.sdz.sprites;

import java.awt.event.*;
import javax.swing.*;

/**
 * Keyboard shortcuts for the Sprite Organizer: 0-9 selects a buffer, l/g/c/m/n/p/s/o
 * trigger load/split/clear/move/new/paste/save/toggle-grid operations, prompting
 * for any needed parameters via input dialogs on the {@link ImagePanel}.
 */
public class ImagePanelKeyListener extends KeyAdapter {
    // fields
    ImageBuffers buffers = null;
    ImagePanel imagePanel = null;

    //constructor
    public ImagePanelKeyListener(ImageBuffers buffers, ImagePanel imagePanel) {
        this.buffers = buffers;
        this.imagePanel = imagePanel;
    }

    //listener utility methods
    void changeBuffer(int buffer) {
        buffers.setBuffer(buffer);
        imagePanel.repaint();
        imagePanel.statsPanel.updateStats();
    }

    void clearSprite(int c, int r) {
        buffers.clearSprite(c, r);
        imagePanel.repaint();
    }

    void moveSprite(int fc, int fr, int tc, int tr) {
        buffers.moveSprite(fc, fr, tc, tr);
        imagePanel.repaint();
    }

    void loadImage() {
        String fileName = JOptionPane.showInputDialog("Enter File Name");
        if (fileName == null) {
            System.out.println("The user canceled");
        } else {
            buffers.loadImage(fileName, imagePanel);
            imagePanel.repaint();
        }
    }

    void saveImage() {
        String fileName = JOptionPane.showInputDialog("Enter File Name");
        if (fileName == null) {
            System.out.println("The user canceled");
        } else {
            buffers.saveImage(fileName);
            imagePanel.repaint();
        }
    }

    void splitImage() {
        buffers.setCols(imagePanel.inputCols());
        buffers.setRows(imagePanel.inputRows());
        imagePanel.statsPanel.updateStats();
    }

    void newBuffer(int x, int y) {
        buffers.newBuffer(x, y);
        imagePanel.statsPanel.updateStats();
        imagePanel.repaint();
    }

    void pasteToBuffer(int buffer, int col, int row) {
        buffers.pasteToBuffer(buffer, col, row);
        imagePanel.repaint();
    }

    // main listener methods that use utility methods
    public void keyPressed(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
        char keyTyped = e.getKeyChar();
        switch (keyTyped) {
            case '1':
                changeBuffer(0);
                break;
            case '2':
                changeBuffer(1);
                break;
            case '3':
                changeBuffer(2);
                break;
            case '4':
                changeBuffer(3);
                break;
            case '5':
                changeBuffer(4);
                break;
            case '6':
                changeBuffer(5);
                break;
            case '7':
                changeBuffer(6);
                break;
            case '8':
                changeBuffer(7);
                break;
            case '9':
                changeBuffer(8);
                break;
            case '0':
                changeBuffer(9);
                break;
            case 'l':
                loadImage();
                break;
            case 'g':
                splitImage();
                break;
            case 'c':
                clearSprite(imagePanel.inputCol(), imagePanel.inputRow());
                break;
            case 'm':
                moveSprite(imagePanel.inputCol(), imagePanel.inputRow(), imagePanel.inputCol(), imagePanel.inputRow());
                break;
            case 'n':
                newBuffer(imagePanel.inputX(), imagePanel.inputY());
                break;
            case 'p':
                pasteToBuffer(imagePanel.inputBuffer(), imagePanel.inputCol(), imagePanel.inputRow());
                break;
            case 's':
                saveImage();
                break;
            case 'o':
                buffers.flipGrid();
                imagePanel.repaint();
            default:
                break;
        }
    }
}
