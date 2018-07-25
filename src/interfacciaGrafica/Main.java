package interfacciaGrafica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Boolean isShowed = false;			//Dichiara una variabile booleana chiamata isShowed e la imposta a false


    //METODO START
    //Crea un oggetto di tipo Parent chiamato rootP facendo riferimento e richiamando
    //l'intefaccia definita in FFXMLmenu.fxml.
    //Quindi rootP sarà l'interfaccia definita in FXMLmenu.fxml.
    //Poi crea una Scene (contenitore più  interno) chiamata scene per il nodo radice
    //specificato (rootP), specifica la scena da usare sullo Stage chiamato primaryStage
    //(con il metodo setScene), poi imposta il titolo dello Stage primaryStage con la scritta
    //"Progetto: NATION WAR", ed infine mostra la window impostando la visibilita' a true
    //(con il metodo show)
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent rootP = FXMLLoader.load(getClass().getResource("FXMLmenu.fxml"));
        Scene scene = new Scene(rootP);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Progetto: NATION WAR");
        primaryStage.show();
    }


    //VIENE SCRITTO AUTOMATICAMNETE
    public static void main(String[] args) {
        launch(args);
    }
}
