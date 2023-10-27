package DadesEstadistiques;

import DadesPartides.Partida;
import java.awt.Color;
import java.util.LinkedHashMap;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TableViewTopPlayers extends Application {

    ObservableList<Partida> data;
    LinkedHashMap<String, Integer> data2;
    String user;

    public TableViewTopPlayers(ObservableList<Partida> data, String user) {
        this.data = data;
        this.user = user;
        init();
    }

    public TableViewTopPlayers(LinkedHashMap<String, Integer> data2, String user) {
        this.data2 = data2;
        this.user = user;
        init();
    }

    /**
     * El método que se encarga de mostrar el ranking donde se encuentran todos
     * los jugadores registrados, mediante la lista retornada por la consulta de
     * la clase PartidesDAPImpl.
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        stage.getIcons().add(new Image("imagenes/mastermindNouIcon8.png"));
        stage.setTitle("Top Games Score");
        TableView<Partida> table = new TableView<>();
        table.setEditable(true);

        TableColumn<Partida, Integer> rankingColumn = new TableColumn<>("Ranking");
        TableColumn<Partida, String> userColumn = new TableColumn<>("User");
        TableColumn<Partida, Integer> scoreColumn = new TableColumn<>("Score");

        rankingColumn.setCellValueFactory(new PropertyValueFactory<>("posicio"));
        rankingColumn.setMinWidth(100);
        rankingColumn.setStyle("-fx-alignment: CENTER;");

        userColumn.setCellValueFactory(new PropertyValueFactory<>("nomUsuari"));
        userColumn.setMinWidth(160);
        userColumn.setStyle("-fx-alignment: CENTER;");

        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("puntuacio"));
        scoreColumn.setMinWidth(100);
        scoreColumn.setStyle("-fx-alignment: CENTER;");

        table.getColumns().addAll(rankingColumn, userColumn, scoreColumn);
        table.setItems(data);

        userColumn.setCellFactory(new Callback<TableColumn<Partida, String>, TableCell<Partida, String>>() {
            @Override
            public TableCell<Partida, String> call(TableColumn<Partida, String> column) {
                return new TableCell<Partida, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            if (item.equals(user)) {
                                setText(item);
                                setStyle("-fx-text-fill: #00BFFF;"
                                        + "-fx-font-weight: bold;"
                                );
                            } else {
                                setText(item);
                                setStyle("");
                            }
                        }
                    }
                };
            }
        });

        VBox vBox = new VBox(table);
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(0));

        Scene scene = new Scene(new Group());
        ((Group) scene.getRoot()).getChildren().addAll(vBox);

        stage.setScene(scene);
        table.prefHeightProperty().addListener((observable, oldValue, newValue) -> stage.sizeToScene());
        table.prefWidthProperty().addListener((observable, oldValue, newValue) -> stage.sizeToScene());
        stage.show();
    }

    /**
     * Lanza el método start.
     */
    public void init() {
        Stage stage = new Stage();
        start(stage);
    }
}
