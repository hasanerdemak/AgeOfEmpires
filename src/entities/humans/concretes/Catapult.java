package entities.humans.concretes;

import entities.Item;
import entities.buildings.concretes.University;
import entities.humans.abstracts.Human;
import entities.humans.abstracts.Soldier;
import game.Player;
import entities.Resources;
import exceptions.AgeOfEmpiresException;

public class Catapult extends Soldier {
    public Catapult() {
        super(10, new Resources(30, 20, 5),
                1, 30, 6, 10);
    }

    public Catapult(Player ownerPlayer, int x, int y) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, 10, new Resources(30, 20, 5),
                1, 30, 6, 10);
    }

    public Catapult(Player ownerPlayer, int x, int y, int lifePoints) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, lifePoints, new Resources(30, 20, 5),
                1, 30, 6, 10);
    }

    @Override
    public int getTrainingCount() {
        University university = getOwnerPlayer().getUniversity();
        if (university != null) {
            return university.getCatapultTrainingCount();
        }
        return 0;
    }

    @Override
    public void makeAttackAdjustments(int x, int y) {
        Item item = getOwnerPlayer().getCurrentGame().getMap().getItemAtCoordinates(x, y);
        if (item instanceof Human) {
            setAttackPower(Integer.MAX_VALUE);
        } else {
            setAttackPower(30);
        }
    }

    @Override
    public String getSymbol() {
        return "C";
    }
}
