package interfacciaGrafica;

//Ogni nazione è composta dal nome e dal colore.
//Nel costruttore il colore è indicato come stringa, ma verrà cambiato nel tipo Color
//quando dova'  essere utilizzato per colorare i bottoni nella griglia.
public class Nation {


    private String nome;					//Stringa nome per il nome della nazione
    private String color;				//Stringa color peril colore della nazione


    //COSTRUTTORE CON DUE PARAMETRI
    public Nation(String nome, String color){
        this.nome = nome;
        this.color = color;
    }


    //METODO GETCOLOR()
    //Metodo che restituisce il colore della nazione sulla quale viene chiamato il metodo
    public String getColor(){
        return this.color;
    }

    //METODO GET NAME
    //Metodo che restituisce il nome della nazione sulla quale viene chiamato il metodo
    public String getName() {
        return this.nome;
    }
}