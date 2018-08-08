package interfacciaGrafica;
import javafx.scene.control.Button;
import java.util.Random;

public class Regione extends Button{ //una Regione rappresenta un Button esteso

    private double risorse;       //numero di risorse naturali della regione
    private String nazione;       //nazione a cui appartiene la regione(inizialmente di nessuno)
    private String tipo;          //tipo di regione che puo' essere in base alle risorse di tipo sterile o fertile
    private double valore;        //valore in denaro de terreno (una nazione deve spendere una certa somma per colonizzare un
                                  // terreno)

    public Regione(){
        Random rand = new Random();               //genera un nuovo oggetto di tipo Random(randomico)
        risorse = rand.nextInt(1000);     //assegna alle risorse un numero casuale compreso tra 0 e 1000
        nazione = "";                            //la nazione di appartenenza inizialmente e' vuota perche' quando generato
                                                 //il territorio non e' di nessuno

        if(risorse >= 350.0){                      //se il numero di risorse e' maggiore uguale a 350 allora la regione e' fertile
            tipo = "fertile";
        }
        else{
            tipo = "sterile";                    //altrimenti se minore si tratta di una regione sterile
        }

        //imposta lo sfondo della cella in base al tipo di territorio
        if(tipo.equals("fertile")){
            this.setStyle("-fx-background-image: url('/interfacciaGrafica/IMG-Fertile.jpg')");
        }
        else{
            this.setStyle("-fx-background-image: url('/interfacciaGrafica/IMG-Sterile.jpg')");
        }

        this.valore = 0.0; //il valore reale verra' valorizzato al momento in cui si aggiunge la regione alla griglia
    }

    //METODO GET RISORSE
    //Restituisce il numero di risorse naturali attuali della nazione
    public double getRisorse() {
        return this.risorse;
    }

    //METODO GET NAZIONE
    //Restituisce la nazione di appartenenza o manda un messaggio in console se la regione non apparatiene a nessuno
    public String getNazione() {
        return this.nazione;
    }


    //METODO GET TIPO
    //Restituisce il tipo di regione(fertile o sterile)
    public String getTipo() {
        return this.tipo;
    }

    //METODO SET NAZIONE
    //Permette di assegnare alla regione la nazione di appartenenza passata come parametro. Metodo usato quando si assegna una
    //regione ad una nazione durante le impostazioni iniziali e durante la simulazione quando una nazione conquista un territorio.
    public void setNazione(String nomeNazione){
        this.nazione = nomeNazione;
    }

    //METODO REFRESH TYPE
    //Setta il terreno al suo tipo di stato attuale(fertile o sterile)
    public void refreshType(){
        if(risorse >= 350.0){                      //se il numero di risorse e' maggiore uguale a 350.0 allora la regione e' fertile
            tipo = "fertile";
        }
        else{
            tipo = "sterile";                    //altrimenti se minore si tratta di una regione sterile
        }
    }
    //METODO CONSUMA RISORSE
    //Viene sottratto un quinto del numero di risorse ad ogni fase di gioco che interessa la nazione: cio' succede nel
    // momento in cui una nazione occupa questa regione e quindi consuma le risorse.
    public void consumaRisorse(){
        this.risorse = risorse - (risorse / 5); //viene consumato un quinto delle risorse
        this.refreshType();                     //viene aggiornato lo stato attuale della regione(fertile o sterile)
    }

    //METODO SET VALORE
    //Metodo per settare il valore reale in denaro della regione
    public void setValore(int righe, int colonne){ //il valore cambiera' in base al numero di risorse e di righe e colonne
                                                   // della tabella
        if(righe > 20 && colonne > 20){            //se si ha una tabella abbastanza grande il valore sara' piu' basso per permettere
                                                   //una maggiore espansione nella tabella
            this.valore = risorse / 2;
        }
        else{                                      //altrimenti se si tratta di una tabella di piccole dimensioni il valore aumenta
                                                   //per contenere l'espansione
            this.valore = risorse;
        }
    }
    //METODO GET VALORE
    //Ritorna il valore in denaro dela regione
    public double getValore(){
        return this.valore;
    }
}
