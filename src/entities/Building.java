package entities;

import abstracts.BuildingInterface;
import concretes.Player;
import concretes.Resources;
import exceptions.AgeOfEmpiresException;

public abstract class Building extends Item implements BuildingInterface {

    public Building(int lifePoints, Resources cost) {
        super(lifePoints, cost);
    }

    public Building(Player ownerPlayer, int x, int y, int lifePoints, Resources cost) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, lifePoints, cost);
    }
}
