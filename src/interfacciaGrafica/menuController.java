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

public class menuController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button buttonPlay;

    @FXML
    //cliccando sul pulsante Play si inizia una nuova partita
    void clickPlay(ActionEvent event) {
        try {
            BorderPane impostazioniGriglia = FXMLLoader.load(getClass().getResource("impostazioniGriglia.fxml"));
            rootPane.getChildren().setAll(impostazioniGriglia);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!Main.isShowed) {
            loadTitle();
        }
    }

    private void loadTitle(){
        try {
            Main.isShowed = true;

            AnchorPane titolo = FXMLLoader.load(getClass().getResource("titolo.fxml"));
            rootPane.getChildren().setAll(titolo);

            //transizione sfumata in entrata
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), titolo);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            //transizione sfumata in uscita
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), titolo);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            //avvio transizione in entrata
            fadeIn.play();

            //lambda espressione per indicare che la transizione in uscita inizia non appena la transizione in entrata termina
            fadeIn.setOnFinished((e) -> fadeOut.play());

            fadeOut.setOnFinished((e) -> {
                try {
                    AnchorPane menu = FXMLLoader.load(getClass().getResource("menu.fxml"));
                    rootPane.getChildren().setAll(menu);
                } catch (IOException ex) {
                    Logger.getLogger(menuController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(menuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

