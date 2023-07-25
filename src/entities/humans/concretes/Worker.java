package entities.humans.concretes;

import entities.buildings.abstracts.Building;
import entities.buildings.concretes.MainBuilding;
import entities.buildings.concretes.Tower;
import entities.buildings.concretes.University;
import entities.humans.abstracts.Human;
import interfaces.WorkerInterface;
import utils.MoveControlUtils;
import game.Player;
import entities.Resources;
import utils.ResourcesUtils;
import exceptions.AgeOfEmpiresException;

public class Worker extends Human implements WorkerInterface {

    public Worker() {
        super(2, new Resources(0, 1, 0),
                3, 1, 1, (float) Math.sqrt(2));
    }

    public Worker(Player ownerPlayer, int x, int y) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, 2, new Resources(0, 1, 0),
                3, 1, 1, (float) Math.sqrt(2));
    }

    public Worker(Player ownerPlayer, int x, int y, int lifePoints) throws AgeOfEmpiresException {
        super(ownerPlayer, x, y, lifePoints, new Resources(0, 1, 0),
                3, 1, 1, (float) Math.sqrt(2));
    }


    @Override
    public String getSymbol() {
        return "W";
    }

    @Override
    public void build(Building b) throws AgeOfEmpiresException {

        // Check default conditions required to make a move
        MoveControlUtils.checkMoveConditions(getOwnerPlayer());

        if (b == null) {
            throw new AgeOfEmpiresException(this + " -> build function's parameter cannot be null");
        }
        if (b instanceof MainBuilding) {
            throw new AgeOfEmpiresException(this + " can only build University or Tower");
        }
        if (getOwnerPlayer().getCurrentGame().getMap().getItemAtCoordinates(getX(), getY()) instanceof Building) {
            throw new AgeOfEmpiresException(this + " -> coordinates the worker is on are not empty. There is another building.");
        }

        var itemCost = b.getCost();
        var ownerPlayer = getOwnerPlayer();
        if (ResourcesUtils.hasEnoughResources(ownerPlayer, itemCost)) {
            b.setOwnerPlayer(ownerPlayer);
            if (b instanceof Tower) {
                ownerPlayer.addTower((Tower) b, true);
            } else if (b instanceof University) {
                ownerPlayer.setUniversity((University) b);
                b.setItemID(0);
            } else {
                throw new AgeOfEmpiresException(this + " can only build university and tower. Building sent: " + b);
            }

            ResourcesUtils.deductResources(ownerPlayer, itemCost);

            b.setX(getX());
            b.setY(getY());
        } else {
            throw new AgeOfEmpiresException(this + " cannot build the " + b);
        }

        // Increase player turn counter after successful move
        getOwnerPlayer().getCurrentGame().increasePlayerTurnCounter();

    }

    @Override
    public boolean checkIfAlive() {
        if (super.checkIfAlive()) {
            return true;
        }

        getOwnerPlayer().removeWorker(this);
        return false;
    }
}
