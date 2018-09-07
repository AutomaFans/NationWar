package interfacciaGrafica;

//Rappresenta un patto su una regione tra nazioni
public class Accordo {
    private Nation nazioneChePropone;  //Nazione che propone l'accordo
    private Nation nazioneCheAccetta;  //Nazione che accetta l'accordo
    private Regione regione;           //Regione sulla quale si e' stretto l'accordo

    //Il costruttore prende come parametri la nazione che propone l'accordo, quella che l'accetta e la regione su cui
    //si stringe il patto
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
