package interfacciaGrafica;
import java.util.ArrayList;
import java.util.Iterator;

//Ogni nazione e' composta dal nome, dal colore, dall'eta', dal denaro, dalle risorse e dal numero di abitanti.
//Il colore sara' utilizzato per colorare i bottoni nella griglia.
public class Nation {

    private String nome;					//Stringa nome per il nome della nazione
    private String color;				    //Stringa color peril colore della nazione
    private Eta age;                        //age conterra' l'eta' in cui si trova la nazione
    private double denaro;                  //denaro corrente della nazione
    private double risorse;                 //risorse naturali della nazione
    private int numAbitanti;                //numero di abitanti della nazione

    //Lista di regioni che rappresentano le celle assegnate e conquistate dalla nazione
    //Quindi ogni nazione avra i suoi territori e questa lista contiene i territori (le celle)
    //posseduti dalla nazione
    private  ArrayList<Regione> regioni = new ArrayList<>();


    //COSTRUTTORE CON DUE PARAMETRI
    public Nation(String nome, String color){
        this.nome = nome;
        this.color = color;
        this.age = Eta.ANTICA;              //L'eta' di default della nazione e' antica, e antica viene
        //scelta dal dominio enumerativo di "Eta"
        this.denaro = 0.0;                  //Di default denaro e risorse sono a 0 e assumono
        //un valore iniziale in base alle celle(pezzi di territorio) assegnati
        //sulla griglia
        this.risorse = 0.0;
        this.numAbitanti = 1;
    }



    //METODO GETCOLOR()
    //Metodo che restituisce il colore della nazione sulla quale viene chiamato il metodo
    public String getColor(){
        return this.color;
    }

    //METODO GET NAME
    //Metodo che restituisce il nome della nazione sulla quale viene chiamato il metodo
    public String getName() {
        return this.nome;
    }

    //METODO GET AGE
    //Restituisce l'eta' attuale della nazione che puo' essere antica, intermedia o moderna
    public Eta getAge(){
        return this.age;
    }

    //METODO GET DENARO
    //Restituisce la quantita' di denaro attuale presente nelle tesorerie della nazione
    public double getDenaro(){
        return this.denaro;
    }

    //METODO GET RISORSE
    //Restituisce il numero di risorse naturali attuali della nazione
    public double getRisorse(){
        return this.risorse;
    }

    //METODO GET NUM ABITANTI
    //restituisce il numero di abitanti della nazione
    public int getNumAbitanti(){
        return this.numAbitanti;
    }

    //METODO REFRESH AGE
    //Metodo che permette di aggiornare l'eta' attuale della nazione.
    //Se le risorse della nazione sono minori di 3000, il numero di abitanti della nazione
    //sono minori di 1000 e il denaro della nazione e' minore di 5000 allora
    //viene impostata l'eta' della nazione come eta' antica.
    //Altrimenti se le risorse della nazione sono maggiori o uguali di 3000 e minori di 5000,
    //il numero di abitanti della nazione sono maggiori o uguali di 1000 e minori di 2000 e
    //il denaro della nazione e' maggiore o uguale di 5000 e minore di 10000 allora
    //viene impostata l'eta' della nazione come eta' intermedia.
    //Altrimenti le risorse della nazione sono maggiori o uguali di 5000, il numero
    //di abitanti della nazione sono maggiori o uguali di 2000 e il denaro dellanazione
    //e' maggiore o uguale a 10000 allora vine impostata l'eta' della nazion come
    //erta' moderna.
    public void refreshAge(){
        if(this.risorse < 3000 && this.numAbitanti < 1000 && this.denaro < 5000){
            this.age = Eta.ANTICA;
        }
        else if((this.risorse >= 3000 && this.numAbitanti >= 1000 && this.denaro >= 5000) &&
                (this.risorse < 5000 && this.numAbitanti < 2000 && this.denaro < 10000)){
            this.age = Eta.INTERMEDIA;
        }
        else{
            this.age = Eta.MODERNA;
        }
    }


    //METODO CONSUMA RISORSE
    //Viene sottratto un decimo del numero di risorse ad ogni fase di gioco che interessa la nazione: cio' succede perche'
    // la nazione occupa una certa regione e quindi consuma le risorse.
    public void consumaRisorse(){
        this.risorse = risorse - (risorse / 10); //Viene consumato un decimo delle risorse
        //this.refreshAge();                     //Viene aggiornata l'eta' attuale della nazione(antica, intermedia, moderna)
    }

    //METODO INCASSA DENARO
    //Aumenta il denaro della nazione in relazione al numero delle risorse totali e degli abitanti. Metodo utilizzato
    //ad ogni fase di azione della nazione
    public void incassaDenaro(){
        this.denaro = (risorse / 2.0) + (numAbitanti*2.0);
    }

    //METODO TAKE PROFIT
    //Metodo per aumentare il numero di abitanti, il denaro e le risorse della nazione in base al tipo di regione assegnata
    //durante l'assegnazione di territori nella fase di impostazione della griglia.
    //Utilizzato nel caso in cui si assegna una regione alla nazione nelle impostazioni iniziali.
    public void takeProfit(String tipoRegione, Double risorseRegione){
        if(tipoRegione.equals("fertile")){ //se la regione e' fertile aumenta il numero di abitanti di 100
            this.numAbitanti += 100;
        }
        else{                              //se la regione e' sterile aumenta il numero di abitanti di 10
            this.numAbitanti += 10;
        }

        this.risorse += risorseRegione;      //aumenta il numero di risorse della nazione in base alla regione assegnata

        this.denaro += risorseRegione / 2.0; //aumenta il denaro della nazione in base alla regione assegnata
    }

    //METODO INCREASE POPULATION
    //Ad ogni turno se richiamato aumenta il numero degli abitanti della nazione in base ai terreni posseduti.
    //Quindi per ogni regione nella lista regioni che contiene tutte le regioni (celle) assegnate asd una nazione
    //se quella regione e' fertile aumentera' la popolazione di 100 mentre se quella regione e'
    //sterile la popolazione diminuira' di 20 abitanti.
    //Per vedere il tipo di territorio viene richiamato il metodo getTipo della classe Regione.
    public void increasePopulation() {
        for(Iterator<Regione> i = regioni.iterator(); i.hasNext();) {
            Regione num = i.next();
            if (num.getTipo()=="fertile") {
                this.numAbitanti += 100;
            }
            else {
                this.numAbitanti -=20;
            }
        }
    }


    //METODO GET ID REGIONI
    //Restituisce l'array list di regioni assegnate alla nazione
    public ArrayList<Regione> getRegioni(){
        return regioni;
    }

    //METODO ADD REGION
    //Assegna una cella alla nazione: inserisce la cella (la regione) che assegnamo alla nazione
    public void addRegion(Regione region){
        this.regioni.add(region);
    }

    //METODO REMOVE ALL REGIONS
    //Rimuove tutte le regioni resettandole, o meglio togliendo nazione di appartenenza
    //su quella cella e togliendo il colore della nazione, richiamando il metodo resetRegion
    //della classe Region.
    //Inoltre toglie le celle  dalla lista regioni, la quale contiene tutte
    //le regioni (le celle) assegnate ad una determinata nazione
    public void removeAllRegions(){
        for(Iterator<Regione> i = regioni.iterator(); i.hasNext();) {
            Regione num = i.next();
            num.resetRegion();      //toglie nazione di appartenza e colore della nazione alla regione
            i.remove();             //toglie la regione dalla lista di quelle appartenenti alla nazione
        }
    }
}