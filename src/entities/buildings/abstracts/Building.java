package entities.buildings.abstracts;

import entities.Item;
import entities.Resources;
import exceptions.AgeOfEmpiresException;
import game.Player;
import interfaces.BuildingInterface;

public abstract class Building extends Item implements BuildingInterface {

    public Building(int lifePoints, Resources cost) {
        super(lifePoints, cost);
    }

    public Building(Player ownerPlayer, int x, int y, int lifePoints, Resources cost) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, lifePoints, cost);
    }
}
