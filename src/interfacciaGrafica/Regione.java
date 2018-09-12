package interfacciaGrafica;
import javafx.application.Platform;
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
    public void resetRegion(){
        this.setValore(nazione.getGridController().getNumeroRighe(), nazione.getGridController().getNumeroColonne());
        if(alleanza != null){
            this.nazione.interrompiAlleanza(alleanza);
        }
        this.nomeNazione = "";
        this.nazione = null;
        this.refreshType();
        //Resetta lo sfondo in base al suo tipo(sterile o fertile) e togliendo il colore della nazione
        if(tipo.equals("fertile")){
            Platform.runLater(
                    () -> {
                        this.setStyle("-fx-background-image: url('/interfacciaGrafica/IMG-Fertile.jpg')");
                    }
            );
        }
        else{
            Platform.runLater(
                    () -> {
                        this.setStyle("-fx-background-image: url('/interfacciaGrafica/IMG-Sterile.jpg')");
                    }
            );
        }
        Platform.runLater(
                () -> {
                    this.setText("");
                }
        );
    }



    //METODO GET ROW
    public int getRow(){
        return this.numRow;
    }


    //METODO GET COLUMN
    public int getColumn(){
        return this.numColumn;
    }


    //METODO GET RISORSE
    public double getRisorse() {
        return this.risorse;
    }


    //METODO GET NOME NAZIONE
    public String getNomeNazione() {
        return this.nomeNazione;
    }


    //METODO GET NAZIONE
    public Nation getNazione(){
        return this.nazione;
    }


    //METODO GET TIPO
    public String getTipo() {
        return this.tipo;
    }


    //METODO SET NAZIONE
    public void setNazione(String nationName, String colore, Nation naz){
        this.nomeNazione = nationName;
        Platform.runLater(
                () -> {
                    this.setStyle("-fx-background-color: " + colore);
                }
        );
        this.nazione = naz;
    }


    //METODO REFRESH TYPE
    public void refreshType(){
        if(risorse >= 350.0){                   //Se il numero di risorse e' maggiore uguale a 350
            tipo = "fertile";					//Allora la regione e' fertile
        }
        else{									//Altrimenti, se il numero di risorse e' minore di 350
            tipo = "sterile";                  //Allora la regione e' sterile
        }
    }


    //METODO CONSUMA RISORSE
    public void consumaRisorse(){
        this.risorse = risorse - (risorse / 5);
        this.refreshType();

    }


    //METODO SET VALORE
    public void setValore(int righe, int colonne){
        if(righe > 10 && colonne > 10){          	//Se il numero di righe e di colonne della griglia e' maggiore di 10
            this.valore = risorse / 2;				//Il valore e' uguale alla meta' delle risorse
        }
        else{                                     	//Altrimenti, se il numero di righe e di colonne della griglia e' minore o uguale di 10
            this.valore = risorse;				   	//Il valore e' uguale alle risorse
        }
    }



    //METODO GET VALORE
    public double getValore(){
        return this.valore;
    }



    //METODO START REGION THREAD
    public void startRegionThread(){
        this.threadRegione.start();
    }



    //METODO SET NEW THREAD
    public void setNewThread(){
        this.threadRegione = new CellThread(this);
    }



    //METODO CREATE POP
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
    public ArrayList<Regione> getRegioniConfinantiAlleate(){
        return regioniConfinantiAlleate;
    }



    //METODO GET REGIONI CONFINANTI SCONOSCIUTE
    public ArrayList<Regione> getRegioniConfinantiSconosciute(){
        return regioniConfinantiSconosciute;
    }



    //METODO GET ALLEANZA
    public Accordo getAlleanza(){
        return alleanza;
    }



    //METODO ROMPI PATTO
    public void rompiPatto(){
        this.alleanza = null;
    }
}
