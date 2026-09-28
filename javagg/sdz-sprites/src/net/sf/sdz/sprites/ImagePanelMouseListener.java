// Author Larry Gray CPL Common Public License  Software Developer Zone
package net.sf.sdz.sprites;

import java.awt.event.*;
import javax.swing.*;

/**
 * Mouse shortcuts for the Sprite Organizer: left-click selects a cell (or
 * shift-left-click copies the selected sprite onto it), right-click clears
 * a cell (or shift-right-click moves the selected sprite onto it).
 */
public class ImagePanelMouseListener extends MouseAdapter {
    // debug methods
    public void log(String s) {
        System.out.println(s);
    }

    //fields
    ImageBuffers buffers = null;
    ImagePanel imagePanel = null;

    // constructors
    public ImagePanelMouseListener(ImageBuffers buffers, ImagePanel imagePanel) {
        this.buffers = buffers;
        this.imagePanel = imagePanel;
    }

    // adapter methods
    public void mouseClicked(MouseEvent me) {
        int button = me.getButton();
        boolean leftClick = false;
        boolean rightClick = false;
        boolean shift = false;
        boolean alt = false;
        boolean control = false;
        if (button == me.BUTTON1) leftClick = true;
        //if(button==me.BUTTON2) log("Wheel Click");
        if (button == me.BUTTON3) rightClick = true;
        if (me.isAltDown()) alt = true;
        if (me.isControlDown()) control = true;
        if (me.isShiftDown()) shift = true;
        log("shift:" + shift);
        log("leftClick:" + leftClick);
        try {
            // dividing by the current buffer's cell size below throws if it
            // has no image or was never split into a grid with 'g' yet.
            buffers.requireGrid(buffers.currentBuffer);
            int x = me.getX();
            int y = me.getY();
            int col = x / buffers.spriteWidth();
            int row = y / buffers.spriteHeight();
            boolean selected = buffers.select();
            // click handling logic
            if (selected) {
                if (leftClick) {
                    if (shift) {
                        buffers.copySprite(col, row);
                    } else {
                        buffers.setSelectedColRow(col, row);
                    }
                } else if (rightClick) {
                    if (shift) {
                        buffers.moveSprite(col, row);
                    } else {
                        buffers.clearSprite(col, row);
                    }
                }
            } else {
                buffers.setSelectedColRow(col, row);
                buffers.setSelect(true);
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(imagePanel, ex.getMessage(), "Can't Do That Yet", JOptionPane.WARNING_MESSAGE);
        }
        imagePanel.repaint();
    }
}
