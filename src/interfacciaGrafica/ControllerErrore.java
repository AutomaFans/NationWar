package interfacciaGrafica;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerErrore {

    @FXML
    private Button buttonClose;

    @FXML
    //METODO CLICK CLOSE
    //metodo che chiude la finestra aperta nel caso in cui una nazione non viene creata correttamente
    void clickClose(ActionEvent event) {
        Stage stage = (Stage) buttonClose.getScene().getWindow();
        stage.close();
    }

}