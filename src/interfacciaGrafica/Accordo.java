package interfacciaGrafica;

public class Accordo {
    private Nation nazioneChePropone;
    private Nation nazioneCheAccetta;
    private Regione regione;


    //COSTRUTTORE CON TRE PARAMETRI
    public Accordo(Nation propone, Nation accetta, Regione region){
        this.nazioneChePropone = propone;
        this.nazioneCheAccetta = accetta;
        this.regione = region;
    }


    //METODO SET NAZIONE CHE PROPONE
    public void setNazioneChePropone(Nation propone){
        this.nazioneChePropone = propone;
    }


    //METODO SET NAZIONE CHE ACCETTA
    public void setNazioneCheAccetta(Nation accetta){
        this.nazioneCheAccetta = accetta;
    }


    //METODO GET NAZIONE CHE PROPONE
    public Nation getNazioneChePropone() {
        return nazioneChePropone;
    }


    //METODO GET NAZIONE CHE ACCETTA
    public Nation getNazioneCheAccetta() {
        return nazioneCheAccetta;
    }


    //METODO GET REGIONE PATTO
    public Regione getRegionePatto(){
        return  regione;
    }

}
