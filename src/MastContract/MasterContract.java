package MastContract;

import java.util.*;
import javafx.stage.Stage;
import mastermind.ColorCombination;
import mastermind.ColorCombination;
import mastermind.MasterMindModelImpl.*;

public interface MasterContract {

    interface MasterMindModel {

        void start(int numColors);

        List<Color> getCombSecreta();

        Map<Matches, Integer> validatePlay(ColorCombination color);

        void addListener(GameListener listener, int numColors);
        
        void setIntentos(int intentos);
        
        int getIntentos();      
        
    }

    interface MasterMindView {

        void setPresenter(MasterMindPresenter p);

        void winGame(int numColors);

        void overGame(int numColors);

        void play(Map<Matches, Integer> mapa);

        void setGridPJoc(Color c);

        void habilitarButtons();

        void desabilitarButtons();

        void reiniciaGriPJoc(); 

    }

    interface MasterMindPresenter {

        void setView(MasterMindView view);

        void setModel(MasterMindModel model);

        void startGame(int numColors);

        void validateCheck(List<Color> colors);

        void win(int numColors);

        void over(int numColors);

        int getIntentos();
        
        void newGame();
  
        Map<Matches, Integer> getLlistaResult();
        
    }

    interface GameListener {

        void over(String message);

        void win(String message);

    }
}
