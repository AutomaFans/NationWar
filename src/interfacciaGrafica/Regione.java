package interfacciaGrafica;
import javafx.scene.control.Button;
import java.util.Random;

//Una Regione rappresenta un Button esteso
//Ovvero una Regione e' una cella che compone il territorio di una Nazione
public class Regione extends Button{

    private double risorse;       //Numero di risorse naturali della regione
    private String nazione;       //Nazione a cui appartiene la regione(inizialmente di nessuno)
    private String tipo;          //Tipo di regione che puo' essere in base alle risorse di tipo sterile o fertile
    private double valore;        //Valore in denaro de terreno (una nazione deve spendere una certa somma per colonizzare un terreno)

    //Genera un nuovo oggetto di tipo Random(randomico), per cui
    //- assegna alle risorse un numero casuale compreso tra 0 e 1000
    //- la nazione di appartenenza inizialmente e' vuota perche' quando generato il territorio non e' di nessuno
    //Inoltre se il numero  di risorse e' maggiore uguale a 350 allora la regione e' fertile
    //per cui viene impostato il tipo = fertile, altrimenti se il numero di risorse è minore
    //di 350 allora la regione è sterile per cui viene impostato il tipo = sterile
    //Poi in base al tipo di territorio viene impostato lo sfondo della cella della griglia.
    //Se il tipo è fertile allora lo sfondo della cella sara' l'immagine IMG-Fertile.jpg,
    //alrimenti, se il tipo è sterile allora lo sfondo della cella sara' l'immagine
    //IMG-Sterile.jpg.
    //Il valore reale verra' valorizzato al momento in cui si aggiunge la regione alla griglia.
    public Regione(){
        Random rand = new Random();              //Genera un nuovo oggetto di tipo Random(randomico)
        risorse = rand.nextInt(1000);            //Assegna alle risorse un numero casuale compreso tra 0 e 1000
        nazione = "";                            //la nazione di appartenenza inizialmente e' vuota perche' quando generato il territorio non e' di nessuno
        //IN BASE ALLE RISORSE VIENE IMPOSTATO IL TIPO DI TERRITOPRIO (FERTILLE O STERILE)
        if(risorse >= 350.0){                   //Se il numero di risorse e' maggiore uguale a 350
            tipo = "fertile";					//Allora la regione e' fertile
        }
        else{									//Altrimenti, se il numero di risorse e' minore di 350
            tipo = "sterile";                   //Allora la regione e' sterile
        }
        //IMPOSTA LO SFONDO DELLA CELLA IN BASE AL TIPO DI TERRITORIO
        if(tipo.equals("fertile")){
            this.setStyle("-fx-background-image: url('/interfacciaGrafica/IMG-Fertile.jpg')");
        }
        else{
            this.setStyle("-fx-background-image: url('/interfacciaGrafica/IMG-Sterile.jpg')");
        }
        this.valore = 0.0;
    }

    //METODO RESET REGION
    //Permette di resettare la regione: toglie la nazione di appartenza e imposta lo sfondo di default. Inoltre aggiorna
    //il tipo della regione.
    public void resetRegion(){
        this.nazione = "";
        this.refreshType();
        //Resetta lo sfondo in base al suo tipo(sterile o fertile) e togliendo il colore della nazione
        if(tipo.equals("fertile")){
            this.setStyle("-fx-background-image: url('/interfacciaGrafica/IMG-Fertile.jpg')");
        }
        else{
            this.setStyle("-fx-background-image: url('/interfacciaGrafica/IMG-Sterile.jpg')");
        }
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
    //Permette di assegnare alla regione la nazione di appartenenza passata come parametro e il colore della nazione. Metodo usato quando
    //si assegna una regione ad una nazione durante le impostazioni iniziali e durante la simulazione quando una nazione conquista un
    //territorio.
    //Alla regione viene applicato setStyle che serve per applicare una propieta' css all'oggetto in questione, in questo caso viene
    //applicato un background color e cioe' il colore di sfondo che sara' quello passato al metodo. Nel caso in cui si sta impostando
    //la griglia si tratta de colore dell'ultima nazione inserita nel sistema.
    public void setNazione(String nomeNazione, String colore){
        this.nazione = nomeNazione;
        this.setStyle("-fx-background-color: " + colore);
    }

    //METODO REFRESH TYPE
    //Setta il terreno al suo tipo di stato attuale(fertile o sterile)
    //se il numero  di risorse e' maggiore uguale a 350 allora la regione e' fertile
    //per cui viene impostato il tipo = fertile, altrimenti se il numero di risorse è minore
    //di 350 allora la regione è sterile per cui viene impostato il tipo = sterile
    public void refreshType(){
        if(risorse >= 350.0){                   //Se il numero di risorse e' maggiore uguale a 350
            tipo = "fertile";					//Allora la regione e' fertile
        }
        else{									//Altrimenti, se il numero di risorse e' minore di 350
            tipo = "sterile";                  //Allora la regione e' sterile
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
    //Il valore cambiera' in base al numero di risorse e di righe e colonne della griglia.
    //Se si ha una griglia abbastanza grande il valore sara' piu' basso per permettere
    //una maggiore espansione nella tabella, per cui il valore sarà la meta'' delle risorse
    //(risorse/2).
    //Altrimenti, se si ha una griglia di piccole dimensioni il valore sara' piu' alto
    //per contenere l'espansione, per cui il valore e' uguale alle risorse (valore = risorse).
    public void setValore(int righe, int colonne){
        if(righe > 20 && colonne > 20){          	//Se il numero di righe e di colonne della griglia è maggiore di 20
            this.valore = risorse / 2;				//Il valore e' uguale alla meta' delle risorse
        }
        else{                                      //Altrimenti, se il numero di righe e di colonne della griglia è minore o uguale di 20
            this.valore = risorse;				   //Il valore e' uguale alle risorse
        }
    }

    //METODO GET VALORE
    //Ritorna il valore in denaro dela regione
    public double getValore(){
        return this.valore;
    }
}
