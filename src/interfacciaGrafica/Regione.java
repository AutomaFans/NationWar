package interfacciaGrafica;
import javafx.scene.control.Button;
import java.util.Random;
import java.util.ArrayList;

//Una Regione rappresenta un Button esteso
//Ovvero una Regione e' una cella che compone il territorio di una Nazione
public class Regione extends Button{

    private double risorse;       		//Numero di risorse naturali della regione
    private String nomeNazione;       	//Nome della nazione a cui appartiene la regione(inizialmente di nessuno)
    private String tipo;          		//Tipo di regione che puo' essere in base alle risorse di tipo sterile o fertile
    private double valore;        		//Valore in denaro de terreno (una nazione deve spendere una certa somma per colonizzare un terreno)
    private CellThread threadRegione; 	//Thread della regione
    private Nation nazione;       		//Nazione a cui appartiene la regione (la cella della griglia)
    private int numRow;                 //numero di riga in cui si trova la cella
    private int numColumn;              //numero di colonna in cui si trova la cella
    private ArrayList<Regione> regioniConfinantiAlleate = new ArrayList<>();    //regioni confinanti alla regione che fanno parte
                                                                                // della stessa nazione
    private ArrayList<Regione> regioniConfinantiSconosciute = new ArrayList<>();//regioni confinanti alla regione che non fanno
                                                                                // parte della stessa naione

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
    //Assegna poi un thread alla Regione che svolgera le azioni inerenti ad essa.
    //Infine valorizza le coordinate della regione nella griglia
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
        this.threadRegione = new CellThread(this); //Assegna un thread alla regione di tipo CellThread
        this.numRow = row;                               //Assegna numero di riga nella griglia
        this.numColumn = column;                         //Assegna numero di colonna nella griglia
    }

    //METODO RESET REGION
    //Permette di resettare la regione.
    //Ovvero ne aggiorna il valore, toglie il nome della nazione di appartenza e l'oggetto Nation su quella cella e
    // imposta lo sfondo di default.
    //Quindi aggiorna il tipo della regione, richiamando il metodo refreshType della classe
    //Regione.
    //In seguito se la regione e' di tipo fertile imposta lo sfondo con l'immagiine IMG-Fertile.jpg, mentre
    //se la regione e' di tipo sterile imposta lo sfondo della cella con l'immagione IMG-Sterile.
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
    //Restituisce il nome della nazione di appartenenza o manda un messaggio in console se la regione non appartiene a nessuno
    public String getNomeNazione() {
        return this.nomeNazione;
    }

    //METODO GET NAZIONE
    //Restituisce l'oggetto Nation che rappresenta la nazione a cui appartiene la regione
    public Nation getNazione(){
        return this.nazione;
    }

    //METODO GET TIPO
    //Restituisce il tipo di regione(fertile o sterile)
    public String getTipo() {
        return this.tipo;
    }

    //METODO SET NAZIONE
    //Permette di assegnare alla regione la nazione di appartenenza passata come parametro e il colore della nazione. Assegna inoltre
    // l'oggetto nazione stesso utile per alcune operazioni(sara' utile ad esempio con i thread)
    // Metodo usato quando si assegna una regione ad una nazione durante le impostazioni iniziali e durante la simulazione quando
    // una nazione conquista un territorio.
    //Alla regione viene applicato setStyle che serve per applicare una propieta' css all'oggetto in questione, in questo caso viene
    //applicato un background color e cioe' il colore di sfondo che sara' quello passato al metodo. Nel caso in cui si sta impostando
    //la griglia si tratta de colore dell'ultima nazione inserita nel sistema.
    public void setNazione(String nationName, String colore, Nation naz){
        this.nomeNazione = nationName;
        this.setStyle("-fx-background-color: " + colore);
        this.nazione = naz;
    }

    //METODO REFRESH TYPE
    //Setta il terreno al suo tipo di stato attuale(fertile o sterile)
    //se il numero  di risorse e' maggiore uguale a 350 allora la regione e' fertile
    //per cui viene impostato il tipo = fertile, altrimenti se il numero di risorse ÃƒÆ’Ã‚Â¨ minore
    //di 350 allora la regione ÃƒÆ’Ã‚Â¨ sterile per cui viene impostato il tipo = sterile
    public void refreshType(){
        if(risorse >= 350.0){                   //Se il numero di risorse e' maggiore uguale a 350
            tipo = "fertile";					//Allora la regione e' fertile
        }
        else{									//Altrimenti, se il numero di risorse e' minore di 350
            tipo = "sterile";                  //Allora la regione e' sterile
        }
    }


    //METODO CONSUMA RISORSE
    //Viene sottratto un quinto del numero di risorse ad ogni fase di gioco che interessa la nazione: cio' succede nel
    //momento in cui una nazione occupa questa regione e quindi consuma le risorse.
    public void consumaRisorse(){
        this.risorse = risorse - (risorse / 5); //viene consumato un quinto delle risorse
        this.refreshType();                     //viene aggiornato lo stato attuale della regione(fertile o sterile)
    }

    //METODO SET VALORE
    //Metodo per settare il valore reale in denaro della regione
    //Il valore cambiera' in base al numero di risorse e di righe e colonne della griglia.
    //Se si ha una griglia abbastanza grande il valore sara' piu' basso per permettere
    //una maggiore espansione nella tabella, per cui il valore sarÃƒÆ’Ã‚Â  la meta'' delle risorse
    //(risorse/2).
    //Altrimenti, se si ha una griglia di piccole dimensioni il valore sara' piu' alto
    //per contenere l'espansione, per cui il valore e' uguale alle risorse (valore = risorse).
    public void setValore(int righe, int colonne){
        if(righe > 20 && colonne > 20){          	//Se il numero di righe e di colonne della griglia ÃƒÆ’Ã‚Â¨ maggiore di 20
            this.valore = risorse / 2;				//Il valore e' uguale alla meta' delle risorse
        }
        else{                                     	//Altrimenti, se il numero di righe e di colonne della griglia ÃƒÆ’Ã‚Â¨ minore o uguale di 20
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
    //Una volta eseguito il run del suo thread non sara' piu' possibile startarlo altrimenti si otterra' un eccezione,
    //per questo il metodo assegna un nuovo thread alla regione cosi' che sia possibile fare di nuovo lo start() del suo thread
    public void setNewThread(){
        this.threadRegione = new CellThread(this);
    }

    //METODO REFRESH NEIGHBORING REGIONS
    //Aggiorna le 2 liste che rappresentano le regioni confinanti e alleate con la regione, e quelle che confinano con
    //essa ma non sono alleate
    public void refreshNeighboringRegions(){
        //Prendo la regione che confina in alto con questa regione "this"
        Regione one = (Regione)this.nazione.getGridController().getNodeFromGridPane(this.nazione.getGridController().getGridPane(), numColumn, numRow-1);
        if(one != null){ //Se questa regione esiste(puo' esserci il caso in cui la regione per la quale si vogliono vedere i confinanti sia una
                         //cella al bordo in alto e quindi una cella sopra non esisterebbe)
            if(one.getNomeNazione().equals(this.getNomeNazione())){  //Se la regione confinante fa parte della stessa nazione della
                                                                     //regione per la quale vediamo le confinanti
                this.regioniConfinantiAlleate.add(one);              //Allora la aggiungiamo a le confinanti alleate
            }
            else{
                this.regioniConfinantiSconosciute.add(one);          //Altrimenti viene aggiunta alle confinanti non alleate
            }
        }
        //Prendo la regione che confina a destra con questa regione "this"
        Regione two = (Regione)this.nazione.getGridController().getNodeFromGridPane(this.nazione.getGridController().getGridPane(), numColumn+1, numRow);
        if(two != null){
            if(two.getNomeNazione().equals(this.getNomeNazione())){
                this.regioniConfinantiAlleate.add(two);
            }
            else{
                this.regioniConfinantiSconosciute.add(two);
            }
        }
        //Prendo la regione che confina in basso con questa regione "this"
        Regione three = (Regione)this.nazione.getGridController().getNodeFromGridPane(this.nazione.getGridController().getGridPane(), numColumn, numRow+1);
        if(three != null){
            if(three.getNomeNazione().equals(this.getNomeNazione())){
                this.regioniConfinantiAlleate.add(three);
            }
            else{
                this.regioniConfinantiSconosciute.add(three);
            }
        }
        //Prendo la regione che confina a sinistra con questa regione "this"
        Regione four = (Regione)this.nazione.getGridController().getNodeFromGridPane(this.nazione.getGridController().getGridPane(), numColumn-1, numRow);
        if(four != null){
            if(four.getNomeNazione().equals(this.getNomeNazione())){
                this.regioniConfinantiAlleate.add(four);
            }
            else{
                this.regioniConfinantiSconosciute.add(four);
            }
        }
    }

    //METODO GET REGIONI CONFINANTI ALLEATE
    public ArrayList<Regione> getRegioniConfinantiAlleate(){
        return regioniConfinantiAlleate;
    }

    //METODO GET REGIONI CONFINANTI SCONOSCIUTE
    public ArrayList<Regione> getRegioniConfinantiSconosciute(){
        return regioniConfinantiSconosciute;
    }
}
