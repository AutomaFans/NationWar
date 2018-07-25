package interfacciaGrafica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;

public class ControllerDeleteNation {

    //Crea una lista di stringhe chiamata ListaNomiNazioni che conterra' tutti i nomi delle nazioni che
    //saranno presenti durante il gioco
    ObservableList<String> ListaNomiNazioni = FXCollections.observableArrayList("Seleziona Nome", "Verde", "Blu", "Giallo", "Arancio", "Rosso");

    @FXML
    private Button buttonElimina;				//Bottone chiamato buttonElimina , per eliminare la nazione selezionata

    @FXML
    private Button buttonClose;					//Bottone chiamato buttonClose, per chiudere la finestra di aiuto

 /*   @FXML
    private MenuButton buttonSelectNation;*/

    @FXML
    private ChoiceBox<String> nomeNazione;		//ChoiceBox (menu a tendina) chiamato nomeNazione che conterrà tutti i nomi
    //delle nazione che potranno essere selezionate per essere eliminate


    //METODO INITIALIZE
    //Aggiunge al choice box chimato nomeNazione tutti gli elementi che sono contenuti dentro la lista
    //ListaNomiNazioni, poi imposta di default la stringa "Seleziona Nome" della lista.
    @FXML
    public void initialize() {
        nomeNazione.getItems().addAll(ListaNomiNazioni);
        nomeNazione.setValue("Seleziona Nome");
    }


    //METODO CLICK CLOSE
    //Quando viene premuto il bottone chiamato buttonClose, viene creato un nuovo stage chaimato stage
    //e (con il metodo getWindow) viene preso il valore della finestra e viene messo dentro stage.
    //Infine viene chiuso lo Stage chiamato stage, che conteneva il valore della finestra
    //(con il metodo close) e cosi si chiude la finestra.
    @FXML
    void clickClose(ActionEvent event) {
        Stage stage = (Stage) buttonClose.getScene().getWindow();
        stage.close();
    }


    //METODO CLICK ELIMINA NAZIONE
    @FXML
    void clickEliminaNazione(ActionEvent event) {
    }

}
