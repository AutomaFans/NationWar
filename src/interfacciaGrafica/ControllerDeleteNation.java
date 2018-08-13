package interfacciaGrafica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import java.util.Iterator;

import static interfacciaGrafica.ControllerAddNation.ListaColori;					//Prende la lista chiamata ListaColori creata nella classe ControllerAddNation
import static interfacciaGrafica.ControllerAddNation.nomiNazioni;					//Prende la lista chiamata nomiNazioni creata nella classe ControllerAddNation
import static interfacciaGrafica.ControllerImpostazioniGriglia.nationList;			//Prende la lista chiamata nationList creata nella classe ControllerImpostazioniGriglia


public class ControllerDeleteNation {


    @FXML
    private Button buttonElimina;				//Bottone chiamato buttonElimina , per eliminare la nazione selezionata

    @FXML
    private Button buttonClose;					//Bottone chiamato buttonClose, per chiudere la finestra di eliminazione della nazione

    @FXML
    private ChoiceBox<String> nomeNazione;		//ChoiceBox (menu a tendina) chiamato nomeNazione che conterra'Ã‚Â  tutti i nomi
    //delle nazione che potranno essere selezionate per essere eliminate


    //METODO INITIALIZE
    //La lista nomiNazioni ÃƒÂ¨ creata nella classe ControllerAddNation e contiene tutti i nomi
    //delle nazioni che sono state create.
    //Aggiunge al choice box chimato nomeNazione tutti gli elementi che sono contenuti dentro la lista
    //nomiNazioni, quindi nel menu a tendina ci saranno tutti i nomi delle nazioni che sono state create.
    //Poi imposta di default la stringa "Seleziona Nome" della lista.
    @FXML
    public void initialize() {
        nomeNazione.getItems().addAll(nomiNazioni);
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
    //Dal menu a tendina chiamato nomeNazione l'utente seleziona il nome della nazione che vuole
    //eliminare.
    //Cosi con un for itera sulla lista di stringhe nomiNazioni e mette la stringa iterata dentro
    //la variabile num, di tipo stinga.
    //Se num ÃƒÂ¨ uguale alla nazione che l'utente ha selezionato per eliminare allora rimuove la stringa
    //dalla lista nomi Nazioni.
    //Allo stesso modo, con un for itera sulla lista di Nazioni nationList e mette la Nazione iterata dentro
    //la variabile num, di tipo Nation.
    //Ogni oggetto di tipo Nation e' composto da un nome e da un colore, cosi se num e'¨ uguale
    //al nome della nazione che l'utente ha selezionato per eliminare allora rimuove la nazione
    //dalla lista nationList e aggiunge il colore usato per quella nazione alla lista ListaColori
    //(cosi che questo colore e' di nuovo disponibile) perche' in controllerAddNation quando si creava una
    //nuova nazione veniva eliminato anche il colore dalla lista Lista Colori per non creare nazioni con lo stesso colore.
    //Inoltre toglie il colore della nazione alle celle che erano che erano che erano state assegnate
    //alla nazione che si sta eliminando, richiamando il metodo removeAllRegion della classe Nation.
    //Dopo di che' quando si preme il bottone chiamato buttonElimina la finestra viene chiusa
    //e si torna sulla griglia.
    @FXML
    void clickEliminaNazione(ActionEvent event) {
        //ELIMINA LA NAZIONE DALLA STRINGA NOMI NAZIONI
        for(Iterator<String> i = nomiNazioni.iterator(); i.hasNext();) {
            String num = i.next();
            if(num == nomeNazione.getValue()) {
                i.remove();
            }
        }
        //ELIMINA LA NAZIONE DALLA LISTA NATIONLIST
        for(Iterator<Nation> i = nationList.iterator(); i.hasNext();) {
            Nation num = i.next();
            if (num.getName() == nomeNazione.getValue()) {
                ListaColori.add(num.getName());
                num.removeAllRegions();   //Tolgo nazione di appartenenza e colore della nazione alle celle che erano state assegnate alla nazione
                i.remove();
            }
        }
        Stage stage = (Stage) buttonElimina.getScene().getWindow();
        stage.close();



    }


}
