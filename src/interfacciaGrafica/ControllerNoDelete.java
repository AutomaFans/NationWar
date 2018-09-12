package interfacciaGrafica;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerNoDelete {

    //Bottone chiamato buttonClose, per chiudere la finestra di NoDelete
    @FXML
    private Button buttonClose;


    //METODO CLICK CLOSE
    @FXML
    void clickClose(ActionEvent event) {
        Stage stage = (Stage) buttonClose.getScene().getWindow();
        stage.close();
    }

}
