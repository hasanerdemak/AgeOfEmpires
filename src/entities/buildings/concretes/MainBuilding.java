package entities.buildings.concretes;

import constants.BuildingConstants.MainBuildingConstants;
import entities.buildings.abstracts.Building;
import entities.humans.abstracts.Human;
import entities.humans.abstracts.Soldier;
import entities.humans.concretes.Worker;
import exceptions.AgeOfEmpiresException;
import game.Player;
import utils.ResourcesUtils;

public class MainBuilding extends Building {

    public MainBuilding() {
        super(MainBuildingConstants.LIFE_POINTS, MainBuildingConstants.COST);

        setItemID(0);
    }

    public MainBuilding(Player ownerPlayer, int x, int y) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, MainBuildingConstants.LIFE_POINTS, MainBuildingConstants.COST);

        setItemID(0);
    }

    public MainBuilding(Player ownerPlayer, int x, int y, int lifePoints) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, lifePoints, MainBuildingConstants.COST);
    }


    @Override
    public String getSymbol() {
        return MainBuildingConstants.SYMBOL;
    }

    public void produceUnit(Human human) throws AgeOfEmpiresException {

        var itemCost = human.getCost();
        var ownerPlayer = getOwnerPlayer();
        if (ResourcesUtils.hasEnoughResources(ownerPlayer, itemCost)) {
            ResourcesUtils.deductResources(ownerPlayer, itemCost);

            human.setOwnerPlayer(ownerPlayer);
            human.setX(getX());
            human.setY(getY());

            if (human instanceof Soldier) {
                ownerPlayer.addSoldier((Soldier) human, true);
            } else {
                ownerPlayer.addWorker((Worker) human, true);
            }
        } else {
            throw new AgeOfEmpiresException(ownerPlayer + " -> does not have enough resources to produce " + human);
        }

    }

    @Override
    public boolean checkIfAlive() {
        boolean isAlive = super.checkIfAlive();
        if (!isAlive) {
            var ownerPlayer = getOwnerPlayer();
            ownerPlayer.setMainBuilding(null);
            ownerPlayer.getCurrentGame().removePlayer(ownerPlayer);
            // System.out.println(ownerPlayer + " lost his Main Building and WAS DEFEATED");
        }
        return isAlive;
    }

}
