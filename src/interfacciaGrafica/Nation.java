package interfacciaGrafica;
import javafx.application.Platform;
import org.controlsfx.control.PopOver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

//Ogni nazione e' composta dal nome, dal colore, dall'eta', dal denaro, dalle risorse
//e dal numero di abitanti.
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


    //COSTRUTTORE CON QUATTRO PARAMETRI
    public Nation(String nome, Eta eta, int numSterili, int numFertili) {
        this.age= eta;
        this.nome = nome;
        this.numFertili = numFertili;
        this.numSterili =numSterili;
    }

    //METODO GET ETA
    public Eta getEta(){
        return age;
    }

    //METODO GET NOME
    public String getNome(){
        return nome;
    }

    //METODO GET NUM STERILI
    public int getNumSterili(){
        return numSterili;
    }

    //METODO GET NUM FERTILI
    public int getNumFertili(){
        return numFertili;
    }


    //Controller della griglia per avvisare (notify()) il thread che gestisce i turni
    //di passare al turno della nazione successiva
    private ControllerImpostazioniGriglia gridController;


    //Lista di regioni che rappresentano le celle assegnate e conquistate dalla nazione
    //Quindi ogni nazione avra' i suoi territori e questa lista contiene i territori
    //(le celle) posseduti dalla nazione
    private  ArrayList<Regione> regioni = new ArrayList<>();

    //Lista delle regioni che confinano con almeno un territorio che non possiede
    //la nazione,e che quindi dovranno essere scelte casualmente per eseguire il
    //thread che gli appartiene.
    private ArrayList<Regione> regionsToExec = new ArrayList<>();

    //Lista degli accordi proposti da una data nazione e poi questi accordi saranno
    //accettati da parte di un altra nazione.
    private ArrayList<Accordo> accordiProposti = new ArrayList<>();

    //Lista degli accordi proposti da una nazione e accettati da una data nazione
    private ArrayList<Accordo> accordiAccettati = new ArrayList<>();


    //COSTRUTTORE CON DUE PARAMETRI
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
    public String getColor(){
        return this.color;
    }


    //METODO GET AGE
    public Eta getAge(){
        return this.age;
    }


    //METODO GET DENARO
    public double getDenaro(){
        return this.denaro;
    }


    //METODO GET RISORSE
    public double getRisorse(){
        return this.risorse;
    }


    //METODO GET NUM ABITANTI
    public int getNumAbitanti(){
        return this.numAbitanti;
    }


    //METODO GET STATO
    public boolean getStato(){
        return this.vivo;
    }


    //METODO SET STATO
    public void setStato(Boolean stato) {
        this.vivo=stato;
    }


    //METODO SET AGE
    public void setAge(Eta eta){
        this.age = eta;
    }


    //METODO REFRESH AGE
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
    public void consumaRisorse(){
        this.risorse = risorse - (risorse / 4);
        this.refreshAge();                  //Aggiorna l'eta della nazione
    }



    //METODO INCASSA DENARO
    public void incassaDenaro(){
        this.denaro = (risorse / 2.0) + (numAbitanti*2.0);
    }



    //METODO CONQUISTA REGIONE
    public void conquistaRegione(Regione region){
        //SE LA REGIONE E' DI QUALCHE NAZIONE
        if (region.getNomeNazione()!=""){
            Nation nazione = region.getNazione();
            nazione.getRegioni().remove(region);
            //SE LA NAZIONE CHE HA APPENA PERSO LA REGIONE NON HA PIU' REGIONI
            if (nazione.getRegioni().size()==0){
                nazione.setStato(false);
                System.out.println("La nazione " + nazione.getName() + " e' morta perche' la sua ultima regione e' stata conquistata da " + this.getName());
                //Scioglie tutti gli accordi che aveva stretto
                for(int i=0; i<nazione.getAccordiProposti().size();i++){
                    nazione.getAccordiProposti().get(i).getNazioneCheAccetta().getAccordiAccettati().remove(nazione.getAccordiProposti().get(i));
                    nazione.getAccordiProposti().get(i).getRegionePatto().setText("");
                    Accordo proposto = nazione.accordiProposti.get(i);
                    Platform.runLater(
                            () -> {
                                proposto.getRegionePatto().setText("");
                            }
                    );
                }
                for(int i=0; i<nazione.getAccordiAccettati().size();i++){
                    nazione.getAccordiAccettati().get(i).getNazioneChePropone().getAccordiProposti().remove(nazione.getAccordiAccettati().get(i));
                    Accordo accettato = nazione.accordiAccettati.get(i);
                    Platform.runLater(
                            () -> {
                                accettato.getRegionePatto().setText("");
                            }
                    );
                }
            }
        }
        //ALTRIMENTI, SE LA NAZIONE NON E' DI NESSUNA NAZIONE
        else {
            this.denaro -= region.getValore();
        }
        region.setNazione(this.getName(), this.getColor(), this);
        this.addRegion(region);
        this.takeProfit(region.getTipo(), region.getRisorse());
        System.out.println(this.getName() + " ha conquistato la regione " + region.getId());
    }



    //METODO TAKE PROFIT
    public void takeProfit(String tipoRegione, Double risorseRegione){
        //SE LE RISORSE DELLA REGIONE SONO MAGGIORI DI 0, LA NAZIONE OTTINE UN PROFITTO
        if(risorseRegione > 0){
            //SE LA REGIONE E' FERTILE
            if(tipoRegione.equals("fertile")){
                this.numAbitanti += 50;
            }
            //SE LA REGIONE E' STERILE
            else{
                this.numAbitanti += 10;
            }
            this.risorse += risorseRegione;      	//Aumenta il numero di risorse della nazione in base alla regione assegnata
            this.denaro += risorseRegione / 2.0; 	//Aumenta il denaro della nazione in base alla regione assegnata
        }
        //ALTRIMENTI, SE LE RISORSE DELLA REGIONE SONO MINORI O UGUALI A O, LA NAZIONE
        //NON OTTIENE NESSUN PROFITTO
    }



    //METODO INCREASE POPULATION
    public void increasePopulation() {
        for(Iterator<Regione> i = regioni.iterator(); i.hasNext();) {
            Regione num = i.next();
            //SE LA REGIONE (CELLA) E' FERTILE
            if (num.getTipo()=="fertile") {
                this.numAbitanti += 60;
            }
            //SE LA REGIONE (CELLA) E' STERILE
            else {
                //SE LA REGIONE (OLTRE AD ESSERE STERILE) NON HA PIU' RISOSE
                if(num.getRisorse() <= 0){
                    this.numAbitanti -=50;
                }
                //SE LA REGIONE (OLTRE AD ESSERE STERILE) NON ANCORA RISOSE
                else{
                    this.numAbitanti -=30;
                }
            }
        }
        for(int i=0; i <this.accordiProposti.size(); i++){
            this.numAbitanti += 10;
        }
    }



    //METODO GET REGIONI
    public ArrayList<Regione> getRegioni(){
        return regioni;
    }


    //METODO GET ACCORDI PROPOSTI
    public ArrayList<Accordo> getAccordiProposti(){
        return this.accordiProposti;
    }


    //METODO GET ACCORDI ACCETTATI
    public ArrayList<Accordo> getAccordiAccettati(){
        return this.accordiAccettati;
    }


    //METODO AGGIORNA PATTI PROPOSTI
    public void aggiornaPattiProposti(ArrayList<Accordo> proposti){
        this.accordiProposti = proposti;
    }


    //METODO AGGIORNA PATTI ACCETTATI
    public void aggiornaPattiAccettati(ArrayList<Accordo> accettati){
        this.accordiAccettati = accettati;
    }


    //METODO ADD REGION
    public void addRegion(Regione region){
        this.regioni.add(region);

    }



    //METODO REFRESH REGIONS TO EXEC
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
    public void removeAllRegions(){
        for(Iterator<Regione> i = regioni.iterator(); i.hasNext();) {
            Regione num = i.next();
            num.resetRegion();      //Toglie dalla cella la nazione di appartenza e il colore della nazione
            i.remove();             //Toglie la cella dalla lista di quelle appartenenti alla nazione
            Platform.runLater(
                    () -> {
                        num.setText("");
                    }
            );
        }
        removeExecRegions();
    }



    //METODO REMOVE EXEC REGIONS
    public void removeExecRegions(){
        for(Iterator<Regione> i = regionsToExec.iterator(); i.hasNext();) {
            Regione num = i.next();
            i.remove();
        }
    }



    //METODO VERIFICA REGOLE TRANSIZIONE
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
    public void guerra(Nation enemy, Regione region){
        System.out.println(this.getName() + " e' entrata in guerra contro " + enemy.getName() + "!");
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
                System.out.println(this.getName() + " ha perso uomini e denaro nella guerra contro " + enemy.getName());
            }
        }
        //ALTRIMENTI, SE LE DUE NAZIONI NON HANNO LA STESSA ETA'
        else{
            //SE LA NAZIONE ATTACCANTE HA L'ETA' ANTICA
            if(this.getAge().equals("ANTICA")){
                warPayment(this);//La nazione che attacca perde risorse, abitanti e denaro
                System.out.println(this.getName() + " ha perso uomini e denaro nella guerra contro " + enemy.getName());
            }
            //ALTIMENTI, SE LA NAZIONE ATTACCANTE HA L'ETA' INTEMEDIA
            else if(this.getAge().equals("INTERMEDIA")){
                //SE LA NAZIONE CHE DIFENDE HA L'ETA' ANTICA
                if(enemy.getAge().equals("ANTICA")){
                    warPayment(enemy);  //La nazione che difende perde risorse, abitanti e denaro
                    System.out.println(this.getName() + " ha perso uomini e denaro nella guerra contro " + enemy.getName());
                    this.conquistaRegione(region);
                }
                //ALTRIMENTI, SE LA NAZIONE CHE DIFENDE HA L'ETA' MODERNA
                else{
                    warPayment(this);//La nazione che attacca perde risorse, abitanti e denaro
                    System.out.println(this.getName() + " ha perso uomini e denaro nella guerra contro " + enemy.getName());
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
    public void warPayment(Nation defender){
        defender.denaro -= defender.denaro/6;
        defender.numAbitanti -= 30;
        defender.consumaRisorse();
    }



    //METODO INTERROMPI ALLEANZA
    public void interrompiAlleanza(Accordo alleanza){
        alleanza.getRegionePatto().rompiPatto();
        Platform.runLater(
                () -> {
                    alleanza.getRegionePatto().setText("");
                }
        );
        //Rimuove il patto tra gli accordi proposti della nazione che lo ha proposto
        alleanza.getNazioneChePropone().getAccordiProposti().remove(alleanza);
        //Rimuove il patto tra gli accordi accettati della nazione che lo ha accettato
        alleanza.getNazioneCheAccetta().getAccordiAccettati().remove(alleanza);
        System.out.println(alleanza.getNazioneChePropone().getName() + " ha interrotto l'alleanza con " + alleanza.getNazioneCheAccetta().getName());
    }



    //METODO PROPONI ACCORDO
    public void proponiAccordo(Nation accettatore, Regione region){
        System.out.println(this.getName() + " ha proposto un accordo a " + accettatore.getName());
        //SE IL DENARO DELLA NAZIONE CHE PROPONE L'ACCORDO E MAGGIORE O UGUALE A 2/3 DEL
        //DENARO DELLA NAZIONE CHE RICEVE L'ACCORDO
        if(this.getDenaro() >= (accettatore.getDenaro() - accettatore.getDenaro()/3.0)){
            //Viene aggiunto il patto a quelli proposti della nazione che propone
            Accordo pattoEconomico = new Accordo(this, accettatore, region);
            //Viene aggiunto il patto a quelli proposti della nazione che propone
            this.accordiProposti.add(pattoEconomico);
            //Viene aggiunto il patto a quelli accettati della nazione che accetta
            accettatore.accordiAccettati.add(pattoEconomico);
            Platform.runLater(
                    () -> {
                        region.setText("p");
                    }
            );
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
                    Platform.runLater(
                            () -> {
                                alleanza.getRegionePatto().setText("");
                            }
                    );
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
                    Platform.runLater(
                            () -> {
                                alleanza.getRegionePatto().setText("");
                            }
                    );
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
                    Platform.runLater(
                            () -> {
                                alleanza.getRegionePatto().setText("");
                            }
                    );
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
                    Platform.runLater(
                            () -> {
                                alleanza.getRegionePatto().setText("");
                            }
                    );
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
    public void riscuotiTasse(){
        for(int i=0; i<accordiAccettati.size(); i++){
            this.accordiAccettati.get(i).getNazioneChePropone().denaro -= 400.0; //La nazione che ha proposto l'accordo paga la tassa
            this.denaro += 400.0;                                                //La nazione che ha accettato l'accordo riscuote la tassa
        }
    }



    //METODO CLONE CHARACTERS
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
            this.addRegion(nazioneDaClonare.getRegioni().get(i));
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
    public boolean getThreadState(){
        return this.active;
    }



    //METODO SET GRID CONTROLLER
    public void setGridController(ControllerImpostazioniGriglia controller){
        this.gridController = controller;
    }



    //METODO GET GRID CONTROLLER
    public ControllerImpostazioniGriglia getGridController(){
        return this.gridController;
    }



    //METODO SVEGLIA
    public synchronized void sveglia(){
        notify();
    }



    //METODO RUN
    public synchronized void run() {
        try{
            this.active = true;
            //sleep(1000);
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
                System.out.println("Sono " + regioni.get(regionToControl).getId() + " e appartengo alla nazione " + regioni.get(regionToControl).getNomeNazione());
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
                System.out.println("Sono " + regionsToExec.get(regionToControl).getId() + " e appartengo alla nazione " + regionsToExec.get(regionToControl).getNomeNazione());
                verificaRegoleTransizione(regionsToExec.get(regionToControl));
            }
            //SE LA NAZIONE PERDE TUTTE LE SUE REGIONI
            if(this.regioni.size() == 0){
                this.vivo = false;
                System.out.println("La nazione " + this.getName() + " e' morta perche' non ha piu' nessuna regione");
                //Scioglie tutti gli accordi che aveva stretto
                for(int i=0; i<accordiProposti.size();i++){  //Scioglie gli accordi proposti rimuovendoli da quelli accettati
                    //delle nazioni che l'hanno accettati
                    accordiProposti.get(i).getNazioneCheAccetta().getAccordiAccettati().remove(accordiProposti.get(i));
                    Accordo proposto = accordiProposti.get(i);
                    Platform.runLater(
                            () -> {
                                proposto.getRegionePatto().setText("");
                            }
                    );
                }
                for(int i=0; i<accordiAccettati.size();i++){  //Scioglie gli accordi accettati rimuovendoli da quelli proposti
                    //delle nazioni che l'hanno proposti
                    accordiAccettati.get(i).getNazioneChePropone().getAccordiProposti().remove(accordiAccettati.get(i));
                    Accordo accettato = accordiAccettati.get(i);
                    Platform.runLater(
                            () -> {
                                accettato.getRegionePatto().setText("");
                            }
                    );
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
                //System.out.println(this.getName() + " nazione");
                wait();            									      	  //La nazione va in attesa che si arrivi alla fine del suo turno per svolgere le azioni successive
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
                    System.out.println("La nazione " + this.getName() + " e' morta perche' il numero di abitanti era inferiore a 10");
                    //Scioglie tutti gli accordi che aveva stretto
                    for(int i=0; i<accordiProposti.size();i++){  //Scioglie gli accordi proposti rimuovendoli da quelli accettati
                        //delle nazioni che l'hanno accettati
                        accordiProposti.get(i).getNazioneCheAccetta().getAccordiAccettati().remove(accordiProposti.get(i));
                        Accordo proposto = accordiProposti.get(i);
                        Platform.runLater(
                                () -> {
                                    proposto.getRegionePatto().setText("");
                                }
                        );
                    }
                    for(int i=0; i<accordiAccettati.size();i++){  //Scioglie gli accordi accettati rimuovendoli da quelli proposti
                        //delle nazioni che l'hanno proposti
                        accordiAccettati.get(i).getNazioneChePropone().getAccordiProposti().remove(accordiAccettati.get(i));
                        Accordo accettato = accordiAccettati.get(i);
                        Platform.runLater(
                                () -> {
                                    accettato.getRegionePatto().setText("");
                                }
                        );
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