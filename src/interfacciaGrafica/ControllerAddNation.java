package interfacciaGrafica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerAddNation {

    //Crea una lista di stringhe chiamata ListaNomiNazioni che conterra' tutti i nomi delle nazioni che
    //saranno presenti durante il gioco
    ObservableList<String> ListaColori = FXCollections.observableArrayList("Seleziona Colore", "Verde", "Blu", "Giallo", "Arancio", "Rosso");

    @FXML
    private Button buttonAggiungi;				//Bottone chiamato buttonAggiungi , per aggiungere la nazione

    @FXML
    private Button buttonClose;					//Bottone chiamato buttonClose, per chiudere la finestra di aiuto

    @FXML
    private TextField txtNomeNazione;			//Area di testo chiamata txtNomeNazione per inserire il nome della nazione che si vuole aggiungere

    @FXML
    private ChoiceBox<String> coloreNazione;	//ChoiceBox (menu a tendina) chiamato coloreNazione che conterrà tutti i
                                                //colori che potranno essere scelti quando si crea una nazione



    //METODO INITIALIZE
    //Aggiunge al choice box chimato nomeNazione tutti gli elementi che sono contenuti dentro la lista
    //ListaNomiNazioni, poi imposta di default la stringa "Seleziona Nome" della lista.
    @FXML
    public void initialize() {
        coloreNazione.getItems().addAll(ListaColori);
        coloreNazione.setValue("Seleziona Colore");
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


    //METODO CLICK AGGIUNGI NAZIONE
    @FXML
    void clickAggiungiNazione(ActionEvent event) {
    }

}
