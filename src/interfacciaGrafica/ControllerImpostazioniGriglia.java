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
            addNationStage.setResizable(false);
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
            deleteNationStage.setResizable(false);
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
            helpStage.setResizable(false);
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
    //Viene utilizzato quando si preme il bottone "Imposta grandezza griglia": permette
    //quindi di impostare la grandezza della griglia.
    //Definisce due variabili intere per il numero di colonne della griglia e per il
    //numero di righe della griglia.
    //Poi (con il metoto getText) prende il numero di colonne e il numero di righe che
    //l'utente ha inserito nelle aree di testo txtColumns e txtRows.
    //Se l'utente non inserisce nelle due aree un  numero intero, viene lanciata l'eccezione
    //per cui la label msgError sara' impostata con la scritta "Inserisci un intero!"
    //Siccome si può inserire un massimo numero di 50 righe e 50 colonne, se il numero
    //di righe e colonne e' maggiore di 50 allora la label msgError sara' impostata con
    //la scritta "Troppe righe e colonne!".
    //Altrimenti, se il numero di colonne e' maggiore di 50 allora la label msgError
    //sara' impostata con la scritta "Troppe colonne!", mentre  se il numero di righe e'
    //maggiore di 50 allora la label msgError sara' impostata con la scritta "Troppe righe!"
    //Se vengono inseriti tutti i dati corretti si puo' proseguire.
    //Quindi la label msgError sara' impostata con la scritta "Inserisci Nazione".
    //Viene disabilitato il bottone btnGridDimensions per togliere la possibilita'
    //di ridimensionare la griglia e per lo stesso motivo sono disabilitate le arre di testo
    //txtRows e txtColumns per togliere la possibilita di inserire un altro numero di righe e di colonne.
    //Invece viene abilitato il bottone buttonAddNation per dare la possibilita di aggiungere una nazione.
    //Poi si passa ad aggiungere i bottoni sulle righe e sulle colonne alla griglia.
    //Viene creata una variabile columnPercentual che è la percentuale di spazio che deve occupare
    //una colonna nella griglia per potersi adattare, cosi viene creata una nuova colonna.
    //e viene settata la percentuale di larghezza che la colonna deve occupare (con il metodo setPercentWidth).
    //Viene creata una variabile rowPercentual che è la percentuale di spazio che deve occupare
    //una riga nella griglia per potersi adattare, cosi viene creata una nuova riga
    //e viene settata la percentuale di larghezza che la riga deve occupare (con il metodo setPercentWidth)
    //Per il numero di righe e di colonne specificate dall'utente, vengono creati i bottoni
    //e viene impostata l'altezza massima e minima del bottone e la grandezza massima e minima
    //del bottone, cosi da riempire tutta la griglia e vengono aggiunti i bottoni alla griglia.
    @FXML
    void clickAddDimensions(ActionEvent event) {
        int gridColumns;                                        	//Numero di colonne della griglia desiderato dall'utente
        int gridRows;                                           	//Numero di righe della griglia desiderato dall'utente
        try{
            gridColumns = Integer.parseInt(txtColumns.getText());  //Prende il numero di colonne inserito dall'utente
            gridRows = Integer.parseInt(txtRows.getText());        //Prende il numero di righe inserito dall'utente
        }
        catch(NumberFormatException n){                         	//Se l'utente non inserisce un intero si ha un eccezione
            this.msgError.setText("Inserire un intero!");			//Label impostata con la scritta "Inserisci un intero"
            return;     											//Esce dal metodo cosi' da non generare errori
        }
        if(gridColumns > 50 || gridRows > 50){                	    //Se il numero di righe o colonne e' maggiore di 50
            if(gridColumns > 50 && gridRows > 50){ 				    //Se il numero di righe e colonne e' maggiore di 50
                this.msgError.setText("Troppe righe e colonne!");	//Label impostata con la scritta "Troppe righe e colonne!"
            }
            else if(gridColumns > 50){ 							    //Altrimenti, se il numero di colonne e' maggiore di 50
                this.msgError.setText("Troppe colonne!");			//Label impostata con la scritta "Troppe colonne!"
            }
            else { 												    //Altrimenti, se il numero di righe e' maggiore di 50
                this.msgError.setText("Troppe righe!");			    //Label impostata con la scritta "Troppe righe!"
            }
            return;    											    //Esce dal metodo cosi' da non aggiungere troppe colonne o righe(o entrambi)
        }
        //Se vengono inseriti dati coerenti si puo' proseguire
        this.msgError.setText("Inserisci nazione"); 				//Label impostta con la scritta "Inserisci Nazione"
        this.btnGridDimensions.setDisable(true);   				    //Viene disabilitato il bottone btnGridDimensions
        this.txtRows.setEditable(false);							//Viene disabilitata l'area di testo per specificare il numero di righe
        this.txtColumns.setEditable(false);						    //Viene disabilitata l'area di testo per specificare il numero di colonne
        this.buttonAddNation.setDisable(false); 					//Viene abilitato il bottone buttonAddNation

        double columnPercentual = 582.0/gridColumns;  				//Percentuale di spazio che deve occupare una colonna nella griglia
                                                                    //per potersi adattare(582.0 e' la larghezza fissa della griglia)
        ColumnConstraints col = new ColumnConstraints();   		    //Crea una nuova colonna
        col.setPercentWidth(columnPercentual);             		    //Setta la percentuale di larghezza che la colonna deve occupare

        double rowPercentual = 517.0/gridRows;  					//Percentuale di spazio che deve occupare una riga nella griglia per
                                                                    //potersi adattare(517.0 e' l'altezza fissa della griglia)
        RowConstraints row = new RowConstraints(); 				    //Crea una nuova riga
        row.setPercentHeight(rowPercentual);       				    //Setta la percentuale di altezza che la riga deve occupare

        for (int i=0; i< gridRows; i++ ){							//Per i che va da 0 fino a  al numero di righe inserito dall'utente
            for (int y=0; y<gridColumns; y++){						//Per y che va da 0 fino a  al numero di colonne inserito dall'utente
                Button bottone=new Button();  						//Crea un bottone (uno per ogni incrocio riga - colonna)
                bottone.setMinHeight(rowPercentual);  				//Imposta l'altezza minima del bottone a rowPercentual
                bottone.setMaxHeight(rowPercentual);				//Imposta l'altezza massima del bottone a rowPercentual
                bottone.setMaxWidth(columnPercentual);				//Imposta la grandezza minima del bottone a columnPercentual
                bottone.setMinWidth(columnPercentual);				//Imposta la grandezza massima del bottone a columnPercentual
                automaGrid.add(bottone,y,i); 						//Aggiunge il bottone alla griglia

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
