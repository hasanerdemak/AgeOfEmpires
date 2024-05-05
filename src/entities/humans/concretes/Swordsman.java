package entities.humans.concretes;

import constants.HumanConstants.SwordsmanConstants;
import entities.buildings.concretes.University;
import entities.humans.abstracts.Soldier;
import game.Player;
import exceptions.AgeOfEmpiresException;

public class Swordsman extends Soldier {
    public Swordsman() {
        super(SwordsmanConstants.LIFE_POINTS, SwordsmanConstants.COST,
                SwordsmanConstants.MOVEMENT_SPEED, SwordsmanConstants.ATTACK_POWER,
                SwordsmanConstants.LOWER_ATTACK_DISTANCE_LIMIT, SwordsmanConstants.UPPER_ATTACK_DISTANCE_LIMIT);
    }

    public Swordsman(Player ownerPlayer, int x, int y) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, SwordsmanConstants.LIFE_POINTS, SwordsmanConstants.COST,
                SwordsmanConstants.MOVEMENT_SPEED, SwordsmanConstants.ATTACK_POWER,
                SwordsmanConstants.LOWER_ATTACK_DISTANCE_LIMIT, SwordsmanConstants.UPPER_ATTACK_DISTANCE_LIMIT);
    }

    public Swordsman(Player ownerPlayer, int x, int y, int lifePoints) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, lifePoints, SwordsmanConstants.COST,
                SwordsmanConstants.MOVEMENT_SPEED, SwordsmanConstants.ATTACK_POWER,
                SwordsmanConstants.LOWER_ATTACK_DISTANCE_LIMIT, SwordsmanConstants.UPPER_ATTACK_DISTANCE_LIMIT);
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
        return SwordsmanConstants.SYMBOL;
    }
}
