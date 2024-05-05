package entities.humans.concretes;

import constants.HumanConstants.SwordmanConstants;
import entities.buildings.concretes.University;
import entities.humans.abstracts.Soldier;
import game.Player;
import exceptions.AgeOfEmpiresException;

public class Swordman extends Soldier {
    public Swordman() {
        super(SwordmanConstants.LIFE_POINTS, SwordmanConstants.COST,
                SwordmanConstants.MOVEMENT_SPEED, SwordmanConstants.ATTACK_POWER,
                SwordmanConstants.LOWER_ATTACK_DISTANCE_LIMIT, SwordmanConstants.UPPER_ATTACK_DISTANCE_LIMIT);
    }

    public Swordman(Player ownerPlayer, int x, int y) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, SwordmanConstants.LIFE_POINTS, SwordmanConstants.COST,
                SwordmanConstants.MOVEMENT_SPEED, SwordmanConstants.ATTACK_POWER,
                SwordmanConstants.LOWER_ATTACK_DISTANCE_LIMIT, SwordmanConstants.UPPER_ATTACK_DISTANCE_LIMIT);
    }

    public Swordman(Player ownerPlayer, int x, int y, int lifePoints) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, lifePoints, SwordmanConstants.COST,
                SwordmanConstants.MOVEMENT_SPEED, SwordmanConstants.ATTACK_POWER,
                SwordmanConstants.LOWER_ATTACK_DISTANCE_LIMIT, SwordmanConstants.UPPER_ATTACK_DISTANCE_LIMIT);
    }

    @Override
    public int getTrainingCount() {
        University university = getOwnerPlayer().getUniversity();
        if (university != null) {
            return university.getInfantryTrainingCount();
        }
        return 0;
    }

    @Override
    public void makeAttackAdjustments(int x, int y) {
    }

    @Override
    public String getSymbol() {
        return SwordmanConstants.SYMBOL;
    }
}
