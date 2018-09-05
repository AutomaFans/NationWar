package interfacciaGrafica;

public class CellThread extends Thread{  //Tipo di thread che costituisce un legame con una cella della griglia

    Regione region;           //Variabile di tipo Regione chiamata region che e' la regione (cella) su cui agisce il CellThread

    //COSTRUTTORE CON UN PARAMETRO
    //Prende come parametro la regione (cella) assegnata al thread e valorizza la regione del
    //thread con quella passata da parametro
    public CellThread(Regione cella){
        this.region = cella;
    }


    //METODO RUN
    //Il thread viene runnato solo nel caso in cui la regione appartiene ad una
    //nazione, e in questo caso viene richiamato il metodo consumaRisorse della classe Regione
    //(questo metodo consumera' ad ogni turno le risorse di questa regione inoltre una volta consumate le
    //risorse in base alle risorse rimaste viene aggiornato il tipo di territorio (fertile o sterile)).
    //In seguito, una volta che la regione ha svolto ed ha finito il proprio turno avvisa la nazione
    //che e' che quindi  e' giunta a fine turno.
    //Quindi siccome la nazione e' in attesa che la regione finisca il proprio turno, quando la regione
    //ha finito avvisa la nazione e per farlo viene utilizzato il metodo sveglia() che avvisa la nazione
    //con una notify()
    public void run(){
        this.region.consumaRisorse();
        this.region.getNazione().sveglia();
    }
}
