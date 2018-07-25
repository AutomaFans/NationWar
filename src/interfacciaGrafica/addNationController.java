package interfacciaGrafica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addNationController {

    ObservableList<String> ListaColori = FXCollections.observableArrayList("Seleziona Colore", "Verde", "Blu", "Giallo", "Arancio", "Rosso");

    @FXML
    private Button buttonClose;

    @FXML
    private TextField idNomeNazione;

    @FXML
    private ChoiceBox idColoreNazione;

    @FXML
    public void initialize() {
        idColoreNazione.getItems().addAll(ListaColori);
        idColoreNazione.setValue("Seleziona Colore");
    }

    @FXML
    void CloseAddNation(ActionEvent event) {
        Stage stage = (Stage) buttonClose.getScene().getWindow();
        stage.close();
    }

}
