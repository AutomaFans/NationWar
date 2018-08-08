package interfacciaGrafica;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import java.util.ArrayList;

import java.io.IOException;

public class ControllerImpostazioniGriglia {

    //Crea una lista di Nazioni che conterra' tutte le nazioni che verranno create
    //L'ArrayList e' statica perche' quando aggiungiamo dal
    //ControllerAddNation una nazione con clickAggiungiNazione vogliamo che
    //quest'ultima sia mantenuta in memoria(nationList rimane lo
    //stesso oggetto per ogni istanza creata da ControllerAddNation per
    //aggiungere una nazione). Se non fosse statica il dato dell' inserimento
    //andrebbe perso.
    static ArrayList<Nation> nationList = new ArrayList<Nation>();

    @FXML
    private TextField txtNomeNazione; 		//Area di testo chiamata txtNomeNAzione per inserire il nome della Nazione

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
    static BarChart<String,Integer> barChart;  //Grafico chiamato barChart, base per le statistiche

    @FXML
    private CategoryAxis nNation;          	//Nazioni coinvolte nelle statistiche (sul grafico)

    @FXML
    private NumberAxis nPeople;            	//Valore (numero di persone) per nazione (sul grafico)

    @FXML
    private Button btnGridDimensions;		//Bottone chiamato btnGridDimensions, per impostare la grandezza della griglia

    @FXML
    private TextArea txtColumns;			//Area di testo chiamata txtColumns, per impostare il numero di colonne della griglia

    @FXML
    private TextArea txtRows;				//Area di testo chiamata txtRows, per impostare il numero di righe della griglia della griglia

    @FXML
    private Label msgError;     			//Label chiamata msgError che serve ad avvisare l'utente di errori o altri messaggi

    @FXML
    private GridPane automaGrid; 			//Griglia chiamata automaGrid che serve per la griglia del gioco

    int contaNumeroCelleUsate;				//Variabile di tipo intera per sapere il numero di celle della griglia che sono state usate


    //METODO CLICK ADD NATION
    //Se il numero  di elementi  della lista chiamata nationList e' minore del numero di elementi
    //della lista chiamata ListaColori -1 allora quando il bottone buttonAddNation viene premuto,
    //viene creato un oggetto di tipo AnchorPane chiamato addNationPane facendo riferimento
    //e richiamando l'intefaccia definita in FXMLaddNation.fxml.
    //Quindi addNationPane sara' l'interfaccia definita in FXMLaddNation.fxml.
    //Poi viene creato un nuovo Stage, chiamato addNationStage, e specifica la scena da usare
    //su quello stage (con il metodo setScene).
    //QUINDI MOSTRA LA SCENA addNationPane SULLO STAGE addNationStage.
    //Infine mostra l'addNationStage impostando la visibilita' a true (con il metodo show).
    //Altrimenti, se il numero  di elementi  della lista chiamata nationList non e' minore del numero di elementi
    //della lista chiamata ListaColori -1 allora significa che non si possono aggiunere piu' nazioni
    //(perche' ogni volta che si crea una nuova nazione, in CoontrollerAddNation, viene tolto
    //dalla ListaColori il colore usato per la nazione creata, in tal modo non possono essere
    //create piu' nazioni con lo stesso colore), allora viene creato un oggetto di tipo AnchorPane
    //chiamato limitPane facendo riferimento e richiamando l'intefaccia definita in FXMlimite.fxml.
    //Quindi limitPane sara' l'interfaccia definita in FXMLlimite.fxml.
    //Poi viene creato un nuovo Stage, chiamato limitStage, e specifica la scena da usare
    //su quello stage (con il metodo setScene).
    //QUINDI MOSTRA LA SCENA limitPane SULLO STAGE limitStage.
    //Infine mostra il limitStage impostando la visibilita' a true (con il metodo show).
    @FXML
    void clickAddNation(ActionEvent event) {
        //SE IL NUMERO DI ELEMENTI DI NATION LIST E' MINORE DEL NUMERO DI ELEMENTI DI LISTA COLORI -1
        //ALLORA SI POSSONO AGGIUNGERE ALTRE NAZONI
        if (nationList.size() < ControllerAddNation.ListaColori.size() - 1) {
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
        //ALTRIMENTI, SE IL NUMERO DI ELEMENTI DI NATION LIST NON E' MINORE DEL NUMERO DI ELEMENTI DI
        //LISTA COLORI -1 ALLORA NON SI POSSONO AGGIUNGERE ALTRE NAZONI PERCHE' SI E' RAGGIUNTO IL
        //LIMITE MASSIMO DI COLORI.
        else {
            try {
                AnchorPane limitPane = FXMLLoader.load(getClass().getResource("FXMLlimite.fxml"));
                Stage limitStage = new Stage();
                limitStage.setScene(new Scene(limitPane));
                limitStage.setResizable(false);
                limitStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //METODO CLICK DELETE NATION
    //Quando il bottone buttonDeleteNation viene premuto, viene creato un oggetto di tipo AnchorPane chiamato
    //deleteNationPane facendo riferimento e richiamando l'intefaccia definita in FXMLdeleteNation.fxml.
    //Quindi deleteNationPane sara'Â  l'interfaccia definita in FXMLdeleteNation.fxml.
    //Poi viene creato un nuovo Stage, chiamato deleteNationStage, e specifica la scena da usare
    //su quello stage (con il metodo setScene).
    //QUINDI MOSTRA LA SCENA deleteNationPane SULLO STAGE deleteNationStage.
    //Infine mostra il deleteNationStage impostando la visibilita' a true (con il metodo show).
    @FXML
    void clickDeleteNation(ActionEvent event) {
        if (nationList.size() == 0) {
            try {
                AnchorPane noDeletePane = FXMLLoader.load(getClass().getResource("FXMLnoDelete.fxml"));
                Stage noDeleteStage = new Stage();
                noDeleteStage.setScene(new Scene(noDeletePane));
                noDeleteStage.setResizable(false);
                noDeleteStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            buttonAddNation.setDisable(false);      //se viene eliminata l'ultima nazione disponibile si riattiva il pulsante addNation
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
    }


    //METODO HELP
    //Quando il bottone buttonHelp viene premuto, viene creato un oggetto di tipo AnchorPane chiamato
    //helpPane facendo riferimento e richiamando l'intefaccia definita in FXMLhelp.fxml.
    //Quindi helpPane sara'Â  l'interfaccia definita in FXMLhelp.fxml.
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
    //Se non ci sono nazioni allora il gioco non può iniziare, percui genera errore, altrimenti inizia il gioco.
    //Quando viene premuto il bottone start, viene disabilitato il bottone buttonAddNation,
    //(cosi da togliera la possibilita' di aggiungere altre nazioni dopo aver premuto Start),
    //viene disabilitato il bottone buttonDeleteNation,(cosi da togliera la possibilita'
    //di cancellare nazioni dopo aver premuto Start) e viene disabilitata anche la grigllia
    //chiamata automaGrid (cosi da togliere la possibilita' di cliccare i bottoni che compongono
    //la griglia)
    @FXML
    void clickStart(ActionEvent event) {
        this.buttonAddNation.setDisable(true); 						//Viene disabilitato il bottone buttonAddNation
        this.buttonDeleteNation.setDisable(true);					//Viene disabilitato il bottone buttonDeleteNation
        this.automaGrid.setDisable(true);							//Viene disabilitato la griglia automaGrid
        if (nationList.size() == 0){
            try {
                AnchorPane noStartPane = FXMLLoader.load(getClass().getResource("FXMLnoStart.fxml"));
                Stage noStartStage = new Stage();
                noStartStage.setScene(new Scene(noStartPane));
                noStartStage.setResizable(false);
                noStartStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            //altrimenti inizia il gioco
        }
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
    //Siccome si puo' inserire un massimo numero di 50 righe e 50 colonne, se il numero
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
    //Ogni bottone rappresenta nella griglia una regione e pertanto gli verra' assegnato un certo valore in denaro che la
    //nazione dovra' spendere se vorra' acquistarlo: tutto cio' usando il metodo setValore(numero righe, numero colonne) di
    // Regione.
    //Viene creata una variabile columnPercentual che e' la percentuale di spazio che deve occupare
    //una colonna nella griglia per potersi adattare, cosi viene creata una nuova colonna.
    //e viene settata la percentuale di larghezza che la colonna deve occupare (con il metodo setPercentWidth).
    //Viene creata una variabile rowPercentual che e' la percentuale di spazio che deve occupare
    //una riga nella griglia per potersi adattare, cosi viene creata una nuova riga
    //e viene settata la percentuale di larghezza che la riga deve occupare (con il metodo setPercentWidth)
    //Per il numero di righe e di colonne specificate dall'utente, vengono creati i bottoni
    //e viene impostata l'altezza massima e minima del bottone, larghezza massima e minima
    //del bottone, identificatore e event handler cosi da riempire tutta la griglia e vengono aggiunti i bottoni alla griglia.
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

        //Crea regioni randomiche nella griglia in base al numero di righe e di colonne desiderate dall'utente
        //Queste regioni sono istanze della classe Regione che estende la classe Button
        for (int i=0; i< gridRows; i++ ){							//Per i che va da 0 fino a  al numero di righe inserito dall'utente
            for (int y=0; y<gridColumns; y++){						//Per y che va da 0 fino a  al numero di colonne inserito dall'utente
                Regione bottone = new Regione();  					//Crea un bottone (uno per ogni incrocio riga - colonna)
                bottone.setValore(gridRows, gridColumns);           //Setta il valore in denaro della regione(cella)

                bottone.setMinHeight(rowPercentual);  				//Imposta l'altezza minima del bottone a rowPercentual
                bottone.setMaxHeight(rowPercentual);				//Imposta l'altezza massima del bottone a rowPercentual
                bottone.setMaxWidth(columnPercentual);				//Imposta la grandezza minima del bottone a columnPercentual
                bottone.setMinWidth(columnPercentual);				//Imposta la grandezza massima del bottone a columnPercentual
                bottone.setId("btn" + i + y);                       //Aggiunge un identificatore(ID) al bottone
                bottone.setOnAction(this::clickAndColorCell);       //Aggiunge un event handler al bottone che e' quello per colorare
                //la cella in base al'ultima nazione inserita
                automaGrid.add(bottone,y,i); 						//Aggiunge il bottone alla griglia

            }
        }
    }


    //METODO MENU
    //Quando il bottone buttonMenu viene premuto, si svuota la lista delle nazioni in maniera da non
    // tenere memorizzate le vecchie nazioni create, poi viene creato un oggetto di tipo AnchorPane chiamato
    //menu facendo riferimento e richiamando l'intefaccia definita in FXMLmenu.fxml.
    //Quindi menu sara'Â  l'interfaccia definita in FXMLmenu.fxml.
    //Poi prende il nodo principale, borderPane,  e sostituisce tutti i figli con l'oggetto creato
    //precedentemente, ovvero con menu.
    @FXML
    void clickMenu(ActionEvent event) {
        try {
            nationList.clear();              //se si ritorna al menu principale allora vengono eliminate tutte le nazioni dalla lista
            AnchorPane menu = FXMLLoader.load(getClass().getResource("FXMLmenu.fxml"));
            borderPane.getChildren().setAll(menu);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //METODO CLICK AND COLOR CELL
    //Quando si clicca su una cella mentre si stanno aggiungendo nazioni alla griglia, questo metodo assegna
    //alla cella cliccata il colore dell'ultima nazione inserita.
    //Se la lista chiamata nationList è vuota, quindi se non e' stat creata nessuna nazione,
    //allora viene soltanto abilitato il bottone chiamato buttonAddNation e non succede nient'altro,
    //perchè se non abbiamo creato nessuna nazione e clicchiamo su una cella della griglia il programma
    //non deve fare nulla.
    //Altrimenti, se abbiamo creato una nazione e clicchiamo su una cella della griglia allora la
    //cella deve colorarsi del colore scelto.
    //Applicando getSource() all'evento individuato (click sulla cella della griglia) si ottiene il bottone
    //su cui si e' verificato l'evento, ma viene restituito come un tipo Object e quindi applichiamo un cast
    //esplicito ((Button)event.getSource())).
    //Al bottone viene applicato setStyle che serve per applicare una propieta' css all'oggetto in questione, in questo caso viene
    //applicato un background color e cioe' il colore di sfondo che sara' quello dell'ultima nazione inserita nel sistema.
    //Inoltre, siccome siamo nel caso in cui e' stata creata almeno una nazione, vine abilitato il bottone buttonDeleteNation
    //e viene abilitato anche il bottone buttonStart.
    //Per la cella su cui si clicca che rappresenta una Regione viene anche associata la Nazione per la quale si sta
    //assegnando territorio sulla griglia. Inoltre assegnando una certa regione ad una nazione la nazione in base alle
    //caratteristiche dela regione prende un certo numero di risorse e denaro. Aumenta inoltre la sua popolazione.
    //Inoltre, ogni volta che si clicca e quindi si colora una cella viene incrementata la variabile che tiene conto
    //del numero di celle utilizzate e se questo numero di celle utilizzate e' maggiore o uguale al prodotto
    //numero di righe per numero di colonne (indseriti dall'utente nelle apposite aree di testo)
    //allora significa che la griglia è piena e che sono state usate tgutte le celle per cui viene disabilitato il
    //bottone chiamato buttonAddNation, cosi che non e' piu' possibile inserire un'altra nazione.
    @FXML
    void clickAndColorCell(ActionEvent event) {
        //SE NON ABBIAMO CREATO NESSUNA NAZIONE E CLICCHIAMO SU UNA CELLA DELLA GRIGLLIA
        //IL PROGRAMMA NON DEVE FARE NULLA
        if (nationList.isEmpty() == true) {
            buttonAddNation.setDisable(false);
            //ALTRIMENTI, SE ABBIAMO CREATO UNA NAZIONE E CLICCHIAMO SU UNA CELLA DELLA GRIGLIA
            //LA CELLA DEVE ESSERE COLORATA DEL COLORE SCELTO
        } else {
            ((Regione) event.getSource()).setStyle("-fx-background-color: " + nationList.get(0).getColor());
            //SE LE CELLE SONO STATE TUTTE UTILIZZATE BISOGNA DISABILITARE IL BOTTONE  BUTTON ADD NATION
            int gridColumns = 0;                                            //Numero di colonne della griglia desiderato dall'utente
            int gridRows = 0;												//Numero di righe della griglia desiderato dall'utente
            this.buttonDeleteNation.setDisable(false);						//Viene abilitato il bottone buttonDeleteNation
            this.buttonStart.setDisable(false);								//Viene abilitato il bottone buttonStart

            ((Regione) event.getSource()).setNazione(nationList.get(0).getName()); //setta la nazione di appartenenza
            nationList.get(0).takeProfit(((Regione) event.getSource()).getTipo(), ((Regione) event.getSource()).getRisorse());
                                                                                   //aumenta il numero di abitanti, le risorse
                                                                                   //e il denaro della nazione in base alle
                                                                                   //caratteristiche del territorio assegnato

            try {
                gridColumns = Integer.parseInt(txtColumns.getText());  		//Prende il numero di colonne inserito dall'utente nell'area di testo chiamata txtColumns
                gridRows = Integer.parseInt(txtRows.getText());        		//Prende il numero di righe inserito dall'utente nell'area di testo chiamata txtRows
            } catch (NumberFormatException n) {                       		//Se l'utente non inserisce un intero si ha un eccezione
                //Esce dal metodo cosi' da non generare errori
            }
            contaNumeroCelleUsate++;										//Incrementa il numero di celle utilizzate
            if (contaNumeroCelleUsate >= (gridColumns * gridRows)) {		//Se sono state usate tutte le celle
                this.buttonAddNation.setDisable(true);						//Viene disabilitato il botttone buttonAddNation
            }

        }
    }

}
