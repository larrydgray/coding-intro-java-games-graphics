// Course example © 2026 Larry D. Gray.
// For enrolled-student educational use; see README.md.

import java.awt.*;
import java.util.ArrayList;

/**
 * A small drawing layer that renders on top of the GameScreen's character
 * grid using ordinary pixel coordinates (lines, rectangles, circles), plus
 * a simple turtle-graphics API. Not used by the example games in this
 * folder, but available if you want to draw shapes over the text grid.
 */
public class MiniGraphics {

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
        line(x1, y1, x2, y2, 1, color);
    }

    /** Same as {@link #line(int, int, int, int, Color)} but drawn with a stroke thicker than 1 pixel. */
    public void line(int x1, int y1, int x2, int y2, int width, Color color) {
        commands.add(g -> {
            g.setColor(color);
            if (width > 1 && g instanceof Graphics2D) {
                Graphics2D g2 = (Graphics2D) g;
                Stroke oldStroke = g2.getStroke();
                g2.setStroke(new BasicStroke(width));
                g2.drawLine(x1, y1, x2, y2);
                g2.setStroke(oldStroke);
            } else {
                g.drawLine(x1, y1, x2, y2);
            }
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
    private int turtlePenWidth = 1;

    public void turtleTo(double x, double y) {
        turtleX = x;
        turtleY = y;
    }

    /** The turtle's current x position — for code that wants to use the turtle as its actual state tracker. */
    public double getTurtleX() {
        return turtleX;
    }

    /** The turtle's current y position — for code that wants to use the turtle as its actual state tracker. */
    public double getTurtleY() {
        return turtleY;
    }

    /** The turtle's current heading in degrees — for code that wants to use the turtle as its actual state tracker. */
    public double getTurtleAngle() {
        return turtleAngle;
    }

    public void turtleColor(Color color) {
        turtleColor = color;
    }

    /** Sets how thick (in pixels) the turtle's trail is from now on. */
    public void penWidth(int width) {
        turtlePenWidth = Math.max(1, width);
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
                    (int) Math.round(turtleX),
                    (int) Math.round(turtleY),
                    (int) Math.round(newX),
                    (int) Math.round(newY),
                    turtlePenWidth,
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

        for (int i = 0; i < sides; i++) {
            forward(length);
            right(angle);
        }
    }
}
