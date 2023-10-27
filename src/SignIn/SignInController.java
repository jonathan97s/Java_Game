package SignIn;

import BaseDades.ConfigConnexioDriveManager;
import BaseDades.ConnexioMySQLConfgiConnexioDataSource;
import BaseDades.UsuariDAO;
import BaseDades.MyException;
import BaseDades.UsuariDAOImpl;
import DadesUsuaris.Usuari;
import Messages.MessageController;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import mastermind.MasterMindViewImpl;

public class SignInController implements Initializable {

    @FXML
    private TextField userTextField;
    
    @FXML    
    private Button login;
    
    @FXML
    private TextField passwordTextField;


    /**
     * El método para registrar usuarios nuevos.
     */
    @FXML
    public void registrarAccion(ActionEvent event) throws MyException {
        MessageController mc = new MessageController();
        UsuariDAOImpl ud = new UsuariDAOImpl(new ConnexioMySQLConfgiConnexioDataSource());       
        String nom = userTextField.getText();
        String contrasenya = passwordTextField.getText();
        Usuari u = new Usuari(nom, contrasenya);
        if (!nom.isEmpty() && !contrasenya.isEmpty()) {
            if (ud.afegirUsuari(u) == true) {
                mc.finestraInformacio("Usuario registrado correctamente");
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                
            } else {
                mc.finestraInformacio("Usuario con nombre duplicado");
            }
        } else {
            mc.finestraInformacio("Datos incorrectos");
        }
    }

    /**
     * Initializes the controller class.
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void initUI(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.getIcons().add(new Image("imagenes/mastermindNouIcon8"));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MasterMindViewImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
