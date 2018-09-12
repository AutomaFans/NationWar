package interfacciaGrafica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;

import java.util.Iterator;

import static interfacciaGrafica.ControllerAddNation.ListaColori;					//Prende la lista chiamata ListaColori creata nella classe ControllerAddNation
import static interfacciaGrafica.ControllerAddNation.nomiNazioni;					//Prende la lista chiamata nomiNazioni creata nella classe ControllerAddNation
import static interfacciaGrafica.ControllerImpostazioniGriglia.nationList;			//Prende la lista chiamata nationList creata nella classe ControllerImpostazioniGriglia


public class ControllerDeleteNation {


    //Bottone chiamato buttonElimina , per eliminare la nazione selezionata
    @FXML
    private Button buttonElimina;

    //Bottone chiamato buttonClose, per chiudere la finestra di eliminazione della nazione
    @FXML
    private Button buttonClose;

    /*ChoiceBox (menu a tendina) chiamato nomeNazione che conterra' tutti i nomi
	delle nazioni che potranno essere selezionate per essere eliminate*/
    @FXML
    private ChoiceBox<String> nomeNazione;


    //METODO INITIALIZE
    @FXML
    public void initialize() {
        nomeNazione.getItems().addAll(nomiNazioni);
        nomeNazione.setValue("Seleziona Nome");
    }



    //METODO CLICK CLOSE
    @FXML
    void clickClose(ActionEvent event) {
        Stage stage = (Stage) buttonClose.getScene().getWindow();
        stage.close();
    }



    //METODO CLICK ELIMINA NAZIONE
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
                ListaColori.add(num.getColor());
                num.removeAllRegions();   //Tolgo nazione di appartenenza e colore della nazione alle celle che erano state assegnate alla nazione
                i.remove();
            }
        }
        ControllerImpostazioniGriglia.useButton = false;
        Stage stage = (Stage) buttonElimina.getScene().getWindow();
        stage.close();
    }


}
