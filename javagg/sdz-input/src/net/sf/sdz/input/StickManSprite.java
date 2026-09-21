package net.sf.sdz.input;

import java.awt.*;

/** Draws a simple vector stick figure (head, torso, arms, legs) at (x, y) sized w x h. */
public class StickManSprite extends Sprite2D {
    int x, y;
    int h, w;
    Color c;
    boolean fill;

    public StickManSprite(int x, int y, int w, int h, Color c) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.c = c;
        restore();
    }

    public void update() {
    }

    public void paint(Graphics g) {
        int neckx = (int) (w / 2);
        int necky = (int) (h / 3);
        int waistx = (int) (w / 2);
        int waisty = (int) (h / 3 * 2);
        if (visible) {
            g.setColor(c);
            if (fill) {
                g.fillOval(neckx + x - 2, necky + y - 7, 5, 5);
                g.drawLine(neckx + x, necky + y, x, y);
                g.drawLine(neckx + x, necky + y, x + w, y);
                g.drawLine(neckx + x, necky + y, waistx + x, waisty + y);
                g.drawLine(waistx + x, waisty + y, x, y + h);
                g.drawLine(waistx + x, waisty + y, x + w, y + h);
            } else {
                g.drawOval(neckx + x - 2, necky + y - 7, 5, 5);
                g.drawLine(neckx + x, necky + y, x, y);
                g.drawLine(neckx + x, necky + y, x + w, y);
                g.drawLine(neckx + x, necky + y, waistx + x, waisty + y);
                g.drawLine(waistx + x, waisty + y, x, y + h);
                g.drawLine(waistx + x, waisty + y, x + w, y + h);
            }
        }
    }
}
