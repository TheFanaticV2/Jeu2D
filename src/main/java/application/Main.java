package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            StackPane root = FXMLLoader.load(getClass().getResource("vue.fxml"));
            Scene scene = new Scene(root, 27*48, 15*48);
            root.requestFocus();
            primaryStage.setTitle("Deplacement");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(true);
            primaryStage.setScene(scene);
            primaryStage.setOnCloseRequest(event -> System.exit(0));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
