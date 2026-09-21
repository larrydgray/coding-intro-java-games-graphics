package net.sf.javagg.awt3d;

public class fPoint3d {
    public double x;
    public double y;
    public double z;

    public fPoint3d() {
        this(0, 0, 0);
    }

    public fPoint3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public fPoint3d copy() {
        return new fPoint3d(x, y, z);
    }

    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void translate(double dx, double dy, double dz) {
        x += dx;
        y += dy;
        z += dz;
    }
}
