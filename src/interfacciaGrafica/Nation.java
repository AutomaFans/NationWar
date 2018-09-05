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

    private boolean active;                 //Variabile booleana che sara' true se la nazione non ha finito di svolgere la sua run, false altrimenti

    private boolean vivo = true;            //Variabile booleana che indica lo stato di una nazione.
											/*Indica se la nazione e' viva (true) o se la nazione e' morta (false).
	 										Sotto i 10 abitanti la nazione e' morta*/
    private int numSterili;  //indica quante regioni sterili ha una nazione
    private int numFertili;  //indica quante regioni fertili ha una nazione


    //Controller della griglia per avvisare(notify()) il thread che gestisce i turni
    //di passare al turno della nazione successiva
    private ControllerImpostazioniGriglia gridController;


    //Lista di regioni che rappresentano le celle assegnate e conquistate dalla nazione
    //Quindi ogni nazione avra' i suoi territori e questa lista contiene i territori (le celle)
    //posseduti dalla nazione
    private  ArrayList<Regione> regioni = new ArrayList<>();

    //Lista delle regioni che confinano con almeno un territorio che non possiede la nazione, e che quindi dovranno essere
    // scelte casualmente per eseguire il thread che gli appartiene.
    private ArrayList<Regione> regionsToExec = new ArrayList<>();


    //COSTRUTTORE CON DUE PARAMETRI
    //Prende come parametri una stringa nome e una stringa colore.
    //Poi setta il nome della nazione, impostando come nome la stringa che e' stata presa da parametro,
    //(richiamamndo il metodo setName che e' un metodo gia' definito nella classe Thread).
    //Poi imposta il colore della nazione, impostando come colore la stringa che e' stata presa da parametro.
    //Poi setta l'eta' della nazione, di default l'eta' della nazione e' antica, e antica viene
    //scelta dal dominio enumerativo di "Eta".
    //poi setta il denaro ele risorse della nazione, questi due valori inizialmente sono a 0
    //e assumono un valore iniziale in base alle celle(pezzi di territorio) assegnati sulla griglia
    //Poi setta a 10 il numero di abitanti della nazione (quindi ogni nazione ha come minimo un abitante).
    //Infine setta la variabil booleana active a false.
    public Nation(String nome, String color){
        this.setName(nome);                 //Setta il nome della nazion
        this.color = color;					//Setta il colore della nazione
        this.age = Eta.ANTICA;             	//Setta l'eta' della nazione
        this.denaro = 0.0;                  //Setta il denaro della nazone
        this.risorse = 0.0;					//Setta le risorse della nazone
        this.numAbitanti = 10;				//Setta il numero di abitanti della nazione
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


    //METODO GETNUMSTERILI
    //restituisce il numero di regioni sterili di una nazione
    public int getNumSterili(){
        return this.numSterili;
    }

    //METODO GETNUMFERTILI
    //restituisce il numero di regioni fertili della nazione
    public int getNumFertili(){ return this.numFertili;
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
    //Restituisce il numero di abitanti della nazione
    public int getNumAbitanti(){
        return this.numAbitanti;
    }

    //METODO GET STATO
    //Restituisce true se la nazione e' viva, false altrimenti
    public boolean getStato(){
        return this.vivo;
    }

    //METODO SET STATO
    //permette di modificare lo stato vivo a true o false della nazione
    public void setStato(Boolean stato) {
        this.vivo=stato;
    }
    //METODO REFRESH AGE
    //Metodo che permette di aggiornare l'eta' attuale della nazione.
    //Ogni passggio di eta' avviene se sono passati almeno 20 turni dall'inizio del gioco
    //o 20 turni da un cambiamento di eta'.
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
        if(this.gridController.turni % 20 == 0) {   //Il passaggio da un'epoca ad un'altra avviene ogni 20 anni (se determinati valori vengono soddisfatti).
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

    //METODO CONQUISTA REGIONE
    //Permette di annettere la regione passata da parametro al dominio della nazione, trendone benefici. La regione
    //puo' anche essere nociva alla nazione, perche' se si tratta di una regione con un numero
    //basso di risorse puo' accadere che la nazione conquistandolo non prenda nessun profitto, anzi col tempo
    //potrebbe abbassarsi piu' velocemente la popolazione e quindi di conseguenza i profitti:
    // in quel caso la nazione avrebbe conquistato una regione che puo' contribuire alla sua rovina.
    public void conquistaRegione(Regione region){
        this.denaro -= region.getValore();                              //Viene speso il denaro che serve per comprare
        //la regione in base al suo valore
        region.setNazione(this.getName(), this.getColor(), this);  //Vengono settato il controllo della nazione
        //sulla regione
        this.addRegion(region);                                         //Viene aggiunta la regione a quelle possedute
        //dalla nazione
        this.takeProfit(region.getTipo(), region.getRisorse());         //Viene preso profitto dalla conquista
    }

    //METODO TAKE PROFIT
    //Metodo per aumentare il numero di abitanti, il denaro e le risorse della nazione: tutto cio' avviene
    // solo se la regione ha risorse da offrire(risorseRegione > 0).
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
        //Se le risorse della regione sono maggiori di 0 allora si ottiene profitto
        if(risorseRegione > 0){
            if(tipoRegione.equals("fertile")){ 		//Se la regione e' fertile
                this.numAbitanti += 100;			//Aumenta il numero di abitanti di 100
            }
            else{                              		//Altrimenti, se la regione e' sterile
                this.numAbitanti += 10;				//Aumenta il numero di abitanti di 10
            }
            this.risorse += risorseRegione;      	//Aumenta il numero di risorse della nazione in base alla regione assegnata
            this.denaro += risorseRegione / 2.0; 	//Aumenta il denaro della nazione in base alla regione assegnata
        }//Altrimenti non si ottiene nessun profitto
    }

    //METODO INCREASE POPULATION
    //Ad ogni turno se richiamato aumenta il numero degli abitanti della nazione in base ai terreni posseduti.
    //Quindi per ogni regione nella lista regioni che contiene tutte le regioni (celle) assegnate asd una nazione
    //se quella regione e' fertile aumentera' la popolazione di 100 mentre se quella regione e'
    //sterile la popolazione diminuira' di 20 abitanti. Se quella regione e' sterile e non ha piu' risorse da offrire
    //la popolazione diminuira' di 30.
    //Per vedere il tipo di territorio viene richiamato il metodo getTipo della classe Regione.
    public void increasePopulation() {
        for(Iterator<Regione> i = regioni.iterator(); i.hasNext();) {
            Regione num = i.next();
            if (num.getTipo()=="fertile") {
                this.numAbitanti += 100;
            }
            else {
                if(num.getRisorse() <= 0){ //Se si tratta di una regione per la quale sono state esaurite tutte le sue risorse
                    this.numAbitanti -=30; //Si ottiene una notevole depressione demografica
                }
                else{                      //Altrimenti la popolazione si abbassa leggermente di meno
                    this.numAbitanti -=20;
                }

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
        this.regioni.add(region);                //Aggiunge la regione alla lista completa delle regioni della nazione
        if (region.getTipo()=="fertile"){
            numFertili++;
            System.out.println("fertili:"+numFertili);
        }

        if (region.getTipo()=="sterile"){
            numSterili++;
            System.out.println("sterili:"+ numSterili);
        }
    }

    //METODO REFRESH REGIONS TO EXEC
    //Si controlla quali delle regioni appartengono a quelle che confinano con almeno una regione che non fa parte della
    // nazione, e se una regione appartiene a questa categoria viene aggiunta alla lista regionsToExec: le regioni di
    // questa lista verranno scelte  randomicamente per eseguire il thread che gli appartiene e quindi per svolgere il
    // compito di una regione.
    public void refreshRegionsToExec(){
        removeExecRegions();                         //Viene svuotata la vecchia lista preparandolo ad essere aggiornata
        for(Regione region : this.regioni){
            int row = region.getRow();               //Ottiene il numero di riga della regione nella griglia
            int column = region.getColumn();         //Ottiene il numero di colonna della regione nella griglia
            int gridRows = this.gridController.getNumeroRighe();      //Ottiene il numero di righe totali della griglia
            int gridColumns = this.gridController.getNumeroColonne(); //Ottiene il numero di colonne totali della griglia
            boolean regioneEseguibile = false;       //Booleano che sara' messo a true se la regione ha almeno un territorio
            //confinante che non fa parte della nazione

            //Ci sono vari tipologie di celle classificate in base a se hanno o meno una
            //coordinata(x=row o y=column) che colloca la cella al bordo della griglia
            if(row == 0){                            //Caso in cui la cella non ha sopra nessuna cella confinante(si tratta di
                // una cella nella prima riga)
                if(column == 0){                     //caso in cui la cella non ha a sinistra nessuna cella confinante(si tratta
                    //di una cella nella prima colonna)
                    //Controllo se la regione ha a destra o in basso una cella confinante che non fa parte della nazione, se siamo in questo
                    //caso si tratta di una cella nell'angolo in alto a sinistra: con il metodo getNodeFromGridPane (a cui viene passato numero
                    //di riga e colonna della cella e la griglia in cui si trova la cella) ottengo il nodo(un oggetto) che si trova nella
                    //griglia alle coordinate specificate tra parametri. Questo nodo viene convertito in una regione con il cast esplicito
                    //"((Regione)nodoOttenuto)", e se ne ottiene il nome della nazione di appartenenza verificando che non sia uguale a quello
                    //della nazione della regione che si vuole verificare se e' eseguibile.
                    if(((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), 1, 0)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), 0, 1)).getNomeNazione().equals(this.getName()) == false){
                        regioneEseguibile = true; //se una delle due celle confinanti non fa parte della nazione allora la regione di cui
                        //si verifica l'eseguibilita' viene scelta per essere inserita nella lista
                    }
                }
                else if(column == (gridColumns-1)){  //Caso in cui la cella non ha a destra nessuna cella confinante(si tratta di
                    //una cella nell'ultima colonna)
                    //Controllo se la regione ha a sinistra o in basso una cella confinante che non fa parte della nazione, se siamo in questo
                    //caso si tratta di una cella nell'angolo in alto a destra
                    if(((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column-1, 0)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column, 1)).getNomeNazione().equals(this.getName()) == false){
                        regioneEseguibile = true;
                    }
                }
                else{                                //Caso in cui la cella sia a destra e sinistra celle confinanti(si tratta di
                    //una cella di una colonna intermedia)
                    //Controllo se la regione ha a destra o a sinistra una cella confinante che non fa parte della nazione, se siamo in questo
                    //caso si tratta di una cella del bordo in alto ma non negli angoli
                    if(((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column-1, 0)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column+1, 0)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column, 1)).getNomeNazione().equals(this.getName()) == false){
                        regioneEseguibile = true;
                    }
                }
            }
            else if(row == (gridRows-1)){             //Caso in cui la cella non ha sotto nessuna cella confinante(si tratta di
                // una cella nell'ultima riga)
                if(column == 0){
                    //Controllo se la regione ha a destra o in alto una cella confinante che non fa parte della nazione, se siamo in questo
                    //caso si tratta di una cella nell'angolo in basso a sinistra
                    if(((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), 0, row-1)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), 1, row)).getNomeNazione().equals(this.getName()) == false){
                        regioneEseguibile = true;
                    }
                }
                else if(column == (gridColumns-1)){
                    //Controllo se la regione ha a sinistra o in alto una cella confinante che non fa parte della nazione, se siamo in questo
                    //caso si tratta di una cella nell'angolo in basso a destra
                    if(((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column, row-1)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column-1, row)).getNomeNazione().equals(this.getName()) == false){
                        regioneEseguibile = true;
                    }
                }
                else{
                    //Controllo se la regione ha in alto, a sinistra o a destra una cella confinante che non fa parte della nazione, se siamo in questo
                    //caso si tratta di una cella nel bordo in basso ma non negli angoli
                    if(((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column, row-1)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column-1, row)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column+1, row)).getNomeNazione().equals(this.getName()) == false){
                        regioneEseguibile = true;
                    }
                }
            }
            else{                                    //Caso in cui la cella ha sia sopra che sotto celle confinanti(si tratta di
                // una cella in una riga intermedia)
                if(column == 0){
                    //Controllo se la regione ha in alto, in basso o a destra una cella confinante che non fa parte della nazione, se siamo in questo
                    //caso si tratta di una cella nel bordo sinistro ma non negli angoli
                    if(((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), 0, row-1)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), 0, row+1)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), 1, row)).getNomeNazione().equals(this.getName()) == false){
                        regioneEseguibile = true;
                    }
                }
                else if(column == (gridColumns-1)){
                    //Controllo se la regione ha in alto, in basso o a sinistra una cella confinante che non fa parte della nazione, se siamo in questo
                    //caso si tratta di una cella nel bordo destro ma non negli angoli
                    if(((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column, row-1)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column, row+1)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column-1, row)).getNomeNazione().equals(this.getName()) == false){
                        regioneEseguibile = true;
                    }
                }
                else{
                    //Controllo se la regione ha in alto, a destra, in basso o a sinistra una cella confinante che non fa parte della nazione,
                    // se siamo in questo caso si tratta di una cella che non si trova in nessuno dei bordi
                    if(((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column, row+1)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column, row-1)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column-1, row)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column+1, row)).getNomeNazione().equals(this.getName()) == false){
                        regioneEseguibile = true;
                    }
                }
            }

            //Se la regione ha almeno un territorio confinante che non appartiene alla nazione
            if(regioneEseguibile == true){
                this.regionsToExec.add(region);      //Viene aggiunta alla lista di quelle che devono essere scelte randomicamente
                //dalla nazione per eseguire il loro thread
            }//Altrimenti non viene aggiunta
        }
    }

    //METODO REMOVE ALL REGIONS
    //Rimuove tutte le regioni resettandole, o meglio togliendo nazione di appartenenza
    //su quella cella e togliendo il colore della nazione, richiamando il metodo resetRegion
    //della classe Region.
    //Inoltre toglie le celle dalla lista regioni, la quale contiene tutte le regioni
    //(le celle) assegnate ad una determinata nazione
    //Infine rimuove le regioni dalla lista delle regioni che confinano con almeno un territorio che non fa parte della nazione
    public void removeAllRegions(){
        for(Iterator<Regione> i = regioni.iterator(); i.hasNext();) {
            Regione num = i.next();
            num.resetRegion();      //Toglie dalla cella la nazione di appartenza e il colore della nazione
            if (num.getTipo()=="fertile"){
                numFertili--;
                System.out.println("fertili in meno:"+numFertili);
            }

            if (num.getTipo()=="sterile"){
                numSterili--;
                System.out.println("sterili in meno:"+ numSterili);
            }
            i.remove();             //Toglie la cella dalla lista di quelle appartenenti alla nazione
        }
        removeExecRegions();
    }

    //METODO REMOVE EXEC REGIONS
    //Metodo per rimuovere tutte le regioni dalla lista di quelle che devono essere scelte randomicamente nel run di Nation
    public void removeExecRegions(){
        for(Iterator<Regione> i = regionsToExec.iterator(); i.hasNext();) {
            Regione num = i.next();

            i.remove();
        }
    }

    //METODO VERIFICA REGOLE TRANSIZIONE
    //Permette di verificare le regole di transizione per la regione passata come parametro: alla fine del metodo si
    //vedra' se la nazione puo' o non puo' mantenere il controllo della regione.
    public void verificaRegoleTransizione(Regione region){
        region.refreshNeighboringRegions();       //Aggiorna le regioni che confinano alla regione passata da parametro
        ArrayList<Regione> alleate=region.getRegioniConfinantiAlleate();        //Regioni confinanti alleate
        ArrayList<Regione> sconosciute=region.getRegioniConfinantiSconosciute();//Regioni confinanti non alleate
        boolean mantieniControllo = false;                         //Variabile per verificare se bisogna mantenere o no il controllo della
                                                                   //regione passata da parametro: il risultato verra' determinato
                                                                   //dalle regole di transizione
        //Verifica di seguito le regole di transizione
        if(region.getTipo().equals("sterile")){      //Se la regione e' sterile
            if(alleate.size() >= 2){            //Se ha almeno due territori confinanti alleati
                boolean fertile = false;        //booleano per vedere se almeno uno dei territori alleati e' fertile
                for(int i=0; i < alleate.size(); i++){
                    if(alleate.get(i).getTipo().equals("fertile")){ //Se auno di questi territori alleati e' fertile
                        fertile = true;                        //Se ne tiene conto
                    }
                }
                if(fertile == true){             //Se almeno una delle regioni alleate e' fertile
                    mantieniControllo = true;       //La nazione mantiene il controllo della regione
                }
                else{                               //Altrimenti si considerano i territori confinanti non alleati
                    for(int i=0; i < sconosciute.size(); i++){
                        if(sconosciute.get(i).getTipo() == "fertile"){ //Se almeno uno di questi e' fertile
                            mantieniControllo = true;       //La nazione mantiene il controllo della regione
                        }
                    }
                }
            }
            else{                               //Altrimenti si considerano i territori confinanti non alleati
                for(int i=0; i < sconosciute.size(); i++){
                    if(sconosciute.get(i).getTipo() == "fertile"){ //Se almeno uno di questi e' fertile
                        mantieniControllo = true;       //La nazione mantiene il controllo della regione
                    }
                }
            }
        }
        else{                                   //Se la regione e' fertile
            mantieniControllo = true;       //La nazione mantiene il controllo della regione
        }

        if (mantieniControllo == false){        //Se non si puo' mantenere il controllo della regione
            region.resetRegion();               //La nazione ne perde il controllo e le sue caratteristiche vengono
                                                //modificate in maniera che questa regione non faccia piu' parte
                                                //della nazione
            if (region.getTipo()=="sterile"){    //mi serve per capire se devo decrementare numSterili
                numSterili--;
            }
            if (region.getTipo() == "fertile"){//mi serve per capire se devo decrementare numFertili
                numFertili--;
            }
            regioni.remove(region);             //Rimuovo la regione dalla lista di quelle che appartengono alla nazione

        }
    }


    //METODO CLONE CHARACTERS
    //Metodo usato nel caso si sta clonando una nazione per runnarla di nuovo senza perdere traccia dei dati, in quel caso
    //quindi questa sarebbe una nazione clone (le uniche nazioni non clone sono quelle del primo turno di gioco).
    //In particolare copia i dati della nazione che si vuole clonare in questa nazione, (nome e colore sono gia' stati copiati)
    //Qusto metodo prende come parametro la nuova nazione da clonare e copia dentro questa l'etÃ  della nazione,
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
    //Il run prevede lo svolgimento del turno della nazione nella simulazione, inizialmente sceglie una sua regione in
    //maniera casuale tra quelle che confinano con almeno una regione che non gli appartiene, e testa su di essa le
    //regole di transizione, poi sceglie di nuovo una regione che confina con almeno una regione che non gli appartiene
    // e lo fa di nuovo casualmente e successivamente attende che il suo turno finisca. Alla fine svolgera delle azioni di fine turno.
    //Siccome la nazione iniziera' ad eseguire il proprio turno allora viene messa la variabile boolena active (che tiene
    //conto se una nazone sta eseguendo il proprio tueno o meno) a true.
    //Viene mandato il thread in sleep per 1000 millisecondi(un secondo) in maniera da poter notare cosa succede tra un turno e
    //l'altro. Se non ci fosse la sleep i cambiamenti sarebbero istantanei e non sarebbe possibile percepire i cambiamenti(in un
    //secondo verrebbero eseguiti decine di turni).
    //Successivamente si controlla se la nazione ha occupato tutta la griglia: se si bisognera' prendere una regione
    //della nazione (dalla lista regioni)per verificarne le regole di transizione.
    //Altrimenti viene aggiornata la lista delle regioni della nazione che confinano con almeno una regione non alleata. Da questa lista
    //viene scelta una regione casualmente. Se l'intero memorizzato nella variabile regionToControl e' uguale alla lunghezza
    // della lista regioni da eseguire(regionsToExec) si sottrae 1, per non incombere successivamente in un OutOfBoundException.
    // Su questa regione si verificano le regole di transizione: una volta verificate si vedra' se la nazione dovra'
    // mantenere o meno il controllo di quella regione.
    //Se applicando le regole di transizione la nazione e' rimasta senza regioni allora la nazione e' dichiarata morta e
    //non bisogna svolgere il codice che segue: si tiene conto quindi che e' morta e si avvisa il thread main che gestiva
    // i turni che il turno della nazione e' finito.
    //Altrimenti se non siamo nel caso precedente si verifica di nuovo se la nazione detiene tutta la griglia: se si
    // bisognera' estrarre una regione qualsiasi(dalla lista regioni) in maniera randomica ed eseguire il suo thread.
    // Altrimenti viene aggiornata nuovamente la lista delle regioni che possono essere scelte per essere eseguite in
    // maniera da scegliere quelle che attualmente confinano con un territorio che non fa parte della nazione.
    //Poi viene presa casualmente da questa lista(regionsToExec) una regione da startare e viene memorizzato l'indice di questa regione
    ///all'interno della variabile regionToStart.
    //Successiavmente fa lo start (richiamando il metodo startRegionThread della classe Regione) di una regione casuale della nazione,
    //e questa regione e' presa dalla lista regioni (per prendere la regione dalla lista si usa l'intero memorizzato nella
    //variabile regionToStart).
    //Cosi la nazione va in attesa e aspetta che finisca il suo turno per svolgere le azioni successive,
    //in particolare attende (wait()) che la regione che agisce la avvisi con una notify() che il suo turno e' finito.
    //Cosi quando la nazione riceve una notify, viene richiamato il metodo increasePopulation della classe Nation,
    //il metodo incassaDenaro della classe Nation e il metodo consumaRisorse della classe Nation.
    //Inoltre avendo finito il prorpio turno setta la variabile active a false ed infine la nazione avvisa il thread
    //che deteneva la griglia e gestiva i turni che il suo turno e' finito e si puo' passare al turno della
    //nazione successiva.
    //Infine se il numero di abitanti e' minore di 10, viene messa la variabile boolena vivo a false.
    public synchronized void run() {
        try{
            this.active = true;                                       //La nazione ha iniziato ad eseguire il codice del suo run() pertanto se ne tiene conto
            // settando active a true
            sleep(1000);                                        	  //Perde un secondo di tempo in maniera da notare i cambiamenti tra un turno e l'altro

            //Se la nazione ha occupato tutta la griglia non ci sono regioni che confinano con regioni non alleate, quindi posso verificare
            // direttamente le regole di transizione su una regione qualsiasi della griglia che scelgo casualmente
            if(this.regioni.size() == (this.gridController.getNumeroRighe() * this.gridController.getNumeroColonne())){
                Random rand1 = new Random();                              //Viene creato un oggetto di tipo Random, chiamato Rand1
                int regionToControl = rand1.nextInt((regioni.size())); //Intero generato casualmente che rappresentera' l'indice della
                                                                       // regione sulla quale verificare le regole di transizione
                if(regionToControl == regioni.size()){                //Se l'intero e' uguale alla lunghezza della lista regioni sottrae 1
                    regionToControl -= 1;
                }
                //Per la regione scelta vengono verificate le regole di transizioni le quali ci diranno se la nazione manterra' o meno
                //il controllo su quella regione
                System.out.println(regioni.get(regionToControl).toString());
                verificaRegoleTransizione(regioni.get(regionToControl));
            }
            //Altrimenti se non e' stata occupata tutta la griglia vuol dire che ci sono delle regioni che confinano con regioni non alleate,
            //e quindi bisogna scegliere una regione di questo tipo per verificarne le regole di transizione
            else{
                refreshRegionsToExec();                                   //Aggiorna l'elenco delle regioni che possono essere scelte randomicamente
                                                                          //per eseguire il loro thread
                Random rand1 = new Random();                              //Viene creato un oggetto di tipo Random, chiamato Rand1
                int regionToControl = rand1.nextInt((regionsToExec.size())); //Intero generato casualmente che rappresentera' l'indice della
                                                                             // regione sulla quale verificare le regole di transizione
                if(regionToControl == regionsToExec.size()){                //Se l'intero e' uguale alla lunghezza della lista regioni da
                                                                            // eseguire(regionsToExec) sottrae 1
                    regionToControl -= 1;
                }
                //Per la regione scelta vengono verificate le regole di transizioni le quali ci diranno se la nazione manterra' o meno
                //il controllo su quella regione
                System.out.println(regionsToExec.get(regionToControl).toString());
                verificaRegoleTransizione(regionsToExec.get(regionToControl));
            }

            if(this.regioni.size() == 0){       //Se la nazione a seguito dell'applicazione delle regole di transizione e' rimasta senza regioni
                                                //caso in cui ho una solo regione e applicando le regole di transizione la perdo
                this.vivo = false;              //Allora la nazione muore
                this.active = false;          						//Avendo finito di eseguire il codice del suo run() setta active a false
                this.gridController.sveglia(); 						//La nazione avvisa il thread che deteneva la griglia e gestiva i turni che
                                                                    //il suo turno e' finito
            }
            else{                               //Altrimenti continua con le prossime istruzioni
                //Si passa a scegliere una nuova regione random, ma questa volta per conquistare nuove regioni(o stringere alleanze)
                //Se le regioni possedute dalla nazione sono uguali al numero di celle della griglia allora la nazione possiede tutta la
                //griglia e quindi non ci saranno celle che confinano con regioni che non sono della stessa nazione. Percio'
                //si va ad eseguire una regione qualsiasi posseduta dalla nazione
                if(this.regioni.size() == (this.gridController.getNumeroRighe() * this.gridController.getNumeroColonne())){
                    Random rand2 = new Random();							  //Viene creato un nuovo oggetto di tipo Random per scegliere di nuovo
                                                                              //una regione randomica tra quelle di "regioni"
                    int regionToStart = rand2.nextInt((regioni.size())); //Intero che rappresentera' l'indice della regione da startare
                    if(regionToStart == regioni.size()){
                        regionToStart -= 1;
                    }
                    regioni.get(regionToStart).startRegionThread();  	  //Fa lo start() di una regione casuale nella lista di quelle da eseguire
                }
                else{ //altrimenti bisognera' scegliere tra quelle che confinano con almeno un territorio non alleato
                    refreshRegionsToExec();                              //Aggiorna l'elenco delle regioni che possono essere scelte randomicamente
                                                                         //per eseguire il loro thread
                    Random rand2 = new Random();					     //Viene creato un nuovo oggetto di tipo Random per scegliere di nuovo
                                                                         //una regione randomica tra quelle di regionsToExec
                    int regionToStart = rand2.nextInt((regionsToExec.size())); //Intero che rappresentera' l'indice della regione da startare
                    if(regionToStart == regionsToExec.size()){
                        regionToStart -= 1;
                    }
                    regionsToExec.get(regionToStart).startRegionThread();  	  //Fa lo start() di una regione casuale nella lista di quelle da eseguire
                }
                System.out.println(this.getName() + "nazione");
                wait();            									      //La nazione va in attesa che si arrivi alla fine del suo turno per svolgere le azioni
                                                                          // successive
                System.out.println("Sono di nuovo in gioco!");

                this.increasePopulation();     						//La popolazione subisce un alzamento o un abbassamento in base allo stato dei terreni posseduti
                this.incassaDenaro();          						//Viene incassato denaro in base alle risorse e agli abitanti della nazione
                this.consumaRisorse();         						/*Vengono consumate le risorse, inoltre in questo metodo viene anche aggiornata l'eta':
                                           						    si vuole infatti tenere conto della situazione in cui si trova la nazione a fine turno*/
                this.active = false;          						//Avendo finito di eseguire il codice del suo run() setta active a false
                this.gridController.sveglia(); 						//La nazione avvisa il thread che deteneva la griglia e gestiva i turni che
                                                                    //il suo turno e' finito e si puo' passare al turno della nazione successiva

                if(this.getNumAbitanti() < 10){
                    vivo = false;
                }
            }

        }
        catch (InterruptedException i){    						//Se si ha un interrupt di questa nazione si ottiene un eccezione
            System.out.println("Vita nazione interrotta!");
        }
    }
}