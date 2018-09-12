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

    //AnchorPane chiamato rootPane
    @FXML
    private AnchorPane rootPane;

    //Bottone chiamato buttonPlay
    @FXML
    private Button buttonPlay;

    //Bottone chiamato buttonExit
    @FXML
    private Button buttonExit;


    //METODO CLICK PLAY
    @FXML
    void clickPlay(ActionEvent event) {
        try {
            BorderPane impostazioniGriglia = FXMLLoader.load(getClass().getResource("FXMLimpostazioniGriglia.fxml"));
            rootPane.getChildren().setAll(impostazioniGriglia);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //METODO CLICK EXIT
    @FXML
    void clickExit(ActionEvent event){
        Stage stageExit = (Stage) buttonExit.getScene().getWindow();
        stageExit.close();
    }


    //METODO INITIALIZE
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ControllerImpostazioniGriglia.useButton = false;
        if (!Main.isShowed) {
            loadTitle();
        }
    }


    //METODO LOAD TITLE
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

