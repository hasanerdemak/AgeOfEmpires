package abstracts;

import exceptions.AgeOfEmpiresException;

public interface HumanInterface {

    void attack(int x, int y) throws AgeOfEmpiresException;

    void move(int x, int y) throws AgeOfEmpiresException;

    int getLifePoints();

    int getLifePoints_WithoutPrinting();


}
