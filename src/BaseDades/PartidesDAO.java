package BaseDades;

import DadesPartides.Partida;
import java.util.List;
import java.util.Map;
import javafx.collections.ObservableList;


public interface PartidesDAO {
    
    void afegirPartida(Partida p)throws MyException;
         
    ObservableList<Partida> getTop10Partidas(String nombreUsuario)throws MyException;
        
    int[] getPosicionUsuario(String usuario)throws MyException;
    
    List<Partida> getPartidasUsuario(String usuario)throws MyException;
    
    Map<String, Integer> getNumPartidasPorUsuario()throws MyException;
}

