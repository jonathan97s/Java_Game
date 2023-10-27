package mastermind;

import Login.LoginController;
import MastContract.MasterContract.*;
import static java.applet.Applet.newAudioClip;
import java.applet.AudioClip;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MasterMind extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        LoginController lc = new LoginController(stage); 
    }

    public static void main(String[] args) {
        launch(args);
    }

}
