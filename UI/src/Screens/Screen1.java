package Screens;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Screen1 extends Application {

        @Override
        public void start(Stage primaryStage) throws Exception {

            primaryStage.setTitle("Enigma Machine");
            Parent load = FXMLLoader.load(getClass().getResource("MainScreens/MainScreens.fxml"));
            Scene scene = new Scene(load, 800, 600);


            primaryStage.setScene(scene);
            primaryStage.show();
        }

    public static void main(String[] args) {
          launch(Screen1.class);
    }
}


