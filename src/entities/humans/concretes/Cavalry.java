package entities.humans.concretes;

import entities.buildings.abstracts.Building;
import entities.Item;
import entities.buildings.concretes.University;
import entities.humans.abstracts.Soldier;
import game.Player;
import entities.Resources;
import exceptions.AgeOfEmpiresException;

public class Cavalry extends Soldier {
    public Cavalry() {
        super(20, new Resources(3, 10, 0),
                9, 5, 1, (float) Math.sqrt(2));
    }

    public Cavalry(Player ownerPlayer, int x, int y) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, 20, new Resources(3, 10, 0),
                9, 5, 1, (float) Math.sqrt(2));
    }

    public Cavalry(Player ownerPlayer, int x, int y, int lifePoints) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, lifePoints, new Resources(3, 10, 0),
                9, 5, 1, (float) Math.sqrt(2));
    }

    @Override
    public int getTrainingCount() {
        University university = getOwnerPlayer().getUniversity();
        if (university != null) {
            return university.getCavalryTrainingCount();
        }
        return 0;
    }

    @Override
    public void makeAttackAdjustments(int x, int y) {
        Item item = getOwnerPlayer().getCurrentGame().getMap().getItemAtCoordinates(x, y);
        if (item instanceof Cavalry || item instanceof Building) {
            setAttackPower(5);
        } else {
            setAttackPower(10);
        }
    }

    @Override
    public String getSymbol() {
        return "A";
    }
}
