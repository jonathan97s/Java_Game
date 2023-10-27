package GameLevel;

import Properties.Props;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.stage.Stage;

public class GameLevelController implements Initializable {

    @FXML
    private ComboBox<String> comboItems;

    @FXML
    private Label comLabel;

    private int numColorsJoc = 4;
    private boolean confirmar = false;
    @FXML
    private Button botonConfirmar;

    /**
     * Acció per confirmar la longitud de la combinació secreta.
     *
     * @param event
     */
    @FXML
    private void confirmarAction(ActionEvent event) {
        if (comboItems.getSelectionModel().isEmpty()) {
            comLabel.setText("Elige un nivel");
        } else {
            confirmar = true;
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Inserta las tres opciones de dificuldad en el ComboBox.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboItems.setItems(FXCollections.observableArrayList("Fácil", "Mediano", "Dificil"));
    }

    /**
     * Muestra la descripción de cada dificuldad.
     *
     * @param event
     */
    @FXML
    public void textShow(ActionEvent event) {
        String s = comboItems.getSelectionModel().getSelectedItem().toString();
        if (s.equals("Fácil")) {
            setNumColorsJoc(4);
            comLabel.setText("La combinación secreta será de " + numColorsJoc + " colores."
                    + "\nSi confirmas, el juego se reiniciará de nuevo.");
        } else if (s.equals("Mediano")) {
            setNumColorsJoc(5);
            comLabel.setText("La combinación secreta será de " + numColorsJoc + " colores."
                    + "\nSi confirmas, el juego se reiniciará de nuevo.");
        } else {
            setNumColorsJoc(6);
            comLabel.setText("La combinación secreta será de " + numColorsJoc + " colores."
                    + "\nSi confirmas, el juego se reiniciará de nuevo.");
        }
    }

    public void setNumColorsJoc(int numColorsJoc) {
        this.numColorsJoc = numColorsJoc;
    }

    public int getNumColorsJoc() {
        return this.numColorsJoc;
    }

    public boolean getConfirmar() {
        return confirmar;
    }

}
