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
            Scene scene = new Scene(root, Param.WIDTH*Param.TUILE_TAILLE, Param.HEIGHT*Param.TUILE_TAILLE);
            root.requestFocus();
            primaryStage.setTitle("Deplacement");
            primaryStage.setResizable(false);
            primaryStage.setMaximized(true);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
