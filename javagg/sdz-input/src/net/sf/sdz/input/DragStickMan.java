package net.sf.sdz.input;

import java.awt.*;

/** Adds drag state and hit-testing/resize helpers on top of {@link StickManSprite}. */
public class DragStickMan extends StickManSprite {
    protected boolean draggable;

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public boolean inside(int x, int y) {
        return (this.x <= x && this.y <= y && (this.x + w >= x) && (this.y + h >= y));
    }

    public void translate(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void grow() {
        w++;
        h++;
    }

    public void shrink() {
        if (w > 0) {
            w--;
        }
        if (h > 0) {
            h--;
        }
    }

    public DragStickMan(int x, int y, int w, int h, Color c) {
        super(x, y, w, h, c);
        fill = true;
        draggable = false;
    }
}
