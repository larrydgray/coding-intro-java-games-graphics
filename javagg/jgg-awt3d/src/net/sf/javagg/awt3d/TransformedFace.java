package net.sf.javagg.awt3d;

public class TransformedFace {
    public Face face;
    public double averageZ;

    public TransformedFace(Face face, double averageZ) {
        this.face = face;
        this.averageZ = averageZ;
    }
}
