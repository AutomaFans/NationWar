package interfacciaGrafica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    //Dichiara una variabile booleana chiamata isShowed e la imposta a false
    public static Boolean isShowed = false;


    //METODO START
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent rootP = FXMLLoader.load(getClass().getResource("FXMLmenu.fxml"));
        Scene scene = new Scene(rootP);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Progetto: NATION WAR");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    //MAIN
    public static void main(String[] args) {
        launch(args);
    }
}
