package mastermind;

import MastContract.MasterContract.*;
import static java.applet.Applet.newAudioClip;
import java.applet.AudioClip;
import java.util.*;
import javax.swing.*;
import mastermind.MasterMindModelImpl.*;
import MastContract.MasterContract.MasterMindPresenter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import mastermind.MasterMindModelImpl;

public class MastermindPresenterImpl extends GameListener implements MasterMindPresenter {

    private MasterMindView view;
    private MasterMindModel model;
    private Map<Matches, Integer> llistaResult = new HashMap<>();
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button buttonNewGame;
    @FXML
    private Button buttonStart;
    @FXML
    private Button buttonExit;
    @FXML
    private Button buttonCheck;
    @FXML
    private GridPane gridPJoc;
    @FXML
    private GridPane gridPAyuda;
    @FXML
    private Button GridPPista;
    @FXML
    private GridPane gridPColors;
    @FXML
    private Button buttonBlue;
    @FXML
    private Button buttonGreen;
    @FXML
    private Button buttonYellow;
    @FXML
    private Button buttonRed;
    @FXML
    private Button buttonHelp;
    @FXML
    private MenuBar Menu;
    @FXML
    private MenuItem startItem;
    @FXML
    private MenuItem newGameItem;
    @FXML
    private MenuItem exitItem;
    @FXML
    private MenuItem gameLevItem;
    @FXML
    private MenuItem gameInfItem;
    @FXML
    private MenuItem rankingitem;
    @FXML
    private MenuItem matchesitem;
    @FXML
    private MenuItem gamesitem;
    @FXML
    private MenuItem logOutItem;
    @FXML
    private Button buttonClear;
    @FXML
    private Button deleteButton;

    
    /**
     * Retorna la lista de resultados a la hora de validar la jugada. 
     * @return 
     */
    @Override
    public Map<Matches, Integer> getLlistaResult() {
        return this.llistaResult;
    }

    @Override
    public void setView(MasterMindView view) {
        this.view = view;
    }

    @Override
    public void setModel(MasterMindModel model) {
        this.model = model;
    }

    /**
     * Es el encargado de empezar una nueva partida en el juego.
     * @param numColors 
     */
    @Override
    public void startGame(int numColors) {
        model.start(numColors);
        view.habilitarButtons();
    }

    /**
     *  
     * @param colors 
     */
    @Override
    public void validateCheck(List<Color> colors) {
        ColorCombination cc = new ColorCombination(colors);
        if (model.getIntentos() <= 10) {          
            cc.setColors(colors);
            llistaResult = model.validatePlay(cc);
            view.play(llistaResult);
        }
    }

    @Override
    public void win(int numColors) {       
        model.addListener(new GameListener(), numColors);
    }

    @Override
    public void over(int numColors) {
        model.addListener(new GameListener(), numColors);
    }

    @Override
    public void newGame() {
        view.reiniciaGriPJoc();
        model.setIntentos(0);
        model.getCombSecreta().clear();
    }
    
    @Override
    public int getIntentos(){
       return model.getIntentos();
    }


    public void buttonClickSound() {
        AudioClip audio;
        audio = newAudioClip(getClass().getResource("/Audios/buttonClick.wav"));
        audio.play();
    }

    @FXML
    private void startAction(ActionEvent event) {
    }

    @FXML
    private void newGameAction(ActionEvent event) {
    }

    @FXML
    private void exitAction(ActionEvent event) {
    }

    @FXML
    private void gameLevelAction(ActionEvent event) {
    }

    @FXML
    private void gameInfoAction(ActionEvent event) {
    }

}
