package interfacciaGrafica;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class impostazioniGrigliaController {

    @FXML
    private Button buttonStart;

    @FXML
    private Button buttonExit;

    @FXML
    private Button buttonAddNation;

    @FXML
    private Button buttonHelp;

    @FXML
    void clickAddNation(ActionEvent event) {

    }

    @FXML
    void clickExit(ActionEvent event) {
        Stage stageExit = (Stage) buttonExit.getScene().getWindow();
        stageExit.close();
    }

    @FXML
    void clickHelp(ActionEvent event) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("help.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Can't load the window");
        }
    }

    @FXML
    void clickStart(ActionEvent event) {

    }
}

