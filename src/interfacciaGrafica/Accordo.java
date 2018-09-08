package interfacciaGrafica;

//Rappresenta un patto su una regione tra nazioni
public class Accordo {
    private Nation nazioneChePropone;  	//Nazione che propone l'accordo
    private Nation nazioneCheAccetta;  	//Nazione che accetta l'accordo
    private Regione regione;           	//Regione sulla quale si e' stretto l'accordo


    //COSTRUTTORE CON DUE PARAMETRI
    //Prende come parametri una Nazione propone, che rappresenta la nazione che propone
    //l'accordo, una Nazione accetta, che rappresenta la nazine che accetta l'accordo
    //e una Regione region che rappresenta la regione sulla quale si stringe l'accordo
    //Poi assegna la Nazione propone presa da parametro al campo chiamato nazioneChePropone
    //della classe Nation, la Nazione accetta presa da parametro al campo chiamato
    //nazioneCheAccetta della classe Nation e infine assegna la Regione region
    //presa da parametro al campo chiamato regione della classe Nation
    public Accordo(Nation propone, Nation accetta, Regione region){
        this.nazioneChePropone = propone;
        this.nazioneCheAccetta = accetta;
        this.regione = region;
    }



    //METODO GET NAZIONE CHE PROPONE
    //Restituisce la nazione che ha proposto l'accordo
    public Nation getNazioneChePropone() {
        return nazioneChePropone;
    }



    //METODO GET NAZIONE CHE ACCETTA
    //Restituisce la nazione che ha accettato l'accordo
    public Nation getNazioneCheAccetta() {
        return nazioneCheAccetta;
    }




    //METODO GET REGIONE PATTO
    //Restituisce la regione su cui e' stato stretto il patto di alleanza
    public Regione getRegionePatto(){
        return  regione;
    }

}
