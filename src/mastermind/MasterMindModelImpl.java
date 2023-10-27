package mastermind;

import MastContract.MasterContract.*;
import MastContract.MasterContract.GameListener;
import static java.applet.Applet.newAudioClip;
import java.applet.AudioClip;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MasterMindModelImpl implements MasterMindModel {

    private MasterMindPresenter presenter;
    private int intentos = 0;
    private Map<Matches, Integer> llistaResult = new HashMap();
    private List<Color> combSecreta = new ArrayList();
    private String message;

    
    /**
     * Lista específica de colores.
     */
    public enum Color {
        BLAU("BLUE"), VERMELL("RED"), GROC("YELLOW"), VERD("GREEN");

        private String i;

        Color(String i) {
            this.i = i;
        }

        public String getI() {
            return i;
        }
    };

     /**
     * Lista específica de mathes.
     */
    public enum Matches {
        OK(1), WRONG_POSITION(2);

        private int opcio;

        Matches(int opcio) {
            this.opcio = opcio;
        }
    };


    /**
     * Este método lanza el mensaje de puntuación de la partida según los intentos.
     * @param listener
     * @param numColors 
     */
    @Override
    public void addListener(GameListener listener, int numColors) {

        int MatcheOK = getLlistaResult().get(Matches.OK).intValue();

        if (MatcheOK == numColors && this.intentos <= 5) {
            this.message = "Muy bien, es la mejor.";           
            listener.win(message);
        } else if (MatcheOK == numColors && (intentos > 5 && intentos <= 10)) {
            this.message = "Bien, todavia puedes mejorar más.";
            listener.win(message);
        } else if (MatcheOK != numColors && intentos == 10) {
            this.message = "Game over";
            listener.over(message);
        }
    }

    
    /**
     * Este método se encarga de iniciar el juego y generar combinaciones
     * secretas, y cuya cantidad depende de la variable que recibe como parámetro.
     * @param numColors
     */
    @Override
    public void start(int numColors) {

        this.setIntentos(0);
        combSecreta = new ArrayList();
        int[] combinacio = new int[numColors];
        Random random = new Random();
        for (int i = 0; i < combinacio.length; i++) {
            combinacio[i] = random.nextInt(4) + 1;
            switch (combinacio[i]) {
                case 1:
                    Color c = Color.BLAU;
                    combSecreta.add(c);
                    break;
                case 2:
                    Color c2 = Color.VERMELL;
                    combSecreta.add(c2);
                    break;
                case 3:
                    Color c3 = Color.GROC;
                    combSecreta.add(c3);
                    break;
                case 4:
                    Color c4 = Color.VERD;
                    combSecreta.add(c4);
                    break;
            }
        }
        setCombSecreta(combSecreta);
        System.out.println(combSecreta.toString());

    }

    /**
     * Retorna la lista con la combinación secreta.
     * @return 
     */
    @Override
    public List<Color> getCombSecreta() {
        return combSecreta;
    }

    
    /**
     * Recibe como parámetro la lista de colores y la compara con la combinación
     * secreta, guarda el resultado en una lista nueva y la retorna.
     * @param color
     * @return 
     */
    @Override
    public Map<Matches, Integer> validatePlay(ColorCombination color) {

        Integer contadorOK = null;
        Integer contadorWrong = null;
        List<Color> copiaSecreta = new ArrayList();

        for (Color c : combSecreta) {
            copiaSecreta.add(c);
        }

        contadorOK = Integer.valueOf(0);
        contadorWrong = Integer.valueOf(0);

        if (intentos <= 10) {
            for (int i = 0; i < copiaSecreta.size(); i++) {
                if (copiaSecreta.get(i).equals(color.getColors().get(i))) {
                    contadorOK = contadorOK + Integer.valueOf(1);
                    copiaSecreta.remove(i);
                    color.getColors().remove(i);
                    i--;
                }
            }
            llistaResult.put(Matches.OK, contadorOK);

            if (color.getColors().size() > 0) {
                for (int i = 0; i < copiaSecreta.size(); i++) {
                    for (int j = 0; j < copiaSecreta.size(); j++) {
                        if (copiaSecreta.get(i).equals(color.getColors().get(j))) {
                            contadorWrong = contadorWrong + Integer.valueOf(1);
                            copiaSecreta.remove(i);
                            color.getColors().remove(j);
                            i--;
                            j--;
                            break;
                        }
                    }
                }
            }
            llistaResult.put(Matches.WRONG_POSITION, contadorWrong);
            intentos++;

        }
        copiaSecreta.clear();
        return llistaResult;
    }
    
    
    public void setCombSecreta(List<Color> combSecreta) {
        this.combSecreta = combSecreta;
    }

    @Override
    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    @Override
    public int getIntentos() {
        return intentos;
    }

    /**
     * Retorna la lista de resultado al validar la jugada.
     * @return 
     */
    public Map<Matches, Integer> getLlistaResult() {
        return llistaResult;
    }
    
    
    
}
