package interfacciaGrafica;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
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

public class ControllerImpostazioniGriglia implements Initializable {

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
    private StackedBarChart barChart;  		//Grafico chiamato barChart, base per le statistiche degli abitanti

    @FXML
    private StackedBarChart barChartR; 		//Grafico chiamato barChartR, base per le statistiche delle risorse

    @FXML
    private StackedBarChart barCharD; 		//Grafico chiamato barChartD, base per le statistiche del denaro

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

    @FXML
    private Tab tabRisorse;                 //Tab per le statistiche sulle risorse di ogni nazione

    @FXML
    private Tab tabDenaro;                  //Tab per le statistiche sul denaro di ogni nazione

    @FXML
    private Tab tabPopolazione;             //Tab per le statistiche sulla popolazione di ogni nazione

    @FXML
    private TextArea txtTurniDaSvolgere;    //TextArea dove inserire durante una pausa il numero di turni per i quali si
                                            // desidera far continuare la simulazione

    int contaNumeroCelleUsate;				//Variabile di tipo intera per sapere il numero di celle della griglia che sono state usate

    double valAttualeRisorse=0;				//Variabile che tiene conto del valore delle risorse

    int valAttualeAbitanti=0;				//Variabile che tiene conto del numero di abitanti

    double valAttualeDenaro=0;				//Variabile che tiene conto della quantita' di denaro

    boolean useStart= false;				//Variabile per vedere se start e' premuto (true) o meno (false)

    static boolean useButton = false;   	//Variabile booleana usata per capire se e' stato cliccato uno dei bottoni aggiungiNazione, eliminaNazione, Help o menuPrincipale

    int turni = 0;                          //La variabile turni tiene conto del numero di turni trascorsi dall'inizio del gioco.
                                            //Un turno è completo quando tutte le nazioni in gioco "hanno fatto la propria mossa".

    //Crea una lista di stringhe chiamata arrayForStart che serve per capire se Start e'
    //stato premuto o no nel metodo ClickMenu
    static ArrayList<String> arrayForStart = new ArrayList<>();

    //Creo una lista di stringhe chiamata NomiNazioniCopia che serve per verificare
    //se la regione per cui sto costruendo il grafico e' gia' presente nel grafico
    //o se e' appena stata creata
    ArrayList<String> NomiNazioniCopia = new ArrayList<>();


    //METODO CLICK ADD NATION
    //Viene impostata la variabile useButton a true (viene rimessa a false nella classe ControllerAddNation
    //quando si clicca il bottone chiamato buttonAggiungi per creare una nazione).
    //Poi se il numero  di elementi  della lista chiamata nationList e' minore di 38 (che sono
    //il nu mero di colori disponibili che si possono assegnare alle nazioni)allora quando il bottone
    //buttonAddNation viene premuto, viene creato un oggetto di tipo AnchorPane chiamato
    //addNationPane facendo riferimento e richiamando l'intefaccia definita in FXMLaddNation.fxml.
    //Quindi addNationPane sara' l'interfaccia definita in FXMLaddNation.fxml.
    //Poi viene creato un nuovo Stage, chiamato addNationStage, e specifica la scena da usare
    //su quello stage (con il metodo setScene).
    //QUINDI MOSTRA LA SCENA addNationPane SULLO STAGE addNationStage.
    //Infine mostra l'addNationStage impostando la visibilita' a true (con il metodo show).
    //Quindi viene visualizzata la finestra per creare ed inserire la nazione.
    //Altrimenti, se il numero  di elementi  della lista chiamata nationList non e' minore di 38
    //allora significa che non si possono aggiunere piu' nazioni perche' significa che sono stati
    //usati tutti i colori (perche' ogni volta che si crea una nuova nazione, in ControllerAddNation,
    //viene tolto dalla ListaColori il colore usato per la nazione creata, in tal modo non possono essere
    //create piu' nazioni con lo stesso colore), allora viene creato un oggetto di tipo AnchorPane
    //chiamato limitPane facendo riferimento e richiamando l'intefaccia definita in FXMlimite.fxml.
    //Quindi limitPane sara' l'interfaccia definita in FXMLlimite.fxml.
    //Poi viene creato un nuovo Stage, chiamato limitStage, e specifica la scena da usare
    //su quello stage (con il metodo setScene).
    //QUINDI MOSTRA LA SCENA limitPane SULLO STAGE limitStage.
    //Infine mostra il limitStage impostando la visibilita' a true (con il metodo show).
    //Quindi viene visualizzata la finestra per avvisare l'utente che si e' raggiunto il limite
    //massimo di nazioni che possono essere create.
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
    //Viene impostata la variabile useButton a true (viene rimessa a false nella classe ControllerDeleteNation
    //quando si clicca il bottone chiamato buttonElimina per eliminare una nazione).
    //Quando il bottone buttonDeleteNation viene premuto, se la lista chiamata nationList
    //che contiene tutte le nazioni che sono state create e' vuota (quindi se ci sono nazioni)
    //viene creato un oggetto di tipo AnchorPane chiamato noDeletePane facendo riferimento
    //e richiamando l'intefaccia definita in FXMLnoDelete.fxml.
    //Quindi noDeletePane sara' l'interfaccia definita in FXMLnoDelete.fxml
    //Poi viene creato un nuovo Stage, chiamato NoDeleteStage, e specifica la scena da usare
    //su quello stage (con il metodo setScene).
    //QUINDI MOSTRA LA SCENA noDeletePane SULLO STAGE noDeleteStage.
    //Infine mostra il noDeleteStage impostando la visibilita' a true (con il metodo show).
    //Quindi viene visualizzata la finestra per avvisare l'utente che non si puo' eliminare
    //la nazione (perche' non ci sono nazioni).
    //Altrimenti,se c'e' almeno una nazione disponibile si riattiva il bottone
    //buttonAddNation e poi viene creato un oggetto di tipo AnchorPane chiamato deleteNationPane
    //facendo riferimento e richiamando l'intefaccia definita in FXMLdeleteNation.fxml.
    //Quindi deleteNationPane sara' l'interfaccia definita in FXMLdeleteNation.fxml
    //Poi viene creato un nuovo Stage, chiamato deleteNatiionStage, e specifica la scena da
    //usare su quello stage (con il metodo setScene).
    //QUINDI MOSTRA LA SCENA deleteNationPane SULLO STAGE deleteNationStage.
    //Infine mostra il deleteNationStage impostando la visibilita' a true (con il metodo show).
    //Quindi viene visualizzata la finestra per eliminare la nazione.
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
    //Viene impostata la variabile useButton a true (viene rimessa a false nella classe ControllerHelp
    //quando si clicca il bottone chiamato buttonClose per chiudere il menu di aiuto).
    //Quando il bottone buttonHelp viene premuto, viene creato un oggetto di tipo AnchorPane chiamato
    //helpPane facendo riferimento e richiamando l'intefaccia definita in FXMLhelp.fxml.
    //Quindi helpPane sara' l'interfaccia definita in FXMLhelp.fxml.
    //Poi viene creato un nuovo Stage, chiamato helpStage, e specifica la scena da usare
    //su quello stage (con il metodo setScene).
    //QUINDI MOSTRA LA SCENA helpPane SULLO STAGE helpStage.
    //Infine mostra il helpStage impostando la visibilita' a true (con il metodo show).
    //Quindi viene visualizzata la finestra per il menu di aiuto.
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
    //Viene impostata la variabile useButton a true
    //C'e' una area di testo sotto lo start da cui vengono presi i turni per i quali avanzare nella simulazione
    //prima di una pausa: inizalmente per default si ha "1"(un turno) e quindi si avanza tranquillamente, ma
    //se poi viene dato un numero uguale a 0 si ottiene un messaggio d'errore nel label in basso a destra. Quindi
    //per continuare bisogna inserire sempre un numero maggiore di 0.
    //Se il numero di turni per cui avanzare e' > 0, viene disabilitato il bottone buttonAddNation,
    //(cosi da togliera la possibilita' di aggiungere altre nazioni dopo aver premuto Start),
    //viene disabilitato il bottone buttonDeleteNation,(cosi da togliera la possibilita'
    //di cancellare nazioni dopo aver premuto Start).
    //Se la lista chiamata nationList che contiene tutte le nazioni che sono state create
    //e' vuota (quindi se non ci sono nazioni) allora il gioco non puo' iniziare per cui
    //vengono riabilitati il bottone buttonAddNation e il bottone buttonDeleteNation e
    //in seguito viene creato un oggetto di tipo
    //AnchorPane chiamato noStartPane facendo riferimento e richiamando l'intefaccia
    //definita in FXMLnoStart.fxml.
    //Quindi noStartPane sara' l'interfaccia definita in FXMLnoStart.fxml
    //Poi viene creato un nuovo Stage, chiamato noStartStage, e specifica la scena da usare
    //su quello stage (con il metodo setScene).
    //QUINDI MOSTRA LA SCENA noStartPane SULLO STAGE noStartStage.
    //Infine mostra il noDeleteStage impostando la visibilita' a true (con il metodo show).
    //Quindi viene visualizzata la finestra per avvisare l'utente che non si puo' far partire
    //il gioco (perche' non ci sono nazioni).
    //Altrimenti,se la lista chiamata nationList che contiene tutte le nazioni che sono state
    //create non e' vuota (quindi se ci sono nazioni) allora il gioco puo' iniziare.
    //Cosi una volta premuto il bottone start viene impostata la variabile useStart a true ed
    //in seguito viene impstata la scritta della label (in basso a destra) chiamata mggError
    //con la scritta "Partita iniziata".
    //Poi per ogni nazione dentro la lista nationList viene creata la base per il grafico degli gli
    //abitanti della nazione chiamata set, la base per il grafico delle risorse chiamata risorse e
    //la base per il grafico del denaro chiamata denaro.
    //Poi controlla se  il nome della nazione che sto aggiornardo non e' contenuta in NomiNazioniCopia
    //e percio' significa che e' la prima volta che la creo quindi devo mettere a 0 il valore attuale di risorse,
    //il numero attuale di abitanti e il denaro attuale della nazione.
    //Dopodiche aggiungo la nazione alla lista NomiNazioniCopia.
    //Poi viene creato un mattone per il grafico che ha sotto il sotto il nome della nazione ed e' alto quanti
    //sono gli abitanti di quella nazione. Poi viene aggiornato il numero di abitanti di quella nazione, richiamando
    //il metodo getNumAbitanti.
    //Poi viene creato un mattone per il grafico che ha sotto il sotto il nome della nazione ed e' alto quanti
    //sono le risorse di quella nazione. Poi viene aggiornato il il valore delle rissorse di quella nazione, richiamando
    //il metodo getRisorse.
    //Poi viene creato un mattone per il grafico che ha sotto il sotto il nome della nazione ed e' alto quanto
    //e' il denaro di quella nazione. Poi viene aggiornato il denaro di quella nazione, richiamando
    //il metodo getDenaro.
    //Poi aggiungo il mattone degli abitanti alla rispettiva barChart degli abitanti, chiamato barChart.
    //Poi aggiungo il mattone dellerisosrse alla rispettiva barChart delle risorse, chiamato barChartR.
    //Poi aggiungo il mattone del denaro alla rispettiva barChart del denaro, chiamato barChartD.
    //Infine viene aggiunta dentro  la lista arrayForStart la stringa "Start e' stato premuto".
    //Vengono disabilitati i bottoni di menu, help, start e statistiche che saranno cliccabili solo a gioco fermo.
    //Disabilitata a gioco che avanza anche l'area di testo in cui inserire i turni.
    //L'uso del pulsante start viene cambiato ora in continua, dove una volta cliccato su start(che una volta avviato il gioco
    //si trasforma in continua) si continua per il numero di turni inseriti nell'area di testo sotto start.
    //Se si clicca su continua e quindi siamo nella situazione in cui start e' gia' stato premuto una volta allora bisogna
    //clonare le nazioni in maniera da poter fare di nuovo start delle nazioni in nationList(cosa altrimenti non possibile
    //visto che l'istanza di un thread puo' essere runnato una sola volta). Si tiene conto poi del numero di turni per il
    //quale avanzare: il numero e' quello indicato nell'area di testo sotto lo start.
    //Si arriva quindi al punto centrale della simulazione in cui c'e' un for che va ad iterare tutte le nazioni dentro
    // la lista nationList (viene iterata nazione per nazione E QUESTO VIENE FATTO DAL THREAD MAIN).
    //Quindi viene presa una nazione per volta in maniera progressiva (e vengono prese dall'ultima creata
    //perche' noi inseriamo sempre in prima posizione nella lista) e il thread main fa start di quella nazione
    //e siccome poi il main continuerebbe ad andare avanti nel codice e' stata messa una wait, cosi la nazione
    //fa il proprio turno e il main (grazie alla wait) aspetta che la nazione ha finito.
    //Cosi facendo viene svolto un turno per ogni nazione.
    //Il thread Main attende finche' la nazione iterata ha finito di svolgere il suo turno (me ne rendo conto richiamando il metodo
    //getThreadState della classe Nation che restituisce true se il thread non ha finito di svolgere il suo turno, false altrimenti)
    //quindi attende finche' il metodo getThreadState restituisce true.
    //In seguito, controlliamo se e' stato eseguito il turno dell'ultima nazione: se si allora si tiene conto che e' stato svolto
    // un turno per ogni nazione(this.turni ++;) e si dimuisce il numero di turni che devono ancora essere svolti(indicati nella
    // casella di testo). Se siamo al primo turno o e' stato svolto il numero di turni indicato si ferma la simulazione(pausa)
    // per poter consultare le statistiche, constatare le differenze e decidere di quanti turni far avanzare la simulazione
    // Se non siamo al primo turno e non e' stato svolto il numero di turni indicato bisogna riiniziare dalla prima nazione ma
    // siccome c'e' un problema con i Thread,  cioe' che una volta eseguito lo start di un thread non si puo' piu' rieseguire
    // lo start, almeno che non si crea una nuova istanza, allora vengono clonate le nazioni (i thread perche' Nation estende
    // Thread) e vengono sostituite a quelle vecchie, richimando il metodo cloneNationThreadList, ottenendo cosi nuovi oggetti
    // di tipo Nation startabili (in grado di eseguire il proprio run()) ma che mantengono gli stessi dati di prima (senza
    // perdite di dati, appunto perche' sono stati clonati).
    //Infine viene messo l'indice del for a -1 per riniziare ad iterare da capo le nazioni della lista nationList.
    //Per eseguire la wait in clickStart bisogna sincronizzare (synchronize) il metodo in maniera che una nazione possa
    //notificare il thread che gestiva i turni, senza synchronize si otterebbero delle eccezioni.
    //Quando la nazione ha finito di eseguire il proprio turno, bisogna svegliare il thread main ed eseguire il
    //turno della nazione successiva, per cui per svegliare il main c'e' il metodo sveglia che fa una notify.
    @FXML
    synchronized void clickStart(ActionEvent event) {
        useButton = true;
        try{
            if(Integer.parseInt(this.txtTurniDaSvolgere.getText()) == 0){    //Per continuare bisogna inserire un numero di turni > 0,
                                                                             //altrimenti si ottiene un errore nel label in basso a destra
                                                                             //(il primo turno viene svolto senza problemi perche' il TextArea
                                                                             //txtTurniDaSvolgere ha gia' "1" come valore di default
                this.msgError.setText("Inserire n. turni > 0");
            }
            else{                                                           //Se i turni per i quali avanzare sono > 0
                this.buttonAddNation.setDisable(true); 						//Viene disabilitato il bottone buttonAddNation
                this.buttonDeleteNation.setDisable(true);					//Viene disabilitato il bottone buttonDeleteNation

                //SE NATION LIST E' VUOTA NON E' POSSIBILE INIZIARE IL GIOCO
                if (nationList.size() == 0){
                    try {
                        AnchorPane noStartPane = FXMLLoader.load(getClass().getResource("FXMLnoStart.fxml"));
                        buttonAddNation.setDisable(false);
                        buttonDeleteNation.setDisable(false);
                        Stage noStartStage = new Stage();
                        noStartStage.setScene(new Scene(noStartPane));
                        noStartStage.setResizable(false);
                        noStartStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //ALTRIMENTI, SE NATION LIST NON E' VUOTA E' POSSIBILE INIZIARE IL GIOCO
                }else{
                    useStart=true;
                    for(int indice = 0; indice <nationList.size(); indice++) {
                        XYChart.Series set = new XYChart.Series<>(); 			//Si crea il grafico degli Abitanti chiamato set (e' una base vuota su cui poi vva scostruito il grafico)
                        XYChart.Series risorse = new XYChart.Series<>();		//Si crea il grafico delle risorse chiamato risorse(e' una base vuota su cui poi vva scostruito il grafico)
                        XYChart.Series denaro = new XYChart.Series<>(); 		//Si crea il grafico del denaro chiamato denaro (e' una base vuota su cui poi vva scostruito il grafico)
                        //SE IL NOME DELLA NAZIONE NON E' CONTENUTO NELLA LISTA NOMINAZIONI COPIA
                        if (!(NomiNazioniCopia.contains(nationList.get(indice).getName()))) {
                            valAttualeAbitanti = 0;
                            valAttualeRisorse = 0;
                            valAttualeDenaro = 0;
                            NomiNazioniCopia.add(nationList.get(indice).getName());
                        }
                        //Viene creato un mattone per il grafico che ha sotto il sotto il nome della nazione ed e' alto quanti sono gli abitanti di quella nazione
                        set.getData().add(new XYChart.Data<String, Number>(nationList.get(indice).getName(), (nationList.get(indice).getNumAbitanti() - valAttualeAbitanti)));
                        valAttualeAbitanti = (nationList.get(indice).getNumAbitanti());
                        //Viene creato un mattone per il grafico che ha sotto il sotto il nome della nazione ed e' alto quanti sono le risorse di quella nazione
                        risorse.getData().add(new XYChart.Data<String, Number>(nationList.get(indice).getName(), nationList.get(indice).getRisorse() - valAttualeRisorse));
                        valAttualeRisorse = (nationList.get(indice).getRisorse());
                        //Viene creato un mattone per il grafico che ha sotto il sotto il nome della nazione ed e' alto quanto e' il denaro di quella nazione
                        denaro.getData().add(new XYChart.Data<String, Number>(nationList.get(indice).getName(), nationList.get(indice).getDenaro() - valAttualeDenaro)); //creo un mattone che ha sotto il nome della nazione ed ÃƒÂ¨ alto tanto quanto ÃƒÂ¨ il denaro di quella nazione
                        valAttualeDenaro = nationList.get(indice).getDenaro();
                        barCharD.getData().addAll(denaro); 		//Aggiungo il mattone del denaro alla rispettiva barChart del denaro, chiamato barChartD.
                        barChart.getData().addAll(set);			//Aggiungo il mattone degli abitanti alla rispettiva barChart degli abitanti, chiamato barChart.
                        barChartR.getData().addAll(risorse); 	//Aggiungo il mattone dellerisosrse alla rispettiva barChart delle risorse, chiamato barChartR.
                    }
                    arrayForStart.add("Start e' stato premuto");

                    //I bottoni non saranno cliccabili durante lo svolgimento di un turno. Inoltre durante lo svolgimento
                    // di un turno non sara' possibile editare l'area di testo in cui inserire i turni per i quali la simulazione deve continuare.
                    this.buttonStart.setDisable(true);
                    this.buttonHelp.setDisable(true);
                    this.buttonMenu.setDisable(true);
                    this.tabPopolazione.setDisable(true);                   //Non e' possibile consultare le statistiche mentre il gioco avanza
                    this.tabDenaro.setDisable(true);
                    this.tabRisorse.setDisable(true);
                    this.txtTurniDaSvolgere.setDisable(true);

                    this.msgError.setText("Turno " + this.turni + 1);       //Mostra il turno in cui ci troviamo nel label in basso a destra
                    this.buttonStart.setText("Continua");                   //Il bottone di start cambia ora utilizzo: servira' ora per continuare
                                                                            //tra una pausa e l'altra
                    if(useStart == true){                                   //Se la simulazione e' iniziata
                        this.nationList = cloneNationThreadList();     	    //Vengono clonate le nazioni per poter startare di nuovo i thread
                    }
                    int turniSvolti = Integer.parseInt(txtTurniDaSvolgere.getText()); //numero di turni da svolgere prima della pausa
                    //Punto centrale della simulazione in cui viene fatto svolgere un turno per ogni nazione finche' e' possibile
                    //I turni vengono fatti svolgere in maniera progressiva dall'ultima alla prima nazione inserita nelle impostazioni iniziali
                    for(int i=0; i < nationList.size(); i++){  				//Viene iterata nazione per nazione della lista nationList
                        nationList.get(i).start();            				//Viene svolto il turno della nazione considerata
                        do {
                            wait();                           				//Il therad main si mette in attesa che la nazione finisca di svolgere il suo turno
                        }
                        while(nationList.get(i).getThreadState() == true); 	//E resta in attesa finche' il metodo getThreadState restituisce true

                        if(i == nationList.size()-1){                      	//Se e' stato svolto il turno dell'ultima nazione in lista bisogna riiniziare dalla prima
                            this.turni ++;                                 //Si tiene conto che si e' arrivati alla fine del turno per tutte le nazioni
                            turniSvolti --;                                //Un turno e' stato svolto e quindi aggiorno il numero di turni da svolgere
                                                                           //rimanenti

                            if(this.turni == 1 || turniSvolti == 0){      //Se siamo al primo turno o e' stato svolto il numero di turni indicato
                                                                          //si ferma la simulazione(pausa) per poter consultare le statistiche,
                                                                          // constatare le differenze e decidere di quanti turni far avanzare la
                                                                          // simulazione
                                //Viene riabilitato l'uso dei bottoni
                                this.buttonHelp.setDisable(false);
                                this.buttonMenu.setDisable(false);
                                this.tabPopolazione.setDisable(false);      //Le varie statistiche saranno consultabili solo a gioco fermo
                                this.tabDenaro.setDisable(false);
                                this.tabRisorse.setDisable(false);
                                this.buttonStart.setDisable(false);         //Viene riabilitato il bottone di start che ora serve per continuare
                                                                            //la simulazione
                                this.txtTurniDaSvolgere.setDisable(false);  //A gioco fermo in questa TextArea si puo' inserire il numero di turni per
                                                                            //il quale continuare la simulazione
                                this.msgError.setText("Inserire n. turni > 0"); //Si avverte di inserire il numero di turni da svolgere

                            }
                            else{                                          //Altrimenti la simulazione continua con il prossimo turno

                                this.msgError.setText("Turno " + this.turni + 1); //Mostra il turno in cui ci troviamo nel label in basso a destra
                                this.nationList = cloneNationThreadList();     	  //Vengono clonate le nazioni
                                i=-1;                                          	  //Infine porto l'indice del for a -1 per riniziare ad iterare da capo
                            }
                        }
                    }
                }
            }
        }
        catch(InterruptedException e){                           //Se si interrompe il thread che gestisce i turni si ottiene un'eccezione
            System.out.println("Il thread gestore della simulazione e' stato interrotto!");
        }
    }



    //METODO CLONE NATION THREAD LIST
    //Clona i thread (le nazioni perche' Nation estende Thread) della lista nationList e li sostituisce a quelli in nationList.
    //Crea una nuova lista di nazioni chiamata cloneList.
    //Poi itera tutte le nazioni nella lista nation list e di ogni nazione iterata viene creata una nuova nazione con il nome e
    //colore della nazione vecchia (nome e colore vengono presi richiamando i metodi getName e getColor della classe Nation)
    //e si copiano le caratteristiche della vecchia nazione incluse le regioni, richiamando il metodo cloneCharacters della
    //classe Nation ed infine si aggiunge il clone della vecchia nazione (quindi si aggiunge la nuova nazione) alla lista
    //cloneList ed infine viene ritornata la lista cloneList
    private ArrayList<Nation> cloneNationThreadList(){
        ArrayList<Nation> cloneList = new ArrayList<Nation>();  										 //Crea la lista cloneList
        for(int i=0; i < nationList.size();i++){               											 //Si itera ogni vecchia nazione in nationList
            Nation cloneNazione = new Nation(nationList.get(i).getName(), nationList.get(i).getColor()); //Di ogni nazione iterata viene crata una nuova nazione con lo stesso nome e colore della nazione vecchia
            cloneNazione.cloneCharacters(nationList.get(i));    										 //Si copiano le caratteristiche della vecchia nazione incluse le regioni
            cloneList.add(i, cloneNazione);                     										 //Si aggiunge la nuova nazione clonata alla lista cloneList
        }
        return cloneList;              																	//Ritorna la lista clonata
    }



    //METODO SVEGLIA
    //Siccome il thread main si mette in attesa (wait) mentre una nazione sta svolgendo il proprio turno turno,
    //(e questo viene fatto nel metodo click start) allora quando la nazione ha finito il proprio turno
    //bisogna svegliare il thread (notify) e passare passare ad eseguire il turno della nazione successiva.
    //Quindi per svegliare il thread viene fatta una notify.
    //Quindi questo metodo e' utilizzato da una nazione che sta svolgendo un turno per svegliare il thread
    //che gestisce l'intera simulazione, ovvero il thread main
    public synchronized void sveglia(){
        notify();
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
    //Siccome si puo' inserire un massimo numero di 32 righe e 32 colonne, se il numero
    //di righe e colonne e' maggiore di 32 allora la label msgError sara' impostata con
    //la scritta "Troppe righe e colonne!".
    //Altrimenti, se il numero di colonne e' maggiore di 32 allora la label msgError
    //sara' impostata con la scritta "Troppe colonne!", mentre  se il numero di righe e'
    //maggiore di 32 allora la label msgError sara' impostata con la scritta "Troppe righe!"
    //Siccome si puo' inserire un minimo numero di 2 righe e 2 colonne, se il numero
    //di righe e colonne e' minore di 2 allora la label msgError sara' impostata con
    //la scritta "Poche righe e colonne!".
    //Altrimenti, se il numero di colonne e' minore di 2 allora la label msgError
    //sara' impostata con la scritta "Poche colonne!", mentre  se il numero di righe e'
    //minore di 2 allora la label msgError sara' impostata con la scritta "Poche righe!"
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
    //del bottone (cosi da riempire tutta la griglia), identificatore e event handler per
    //colorare il bottone in base al colore scelto dall' utente e vengono aggiunti i bottoni alla griglia.
    //inolte ogni volta che viene aggiuntonuovo bottone alla griglia viene anche creato il popover corrispondente.
    //Un PopOver e' una specie di finetra (senza pero' titolo e bottoni).
    //Poi viene creata una label, chiamata risorseRegione e viene impostato il testo di questa label con
    //il valore delle risorse relative a quella specifica regione (cioe' quella cella quante risorse ha).
    //Poi viene creata un'altra label, chiamata tipoRegione e viene impostato il testo di questa label con
    //il tipo di terreno (fertile o sterile) relativo a quella specifica regione (cioe' il terreno di quella cella se e' fertile o sterile).
    //Poi viene creata un'altra label, chiamata valoreRegione e viene impostato il testo di questa label con
    //il denaro relative a quella specifica regione (cioe' quella cella quanto denaro ha).
    //In seguito viene creato un VBox chiamato VBox (per disporre i componenti verticalmente, in questo caso i
    //componenti sono le dtree label) e aggiunge a VBox prima la label risorseRegione poi la label tipoRegione
    //ed infine la label valoreRegione
    //Poi viene creato un PopOver chiamato pop, e questo PopOver mostrera' il valore delle risorse presenti in quella Regione,
    //che saranno poi sfruttate dalle nazioni che la occuperanno.
    //Poi viene impostata l'azione che quando si passa il mouse sopra una regione (una cella) allora viene visualizzato
    //il PopOver (cioe' il PopOver viene visualizzato solo quando si passail mouse sopra una cella ).
    //Allo stesso modo viene impostata l'azione che quando si sposta il mouse allora viene chiuso il PopOver.
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
            for (int y=0; y<gridColumns; y++){						//Per y che va da 0 fino a  al numero di colonne inserito dall'utente
                Regione bottone = new Regione();  					//Crea un bottone (uno per ogni incrocio riga - colonna)
                bottone.setValore(gridRows, gridColumns);           //Setta il valore in denaro della regione(cella)
                bottone.setMinHeight(rowPercentual-0.56);  			//Imposta l'altezza minima del bottone a rowPercentual
                bottone.setMaxHeight(rowPercentual-0.56);			//Imposta l'altezza massima del bottone a rowPercentual
                bottone.setMaxWidth(columnPercentual-0.60);			//Imposta la grandezza minima del bottone a columnPercentual
                bottone.setMinWidth(columnPercentual-0.60);			//Imposta la grandezza massima del bottone a columnPercentual
                bottone.setId("btn" + i + y);                       //Aggiunge un identificatore(ID) al bottone
                bottone.setOnAction(this::addRegionToNation);       /*Aggiunge un event handler al bottone che e' quello per colorare
                													la cella in base al'ultima nazione inserita*/
                automaGrid.add(bottone,y,i); 						//Aggiunge il bottone alla griglia

                //Ogni volta che viene aggiunto un nuovo bottone alla griglia viene anche creato il popover corrispondente.
                Label risorseRegione = new Label();											//Si crea una Label  chiamata appartenenzaNazione
                risorseRegione.setText("Risorse disponibili: " + bottone.getRisorse());		//Setta il testo della label con il valore delle risorse relative a quella specifica regione
                Label tipoRegione = new Label();											//Si crea una Label  chiamata tipoRegione
                tipoRegione.setText("Regione: " + bottone.getTipo());						//Setta il testo della label con il tipo di terreno relativo a quella specifica regione
                Label valoreRegione = new Label();											//Si crea una Label chiamata valoreRegione
                valoreRegione.setText("Valore in denaro: " + bottone.getValore());			//Setta il testo della label con il denaro relativo a quella specifica regione
                VBox vBox = new VBox(risorseRegione,tipoRegione,valoreRegione);				//Si crea un VBox che contiene le tre label create precedentemente
                PopOver pop = new PopOver(vBox);											//Si crea un PopOver chiamato pop
                //IL POPOVER VERRA' VISUALIZZATO QUANDO SI PASSA IL MOUSE SOPRA UNA REGIONE (CELLA)
                bottone.setOnMouseEntered(MouseEvent -> {
                    if(useButton == false) {
                        pop.show(bottone);
                    }
                });
                //IL POPOVER SI CHIDERA' QUANDO SI SPOSTA IL MOUSE DALLA REGIONE (CELLA)
                bottone.setOnMouseExited(MouseEvent -> {
                    if(useButton == false) {
                        pop.hide();
                    }
                });
            }
        }
    }



    //METODO MENU
    //Se la lunghezza dell'array arrayForStart e' maggiore o uguale di 1 (quindi si e' gia'
    //premuto Start e poi si preme Menu) viene creato un nuovo Stage, chiamato stageFinestra e imposta il titolo
    //di questo stage con la scritta "ATTENZIONE".
    //Poi vengono creati i vari componenti (o elementi) da mettere dentro questo stage.
    //Quindi viene creata una label chiamata label e viene impostato il testo di questa label
    //con la scritta "Perderai tutto quello che hai fatto fino ad ora. Sicuro di voler interrompere la simulazione?".
    //Poi viene creato un bottone chiamato yesButton e viene impostato il testo di questo bottone con la scritta "Si".
    //Allo stesso modo viene creato un altro bottone chiamato no Button e viene impostato il testo di questo bottone
    //con la scritta "No".
    //Poi crea un VBox chiamato layout (per disporre i componenti verticalmente, in questo caso i
    //componenti sono la label e i due bottoni yesButton e noButton)
    //e aggiunge a layout prima la label poi il bottone yesButton e poi il bottone
    //noButton ed infine posiziona questi due componenti al centro (richiamando il metodo setAlignment)
    //Infine crea una Scene (contenitore piu'  interno) chiamata scene per il nodo radice
    //specificato (layout) e specifica la scena da usare sullo stage stageFinestra
    //(con il metodo setScene) e poi mostra lo stage stageFinestra impostandola visibilita' a
    //true (con il metodo show).
    //Se il bottone yesButton viene premuto (quindi se si e' sicuri di interrompere la simulazione)
    //vengono presi tutti i colori che erano stati usati per colorare le celle della nazioni e vengono riaggiunti
    //alla lista ListaColori (che contiene' tutti i colori delle nazioni che potranno essere scelti quando si crea
    //una nuova nazione) cosida rendere nuovamenti i colori diponibili ed in seguito viene cancellato tutto cio' che si
    //trova dentro la lista nomiNazioni (che contiene' tutti i nomi delle nazioni che sono state create) e viene cancellato
    //tutto cio' che si trova dentro la lista nationList  (che conterra' tutte le nazioni che sono state create) in maniera
    //da non tenere memorizzate le nazioni che sono state create.
    //Poi viene creato un oggetto di tipo AnchorPane chiamato
    //menu facendo riferimento e richiamando l'intefaccia definita in FXMLmenu.fxml.
    //Quindi menu sara' l'interfaccia definita in FXMLmenu.fxml.
    //Poi prende il nodo principale, borderPane,  e sostituisce tutti i figli con l'oggetto creato
    //precedentemente, ovvero con menu (quindi si tornera' alla schermata del menu' principale).
    //Se il bottone noButton viene premuto (quindi se non si e' sicuri di interrompere la simulazione)
    //viene semplicemente chiuso lo stage chiamato stageFinestra (con il metodo close).
    //Altrimenti, se non si e'Â¨ premuto start e viene premuto Menu, vengono presi tutti i colori che erano stati usati per
    //colorare le celle della nazioni e vengono riaggiunti alla lista ListaColori (che contiene' tutti i colori delle nazioni
    //che potranno essere scelti quando si crea una nuova nazione) cosida rendere nuovamenti i colori diponibili ed in seguito
    //viene cancellato tutto cio' che si trova dentro la lista nomiNazioni (che contiene' tutti i nomi delle nazioni che sono
    //state create) e viene cancellato tutto cio' che si trova dentro la lista nationList  (che conterra' tutte le nazioni che
    //sono state create) in maniera da non tenere memorizzate le nazioni che sono state create.
    //Poi viene creato un oggetto di tipo AnchorPane chiamato menu facendo riferimento e richiamando l'intefaccia definita in FXMLmenu.fxml.
    //Quindi menu sara' l'interfaccia definita in FXMLmenu.fxml.
    //Poi prende il nodo principale, borderPane,  e sostituisce tutti i figli con l'oggetto creato
    //precedentemente, ovvero con menu (quindi si tornera' alla schermata del menu' principale).
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
    //Quando si clicca su una cella mentre si stanno aggiungendo nazioni alla griglia, questo metodo assegna
    //alla cella cliccata il colore dell'ultima nazione inserita.
    //Se la variabile useStart e' true (cio' se e' gia' stato premuto start) non seccede nulla.
    //Se la lista chiamata nationList e' vuota, quindi se non e' stata creata nessuna nazione,
    //allora viene soltanto abilitato il bottone chiamato buttonAddNation e non succede nient'altro,
    //perche' se non abbiamo creato nessuna nazione e clicchiamo su una cella della griglia il programma
    //non deve fare nulla.
    //Altrimenti se abbiamo creato la nazione, controlla se quella cella non e' stata assegnata a nessuna nazione:
    //e se non e' stata assegnata (quindi se la cella su cui si clicca non fa gia' parte
    //di una nazione si assegna quella regione, quindi quella cella, alla nazione)
    //e verra' colorata in base al colore scelto.
    //Siccome siamo nel caso in cui e' stata creata almeno una nazione: viene assegnato questo controller (this) alla
    //nazione, cio' servira' per ricevere un avviso (notify()) da parte della nazione che ha finito il suo turno di gioco
    //e che quindi si puo' passare al turno della nazione successiva. Inoltre viene abilitato il bottone
    //buttonDeleteNation e viene abilitato anche il bottone buttonStart.
    //Applicando getSource() all'evento individuato (click sulla cella della griglia) si ottiene il bottone
    //su cui si e' verificato l'evento, ma viene restituito come un tipo Object e quindi applichiamo un cast
    //esplicito ((Regione)event.getSource())).
    //Per la cella su cui si clicca che rappresenta una Regione viene anche associata la Nazione
    //per la quale si sta assegnando territorio sulla griglia, richiamando il metodo getName della
    //classe Nation.
    //Inoltre assegnando una certa regione ad una nazione la nazione in base alle caratteristiche
    //della regione prende un certo numero di risorse e denaro e aumenta inoltre la sua popolazione.
    //Si va ad assegnare in maniera definitiva la cella alla nazione aggiungendo l'object Regione(la cella)
    //alla lista delle regioni possedute dalla nazione (si tratta di un array list chiamato regioni della classe Nation).
    //Inoltre, ogni volta che si clicca e quindi si colora una cella viene incrementata la variabile che tiene conto
    //del numero di celle utilizzate e se questo numero di celle utilizzate e' maggiore o uguale al prodotto
    //numero di righe per numero di colonne (indseriti dall'utente nelle apposite aree di testo)
    //allora significa che la griglia e' piena e che sono state usate tutte le celle per cui viene disabilitato il
    //bottone chiamato buttonAddNation, cosi che non e' piu' possibile inserire un'altra nazione.
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



    //METODO INITIALIZE
    //Questo metodo e' vuoto
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


}
