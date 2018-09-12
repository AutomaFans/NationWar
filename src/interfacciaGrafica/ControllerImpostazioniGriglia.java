package interfacciaGrafica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Iterator;
import java.util.ResourceBundle;
import static interfacciaGrafica.ControllerAddNation.ListaColori;
import static interfacciaGrafica.ControllerAddNation.nomiNazioni;
import static interfacciaGrafica.Regione.createPop;

import javafx.scene.Node;         //libreria per utilizzare l'interfaccia Node

public class ControllerImpostazioniGriglia{

    //Crea una lista di Nazioni che conterra' tutte le nazioni che verranno create
    //L'ArrayList e' statica perche' quando aggiungiamo dal
    //ControllerAddNation una nazione con clickAggiungiNazione vogliamo che
    //quest'ultima sia mantenuta in memoria(nationList rimane lo
    //stesso oggetto per ogni istanza creata da ControllerAddNation per
    //aggiungere una nazione). Se non fosse statica il dato dell' inserimento
    //andrebbe perso.
    static ArrayList<Nation> nationList = new ArrayList<Nation>();


    //Area di testo chiamata txtNomeNAzione per inserire il nome della Nazione
    @FXML
    private TextField txtNomeNazione;

    //BorderPane chiamato borderPane
    @FXML
    private BorderPane borderPane;

    //Bottone chiamato buttonAddNation, per aggiungere una nazione
    @FXML
    private Button buttonAddNation;

    //Bottone chiamato buttonDeleteNation, per eliminare una nazione
    @FXML
    private Button buttonDeleteNation;

    //Bottone chiamato buttonStart, per iniziare a giocare
    @FXML
    private Button buttonStart;

    //Bottone chiamato buttonHelp, per aprire il menu di aiuto
    @FXML
    private Button buttonHelp;

    //Bottone chiamato buttonMenu, per tornare al menu principale (FXMLmenu.fxml)
    @FXML
    private Button buttonMenu;

    //Grafico chiamato barChart, base per le statistiche degli abitanti
    @FXML
    private StackedBarChart barChart;

    //Grafico chiamato barChartR, base per le statistiche delle risorse
    @FXML
    private StackedBarChart barChartR;

    //Grafico chiamato barChartD, base per le statistiche del denaro
    @FXML
    private StackedBarChart barCharD;

    //Nazioni coinvolte nelle statistiche (sul grafico)
    @FXML
    private CategoryAxis nNation;

    //Valore (numero di persone) per nazione (sul grafico)
    @FXML
    private NumberAxis nPeople;

    //Bottone chiamato btnGridDimensions, per impostare la grandezza della griglia
    @FXML
    private Button btnGridDimensions;

    //Area di testo chiamata txtColumns, per impostare il numero di colonne della griglia
    @FXML
    private TextArea txtColumns;

    //Area di testo chiamata txtRows, per impostare il numero di righe della griglia della griglia
    @FXML
    private TextArea txtRows;

    //Label chiamata msgError che serve ad avvisare l'utente di errori o altri messaggi
    @FXML
    private Label msgError;

    //Griglia chiamata automaGrid che serve per la griglia del gioco
    @FXML
    private GridPane automaGrid;

    //Tab per le statistiche sulle risorse di ogni nazione
    @FXML
    private Tab tabRisorse;

    //Tab per le statistiche sul denaro di ogni nazione
    @FXML
    private Tab tabDenaro;

    //Tab per le statistiche sulla popolazione di ogni nazione
    @FXML
    private Tab tabPopolazione;

    //Tab per le informazioni su ogni nazione (nome, eta, numero di terreni
    //fertili e numero di tereni sterili)
    @FXML
    private Tab tabInfoNazioni;

    //Tabella chiamata InfoTable per contenetere le informzazioni delle nazioni
    @FXML
    private TableView<Nation> InfoTable;

    //Colonna della tabella InfoTable, chiamata ColonnaNazioni per i nomi di tutte le nazioni
    @FXML
    TableColumn <Nation, String> ColonnaNazioni;

    //Colonna della tabella InfoTable, chiamata ColonnaEta per l'eta' della nazione su quella riga
    @FXML
    TableColumn <Nation, Eta> ColonnaEta;

    //Colonna della tabella InfoTable, chiamata ColonnaFertili per il numero di territori fertili della nazione su quella riga
    @FXML
    TableColumn <Nation, Integer> ColonnaFertili;

    //Colonna della tabella InfoTable, chiamata ColonnaFertili per il numero di territori sterili della nazione su quella riga
    @FXML
    TableColumn <Nation, Integer> ColonnaSterili;

    /*TextArea dove inserire durante una pausa il numero di turni per i quali si
	desidera far continuare la simulazione*/
    @FXML
    private TextArea txtTurniDaSvolgere;

    //Variabile di tipo intera per sapere il numero di celle della griglia che sono state usate
    int contaNumeroCelleUsate;

    //Variabile che tiene conto del valore delle risorse
    double valAttualeRisorse=0;

    //Variabile che tiene conto del numero di abitanti
    int valAttualeAbitanti=0;

    //Variabile che tiene conto della quantita' di denaro
    double valAttualeDenaro=0;

    //Variabile per vedere se start e' premuto (true) o meno (false)
    boolean useStart= false;

    //Variabile booleana usata per capire se e' stato cliccato uno dei bottoni
    //aggiungiNazione, eliminaNazione, Help o menuPrincipale
    static boolean useButton = false;

    /*La variabile turni tiene conto del numero di turni trascorsi dall'inizio del gioco.
	Un turno e' completo quando tutte le nazioni in gioco "hanno fatto la propria mossa"*/
    int turni = 0;

    //Crea una lista di stringhe chiamata arrayForStart che serve per capire se Start e'
    //stato premuto o no nel metodo ClickMenu
    static ArrayList<String> arrayForStart = new ArrayList<>();

    //Creo una lista di stringhe chiamata NomiNazioniCopia che serve per verificare
    //se la regione per cui sto costruendo il grafico e' gia' presente nel grafico
    //o se e' appena stata creata
    ArrayList<String> NomiNazioniCopia = new ArrayList<>();



    //METODO GET NUMERO RIGHE
    public int getNumeroRighe(){
        return Integer.parseInt(txtRows.getText());
    }



    //METODO GET NUMERO COLONNE
    public int getNumeroColonne(){
        return Integer.parseInt(txtColumns.getText());
    }



    //METODO GET GRIDPANE
    public GridPane getGridPane(){
        return this.automaGrid;
    }



    //METODO GET NODE FROM GRID PANE
    public Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        //OGNI OGGETTO DELLA GRIGLIA E' UN ITERABILE QUINDI VIENE ITERATO IN QUESTO FOR EACH
        for (Node node : gridPane.getChildren()) {
            //SE L'OGGETTO ITERATO CORRISPONDE AL NUMERO DI RIGA E COLONNA PASSATI COME PARAMETRI
            //ALLORA SIGNIFICA CHE SI TRATTA DELL'OGGETTO CHE STIAMO CERCANDO E PERCIO' VIENE
            //RESTITUITO
            if (GridPane.getColumnIndex(node) == (Integer)col && GridPane.getRowIndex(node) == (Integer)row) {
                return node;
            }
        }
        //SE NON VIENE TROVATO NULLA RITORNA NULL
        return null;
    }



    //METODO CLICK ADD NATION
    @FXML
    void clickAddNation(ActionEvent event) {
        useButton = true;
        //SE IL NUMERO DI ELEMENTI DI NATION LIST E' MINORE DI 38 ALLORA SI POSSONO
        //AGGIUNGERE ALTRE NAZONI
        if (nationList.size() < 38) {
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
        //ALTRIMENTI, SE IL NUMERO DI ELEMENTI DI NATION LIST NON E' MINORE DI 38 ALLORA
        //NON SI POSSONO AGGIUNGERE ALTRE NAZONI PERCHE' SONO STATI USATI TUTTI I COLORI
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
    @FXML
    void clickDeleteNation(ActionEvent event) {
        useButton = true;
        //SE NATION LIST E' VUOTA NON E' POSSIBILE CANCELLARE LE NAZIONI
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
            //SE INVECE C'E' ALMENO UNA NAZIONE SI RIABILITA IL BOTTONE ADDNATION ED E' ANCHE
            //POSSIBILE CANCELLARE LA NAZIONE
        } else {
            buttonAddNation.setDisable(false);
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
    @FXML
    void clickHelp(ActionEvent event) {
        useButton = true;
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
    synchronized void clickStart(ActionEvent event) {
        ObservableList<Nation> informazioni = FXCollections.observableArrayList();
        //SE IL GIOCO NON E' INIZIATO
        if(useStart == false){
            //ELIMINA LE NAZIONI CHE SONO STATE CREATE MA A CUI NON E' STATA ASSEGNATA NESSUNA REGIONE
            for (int indice=0; indice<nationList.size();indice++){
                if (nationList.get(indice).getRegioni().size()==0){
                    ListaColori.add(nationList.get(indice).getColor()); //Viene riaggiunto il colore della nazione cancellata in ListaColori
                    nationList.remove(indice);
                }
            }
        }
        useButton = true;
        int nazioniMorte = 0;                   //Variabile che tiene conto del numero di nazioni morte durante la simulazione
        try{
            //SE IL NUMERO DI TURNI INSERITO E' UGUALE DI 0
            if(Integer.parseInt(this.txtTurniDaSvolgere.getText()) == 0){

            }
            //ALTRIMENTI,SE IL NUMERO DI TURNI INSERITO E' MAGGIORE DI 0
            else{
                this.buttonAddNation.setDisable(true);
                this.buttonDeleteNation.setDisable(true);
                //I bottoni non saranno cliccabili durante lo svolgimento di un turno. Inoltre durante lo svolgimento
                //di un turno non sara' possibile editare l'area di testo in cui inserire i turni per i quali la simulazione deve continuare.
                this.buttonStart.setDisable(true);
                this.buttonHelp.setDisable(true);
                this.buttonMenu.setDisable(true);
                this.tabPopolazione.setDisable(true);                   //Non e' possibile consultare le statistiche mentre il gioco avanza
                this.tabDenaro.setDisable(true);
                this.tabRisorse.setDisable(true);
                this.tabInfoNazioni.setDisable(true);
                this.txtTurniDaSvolgere.setDisable(true);
                this.buttonStart.setText("Continua");                   //Il bottone di start cambia ora utilizzo: servira' ora per continuare tra una pausa e l'altra
                //SE USESTART E' A TRUE
                if(useStart == true){
                    this.nationList = cloneNationThreadList();     	    //Vengono clonate le nazioni per poter startare di nuovo i thread
                }
                useStart=true;
                int turniSvolti = Integer.parseInt(txtTurniDaSvolgere.getText()); //Numero di turni da svolgere prima della pausa
                //Punto centrale della simulazione in cui viene fatto svolgere un turno per ogni nazione finche' e' possibile
                //I turni vengono fatti svolgere in maniera progressiva dall'ultima alla prima nazione creata
                for(int i=0; i < nationList.size(); i++){  				//Viene iterata nazione per nazione della lista nationList
                    if(i == 0 || nationList.size() == 0){
                        System.out.println("===== turno " + this.turni + " =====");
                    }
                    //SE LA NAZIONE ITERATA E' VIVA
                    if(nationList.get(i).getStato() == true) {
                        nationList.get(i).start();                      //Viene svolto il turno della nazione considerata
                        do {
                            wait();                                     //Il therad main si mette in attesa che la nazione finisca di svolgere il suo turno
                        }
                        while (nationList.get(i).getThreadState() == true);   //E resta in attesa finche' il metodo getThreadState restituisce true
                        //SE E' STATO SVOLTO IL TURNO DELL'ULTIMA NAZIONE IN LISTA BISOGNA VEDERE SE RIINIZIARE DALLA PRIMA
                        if(i == nationList.size()-1){
                            this.turni ++;                                    //Si tiene conto che si e' arrivati alla fine del turno per tutte le nazioni
                            turniSvolti --;                                   //Un turno e' stato svolto e quindi aggiorno il numero di turni da svolgere rimanenti
                            //SE SIAMO AL PRIMO TURNO O SE SONO STATI SVOLTI TUTTI I TURNI INDICATI
                            if(this.turni == 1 || turniSvolti == 0){
                                this.buttonHelp.setDisable(false);
                                this.buttonMenu.setDisable(false);
                                this.tabPopolazione.setDisable(false);
                                this.tabDenaro.setDisable(false);
                                this.tabRisorse.setDisable(false);
                                this.tabInfoNazioni.setDisable(false);
                                this.buttonStart.setDisable(false);
                                this.txtTurniDaSvolgere.setDisable(false);
                                this.msgError.setText("Inserire n. turni > 0"+" "+"("+turni+")");
                                useButton= false;
                                //VENGONO ELIMINATE TUTTE LE STATISTICCHE FATTE FINO AD ORA
                                barChart.getData().clear();
                                barCharD.getData().clear();
                                barChartR.getData().clear();
                                //VIENE PULITA LA LISTA DELLE INFORMAZIONI SULLE NAZIONI
                                informazioni.clear();
                                //PER OGNI NAZIONE SI TIENE CONTO DEL NUMERO DI TERRENI FERTILI E DEL NUMERO DI TERRENI
                                //STERILI, CHE QUELLA NAZIONE HA
                                for (int k=0; k< nationList.size(); k++) {
                                    int numSterile=0;
                                    int numFertile=0;
                                    for (int ind=0; ind< nationList.get(k).getRegioni().size();ind++){
                                        //SE LA REGIONE E' STERILE INCREMENTA LA VARIABILE NUMSTERILE
                                        if (nationList.get(k).getRegioni().get(ind).getTipo()=="sterile") {
                                            numSterile++;
                                            //ALTRIMENTI, SE LA REGIONE E' FERTILE INCREMENTA LA VARIABILE NUMFERTILE
                                        }else{
                                            numFertile++;
                                        }
                                    }
                                    ColonnaNazioni.setCellValueFactory(new PropertyValueFactory<Nation,String>("nome")); 			//Assegno alla colonna ColonnaNazione il dato nome (nome della nazione)
                                    ColonnaEta.setCellValueFactory(new PropertyValueFactory<Nation,Eta>("eta")); 					//Assegno alla colonnaEta il dato dell'eta
                                    ColonnaFertili.setCellValueFactory(new PropertyValueFactory<Nation,Integer>("numFertili")); 	//Assegno alla colonnaFertili il numFertili (numero terreni fertili)
                                    ColonnaSterili.setCellValueFactory(new PropertyValueFactory<Nation,Integer>("numSterili")); 	//Assegno alla colonnaSterili il numSterili (numero terreni sterili)
                                    InfoTable.setItems(informazioni); 																//Aggiungo il tutto nella tabella principale
                                    //SE LA NAZIONE ITERATA E' VIVA
                                    if (nationList.get(k).getStato() == true) {
                                        informazioni.add(new Nation(nationList.get(k).getName(),nationList.get(k).getAge(),numSterile,numFertile)); //Aggiungo i dati di ogni nazione sulla tabella delle info Nazioni
                                        XYChart.Series set1 = new XYChart.Series<>(); 			//Si crea il grafico degli Abitanti chiamato set (e' una base vuota su cui poi vva scostruito il grafico)
                                        XYChart.Series risorse1 = new XYChart.Series<>();		//Si crea il grafico delle risorse chiamato risorse(e' una base vuota su cui poi vva scostruito il grafico)
                                        XYChart.Series denaro1 = new XYChart.Series<>(); 		//Si crea il grafico del denaro chiamato denaro (e' una base vuota su cui poi vva scostruito il grafico)
                                        //Viene creato un mattone per il grafico che ha sotto il sotto il nome della nazione ed e' alto quanti sono gli abitanti di quella nazione
                                        set1.getData().add(new XYChart.Data<String, Number>(nationList.get(k).getName(), (nationList.get(k).getNumAbitanti())));
                                        //Viene creato un mattone per il grafico che ha sotto il sotto il nome della nazione ed e' alto quanti sono le risorse di quella nazione
                                        risorse1.getData().add(new XYChart.Data<String, Number>(nationList.get(k).getName(), (int)nationList.get(k).getRisorse()));
                                        //Viene creato un mattone per il grafico che ha sotto il sotto il nome della nazione ed e' alto quanto e' il denaro di quella nazione
                                        denaro1.getData().add(new XYChart.Data<String, Number>(nationList.get(k).getName(), (int)nationList.get(k).getDenaro()));
                                        //Aggiungo i mattoni nei vari barChart
                                        barCharD.getData().addAll(denaro1); 		//Aggiungo il mattone del denaro alla rispettiva barChart del denaro, chiamato barChartD.
                                        barChart.getData().addAll(set1);			//Aggiungo il mattone degli abitanti alla rispettiva barChart degli abitanti, chiamato barChart.
                                        barChartR.getData().addAll(risorse1); 		//Aggiungo il mattone dellerisosrse alla rispettiva barChart delle risorse, chiamato barChartR.

                                    }
                                    //ALTRIMENTI, SE LA NAZIONE ITERATA E' MORTA
                                    else {
                                        informazioni.add(new Nation(nationList.get(k).getName()+"(MORTA)",nationList.get(k).getAge(),numSterile,numFertile)); //aggiungo i dati di ogni nazione sulla tabella delle info Nazioni
                                        continue;
                                    }
                                }
                            }
                            //ALTRIMENTI, SE NON SIAMO AL PRIMO TURNO O SE NON SONO STATI SVOLTI TUTTI I TURNI INDICATI
                            else{
                                //SE NON CI SONO PIU' NAZIONI VIVE DENTRO LA LISTA NATION LIST E' INUTILE CONTINUARE AD
                                //ITERARE NAZIONI
                                boolean vive = false;                         //Booleano per verificare se ci sono ancora nazione vive
                                for(int j=0;j<nationList.size();j++){         //Controlla per ogni nazione se e' viva, e se lo e' tiene
                                    //conto che c'e' almento una nazione viva
                                    if(nationList.get(j).getStato()==true){
                                        vive = true;
                                    }
                                }
                                if(vive = false) {                            //Se non c'e' nessuna nazione viva interrompe il for che fa
                                    //eseguire i turni
                                    this.turni ++;
                                    break;
                                }
                                else{
                                    this.nationList = cloneNationThreadList();     	  //Vengono clonate le nazioni
                                    i=-1;
                                }                                    	  //Infine porto l'indice del for a -1 per riniziare ad iterare da capo
                            }
                        }
                    }
                    //ALTRIMENTI, SE LA NAZIONE E' MORTA
                    else {
                        //SE NON CI SONO PIU' NAZIONI VIVE DENTRO LA LISTA NATION LIST E' INUTILE CONTINUARE AD
                        //ITERARE NAZIONI
                        boolean vive2 = false;                         //Booleano per verificare se ci sono ancora nazione vive
                        for(int j=0;j<nationList.size();j++){         //Controlla per ogni nazione se e' viva, e se lo e' tiene
                            //conto che c'e' almento una nazione viva
                            if(nationList.get(j).getStato()==true){
                                vive2 = true;
                            }
                        }
                        if(vive2 = false) {                            //Se non c'e' nessuna nazione viva interrompe il for che fa
                            //eseguire i turni
                            this.turni ++;
                            break;
                        }
                        //ALTRIMENTI, SE CI SONO ANCORA NAZIONI VIVE DENTRO LA LISTA NATION LIST SI CONTINUA ITERANDO LA
                        //PROSSIMA NAZIONE
                        else {
                            if(i == nationList.size()-1){
                                this.turni ++;                                    //Si tiene conto che si e' arrivati alla fine del turno per tutte le nazioni
                                turniSvolti --;                                   //Un turno e' stato svolto e quindi aggiorno il numero di turni da svolgere rimanenti
                                //SE SIAMO AL PRIMO TURNO O SE SONO STATI SVOLTI TUTTI I TURNI INDICATI
                                if(this.turni == 1 || turniSvolti == 0){
                                    this.buttonHelp.setDisable(false);
                                    this.buttonMenu.setDisable(false);
                                    this.tabPopolazione.setDisable(false);
                                    this.tabDenaro.setDisable(false);
                                    this.tabRisorse.setDisable(false);
                                    this.tabInfoNazioni.setDisable(false);
                                    this.buttonStart.setDisable(false);
                                    this.txtTurniDaSvolgere.setDisable(false);
                                    this.msgError.setText("Inserire n. turni > 0"+" "+"("+turni+")");
                                    useButton= false;
                                    //VENGONO ELIMINATE TUTTE LE STATISTICCHE FATTE FINO AD ORA
                                    barChart.getData().clear();
                                    barCharD.getData().clear();
                                    barChartR.getData().clear();
                                    //VIENE PULITA LA LISTA DELLE INFORMAZIONI SULLE NAZIONI
                                    informazioni.clear();
                                    //PER OGNI NAZIONE SI TIENE CONTO DEL NUMERO DI TERRENI FERTILI E DEL NUMERO DI TERRENI
                                    //STERILI, CHE QUELLA NAZIONE HA
                                    for (int k=0; k< nationList.size(); k++) {
                                        int numSterile=0;
                                        int numFertile=0;
                                        for (int ind=0; ind< nationList.get(k).getRegioni().size();ind++){
                                            //SE LA REGIONE E' STERILE INCREMENTA LA VARIABILE NUMSTERILE
                                            if (nationList.get(k).getRegioni().get(ind).getTipo()=="sterile") {
                                                numSterile++;
                                                //ALTRIMENTI, SE LA REGIONE E' FERTILE INCREMENTA LA VARIABILE NUMFERTILE
                                            }else{
                                                numFertile++;
                                            }
                                        }
                                        ColonnaNazioni.setCellValueFactory(new PropertyValueFactory<Nation,String>("nome")); 			//Assegno alla colonna ColonnaNazione il dato nome (nome della nazione)
                                        ColonnaEta.setCellValueFactory(new PropertyValueFactory<Nation,Eta>("eta")); 					//Assegno alla colonnaEta il dato dell'eta
                                        ColonnaFertili.setCellValueFactory(new PropertyValueFactory<Nation,Integer>("numFertili")); 	//Assegno alla colonnaFertili il numFertili (numero terreni fertili)
                                        ColonnaSterili.setCellValueFactory(new PropertyValueFactory<Nation,Integer>("numSterili")); 	//Assegno alla colonnaSterili il numSterili (numero terreni sterili)
                                        InfoTable.setItems(informazioni); 																//Aggiungo il tutto nella tabella principale
                                        //SE LA NAZIONE ITERATA E' VIVA
                                        if (nationList.get(k).getStato() == true) {
                                            informazioni.add(new Nation(nationList.get(k).getName(),nationList.get(k).getAge(),numSterile,numFertile)); //Aggiungo i dati di ogni nazione sulla tabella delle info Nazioni
                                            XYChart.Series set1 = new XYChart.Series<>(); 			//Si crea il grafico degli Abitanti chiamato set (e' una base vuota su cui poi vva scostruito il grafico)
                                            XYChart.Series risorse1 = new XYChart.Series<>();		//Si crea il grafico delle risorse chiamato risorse(e' una base vuota su cui poi vva scostruito il grafico)
                                            XYChart.Series denaro1 = new XYChart.Series<>(); 		//Si crea il grafico del denaro chiamato denaro (e' una base vuota su cui poi vva scostruito il grafico)
                                            //Viene creato un mattone per il grafico che ha sotto il sotto il nome della nazione ed e' alto quanti sono gli abitanti di quella nazione
                                            set1.getData().add(new XYChart.Data<String, Number>(nationList.get(k).getName(), (nationList.get(k).getNumAbitanti())));
                                            //Viene creato un mattone per il grafico che ha sotto il sotto il nome della nazione ed e' alto quanti sono le risorse di quella nazione
                                            risorse1.getData().add(new XYChart.Data<String, Number>(nationList.get(k).getName(),(int) nationList.get(k).getRisorse()));
                                            //Viene creato un mattone per il grafico che ha sotto il sotto il nome della nazione ed e' alto quanto e' il denaro di quella nazione
                                            denaro1.getData().add(new XYChart.Data<String, Number>(nationList.get(k).getName(),(int) nationList.get(k).getDenaro()));
                                            //Aggiungo i mattoni nei vari barChart
                                            barCharD.getData().addAll(denaro1); 		//Aggiungo il mattone del denaro alla rispettiva barChart del denaro, chiamato barChartD.
                                            barChart.getData().addAll(set1);			//Aggiungo il mattone degli abitanti alla rispettiva barChart degli abitanti, chiamato barChart.
                                            barChartR.getData().addAll(risorse1); 		//Aggiungo il mattone dellerisosrse alla rispettiva barChart delle risorse, chiamato barChartR.

                                        }
                                        //ALTRIMENTI, SE LA NAZIONE ITERATA E' MORTA
                                        else {
                                            informazioni.add(new Nation(nationList.get(k).getName()+"(MORTA)",nationList.get(k).getAge(),numSterile,numFertile)); //aggiungo i dati di ogni nazione sulla tabella delle info Nazioni
                                            continue;
                                        }
                                    }
                                }
                                //ALTRIMENTI, SE NON SIAMO AL PRIMO TURNO O SE NON SONO STATI SVOLTI TUTTI I TURNI INDICATI
                                else{
                                    //SE NON CI SONO PIU' NAZIONI VIVE DENTRO LA LISTA NATION LIST E' INUTILE CONTINUARE AD
                                    //ITERARE NAZIONI
                                    boolean vive3 = false;                         //Booleano per verificare se ci sono ancora nazione vive
                                    for(int j=0;j<nationList.size();j++){         //Controlla per ogni nazione se e' viva, e se lo e' tiene
                                        //conto che c'e' almento una nazione viva
                                        if(nationList.get(j).getStato()==true){
                                            vive3 = true;
                                        }
                                    }
                                    if(vive3 = false) {                            //Se non c'e' nessuna nazione viva interrompe il for che fa
                                        //eseguire i turni
                                        this.turni ++;
                                        break;
                                    }
                                    else{
                                        this.nationList = cloneNationThreadList();     	  //Vengono clonate le nazioni
                                        i=-1;
                                    }                                    	  //Infine porto l'indice del for a -1 per riniziare ad iterare da capo                                         	  //Infine porto l'indice del for a -1 per riniziare ad iterare da capo
                                }
                            }
                        }
                    }
                }
                //Viene aggiornato il numero di nazioni morte attuale
                for (int k=0; k<nationList.size();k++){
                    //SE LA NAZIONE NON HA PIU' REGIONI O E' SCESA SOTTO 10 ABITANTI
                    if (nationList.get(k).getStato()==false){
                        nationList.get(k).setStato(false); //Se non e' stato gia' fatto allora setto il suo stato vivo a
                        // false (quindi la faccio morire)
                        nazioniMorte++;                    //Tengo conto di una nazione morta in piu'
                    }
                }
                //SE LE NAZIONI SONO TUTTE MORTE ALLORA SI CONCLUDE IL GIOCO
                if(nazioniMorte == nationList.size()){
                    buttonMenu.setDisable(false);
                    buttonHelp.setDisable(false);
                    try {
                        useButton = true;
                        AnchorPane fPane = FXMLLoader.load(getClass().getResource("FXMLfineGioco.fxml"));
                        Stage fStage = new Stage();
                        fStage.setScene(new Scene(fPane));
                        fStage.setResizable(false);
                        fStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        catch(InterruptedException e){                           //Se si interrompe il thread che gestisce i turni si ottiene un'eccezione
            System.out.println("Il thread gestore della simulazione e' stato interrotto!");
        }
    }



    //METODO CLONE NATION THREAD LIST
    private ArrayList<Nation> cloneNationThreadList(){
        ArrayList<Nation> cloneList = new ArrayList<Nation>();  										 //Crea la lista cloneList
        for(int i=0; i < nationList.size();i++){               											 //Si itera ogni vecchia nazione in nationList
            Nation cloneNazione = new Nation(nationList.get(i).getName(), nationList.get(i).getColor()); //Di ogni nazione iterata viene crata una nuova nazione con lo stesso nome e colore della nazione vecchia
            cloneNazione.setStato(nationList.get(i).getStato());                                         //Viene clonato lo stato della vecchia nazione(vivo
            //o morto).
            if (cloneNazione.getStato() == false) { //se la nazione e' morta oltre al nome, colore e stato viene copiata anche l'eta', ma non il resto:
                //non vengono copiate le altre caratteristiche per evitare errori, anche perche' per le nazioni morte
                //le altre caratteristiche non hanno alcuna utilita' nel proseguimento della simulazione
                cloneNazione.setAge(nationList.get(i).getAge());
            }
            else{
                cloneNazione.cloneCharacters(nationList.get(i));    										 //Si copiano le caratteristiche della vecchia nazione incluse le regioni
            }
            cloneList.add(i, cloneNazione);                     										 //Si aggiunge la nuova nazione clonata alla lista cloneList
        }
        return cloneList;              																	//Ritorna la lista clonata
    }



    //METODO SVEGLIA
    public synchronized void sveglia(){
        notify();
    }



    //METODO CLICK ADD DIMENSIONS
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
        if(gridColumns > 32 || gridRows > 32){                	    //Se il numero di righe o colonne e' maggiore di 32
            if(gridColumns > 32 && gridRows > 32){ 				    //Se il numero di righe e colonne e' maggiore di 32
                this.msgError.setText("Troppe righe e colonne!");	//Label impostata con la scritta "Troppe righe e colonne!"
            }
            else if(gridColumns > 32){ 							    //Altrimenti, se il numero di colonne e' maggiore di 32
                this.msgError.setText("Troppe colonne!");			//Label impostata con la scritta "Troppe colonne!"
            }
            else { 												    //Altrimenti, se il numero di righe e' maggiore di 32
                this.msgError.setText("Troppe righe!");			    //Label impostata con la scritta "Troppe righe!"
            }
            return;    											    //Esce dal metodo cosi' da non aggiungere troppe colonne o righe(o entrambi)
        }
        if(gridColumns < 2 || gridRows < 2){                	    //Se il numero di righe o colonne e' minore di 2
            if(gridColumns < 2 && gridRows < 2){ 				    //Se il numero di righe e colonne e' minore di 2
                this.msgError.setText("Poche righe e colonne!");	//Label impostata con la scritta "Poche righe e colonne!"
            }
            else if(gridColumns < 2){ 							    //Altrimenti, se il numero di colonne e' minore di 2
                this.msgError.setText("Poche colonne!");			//Label impostata con la scritta "Poche colonne!"
            }
            else { 												    //Altrimenti, se il numero di righe e' minore di 2
                this.msgError.setText("Poche righe!");			    //Label impostata con la scritta "Poche righe!"
            }
            return;    											    //Esce dal metodo cosi' da non aggiungere troppe colonne o righe(o entrambi)
        }
        //Se vengono inseriti dati coerenti si puo' proseguire
        this.msgError.setText("Inserisci nazione"); 				//Label impostta con la scritta "Inserisci Nazione"
        this.btnGridDimensions.setDisable(true);   				    //Viene disabilitato il bottone btnGridDimensions
        this.txtRows.setEditable(false);							//Viene disabilitata l'area di testo per specificare il numero di righe
        this.txtColumns.setEditable(false);						    //Viene disabilitata l'area di testo per specificare il numero di colonne
        this.buttonAddNation.setDisable(false); 					//Viene abilitato il bottone buttonAddNation

        double columnPercentual = 582.0/gridColumns;  				/*Percentuale di spazio che deve occupare una colonna nella griglia
        															per potersi adattare(582.0 e' la larghezza fissa della griglia)*/
        ColumnConstraints col = new ColumnConstraints();   		    //Crea una nuova colonna
        col.setPercentWidth(columnPercentual);             		    //Setta la percentuale di larghezza che la colonna deve occupare

        double rowPercentual = 517.0/gridRows;  					/*Percentuale di spazio che deve occupare una riga nella griglia per
        															potersi adattare(517.0 e' l'altezza fissa della griglia)*/
        RowConstraints row = new RowConstraints(); 				    //Crea una nuova riga
        row.setPercentHeight(rowPercentual);       				    //Setta la percentuale di altezza che la riga deve occupare

        //Crea regioni randomiche nella griglia in base al numero di righe e di colonne desiderate dall'utente
        //Queste regioni sono istanze della classe Regione che estende la classe Button
        for (int i=0; i< gridRows; i++ ){							//Per i che va da 0 fino a  al numero di righe inserito dall'utente
            for (int y=0; y<gridColumns; y++){
                Regione bottone = new Regione(i, y);  				//Crea un bottone (uno per ogni incrocio riga - colonna)
                bottone.setMinHeight(rowPercentual-0.56);  			//Imposta l'altezza minima del bottone a rowPercentual
                bottone.setMaxHeight(rowPercentual-0.56);			//Imposta l'altezza massima del bottone a rowPercentual
                bottone.setMaxWidth(columnPercentual-0.60);			//Imposta la grandezza minima del bottone a columnPercentual
                bottone.setMinWidth(columnPercentual-0.60);			//Imposta la grandezza massima del bottone a columnPercentual
                bottone.setId("btn" + i + y);                       //Aggiunge un identificatore(ID) al bottone
                bottone.setValore(gridRows, gridColumns);           //Setta il valore in denaro della regione(cella)
                bottone.setOnAction(this::addRegionToNation);       /*Aggiunge un event handler al bottone che e' quello per colorare
                													la cella in base al'ultima nazione inserita*/
                automaGrid.add(bottone,y,i); 						//Aggiunge il bottone alla griglia
                PopOver pop = createPop(bottone);					//Viene creato il PopOver su quel bottone
            }
        }
    }



    //METODO MENU
    @FXML
    void clickMenu(ActionEvent event) {
        useButton = true;
        //SE E' STATO PREMUTO START (PRIMA DI PREMERE IL BOTTONE BUTTON MENU)
        if(arrayForStart.size()>=1){
            Stage stageFinestra = new Stage();
            stageFinestra.setTitle("ATTENZIONE");
            Label label = new Label();
            label.setText("Perderai tutto quello che hai fatto fino ad ora. Sicuro di voler interrompere la simulazione?");
            Button yesButton = new Button();
            yesButton.setText("Si");
            Button noButton = new Button();
            noButton.setText("No");
            VBox layout = new VBox(10);//
            layout.getChildren().addAll(label, yesButton, noButton);
            layout.setAlignment(Pos.CENTER);
            Scene scene = new Scene(layout);
            stageFinestra.setScene(scene);
            stageFinestra.show();
            //SE IL BOTTONE YES BUTTON VIENE PREMUTO
            yesButton.setOnAction(e -> {
                for(Iterator<Nation> i = nationList.iterator(); i.hasNext();) {
                    Nation nazione = i.next();
                    ListaColori.add(nazione.getColor());
                }
                nomiNazioni.clear();
                nationList.clear();
                try {
                    AnchorPane menu = FXMLLoader.load(getClass().getResource("FXMLmenu.fxml"));
                    borderPane.getChildren().setAll(menu);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                stageFinestra.close(); //e chiudo la finestra
            });
            //SE IL BOTTONE NO BUTTON VIENE PREMUTO
            noButton.setOnAction(e -> {
                stageFinestra.close();
            });
        }
        //ALTRIMENTI, SE E' STATO PREMUTO MENU (NON AVENDO PREMUTO IL BOTTONE BUTTON START)
        else{
            for(Iterator<Nation> i = nationList.iterator(); i.hasNext();) {
                Nation nazione = i.next();
                ListaColori.add(nazione.getColor());
            }
            nomiNazioni.clear();
            nationList.clear();
            try {
                AnchorPane menu = FXMLLoader.load(getClass().getResource("FXMLmenu.fxml"));
                borderPane.getChildren().setAll(menu);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    //METODO ADD REGION TO NATION
    @FXML
    void addRegionToNation(ActionEvent event) {
        //SE SI E' GIA' PREMUTO START NON SUCCEDE NULLA
        if (useStart==true){
            return;
        }
        //SE NON ABBIAMO CREATO NESSUNA NAZIONE E CLICCHIAMO SU UNA CELLA DELLA GRIGLIA
        //IL PROGRAMMA NON DEVE FARE NULLA
        if (nationList.isEmpty() == true) {
            buttonAddNation.setDisable(false);
        }
        //ALTRIMENTI, SE ABBIAMO CREATO UNA NAZIONE E CLICCHIAMO SU UNA CELLA DELLA GRIGLIA
        //LA CELLA DEVE ESSERE COLORATA DEL COLORE SCELTO E DEVE ESSERE ASSEGNATA ALLA NAZIONE
        else {
            if(((Regione) event.getSource()).getNomeNazione().equals("")) { /*Se la cella su cui si clicca non fa gia'
               																parte di una nazione si assegna quella regione alla nazione*/
                nationList.get(0).setGridController(this);                 	//Passo this alla nazione per operazioni con i thread
                int gridColumns = 0;                                       	//Numero di colonne della griglia desiderato dall'utente
                int gridRows = 0;                                         	//Numero di righe della griglia desiderato dall'utente
                this.buttonDeleteNation.setDisable(false);                 	//Viene abilitato il bottone buttonDeleteNation
                this.buttonStart.setDisable(false);                        	//Viene abilitato il bottone buttonStart
                //SETTA LA NAZIONE DI APPARTENENZA E IL COLORE DELLA NAZIONE SULLA CELLA: inoltre passa l'oggeto nazione alla regione utile
                //poi per la gestione dei thread
                ((Regione) event.getSource()).setNazione(nationList.get(0).getName(), nationList.get(0).getColor(), nationList.get(0));
                //AUMENTA IL NUMERO DI ABITANTI, LE RISORSE E IL DENARO DELLA NAZIONE IN BASE ALLE CARATTERISTICCHE DEL TERRITORIO ASSEGNATO
                nationList.get(0).takeProfit(((Regione) event.getSource()).getTipo(), ((Regione) event.getSource()).getRisorse());
                //AGGIUNGE L'OBJECT REGIONE (LA CELLA) ALLA LISTA DELLE REGIONI(LE CELLE) ASSEGNATE ALLA NAZIONE
                //QUINDI AGGIUNGE LA CELLA ALLA LISTA REGIONI DI Nation
                nationList.get(0).addRegion((Regione) event.getSource());
                //System.out.println(nationList.get(0).getRegioni().get(nationList.get(0).getRegioni().size()-1)); //stampo l'ultimo id inserito
                try {
                    gridColumns = Integer.parseInt(txtColumns.getText());       //Prende il numero di colonne inserito dall'utente nell'area di testo chiamata txtColumns
                    gridRows = Integer.parseInt(txtRows.getText());             //Prende il numero di righe inserito dall'utente nell'area di testo chiamata txtRows
                } catch (NumberFormatException n) {                            	//Se l'utente non inserisce un intero si ha un eccezione
                    //Esce dal metodo cosi' da non generare errori
                }
                contaNumeroCelleUsate++;                                        //Incrementa il numero di celle utilizzate
                //SE LE CELLE SONO STATE TUTTE UTILIZZATE BISOGNA DISABILITARE IL BOTTONE  BUTTON ADD NATION
                if (contaNumeroCelleUsate >= (gridColumns * gridRows)) {        //Se sono state usate tutte le celle
                    this.buttonAddNation.setDisable(true);                        //Viene disabilitato il botttone buttonAddNation
                }
            }
            //ALTRIMENTI SE LA CELLA SU CUI CLICCHIAMO E' STATA ASSEGNATA GIA' AD UNA NAZIONE NON SUCCEDE NULLA
            else{
                return;
            }
        }
    }
}
