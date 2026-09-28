// Author Larry Gray CPL Common Public License  Software Developer Zone
package net.sf.sdz.sprites;

import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.awt.image.*;

/**
 * Main Frame and entry point for the Sprite Organizer: hosts an
 * {@link ImagePanel} for viewing/editing image buffers and a status bar
 * showing info about the buffer currently in view.
 */
class SpriteOrganizer extends JFrame {
    // debug methods
    public void log(String s) {
        System.out.println(s);
    }

    //inner class
    public class StatsPanel extends JPanel {
        JLabel statusLabel = null;
        ImageBuffers buffers = null;

        // constructor
        public StatsPanel(ImageBuffers buffers) {
            this.buffers = buffers;
            statusLabel = new JLabel();
            this.setBorder(new BevelBorder(BevelBorder.LOWERED));

            this.setPreferredSize(new Dimension(this.getWidth(), 16));
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
            this.add(statusLabel);
        }

        // ui status methods
        public void updateStats() {
            String status = "H-Help  |  Buff:" + (buffers.currentBuffer + 1) + " BufW:" + buffers.width() + " BufH:" + buffers.height();
            if (buffers.cols() != 0) {
                status += " BufC:" + buffers.cols() + " BufR:" + buffers.rows();
                status += " SprW:" + buffers.spriteWidth() + " SprH:" + buffers.spriteHeight();
            }
            statusLabel.setText(status);
        }

    }

    // fields
    StatsPanel statusPanel = null;
    ImageBuffers buffers = null;

    //constructors
    public SpriteOrganizer() {
        buffers = new ImageBuffers(10);
        statusPanel = new StatsPanel(buffers);
        ImagePanel imagePanel = new ImagePanel(buffers);
        imagePanel.setStatsPanel(statusPanel);
        // loads some images
        //buffers.loadImage("chess.png",0,this);
        //buffers.loadImage("colorlightning.jpg",1,this);
        //buffers.loadImage("dice.jpg",2,this);
        //buffers.loadImage("lens.jpg",3,this);
        //buffers.loadImage("mountain.jpg",4,this);
        //currentImage=buffers[0];
        // finish setup of ui
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("sprite.png"));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        this.add(statusPanel, BorderLayout.SOUTH);
        this.setTitle("Sprite Organizer 0.1 Alpha");
        statusPanel.updateStats();
        this.add(imagePanel);
        this.setSize(500, 500);
        this.setVisible(true);
    }

    //main bootstrap method
    public static void main(String args[]) {
        new SpriteOrganizer();
    }

}
