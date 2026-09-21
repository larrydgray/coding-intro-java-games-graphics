package net.sf.sdz.input;

import java.awt.*;
import java.awt.event.*;

/**
 * Minimal mouse input demo as an AWT Frame: logs press/release/move/drag/
 * enter/exit events with their (x, y) position.
 */
public class MouseTest extends Frame implements MouseListener, MouseMotionListener {

    public MouseTest() {
        super("Mouse Input");
        addMouseListener(this);
        addMouseMotionListener(this);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
        setSize(300, 200);
        setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("mouseDown at (" + e.getX() + ", " + e.getY() + ")");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("mouseUp at (" + e.getX() + ", " + e.getY() + ")");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("mouseMove at (" + e.getX() + ", " + e.getY() + ")");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("mouseDrag at (" + e.getX() + ", " + e.getY() + ")");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("mouseEnter at (" + e.getX() + ", " + e.getY() + ")");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("mouseExit at (" + e.getX() + ", " + e.getY() + ")");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    public static void main(String[] args) {
        new MouseTest();
    }
}
