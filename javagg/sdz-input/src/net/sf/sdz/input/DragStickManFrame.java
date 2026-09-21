package net.sf.sdz.input;

import java.awt.*;
import java.awt.event.*;

/**
 * Draggable stick-man demo as an AWT Frame: click and drag anywhere within
 * the stick man's bounding box to move him, or press Left/Right arrow keys
 * to shrink/grow him.
 */
public class DragStickManFrame extends Frame implements MouseListener, MouseMotionListener, KeyListener {

    Font courierFont;
    String testString = "Drag the StickMan!";
    DragStickMan dragStickMan = new DragStickMan(0, 0, 50, 100, Color.red);
    int oldx, oldy;

    public DragStickManFrame() {
        super("Stick Man Drag and Drop");
        courierFont = new Font("Courier", Font.BOLD + Font.ITALIC, 14);
        setSize(400, 400);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        g.setFont(courierFont);
        FontMetrics fontMetrics = g.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(testString);
        Insets insets = getInsets();
        int width = insets.left + (getWidth() - insets.left - insets.right - stringWidth) / 2;
        int height = insets.top + 30;
        g.setColor(Color.green);
        g.drawString(testString, width, height);
        dragStickMan.paint(g);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (dragStickMan.inside(x, y)) {
            oldx = x;
            oldy = y;
            dragStickMan.setDraggable(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dragStickMan.setDraggable(false);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (dragStickMan.isDraggable()) {
            int x = e.getX();
            int y = e.getY();
            dragStickMan.translate(x - oldx, y - oldy);
            oldx = x;
            oldy = y;
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                dragStickMan.grow();
                repaint();
                break;
            case KeyEvent.VK_LEFT:
                dragStickMan.shrink();
                repaint();
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        new DragStickManFrame();
    }
}
