package interfacciaGrafica;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class impostazioniGrigliaController {

    private BorderPane impostazioniGriglia;

    @FXML
    private Button buttonAddNation;

    @FXML
    private Button buttonDeleteNation;

    @FXML
    private Button buttonStart;

    @FXML
    private Button buttonHelp;

    @FXML
    private Button buttonExit;

    @FXML
    void clickAddNation(ActionEvent event) {

    }

    @FXML
    void clickButtonExit(ActionEvent event) {
        Stage exitStage = (Stage) buttonExit.getScene().getWindow();
        exitStage.close();
    }

    @FXML
    void clickDeleteNation(ActionEvent event) {

    }

    @FXML
    void clickHelp(ActionEvent event) {
        try {
            AnchorPane helpPane = FXMLLoader.load(getClass().getResource("help.fxml"));
            Stage helpStage = new Stage();
            helpStage.setScene(new Scene(helpPane));
            helpStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void clickStart(ActionEvent event) {

    }

}
