package interfacciaGrafica;

//Classe delle nazioni. Ogni nazione è composta dal nome e dal colore (nel costruttore il colore è indicato come stringa,
//ma verrà cambiato nel tipo Color quando dovrà essere utilizzato per colorare i bottoni nella griglia.
public class Nation {

    public String nome;
    public String color;

    public Nation(String nome, String color){
        this.nome = nome;
        this.color = color;
    }
     //METODO GET COLOR
    //Metodo che restituisce il colore della nazione sulla quale viene chiamato il metodo
    public String getColor(){
        return this.color;
    }

    //METODO GET NAME
    //Metodo che restituisce il nome della nazione
    public String getName() { return this.nome; }
}