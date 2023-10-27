package DadesEstadistiques;

import java.awt.Button;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

public class BarChartNumberGames extends Application {

    Map<String, Integer> jugadorPartidas = new LinkedHashMap();

    public BarChartNumberGames(Map<String, Integer> jugadorPartidas) {
        this.jugadorPartidas = jugadorPartidas;
        init();
    }
    
    /**
     * El método que se encarga de mostrar el gráfico sobre la cantidad de
     * partidas jugadas por cada jugador, mediante la lista retornada por la
     * consulta de la clase PartidesDAPImpl.
     */
    @Override
    public void start(Stage stage) {
        stage.getIcons().add(new Image("imagenes/mastermindNouIcon8.png"));
        stage.setTitle("Player games");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc
                = new BarChart<String, Number>(xAxis, yAxis);

        bc.setTitle("Number of player games");
        xAxis.setLabel("Player");
        yAxis.setLabel("Games");

        XYChart.Series series = new XYChart.Series();
        series.setName("Games");

        jugadorPartidas.entrySet().forEach((entry) -> {
            String key = entry.getKey();
            Integer value = entry.getValue();
            XYChart.Data data = new XYChart.Data(key, value);
            series.getData().add(data);
        });
        
        Scene scene = new Scene(bc, 800, 600);
        bc.getData().addAll(series);
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
