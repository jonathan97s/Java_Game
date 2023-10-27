package BaseDades;

import BaseDades.MyException;
import DadesUsuaris.Usuari;
import java.util.List;


public interface UsuariDAO {
    
    boolean afegirUsuari(Usuari u) throws MyException;

    boolean cercarUser(Usuari u) throws MyException;
   
    boolean decryptPassword(String enteredPassword, String storedHash)throws MyException;
}
