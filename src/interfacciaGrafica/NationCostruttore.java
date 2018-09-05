package interfacciaGrafica;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
public class NationCostruttore {
        private Eta eta;
        private  String nome;
        private  int numSterili;
        private  int numFertili;

        public NationCostruttore(String nome, Eta eta, int numSterili, int numFertili) {
            this.eta= eta;
            this.nome = nome;
            this.numFertili = numFertili;
            this.numSterili =numSterili;
        }

        public Eta getEta(){
            return eta;
        }
        public String getNome(){
            return nome;
        }
        public int getNumSterili(){
            return numSterili;
        }
        public int getNumFertili(){
            return numFertili;
        }

}


