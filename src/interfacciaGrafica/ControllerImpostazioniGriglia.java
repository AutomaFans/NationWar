package interfacciaGrafica;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class ControllerImpostazioniGriglia {

    @FXML
    private BorderPane borderPane;			//BorderPane chiamato borderPane

    @FXML
    private Button buttonAddNation;			//Bottone chiamato buttonAddNation, per aggiungere una nazione

    @FXML
    private Button buttonDeleteNation;		//Bottone chiamato buttonDeleteNation, per eliminare una nazione

    @FXML
    private Button buttonStart;				//Bottone chiamato buttonStart, per iniziare a giocare

    @FXML
    private Button buttonHelp;				//Bottone chiamato buttonHelp, per aprire il menu di aiuto

    @FXML
    private Button buttonMenu;				//Bottone chiamato buttonMenu, per tornare al menu principale (FXMLmenu.fxml)

    @FXML
    private Button btnGridDimensions;		//Bottone chiamato btnGridDimensions, per impostare la grandezza della griglia

    @FXML
    private TextArea txtWidth;				//Area di testo chiamata txtWidth, per impostare la larghezza delle celle della griglia

    @FXML
    private TextArea txtHeight;				//Area di testo chiamata txtHeight, per impostare l'altezza delle celle della griglia


    //METODO CLICK ADD NATION
    //Quando il bottone buttonAddNation viene premuto, viene creato un oggetto di tipo AnchorPane chiamato
    //addNationPane facendo riferimento e richiamando l'intefaccia definita in FXMLaddNation.fxml.
    //Quindi addNAtionPane sarà l'interfaccia definita in FXMLaddNation.fxml.
    //Poi viene creato un nuovo Stage, chiamato addNationStage, e specifica la scena da usare
    //su quello stage (con il metodo setScene).
    //QUINDI MOSTRA LA SCENA addNationPane SULLO STAGE addNationStage.
    //Infine mostra l'addNationStage impostando la visibilita' a true (con il metodo show).
    @FXML
    void clickAddNation(ActionEvent event) {
        try {
            AnchorPane addNationPane = FXMLLoader.load(getClass().getResource("FXMLaddNation.fxml"));
            Stage addNationStage = new Stage();
            addNationStage.setScene(new Scene(addNationPane));
            addNationStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //METODO CLICK DELETE NATION
    //Quando il bottone buttonDeleteNation viene premuto, viene creato un oggetto di tipo AnchorPane chiamato
    //deleteNationPane facendo riferimento e richiamando l'intefaccia definita in FXMLdeleteNation.fxml.
    //Quindi deleteNationPane sarà l'interfaccia definita in FXMLdeleteNation.fxml.
    //Poi viene creato un nuovo Stage, chiamato deleteNationStage, e specifica la scena da usare
    //su quello stage (con il metodo setScene).
    //QUINDI MOSTRA LA SCENA deleteNationPane SULLO STAGE deleteNationStage.
    //Infine mostra il deleteNationStage impostando la visibilita' a true (con il metodo show).
    @FXML
    void clickDeleteNation(ActionEvent event) {
        try {
            AnchorPane deleteNationPane = FXMLLoader.load(getClass().getResource("FXMLdeleteNation.fxml"));
            Stage deleteNationStage = new Stage();
            deleteNationStage.setScene(new Scene(deleteNationPane));
            deleteNationStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //METODO HELP
    //Quando il bottone buttonHelp viene premuto, viene creato un oggetto di tipo AnchorPane chiamato
    //helpPane facendo riferimento e richiamando l'intefaccia definita in FXMLhelp.fxml.
    //Quindi helpPane sarà l'interfaccia definita in FXMLhelp.fxml.
    //Poi viene creato un nuovo Stage, chiamato helpStage, e specifica la scena da usare
    //su quello stage (con il metodo setScene).
    //QUINDI MOSTRA LA SCENA helpPane SULLO STAGE helpStage.
    //Infine mostra il helpStage impostando la visibilita' a true (con il metodo show).
    @FXML
    void clickHelp(ActionEvent event) {
        try {
            AnchorPane helpPane = FXMLLoader.load(getClass().getResource("FXMLhelp.fxml"));
            Stage helpStage = new Stage();
            helpStage.setScene(new Scene(helpPane));
            helpStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //METODO CLICK START
    @FXML
    void clickStart(ActionEvent event) {
    }


    //METODO CLICK START
    @FXML
    void clickAddDimensions(ActionEvent event) {
    }


    //METODO MENU
    //Quando il bottone buttonMenu viene premuto, viene creato un oggetto di tipo AnchorPane chiamato
    //menu facendo riferimento e richiamando l'intefaccia definita in FXMLmenu.fxml.
    //Quindi menu sarà l'interfaccia definita in FXMLmenu.fxml.
    //Poi prende il nodo principale, borderPane,  e sostituisce tutti i figli con l'oggetto creato
    //precedentemente, ovvero con menu.
    @FXML
    void clickMenu(ActionEvent event) {
        try {
            AnchorPane menu = FXMLLoader.load(getClass().getResource("FXMLmenu.fxml"));
            borderPane.getChildren().setAll(menu);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
