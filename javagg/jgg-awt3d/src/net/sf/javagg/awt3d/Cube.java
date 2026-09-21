package net.sf.javagg.awt3d;

import java.awt.*;
public class Cube {

    public Face[] faces = {
            new Face(new int[]{0, 3, 2, 1}, Color.red, Color.white),
            new Face(new int[]{4, 5, 6, 7}, Color.green, Color.white),
            new Face(new int[]{0, 1, 5, 4}, Color.blue, Color.white),
            new Face(new int[]{3, 7, 6, 2}, Color.yellow, Color.white),
            new Face(new int[]{1, 2, 6, 5}, Color.cyan, Color.white),
            new Face(new int[]{0, 4, 7, 3}, Color.magenta, Color.white)
    };
    public fPoint3d[] vertices;

    public Cube(double size) {
        double s = size / 2.0;

        vertices = new fPoint3d[]{
                new fPoint3d(-s, -s, -s),
                new fPoint3d( s, -s, -s),
                new fPoint3d( s,  s, -s),
                new fPoint3d(-s,  s, -s),

                new fPoint3d(-s, -s,  s),
                new fPoint3d( s, -s,  s),
                new fPoint3d( s,  s,  s),
                new fPoint3d(-s,  s,  s)
        };
    }
}
