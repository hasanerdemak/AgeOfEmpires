package abstracts;

import concretes.Player;

public interface ItemInterface {


    int getX();

    int getY();  // y koordinatını yazar

    int getLifePoints(); // kaç canı kalmışsa onu yazar

    String getSymbol();

    default String print_message() {
        return getSymbol() + "\t" + getX() + "\t" + getY() + "\t" + getLifePoints();
    }

    Player getOwnerPlayer();

    boolean checkIfAlive();

}
