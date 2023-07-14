package abstracts;

import concretes.Player;

public interface ItemInterface {


    int getX();

    int getY();

    int getLifePoints();

    String getSymbol();

    default String print_message() {
        return getSymbol() + "\t" + getX() + "\t" + getY() + "\t" + getLifePoints();
    }

    Player getOwnerPlayer();

    boolean checkIfAlive();

}
