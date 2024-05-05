package entities.humans.concretes;

import constants.HumanConstants.CavalryConstants;
import entities.buildings.abstracts.Building;
import entities.Item;
import entities.buildings.concretes.University;
import entities.humans.abstracts.Soldier;
import game.Player;
import exceptions.AgeOfEmpiresException;

public class Cavalry extends Soldier {
    public Cavalry() {
        super(CavalryConstants.LIFE_POINTS, CavalryConstants.COST,
                CavalryConstants.MOVEMENT_SPEED, CavalryConstants.ATTACK_POWER,
                CavalryConstants.LOWER_ATTACK_DISTANCE_LIMIT, CavalryConstants.UPPER_ATTACK_DISTANCE_LIMIT);
    }

    public Cavalry(Player ownerPlayer, int x, int y) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, CavalryConstants.LIFE_POINTS, CavalryConstants.COST,
                CavalryConstants.MOVEMENT_SPEED, CavalryConstants.ATTACK_POWER,
                CavalryConstants.LOWER_ATTACK_DISTANCE_LIMIT, CavalryConstants.UPPER_ATTACK_DISTANCE_LIMIT);
    }

    public Cavalry(Player ownerPlayer, int x, int y, int lifePoints) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, lifePoints, CavalryConstants.COST,
                CavalryConstants.MOVEMENT_SPEED, CavalryConstants.ATTACK_POWER,
                CavalryConstants.LOWER_ATTACK_DISTANCE_LIMIT, CavalryConstants.UPPER_ATTACK_DISTANCE_LIMIT);
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
            setAttackPower(CavalryConstants.ATTACK_POWER_TO_CAVALRY_AND_BUILDING);
        } else {
            setAttackPower(CavalryConstants.ATTACK_POWER);
        }
    }

    @Override
    public String getSymbol() {
        return CavalryConstants.SYMBOL;
    }
}
