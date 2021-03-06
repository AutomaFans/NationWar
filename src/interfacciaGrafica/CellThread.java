package interfacciaGrafica;

import javafx.application.Platform;
import org.controlsfx.control.PopOver;
import java.util.ArrayList;
import java.util.Random;

import static interfacciaGrafica.Regione.createPop;		//Importo il metodo createPop della classe Regione

//Tipo di thread che costituisce un legame con una cella della griglia
public class CellThread extends Thread{

    /*Variabile di tipo Regione chiamata region
    che e' la regione (cella) su cui agisce il CellThread*/
    Regione region;


    //COSTRUTTORE CON UN PARAMETRO
    public CellThread(Regione cella){
        this.region = cella;
    }


    //METODO RUN
    public void run(){
        //SE LA GRIGLIA  NON E' STATA TUTTA OCCUPATA DALLA NAZIONE ALLORA VISOGNA CONTROLLARE
        //SE CI SONO TERRITORI CHE POSSONO ESSERE CONQUISTATI
        if(this.region.getNazione().getRegioni().size() < (this.region.getNazione().getGridController().getNumeroRighe() * this.region.getNazione().getGridController().getNumeroColonne())){
            this.region.refreshNeighboringRegions();         //aggiorna le regioni che confinano sui lati con la cella cosi' da sapere quali
            //sono le regioni non alleate che si puo' provare a conquistare
            Random rand = new Random();                      //Creo un oggetto random
            int conquestIndex = rand.nextInt(this.region.getRegioniConfinantiSconosciute().size());  //Questo intero l'indice casuale per scegliere
            //quale regione conquistare tra quelle confinanti
            //non alleate
            if(conquestIndex == this.region.getRegioniConfinantiSconosciute().size()){               //Per evitare un IndexOutOfBound se l'indice generato
                //e' uguale alla lunghezza della lista delle regioni confinanti
                //e non alleate, allora viene diminuito di uno
                conquestIndex -= 1;
            }
            Regione regionToConquest = this.region.getRegioniConfinantiSconosciute().get(conquestIndex); //Regione che si vuole conquistare

            //Se la regione da conquistare fa parte di un'altra nazione
            if(regionToConquest.getNomeNazione().equals("") == false && regionToConquest.getNomeNazione().equals(this.region.getNomeNazione()) == false){
                //Se la regione da conquistare non fa parte di nessuna alleanza
                if(regionToConquest.getAlleanza() == null){
                    //A questo punto stringe un patto economico sulla regione da conquistare o va in guerra con la nazione nemica
                    this.makePeaceOrWar(regionToConquest);
                }
                //Se la regione da conquistare fa parte di un'alleanza
                else{
                    //Se la nazione che vuole conquistare la regione non e' coinvolta nell'alleanza
                    if((regionToConquest.getAlleanza().getNazioneCheAccetta().getName().equals(this.region.getNomeNazione()) == false) ||
                            (regionToConquest.getAlleanza().getNazioneChePropone().getName().equals(this.region.getNomeNazione()) == false)){
                        //La nazione interrompe l'alleanza
                        this.region.getNazione().interrompiAlleanza(regionToConquest.getAlleanza());
                        //A questo punto stringe un patto economico sulla regione da conquistare o va in guerra con la nazione nemica
                        this.makePeaceOrWar(regionToConquest);
                    }//Se e' coinvolta nell'alleanza non succede nulla
                }
            }
            //Se la regione da conquistare non fa parte di nessuna nazione
            else{
                //Se la nazione ha abbastanza denaro per conquistare la regione allora la annette tramite questo thread(tramite la regione scelta per
                //compiere una azione)
                if(this.region.getNazione().getDenaro() >= regionToConquest.getValore()){
                    //La regione viene conquistata
                    this.region.getNazione().conquistaRegione(regionToConquest);
                }//Altrimenti la regione non puo' essere conquistata
            }
        }
        this.region.consumaRisorse();             //consuma le risorse della regione
        this.region.setValore(this.region.getNazione().getGridController().getNumeroRighe(),this.region.getNazione().getGridController().getNumeroColonne());
        PopOver pop2 = createPop(this.region);    //aggiorna il popover relativo alla regione creandone uno nuovo che si va a sovrepporre al precedente
        this.region.getNazione().sveglia();
    }



    //METODO MAKE PEACE OR WAR
    private void makePeaceOrWar(Regione regionToConquest){
        System.out.println(this.region.getNomeNazione() + " vuole conquistare " + regionToConquest.getId());
        //Se la nazione che tenta la conquista si torva nell'eta' antica
        if(this.region.getNazione().getAge() == Eta.ANTICA){
            //Se ci sono degli accordi tra le due nazioni vengono sciolti
            this.sciogliAccordi(regionToConquest.getNazione());
            //La nazione va in guerra contro la nazione che possiede la regione da conquistare
            this.region.getNazione().guerra(regionToConquest.getNazione(), regionToConquest);
        }
        //Se la nazione che tenta la conquista si trova nell'eta' intermedia
        else if(this.region.getNazione().getAge() == Eta.INTERMEDIA){
            //Se la nazione che vuole conquistare il territorio nemico ha le risorse maggiori del nemico,
            //ipotizzando che quindi ha anche piu' armi
            if(this.region.getNazione().getRisorse() > regionToConquest.getNazione().getRisorse()){
                //Se ci sono degli accordi tra le due nazioni vengono sciolti
                this.sciogliAccordi(regionToConquest.getNazione());
                //La nazione va in guerra contro la nazione che possiede la regione da conquistare
                this.region.getNazione().guerra(regionToConquest.getNazione(), regionToConquest);
            }//Altrimenti non agisce
        }
        //Se la nazione che tenta la conquista si trova nell'eta' moderna
        else{
            //Prova a stringere un patto sulla regione con la nazione avversaria
            this.region.getNazione().proponiAccordo(regionToConquest.getNazione(), regionToConquest);
        }
    }



    //METODO SCIOGLI ACCORDI
    public void sciogliAccordi(Nation defender){
        //Le liste conterrano tutti gli accordi delle due nazioni(accettati e proposti) ma senza quelli stretti insieme
        ArrayList<Accordo> accordiPropostiDaChiPropone = new ArrayList<>();
        ArrayList<Accordo> accordiAccettatiDaChiPropone = new ArrayList<>();
        ArrayList<Accordo> accordiPropostiDaChiNonAccetta = new ArrayList<>();
        ArrayList<Accordo> accordiAccettatiDaChiNonAccetta = new ArrayList<>();
        //Rimuove accordi tra quelli della nazione che aveva proposto l'accordo
        //Aggiorna gli accordi proposti
        for(Accordo alleanza: region.getNazione().getAccordiProposti()){
            if(alleanza.getNazioneCheAccetta() != defender){    //Se non si tratta di un accordo stretto con la nazione
                //acerrima nemica
                accordiPropostiDaChiPropone.add(alleanza);        //L'accordo non viene sciolto
            }
            else{ //Se si tratta di un accordo da sciogliere, deve essere tolto il patto dalla regione coinvolta
                alleanza.getRegionePatto().rompiPatto();
                Platform.runLater(
                        () -> {
                            alleanza.getRegionePatto().setText("");
                        }
                );
            }
        }
        region.getNazione().aggiornaPattiProposti(accordiPropostiDaChiPropone);  //Viene aggiornata la nuova lista di accordi proposti
        //Aggiorna gli accordi accettati
        for(Accordo alleanza: region.getNazione().getAccordiAccettati()){
            if(alleanza.getNazioneChePropone() != defender){    //Se non si tratta di un accordo stretto con la nazione
                //acerrima nemica
                accordiAccettatiDaChiPropone.add(alleanza);        //L'accordo non viene sciolto
            }
            else{ //Se si tratta di un accordo da sciogliere, deve essere tolto il patto dalla regione coinvolta
                alleanza.getRegionePatto().rompiPatto();
                Platform.runLater(
                        () -> {
                            alleanza.getRegionePatto().setText("");
                        }
                );
            }
        }
        region.getNazione().aggiornaPattiAccettati(accordiAccettatiDaChiPropone);//Viene aggiornata la nuova lista di accordi accettati

        //Rimuove accordi tra quelli della nazione che non ha accettato l'accordo
        //            //Aggiorna gli accordi proposti
        for(Accordo alleanza: defender.getAccordiProposti()){
            if(alleanza.getNazioneCheAccetta() != region.getNazione()){              //Se non si tratta di un accordo stretto con la nazione
                //acerrima nemica
                accordiPropostiDaChiNonAccetta.add(alleanza);         //L'accordo non viene sciolto
            }
            else{ //Se si tratta di un accordo da sciogliere, deve essere tolto il patto dalla regione coinvolta
                alleanza.getRegionePatto().rompiPatto();
                Platform.runLater(
                        () -> {
                            alleanza.getRegionePatto().setText("");
                        }
                );
            }
        }
        defender.aggiornaPattiProposti(accordiPropostiDaChiNonAccetta);//Viene aggiornata la nuova lista di accordi proposti
        //Aggiorna gli accordi accettati
        for(Accordo alleanza: defender.getAccordiAccettati()){
            if(alleanza.getNazioneChePropone() != region.getNazione()){              //Se non si tratta di un accordo stretto con la nazione
                //acerrima nemica
                accordiAccettatiDaChiNonAccetta.add(alleanza);        //L'accordo non viene sciolto
            }
            else{ //Se si tratta di un accordo da sciogliere, deve essere tolto il patto dalla regione coinvolta
                alleanza.getRegionePatto().rompiPatto();
                Platform.runLater(
                        () -> {
                            alleanza.getRegionePatto().setText("");
                        }
                );
            }
        }
        defender.aggiornaPattiAccettati(accordiAccettatiDaChiNonAccetta);//Viene aggiornata la nuova lista di accordi accettati
    }

}
