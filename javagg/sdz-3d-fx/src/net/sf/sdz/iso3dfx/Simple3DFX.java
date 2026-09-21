package net.sf.sdz.iso3dfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX/FXML rewrite of the Swing isometric surface demo (see sdz-3d-swing).
 * Loads simple3d.fxml as the scene root and shows it.
 */
public class Simple3DFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("simple3d.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("Simple3D FX");
        stage.setScene(scene);
        stage.setWidth(800);
        stage.setHeight(650);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            if (e.getCause().getClass().equals(AssertionError.class)) {
                e.getCause().printStackTrace();
            } else {
                e.getCause().printStackTrace();
                // handle the rest of the world exception
            }
        }
    }

}
