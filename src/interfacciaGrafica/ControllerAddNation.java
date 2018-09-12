package interfacciaGrafica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

import static interfacciaGrafica.ControllerImpostazioniGriglia.nationList;			//Prende la lista chiamata nationList creata nella classe ControllerImpostazioniGriglia

public class ControllerAddNation {

    //Crea una lista di stringhe chiamata ListaColori che conterra' tutti i colori delle nazioni che
    //potranno essere scelti quando si crea una nuova nazione
    static ObservableList<String> ListaColori = FXCollections.observableArrayList("Seleziona Colore", "BLUE", "YELLOW", "ORANGE", "RED", "MAROON", "CORAL","FIREBRICK", "INDIANRED", "TOMATO", "DARKRED", "DARKORANGE", "THISTLE", "TURQUOISE", "DARKCYAN", "DEEPSKYBLUE", "CADETBLUE", "OLIVE", "LIGHTSLATEGREY", "DARKSLATEGRAY", "PALEGREEN", "CYAN", "YELLOWGREEN", "PURPLE", "BLUEVIOLET", "INDIGO", "FUCHSIA", "SIENNA", "BROWN", "CHOCOLATE", "PINK", "ORCHID", "LIME", "TEAL", "AQUA", "STEELBLUE", "NAVY", "GRAY", "BLACK");

    //Crea una lista di stringhe chiamata nomiNazioni che conterra' tutti i nomi delle nazioni che
    //veranno create
    static ArrayList<String> nomiNazioni = new ArrayList<>();

    //Bottone chiamato buttonAggiungi, per aggiungere la nazione
    @FXML
    private Button buttonAggiungi;

    //Bottone chiamato buttonClose, per chiudere la finestra aggiungi nazione
    @FXML
    private Button buttonClose;

    //Area di testo chiamata txtNomeNazione per inserire il nome della nazione che si vuole aggiungere
    @FXML
    private TextField txtNomeNazione;

    /*ChoiceBox (menu a tendina) chiamato coloreNazione che conterra' tutti i
	colori che potranno essere scelti quando si crea una nazione*/
    @FXML
    private ChoiceBox<String> coloreNazione;



    //METODO INITIALIZE
    @FXML
    public void initialize() {
        coloreNazione.getItems().addAll(ListaColori);
        coloreNazione.setValue("Seleziona Colore");
    }



    //METODO CLICK CLOSE
    @FXML
    void clickClose(ActionEvent event) {
        Stage stage = (Stage) buttonClose.getScene().getWindow();
        stage.close();
    }



    //METODO CLICK AGGIUNGI NAZIONE
    @FXML
    void clickAggiungiNazione(ActionEvent event) {
        String coloreNaz = coloreNazione.getSelectionModel().getSelectedItem();
        String nomeNaz = txtNomeNazione.getText();
        //CONTROLLA SE TUTTI I VALORI INSERITI QUANDO SI CREA UNA NAZIONE SONO GIUSTI
        //SE I VALORI INSERITI NON SONO CORETTI ESCE IL MESSAGGIO DI ERRORE
        if (nomeNaz.isEmpty() || coloreNaz.contentEquals("Seleziona Colore") || nomeNaz.contentEquals("Inserisci nome della nazione che vuoi aggiungere") ) {
            try {
                AnchorPane errorPane = FXMLLoader.load(getClass().getResource("FXMLerrore.fxml"));
                Stage errorStage = new Stage();
                errorStage.setScene(new Scene(errorPane));
                errorStage.setResizable(false);
                errorStage.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        //CONTROLLA SE IL NOME DELLA NAZIONE CHE SI STA CREANDO NON E' GIA' STATO USATO
        //SE E' GIA STATO USATO ESCE IL MESSAGGIO DI ERRORE
        if (nomiNazioni.contains(nomeNaz)==true){
            try {
                AnchorPane errorPane = FXMLLoader.load(getClass().getResource("FXMLnazExist.fxml"));
                Stage errorStage = new Stage();
                errorStage.setScene(new Scene(errorPane));
                errorStage.setResizable(false);
                errorStage.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        else {
            //ALTRIMENTI, SE I VALORI INSERITI NON SONO CORRETTI NON AGGIUNGE LA NAZIONE A NESSUNA LISTA
            //QUINDI NO FA NIENTE
            if (nomeNaz.isEmpty() || coloreNaz.contentEquals("Seleziona Colore") || nomeNaz.contentEquals("Inserisci nome della nazione che vuoi aggiungere")){
                //Non fa niente
            }
            //INVECE SE I VALORI INSERITI SONO CORRETTI AGGIUNGE LA NAZIONE ALLE LISTE
            else{
                Nation nazione = new Nation(nomeNaz, coloreNaz);
                new ControllerImpostazioniGriglia().nationList.add(0, nazione); /*Viene creata una nuova istanza di ControllerImpostazioniGriglia
                																in maniera da aggiungere alla sua ArrayList statica la nazione*/
                nomiNazioni.add(nomeNaz);
                ListaColori.remove(coloreNaz);
                Stage stage = (Stage) buttonAggiungi.getScene().getWindow();
                stage.close();
            }
        }
        ControllerImpostazioniGriglia.useButton = false;
    }
}
