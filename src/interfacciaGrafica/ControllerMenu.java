package interfacciaGrafica;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ControllerMenu implements Initializable {

    @FXML
    private AnchorPane rootPane;			//AnchorPane chiamato rootPane

    @FXML
    private Button buttonPlay;				//Bottone chiamato buttonPlay
    @FXML
    private Button buttonExit;

    //METODO CLICK PLAY
    //Crea un oggetto di tipo BorderPane chiamato impostazioniGriglia facendo riferimento e richiamando
    //l'intefaccia definita in FXMLimpostazioniGriglia.fxml.
    //Quindi impostazioniGriglia sarà l'interfaccia definita in FXMLimpostazioniGriglia.fxml.
    //Poi prende il nodo principale, rootPane,  e sostituisce tutti i figli con l'oggetto creato
    //precedentemente, ovvero con impostazioniGriglia.
    @FXML
    void clickPlay(ActionEvent event) {
        try {
            BorderPane impostazioniGriglia = FXMLLoader.load(getClass().getResource("FXMLimpostazioniGriglia.fxml"));
            rootPane.getChildren().setAll(impostazioniGriglia);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void clickExit(ActionEvent event){ //creo stageExit
        Stage stageExit = (Stage) buttonExit.getScene().getWindow();
        stageExit.close(); //gli do una funzione (di chiusura)
    }


    //METODO INITIALIZE
    //Se la negazione della variabile definita dentro il Main è uguale a true (quindi se isShowed è true)
    //allora richiama il metodo loadTitle().
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!Main.isShowed) {
            loadTitle();
        }
    }


    //METODO LOAD TITLE
    //Mette la variabile isShowe definita nel main a true.
    //Poi crea un oggetto di tipo AnchorPane chiamato titolo facendo riferimento e richiamando
    //l'intefaccia definita in FXMLtitolo.fxml.
    //Quindi titolo sarà l'interfaccia definita in FXMLtitolo.fxml.
    //Poi prende il nodo principale, rootPane,  e sostituisce tutti i figli con l'oggetto creato
    //precedentemente, ovvero con titolo.
    //Poi crea un oggetto di tipo FadeTransition, chiamato fadeIn, per la transazione sfumata in entrata
    //per il titolo, della durata di 3 secondi.
    //E allo stesso modo crea un oggetto di tipo FadeTransition, chiamato fadeOut, per la transazione
    //sfumata in uscitaper il titolo, della durata di 3 secondi.
    //In seguito avvia la transazione in entrata (con il metodo play) e quando questa è
    //finita inizia la transazione in uscita (con il metodo setOnFinished).
    //Poi, quando anche la transazione in uscita è finita, crea un oggetto di tipo AnchorPane
    //chiamato menu facendo riferimento e richiamando l'intefaccia definita in FXMLmenu.fxml.
    //Quindi menu sarà l'interfaccia definita in FXMLmenu.fxml.
    //Poi prende il nodo principale, rootPane,  e sostituisce tutti i figli con l'oggetto creato
    //precedentemente, ovvero con menu.
    private void loadTitle(){
        try {
            Main.isShowed = true;

            AnchorPane titolo = FXMLLoader.load(getClass().getResource("FXMLtitolo.fxml"));
            rootPane.getChildren().setAll(titolo);

            //TRANSIZIONE SFUMATA IN ENTRATA
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), titolo);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            //TRANSIZIONE SFUMATA IN USCITA
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), titolo);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            //AVVIA TRANSIZIONE IN ENTRATA
            fadeIn.play();

            //Lambda Espressione
            //LA TRANSAZIONE IN USCITA INIZIA QUANDO LA TRANSAZIONE IN ENTRATA E' TERMINATA
            fadeIn.setOnFinished((e) -> fadeOut.play());

            //Lambda Espressione
            //QUANDO LA TRANSAZIONE IN USCITA E' FINITA CARICA L'INTERFACCIA FXMLMENU.fxml
            fadeOut.setOnFinished((e) -> {
                try {
                    AnchorPane menu = FXMLLoader.load(getClass().getResource("FXMLmenu.fxml"));
                    rootPane.getChildren().setAll(menu);
                } catch (IOException ex) {
                    Logger.getLogger(ControllerMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(ControllerMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

