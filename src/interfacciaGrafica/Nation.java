package interfacciaGrafica;
import org.controlsfx.control.PopOver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

//Ogni nazione e' composta dal nome, dal colore, dall'eta', dal denaro, dalle risorse e dal numero di abitanti.
//Il colore sara' utilizzato per colorare i bottoni nella griglia.
//Una Nazione estende un thread.
public class Nation extends Thread{

    private String color;				    //Stringa color per il colore della nazione
    private Eta age;                        //Age conterra' l'eta' in cui si trova la nazione
    private double denaro;                  //Duble denaro che tiene conto del denaro corrente della nazione
    private double risorse;                 //Duble risorse che tiene conto delle risorse naturali della nazione
    private int numAbitanti;                //Inteo numAbitanti che tiene conto del numero di abitanti della nazione
    private  String nome;       			//Nome della nazione
    private  int numSterili;    			//Numero di territori sterili della nazione
    private  int numFertili;    			//Numero di territori fertili della nazione
    private boolean active;                 //Variabile booleana che sara' true se la nazione non ha finito di svolgere la sua run, false altrimenti
    private boolean vivo = true;            //Variabile booleana che indica lo stato di una nazione.
											/*Indica se la nazione e' viva (true) o se la nazione e' morta (false).
	 										Sotto i 10 abitanti la nazione e' morta*/


    //COSTRUTTORE CON CINQUE PARAMETRI
    //Questo costruttore viene richiamato nel metodo clickStart della classe
    //ControllerImpGriglia, per aggiungere le informazioni riguardanti una nazione
    //alla lista informazioni (che sara' poi aggiunta alla tabella InfoTable sempre
    //della classe ControllerImpGriglia).
    //Prende come parametri una Stringa nome, un Eta eta, un intero numSterili, un intero
    //numFertili.
    //Poi assegna l'eta eta presa da parametro al campo chiamato eta della classe
    //Nation, la stringa nome presa da parametro al campo chiamato nome della classe
    //Nation, l'intero numFertili preso da parametro al campo chiamato numFertili
    //della classe Nation, l'intero numSterili preso da parametro al campo chiamato
    //numSterili della classe Nation
    public Nation(String nome, Eta eta, int numSterili, int numFertili) {
        this.age= eta;
        this.nome = nome;
        this.numFertili = numFertili;
        this.numSterili =numSterili;
    }

    //METODO GET ETA
    //Restituisce il campo chiamato eta della classe Nation
    //Questo metodo serve aggiungere le informazioni riguardanti una nazione
    //alla lista informazioni
    public Eta getEta(){
        return age;
    }

    //METODO GET NOME
    //Restituisce il campo chiamato nome della classe Nation
    //Questo metodo serve aggiungere le informazioni riguardanti una nazione
    //alla lista informazioni
    public String getNome(){
        return nome;
    }

    //METODO GET NUM STERILI
    //Restituisce il campo chiamato numSterili della classe Nation
    //Questo metodo serve aggiungere le informazioni riguardanti una nazione
    //alla lista informazioni
    public int getNumSterili(){
        return numSterili;
    }

    //METODO GET NUM FERTILI
    //Restituisce il campo chiamato numFertili della classe Nation
    ///Questo metodo serve aggiungere le informazioni riguardanti una nazione
    //alla lista informazioni
    public int getNumFertili(){
        return numFertili;
    }


    //Controller della griglia per avvisare (notify()) il thread che gestisce i turni
    //di passare al turno della nazione successiva
    private ControllerImpostazioniGriglia gridController;


    //Lista di regioni che rappresentano le celle assegnate e conquistate dalla nazione
    //Quindi ogni nazione avra' i suoi territori e questa lista contiene i territori (le celle)
    //posseduti dalla nazione
    private  ArrayList<Regione> regioni = new ArrayList<>();

    //Lista delle regioni che confinano con almeno un territorio che non possiede la nazione,
    //e che quindi dovranno essere scelte casualmente per eseguire il thread che gli appartiene.
    private ArrayList<Regione> regionsToExec = new ArrayList<>();

    //Lista degli accordi proposti da una data nazione e poi questi accordi saranno
    //accettati da parte di un altra nazione.
    private ArrayList<Accordo> accordiProposti = new ArrayList<>();

    //Lista degli accordi proposti da una nazione e accettati da una data nazione
    private ArrayList<Accordo> accordiAccettati = new ArrayList<>();


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
    //Permette di settare la variabile booleana chiamata vivi.
    //Prende come parametro un booleano stato (che puÃƒÂ² essere false o true) e
    //se gli viene passato true setta la variabile vivo con true, altrimente se gli
    //viene passato false setta la variabile vivo con false.
    //Quindi se la nazione e' viva verra' passato true (cosi la variabile booleana vivo sara' true),
    //se la nazione e' morta verra' passato false (cosi la variabile booleana vivo sara' false)
    public void setStato(Boolean stato) {
        this.vivo=stato;
    }



    //METODO REFRESH AGE
    //Metodo che permette di aggiornare l'eta' attuale della nazione.
    //Ogni passaggio di eta' avviene se sono passati almeno 20 turni dall'inizio del gioco
    //o 20 turni da un cambiamento di eta'.
    //Per fare cio' bisogna vedere se il numero di turni diviso 20 da resto 0 (cioe' se il numero
    //di turni e' un multiplo di 20).
    //Quindi se il numero di turni diviso 20 da resto 0 si va a vedere:
    //- se le risorse della nazione sono minori di 3000, il numero di abitanti della nazione
    //sono minori di 1000 e il denaro della nazione e' minore di 5000 allora
    //viene impostata l'eta' della nazione come eta' antica.
    //- se le risorse della nazione sono maggiori o uguali di 3000 e minori di 5000,
    //il numero di abitanti della nazione sono maggiori o uguali di 1000 e minori di 2000 e
    //il denaro della nazione e' maggiore o uguale di 5000 e minore di 10000 allora
    //viene impostata l'eta' della nazione come eta' intermedia.
    //- se le risorse della nazione sono maggiori o uguali di 5000, il numero
    //di abitanti della nazione sono maggiori o uguali di 2000 e il denaro dellanazione
    //e' maggiore o uguale a 10000 allora vine impostata l'eta' della nazione come
    //eta' moderna.
    public void refreshAge(){
        if(this.gridController.turni % 20 == 0) {
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
    //Siccome la nazione occupa una certa regione e quindi consuma le risorse, allora ad ogni
    //turno del gioco viene richiamato questo metodo, per cui viene sottratto un decimo del numero
    //di risorse e in seguito viene richiamato il metodo RefreshAge (della classe Nation)
    public void consumaRisorse(){
        this.risorse = risorse - (risorse / 10); //Viene consumato un decimo delle risorse
        this.refreshAge();                       //Viene aggiornata l'eta' attuale della nazione(antica, intermedia, moderna)
    }



    //METODO INCASSA DENARO
    //Ad ogni turno del gioco viene richiamato questo metodo, per cui viene aumentato
    //il denaro della nazione in relazione al numero delle risorse totali e degli abitanti.
    public void incassaDenaro(){
        this.denaro = (risorse / 2.0) + (numAbitanti*2.0);
    }



    //METODO CONQUISTA REGIONE
    //Questo metodo prende come parametri una Regione chiamata regione (di tipo Regione)
    //e permette di assegnare la regione passata da parametro ad una determinata nazione
    //traendone benefici (quindi la nazione conquista la regione che viene presa da parametro
    //e ne ricava benefici).
    //Quindi, quando la nazione conquista (acquista) la nuova regione, allora il denaro della
    //nazione viene diminuito in base al costo di quella regione (e questo costo e' dato
    //dal metodo getValore della classe Regione).
    //In seguito viene assegna a quella regione il controllo da parte di quella nazione (con
    //il metodo setNazione della classe Regione) e poi viene aggiunta la regione appena conquistata
    //a quelle gia possedute da quella nazione, quindi viene aggiunta la nazione alla lista region
    //(con il metodo addRegion della classe Nation).
    //Infine viene richiamato il metodo takeProfit della classe antion per far si che la nazionee
    //prenda i suoi profitti delle regioni conquistate.
    //La regione puo' anche essere nociva alla nazione, perche' se si tratta di una regione con un numero
    //basso di risorse puo' accadere che la nazione conquistandola non prenda nessun profitto, anzi col tempo
    //potrebbe abbassarsi piu' velocemente la popolazione e quindi di conseguenza i profitti:
    //in quel caso la nazione avrebbe conquistato una regione che puo' contribuire alla sua rovina.
    public void conquistaRegione(Regione region){
        if (region.getNomeNazione()!=""){ //se la regione conquistata e' di qualcuno
            Nation nazione = region.getNazione(); //creo un oggetto nazione che è proprio la nazione che ha perso una regione
            nazione.getRegioni().remove(region); //viene rimossa la regione dalla nazione che l'ha persa
            if (nazione.getRegioni().size()==0){ //Se la regione che ha appena perso la regione ora non ha più regioni
                nazione.setStato(false); //muore
                //Scioglie tutti gli accordi che aveva stretto
                for(int i=0; i<nazione.getAccordiProposti().size();i++){  //Scioglie gli accordi proposti rimuovendoli da quelli
                                                                          // accettati delle nazioni che l'hanno accettati
                    nazione.getAccordiProposti().get(i).getNazioneCheAccetta().getAccordiAccettati().remove(nazione.getAccordiProposti().get(i));
                    nazione.getAccordiProposti().get(i).getRegionePatto().setText("");
                }
                for(int i=0; i<nazione.getAccordiAccettati().size();i++){  //Scioglie gli accordi accettati rimuovendoli da quelli
                                                                           // proposti /delle nazioni che l'hanno proposti
                    nazione.getAccordiAccettati().get(i).getNazioneChePropone().getAccordiProposti().remove(nazione.getAccordiAccettati().get(i));
                    nazione.getAccordiAccettati().get(i).getRegionePatto().setText("");
                }
            }

        }
        else { //altrimenti
            this.denaro -= region.getValore();                            //Viene speso il denaro che serve per comprare la regione in base al suo valore
        }
        region.setNazione(this.getName(), this.getColor(), this);   //Viene settato il controllo della nazione sulla regione
        this.addRegion(region);                                         //Viene aggiunta la regione a quelle possedute dalla nazione
        this.takeProfit(region.getTipo(), region.getRisorse());         //Viene preso profitto dalla conquista
}



    //METODO TAKE PROFIT
    //Questo metodo prende come parametri una stringa che rappresenta il tipo di regione (sterile o fertile)
    //e un valore double che rappresenta il numero di risorse di quella regione.
    //E questo metodo serve per aumentare il numero di abitanti, il denaro e le risorse della nazione:
    //tutto cio' avviene solo se la regione ha risorse da offrire(risorseRegione > 0).
    //Quindi se le risorse della regione sono maggiori di 0 allora la  nazione puo' riscuotere
    //un profitto, per cui si va a vedere se la regione (cella) e' fertile aumenta il numero di abitanti
    //di 100, mentre se laregione e' sterile aumenta il numero di abitanti soltanto di 10.
    //Inoltre, se le risorse della regione sono maggiori di 0, aumenta anche il numero delle risorse
    //della nazione in base alla regione (cella) che e' stata assegnata a quella nazione (quindi siccome
    //ogni cella ha un certo numero di risorse quando viene assegna quella cella alla nazione, aumentano
    //le risorse della nazione della quantita' di risorse che la cella aveva) ed aumenta anche
    //il numero di denaro della nazione in base alla regione (cella) che e'stata assegnata a quella
    //nazione (quindi siccome ogni cella ha un certo numero di risorse quando viene assegna quella cella
    //alla nazione, aumentano le risorse della nazione della meta della quantita di risorse che la cella aveva).
    //Se invece le risorse della regione sono minori o uguali a 0 la nazione non ottiene nessun profitto.
    public void takeProfit(String tipoRegione, Double risorseRegione){
        //SE LE RISORSE DELLA REGIONE SONO MAGGIORI DI 0, LA NAZIONE OTTINE UN PROFITTO
        if(risorseRegione > 0){
            if(tipoRegione.equals("fertile")){ 		//Se la regione e' fertile
                this.numAbitanti += 100;			//Aumenta il numero di abitanti di 100
            }
            else{                              		//Altrimenti, se la regione e' sterile
                this.numAbitanti += 10;				//Aumenta il numero di abitanti di 10
            }
            this.risorse += risorseRegione;      	//Aumenta il numero di risorse della nazione in base alla regione assegnata
            this.denaro += risorseRegione / 2.0; 	//Aumenta il denaro della nazione in base alla regione assegnata
        }
        //ALTRIMENTI, SE LE RISORSE DELLA REGIONE SONO MINORI O UGUALI A O, LA NAZIONE
        //NON OTTIENE NESSUN PROFITTO
    }



    //METODO INCREASE POPULATION
    //Questo metodo viene richiamato ad ogni turno per aumentare il numero degli abitanti della
    //nazione in base ai terreni posseduti.
    //Quindi per ogni regione nella lista regioni (che contiene tutte le regioni (celle) assegnate
    //ad una nazione), se quella regione e' fertile aumentera' la popolazione di 100 mentre se
    //quella regione e' sterile la popolazione diminuira' di 20 abitanti. Se quella regione e'
    //sterile, si ava vedere il numero di risorse che quella regione ha.
    //Se quella regione ha un numero di risorse minore o ugulale a 0 (quindi se sono state esaurite
    //tutte le risose) allora la popolazione diminuira' di 30, altrimenti se quella regione
    //non ha ha un numero di risorse minore o ugulale a 0 (quindi se non sono state esaurite
    //tutte le risose) allora la popolazione diminuira' soltanto di 20.
    //Per vedere il tipo di territorio viene richiamato il metodo getTipo della classe Regione.
    //Per vedere il tipo di territorio viene richiamato il metodo getTipo della classe Regione.
    //Inoltre per ogni accordo proposto dalla nazione e stretto con altre nazioni si ottiene
    //un aumento della popolazione di 50 abitanti.
    public void increasePopulation() {
        for(Iterator<Regione> i = regioni.iterator(); i.hasNext();) {
            Regione num = i.next();
            //SE LA REGIONE (CELLA) E' FERTILE
            if (num.getTipo()=="fertile") {
                this.numAbitanti += 100;
            }
            //SE LA REGIONE (CELLA) E' STERILE
            else {
                //SE LA REGIONE (OLTRE AD ESSERE STERILE) NON HA PIU' RISOSE
                if(num.getRisorse() <= 0){
                    this.numAbitanti -=30;
                }
                //SE LA REGIONE (OLTRE AD ESSERE STERILE) NON ANCORA RISOSE
                else{
                    this.numAbitanti -=20;
                }
            }
        }
        for(int i=0; i <this.accordiProposti.size(); i++){
            this.numAbitanti += 50;
        }
    }



    //METODO GET REGIONI
    //Restituisce l'array list chimato regioni che sono le regioni assegnate alla nazione
    public ArrayList<Regione> getRegioni(){
        return regioni;
    }



    //METODO GET ACCORDI PROPOSTI
    //Restituisce la lista di accordi proposti da parte della nazione this e attualmente
    //in vigore
    public ArrayList<Accordo> getAccordiProposti(){
        return this.accordiProposti;
    }



    //METODO GET ACCORDI ACCETTATI
    //Restituisci la lista degli accordi ricevuti ed accettati dalla nazione this
    public ArrayList<Accordo> getAccordiAccettati(){
        return this.accordiAccettati;
    }



    //METODO AGGIORNA PATTI PROPOSTI
    //Questo metodo prende come parametri una la lista chiamata proposti, che rappresenta i
    //nuovi accordi proposti e aggiorna la lista chiamata accordiProposti (della classe nation)
    //con la lista presa da parametro.
    //Questo metodo usato quando una nazione che entra in guerra con una alleata deve
    //sciogliere i patti e di seguito aggiornarli
    public void aggiornaPattiProposti(ArrayList<Accordo> proposti){
        this.accordiProposti = proposti;
    }



    //METODO AGGIORNA PATTI ACCETTATI
    //Questo metodo prende come parametri una la lista chiamata accettati, che rappresenta i
    //nuovi accordi proposti e aggiorna la lista chiamata accordiAccettati (della classe nation)
    //con la lista presa da parametro.
    //Questo metodo usato quando una nazione che entra in guerra con una alleata deve
    //sciogliere i patti e di seguito aggiornarli
    public void aggiornaPattiAccettati(ArrayList<Accordo> accettati){
        this.accordiAccettati = accettati;
    }



    //METODO ADD REGION
    //Questo metodo prende come paramentro una regin di tipo Regione e assegna
    //la regione presa da parametro alla nazione (quindi assegna una cella alla nazione).
    //Per cui inserisce la cella (la regione) che assegnamo alla nazione alla lista regioni
    //che contiene tutte le celle assegnate e conquistate da quella nazione.
    public void addRegion(Regione region){
        this.regioni.add(region);                //Aggiunge la regione alla lista completa delle regioni della nazione

    }



    //METODO REFRESH REGIONS TO EXEC
    //Si controlla quali delle regioni appartengono a quelle che confinano con almeno una
    //regione che non fa parte della nazione, e se una regione appartiene a questa categoria
    //viene aggiunta alla lista regionsToExec e poi le regioni di questa lista verranno scelte
    //randomicamente per eseguire il thread che gli appartiene e quindi per svolgere il
    //compito di una regione.
    //Come prima cosa viene richiamato il metodo removeExecRegions della classe Nation (e
    //questo metodo rimuovere tutte le regioni dalla lista regionsToExec).
    //In seguito per ogni regione (cella) dentro la lista regioni si ottiene il numero di riga
    //e il numero di colonna di quella regione e si prende anche il numero di righe totali
    //e il numero di colonne totali della griglia (richiamano i metodi getNumeroRighe e getNumeroColonne).
    //Inoltre viene settata la variabile booleana chiamata regioneEseguibile a false, questa
    //variabile tiene conto se la regione ha almeno un territorio confinante che non fa parte
    //della nazione (in tal caso vine messa a true), altrimenti viene messa a false.
    //Ci sono vari tipologie di celle classificate in base la loro posizionamento nella griglia,cioe':
    //-se la regione e' nella prima riga e nella prima colonna (quindi non ci sono regioni confinanti
    //al di sopra e alla sua sinistra) allora bisogna controllare se la regione ha a destra o in basso
    //una cella confinante che non fa parte della stessa sua nazione. In questo caso si tratta di una
    //cella nell'angolo in alto a sinistra, per cui con il metodo getNodeFromGridPane della classe ControllerImpGriglia
    //(a cui viene passato numero di riga e colonna della cella e la griglia in cui si trova la cella)
    //ottengo il nodo(l'oggetto) che si trova nella griglia alle coordinate specificate tra parametri.
    //Cosi questo nodo viene convertito in una regione con il cast esplicito ((Regione)nodoOttenuto), e
    //ne viene preso il nome della nazione di appartenenza verificando che non sia uguale a quello
    //della nazione della regione che si vuole verificare se e' eseguibile. Percio' se una delle due
    //celle confinanti non fa parte della nazione allora la regione di cui si verifica l'eseguibilita'
    //viene scelta per essere inserita nella lista per cui viene impostata la sua variabili regioneEseguibile
    //a true.
    //-se la regione e' nella prima riga e nell'ultima colonna (quindi non ci sono regioni confinanti
    //al di sopra e alla sua destra) allora bisogna controllare se la regione ha a sinistra o in basso
    //una cella confinante che non fa parte della stessa sua nazione. In questo caso si tratta di una
    //cella nell'angolo in alto a destra, per cui con il metodo getNodeFromGridPane della classe ControllerImpGriglia
    //(a cui viene passato numero di riga e colonna della cella e la griglia in cui si trova la cella)
    //ottengo il nodo(l'oggetto) che si trova nella griglia alle coordinate specificate tra parametri.
    //Cosi questo nodo viene convertito in una regione con il cast esplicito ((Regione)nodoOttenuto), e
    //ne viene preso il nome della nazione di appartenenza verificando che non sia uguale a quello
    //della nazione della regione che si vuole verificare se e' eseguibile. Percio' se una delle due
    //celle confinanti non fa parte della nazione allora la regione di cui si verifica l'eseguibilita'
    //viene scelta per essere inserita nella lista per cui viene impostata la sua variabili regioneEseguibile
    //a true.
    //-se la regione e' nella prima riga e nelle colonne in mezzo (quindi non ci sono regioni confinanti
    //al di sopra ma ci sono celle confinanti alla sua sinistra e destra e in basso) allora bisogna
    //controllare se la regione ha a destra o a sinistra o in basso una cella confinante che non fa
    //parte della stessa sua nazione. In questo caso si tratta di una cella al bordo in alto
    //ma non negli angoli, per cui con il metodo getNodeFromGridPane della classe ControllerImpGriglia
    //(a cui viene passato numero di riga e colonna della cella e la griglia in cui si trova la cella)
    //ottengo il nodo(l'oggetto) che si trova nella griglia alle coordinate specificate tra parametri.
    //Cosi questo nodo viene convertito in una regione con il cast esplicito ((Regione)nodoOttenuto), e
    //ne viene preso il nome della nazione di appartenenza verificando che non sia uguale a quello
    //della nazione della regione che si vuole verificare se e' eseguibile. Percio' se una delle due
    //celle confinanti non fa parte della nazione allora la regione di cui si verifica l'eseguibilita'
    //viene scelta per essere inserita nella lista per cui viene impostata la sua variabili regioneEseguibile
    //a true.

    //-se la regione e' nell'ultima riga e nella prima colonna (quindi non ci sono regioni confinanti
    //al di sotto e alla sua sinistra) allora bisogna controllare se la regione ha a destra o in alto
    //una cella confinante che non fa parte della stessa sua nazione. In questo caso si tratta di una
    //cella nell'angolo in basso a sinistra, per cui con il metodo getNodeFromGridPane della classe ControllerImpGriglia
    //(a cui viene passato numero di riga e colonna della cella e la griglia in cui si trova la cella)
    //ottengo il nodo(l'oggetto) che si trova nella griglia alle coordinate specificate tra parametri.
    //Cosi questo nodo viene convertito in una regione con il cast esplicito ((Regione)nodoOttenuto), e
    //ne viene preso il nome della nazione di appartenenza verificando che non sia uguale a quello
    //della nazione della regione che si vuole verificare se e' eseguibile. Percio' se una delle due
    //celle confinanti non fa parte della nazione allora la regione di cui si verifica l'eseguibilita'
    //viene scelta per essere inserita nella lista per cui viene impostata la sua variabili regioneEseguibile
    //a true.
    //-se la regione e' nell'ultima riga e nell'ultima colonna (quindi non ci sono regioni confinanti
    //al di sotto e alla sua destra) allora bisogna controllare se la regione ha a sinistra o in alto
    //una cella confinante che non fa parte della stessa sua nazione. In questo caso si tratta di una
    //cella nell'angolo in basso a destra, per cui con il metodo getNodeFromGridPane della classe ControllerImpGriglia
    //(a cui viene passato numero di riga e colonna della cella e la griglia in cui si trova la cella)
    //ottengo il nodo(l'oggetto) che si trova nella griglia alle coordinate specificate tra parametri.
    //Cosi questo nodo viene convertito in una regione con il cast esplicito ((Regione)nodoOttenuto), e
    //ne viene preso il nome della nazione di appartenenza verificando che non sia uguale a quello
    //della nazione della regione che si vuole verificare se e' eseguibile. Percio' se una delle due
    //celle confinanti non fa parte della nazione allora la regione di cui si verifica l'eseguibilita'
    //viene scelta per essere inserita nella lista per cui viene impostata la sua variabili regioneEseguibile
    //a true.
    //-se la regione e' nell'ultima riga e nelle colonne in mezzo (quindi non ci sono regioni confinanti
    //al di sotto ma ci sono celle confinanti alla sua sinistra e destra e in alto) allora bisogna
    //controllare se la regione ha a destra o a sinistra o in alto una cella confinante che non fa
    //parte della stessa sua nazione. In questo caso si tratta di una cella al bordo in basso
    //ma non negli angoli, per cui con il metodo getNodeFromGridPane della classe ControllerImpGriglia
    //(a cui viene passato numero di riga e colonna della cella e la griglia in cui si trova la cella)
    //ottengo il nodo(l' oggetto) che si trova nella griglia alle coordinate specificate tra parametri.
    //Cosi questo nodo viene convertito in una regione con il cast esplicito ((Regione)nodoOttenuto), e
    //ne viene preso il nome della nazione di appartenenza verificando che non sia uguale a quello
    //della nazione della regione che si vuole verificare se e' eseguibile. Percio' se una delle due
    //celle confinanti non fa parte della nazione allora la regione di cui si verifica l'eseguibilita'
    //viene scelta per essere inserita nella lista per cui viene impostata la sua variabili regioneEseguibile
    //a true.

    //-se la regione e' nelle riga in mezzo e nella prima colonna (quindi non ci sono regioni confinanti
    //alla sua sinistra ma ci sono celle confinanti alla sua destra, in alto e in basso) allora bisogna
    //controllare se la regione ha in alto, in basso o a destra una cella confinante che non fa parte
    //della stessa sua nazione. In questo caso si tratta di una cella al bordo a sinistra,ma non negli angoli,
    //per cui con il metodo getNodeFromGridPane della classe ControllerImpGriglia (a cui viene passato numero
    //di riga e colonna della cella e la griglia ((in cui si trova la cella) ottengo il nodo(l' oggetto)
    //che si trova nella griglia alle coordinate specificate tra parametri.
    //Cosi questo nodo viene convertito in una regione con il cast esplicito ((Regione)nodoOttenuto), e
    //ne viene preso il nome della nazione di appartenenza verificando che non sia uguale a quello
    //della nazione della regione che si vuole verificare se e' eseguibile. Percio' se una delle due
    //celle confinanti non fa parte della nazione allora la regione di cui si verifica l'eseguibilita'
    //viene scelta per essere inserita nella lista per cui viene impostata la sua variabili regioneEseguibile
    //-se la regione e' nelle riga in mezzo e nell'ultima colonna (quindi non ci sono regioni confinanti
    //alla sua detra ma ci sono celle confinanti alla sua sinistra, in alto e in basso) allora bisogna controllare
    //se la regione ha in alto, in basso o a sinistra una cella confinante che non fa parte della stessa sua nazione.
    //In questo caso si tratta di una cella al bordo a destra,ma non negli angoli, per cui con il metodo
    //getNodeFromGridPane della classe ControllerImpGriglia (a cui viene passato numero di riga e colonna della
    //cella e la griglia ((in cui si trova la cella) ottengo il nodo(l' oggetto) che si trova nella griglia
    //alle coordinate specificate tra parametri.
    //Cosi questo nodo viene convertito in una regione con il cast esplicito ((Regione)nodoOttenuto), e
    //ne viene preso il nome della nazione di appartenenza verificando che non sia uguale a quello
    //della nazione della regione che si vuole verificare se e' eseguibile. Percio' se una delle due
    //celle confinanti non fa parte della nazione allora la regione di cui si verifica l'eseguibilita'
    //viene scelta per essere inserita nella lista per cui viene impostata la sua variabili regioneEseguibile
    //-se la regione e' nelle riga in mezzo e nelle colonne in mezzo (quindi ci sono regioni confinanti
    //alla sua detra, alla sua sinistra, in alto e in basso) allora bisogna controllare se la regione
    //ha in alto, in basso, a sinistra o a destra una cella confinante che non fa parte della stessa sua nazione.
    //In questo caso si tratta di una cella che non e' in nessun bordo per cui con il metodo getNodeFromGridPane della
    //classe ControllerImpGriglia (a cui viene passato numero di riga e colonna della cella e la griglia
    //((in cui si trova la cella) ottengo il nodo(l'oggetto) che si trova nella griglia alle coordinate
    //specificate tra parametri.
    //Cosi questo nodo viene convertito in una regione con il cast esplicito ((Regione)nodoOttenuto), e
    //ne viene preso il nome della nazione di appartenenza verificando che non sia uguale a quello
    //della nazione della regione che si vuole verificare se e' eseguibile. Percio' se una delle due
    //celle confinanti non fa parte della nazione allora la regione di cui si verifica l'eseguibilita'
    //viene scelta per essere inserita nella lista per cui viene impostata la sua variabili regioneEseguibile
    //Infine se la variabile boolena della regione chiamata regioneEseguibile e' true (cioe' se la regione ha
    //almeno una regione confinante che non appartine alla sua stessa nazione) viene aggiunta alla lista
    //di quelle che devono essere scelte randomicamente dalla nazione per eseguire il loro thread, quindi
    //vengono aggiunte alla lista regionsToExec, altimenti se la variabile boolena della regione chiamata
    //regioneEseguibile e' false non viene aggiunta alla lista regionsToExec (perche' significa che tutte
    //le regioni confinanti della nazione fanno parte della sua stessa nazione).
    public void refreshRegionsToExec(){
        removeExecRegions();                        					//Viene svuotata la vecchia lista regionsToExec preparandola per essere aggiornata
        for(Regione region : this.regioni){
            int row = region.getRow();               					//Ottiene il numero di riga della regione nella griglia
            int column = region.getColumn();         					//Ottiene il numero di colonna della regione nella griglia
            int gridRows = this.gridController.getNumeroRighe();     	//Ottiene il numero di righe totali della griglia
            int gridColumns = this.gridController.getNumeroColonne(); 	//Ottiene il numero di colonne totali della griglia
            boolean regioneEseguibile = false;       					//Booleano che sara' messo a true se la regione ha almeno un territorio confinante che non fa parte della nazione

            //SE LA REGIONE E' NELLA PRIMA RIGA
            //(QUINDI NON C'E' NESSUNA REGIONE CONFINANTE AL DI SOPRA)
            if(row == 0){
                //SE LA REGIONE E' NELLA PRIMA COLONNA
                //(QUINDI NON C'E' NESSUNA REGIONE CONFINANTE ALLA SUA SINISTRA)
                //IN QUESTO CASO SI TRATTA DI UNA CELLA NELL'ANGOLO IN ALTO A SINISTRA
                if(column == 0){
                    //SE LA REGIONE HA A DESTRA O IN BASSO UNA CELlA CONFINANTE CHE NON FA DELLA NAZIONE
                    if(((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), 1, 0)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), 0, 1)).getNomeNazione().equals(this.getName()) == false){
                        regioneEseguibile = true;
                    }
                }
                //SE LA REGIONE E' NELL'ULTIMA COLONNA
                //(QUINDI NON C'E' NESSUNA REGIONE CONFINANTE ALLA SUA DESTRA)
                //IN QUESTO CASO SI TRATTA DI UNA CELLA NELL'ANGOLO IN ALTO A DESTRA
                else if(column == (gridColumns-1)){
                    //SE LA REGIONE HA A SINISTRA O IN BASSO UNA CELLA CONFINANTE CHE NON FA DELLA NAZIONE
                    if(((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column-1, 0)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column, 1)).getNomeNazione().equals(this.getName()) == false){
                        regioneEseguibile = true;
                    }
                }
                //SE LA REGIONE STA NELLE COLONNE MEZZO
                //(QUINDI SE CI SONO REGIONI CONFINANTI ALLA SUA DESTRA E ALLA SUA SINISTRA E IN BASSO)
                //IN QUESTO CASO SI TRATTA DI UNA CELLA AL BORDO IN ALTO, MA NON AGLI ANGOLI
                else{
                    if(((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column-1, 0)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column+1, 0)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column, 1)).getNomeNazione().equals(this.getName()) == false){
                        regioneEseguibile = true;
                    }
                }
            }
            //SE LA REGIONE E' NELLA PRIMA RIGA
            //(QUINDI NON C'E' NESSUNA REGIONE CONFINANTE AL SOTTO)
            else if(row == (gridRows-1)){
                //SE LA REGIONE E' NELLA PRIMA COLONNA
                //(QUINDI NON C'E' NESSUNA REGIONE CONFINANTE ALLA SUA SINISTRA)
                //IN QUESTO CASO SI TRATTA DI UNA CELLA NELL'ANGOLO IN BASSO A SINISTRA
                if(column == 0){
                    if(((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), 0, row-1)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), 1, row)).getNomeNazione().equals(this.getName()) == false){
                        regioneEseguibile = true;
                    }
                }
                //SE LA REGIONE E' NELL'ULTIMA COLONNA
                //(QUINDI NON C'E' NESSUNA REGIONE CONFINANTE ALLA SUA DESTRA)
                //IN QUESTO CASO SI TRATTA DI UNA CELLA NELL'ANGOLO IN BASSO A DESTRA
                else if(column == (gridColumns-1)){
                    if(((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column, row-1)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column-1, row)).getNomeNazione().equals(this.getName()) == false){
                        regioneEseguibile = true;
                    }
                }

                else{
                    //SE LA REGIONE STA NELLE COLONNE IN MEZZO
                    //(QUINDI SE CI SONO REGIONI CONFINANTI ALLA SUA DESTRA E ALLA SUA SINISTRA E IN ALTO)
                    //IN QUESTO CASO SI TRATTA DI UNA CELLA AL BORDO IN BASSO, MA NON AGLI ANGOLI
                    if(((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column, row-1)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column-1, row)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column+1, row)).getNomeNazione().equals(this.getName()) == false){
                        regioneEseguibile = true;
                    }
                }
            }
            //SE LA REGIONE E' NELLA RIGHE IN MEZZO
            //(QUINDI SE CI SONO REGIONI CONFINANTI AL DI SOPRA E AL DI SOTTO)
            else{
                //SE LA REGIONE E' NELLA PRIMA COLONNA
                //(QUINDI NON C'E' NESSUNA REGIONE CONFINANTE ALLA SUA SINISTRA)
                //IN QUESTO CASO SI TRATTA DI UNA CELLA AL BORDO A SINISTRA, MA NON AGLI ANGOLI
                if(column == 0){
                    if(((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), 0, row-1)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), 0, row+1)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), 1, row)).getNomeNazione().equals(this.getName()) == false){
                        regioneEseguibile = true;
                    }
                }
                //SE LA REGIONE E' NELL'ULTIMA COLONNA
                //(QUINDI NON C'E' NESSUNA REGIONE CONFINANTE ALLA SUA DESTRA)
                //IN QUESTO CASO SI TRATTA DI UNA CELLA AL BORDO A DESTRA, MA NON AGLI ANGOLI
                else if(column == (gridColumns-1)){
                    if(((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column, row-1)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column, row+1)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column-1, row)).getNomeNazione().equals(this.getName()) == false){
                        regioneEseguibile = true;
                    }
                }
                //SE LA REGIONE STA NELLE COLONNE IN MEZZO
                //(QUINDI SE CI SONO REGIONI CONFINANTI ALLA SUA DESTRA E ALLA SUA SINISTRA, IN ALTO E IN BASSO)
                //IN QUESTO CASO SI TRATTA DI UNA CELLA CHE NON STA AI BORDI
                else{
                    if(((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column, row+1)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column, row-1)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column-1, row)).getNomeNazione().equals(this.getName()) == false
                            || ((Regione)this.gridController.getNodeFromGridPane(this.gridController.getGridPane(), column+1, row)).getNomeNazione().equals(this.getName()) == false){
                        regioneEseguibile = true;
                    }
                }
            }
            //SE LA NAZIONE HA ALMENO UNA REGIONE CONFINANTE CHE NON APPARTIENE ALLA SUA STESSA NAZIONE
            //(CIOE' SE LA VARIABILE REGIONE ESEGUIBILE E' TRUE)
            if(regioneEseguibile == true){
                this.regionsToExec.add(region);
            }//Altrimenti non viene aggiunta
        }
    }



    //METODO REMOVE ALL REGIONS
    //Rimuove tutte le regioni resettandole, o meglio togliendo nazione di appartenenza
    //su quella cella e togliendo il colore della nazione, richiamando il metodo resetRegion
    //della classe Region.
    //Inoltre toglie le celle dalla lista regioni, la quale contiene tutte le regioni
    //(le celle) assegnate ad una determinata nazione
    //Infine rimuove le regioni dalla lista delle regioni che confinano con almeno un
    //territorio che non fa parte della nazione, cioe' rimuove tutte le regioni anche dalla lista
    //regionsToExec richiammando il metodo removeExecRegions della classe Nation.
    public void removeAllRegions(){
        for(Iterator<Regione> i = regioni.iterator(); i.hasNext();) {
            Regione num = i.next();
            num.resetRegion();      //Toglie dalla cella la nazione di appartenza e il colore della nazione
            i.remove();             //Toglie la cella dalla lista di quelle appartenenti alla nazione
        }
        removeExecRegions();
    }



    //METODO REMOVE EXEC REGIONS
    //Metodo per rimuovere tutte le regioni dalla lista di quelle che devono essere scelte
    //randomicamente nel run di Nation, ovvero rimuove tutte le regioni dalla lista
    //regionsToExec
    public void removeExecRegions(){
        for(Iterator<Regione> i = regionsToExec.iterator(); i.hasNext();) {
            Regione num = i.next();
            i.remove();
        }
    }



    //METODO VERIFICA REGOLE TRANSIZIONE
    //Questo metodo prende come parametri una regione chiamata region e permettre verificare
    //le regole di transizione per la regione passata come parametro: alla fine del metodo si
    //vedra' se la nazione puo' o non puo' mantenere il controllo della regione.
    //come prima cosa richiama il metodo refreshNeighboringRegions della classe Regione e questo
    //aggiorna le regioni che confinano alla regione passata da parametro.
    //In seguito viene presa la lista di regioni chiamata alleate che contiene tutte
    //le regioni confinanti alleate alla regione presa da parametro e vengono prese queste regioni
    //(per trovare le regioni confinanti alleate viene richiamato il metodo getRegioniConfinantiAlleate
    //della classe Regione). Questa lista e' stata creata nella classe Regione.
    //Viene poi presa un'altra lista di regioni chiamata sconosciute che contiene tutte
    //le regioni confinanti non alleate alla regione presa da parametro e vengono prese queste regioni
    //(per trovare le regioni confinanti alleate viene richiamato il metodo getRegioniConfinantiSconosciute
    //della classe Regione). Questa lista e' stata creata nella classe Regione.
    //Viene settata poi una variabile booleana chiamata mantieniControllo a flase, questa variabile
    //serve a verificare se bisogna mantenere o no il controllo della regione passata da parametro
    //e il risultato verra' determinato dalle regole di transizione.
    //Si passa in seguito alla verifica delle regole di transazione.
    //Se la regione e' sterile (per vedere il tipo della regione viene chiamato il metodo getStato della
    //classe Regione) e ha almeno due regioni confinanti allete, allora per ogni territorio alleato
    //bisogna vedere se almeno uno e' fertile e se si si setta la variabile booleana fertile a true,
    //quindi la variabile fertile serve per vedere se almeno uno dei territori alleati e' fertile
    //Quindi si passa a controllare poi se la variabile fertile e' true (cio' significa che si sta
    //controllando se almeno una delle regioni alleate e' fertile) allora viene settata la variabile
    //mantieniControllo a true, cosi la nazione puo' mantenere il controllo della regione passata da paramentro.
    //Altrimeneti, se la regione e' sterile (per vedere il tipo della regione viene chiamato il metodo
    //getStato della classe Regione) ma non ha almeno due regioni confinanti allete, allora per ogni
    //territorio non alleato (ovvero per ogni territorio sconosciuto) bisogna vedere se almeno uno e'
    //fertile e se si si setta la variabile mantieniControllo a true, cosi la nazione puo' mantenere il
    //controllo della regione passata da paramentro.
    //Altrimenti, se la nazione e' fertile viene settata la variabile mantieniControllo a true, cosi
    //la nazione puo' mantenere il controllo della regione passata da paramentro.
    //Infine se la variabile mantieniControllo e' false, allora la nazione perde il controllo della
    //regione passata da parametro: quindi se e' stato stretto un patto sulla regione viene sciolto
    // e le caratteristiche della regione vengono modificate in maniera che questa regione non faccia piu'
    // parte della nazione, per cui viene richiamato il metodo resetRegion della classe regione e viene
    // rimossa la regione dalla lista regioni (che contiene le regioni posseduti dalla nazione)
    public void verificaRegoleTransizione(Regione region){
        region.refreshNeighboringRegions();      								//Aggiorna le regioni che confinano alla regione passata da parametro
        ArrayList<Regione> alleate=region.getRegioniConfinantiAlleate();        //Regioni confinanti alleate
        ArrayList<Regione> sconosciute=region.getRegioniConfinantiSconosciute();//Regioni confinanti non alleate
        boolean mantieniControllo = false;
        //VERIFICA DI SEGUITO LE REGOLE DI TRANSAZIONE
        //SE LA REGIONE E' STERILE
        if(region.getTipo().equals("sterile")){
            //SE HA ALMENO DUE TERRITORI CONFINANTI ALLEATI
            if(alleate.size() >= 2){
                boolean fertile = false;
                //SE ALMENO UN DEI TERRITRI ALLEATI E' FERTILE SE NE TIENE CONTO
                for(int i=0; i < alleate.size(); i++){
                    if(alleate.get(i).getTipo().equals("fertile")){
                        fertile = true;
                    }
                }
                //SE ALMENO UNA DELLE REGIONI ALLEATE E' FERTILE
                if(fertile == true){
                    mantieniControllo = true;
                }
            }
            //ALTRIMENTI SE NON HA ALMENO DUE TERRITORI CONFINANTI ALLEATI
            else{
                for(int i=0; i < sconosciute.size(); i++){
                    if(sconosciute.get(i).getTipo() == "fertile"){
                        mantieniControllo = true;
                    }
                }
            }
        }
        //ALTIMENTI, SE LA REGIONE E' FERTILE
        else{
            mantieniControllo = true;
        }
        //SE MANTIENI CONTROLLO E' FALSE
        if (mantieniControllo == false){
            if(region.getAlleanza() != null){ //Se e' stato stretto un patto sulla regione viene sciolto
                interrompiAlleanza(region.getAlleanza());
            }
            region.resetRegion();
            regioni.remove(region);

        }
    }



    //METODO GUERRA
    //Questo metodo prende come parametri una nazione enemy e una regione region e permette
    //di andare in guerra con la nazione (passata da parametro) che detiene la regione
    //passata da parametro che si vuole conquistare.
    //Come prima cosa la nazione presa da paremetro, ovvero la nazione che difende il proprio
    //territorio subisce un piccolo calo nell'economia, nella popolazione e nelle risorse
    //entrando in guerra (richiamando il metodo warPayment della classe Nation).
    //Ora, a parita' di eta' della nazione quella attaccante (nazione this) e della nazione che
    //difende (nazione enemy), se la nazione attaccante ha piu' risorse di quella che difende
    //allora vince la nazione attaccante, per cui conquista la regione (presa da parametro)
    //della nazione che difende e ne trae profitto (richiamando il metodo conquistaRegione della
    //classe Nation): in questo caso la nazione che difende paga di nuovo un tributo di guerra con
    // il metodo warPayment.
    //Altrimenti, se la nazione attaccante ha lo stesso numero di risorse o ha meno risorse della
    //nazione che difende, allora perde la nazione attaccante, per cui subisce un piccolo calo
    //nell'economia, nella popolazione e nelle risorse (richiamando il metodo warPayment della
    //classe Nation).
    //Invece se la nazione attaccante (nazione this) e la nazione che difende (nazione enemy)
    //non hanno la stessa eta', allora vince la nazione che ha l'eta' piu' avanzata.
    //per cui, se la nazione attaccante ha un eta' antica, allora possiamo subito dire che ha
    //perso perche' sicuramente l'eta' della nazione che difende sara' piu' avanzata, percio'
    //la nazione che attacca subisce un piccolo calo nell'economia, nella popolazione e nelle
    //risorse (richiamando il metodo warPayment della classe Nation).
    //Altrimenti, se la nazione che attacca ha l'eta' intermedia, allora bisogna vedere l'eta della
    //nazione che difende, per cui se la nazione che difende ha l'eta' antica allora la nazione
    //attaccante vince e conquista la regione della nazione che difendeva e ne trae profitto
    //(richiamando il metodo conquistaRegione della classe Nation): in questo caso la nazione che difende
    // paga di nuovo un tributo di guerra con il metodo warPayment. Mentre se la nazione che difende
    //he l'eta' moderna allora vince, per cui la nazione che attacca subisce un piccolo calo
    //nell'economia, nella popolazione e nelle risorse (richiamando il metodo warPayment della classe Nation).
    //Infine, se la nazione che attacca ha l'eta' moderna allora possiamo subito dire che ha
    //vinto perche' sicuramente l'eta' della nazione che difende sara' memno avanzata, percio'
    //conquista la regione della nazione che difendeva e ne trae profitto (richiamando il metodo
    //conquistaRegione della classe Nation): in questo caso la nazione che difende paga di nuovo
    // un tributo di guerra con il metodo warPayment.
    public void guerra(Nation enemy, Regione region){
        warPayment(enemy);
        //SE LE DUE NAZIONI HANNO LA STESSA ETA'
        if(this.getAge() == enemy.getAge()){
            //SE LA NAZIONE ATTACCANTE HA PIU' RISORSE DELLA NAZIONE CHE DIFENDE
            if(this.getRisorse() > enemy.getRisorse()){
                warPayment(enemy);  //La nazione che difende perde risorse, abitanti e denaro
                this.conquistaRegione(region);
            }
            //ALTRIMENTI, SE LA NAZIONE ATTACCANTE HA MENO O HA LO STESSO NUMERO DI RISORSE
            //DELLA NAZIONE CHE DIFENDE
            else{
                warPayment(this);//La nazione che attacca perde risorse, abitanti e denaro
            }
        }
        //ALTRIMENTI, SE LE DUE NAZIONI NON HANNO LA STESSA ETA'
        else{
            //SE LA NAZIONE ATTACCANTE HA L'ETA' ANTICA
            if(this.getAge().equals("ANTICA")){
                warPayment(this);//La nazione che attacca perde risorse, abitanti e denaro
            }
            //ALTIMENTI, SE LA NAZIONE ATTACCANTE HA L'ETA' INTEMEDIA
            else if(this.getAge().equals("INTERMEDIA")){
                //SE LA NAZIONE CHE DIFENDE HA L'ETA' ANTICA
                if(enemy.getAge().equals("ANTICA")){
                    warPayment(enemy);  //La nazione che difende perde risorse, abitanti e denaro
                    this.conquistaRegione(region);
                }
                //ALTRIMENTI, SE LA NAZIONE CHE DIFENDE HA L'ETA' MODERNA
                else{
                    warPayment(this);//La nazione che attacca perde risorse, abitanti e denaro
                }
            }
            //ALTRIMENTI, SE LA NAZIONE CHE ATTACCA HA L'ETA' MODERNA
            else{
                warPayment(enemy);  //La nazione che difende perde risorse, abitanti e denaro
                this.conquistaRegione(region);
            }
        }
    }



    //METODO WAR PAYMENT
    //Questo metodo prende come parametri una nazione defender e viene utilizzato quando
    //una nazione che difende un territorio entra in guerra e quando la nazione attaccante
    //viene sconfitta.
    //Per cui la nazione presa come parametro perde un sesto del totale del suo denaro,
    //perde 30 abitanti e infine perde alcune risorse e aggiorna la sua eta'(richiamando
    //il metodo consumaRisorse della classe Nation)
    public void warPayment(Nation defender){
        defender.denaro -= defender.denaro/6;
        defender.numAbitanti -= 30;
        defender.consumaRisorse();
    }



    //METODO INTERROMPI ALLEANZA
    //Questo metodo prende come parametri un Accordo chiamato alleanza e permette di
    //interrompere l'alleanza (presa come parametro) tra le nazioni, se sono stato sciolti
    //tutti gli accordi tra quelle due nazioni. Quindi se le due nazioni non hanno piu'
    //accordi allora l'alleanza finisce, mentre se quelle due nazioni hanno accordi l'allenza
    //non puo' finire.
    //Per cui, come prima cosa, viene presa la regione sulla quale si vuole interrompere il patto
    //(questa regione e' presa richiamando il metodo getRegionePatto della classe Accordo)e su questa
    //regione viene rotto il patto (richiamando il metodo rompiPatto della classe Regione).
    //ora siccome si e' rotto il patto, allora viene settato il testo di quella regione (ricorda che
    //una regione e' un bottone) con la scritta "" (stringa vuota).
    //In seguito viene rimosso il patto tra gli accordi proposti della nazione che lo ha proposto e viene
    //rimosso il patto anche tra gli accordi accettati della nazione che lo ha accettato.
    public void interrompiAlleanza(Accordo alleanza){
        alleanza.getRegionePatto().rompiPatto();
        alleanza.getRegionePatto().setText("");
        //Rimuove il patto tra gli accordi proposti della nazione che lo ha proposto
        alleanza.getNazioneChePropone().getAccordiProposti().remove(alleanza);
        //Rimuove il patto tra gli accordi accettati della nazione che lo ha accettato
        alleanza.getNazioneCheAccetta().getAccordiAccettati().remove(alleanza);
    }



    //METODO PROPONI ACCORDO
    //Questo metodo prende una nazione accettatore e una regione region e permette di
    //stringere un'alleanza con un'altra nazione: l'alleanza creera' un patto fatto sulla
    //regione presa da parametro e questa regione fa parte della nazione che deve decidere
    //se accettare o meno l'alleanza.
    //Se non accettera' le due nazioni saranno acerrime nemiche e andranno per sempre in guerra.
    //Come prima cosa si vede  se il denaro della nazione che propone l'accordo e' maggiore o uguale
    //a 2/3 di quello della nazione che dovrebbe accettare l'accordo, allora viene accettato
    //l'accordo sulla regione presa da paramentro (questo perche' la nazione che accetta deve
    //poter riscuotere delle tasse dalla nazione che propone).
    //Quindi, viene creato un nuovo accordo chiamato pattoEconomico (viene creato richiamando
    //il costruttore con due parametri della classe Accordo), e in seguito vine aggiunto
    //il pattoEconomico  a quelli proposti della nazione che propone e viene anche aggiunto
    //il pattoEconomico a quelli accettati della nazione che accetta.
    //Per cui ora si e' stretta l'alleanza su quella regione allora viene settato il testo di quella regione
    //(ricorda che una regione e' un bottone) con la scritta "Stretto un patto".
    //Altimenti, se il denaro della nazione che propone l'accordo non e' maggiore o uguale
    //a 2/3 di quello della nazione che dovrebbe accettare l'accordo, allora non viene accettato e le nazioni
    //sciolgono tutti gli accordi stretti in precedenza(se esistono).

    //In seguito vengono aggiornati gli accordi proposti della  nazione che propone, per cui per ogni alleanza dentro la lista
    //accordiProposti (presa richiamando il metodo getAccordiProposti) se la nazione che riceve l'accordo
    //non e' la nazione che e' appena stata dichiarata nemica allora l'accordo non viene sciolto,
    //altrimenti se la nazione che riceve l'accordo e' la nazione che e' appena stata dichiarata nemica
    //allora l'accordo su quella regione viene sciolto (richiamando il metodo rompiPatto della classe Regione)
    //e percio' viene settato il testo di quella regione  (ricorda che una regione e' un bottone)
    //con la scritta "" (stringa vuota).
    //In seguito vengono aggiornati gli accordi accettati della nazione che propone per cui per ogni alleanza dentro la lista
    //accordiAccetatti (presa richiamando il metodo getAccordiAccettati) se la nazione che propone l'accordo
    //non e' la nazione che e' appena stata dichiarata nemica allora l'accordo non viene sciolto,
    //altrimenti se la nazione che propone l'accordo e' la nazione che e' appena stata dichiarata nemica
    //allora l'accordo su quella regione viene sciolto (richiamando il metodo rompiPatto della classe Regione)
    //e percio' viene settato il testo di quella regione  (ricorda che una regione e' un bottone)
    //con la scritta "" (stringa vuota).
    //In seguito vengono aggiornati gli accordi accettati della nazione che accetta  per cui per ogni alleanza dentro la lista
    //accordiProposti (presa richiamando il metodo getAccordiProposti) se la nazione che riceve l'accordo
    //non e' la nazione che e' appena stata dichiarata nemica allora l'accordo non viene sciolto,
    //altrimenti se la nazione che riceve l'accordo e' la nazione che e' appena stata dichiarata nemica
    //allora l'accordo su quella regione viene sciolto (richiamando il metodo rompiPatto della classe Regione)
    //e percio' viene settato il testo di quella regione  (ricorda che una regione e' un bottone)
    //con la scritta "" (stringa vuota).
    //In seguito vengono aggiornati gli accordi accettati della nazione che accetta per cui per ogni alleanza dentro la lista
    //accordiAccetatti (presa richiamando il metodo getAccordiAccettati) se la nazione che propone l'accordo
    //non e' la nazione che e' appena stata dichiarata nemica allora l'accordo non viene sciolto,
    //altrimenti se la nazione che propone l'accordo e' la nazione che e' appena stata dichiarata nemica
    //allora l'accordo su quella regione viene sciolto (richiamando il metodo rompiPatto della classe Regione)
    //e percio' viene settato il testo di quella regione  (ricorda che una regione e' un bottone)
    //con la scritta "" (stringa vuota).
    //Infine visto che l'accordo non e' stato accettato, la nazione che aveva proposto l'accordo va in guerra con
    // quella che non l'aveva accettato.
    public void proponiAccordo(Nation accettatore, Regione region){
        //SE IL DENARO DELLA NAZIONE CHE PROPONE L'ACCORDO E MAGGIORE O UGUALE A 2/3 DEL
        //DENARO DELLA NAZIONE CHE RICEVE L'ACCORDO
        if(this.getDenaro() >= (accettatore.getDenaro() - accettatore.getDenaro()/3.0)){
            //Viene aggiunto il patto a quelli proposti della nazione che propone
            Accordo pattoEconomico = new Accordo(this, accettatore, region);
            //Viene aggiunto il patto a quelli proposti della nazione che propone
            this.accordiProposti.add(pattoEconomico);
            //Viene aggiunto il patto a quelli accettati della nazione che accetta
            accettatore.accordiAccettati.add(pattoEconomico);
            region.setText("p");
        }
        //ALTRIMENTI, SE L'ACCORDO NON VIENE ACCETTATO
        else{
            //Vengono sciolti tutti gli accordi tra le due nazioni (se presenti)
            //Le liste conterrano tutti gli accordi delle due nazioni(accettati e proposti) ma senza quelli stretti insieme
            ArrayList<Accordo> accordiPropostiDaChiPropone = new ArrayList<>();
            System.out.println(accordiPropostiDaChiPropone);
            ArrayList<Accordo> accordiAccettatiDaChiPropone = new ArrayList<>();
            ArrayList<Accordo> accordiPropostiDaChiNonAccetta = new ArrayList<>();
            ArrayList<Accordo> accordiAccettatiDaChiNonAccetta = new ArrayList<>();
            //RIMUOVE ACCORDI TRA QUELLI DELLA NAZIONE CHE AVEVA PROPOSTO L'ACCORDO
            //Aggiorna gli accordi proposti
            for(Accordo alleanza: this.getAccordiProposti()){
                //SE LA NAZIONE CHE RICEVE L'ACCORDO NON E' LA NAZIONE CHE NON HA
                // ACCETTATO IL PATTO, ALLORA L'ACCORDO NON VIENE SCIOLTO
                if(alleanza.getNazioneCheAccetta() != accettatore){
                    accordiPropostiDaChiPropone.add(alleanza);
                }
                //ALTRIMENTI, SE LA NAZIONE CHE RICEVE L'ACCORDO E' LA NAZIONE CHE NON HA
                // ACCETTATO IL PATTO, ALLORA L'ACCORDO VIENE SCIOLTO
                else{
                    alleanza.getRegionePatto().rompiPatto();
                    alleanza.getRegionePatto().setText("");
                }
            }
            //Viene aggiornata la nuova lista di accordi proposti
            accordiProposti = accordiPropostiDaChiPropone;

            //Aggiorna gli accordi accettati
            for(Accordo alleanza: this.getAccordiAccettati()){
                //SE LA NAZIONE CHE PROPONE L'ACCORDO NON E' LA NAZIONE CHE NON HA
                // ACCETTATO IL PATTO, ALLORA L'ACCORDO NON VIENE SCIOLTO
                if(alleanza.getNazioneChePropone() != accettatore){
                    accordiAccettatiDaChiPropone.add(alleanza);
                }
                //ALTRIMENTI, SE LA NAZIONE CHE RICEVE L'ACCORDO E' LA NAZIONE CHE NON HA
                // ACCETTATO IL PATTO, ALLORA L'ACCORDO VIENE SCIOLTO
                else{
                    alleanza.getRegionePatto().rompiPatto();
                    alleanza.getRegionePatto().setText("");
                }
            }
            //Viene aggiornata la nuova lista di accordi accettati
            accordiAccettati = accordiAccettatiDaChiPropone;           //Viene aggiornata la nuova lista di accordi accettati

            //RIMUOVE ACCORDI TRA QUELLI DELLA NAZIONE CHE NON HA ACCETTATO L'ACCORDO
            //Aggiorna gli accordi proposti
            for(Accordo alleanza: accettatore.getAccordiProposti()){
                //SE LA NAZIONE CHE ACCETTA L'ACCORDO NON E' LA NAZIONE CHE HA PROPOSTO IL PATTO
                // , ALLORA L'ACCORDO NON VIENE SCIOLTO
                if(alleanza.getNazioneCheAccetta() != this){
                    accordiPropostiDaChiNonAccetta.add(alleanza);
                }
                //ALTRIMENTI, SE LA NAZIONE CHE ACCETTA L'ACCORDO E' LA NAZIONE CHE HA PROPOSTO IL PATTO
                // , ALLORA L'ACCORDO VIENE SCIOLTO
                else{
                    alleanza.getRegionePatto().rompiPatto();
                    alleanza.getRegionePatto().setText("");
                }
            }
            //Viene aggiornata la nuova lista di accordi proposti
            accettatore.accordiProposti = accordiPropostiDaChiNonAccetta;

            //Aggiorna gli accordi accettati
            for(Accordo alleanza: accettatore.getAccordiAccettati()){
                if(alleanza.getNazioneChePropone() != this){
                    //SE LA NAZIONE CHE ACCETTA L'ACCORDO NON E' LA NAZIONE CHE HA PROPOSTO IL PATTO,
                    // ALLORA L'ACCORDO NON VIENE SCIOLTO
                    accordiAccettatiDaChiNonAccetta.add(alleanza);        //L'accordo non viene sciolto
                }
                //ALTRIMENTI, SE LA NAZIONE CHE ACCETTA L'ACCORDO E' LA NAZIONE CHE HA PROPOSTO
                // IL PATTO, ALLORA L'ACCORDO VIENE SCIOLTO
                else{
                    alleanza.getRegionePatto().rompiPatto();
                    alleanza.getRegionePatto().setText("");
                }
            }
            //Viene aggiornata la nuova lista di accordi accettati
            accettatore.accordiAccettati = accordiAccettatiDaChiNonAccetta;
            //Infine visto che l'accordo non e' stato accettato, la nazione che aveva proposto l'accordo
            // va in guerra con quella che non l'aveva accettato
            this.guerra(accettatore, region);
        }
    }

    //METODO RISCUOTI TASSE
    //Permette di riscuotere le tasse dalle nazioni alle quali sono stati accettati degli accordi.
    //Quindi per ogni accordo accettato, la nazione che ha proposto l'accordo (presa con il metodo
    //getNazioneChePropone della classe Accordo) pasa la tassa, per cui gli viene sottratta una
    //cifra di denaro di 400, mentrela nazione che ha accettato l'accordo, riscuote questa cifra
    //e quindi gli viene sommata una cifra di denaro di 400
    public void riscuotiTasse(){
        for(int i=0; i<accordiAccettati.size(); i++){
            this.accordiAccettati.get(i).getNazioneChePropone().denaro -= 400.0; //La nazione che ha proposto l'accordo paga la tassa
            this.denaro += 400.0;                                                //La nazione che ha accettato l'accordo riscuote la tassa
        }
    }



    //METODO CLONE CHARACTERS
    //Questo metodo prende come paramentro una Nazione, che rappresenta la nazione che ssi vuole clonare.
    //Ed e' usato nel caso si sta clonando una nazione per runnarla di nuovo senza perdere traccia dei dati, in quel caso
    //quindi questa sarebbe una nazione clone (le uniche nazioni non clone sono quelle del primo turno di gioco).
    //In particolare copia i dati della nazione che si vuole clonare in questa nazione, (nome e colore sono gia' stati copiati)
    //Questo metodo prende come parametro la nuova nazione da clonare e copia dentro questa l'eta' della nazione,
    //la quantita' di denaro, la quantita' di risorse, il numero di abitanti e copia l'oggetto griglia sul quale la nazione
    //svolge delle azioni. Vengono inoltre copiate le regioni, gli accordi e lo stato della nazione(se e' viva o morta).
    public void cloneCharacters(Nation nazioneDaClonare){
        this.age = nazioneDaClonare.getAge();                        	//Copia l'eta'
        this.denaro = nazioneDaClonare.getDenaro();                  	//Copia la quantita' di denaro
        this.risorse = nazioneDaClonare.getRisorse();                	//Copia la quantita' di risorse
        this.numAbitanti = nazioneDaClonare.getNumAbitanti();        	//Copia il numero di abitanti
        this.gridController = nazioneDaClonare.getGridController();  	//Copia l'oggetto griglia sul quale la nazione svolge delle azioni
        this.vivo = nazioneDaClonare.getStato();                        //Copia lo stato e cioe' se la nazione e' viva o morta
        for(int i=0; i < nazioneDaClonare.getRegioni().size(); i++){ 	//Itero le regioni della nazione da clonare
            //Passo alle regioni il nuovo oggetto nazioneclonato che gli appartiene
            nazioneDaClonare.getRegioni().get(i).setNazione(nazioneDaClonare.getName(), nazioneDaClonare.getColor(), this);
            //Inoltre anche i thread delle regioni se sono stati startati(start()) non possono piu' eseguire il loro run()
            //quindi bisogna crearne una nuova istanza
            nazioneDaClonare.getRegioni().get(i).setNewThread();
            //Infine viene aggiunta la regione alla nazione clonata(this.addRegion("regione");)
            addRegion(nazioneDaClonare.getRegioni().get(i));
        }
        for(int i=0; i<nazioneDaClonare.getAccordiProposti().size();i++){
            nazioneDaClonare.getAccordiProposti().get(i).setNazioneChePropone(this);
            this.accordiProposti.add(nazioneDaClonare.getAccordiProposti().get(i));
        }
        for(int i=0; i<nazioneDaClonare.getAccordiAccettati().size();i++){
            nazioneDaClonare.getAccordiAccettati().get(i).setNazioneCheAccetta(this);
            this.accordiAccettati.add(nazioneDaClonare.getAccordiAccettati().get(i));
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
    //Il run prevede lo svolgimento del turno della nazione nella simulazione.
    //Compe prima cosa,siccome la nazione ha iniziato ad eseguire il codice del suo run()
    //allora viene settata la variabile active a true (perche' active serve a vedere se la
    //nazione ha finito di svolgere il suo turno o meno).
    //Poi si perde un secondo di tempo in maniera da notare i cambiamenti tra un turno e l'altro
    //(con il metodo sleep).
    //In seguito si passa a controllare se la nazione ha occupato tutta la griglia, perchÃƒÂ¨
    //nel caso la nazione ha occupato tutta la griglia non ci sono regioni che confinano con regioni
    //non alleate, quindi si possono verificare direttamente le regole di transizione su una regione
    //qualsiasi della griglia che viene scelta casualmente.
    //Per verificare se la nazione ha occupato tutta la griglia biene preso la lunghezza della lista
    //regione e si vede se e' uguale al prodotto righe per colonne della griglia.
    //Quindi se la lunghezza della lista regioni e' uguale al prodotto righe per colonne, allora
    //significa che la nazione ha occupato tutta la griglia per cui viene creato un oggetto di tipo
    //Random chiamato rand1 e viene generato casualmente un intero che rappresenta l'indice della
    //regione sulla quale verificare le regole di transizione e questo intero e' chiamato regionToControl.
    //A questo punto se l'intero regionToControl e' uguale alla lunghezza della lista regioni sottrae 1
    //all'indice regionToControl.
    //Ora, per la regione scelta bisogna verificare le regole di transizione le quali ci diranno
    //se la nazione manterra' o meno il controllo su quella regione, per cui viene richiamato
    //il metodo verificaRegoleTransizione della classe Nation.
    //Altrimenti se non e' stata occupata tutta la griglia vuol dire che ci sono delle regioni
    //che confinano con regioni non alleate, e quindi bisogna scegliere una regione di questo
    //tipo per verificarne le regole di transizione, per cui vine richiamato il metodo refreshRegionsToExec
    //per aggiornare l'elenco delle regioni che possono essere scelte randomicamente per essere conquistate
    //e per eseguire il loro thread.
    //per cui viene creato un oggetto di tipo Random chiamato rand1 e viene generato casualmente
    //un intero che rappresenta l'indice della regione sulla quale verificare le regole di transizione
    //e questo intero e' chiamato regionToControl.
    //A questo punto se l'intero regionToControl e' uguale alla lunghezza della lista regionsToExec
    //sottrae 1 all'indice regionToControl (ovvero la lista delle regioni che confinano con almeno
    //un territorio che non possiede la nazione).
    //Ora, per la regione scelta bisogna verificare le regole di transizione le quali ci diranno
    //se la nazione manterra' o meno il controllo su quella regione, per cui viene richiamato
    //il metodo verificaRegoleTransizione della classe Nation.
    //Se la nazione a seguito dell'applicazione delle regole di transizione e' rimasta senza regioni
    //(ad esempio nel  caso in cui una nazione ha una sola regione e applicando le regole
    //di transizione la perde) allora la nazion muore, per cui viene settata la variabile vivo a false,
    //scioglie tutti gli accordi fatti con altre nazioni(se esistono) e siccome ha finito di eseguire
    // il codice del suo run() setta la variabile active a false.
    //Infine la nazione avvisa il thread che deteneva la griglia e gestiva i turni che
    //il suo turno e' finito.
    //Altrimenti, se la nazione non perde tutte le sue regioni si sceglie una nuova regione random,
    //ma questa volta per conquistare nuove regioni(o stringere alleanze).
    //Per cui se le regioni possedute dalla nazione sono uguali al numero di celle della griglia
    //allora la nazione possiede tutta la griglia e quindi non ci saranno celle che confinano
    //con regioni che non sono della stessa nazione.
    //Percio' si va ad eseguire una regione qualsiasi posseduta dalla nazione, cosi viene creato
    //un oggetto di tipo Random chiamato rand2 e viene generato casualmente un intero che
    //rappresenta l'indice della regione da startare e questo intero e' chiamato regionToStart.
    //A questo punto se l'intero regionToStart e' uguale alla lunghezza della lista regionsToExec
    //sottrae 1 all'indice regionToStart e poi fa lo start della regione all'indice regionTostart.
    //Altrimenti, se le regioni possedute dalla nazione non sono uguali al numero di celle della griglia
    //allora la nazione non possiede tutta la griglia, per cui bisognera' scegliere tra le regioni
    //che confinano con almeno una regione non alleata.
    //per cui aggiorna l'elenco delle regioni che possono essere scelte randomicamente per eseguire
    //il lore thread (richiamando il metodo refreshRegionsToExec della classe Nation), cosi viene creato
    //un oggetto di tipo Random chiamato rand2 e viene generato casualmente un intero che
    //rappresenta l'indice della regione da startare e questo intero e' chiamato regionToStart.
    //A questo punto se l'intero regionToStart e' uguale alla lunghezza della lista regionsToExec
    //sottrae 1 all'indice regionToStart e poi fa lo start della regione all'indice regionTostart.
    //In seguito la nazione va in attesa che si arrivi alla fine del suo turno per svolgere le azioni
    //successive, e quando viene risvegliata vengono richiamati il metodi increasePopulation (della
    //classe Nation) il quale aumenta o diminuisce il numero degli abitanti della nazione in base
    //ai terreni posseduti, il metodo incassaDenaro (della classe Nation) il quale aumenta
    //il denaro della nazione in relazione al numero delle risorse totali e degli abitanti,
    //il metodo riscuotiTasse (della classe Nation) il quale riscuote tasse da eventuali
    //alleanze in cui la nazione ha accettato l'accordo e viene richiamato infine il metodo
    //consumaRisosse (della classe nation) il quale consuma le risorse della nazione e aggiorna l'eta
    //della nazione.
    //Dopo cio, la nazione finisce di eseguire il suo turno per cui setta la variabile active a false,
    //e in seguito la nazione avvisa il thread che deteneva la griglia e gestiva i turni che
    //il suo turno e' finito e si puo' passare al turno della nazione successiva.
    //Infine se il numero di abitanti e' minore di 10, viene messa la variabile boolena vivo a false,
    //cosi la nazione muore, sciogliendo inoltre(se esistono) tutti gli accordi fatti con altre nazioni.
    public synchronized void run() {
        try{
            this.active = true;
            sleep(1000);
            //Se la nazione ha occupato tutta la griglia non ci sono regioni che confinano con regioni non alleate,
            //quindi posso verificare direttamente le regole di transizione su una regione qualsiasi della griglia
            //che scelgo casualmente
            if(this.regioni.size() == (this.gridController.getNumeroRighe() * this.gridController.getNumeroColonne())){
                Random rand1 = new Random();
                int regionToControl = rand1.nextInt((regioni.size())); 	//Indice della regione sulla quale verificare le regole di transizione
                //SE L'INDICE DELLA REGIONE, GENERATO CASUALMENTE, E' UGUALE ALLA LUGHEZZA DELLA LISTA REGIONI
                if(regionToControl == regioni.size()){
                    regionToControl -= 1;
                }
                //Per la regione scelta vengono verificate le regole di transizioni le quali
                //ci diranno se la nazione manterra' o meno il controllo su quella regione
                System.out.println(regioni.get(regionToControl).toString());
                verificaRegoleTransizione(regioni.get(regionToControl));
            }
            //Altrimenti se non e' stata occupata tutta la griglia vuol dire che ci sono delle regioni che confinano con regioni non alleate,
            //e quindi bisogna scegliere una regione di questo tipo per verificarne le regole di transizione
            else{
                refreshRegionsToExec();
                Random rand1 = new Random();
                int regionToControl = rand1.nextInt((regionsToExec.size()));
                if(regionToControl == regionsToExec.size()){
                    regionToControl -= 1;
                }
                //Per la regione scelta vengono verificate le regole di transizioni le quali ci diranno se la nazione manterra' o meno
                //il controllo su quella regione
                System.out.println(regionsToExec.get(regionToControl).toString());
                verificaRegoleTransizione(regionsToExec.get(regionToControl));
            }
            //SE LA NAZIONE PERDE TUTTE LE SUE REGIONI
            if(this.regioni.size() == 0){
                this.vivo = false;
                //Scioglie tutti gli accordi che aveva stretto
                for(int i=0; i<accordiProposti.size();i++){  //Scioglie gli accordi proposti rimuovendoli da quelli accettati
                                                             //delle nazioni che l'hanno accettati
                    accordiProposti.get(i).getNazioneCheAccetta().getAccordiAccettati().remove(accordiProposti.get(i));
                    accordiProposti.get(i).getRegionePatto().setText("");
                }
                for(int i=0; i<accordiAccettati.size();i++){  //Scioglie gli accordi accettati rimuovendoli da quelli proposti
                                                              //delle nazioni che l'hanno proposti
                    accordiAccettati.get(i).getNazioneChePropone().getAccordiProposti().remove(accordiAccettati.get(i));
                    accordiAccettati.get(i).getRegionePatto().setText("");
                }
                this.active = false;
                this.gridController.sveglia();
            }
            //ALTRIMENTI, SE LA NAZIONE NON PERDE TUTTE LE SUE REGIONI
            else{
                //SE LE REGIONI POSSEDUTE DALLA NAZIONE DELLA GRIGLIA SONO UGUALI AL NUMERO DI
                //CELLE DELLA GRIGLIA, ALLORE SIGNIFICA CHE LA NAZIONE POSSIEDE TUTTE LE REGIONI
                //PRESENTI SULLA GRIGLIA
                if(this.regioni.size() == (this.gridController.getNumeroRighe() * this.gridController.getNumeroColonne())){
                    Random rand2 = new Random();
                    int regionToStart = rand2.nextInt((regioni.size()));
                    if(regionToStart == regioni.size()){
                        regionToStart -= 1;
                    }
                    regioni.get(regionToStart).startRegionThread();  	  //Fa lo start() di una regione casuale nella lista di quelle da eseguire
                }
                //ALTRIMENTI, SE LE REGIONI POSSEDUTE DALLA NAZIONE DELLA GRIGLIA NON SONO UGUALI AL NUMERO DI
                //CELLE DELLA GRIGLIA, ALLORE SIGNIFICA CHE LA NAZIONE NON POSSIEDE TUTTE LE REGIONI
                //PRESENTI SULLA GRIGLIA, PER CUI BISOGNERA' TRA QUELLE CHE CONFINANO CON ALMENO
                //UNA REGIONE NON ALLEATA
                else{
                    refreshRegionsToExec();
                    Random rand2 = new Random();
                    int regionToStart = rand2.nextInt((regionsToExec.size()));
                    if(regionToStart == regionsToExec.size()){
                        regionToStart -= 1;
                    }
                    regionsToExec.get(regionToStart).startRegionThread();  	  //Fa lo start() di una regione casuale nella lista di quelle da eseguire
                }
                System.out.println(this.getName() + "nazione");
                wait();            									      	  //La nazione va in attesa che si arrivi alla fine del suo turno per svolgere le azioni successive
                System.out.println("Sono di nuovo in gioco!");
                this.increasePopulation();
                this.incassaDenaro();
                this.riscuotiTasse();                               //Riscuote tasse da eventuali alleanze in cui la nazione ha accettato l'accordo
                this.consumaRisorse();
                this.active = false;          						//Avendo finito di eseguire il codice del suo run() setta active a false
                this.gridController.sveglia(); 						/*La nazione avvisa il thread che deteneva la griglia e gestiva i turni che
                													il suo turno e' finito e si puo' passare al turno della nazione successiva*/
                //SE IL NUMERO DI ABITANTI E' MINORE DI 10
                if(this.getNumAbitanti() < 10){
                    vivo = false;
                    //Scioglie tutti gli accordi che aveva stretto
                    for(int i=0; i<accordiProposti.size();i++){  //Scioglie gli accordi proposti rimuovendoli da quelli accettati
                                                                 //delle nazioni che l'hanno accettati
                        accordiProposti.get(i).getNazioneCheAccetta().getAccordiAccettati().remove(accordiProposti.get(i));
                        accordiProposti.get(i).getRegionePatto().setText("");
                    }
                    for(int i=0; i<accordiAccettati.size();i++){  //Scioglie gli accordi accettati rimuovendoli da quelli proposti
                                                                  //delle nazioni che l'hanno proposti
                        accordiAccettati.get(i).getNazioneChePropone().getAccordiProposti().remove(accordiAccettati.get(i));
                        accordiAccettati.get(i).getRegionePatto().setText("");
                    }
                    this.removeAllRegions();
                }
            }
        }
        catch (InterruptedException i){    						//Se si ha un interrupt di questa nazione si ottiene un eccezione
            System.out.println("Vita nazione interrotta!");
        }
    }
}