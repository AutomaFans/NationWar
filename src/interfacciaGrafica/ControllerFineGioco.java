package interfacciaGrafica;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerFineGioco {

    //Bottone chiamato buttonClose, per chiudere la finestra di fine gioco
    @FXML
    private Button buttonClose;


    //METODO CLICK CLOSE
    @FXML
    void clickClose(ActionEvent event) {
        ControllerImpostazioniGriglia.useButton = false;
        Stage stage = (Stage) buttonClose.getScene().getWindow();
        stage.close();
    }

}
