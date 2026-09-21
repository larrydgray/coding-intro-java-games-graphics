package net.sf.javagg.awt3d;

public class fMatrix3d {
    public double[][] m = new double[4][4];

    public fMatrix3d() {
        identity();
    }

    public void identity() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                m[i][j] = (i == j) ? 1 : 0;
            }
        }
    }

    public static fMatrix3d rotationX(double angle) {
        fMatrix3d r = new fMatrix3d();
        double c = Math.cos(angle);
        double s = Math.sin(angle);

        r.m[1][1] = c;
        r.m[1][2] = -s;
        r.m[2][1] = s;
        r.m[2][2] = c;

        return r;
    }

    public static fMatrix3d rotationY(double angle) {
        fMatrix3d r = new fMatrix3d();
        double c = Math.cos(angle);
        double s = Math.sin(angle);

        r.m[0][0] = c;
        r.m[0][2] = s;
        r.m[2][0] = -s;
        r.m[2][2] = c;

        return r;
    }

    public static fMatrix3d rotationZ(double angle) {
        fMatrix3d r = new fMatrix3d();
        double c = Math.cos(angle);
        double s = Math.sin(angle);

        r.m[0][0] = c;
        r.m[0][1] = -s;
        r.m[1][0] = s;
        r.m[1][1] = c;

        return r;
    }

    public static fMatrix3d translation(double x, double y, double z) {
        fMatrix3d t = new fMatrix3d();
        t.m[0][3] = x;
        t.m[1][3] = y;
        t.m[2][3] = z;
        return t;
    }

    public fMatrix3d multiply(fMatrix3d other) {
        fMatrix3d result = new fMatrix3d();

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                result.m[row][col] = 0;

                for (int i = 0; i < 4; i++) {
                    result.m[row][col] += this.m[row][i] * other.m[i][col];
                }
            }
        }

        return result;
    }

    public fPoint3d transform(fPoint3d p) {
        double x = p.x * m[0][0] + p.y * m[0][1] + p.z * m[0][2] + m[0][3];
        double y = p.x * m[1][0] + p.y * m[1][1] + p.z * m[1][2] + m[1][3];
        double z = p.x * m[2][0] + p.y * m[2][1] + p.z * m[2][2] + m[2][3];

        return new fPoint3d(x, y, z);
    }
}
