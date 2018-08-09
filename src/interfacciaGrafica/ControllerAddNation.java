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
    //potranno essere scelti quando si crea una nuovca nazione
    static ObservableList<String> ListaColori = FXCollections.observableArrayList("Seleziona Colore", "GREEN", "BLUE", "YELLOW", "ORANGE", "RED", "MAROON", "CORAL","FIREBRICK", "INDIANRED", "TOMATO", "DARKRED", "DARKORANGE", "THISTLE", "TURQUOISE", "DARKCYAN", "DEEPSKYBLUE", "KHAKI", "CADETBLUE", "OLIVE", "LIGHTSLATEGREY", "DARKSLATEGRAY", "PALEGREEN", "CYAN", "YELLOWGREEN", "PURPLE", "BLUEVIOLET", "INDIGO", "FUCHSIA", "SIENNA", "BROWN", "CHOCOLATE", "PINK", "ORCHID", "LIME", "TEAL", "AQUA", "STEELBLUE", "NAVY", "GRAY", "BLACK");

    //Crea una lista di stringhe chiamata nomiNazioni che conterra' tutti i nomi delle nazioni che
    //veranno create
    static ArrayList<String> nomiNazioni = new ArrayList<>();

    @FXML
    private Button buttonAggiungi;				//Bottone chiamato buttonAggiungi , per aggiungere la nazione

    @FXML
    private Button buttonClose;					//Bottone chiamato buttonClose, per chiudere la finestra di aiuto

    @FXML
    private TextField txtNomeNazione;			//Area di testo chiamata txtNomeNazione per inserire il nome della nazione che si vuole aggiungere

    @FXML
    private ChoiceBox<String> coloreNazione;	//ChoiceBox (menu a tendina) chiamato coloreNazione che conterra'Â  tutti i
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
    //Crea un una stringa coloreNaz che memorizza il colore che viene scelto dall'utente quando
    //crea una nazione, tra tutti i colori del menu a tendina chiamato coloreNazione.
    //Inoltre crea un'altra stringa nomaNaz che memorizza il nome che viene scelto dall'utente
    //quando crea una nazione.
    //Se la stringa nomeNaz e' vuota (quindi se l'utente non ha inserito nessun nome),
    //oppure se la stringa coloreNaz e' uguale a "Seleziona Colore" (quindi se l'utente non ha
    //scelto nessun colore), oppure se la Stringa nomeNaz e' uguale a  "Inserisci nome della
    //nazione che vuoi aggiungere" (quindi se l'utente ha inserito un nome sbagliato)
    //allora viene creato un oggetto di tipo AnchorPane chiamato errorPane facendo riferimento
    //e richiamando l'intefaccia definita in FXMLerrore.fxml.
    //Quindi errorPane sara'  l'interfaccia definita in FXMLerror.fxml.
    //Poi viene creato un nuovo Stage, chiamato errorStage, e specifica la scena da usare
    //su quello stage (con il metodo setScene).
    //QUINDI MOSTRA LA SCENA errorPane SULLO STAGE errorStage.
    //Infine mostra l'errorStage impostando la visibilita' a true (con il metodo show).
    //Altrimenti, se la lista nomiNazioni contiene gia' il nome che l'utente sta
    //inserendo (quindi se l'utente sta provando ad aggiungere una nuova nazione
    //controlla se e' gia presente nella lista nomiNazioni) allora
    //viene creato un oggetto di tipo AnchorPane chiamato errorPane facendo riferimento
    //e richiamando l'intefaccia definita in FXMLnazExist.fxml.
    //Quindi errorPane sara' l'interfaccia definita in FXMLerror.fxml.
    //Poi viene creato un nuovo Stage, chiamato errorStage, e specifica la scena da usare
    //su quello stage (con il metodo setScene).
    //QUINDI MOSTRA LA SCENA errorPane SULLO STAGE errorStage.
    //Infine mostra l'errorStage impostando la visibilita' a true (con il metodo show).
    //Inoltre, se si verifica il caso in cui la stringa nomeNaz e' vuota (quindi se
    //l'utente non ha inserito nessun nome), oppure se la stringa coloreNaz e' uguale
    //a "Seleziona Colore" (quindi se l'utente non ha scelto nessun colore), oppure
    //se la Stringa nomeNaz e' uguale a  "Inserisci nome della nazione che vuoi aggiungere"
    //(quindi se l'utente ha inserito un nome sbagliato)non viene aggiunto a nessuna lista la
    //nazine che si sta tentando di creare.
    //Altrimenti, se non si verifica nessuna delle due condizione precedenti, cosi
    //quando viene premuto il bottone buttonAggiungi, viene creato un nuovo oggetto di tipo Nation
    //con nome txtNomeNazione (nome inserito dall'utente) e colore scelto tra i colori del ChoiceBox.
    //Inoltre aggiunge questa nuova nazione alla listaNazioni (in posizione 0).
    //Poi aggiunge il valore della stringa nomeNaz alla lista nomiNazioni e rimuove dalla lista
    //ListaColori il colore memorizzato nella stringa coloreNaz che contiene il colore che
    //viene scelto dall'utente quando crea una nazione  (in modo che non puo' essere scelto un' altra volta lo stesso colore).
    //Dopo di che' la finestra viene chiusa e si torna sulla griglia.
    @FXML
    void clickAggiungiNazione(ActionEvent event) {
        String coloreNaz = coloreNazione.getSelectionModel().getSelectedItem();
        String nomeNaz = txtNomeNazione.getText();
        //CONTROLLA SE TUTTI I VALORI INSERITI QUANDO SI CREA UNA NAZIONE SONO GIUSTI
        //SE I VALORI INSERITI NON ONO CORETTI ESCE IL MESSAGGIO DI ERRORE
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
        //SE E' GIA STATO USATO CHIAMA ESCE IL MESSAGGIO DI ERRORE
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
            if (nomeNaz.isEmpty() || coloreNaz.contentEquals("Seleziona Colore") || nomeNaz.contentEquals("Inserisci nome della nazione che vuoi aggiungere")){
                //non aggiunge la nazione (non ha un nome valido)
                //INVECE SE I VALORI INSERITI SONO CORRETTI AGGIUNGE LA NAZINE ALLE LISTE
            } else{
                Nation nazione = new Nation(nomeNaz, coloreNaz);
                new ControllerImpostazioniGriglia().nationList.add(0, nazione); //Viene creata una nuova istanza di ControllerImpostazioniGriglia
                //in maniera da aggiungere alla sua ArrayList statica la nazione
                nomiNazioni.add(nomeNaz);
                ListaColori.remove(coloreNaz);
                Stage stage = (Stage) buttonAggiungi.getScene().getWindow();
                stage.close();
            }
        }
    }

}
