package net.sf.javagg.gamescreen;


import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Demo {

    public Demo(){

    }

    public static void main(String[] args) {
        Frame frame = new Frame("GameScreen Demo");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        GameScreen screen = new GameScreen(40, 20);
        screen.setScreenFont(FontType.CONSOLAS);

        ScreenBuffer buffer = screen.getBuffer();

        buffer.clear(Color.BLACK);

        buffer.write(2, 2, "JAVA COLOR CONSOLE", Color.YELLOW, Color.BLUE);
        buffer.write(2, 4, "HP: 25", Color.RED, Color.BLACK);
        buffer.write(2, 5, "Gold: 100", Color.YELLOW, Color.BLACK);
        buffer.write(2, 7, "Move ideas later: WASD / Arrow Keys", Color.GREEN, Color.BLACK);

        buffer.write(10, 10, "@", Color.WHITE, Color.BLACK);
        buffer.write(12, 10, "Goblin", Color.GREEN, Color.BLACK);
        buffer.write(12, 12, "Treasure Chest", Color.ORANGE, Color.BLACK);

        frame.add(screen);
        frame.pack();
        frame.setVisible(true);

        screen.repaint();
    }
}