package net.sf.javagg.gamescreen;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DemoGraphics {

    public DemoGraphics(){

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

        MiniGraphics graphics = screen.getMiniGraphics();

        graphics.line(20, 20, 200, 80, Color.WHITE);
        graphics.rect(40, 100, 120, 60, Color.GREEN);
        graphics.fillRect(200, 100, 80, 60, Color.BLUE);
        graphics.circle(120, 250, 50, Color.YELLOW);
        graphics.fillCircle(260, 250, 40, Color.RED);
        graphics.ellipse(350, 200, 120, 60, Color.MAGENTA);
        graphics.arc(350, 300, 120, 80, 0, 180, Color.CYAN);

        graphics.turtleTo(300, 100);
        graphics.turtleColor(Color.ORANGE);

        for(int i = 0; i < 4; i++) {
            graphics.forward(80);
            graphics.right(90);
        }

        graphics.turtleTo(300, 250);
        graphics.turtleColor(Color.GREEN);

        for(int i = 0; i < 30; i++) {
            graphics.forward(i * 4);
            graphics.right(90);
        }

        frame.add(screen);
        frame.pack();
        frame.setVisible(true);

        screen.repaint();
    }
}