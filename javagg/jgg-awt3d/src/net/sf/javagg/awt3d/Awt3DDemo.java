package net.sf.javagg.awt3d;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Awt3DDemo extends Frame implements Runnable {
    private Thread loop;
    private boolean running = true;

    private Cube cube;
    private double angle = 0;
    private RenderMode renderMode = RenderMode.WIREFRAME;

    public Awt3DDemo() {
        super("AWT 3D Demo");

        setSize(800, 600);
        setBackground(Color.black);

        cube = new Cube(200);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                running = false;
                dispose();
                System.exit(0);
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case '1' -> renderMode = RenderMode.WIREFRAME;
                    case '2' -> renderMode = RenderMode.SOLID;
                    case '3' -> renderMode = RenderMode.SOLID_WITH_EDGES;
                }
            }
        });

        setVisible(true);

        loop = new Thread(this);
        loop.start();
    }

    @Override
    public void run() {
        while (running) {
            angle += 0.02;
            repaint();

            try {
                Thread.sleep(16);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private Point project(fPoint3d p) {
        double distance = 400;
        double scale = distance / (distance + p.z);

        int x = (int) (p.x * scale + getWidth() / 2);
        int y = (int) (p.y * scale + getHeight() / 2);

        return new Point(x, y);
    }

    @Override
    public void paint(Graphics g) {
        fMatrix3d rotX = fMatrix3d.rotationX(angle);
        fMatrix3d rotY = fMatrix3d.rotationY(angle);

        fMatrix3d transform = rotX.multiply(rotY);

        fPoint3d[] transformed = new fPoint3d[cube.vertices.length];
        Point[] projected = new Point[cube.vertices.length];

        for (int i = 0; i < cube.vertices.length; i++) {
            fPoint3d p = transform.transform(cube.vertices[i]);
            p.z += 300;

            transformed[i] = p;
            projected[i] = project(p);
        }

        int[][] edges = {
                {0, 1}, {1, 2}, {2, 3}, {3, 0},
                {4, 5}, {5, 6}, {6, 7}, {7, 4},
                {0, 4}, {1, 5}, {2, 6}, {3, 7}
        };

        // Build and sort faces by depth
        java.util.List<TransformedFace> faceList = new java.util.ArrayList<>();

        for (Face face : cube.faces) {
            if (!isFrontFacing(face, projected)) {
                continue;
            }

            double zSum = 0;

            for (int index : face.indices) {
                zSum += transformed[index].z;
            }

            double averageZ = zSum / face.indices.length;
            faceList.add(new TransformedFace(face, averageZ));
        }

        // Farthest first, nearest last
        faceList.sort((a, b) -> Double.compare(b.averageZ, a.averageZ));

        // Draw solid faces
        if (renderMode == RenderMode.SOLID ||
                renderMode == RenderMode.SOLID_WITH_EDGES) {

            for (TransformedFace transformedFace : faceList) {
                Face face = transformedFace.face;
                int count = face.indices.length;

                int[] xs = new int[count];
                int[] ys = new int[count];

                for (int i = 0; i < count; i++) {
                    Point p = projected[face.indices[i]];
                    xs[i] = p.x;
                    ys[i] = p.y;
                }

                g.setColor(face.fillColor);
                g.fillPolygon(xs, ys, count);

                if (renderMode == RenderMode.SOLID_WITH_EDGES) {
                    g.setColor(face.edgeColor);
                    g.drawPolygon(xs, ys, count);
                }
            }
        }

        // Draw wireframe edges last so they stay visible
        if (renderMode == RenderMode.WIREFRAME ) {

            g.setColor(Color.white);

            for (int[] edge : edges) {
                Point p1 = projected[edge[0]];
                Point p2 = projected[edge[1]];

                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }

        g.setColor(Color.gray);
        g.drawString("1 = Wireframe   2 = Solid   3 = Solid + Edges", 40, 70);
        g.drawString("Current mode: " + renderMode, 40, 90);
    }
    private boolean isFrontFacing(Face face, Point[] projected) {
        Point p0 = projected[face.indices[0]];
        Point p1 = projected[face.indices[1]];
        Point p2 = projected[face.indices[2]];

        int ax = p1.x - p0.x;
        int ay = p1.y - p0.y;

        int bx = p2.x - p0.x;
        int by = p2.y - p0.y;

        int cross = ax * by - ay * bx;

        return cross < 0;
    }
    public static void main(String[] args) {
        new Awt3DDemo();
    }
}