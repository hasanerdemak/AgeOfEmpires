package entities.humans.concretes;

import constants.HumanConstants.CatapultConstants;
import entities.Item;
import entities.buildings.concretes.University;
import entities.humans.abstracts.Human;
import entities.humans.abstracts.Soldier;
import game.Player;
import exceptions.AgeOfEmpiresException;

public class Catapult extends Soldier {
    public Catapult() {
        super(CatapultConstants.LIFE_POINTS, CatapultConstants.COST,
                CatapultConstants.MOVEMENT_SPEED, CatapultConstants.ATTACK_POWER,
                CatapultConstants.LOWER_ATTACK_DISTANCE_LIMIT, CatapultConstants.UPPER_ATTACK_DISTANCE_LIMIT);
    }

    public Catapult(Player ownerPlayer, int x, int y) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, CatapultConstants.LIFE_POINTS, CatapultConstants.COST,
                CatapultConstants.MOVEMENT_SPEED, CatapultConstants.ATTACK_POWER,
                CatapultConstants.LOWER_ATTACK_DISTANCE_LIMIT, CatapultConstants.UPPER_ATTACK_DISTANCE_LIMIT);
    }

    public Catapult(Player ownerPlayer, int x, int y, int lifePoints) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, lifePoints, CatapultConstants.COST,
                CatapultConstants.MOVEMENT_SPEED, CatapultConstants.ATTACK_POWER,
                CatapultConstants.LOWER_ATTACK_DISTANCE_LIMIT, CatapultConstants.UPPER_ATTACK_DISTANCE_LIMIT);
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
            setAttackPower(CatapultConstants.ATTACK_POWER_TO_HUMAN);
        } else {
            setAttackPower(CatapultConstants.ATTACK_POWER);
        }
    }

    @Override
    public String getSymbol() {
        return CatapultConstants.SYMBOL;
    }
}
