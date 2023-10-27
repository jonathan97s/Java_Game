package Login;

import Messages.MessageController;
import BaseDades.MyException;
import BaseDades.UsuariDAOImpl;
import DadesUsuaris.Usuari;
import MastContract.MasterContract;
import MastContract.MasterContract.MasterMindView;
import SignIn.SignInController;
import BaseDades.ConnexioMySQLConfgiConnexioDataSource;
import BaseDades.PartidesDAOImpl;
import DadesPartides.Partida;
import Properties.PropsUsuari;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Platform.exit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mastermind.MasterMindModelImpl;
import mastermind.MasterMindViewImpl;
import mastermind.MastermindPresenterImpl;

public class LoginController {

    @FXML
    private TextField userTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Button signinButton;
    

    
    
    private PropsUsuari pu = new PropsUsuari();
    private MessageController mc = new MessageController();
 

    public LoginController(Stage stage) {
        initUi(stage);

    }

    /**
     * La accion que nos lleva a la ventana de registro de nuevos usuarios.
     *
     * @param event
     */
    @FXML
    private void signinAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(SignInController.class.getResource("SignIn.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image("imagenes/mastermindNouIcon8.png"));
            stage.setTitle("Sign In");
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * La accion para validar los datos de usuario, si coinciden con los de la
     * base de datos, abre la ventana del juego.
     *
     * @param event
     */
    @FXML
    public void loginAction(ActionEvent event) throws MyException {
        Stage stageMastermind = new Stage();
        String user = userTextField.getText();
        String password = passwordTextField.getText();
        UsuariDAOImpl ud = new UsuariDAOImpl(new ConnexioMySQLConfgiConnexioDataSource());
        Usuari u = new Usuari(user, password);
        if (!user.isEmpty() && !password.isEmpty()) {
            if (ud.cercarUser(u) == true) {
                Properties p = pu.cargarProperties();
                pu.modificarProperties(p, user);
                pu.guardarProperties(p);
                Node source = (Node) event.getSource();
                Stage stageLogin = (Stage) source.getScene().getWindow();
                stageLogin.close();
                MasterMindView view = new MasterMindViewImpl(stageMastermind);
                MasterContract.MasterMindPresenter presenter = new MastermindPresenterImpl();
                MasterContract.MasterMindModel model = new MasterMindModelImpl();
                presenter.setView(view);
                presenter.setModel(model);
                view.setPresenter(presenter);               
            } else {
                mc.finestraInformacio("Usuario o contraseña incorrecto");
            }
        } else {
            mc.finestraInformacio("Datos incorrectos");
        }
    }

    /**
     * Nos muestra la ventana de login
     *
     * @param stage
     */
    public void initUi(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setController(this);
            loader.setLocation(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.getIcons().add(new Image("imagenes/mastermindNouIcon8.png"));
            stage.setTitle("Login");
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
