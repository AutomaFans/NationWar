package interfacciaGrafica;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerHelp {

    @FXML
    private Button buttonClose;		//Bottone chiamato buttonClose, per chiudere la finestra di aiuto


    //METODO CLICK CLOSE
    //Quando viene premuto il bottone chiamato buttonClose, viene impostata la variabile useButton
    //(creata dentro ControllerImpGriglia) a false. Poi viene creato un nuovo stage chaimato stage
    //e (con il metodo getWindow) viene preso il valore della finestra e viene messo dentro stage.
    //Infine viene chiuso lo Stage chiamato stage, che conteneva il valore della finestra
    //(con il metodo close) e cosi si chiude la finestra.
    @FXML
    void clickClose(ActionEvent event) {
        ControllerImpostazioniGriglia.useButton = false;
        Stage stage = (Stage) buttonClose.getScene().getWindow();
        stage.close();
    }

}

