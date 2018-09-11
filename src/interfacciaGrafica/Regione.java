package interfacciaGrafica;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;

import java.util.Random;
import java.util.ArrayList;

import static interfacciaGrafica.ControllerImpostazioniGriglia.useButton;

//Una Regione rappresenta un Button esteso
//Ovvero una Regione e' una cella che compone il territorio di una Nazione
public class Regione extends Button{

    private double risorse;       		//Numero di risorse naturali della regione
    private String nomeNazione;       	//Nome della nazione a cui appartiene la regione(inizialmente di nessuno)
    private String tipo;          		//Tipo di regione che puo' essere in base alle risorse di tipo sterile o fertile
    private double valore;        		//Valore in denaro de terreno (una nazione deve spendere una certa somma per colonizzare un terreno)
    private CellThread threadRegione; 	//Thread della regione
    private Nation nazione;       		//Nazione a cui appartiene la regione (la cella della griglia)
    private Accordo alleanza;           //Booleano che e' a true se il territorio e' utilizzato per un'alleanza
    private int numRow;                 //numero di riga in cui si trova la cella
    private int numColumn;              //numero di colonna in cui si trova la cella

    //Lista di Regioni chiamata regioniConfinantiAlleate che contiene
    //tutte le regioni confinanti alla regione che fanno parte della stessa nazione
    private ArrayList<Regione> regioniConfinantiAlleate = new ArrayList<>();

    //Lista di Regioni chiamata regioniConfinantiSconosciute che contiene
    //tutte le regioni confinanti alla regione che non fanno parte della stessa nazione
    private ArrayList<Regione> regioniConfinantiSconosciute = new ArrayList<>();


    //COSTRUTTORE CN DUE PARAMETRI
    //Il costruttore prende il numero di riga e di colonna in cui si trova la cella(regione) nella griglia
    //Genera un nuovo oggetto di tipo Random(randomico), per cui
    //- assegna alle risorse un numero casuale compreso tra 0 e 1000
    //- la nazione di appartenenza inizialmente e' nulla perche' quando generato il territorio non e' di nessuno
    //Inoltre se il numero  di risorse e' maggiore uguale a 350 allora la regione e' fertile
    //per cui viene impostato il tipo = fertile, altrimenti se il numero di risorse e' minore
    //di 350 allora la regione e' sterile per cui viene impostato il tipo = sterile
    //Poi in base al tipo di territorio viene impostato lo sfondo della cella della griglia.
    //Se il tipo e' fertile allora lo sfondo della cella sara' l'immagine IMG-Fertile.jpg,
    //alrimenti, se il tipo e' sterile allora lo sfondo della cella sara' l'immagine
    //IMG-Sterile.jpg.
    //Il valore reale verra' valorizzato al momento in cui si aggiunge la regione alla griglia.
    //Assegna poi un thread alla Regione (richiammando il costruttore con un parametro della classe
    //CellThread) che svolgera le azioni inerenti ad essa.
    //Infine valorizza le coordinate della regione nella griglia e il la variabile che mi dice se la regione
    //ha stretto un'alleanzae e siccome inizialmente la regione non fa parte di nessuna allenaza,
    //la variabile booleana alleanza viene settata a null
    public Regione(int row, int column){
        Random rand = new Random();         //Genera un nuovo oggetto di tipo Random(randomico)
        risorse = rand.nextInt(1000);     	//Assegna alle risorse un numero casuale compreso tra 0 e 1000
        nomeNazione = "";                   //La nazione di appartenenza inizialmente e' vuota perche' quando generato il territorio non e' di nessuno
        nazione = null;
        //IN BASE ALLE RISORSE VIENE IMPOSTATO IL TIPO DI TERRITOPRIO (FERTILLE O STERILE)
        if(risorse >= 350.0){               //Se il numero di risorse e' maggiore uguale a 350
            tipo = "fertile";				//Allora la regione e' fertile
        }
        else{								//Altrimenti, se il numero di risorse e' minore di 350
            tipo = "sterile";               //Allora la regione e' sterile
        }
        //IMPOSTA LO SFONDO DELLA CELLA IN BASE AL TIPO DI TERRITORIO
        if(tipo.equals("fertile")){
            this.setStyle("-fx-background-image: url('/interfacciaGrafica/IMG-Fertile.jpg')");
        }
        else{
            this.setStyle("-fx-background-image: url('/interfacciaGrafica/IMG-Sterile.jpg')");
        }
        this.valore = 0.0;
        this.threadRegione = new CellThread(this); 		 //Assegna un thread alla regione di tipo CellThread
        this.numRow = row;                               //Assegna numero di riga nella griglia
        this.numColumn = column;                         //Assegna numero di colonna nella griglia
        this.alleanza = null;                            //Inizialmente la regione non fa parte di nessuna alleanza
    }



    //METODO RESET REGION
    //Permette di resettare la regione.
    //Ovvero ne aggiorna il valore, toglie il nome della nazione di appartenza e l'oggetto Nation su quella cella e
    // imposta lo sfondo di default.
    //Quindi aggiorna il tipo della regione, richiamando il metodo refreshType della classe
    //Regione.
    //In seguito se la regione e' di tipo fertile imposta lo sfondo con l'immagiine IMG-Fertile.jpg, mentre
    //se la regione e' di tipo sterile imposta lo sfondo della cella con l'immagione IMG-Sterile.
    //inoltre se la regione faceva parte di una allenaza, allora viene sciolta ancjhe l'alleanza su quella
    //regione per cui viene impostata la variabile booleana alleanza a null
    public void resetRegion(){
        this.setValore(nazione.getGridController().getNumeroRighe(), nazione.getGridController().getNumeroColonne());
        this.nomeNazione = "";
        this.nazione = null;
        this.refreshType();
        //Resetta lo sfondo in base al suo tipo(sterile o fertile) e togliendo il colore della nazione
        if(tipo.equals("fertile")){
            this.setStyle("-fx-background-image: url('/interfacciaGrafica/IMG-Fertile.jpg')");
        }
        else{
            this.setStyle("-fx-background-image: url('/interfacciaGrafica/IMG-Sterile.jpg')");
        }
        this.alleanza = null;    //Se la regione faceva parte di un'accordo quell'accordo viene sciolto
    }



    //METODO GET ROW
    //Restituisce il numero di riga in cui la regione si trova nella griglia
    public int getRow(){
        return this.numRow;
    }



    //METODO GET COLUMN
    //Restituisce il numero di colonna in cui la regione si trova nella griglia
    public int getColumn(){
        return this.numColumn;
    }



    //METODO GET RISORSE
    //Restituisce il numero di risorse naturali attuali della nazione
    public double getRisorse() {
        return this.risorse;
    }



    //METODO GET NOME NAZIONE
    //Restituisce il nome della nazione di appartenenza o manda un messaggio in console
    //se la regione non appartiene a nessuno
    public String getNomeNazione() {
        return this.nomeNazione;
    }



    //METODO GET NAZIONE
    //Restituisce l'oggetto di tipo Nation che rappresenta la nazione a cui
    //appartiene la regione.
    public Nation getNazione(){
        return this.nazione;
    }



    //METODO GET TIPO
    //Restituisce il tipo di regione(fertile o sterile)
    public String getTipo() {
        return this.tipo;
    }



    //METODO SET NAZIONE
    //Questo metodo prende come parametri una stringa nationName che rappresenta il nome
    //della nazione, una stringa color che rappresenta il colore da dare alla regione, e
    //un oggetto naz di tipo Nazione che rappresenta la nazione che sara assegnata alla regione.
    //Queso metodo permette di assegnare alla regione la nazione di appartenenza presa come
    //parametro e il colore della nazione preso come parametro.
    //Assegna inoltre l'oggetto nazione stesso (utile per alcune operazioni come ad esempio con i thread)
    //Il metodo viene richiamato quando si assegna una regione ad una nazione durante le
    //impostazioni iniziali e durante la simulazione quando una nazione conquista un territorio.
    //Alla regione viene applicato setStyle che serve per applicare una propieta' css all'oggetto in questione, in questo caso viene
    //applicato un background color e cioe' il colore di sfondo che sara' quello passato al metodo. Nel caso in cui si sta impostando
    //la griglia si tratta de colore dell'ultima nazione inserita nel sistema.
    public void setNazione(String nationName, String colore, Nation naz){
        this.nomeNazione = nationName;
        this.setStyle("-fx-background-color: " + colore);
        this.nazione = naz;
    }



    //METODO REFRESH TYPE
    //Setta il tipo di terreno (fertile o sterile) in base al suo stato attuale.
    //Se il numero  di risorse e' maggiore uguale a 350 allora la regione e' fertile
    //per cui viene impostato il tipo = fertile, altrimenti se il numero di risorse e' minore
    //di 350 allora la regione e' sterile per cui viene impostato il tipo = sterile
    public void refreshType(){
        if(risorse >= 350.0){                   //Se il numero di risorse e' maggiore uguale a 350
            tipo = "fertile";					//Allora la regione e' fertile
        }
        else{									//Altrimenti, se il numero di risorse e' minore di 350
            tipo = "sterile";                  //Allora la regione e' sterile
        }
    }



    //METODO CONSUMA RISORSE
    //Viene sottratto un quinto del numero di risorse ad ogni turno di gioco che interessa
    //la nazione, cio' succede perche' siccome una nazione occupa la regione allora le
    //risorse vengono usate e quindi consumate.
    //inoltreviene anche richiamato il metodo refreshType della classe Regione, per aggiornare
    //il tipo di terreno (fertile o sterile) dopo aver consumato le risorse.
    public void consumaRisorse(){
        this.risorse = risorse - (risorse / 5);
        this.refreshType();

    }



    //METODO SET VALORE
    //Questo metodo prende come parametri un intero che rappresenta il numero di righe e un
    //intero che rappresenta il numero di colonne.
    //Questo metodo serve per settare il valore reale in denaro della regionee questo
    //valore cambiera' in base al numero di risorse e di righe e colonne della griglia.
    //Se si ha una griglia abbastanza grande, cioe' se si hanno piu' di 10 righe e piu'
    //di 10 colonne, il valore sara' piu' basso per permettere una maggiore espansione
    //nella tabella, per cui il valore sara' la meta' delle risorse (risorse/2).
    //Altrimenti, se si ha una griglia di piccole dimensioni il valore sara' piu' alto
    //per contenere l'espansione, per cui il valore e' uguale alle risorse (valore = risorse).
    public void setValore(int righe, int colonne){
        if(righe > 10 && colonne > 10){          	//Se il numero di righe e di colonne della griglia e' maggiore di 10
            this.valore = risorse / 2;				//Il valore e' uguale alla meta' delle risorse
        }
        else{                                     	//Altrimenti, se il numero di righe e di colonne della griglia e' minore o uguale di 10
            this.valore = risorse;				   	//Il valore e' uguale alle risorse
        }
    }



    //METODO GET VALORE
    //Ritorna il valore in denaro dela regione
    public double getValore(){
        return this.valore;
    }



    //METODO START REGION THREAD
    //Esegue lo start del thread su quella regione
    public void startRegionThread(){
        this.threadRegione.start();
    }



    //METODO SET NEW THREAD
    //Una volta eseguito il run del suo thread non sara' piu' possibile startarlo altrimenti
    //si otterra' un eccezione, per questo il metodo assegna un nuovo thread alla regione
    //cosi' che e' possibile fare di nuovo lo start() del suo thread
    public void setNewThread(){
        this.threadRegione = new CellThread(this);
    }



    //METODO CREATE POP
    //Qusto metodo prende come parametri una Regione r e serve per creare un oggetto di tipo
    //PopOver relativo alla Regione presa come parametro.
    //Quindi ogni volta che viene aggiunto un nuovo bottone alla griglia viene anche creato
    //il popover corrispondente.
    //Per farlo viene viene creata una label, chiamata risorseRegione e viene impostato il testo di
    //questa label con il valore delle risorse relative a quella specifica regione (cioe' quella
    //cella quante risorse ha).
    //Poi viene creata un'altra label, chiamata tipoRegione e viene impostato il testo di questa
    //label con il tipo di terreno (fertile o sterile) relativo a quella specifica regione (cioe' il
    //terreno di quella cella se e' fertile o sterile).
    //Poi viene creata un'altra label, chiamata valoreRegione e viene impostato il testo di questa
    //label con il denaro relative a quella specifica regione (cioe' quella cella quanto denaro ha).
    //In seguito viene creato un VBox chiamato VBox (per disporre i componenti verticalmente, in questo caso i
    //componenti sono le dtree label) e aggiunge a VBox prima la label risorseRegione poi la label tipoRegione
    //ed infine la label valoreRegione
    //Poi viene creato un PopOver chiamato pop del  VBOx sopra creato, e questo PopOver mostrera'
    //risorse disponibili, il tipo di territorio e il suo valore in denaro (dati relativi alla
    //regione su cui viene creato).
    //Poi viene impostata l'azione che quando si passa il mouse sopra una regione (una cella) allora
    //viene visualizzato il PopOver (cioe' il PopOver viene visualizzato solo quando si passail mouse sopra una cella ).
    //Allo stesso modo viene impostata l'azione che quando si sposta il mouse allora viene chiuso il PopOver.
    //infine ritorna pop
    public static PopOver createPop(Regione r){
        Label risorseRegione = new Label();											//Si crea una Label  chiamata appartenenzaNazione
        risorseRegione.setText("Risorse disponibili: " + (int)r.getRisorse());			//Setta il testo della label con il valore delle risorse relative a quella specifica regione
        Label tipoRegione = new Label();											//Si crea una Label  chiamata tipoRegione
        tipoRegione.setText("Regione: " + r.getTipo());								//Setta il testo della label con il tipo di terreno relativo a quella specifica regione
        Label valoreRegione = new Label();											//Si crea una Label chiamata valoreRegione
        valoreRegione.setText("Valore in denaro: " + (int)r.getValore());				//Setta il testo della label con il denaro relativo a quella specifica regione
        VBox vBox = new VBox(risorseRegione,tipoRegione,valoreRegione);				//Si crea un VBox che contiene le tre label create precedentemente
        PopOver pop = new PopOver(vBox);											//Si crea un PopOver chiamato pop
        //IL POPOVER VERRA' VISUALIZZATO QUANDO SI PASSA IL MOUSE SOPRA UNA REGIONE (CELLA)
        r.setOnMouseEntered(MouseEvent -> {
            if(useButton == false) {
                pop.show(r);
            }
        });
        //IL POPOVER SI CHIDERA' QUANDO SI SPOSTA IL MOUSE DALLA REGIONE (CELLA)
        r.setOnMouseExited(MouseEvent -> {
            if(useButton == false) {
                pop.hide();
            }
        });
        return pop;
    }



    //METODO REFRESH NEIGHBORING REGIONS
    //Questo metodo aggiorna le 2 liste: la lista chiamata regioniConfinantiAlleate (che contiene
    //le regioni confinanti e alleate con la regione), e la lista regioniConfinantiSconosciute
    //(che contiene le regioni confinanti e non alleate con la regione).
    //Viene presa la regione che confina in alto con questa regione "this" e viene messa dentro
    //la variabile di tipo Regione chiamata one.
    //Se la regione one esiste (perchÃƒÆ’Ã‚Â¨ puo' esserci il caso in cui la regione per la quale
    //si vogliono vedere i confinanti sia una cella al bordo in alto e quindi una cella
    //sopra non esisterebbe) si va a vedere se la regione confinante fa parte della stessa
    //nazone per la quale stamo vedendo i confinanti, allora in tal caso viene aggiunta
    //alla lista delle regioni confinanti allete, altrimenti se la regione confinante non fa
    //parte della stessa nazone per la quale stamo vedendo i confinanti, allora in tal caso
    //viene aggiunta alla lista delle regioni confinanti non allete.
    //Poi viene presa la regione che confina a destra con questa regione "this" e viene messa
    //dentro la variabile di tipo Regione chiamata two.
    //Se la regione two esiste (perchÃƒÆ’Ã‚Â¨ puo' esserci il caso in cui la regione per la quale
    //si vogliono vedere i confinanti sia una cella al bordo a destra e quindi una cella
    //a destra non esisterebbe) si va a vedere se la regione confinante fa parte della stessa
    //nazone per la quale stamo vedendo i confinanti, allora in tal caso viene aggiunta
    //alla lista delle regioni confinanti allete, altrimenti se la regione confinante non fa
    //parte della stessa nazone per la quale stamo vedendo i confinanti, allora in tal caso
    //viene aggiunta alla lista delle regioni confinanti non allete.
    //Poi viene presa la regione che confina in basso con questa regione "this" e viene messa
    //dentro la variabile di tipo Regione chiamata three.
    //Se la regione three esiste (perchÃƒÆ’Ã‚Â¨ puo' esserci il caso in cui la regione per la quale
    //si vogliono vedere i confinanti sia una cella al bordo in basso e quindi una cella
    //al di sotto non esisterebbe) si va a vedere se la regione confinante fa parte della stessa
    //nazone per la quale stamo vedendo i confinanti, allora in tal caso viene aggiunta
    //alla lista delle regioni confinanti allete, altrimenti se la regione confinante non fa
    //parte della stessa nazone per la quale stamo vedendo i confinanti, allora in tal caso
    //viene aggiunta alla lista delle regioni confinanti non allete.
    //Poi viene presa la regione che confina a sinistra con questa regione "this" e viene messa
    //dentro la variabile di tipo Regione chiamata four.
    //Se la regione four esiste (perchÃƒÆ’Ã‚Â¨ puo' esserci il caso in cui la regione per la quale
    //si vogliono vedere i confinanti sia una cella al bordo a sinistra e quindi una cella
    //a sinistra non esisterebbe) si va a vedere se la regione confinante fa parte della stessa
    //nazone per la quale stamo vedendo i confinanti, allora in tal caso viene aggiunta
    //alla lista delle regioni confinanti allete, altrimenti se la regione confinante non fa
    //parte della stessa nazone per la quale stamo vedendo i confinanti, allora in tal caso
    //viene aggiunta alla lista delle regioni confinanti non allete
    public void refreshNeighboringRegions(){
        //PRENDE LA REGIONE CHE CONFINA IN ALTO CON QUESTA REGIONE "THIS"
        Regione one = (Regione)this.nazione.getGridController().getNodeFromGridPane(this.nazione.getGridController().getGridPane(), numColumn, numRow-1);
        //SE QUESTA REGIONE ESISTE
        if(one != null){
            //SE LA REGIONE CONFINANTE FA PARTE DELLA STESSA NAZIONE DELLA REGIONE PER LA
            //QUALE STIAMO VEDENDO I TERRITORI CONFINANTI
            if(one.getNomeNazione().equals(this.getNomeNazione())){
                this.regioniConfinantiAlleate.add(one);
            }
            //SE LA REGIONE CONFINANTE NON FA PARTE DELLA STESSA NAZIONE DELLA REGIONE PER LA
            //QUALE STIAMO VEDENDO I TERRITORI CONFINANTI
            else{
                this.regioniConfinantiSconosciute.add(one);
            }
        }
        //PRENDE LA REGIONE CHE CONFINA A DESTRA CON QUESTA REGIONE "THIS"
        Regione two = (Regione)this.nazione.getGridController().getNodeFromGridPane(this.nazione.getGridController().getGridPane(), numColumn+1, numRow);
        if(two != null){
            //SE LA REGIONE CONFINANTE FA PARTE DELLA STESSA NAZIONE DELLA REGIONE PER LA
            //QUALE STIAMO VEDENDO I TERRITORI CONFINANTI
            if(two.getNomeNazione().equals(this.getNomeNazione())){
                this.regioniConfinantiAlleate.add(two);
            }
            //SE LA REGIONE CONFINANTE NON FA PARTE DELLA STESSA NAZIONE DELLA REGIONE PER LA
            //QUALE STIAMO VEDENDO I TERRITORI CONFINANTI
            else{
                this.regioniConfinantiSconosciute.add(two);
            }
        }
        //PRENDE LA REGIONE CHE CONFINA IN BASSO CON QUESTA REGIONE "THIS"
        Regione three = (Regione)this.nazione.getGridController().getNodeFromGridPane(this.nazione.getGridController().getGridPane(), numColumn, numRow+1);
        if(three != null){
            //SE LA REGIONE CONFINANTE FA PARTE DELLA STESSA NAZIONE DELLA REGIONE PER LA
            //QUALE STIAMO VEDENDO I TERRITORI CONFINANTI
            if(three.getNomeNazione().equals(this.getNomeNazione())){
                this.regioniConfinantiAlleate.add(three);
            }
            //SE LA REGIONE CONFINANTE NON FA PARTE DELLA STESSA NAZIONE DELLA REGIONE PER LA
            //QUALE STIAMO VEDENDO I TERRITORI CONFINANTI
            else{
                this.regioniConfinantiSconosciute.add(three);
            }
        }
        //PRENDE LA REGIONE CHE CONFINA A SINISTRA CON QUESTA REGIONE "THIS"
        Regione four = (Regione)this.nazione.getGridController().getNodeFromGridPane(this.nazione.getGridController().getGridPane(), numColumn-1, numRow);
        if(four != null){
            //SE LA REGIONE CONFINANTE FA PARTE DELLA STESSA NAZIONE DELLA REGIONE PER LA
            //QUALE STIAMO VEDENDO I TERRITORI CONFINANTI
            if(four.getNomeNazione().equals(this.getNomeNazione())){
                this.regioniConfinantiAlleate.add(four);
            }
            //SE LA REGIONE CONFINANTE NON FA PARTE DELLA STESSA NAZIONE DELLA REGIONE PER LA
            //QUALE STIAMO VEDENDO I TERRITORI CONFINANTI
            else{
                this.regioniConfinantiSconosciute.add(four);
            }
        }
    }



    //METODO GET REGIONI CONFINANTI ALLEATE
    //Restituisce la lista regioniConfinantiAlleate (che contiene le regioni
    //confinanti e alleate con la regione)
    public ArrayList<Regione> getRegioniConfinantiAlleate(){
        return regioniConfinantiAlleate;
    }



    //METODO GET REGIONI CONFINANTI SCONOSCIUTE
    //Restituisce la lista regioniConfinantiSconosciute (che contiene le regioni
    //confinanti e non alleate con la regione)
    public ArrayList<Regione> getRegioniConfinantiSconosciute(){
        return regioniConfinantiSconosciute;
    }



    //METODO GET ALLEANZA
    //Restituisce l'alleanza stretta sulla regione, ammesso che ne sia stata stretta una
    public Accordo getAlleanza(){
        return alleanza;
    }



    //METODO ROMPI PATTO
    //Permette di annullare un patto di alleanza stretto su una regione
    public void rompiPatto(){
        this.alleanza = null;
    }
}
