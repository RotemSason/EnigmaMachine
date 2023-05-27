import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenAllies extends Application {

        @Override
        public void start(Stage primaryStage) throws Exception {

            primaryStage.setTitle("Allies");
            Parent load = FXMLLoader.load(getClass().getResource("MainScreensAllies/MainScreensAllies.fxml"));
            Scene scene = new Scene(load, 800, 600);


            primaryStage.setScene(scene);
            primaryStage.show();
        }

        public static void main(String[] args) {
            launch(ScreenAllies.class);
        }
    }


