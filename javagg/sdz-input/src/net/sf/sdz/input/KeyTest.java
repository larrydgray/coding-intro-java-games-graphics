package net.sf.sdz.input;

import java.awt.*;
import java.awt.event.*;

/**
 * Minimal keyboard input demo as an AWT Frame: prints which key was pressed
 * (special-casing UP arrow and F1), and shows it in a Label standing in for
 * the applet status bar the original article used.
 */
public class KeyTest extends Frame implements KeyListener {

    Label statusLabel = new Label("Press a key...");

    public KeyTest() {
        super("Keyboard Input");
        setLayout(new BorderLayout());
        add(statusLabel, BorderLayout.SOUTH);
        addKeyListener(this);
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
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            System.out.println("UP");
        } else if (key == KeyEvent.VK_F1) {
            System.out.println("F1");
        } else {
            if (e.isControlDown()) {
                System.out.print("CTRL+");
            }
            System.out.println("key:" + e.getKeyChar());
        }
        statusLabel.setText("Hello you pressed " + e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        new KeyTest();
    }
}
