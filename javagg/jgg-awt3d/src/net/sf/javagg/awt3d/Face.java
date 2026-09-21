package net.sf.javagg.awt3d;

import java.awt.*;

public class Face {
    public int[] indices;
    public Color fillColor;
    public Color edgeColor;

    public Face(int[] indices, Color fillColor, Color edgeColor) {
        this.indices = indices;
        this.fillColor = fillColor;
        this.edgeColor = edgeColor;
    }
}