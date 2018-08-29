package interfacciaGrafica;

public class CellThread extends Thread{  //Tipo di thread che costituisce un legame con una cella della griglia

    Regione region;           //regione su cui agisce il CellThread

    public CellThread(Regione cella){ //costruttore che prende come parametro la regione assegnata al thread
        this.region = cella;          //valorizza la regione del thread con quella passata da parametro
    }

    public void run(){
        this.region.consumaRisorse(); //il thread viene runnato solo nel caso in cui la regione appartiene ad una
        //nazione, e in questo caso avremo che la nazione consumera' ad ogni turno le risorse
        //di questa regione, inoltre una volta consumate le risorse si aggiornera' il tipo
        //della regione in base alle risorse rimaste(fertile o sterile)
        System.out.println(this.region.getNazione().getName() + "regione");
        this.region.getNazione().sveglia();   //avvisa la nazione che e' stato svolto il lavoro della regione e che quindi si e'
        //giunti a fine turno: la nazione e' in attesa(wait) che la regione una volta finito
        //il suo lavoro(run()) la avvisa che si e' giunti a fine turno e per farlo viene
        //utilizzato il metodo sveglia() che avvisa la nazione con una notify()
    }
}
