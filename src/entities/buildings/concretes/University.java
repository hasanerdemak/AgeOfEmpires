package entities.buildings.concretes;

import constants.BuildingConstants.UniversityConstants;
import entities.buildings.abstracts.Building;
import exceptions.AgeOfEmpiresException;
import game.Player;
import interfaces.UniversityInterface;
import utils.MoveControlUtils;
import utils.ResourcesUtils;

public class University extends Building implements UniversityInterface {
    private int infantryTrainingCount = 0;
    private int cavalryTrainingCount = 0;
    private int catapultTrainingCount = 0;

    public University() {
        super(UniversityConstants.LIFE_POINTS, UniversityConstants.COST);
    }

    public University(Player ownerPlayer, int x, int y) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, UniversityConstants.LIFE_POINTS, UniversityConstants.COST);
    }

    public University(Player ownerPlayer, int x, int y, int lifePoints) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, lifePoints, UniversityConstants.COST);
    }

    public University(Player ownerPlayer, int x, int y, int infantryTrainingCount, int cavalryTrainingCount, int catapultTrainingCount) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, UniversityConstants.LIFE_POINTS, UniversityConstants.COST);
        this.infantryTrainingCount = infantryTrainingCount;
        this.cavalryTrainingCount = cavalryTrainingCount;
        this.catapultTrainingCount = catapultTrainingCount;
    }

    public int getInfantryTrainingCount() {
        return infantryTrainingCount;
    }

    public void setInfantryTrainingCount(int infantryTrainingCount) {
        this.infantryTrainingCount = infantryTrainingCount;
    }

    public int getCavalryTrainingCount() {
        return cavalryTrainingCount;
    }

    public void setCavalryTrainingCount(int cavalryTrainingCount) {
        this.cavalryTrainingCount = cavalryTrainingCount;
    }

    public int getCatapultTrainingCount() {
        return catapultTrainingCount;
    }

    public void setCatapultTrainingCount(int catapultTrainingCount) {
        this.catapultTrainingCount = catapultTrainingCount;
    }

    @Override
    public String getSymbol() {
        return UniversityConstants.SYMBOL;
    }

    private void trainUnit(UnitType unitType) throws AgeOfEmpiresException {
        // Check default conditions required to make a move
        MoveControlUtils.checkMoveConditions(getOwnerPlayer());

        var player = getOwnerPlayer();
        var cost = UniversityConstants.TRAINING_COST;
        if (ResourcesUtils.hasEnoughResources(player, cost)) {
            ResourcesUtils.deductResources(player, cost);
            switch (unitType) {
                case INFANTRY -> infantryTrainingCount++;
                case CAVALRY -> cavalryTrainingCount++;
                case CATAPULT -> catapultTrainingCount++;
                default -> throw new AgeOfEmpiresException("Invalid unit type: " + unitType);
            }
        } else {
            throw new AgeOfEmpiresException(getOwnerPlayer() + " doesn't have enough resources to train " + unitType + " at the University");
        }

        // Increase player turn counter after successful move
        getOwnerPlayer().getCurrentGame().increasePlayerTurnCounter();

    }

    @Override
    public void trainInfantry() throws AgeOfEmpiresException {
        trainUnit(UnitType.INFANTRY);
    }

    @Override
    public void trainCavalry() throws AgeOfEmpiresException {
        trainUnit(UnitType.CAVALRY);
    }

    @Override
    public void trainCatapult() throws AgeOfEmpiresException {
        trainUnit(UnitType.CATAPULT);
    }

    @Override
    public boolean checkIfAlive() {
        boolean isAlive = super.checkIfAlive();
        if (!isAlive) {
            var ownerPlayer = getOwnerPlayer();
            try {
                ownerPlayer.setUniversity(null);
            } catch (AgeOfEmpiresException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < ownerPlayer.getSoldierCount(); i++) {
                ownerPlayer.getSoldier(i).checkIfAlive();
            }
        }
        
        return isAlive;
    }

    public enum UnitType {
        INFANTRY,
        CAVALRY,
        CATAPULT
    }
}
