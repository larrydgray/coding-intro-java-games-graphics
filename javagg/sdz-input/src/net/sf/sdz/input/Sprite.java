package net.sf.sdz.input;

import java.awt.*;

/** Base sprite: visible/active flags, and abstract paint/update hooks. */
public abstract class Sprite {
    protected boolean visible;
    protected boolean active;

    abstract void paint(Graphics g);

    abstract void update();

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void suspend() {
        setVisible(false);
        setActive(false);
    }

    public void restore() {
        setVisible(true);
        setActive(true);
    }
}
