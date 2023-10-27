package Messages;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LogOutMessegeController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label labeInformacion;
    @FXML
    private Button aceptarButton;
    @FXML
    private Button cancelarButton;

    private int aceptado = -1;

    @FXML
    private void aceptarAccion(ActionEvent event) {
        aceptado = 1;
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelarAccion(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public int getAceptado() {
        return aceptado;
    }

}
