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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;

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
    private TextArea txtColumns;				//Area di testo chiamata txtColumns, per impostare il numero di colonne della griglia

    @FXML
    private TextArea txtRows;				//Area di testo chiamata txtRows, per impostare il numero di righe della griglia della griglia

    @FXML
    private Label msgError;      /*identificatore del label nell'angolo in basso a destra che serve per avvisare
                                 l'utente di errori o altri messaggi*/

    @FXML
    private GridPane automaGrid; //identificatore della griglia in cui si visualizzera la simulazione


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


    //METODO CLICK ADD DIMENSIONS
    //Vieni utilizzato quando si preme il bottone "Imposta grandezza griglia": permette quindi di impostare la grandezza della griglia
    @FXML
    void clickAddDimensions(ActionEvent event) {
        int numeroColonne=0;
        int numeroRighe=0;
        int gridColumns;                                        //numero di colonne della griglia desiderato dall'utente
        int gridRows;                                           //numero di righe della griglia desiderato dall'utente
        try{
            gridColumns = Integer.parseInt(txtColumns.getText());   //prende il numero di colonne sottomesso dall'utente
            gridRows = Integer.parseInt(txtRows.getText());         //prende il numero di righe sottomesso dall'utente
        }
        catch(NumberFormatException n){                         //se l'utente non inserisce un intero si ha un eccezione
            this.msgError.setText("Inserire un intero!");
            return;     //esce dal metodo cosi' da non generare errori
        }
        if(gridColumns > 100 || gridRows > 100){                //se il numero di righe o colonne e' maggiore di 100
            //si puo' inserire un numero massimo di 100 righe o colonne
            if(gridColumns > 100 && gridRows > 100){ //se si supera il numero di righe e colonne
                this.msgError.setText("Troppe righe e colonne!");
            }
            else if(gridColumns > 100){ //se si supera il numero di colonne
                this.msgError.setText("Troppe colonne!");
            }
            else { //se si supera il numero di righe
                this.msgError.setText("Troppe righe!");
            }
            return;     //esce dal metodo cosi' da non aggiungere troppe colonne o righe(o entrambi)
        }
        this.msgError.setText("Inserisci nazione"); //se vengono inseriti dati coerenti si puo' proseguire
        this.btnGridDimensions.setDisable(true);   //viene disabilitata ora la possibilita' di ridimensionare la griglia
        this.txtRows.setEditable(false);
        this.txtColumns.setEditable(false);
        this.buttonAddNation.setDisable(false); /*vengono abilitati i pulsanti di addNation e start, ma non ancora
                                                quello di deleteNation perche' non e' stata ancora inserita alcuna nazione*/
        double columnPercentual = 582/gridColumns;   /*percentuale di spazio che deve occupare una colonna nella griglia
                                                     per potersi adattare(582 e' la larghezza fissa della griglia)*/
        ColumnConstraints col = new ColumnConstraints();   //crea una nuova colonna
        col.setPercentWidth(columnPercentual);             //setta la percentuale di larghezza che la colonna deve occupare
        double rowPercentual = 517/gridRows;   /*percentuale di spazio che deve occupare una riga nella griglia per
                                               potersi adattare(517 e' l'altezza fissa della griglia)*/
        RowConstraints row = new RowConstraints(); //crea una nuova riga
        row.setPercentHeight(rowPercentual);       //setta la percentuale di altezza che la riga deve occupare

        for (int y=0; y< gridRows; y++ ){
            for (int x=0; x<gridColumns; x++){
                Button bottone=new Button();  //cosa scrivere all'interno di ogni bottone
                bottone.setMinHeight(rowPercentual);  //do le dimensioni ai bottoni in modo ch ricoprano tutto l'automaGrid
                bottone.setMaxHeight(rowPercentual);
                bottone.setMaxWidth(columnPercentual);
                bottone.setMinWidth(columnPercentual);
                automaGrid.add(bottone,x,y); //aggiunge il bottone alla griglia

            }
        }
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
