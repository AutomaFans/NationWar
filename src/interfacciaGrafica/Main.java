package interfacciaGrafica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Boolean isShowed = false;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent rootP = FXMLLoader.load(getClass().getResource("menu.fxml"));

        Scene scene = new Scene(rootP);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Progetto");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
