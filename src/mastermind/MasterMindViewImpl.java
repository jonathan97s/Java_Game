package mastermind;

import BaseDades.ConnexioMySQLConfgiConnexioDataSource;
import BaseDades.MyException;
import BaseDades.PartidesDAOImpl;
import DadesEstadistiques.BarCharGamesPlayer;
import DadesEstadistiques.BarChartNumberGames;
import DadesEstadistiques.TableViewTopPlayers;
import DadesPartides.Partida;
import GameLevel.GameLevelController;
import Login.LoginController;
import MastContract.MasterContract.*;
import Messages.ExitGameMessageController;
import Messages.LogOutMessegeController;
import Properties.Props;
import Properties.PropsUsuari;
import static java.applet.Applet.newAudioClip;
import java.applet.AudioClip;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mastermind.MasterMindModelImpl.*;

public class MasterMindViewImpl implements MasterMindView {

    private MasterMindPresenter presenter;
    private List<Color> listColors = new ArrayList<>();
    private Integer numColorsGrid = 4;
    private int col = 0, row = 9, click = 0, contadorPrintarColor = 0;
    private String s = (new Button()).getStyle();
    private String nombreUsuario;
    private Button b;
    private Props pr = new Props();
    private PropsUsuari pu = new PropsUsuari();
    private PartidesDAOImpl pdi = new PartidesDAOImpl(new ConnexioMySQLConfgiConnexioDataSource());
    private Stage stage;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button buttonStart;
    @FXML
    private Button buttonNewGame;
    @FXML
    private Button buttonExit;
    @FXML
    private Button buttonCheck;
    @FXML
    private GridPane gridPColors;
    @FXML
    private GridPane gridPJoc;
    @FXML
    private GridPane gridPAyuda;
    @FXML
    private Button buttonHelp;
    @FXML
    private MenuItem logOutItem;
    @FXML
    private MenuItem rankingitem;
    @FXML
    private MenuItem matchesitem;
    @FXML
    private MenuItem gamesitem;
    @FXML
    private Button GridPPista;
    @FXML
    private Button buttonBlue;
    @FXML
    private Button buttonGreen;
    @FXML
    private Button buttonYellow;
    @FXML
    private Button buttonRed;
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
    private Button buttonClear;
    @FXML
    private Button deleteButton;

    /**
     * Se encarga de eliminar el color de lista de combinación.
     */
    @FXML
    private void deleteAction() {
        if (col >= 10) {
            click--;
            col = col - 10;
            contadorPrintarColor = row + col;
            b = (Button) gridPJoc.getChildren().get(contadorPrintarColor);
            b.setStyle(s);
            int size = listColors.size();

            listColors.remove(size - 1);
            buttonCheck.setDisable(true);
        }
    }

    /**
     * Se encarga de vaciar toda la lista de combinación de colores.
     */
    @FXML
    private void clearAction() {
        while (col >= 10) {
            col = col - 10;
            contadorPrintarColor = row + col;
            b = (Button) gridPJoc.getChildren().get(contadorPrintarColor);
            b.setStyle(s);
        }
        click = 0;
        buttonCheck.setDisable(true);
    }

    /**
     * Es la action del MenuItem de la barra menú, que empieze la partida.
     *
     */
    @FXML
    private void startAction() {
        buttonClickSound();
        presenter.startGame(numColorsGrid);
    }

    /**
     * Es la action del MenuItem de la barra menú, que abre la ventana donde
     * podemos encontrar la instrucción del juego.
     *
     */
    @FXML
    private void gameInfoAction() {
        buttonClickSound();
        Ayuda a = new Ayuda();
    }

    /**
     * Es la action del MenuItem de la barra menú, que cierra el juego.
     *
     */
    @FXML
    private void exitAction() {
        buttonClickSound();
        try {
            FXMLLoader loader = new FXMLLoader(ExitGameMessageController.class.getResource("ExitGameMessage.fxml"));
            Parent root = loader.load();
            ExitGameMessageController em = loader.getController();
            Scene scene = new Scene(root);
            Stage stageInformacio = new Stage();
            stageInformacio.setScene(scene);
            stageInformacio.initModality(Modality.APPLICATION_MODAL);
            stageInformacio.getIcons().add(new Image("imagenes/mastermindNouIcon8.png"));
            stageInformacio.setTitle("Información");
            stageInformacio.setResizable(false);
            stageInformacio.showAndWait();
            if (em.getAceptado() == 1) {
                buttonClickSound();
                System.exit(0);
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }

    /**
     * Es la action del MenuItem de la barra menú, que muestra la ventana, en la
     * que podemos configurar el nivel de juego y lo guarda en el fichero de
     * propiedades.
     *
     */
    @FXML
    private void gameLevelAction() {
        buttonClickSound();
        try {
            FXMLLoader loader = new FXMLLoader(GameLevelController.class.getResource("GameLevel.fxml"));
            Parent root = loader.load();
            GameLevelController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Nivel de juego");
            stage.getIcons().add(new Image("imagenes/mastermindNouIcon8.png"));
            stage.setResizable(false);
            stage.setScene(scene);
            stage.showAndWait();
            if (controller.getConfirmar() == true) {
                numColorsGrid = controller.getNumColorsJoc();
                setPane();
                presenter.newGame();
                gridPColors.setDisable(true);
                buttonNewGame.setDisable(true);
                Properties p = pr.cargarProperties();
                pr.modificarProperties(p, numColorsGrid);
                pr.guardarProperties(p);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Es la action del MenuItem de la barra menú, que empieze una nueva
     * partida.
     *
     */
    @FXML
    private void newGameAction() {
        buttonClickSound();
        desabilitarButtons();
        presenter.newGame();
    }

    /**
     * Constructor que ejecuta el juego.
     *
     * @param stage
     */
    public MasterMindViewImpl(Stage stage) {
        initUI(stage);
        desabilitarButtons();
    }

    /**
     * El método que inserta las columnas extras segun el nivel del juego.
     */
    public void setPane() {

        gridPJoc.getColumnConstraints().clear();
        gridPJoc.getRowConstraints().clear();
        gridPJoc.getChildren().clear();
        gridPJoc.setPrefSize(300, 500);

        gridPJoc.setLayoutX(107);
        gridPJoc.setLayoutY(175);

        for (int i = 0; i < numColorsGrid; i++) {
            for (int j = 0; j < 10; j++) {
                Button b = new Button();
                gridPJoc.add(b, i, j);
                gridPJoc.setMargin(b, new Insets(10, 5, 10, 5));
                b.setPrefSize(65, 40);
            }
        }

        gridPAyuda.getColumnConstraints().clear();
        gridPAyuda.getRowConstraints().clear();
        gridPAyuda.getChildren().clear();
        gridPAyuda.setPrefSize(250, 500);

        gridPAyuda.setLayoutX(420);
        gridPAyuda.setLayoutY(175);

        for (int i = 0; i < numColorsGrid; i++) {
            for (int j = 0; j < 10; j++) {
                Button b = new Button();
                gridPAyuda.add(b, i, j);
                gridPAyuda.setMargin(b, new Insets(10, 6, 10, 6));
                b.setPrefSize(30, 30);
                b.setStyle("-fx-background-color: #a4a4a4;"
                        + "-fx-background-radius: 100%;");
            }
        }
    }

    @Override
    public void setPresenter(MasterMindPresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Es el metodo para hacer visible el juego, y mantener la configuración de
     * la última partida.
     *
     * @param stage
     */
    private void initUI(Stage stage) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setController(this);
            loader.setLocation(getClass().getResource("FXMLDocument.fxml"));
            Properties p = pr.cargarProperties();
            numColorsGrid = pr.propertiesIniciales(p);
            Properties pro = pu.cargarProperties();
            nombreUsuario = pu.propertiesIniciales(pro);
            Parent root = loader.load();
            setPane();
            configButtonsHandle();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.getIcons().add(new Image("imagenes/mastermindNouIcon8.png"));
            stage.setResizable(false);
            stage.setTitle("MasterMind");
            stage.show();

            logOutItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    stage.close();
                    Stage stageLogin = new Stage();
                    LoginController lc = new LoginController(stageLogin);
                }
            });
            rankingitem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        TableViewTopPlayers ventana = new TableViewTopPlayers(pdi.getTop10Partidas(nombreUsuario), nombreUsuario);
                    } catch (MyException ex) {
                        Logger.getLogger(MasterMindViewImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            matchesitem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        BarCharGamesPlayer ventana = new BarCharGamesPlayer(pdi.getPartidasUsuario(nombreUsuario));
                    } catch (MyException ex) {
                        Logger.getLogger(MasterMindViewImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            gamesitem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        BarChartNumberGames ventana = new BarChartNumberGames(pdi.getNumPartidasPorUsuario());
                    } catch (MyException ex) {
                        Logger.getLogger(MasterMindViewImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("loading FXMLDocument.xml", e);
        }

    }

    /**
     * El método para habilitar botones del juego en el caso necesario.
     */
    public void habilitarButtons() {
        buttonNewGame.setDisable(false);
        gridPColors.setDisable(false);
        buttonStart.setDisable(true);
    }

    /**
     * El método para desabilitar botones del juego en el caso necesario.
     */
    public void desabilitarButtons() {
        buttonCheck.setDisable(true);
        buttonNewGame.setDisable(true);
        gridPColors.setDisable(true);
    }

    /**
     * Es el encargado de lanzar el audio en el caso en el que el jugador pierde
     * la partida.
     *
     * @param numColors
     */
    @Override
    public void winGame(int numColors) {
        if (presenter.getIntentos() <= 5) {
            AudioClip audio = newAudioClip(getClass().getResource("/Audios/gameWin.wav"));
            audio.play();
        } else {
            AudioClip audio = newAudioClip(getClass().getResource("/Audios/gameWin(2).wav"));
            audio.play();
        }
        presenter.win(numColors);
    }

    /**
     * Es el encargado de lanzar el audio en el caso en el que el jugador pierde
     * la partida.
     *
     * @param numColors
     */
    @Override
    public void overGame(int numColors) {
        AudioClip audio = newAudioClip(getClass().getResource("/Audios/gameOver.wav"));
        audio.play();
        presenter.over(numColors);
    }

    /**
     * Este método contiene todas las acciones para cada uno de los botones del
     * juego.
     */
    private void configButtonsHandle() {
        EventHandler start = new EventHandler() {
            @Override
            public void handle(Event event) {
                buttonClickSound();
                presenter.startGame(numColorsGrid);
                Props pr = new Props();
                Properties p = pr.cargarProperties();
                pr.muestraProperties(p);
            }
        };

        /**
         * La acción de empezar una partida nueva.
         */
        EventHandler newGame = new EventHandler() {
            @Override
            public void handle(Event event) {
                buttonClickSound();
                desabilitarButtons();
                presenter.newGame();
            }
        };

        /**
         * La acción para cerrra el juego.
         */
        EventHandler exit = new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    buttonClickSound();
                    Button b = (Button) event.getSource();
                    Stage stageMasterMind = (Stage) b.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(ExitGameMessageController.class.getResource("ExitGameMessage.fxml"));
                    Parent root = loader.load();
                    ExitGameMessageController em = loader.getController();
                    Scene scene = new Scene(root);
                    Stage stageInformacio = new Stage();
                    stageInformacio.setScene(scene);
                    stageInformacio.initModality(Modality.APPLICATION_MODAL);
                    stageInformacio.getIcons().add(new Image("imagenes/mastermindNouIcon8.png"));
                    stageInformacio.setTitle("Información");
                    stageInformacio.setResizable(false);
                    stageInformacio.showAndWait();
                    if (em.getAceptado() == 1) {
                        stageMasterMind.close();
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        };

        /**
         * La acción para validar la jugada, y una vez que gane o pierde la
         * partida, se almacena toda la infomarción en la base da datos.
         */
        EventHandler validatecheck = new EventHandler() {
            @Override
            public void handle(Event event) {
                col = 0;
                click = 0;
                buttonClickSound();
                presenter.validateCheck(listColors);
                int MatcheOK = presenter.getLlistaResult().get(Matches.OK).intValue();
                int intentos = presenter.getIntentos();
                boolean estat;
                int dificultat = numColorsGrid;
                int puntuacio;
                if (MatcheOK == numColorsGrid && intentos <= 5) {
                    winGame(numColorsGrid);
                    gridPColors.setDisable(true);
                    estat = true;
                    puntuacio = 13 * (11 - intentos);
                    try {
                        Partida p = new Partida(nombreUsuario, estat, intentos, dificultat, puntuacio);
                        pdi.afegirPartida(p);
                    } catch (MyException ex) {
                        ex.printStackTrace();
                    }
                } else if (MatcheOK == numColorsGrid && (intentos > 5 && intentos <= 10)) {
                    winGame(numColorsGrid);
                    gridPColors.setDisable(true);
                    estat = true;
                    puntuacio = 13 * (11 - intentos);
                    try {
                        Partida p = new Partida(nombreUsuario, estat, intentos, dificultat, puntuacio);
                        pdi.afegirPartida(p);
                    } catch (MyException ex) {
                        ex.printStackTrace();
                    }
                } else if (MatcheOK != numColorsGrid && intentos == 10) {
                    overGame(numColorsGrid);
                    gridPColors.setDisable(true);
                    estat = false;
                    puntuacio = -10;
                    try {
                        Partida p = new Partida(nombreUsuario, estat, dificultat, puntuacio);
                        pdi.afegirPartida(p);
                    } catch (MyException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };

        /**
         * La acción para asignar a cada botón de color el atributo del objeto
         * Color.
         */
        EventHandler printar = new EventHandler() {
            @Override
            public void handle(Event event) {
                buttonClickSound();
                Button b = (Button) event.getSource();
                Color c = null;
                if (b.getId().equals("buttonRed")) {
                    c = Color.VERMELL;
                } else if (b.getId().equals("buttonBlue")) {
                    c = Color.BLAU;
                } else if (b.getId().equals("buttonGreen")) {
                    c = Color.VERD;
                } else if (b.getId().equals("buttonYellow")) {
                    c = Color.GROC;
                }
                setGridPJoc(c);
            }
        };

        /**
         * La acción para hacer visible una ventana en la qual contiene toda la
         * instrucción del juego.
         */
        EventHandler help = new EventHandler() {
            @Override
            public void handle(Event event) {
                buttonClickSound();
                Ayuda a = new Ayuda();
                a.setLocationRelativeTo(null);
                a.setVisible(true);
            }
        };

        buttonStart.setOnAction(start);
        buttonCheck.setOnAction(validatecheck);
        buttonNewGame.setOnAction(newGame);
        buttonExit.setOnAction(exit);
        buttonHelp.setOnAction(help);

        Button b = new Button();
        for (Object object : gridPColors.getChildren()) {
            b = (Button) object;
            b.setOnAction(printar);
        }
    }

    /**
     * Recibe el atributo, y sengún el nombre del atributo pinta los campos de
     * cada jugada.
     *
     * @param c
     */
    @Override
    public void setGridPJoc(Color c) {

        if (click == 0) {
            listColors.clear();
        }
        if (click < numColorsGrid) {
            buttonCheck.setDisable(true);
            contadorPrintarColor = row + col;
            Button b = (Button) gridPJoc.getChildren().get(contadorPrintarColor);
            b.setStyle("-fx-background-color: " + c.getI());
            listColors.add(c);
            col = col + 10;
            click++;
        }
        if (numColorsGrid == click) {
            buttonCheck.setDisable(false);
        }
    }

    /**
     * Es el método que empieze una nueva partida.
     */
    public void reiniciaGriPJoc() {

        Object o = null;
        Button b = null;

        for (Node node : gridPJoc.getChildren()) {
            o = (Object) node;
            b = (Button) o;
            b.setStyle(s);
        }

        for (Node node : gridPAyuda.getChildren()) {
            o = (Object) node;
            b = (Button) o;
            b.setStyle("-fx-background-color: #a4a4a4;"
                    + "-fx-background-radius: 100%;");
        }
        click = 0;
        col = 0;
        row = 9;
        buttonStart.setDisable(false);

    }

    /**
     * Recibe un map para hace la validación de cada jugada del juego, y pinta
     * los campos de la ayuda.
     *
     * @param mapa
     */
    @Override
    public void play(Map<Matches, Integer> mapa) {
        Object o = null;
        boolean continuar = true;

        int negre = mapa.get(Matches.OK).intValue();
        int blanc = mapa.get(Matches.WRONG_POSITION).intValue();

        for (int i = 0; i < numColorsGrid; i++) {
            contadorPrintarColor = row + col;
            b = (Button) gridPAyuda.getChildren().get(contadorPrintarColor);
            if (negre > 0) {
                b.setStyle("-fx-background-color: #000000;"
                        + "-fx-background-radius: 100%;");
                negre--;
            } else if (blanc > 0) {
                b.setStyle("-fx-background-color: WHITE;"
                        + "-fx-background-radius: 100%;");
                blanc--;
            }
            col = col + 10;
        }
        row--;
        col = 0;
        buttonCheck.setDisable(true);
        gridPColors.setDisable(false);

    }

    /**
     * El método que lanza el sonido al dar cualquier de los botones del juego.
     */
    public void buttonClickSound() {
        AudioClip audio = newAudioClip(getClass().getResource("/Audios/buttonClick.wav"));
        audio.play();
    }

}
