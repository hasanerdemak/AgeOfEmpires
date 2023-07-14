package abstracts;

import concretes.Player;

public interface ItemInterface {


    int getX();

    int getX_WithoutPrinting();

    int getY();

    int getY_WithoutPrinting();

    int getLifePoints();

    int getLifePoints_WithoutPrinting();

    String getSymbol();

    default String print_message() {
        return getSymbol() + "\t" + getX_WithoutPrinting() + "\t" + getY_WithoutPrinting() + "\t" + getLifePoints_WithoutPrinting();
    }

    Player getOwnerPlayer();

    boolean checkIfAlive();

}
