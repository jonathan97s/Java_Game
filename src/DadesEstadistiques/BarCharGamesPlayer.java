package DadesEstadistiques;

import DadesPartides.Partida;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class BarCharGamesPlayer extends Application {

    List<Partida> games = new ArrayList<>();

    public BarCharGamesPlayer(List<Partida> games) {
        this.games = games;
        init();
    }

    /**
     * El método que se encarga de mostrar el gráfico sobre la cantidad de
     * partidas jugadas en diferentes niveles por el jugador que ha iniciado la
     * sesión, mediante la lista retornada por la consulta de la clase
     * PartidesDAPImpl.
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        stage.getIcons().add(new Image("imagenes/mastermindNouIcon8.png"));
        stage.setTitle("Player Games");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis(0, 130, 1);
        final BarChart<String, Number> bc
                = new BarChart<String, Number>(xAxis, yAxis);
        bc.setTitle("Games");
        xAxis.setLabel("Player");
        xAxis.setMaxWidth(10);
        yAxis.setLabel("Score");

        XYChart.Series seriesLevel4 = new XYChart.Series();
        seriesLevel4.setName("Level 4 colors");

        XYChart.Series seriesLevel5 = new XYChart.Series();
        seriesLevel5.setName("Level 5 colors");

        XYChart.Series seriesLevel6 = new XYChart.Series();
        seriesLevel6.setName("Level 6 colors");

        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).getDificultat() == 4) {
                seriesLevel4.getData().add(new XYChart.Data(games.get(i).getNum(), games.get(i).getPuntuacio()));
            } else if (games.get(i).getDificultat() == 5) {
                seriesLevel5.getData().add(new XYChart.Data(games.get(i).getNum(), games.get(i).getPuntuacio()));
            } else {
                seriesLevel6.getData().add(new XYChart.Data(games.get(i).getNum(), games.get(i).getPuntuacio()));
            }
        }
        Scene scene = new Scene(bc, 800, 600);
        bc.getData().addAll(seriesLevel4, seriesLevel5, seriesLevel6);
        stage.setScene(scene);
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
