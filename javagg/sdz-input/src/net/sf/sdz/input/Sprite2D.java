package net.sf.sdz.input;

import java.awt.*;

/** Adds 2D position, color, and fill state to {@link Sprite}. */
public abstract class Sprite2D extends Sprite {
    protected int locx;
    protected int locy;
    Color color;
    boolean fill;

    public boolean getFill() {
        return fill;
    }

    public void setFill(Boolean fill) {
        this.fill = fill;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
