package mastermind;

import java.util.*;
import mastermind.MasterMindModelImpl.Color;
import mastermind.MasterMindModelImpl.Matches;

public class ColorCombination {

    private List<Color> colorsJugada;
    

    public ColorCombination(List<Color> colors) {
        this.colorsJugada = colors;
    }

    public void setColors(List<Color> colorsJugada) {
        this.colorsJugada = colorsJugada;
    }

    public List<Color> getColors() {
        return colorsJugada;
    }

}
