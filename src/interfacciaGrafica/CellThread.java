package interfacciaGrafica;

import org.controlsfx.control.PopOver;
import java.util.Random;

import static interfacciaGrafica.Regione.createPop;		//Importo il metodo createPop della classe Regione

public class CellThread extends Thread{  //Tipo di thread che costituisce un legame con una cella della griglia

    Regione region;           //Variabile di tipo Regione chiamata region che e' la regione (cella) su cui agisce il CellThread

    //COSTRUTTORE CON UN PARAMETRO
    //Prende come parametro la regione (cella) assegnata al thread e valorizza la regione del
    //thread con quella passata da parametro
    public CellThread(Regione cella){
        this.region = cella;
    }


    //METODO RUN
    //Il thread viene runnato solo nel caso in cui la regione appartiene ad una nazione. Se la griglia non e' stata
    //tutta occupata dalla nazione significa che ci sono ancora regioni che non gli appartengono e che puo' quindi
    //conquistare. La regione a quel punto controlla se si puo' conquistare una delle regioni che confinano con essa
    //(presa casualmente): cio' dipende da se la nazione ha abbastanza denaro per comprare la regione, e se ne ha
    //abbastanza la conquista richiamando il metodo "conquistaRegione" di Nation.
    //Se la griglia e' stata invece tutta occupata non c'e' bisogno di conqusitare territori e quindi non si applicano
    //le procedure di conquista.
    // In ogni caso viene richiamato il metodo consumaRisorse della classe Regione (questo metodo consumera' ad ogni
    // turno in cui viene scelta la regione le sue risorse inoltre una volta consumate le
    //risorse in base alle risorse rimaste viene aggiornato il tipo di territorio (fertile o sterile)).
    //In seguito, una volta che la regione ha svolto ed ha finito il proprio turno avvisa la nazione
    //che e' che quindi giunta a fine turno.
    //Quindi siccome la nazione e' in attesa che la regione finisca il proprio turno, quando la regione
    //ha finito avvisa la nazione e per farlo viene utilizzato il metodo sveglia() che avvisa la nazione
    //con una notify()
    public void run(){
        //Se la griglia non e' stata tutta occupata dalla nazione allora ci sono territori per i quali bisogna verificare
        //se si possono conquistare
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
            //Se la nazione ha abbastanza denaro per conquistare la regione allora la annette tramite questo thread(tramite la regione scelta per
            //compiere una azione)
            if(this.region.getNazione().getDenaro() >= regionToConquest.getValore()){
                //La regione viene conquistata
                this.region.getNazione().conquistaRegione(regionToConquest);
            }//Altrimenti la regione non puo' essere conquistata
        }//
        this.region.consumaRisorse();             //consuma le risorse della regione
        this.region.setValore(this.region.getNazione().getGridController().getNumeroRighe(),this.region.getNazione().getGridController().getNumeroColonne());
        PopOver pop2 = createPop(this.region);    //aggiorna il popover relativo alla regione creandone uno nuovo che si va a sovrepporre al precedente
        this.region.getNazione().sveglia();
    }

}
