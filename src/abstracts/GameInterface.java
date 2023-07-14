package abstracts;

import concretes.Map;
import concretes.Player;
import exceptions.AgeOfEmpiresException;

public interface GameInterface {

    Player getPlayer(int x) throws AgeOfEmpiresException;

    Map getMap();

    void save_text(String filename) throws AgeOfEmpiresException;

    void save_binary(String filename) throws AgeOfEmpiresException;

}
