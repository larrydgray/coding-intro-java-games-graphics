package net.sf.javagg.gamescreen;

import java.awt.*;
import java.util.ArrayList;

public class MiniGraphics {

    private Graphics g;


    public void render(Graphics g) {
        for (DrawCommand command : commands) {
            command.draw(g);
        }
    }

    private final ArrayList<DrawCommand> commands = new ArrayList<>();

    private interface DrawCommand {
        void draw(Graphics g);
    }

    public void clearGraphics() {
        commands.clear();

    }

    public void line(int x1, int y1, int x2, int y2, Color color) {
        commands.add(g -> {
            g.setColor(color);
            g.drawLine(x1, y1, x2, y2);
        });
    }

    public void rect(int x, int y, int w, int h, Color color) {
        commands.add(g -> {
            g.setColor(color);
            g.drawRect(x, y, w, h);
        });

    }

    public void fillRect(int x, int y, int w, int h, Color color) {
        commands.add(g -> {
            g.setColor(color);
            g.fillRect(x, y, w, h);
        });

    }

    public void circle(int cx, int cy, int r, Color color) {
        commands.add(g -> {
            g.setColor(color);
            g.drawOval(cx - r, cy - r, r * 2, r * 2);
        });

    }

    public void fillCircle(int cx, int cy, int r, Color color) {
        commands.add(g -> {
            g.setColor(color);
            g.fillOval(cx - r, cy - r, r * 2, r * 2);
        });

    }

    public void ellipse(int x, int y, int w, int h, Color color) {
        commands.add(g -> {
            g.setColor(color);
            g.drawOval(x, y, w, h);
        });

    }

    public void arc(int x, int y, int w, int h, int startAngle, int arcAngle, Color color) {
        commands.add(g -> {
            g.setColor(color);
            g.drawArc(x, y, w, h, startAngle, arcAngle);
        });

    }

    private double turtleX = 0;
    private double turtleY = 0;
    private double turtleAngle = 0;
    private boolean penDown = true;
    private Color turtleColor = Color.WHITE;

    public void turtleTo(double x, double y) {
        turtleX = x;
        turtleY = y;
    }

    public void turtleColor(Color color) {
        turtleColor = color;
    }

    public void penUp() {
        penDown = false;
    }

    public void penDown() {
        penDown = true;
    }

    public void right(double degrees) {
        turtleAngle += degrees;
    }

    public void left(double degrees) {
        turtleAngle -= degrees;
    }

    public void forward(double distance) {
        double radians = Math.toRadians(turtleAngle);

        double newX = turtleX + Math.cos(radians) * distance;
        double newY = turtleY + Math.sin(radians) * distance;

        if (penDown) {
            line(
                    (int)Math.round(turtleX),
                    (int)Math.round(turtleY),
                    (int)Math.round(newX),
                    (int)Math.round(newY),
                    turtleColor
            );
        }

        turtleX = newX;
        turtleY = newY;
    }

    public void backward(double distance) {
        forward(-distance);
    }

    public void setAngle(double degrees) {
        turtleAngle = degrees;
    }

    public void jumpTo(double x, double y) {
        turtleX = x;
        turtleY = y;
    }

    public void home() {
        turtleX = 0;
        turtleY = 0;
        turtleAngle = 0;
    }

    public void polygon(int sides, double length) {
        double angle = 360.0 / sides;

        for(int i = 0; i < sides; i++) {
            forward(length);
            right(angle);
        }
    }





}
