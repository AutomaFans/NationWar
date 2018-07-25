package interfacciaGrafica;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;

public class deleteNationController {

    @FXML
    private Button buttonElimina;

    @FXML
    private Button buttonAnnulla;

    @FXML
    private MenuButton buttonSelectNation;

    @FXML
    void clickAnnulla(ActionEvent event) {
        Stage stage = (Stage) buttonAnnulla.getScene().getWindow();
        stage.close();
    }

    @FXML
    void clickEliminaNazione(ActionEvent event) {

    }

    @FXML
    void clickSelectNation(ActionEvent event) {

    }

}
