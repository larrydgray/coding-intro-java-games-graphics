import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HelloFX extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Hello JavaFX");
        stage.setScene(new Scene(new StackPane(new Label("Hello, JavaFX!")), 400, 300));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}