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
    private Button buttonExit;

    @FXML
    //cliccando sul pulsante Exit si esce dall'applicazione
    void clickExit(ActionEvent event) {
        Stage stage = (Stage) buttonExit.getScene().getWindow();
        stage.close();
    }

    @FXML
    //cliccando sul pulsante Play si inizia una nuova partita
    void clickPlay(ActionEvent event) {

    }

    /*@FXML
    void initialize() {
        assert buttonPlay != null : "fx:id=\"buttonNewGame\" was not injected: check your FXML file 'menu.fxml'.";
        assert buttonExit != null : "fx:id=\"buttonExit\" was not injected: check your FXML file 'menu.fxml'.";

    }*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!Main.isShowed) {
            loadTitle();
        }
    }

    private void loadTitle(){
        try {
            Main.isShowed = true;

            StackPane pane = FXMLLoader.load(getClass().getResource("titolo.fxml"));
            rootPane.getChildren().setAll(pane);

            //transizione sfumata in entrata
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            //transizione sfumata in uscita
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3),pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            //avvio transizione in entrata
            fadeIn.play();

            //lambda espressione per indicare che la transizione in uscita inizia non appena la transizione in entrata termina
            fadeIn.setOnFinished((e) -> fadeOut.play());

            fadeOut.setOnFinished((e) -> {
                try {
                    AnchorPane parentContent = FXMLLoader.load(getClass().getResource("menu.fxml"));
                    rootPane.getChildren().setAll(parentContent);
                } catch (IOException ex) {
                    Logger.getLogger(menuController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(menuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

