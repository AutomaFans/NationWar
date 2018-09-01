package interfacciaGrafica;
import org.controlsfx.control.PopOver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

//Ogni nazione e' composta dal nome, dal colore, dall'eta', dal denaro, dalle risorse e dal numero di abitanti.
//Il colore sara' utilizzato per colorare i bottoni nella griglia.
//Una Nazione estende un thread.
public class Nation extends Thread{

    private String color;				    //Stringa color peril colore della nazione
    private Eta age;                        //Age conterra' l'eta' in cui si trova la nazione
    private double denaro;                  //Duble denaro che tiene conto del denaro corrente della nazione
    private double risorse;                 //Duble risorse che tiene conto delle risorse naturali della nazione
    private int numAbitanti;                //Inteo numAbitanti che tiene conto del numero di abitanti della nazione

    static int turni = 0;                   //La variabile turni tiene conto del numero di turni trascorsi dal'inizio del gioco.
                                            //Un turno è completo quando tutte le nazioni in gioco "hanno fatto la propria mossa".
    static int nNazione = 0;                //Varibile utilizzata per il conteggio dei turni.

    private boolean active;                 //Variabile booleana che sara' true se la nazione non ha finito di svolgere la sua run, false altrimenti

    //Controller della griglia per avvisare(notify()) il thread che gestisce i turni
    //di passare al turno della nazione successiva
    private ControllerImpostazioniGriglia gridController;


    //Lista di regioni che rappresentano le celle assegnate e conquistate dalla nazione
    //Quindi ogni nazione avra' i suoi territori e questa lista contiene i territori (le celle)
    //posseduti dalla nazione
    private  ArrayList<Regione> regioni = new ArrayList<>();


    //COSTRUTTORE CON DUE PARAMETRI
    //Prende come parametri una stringa nome e una stringa colore.
    //Poi setta il nome della nazione, impostando come nome la stringa che e' stata presa da parametro,
    //(richiamamndo il metodo setName che e' un metodo gia' definito nella classe Thread).
    //Poi imposta il colore della nazione, impostando come colore la stringa che e' stata presa da parametro.
    //Poi setta l'eta' della nazione, di default l'eta' della nazione e' antica, e antica viene
    //scelta dal dominio enumerativo di "Eta".
    //poi setta il denaro ele risorse della nazione, questi due valori inizialmente sono a 0
    //e assumono un valore iniziale in base alle celle(pezzi di territorio) assegnati sulla griglia
    //Poi setta ad 1 il numero di abitanti della nazione (quindi ogni nazione ha come minimo un abitante).
    //Infine setta la variabil booleana active a false.
    public Nation(String nome, String color){
        this.setName(nome);                 //Setta il nome della nazion
        this.color = color;					//Setta il colore della nazione
        this.age = Eta.ANTICA;             	//Setta l'eta' della nazione
        this.denaro = 0.0;                  //Setta il denaro della nazone
        this.risorse = 0.0;					//Setta le risorse della nazone
        this.numAbitanti = 1;				//Setta il numero di abitanti della nazione
        this.active = false;				//Imposta la variabile booleana active a false
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
    //e' maggiore o uguale a 10000 allora vine impostata l'eta' della nazione come
    //eta' moderna.
    public void refreshAge(){
        if(turni%20 == 0) {   //Il passaggio da un'epoca ad un'altra avviene ogni 20 anni (se determinati valori vengono soddisfatti).
            if (this.risorse < 3000 && this.numAbitanti < 1000 && this.denaro < 5000) {
                this.age = Eta.ANTICA;
            } else if ((this.risorse >= 3000 && this.numAbitanti >= 1000 && this.denaro >= 5000) &&
                    (this.risorse < 5000 && this.numAbitanti < 2000 && this.denaro < 10000)) {
                this.age = Eta.INTERMEDIA;
            } else {
                this.age = Eta.MODERNA;
            }
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
    //Metodo per aumentare il numero di abitanti, il denaro e le risorse della nazione.
    //Queto metodo prende due parametri: il tipo di regione (fertile o sterile) e le risorse
    //della regione.
    //Se la regione (cella)  e' fertile aumenta il numero di abitanti di 100, mentre se la
    //regione e' sterile aumenta il numero di abitanti di 10.
    //Inoltre aumenta il numero delle risorse della nazione in base alla regione (cella) che e'
    //stata assegnata a quella nazione (quindi siccome ogni cella ha un certo numero di risorse
    //quando viene assegna quella cella alla nazione, aumentano le risorse della nazione della quantita'
    //di risorse che la cella aveva).
    //Infine aumenta il numero di denaro della nazione in base alla regione (cella) che e'
    //stata assegnata a quella nazione (quindi siccome ogni cella ha un certo numero di risorse
    //quando viene assegna quella cella alla nazione, aumentano le risorse della nazione della meta
    //della quantita di risorse che la cella aveva).
    public void takeProfit(String tipoRegione, Double risorseRegione){
        if(tipoRegione.equals("fertile")){ 		//Se la regione e' fertile
            this.numAbitanti += 100;			//Aumenta il numero di abitanti di 100
        }
        else{                              		//Altrimenti, se la regione e' sterile
            this.numAbitanti += 10;				//Aumenta il numero di abitanti di 10
        }
        this.risorse += risorseRegione;      	//Aumenta il numero di risorse della nazione in base alla regione assegnata
        this.denaro += risorseRegione / 2.0; 	//Aumenta il denaro della nazione in base alla regione assegnata
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
    //Restituisce l'array list chimato regioni che sono le regioni assegnate alla nazione
    public ArrayList<Regione> getRegioni(){
        return regioni;
    }

    //METODO ADD REGION
    //Assegna una cella alla nazione.
    //Inserisce la cella (la regione) che assegnamo alla nazione alla lista regioni
    //che contiene tutte le celle assegnate e conquistate da quella nazione.
    public void addRegion(Regione region){
        this.regioni.add(region);
    }

    //METODO REMOVE ALL REGIONS
    //Rimuove tutte le regioni resettandole, o meglio togliendo nazione di appartenenza
    //su quella cella e togliendo il colore della nazione, richiamando il metodo resetRegion
    //della classe Region.
    //Inoltre toglie le celle dalla lista regioni, la quale contiene tutte le regioni
    //(le celle) assegnate ad una determinata nazione
    public void removeAllRegions(){
        for(Iterator<Regione> i = regioni.iterator(); i.hasNext();) {
            Regione num = i.next();
            num.resetRegion();      //Toglie dalla cella la nazione di appartenza e il colore della nazione
            i.remove();             //Toglie la cella dalla lista di quelle appartenenti alla nazione
        }
    }


    //METODO CLONE CHARACTERS
    //Metodo usato nel caso si sta clonando una nazione per runnarla di nuovo senza perdere traccia dei dati, in quel caso
    //quindi questa sarebbe una nazione clone (le uniche nazioni non clone sono quelle del primo turno di gioco).
    //In particolare copia i dati della nazione che si vuole clonare in questa nazione, (nome e colore sono gia' stati copiati)
    //Qusto metodo prende come parametro la nuova nazione da clonare e copia dentro questa l'età della nazione,
    //la quantita' di denaro, la quantita' di risorse, il numero di abitanti ecopia l'oggetto griglia sul quale la nazione
    //svolge delle azioni.
    public void cloneCharacters(Nation nazioneDaClonare){
        this.age = nazioneDaClonare.getAge();                        	//Copia l'eta'
        this.denaro = nazioneDaClonare.getDenaro();                  	//Copia la quantita' di denaro
        this.risorse = nazioneDaClonare.getRisorse();                	//Copia la quantita' di risorse
        this.numAbitanti = nazioneDaClonare.getNumAbitanti();        	//Copia il numero di abitanti
        this.gridController = nazioneDaClonare.getGridController();  	//Copia l'oggetto griglia sul quale la nazione svolge delle azioni
        for(int i=0; i < nazioneDaClonare.getRegioni().size(); i++){ 	//Itero le regioni della nazione da clonare
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
    //Restituisce il valore della variabile active, quindi restituisce true se il thread non
    //ha finito di svolgere la sua run, false altrimenti
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
    //Metodo usato dalla regione scelta casualmente dalla nazione: la regione avvisa la Nation
    //che era in attesa(wait()) che ha finito il suo lavoro e che quindi si e' giunti
    //a fine turno
    public synchronized void sveglia(){
        notify();
    }

    //METODO RUN
    //Il run prevede lo svolgimento del turno della nazione nella simulazione, inizialmente fa agire una sua regione presa
    //casualmente e successivamente attende che il suo turno finisca. Alla fine svolgera delle azioni di fine turno.
    //Siccome la nazone iniziera' ad eseguire il proprio turno allora viene messa la variabile boolena active (che tiene
    //conto se una nazone sta eseguendo il proprio tueno o meno) a true.
    //poi viene presa casualmente dallalista regioni una regione da startare e viene memorizzato l'indice di questa regione
    ///all'interno della variabile regionToStart.
    //Se l'intero memorizzato nella variabile regionToStart e' uguale alla lunghezza della lista regioni si sottrae
    //1, per non incombere successivamente in un OutOfBoundException.
    //Successiavmente fa lo start (richiamando il metodo startRegionThread della classe Regione) di una regione casuale della nazione,
    //e questa regione e' presa dalla lista regioni (per prendere la regione dalla lista si usa l'intero memorizzato nella
    //variabile regionToStart).
    //Cosi la nazione va in attesa e aspetta che finisca il suo turno per svolgere le azioni successive,
    //in particolare attende (wait()) che la regione che agisce la avvisi con una notify() che il suo turno e' finito.
    //Cosi quando la nazione riceve una notify, viene richiamato il metodo increasePopulation della classe Nation,
    //il metodo incassaDenaro della classe Nation e il metodo consumaRisorse della classe Nation.
    //Inoltre avendo finito il prorpio turno setta la variabile acive a false ed infine la nazione avvisa il thread
    //che deteneva la griglia e gestiva i turni che il suo turno e' finito e si puo' passare al turno della
    //nazione successiva.
    public synchronized void run() {
        try{
            this.active = true;                                 //La nazione ha iniziato ad eseguire il codice del suo run() pertanto se ne tiene conto settando active a true
            Random rand = new Random();							//Viene creato un oggetto di tipo Random, chiamato rand
            int regionToStart = rand.nextInt((regioni.size())); //Intero che rappresentera' l'indice della regione da startare
            if(regionToStart == regioni.size()){                //Se l'intero e' uguale alla lunghezza della lista regioni
                regionToStart -= 1;								//Sottrae 1
            }
            regioni.get(regionToStart).startRegionThread();  	//Fa lo start() di una regione casuale della nazione
            System.out.println(this.getName() + "nazione");
            wait();            									 //La nazione va in attesa che si arrivi alla fine del suo turno per svolgere le azioni successive
            System.out.println("Sono di nuovo in gioco!");

            this.increasePopulation();     						//La popolazione subisce un alzamento o un abbassamento in base allo stato dei terreni posseduti
            this.incassaDenaro();          						//Viene incassato denaro in base alle risorse e agli abitanti della nazione
            this.consumaRisorse();         						/*Vengono consumate le risorse, inoltre in questo metodo viene anche aggiornata l'eta':
                                           						si vuole infatti tenere conto della situazione in cui si trova la nazione a fine turno*/
            //Controlla il conteggio dei turni.
            //Se tutte le nazioni hanno giocato allora si incrementa di 1 il turno, altrimenti incrementa di 1 solamente il numero di nazioni che hanno giocato in quel turno.
            if(nNazione == ControllerImpostazioniGriglia.nationList.size()){
                nNazione = 0;
                turni += 1;
            }
            else{
                nNazione += 1;
            }

            this.active = false;          						//Avendo finito di eseguire il codice del suo run() setta active a false
            this.gridController.sveglia(); 						//La nazione avvisa il thread che deteneva la griglia e gestiva i turni che
            //il suo turno e' finito e si puo' passare al turno della nazione successiva
        }
        catch (InterruptedException i){    						//Se si ha un interrupt di questa nazione si ottiene un eccezione
            System.out.println("Vita nazione interrotta!");
        }
    }
}