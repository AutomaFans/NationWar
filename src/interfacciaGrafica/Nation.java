package interfacciaGrafica;
import org.controlsfx.control.PopOver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

//Ogni nazione e' composta dal nome, dal colore, dall'eta', dal denaro, dalle risorse e dal numero di abitanti.
//Il colore sara' utilizzato per colorare i bottoni nella griglia.
public class Nation extends Thread{

    private String color;				    //Stringa color peril colore della nazione
    private Eta age;                        //age conterra' l'eta' in cui si trova la nazione
    private double denaro;                  //denaro corrente della nazione
    private double risorse;                 //risorse naturali della nazione
    private int numAbitanti;                //numero di abitanti della nazione
    private boolean active;                 //booleano con valore ture se nazione non ha finito di svolgere la sua run,
    // false altrimenti
    private ControllerImpostazioniGriglia gridController; //controller della griglia per avvisare(notify()) il thread
    //che gestisce i turni di passare al turno della nazione successiva

    //Lista di regioni che rappresentano le celle assegnate e conquistate dalla nazione
    //Quindi ogni nazione avra i suoi territori e questa lista contiene i territori (le celle)
    //posseduti dalla nazione
    private  ArrayList<Regione> regioni = new ArrayList<>();


    //COSTRUTTORE CON DUE PARAMETRI
    public Nation(String nome, String color){
        this.setName(nome);                 //setta il nome della nazione, con il metodo setName() della classe Thread
        this.color = color;
        this.age = Eta.ANTICA;              //L'eta' di default della nazione e' antica, e antica viene
        //scelta dal dominio enumerativo di "Eta"
        this.denaro = 0.0;                  //Di default denaro e risorse sono a 0 e assumono
        //un valore iniziale in base alle celle(pezzi di territorio) assegnati
        //sulla griglia
        this.risorse = 0.0;
        this.numAbitanti = 1;
        this.active = false;
    }



    //METODO GETCOLOR()
    //Metodo che restituisce il colore della nazione sulla quale viene chiamato il metodo
    public String getColor(){
        return this.color;
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
    //eta' moderna.
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
        this.refreshAge();                       //Viene aggiornata l'eta' attuale della nazione(antica, intermedia, moderna)
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


    //METODO GET REGIONI
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


    //METODO CLONE CHARACTERS
    //Metodo usato nel caso si sta clonando una nazione per runnarla di nuovo senza perdere traccia dei dati, in quel caso
    //quindi questa sarebbe una nazione clone(l'uniche nazioni non clone sono quelle del primo turno di gioco).
    //In particolare copia i dati della nazione che si vuole clonare in questa nazione, mentre nome e colore sono gia' stati copiati
    public void cloneCharacters(Nation nazioneDaClonare){            //Prende come parametro la nazione daclonare
        this.age = nazioneDaClonare.getAge();                        //copia l'eta'
        this.denaro = nazioneDaClonare.getDenaro();                  //copia la quantita' di denaro
        this.risorse = nazioneDaClonare.getRisorse();                //copia la quantita' di risorse
        this.numAbitanti = nazioneDaClonare.getNumAbitanti();        //copia il numero di abitanti
        this.gridController = nazioneDaClonare.getGridController();  //copia l'oggetto griglia sul quale la nazione
        //svolge delle azioni
        for(int i=0; i < nazioneDaClonare.getRegioni().size(); i++){ //Itero le regioni della nazione da clonare
            //Passo alle regioni il nuovo oggetto nazioneclonato che gli appartiene
            nazioneDaClonare.getRegioni().get(i).setNazione(nazioneDaClonare.getName(), nazioneDaClonare.getColor(), this);
            //Inoltre anche i thread delle regioni se sono stati startati(start()) non possono piu' eseguire il loro run()
            //quindi bisogna crearne una nuova istanza
            nazioneDaClonare.getRegioni().get(i).setNewThread();
            //Infine viene aggiunta la regione alla naione clonata(this.addRegion("regione");)
            addRegion(nazioneDaClonare.getRegioni().get(i));
        }
    }

    //METODO GET THREAD STATE
    //Restituisce true se il thread non ha finito di svolgere la sua run, false altrimenti
    public boolean getThreadState(){
        return this.active;
    }

    //METODO SET GRID CONTROLLER
    //Usato in ControllerImpostazioniGriglia per assegnare appunto il ControllerImpostazioniGriglia generale a Nation.
    //Questo servira' per l'operazione di notifica(notify()) della nazione al thread che gestisce i turni delle nazioni
    //per dirgli che ha finito il suo turno e puo' passare al turno della nazione successiva.
    public void setGridController(ControllerImpostazioniGriglia controller){
        this.gridController = controller;
    }

    //METODO GET GRID CONTROLLER
    //Restituisce la griglia utilizzata nella simulazione dalla nazione
    public ControllerImpostazioniGriglia getGridController(){
        return this.gridController;
    }

    //METODO SVEGLIA
    //Metodo usato dalla regione scelta casualmente dalla nazione: la regione avvisa la Nation che era in attesa(wait())
    //che ha finito il suo lavoro e che quindi si e' giunti a fine turno
    public synchronized void sveglia(){
        notify();
    }

    //METODO RUN
    //Il run prevede lo svolgimento del turno della nazione nella simulazione, inizialmente fa agire una sua regione presa
    //casualmente e successivamente attende che il suo turno finisca. Alla fine svolgera delle azioni di fine turno.
    public synchronized void run() {
        try{
            this.active = true;                                 //la nazione ha iniziato ad eseguire il codice del suo run()
                                                                //pertanto se ne tiene conto settando active a true
            Random rand = new Random();
            int regionToStart = rand.nextInt((regioni.size())); //intero che rappresentera' l'indice della regione da startare
            if(regionToStart == regioni.size()){                //se l'intero e' uguale alla lunghezza della lista regioni si
                                                                //sottrae 1, cio' per non incombere successivamente in un
                                                                // OutOfBoundException
                regionToStart -= 1;
            }
            regioni.get(regionToStart).startRegionThread();  //fa lo start() di una regione casuale dalla
                                                             // nazione(usando come indice l'intero regionToStart)
            System.out.println(this.getName() + "nazione");
            wait();             //La nazione va in attesa che si arrivi alla fine del suo turno per svolgere le azioni successive:
                                //in particolare attende(wait()) che la regione che agisce la avvisi con una notify() che
                                //il suo turno e' finito.
            System.out.println("Sono di nuovo in gioco!");

            this.increasePopulation();     //La popolazione subisce un alzamento o un abbassamento in base allo stato dei terreni posseduti
            this.incassaDenaro();          //Viene incassato denaro in base alle risorse e agli abitanti della nazione
            this.consumaRisorse();         //Vengono consumate le risorse, inoltre in questo metodo viene anche aggiornata l'eta':
                                           //si vuole infatti tenere conto della situazione in cui si trova la nazione a fine turno
            this.active = false;           //Avendo finito di eseguire il codice del suo run() setta active a false
            this.gridController.sveglia(); //La nazione avvisa il thread che deteneva la griglia e gestiva i turni che
                                           // il suo turno e' finito e si puo' passare al turno della nazione successiva
        }
        catch (InterruptedException i){    //Se si ha un interrupt di questa nazione si ottiene un eccezione
            System.out.println("Vita nazione interrotta!");
        }
    }
}