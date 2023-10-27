package mastermind;

import Messages.MessageController;
import MastContract.MasterContract;
import javax.swing.JOptionPane;

public class GameListener implements MasterContract.GameListener{

    @Override
    public void over(String message) {
        MessageController mc = new MessageController();
        mc.finestraInformacio(message);
    }

    @Override
    public void win(String message) {
        MessageController mc = new MessageController();
        mc.finestraInformacio(message);
    }
    
}
