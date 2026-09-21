package net.sf.sdz.iso3dfx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

/**
 * Controller for simple3d.fxml: same isometric surface projection and
 * terrain generation as the Swing version (Iso3D + IsoPanel in
 * sdz-3d-swing), redrawn on a JavaFX Canvas and driven by FXML-bound button
 * handlers instead of Swing ActionListeners.
 */
public class Simple3dController implements Initializable {

    public Simple3dController() throws IOException {

        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("simple3d.fxml"));
        // fxmlLoader.setRoot(this);
        // fxmlLoader.setController(this);
        // fxmlLoader.load();
    }

    public class Point2D {

        public Point2D(int x, int y) {
            this.x = x;
            this.y = y;
        }
        int x = 0;
        int y = 0;
    }

    public class Point3D {

        public Point3D(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        int x = 0;
        int y = 0;
        int z = 0;
    }
    int step = 50;
    int mode = 1;
    public final int LEFT_ISO = 0;
    public final int CENTER_ISO = 1;
    public final int RIGHT_ISO = 2;
    public float ySkew = 1.0f;

    public void setTransformMode(int mode) {
        this.mode = mode;
    }
    float y2dSkew = 1.0f;

    public Point2D transform3D(Point3D point3D) throws Exception {
        switch (mode) {
            case LEFT_ISO:
                return new Point2D(point3D.x + point3D.z, (int) (((-point3D.y * y2dSkew) + point3D.z) * ySkew));
            case CENTER_ISO:
                return new Point2D(point3D.x + point3D.z, (int) (((-point3D.y * y2dSkew) + point3D.z - point3D.x) * ySkew));
            case RIGHT_ISO:
                return new Point2D(point3D.x - point3D.z, (int) (((-point3D.y * y2dSkew) + point3D.z) * ySkew));
            default:
                throw new Exception("Invalid Transformation Mode! [" + mode + "]?");
        }
    }

    public class Surface3D {

        int xSquareSize = 5, ySquareSize = 5;
        int xGridSize, yGridSize;
        int[][] surfaceElevations = null;
        int[][] surfaceElevationsAverage = null;

        public int getElevation(int x, int y) {
            return surfaceElevations[y][x];
        }

        public void setElevation(int x, int y, int elevation) {
            surfaceElevations[y][x] = elevation;
        }

        public int randomElevation(int minHeight, int maxHeight) {
            return (int) (Math.random() * (maxHeight - minHeight) + minHeight);
        }

        public int getElevationAvg(int x, int y) {
            return surfaceElevations[y][x];
        }

        public void setElevationAvg(int x, int y, int elevation) {
            surfaceElevationsAverage[y][x] = elevation;
        }

        public int average(int x, int y) {
            int total = getElevation(x - 1, y - 1);
            total += getElevation(x, y - 1);
            total += getElevation(x + 1, y - 1);
            total += getElevation(x - 1, y);
            total += getElevation(x, y);
            total += getElevation(x + 1, y);
            total += getElevation(x - 1, y + 1);
            total += getElevation(x, y + 1);
            total += getElevation(x + 1, y + 1);
            return (int) total / 9;
        }

        Surface3D(int xGridSize, int yGridSize, int nRandomHeights, int minHeight, int maxHeight) {
            this.xGridSize = xGridSize;
            this.yGridSize = yGridSize;
            surfaceElevations = new int[xGridSize][yGridSize];
            int randomX, randomY;
            for (int i = 0; i < nRandomHeights; i++) {
                randomX = (int) (Math.random() * (xGridSize - 2) + 1); //-2 leaves 0's in outsize edge points
                randomY = (int) (Math.random() * (yGridSize - 2) + 1);
                setElevation(randomX, randomY, randomElevation(minHeight, maxHeight));
            }
            surfaceElevationsAverage = new int[xGridSize][yGridSize];
            for (int i = 0; i < 2; i++) {
                for (int y = 1; y < yGridSize - 1; y++) {
                    for (int x = 1; x < xGridSize - 1; x++) {
                        setElevationAvg(x, y, average(x, y));
                    }
                }
                surfaceElevations = surfaceElevationsAverage;
            }
        }
    }
    Surface3D surface = new Surface3D(50, 50, 150, -300, 300);

    public int originX = 0;
    public int originY = 0;
    //public float ySkew = 1.0f;
    //public Iso3D iso3D = new Iso3D();
    GraphicsContext gc = null;
    Simple3dController simple3dController = null;

    private void setElevationColor(int y1, int y2) {
        int yAvg = (y1 + y2) / 2;
        if ((yAvg > -100) && (yAvg < -10)) gc.setStroke(Color.DARKBLUE);
        if ((yAvg > -10) && (yAvg < -2)) gc.setStroke(Color.BLUE);
        if ((yAvg > -2) && (yAvg < 0)) gc.setStroke(Color.AQUAMARINE);
        if ((yAvg > 0) && (yAvg < 10)) gc.setStroke(Color.GREEN);
        if ((yAvg > 10) && (yAvg < 15)) gc.setStroke(Color.GRAY);
        if ((yAvg > 15) && (yAvg < 20)) gc.setStroke(Color.WHITE);
    }

    public void draw2DShapes() {
        gc.setLineWidth(1.0);
        gc.setFill(Color.BLACK);
        //gc.setStroke(Color.BLACK);
        //gc.clearRect(0, 0, canvas3d.getWidth(), canvas3d.getHeight());
        gc.fillRect(0.0d, 0.0d, canvas3d.getWidth(), canvas3d.getHeight());
        //gc.strokeRect(0, 0, canvas3d.getWidth(), canvas3d.getHeight());
        // = canvas3d.getParent();

        //gc.setStroke(Color.BLUE);
        Point2D point1;
        Point2D point2;
        Point3D point1_3D;
        Point3D point2_3D;
        int x;
        int y;
        int xs;
        int ys;
        xs = surface.xSquareSize;
        ys = surface.ySquareSize;
        gc.setStroke(Color.GREEN);
        for (y = 0; y < surface.yGridSize; y++) {
            for (x = 0; x < surface.xGridSize; x++) {
                try {
                    if (x < surface.xGridSize - 1) {
                        point1_3D = new Point3D(x * xs, (int) (surface.getElevationAvg(x, y) * ySkew), y * ys);
                        point2_3D = new Point3D((x + 1) * xs, (int) (surface.getElevationAvg(x + 1, y) * ySkew), y * ys);

                        point1 = transform3D(point1_3D);
                        point2 = transform3D(point2_3D);
                        setElevationColor(point1_3D.y, point2_3D.y);
                        gc.strokeLine(point1.x + originX, point1.y + originY, point2.x + originX, point2.y + originY);
                        //gc.moveTo(point1.x + originX, point1.y + originY);
                        //gc.lineTo(point2.x + originX, point2.y + originY);
                        //gc.stroke();

                        //from Swing gc.drawLine(point1.x + originX, point1.y + originY, point2.x + originX, point2.y + originY);
                    }
                    if (y < surface.yGridSize - 1) {
                        point1_3D = new Point3D(x * xs, (int) (surface.getElevationAvg(x, y) * ySkew), y * ys);
                        point2_3D = new Point3D(x * xs, (int) (surface.getElevationAvg(x, y + 1) * ySkew), (y + 1) * ys);
                        point1 = transform3D(point1_3D);
                        point2 = transform3D(point2_3D);
                        setElevationColor(point1_3D.y, point2_3D.y);
                        gc.strokeLine(point1.x + originX, point1.y + originY, point2.x + originX, point2.y + originY);
                        //gc.moveTo(point1.x + originX, point1.y + originY);
                        //gc.lineTo(point2.x + originX, point2.y + originY);
                        //gc.stroke();

                        //from Swing gc.drawLine(point1.x + originX, point1.y + originY, point2.x + originX, point2.y + originY);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        gc.stroke();
    }

    @FXML // fx:id="canvas3d"
    private Canvas canvas3d;

    @FXML // fx:id="modeLeftBtn"
    private Button modeLeftBtn; // Value injected by FXMLLoader

    @FXML // fx:id="modeCenterBtn"
    private Button modeCenterBtn; // Value injected by FXMLLoader

    @FXML // fx:id="modeRightBtn"
    private Button modeRightBtn; // Value injected by FXMLLoader

    @FXML // fx:id="leftBtn"
    private Button leftBtn; // Value injected by FXMLLoader

    @FXML // fx:id="rightBtn"
    private Button rightBtn; // Value injected by FXMLLoader

    @FXML // fx:id="upBtn"
    private Button upBtn; // Value injected by FXMLLoader

    @FXML // fx:id="downBtn"
    private Button downBtn; // Value injected by FXMLLoader

    @FXML // fx:id="yPlusBtn"
    private Button yPlusBtn; // Value injected by FXMLLoader

    @FXML // fx:id="yMinusBtn"
    private Button yMinusBtn; // Value injected by FXMLLoader

    @FXML // fx:id="twoDYPlusBtn"
    private Button twoDYPlusBtn; // Value injected by FXMLLoader

    @FXML // fx:id="zoomPlusBtn"
    private Button zoomPlusBtn; // Value injected by FXMLLoader

    @FXML // fx:id="zoomMinusBtn"
    private Button zoomMinusBtn; // Value injected by FXMLLoader

    @FXML
    void doModeLeft(ActionEvent event) {
        mode = 0;
        draw2DShapes();
    }

    @FXML
    void doModeCenter(ActionEvent event) {
        mode = 1;
        draw2DShapes();
    }

    @FXML
    void doModeRight(ActionEvent event) {
        mode = 2;
        draw2DShapes();
    }

    @FXML
    void up(ActionEvent event) {
        originY -= step;
        draw2DShapes();
    }

    @FXML
    void down(ActionEvent event) {
        originY += step;
        draw2DShapes();
    }

    @FXML
    void left(ActionEvent event) {
        originX -= step;
        draw2DShapes();
    }

    @FXML
    void right(ActionEvent event) {
        originX += step;
        draw2DShapes();
    }

    @FXML
    void yMinus(ActionEvent event) {
        ySkew += 0.1f;
        if (ySkew == 0) {
            ySkew = 0.1f;
        }
        draw2DShapes();
    }

    @FXML
    void yPlus(ActionEvent event) {
        ySkew -= 0.1f;
        if (ySkew == 0) {
            ySkew = -0.1f;
        }
        draw2DShapes();
    }

    @FXML
    void twoDYPlus(ActionEvent event) {
        y2dSkew += .1f;
        draw2DShapes();
    }

    @FXML
    void zoomMinus(ActionEvent event) {
        surface.xSquareSize -= 1;
        surface.ySquareSize -= 1;
        if (surface.xSquareSize <= 0) {
            surface.xSquareSize = 1;
        }
        if (surface.ySquareSize <= 0) {
            surface.ySquareSize = 1;
        }

        draw2DShapes();
    }

    @FXML
    void zoomPlus(ActionEvent event) {
        surface.xSquareSize += 1;
        surface.ySquareSize += 1;
        draw2DShapes();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        assert modeLeftBtn != null : "fx:id=\"modeLeftBtn\" was not injected: check your FXML file 'simple3d.fxml'.";
        assert modeCenterBtn != null : "fx:id=\"modeCenterBtn\" was not injected: check your FXML file 'simple3d.fxml'.";
        assert modeRightBtn != null : "fx:id=\"modeRightBtn\" was not injected: check your FXML file 'simple3d.fxml'.";
        assert leftBtn != null : "fx:id=\"leftBtn\" was not injected: check your FXML file 'simple3d.fxml'.";
        assert rightBtn != null : "fx:id=\"rightBtn\" was not injected: check your FXML file 'simple3d.fxml'.";
        assert upBtn != null : "fx:id=\"upBtn\" was not injected: check your FXML file 'simple3d.fxml'.";
        assert downBtn != null : "fx:id=\"downBtn\" was not injected: check your FXML file 'simple3d.fxml'.";
        assert yPlusBtn != null : "fx:id=\"yPlusBtn\" was not injected: check your FXML file 'simple3d.fxml'.";
        assert yMinusBtn != null : "fx:id=\"yMinusBtn\" was not injected: check your FXML file 'simple3d.fxml'.";
        assert twoDYPlusBtn != null : "fx:id=\"twoDYPlusBtn\" was not injected: check your FXML file 'simple3d.fxml'.";
        assert zoomPlusBtn != null : "fx:id=\"zoomPlusBtn\" was not injected: check your FXML file 'simple3d.fxml'.";
        assert zoomMinusBtn != null : "fx:id=\"zoomMinusBtn\" was not injected: check your FXML file 'simple3d.fxml'.";

        gc = canvas3d.getGraphicsContext2D();
        draw2DShapes();

    }

}
