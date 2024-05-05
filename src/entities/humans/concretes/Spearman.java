package entities.humans.concretes;

import constants.HumanConstants.SpearmanConstants;
import entities.Item;
import entities.buildings.concretes.University;
import entities.humans.abstracts.Soldier;
import game.Player;
import exceptions.AgeOfEmpiresException;

public class Spearman extends Soldier {
    public Spearman() {
        super(SpearmanConstants.LIFE_POINTS, SpearmanConstants.COST,
                SpearmanConstants.MOVEMENT_SPEED, SpearmanConstants.ATTACK_POWER,
                SpearmanConstants.LOWER_ATTACK_DISTANCE_LIMIT, SpearmanConstants.UPPER_ATTACK_DISTANCE_LIMIT);
    }

    public Spearman(Player ownerPlayer, int x, int y) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, SpearmanConstants.LIFE_POINTS, SpearmanConstants.COST,
                SpearmanConstants.MOVEMENT_SPEED, SpearmanConstants.ATTACK_POWER,
                SpearmanConstants.LOWER_ATTACK_DISTANCE_LIMIT, SpearmanConstants.UPPER_ATTACK_DISTANCE_LIMIT);
    }

    public Spearman(Player ownerPlayer, int x, int y, int lifePoints) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, lifePoints, SpearmanConstants.COST,
                SpearmanConstants.MOVEMENT_SPEED, SpearmanConstants.ATTACK_POWER,
                SpearmanConstants.LOWER_ATTACK_DISTANCE_LIMIT, SpearmanConstants.UPPER_ATTACK_DISTANCE_LIMIT);
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
        Item item = getOwnerPlayer().getCurrentGame().getMap().getItemAtCoordinates(x, y);
        if (item instanceof Cavalry) {
            setAttackPower(SpearmanConstants.ATTACK_POWER_TO_CAVALRY);
        } else {
            setAttackPower(SpearmanConstants.ATTACK_POWER);
        }
    }

    @Override
    public String getSymbol() {
        return SpearmanConstants.SYMBOL;
    }
}
