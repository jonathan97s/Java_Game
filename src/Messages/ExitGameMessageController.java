package Messages;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mastermind.MasterMindViewImpl;

public class ExitGameMessageController {

    @FXML
    private Label labeInformacion;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button aceptarButton;

    @FXML
    private Button cancelarButton;

    private int aceptado = -1;
       
    /**
     * La acción que cierra el programa.
     *
     * @param event
     */
    
    public ExitGameMessageController() {
    }

    @FXML
    private void aceptarAccion(ActionEvent event) {
        aceptado = 1;
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Para cancelar la accion de cerrar el programa
     * @param event 
     */
    @FXML
    private void cancelarAccion(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public int getAceptado() {
        return aceptado;
    }
    
  
    public void finestra(String text){
        
    }

}
