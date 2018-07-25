package interfacciaGrafica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;

public class deleteNationController {

    ObservableList<String> ListaColori = FXCollections.observableArrayList("Seleziona Colore", "Verde", "Blu", "Giallo", "Arancio", "Rosso");

    @FXML
    private Button buttonElimina;

    @FXML
    private Button buttonAnnulla;

    @FXML
    private MenuButton buttonSelectNation;

    @FXML
    private ChoiceBox<String> nomeNazione;


    @FXML
    public void initialize() {
        nomeNazione.getItems().addAll(ListaColori);
    }

    @FXML
    void clickAnnulla(ActionEvent event) {
        Stage stage = (Stage) buttonAnnulla.getScene().getWindow();
        stage.close();
    }

    @FXML
    void clickEliminaNazione(ActionEvent event) {

    }
}
