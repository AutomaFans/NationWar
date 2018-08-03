package interfacciaGrafica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;


import java.util.Iterator;

import static interfacciaGrafica.ControllerAddNation.ListaColori;
import static interfacciaGrafica.ControllerAddNation.nomiNazioni;
import static interfacciaGrafica.ControllerImpostazioniGriglia.nationList;


public class ControllerDeleteNation {


    //Crea una lista di stringhe chiamata ListaNomiNazioni che conterra' tutti i nomi delle nazioni che
    //saranno presenti durante il gioco

    ObservableList<String> ListaNomiNazioni = FXCollections.observableArrayList(nomiNazioni);






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
        for(Iterator<String> i = nomiNazioni.iterator(); i.hasNext();) { //elimino la Nazione dalla lista nomiNazioni
            String num = i.next();
            if(num == nomeNazione.getValue()) {
                i.remove();


            }

            System.out.println("lista delle nazioni " + nomiNazioni);
        }
        for(Iterator<Nation> i = nationList.iterator(); i.hasNext();) { //elimino la Nazione dalla lista nationList
            Nation num = i.next();
            if (num.nome == nomeNazione.getValue()) {
                ListaColori.add(num.color); //rendo il colore della Nazione che ho eliminato di nuovo disponibile
                i.remove();




            }
        }
        ListaNomiNazioni.removeAll(nomeNazione.getValue()); //rimuovo la Nazione anche dalla ListaNomiNazioni ( ovvero da dove ho selezionato la Nazione che volevo eliminare)
        Stage stage = (Stage) buttonElimina.getScene().getWindow();
        stage.close();



    }


}
