package concretes;

import abstracts.AttackableInterface;
import abstracts.ItemInterface;
import entities.Catapult;
import entities.Human;
import entities.Item;
import entities.MainBuilding;
import exceptions.AgeOfEmpiresException;

public class MoveControlUtils {

    public static void checkMoveConditions(Player player) throws AgeOfEmpiresException {
        // Check if the game is already over
        if (player.getCurrentGame().getGameOver()) {
            throw new AgeOfEmpiresException("The game is already over. " + player + " cannot make a move.");
        }
        // Check if it is the player's turn
        if (!Game.checkPlayerTurn(player)) {
            throw new AgeOfEmpiresException("This is not the " + player + "'s turn.");
        }
    }

    public static <T extends ItemInterface & AttackableInterface> void checkCoordinates(T callerItem, int x, int y, String moveName) throws AgeOfEmpiresException {
        if (x < 1 || x > 100 || y < 1 || y > 50) {
            throw new AgeOfEmpiresException(callerItem + " cannot " + moveName + " these coordinates. Indexes are out of range");
        }

        if (x == callerItem.getX_WithoutPrinting() && y == callerItem.getY_WithoutPrinting()) {
            throw new AgeOfEmpiresException(callerItem + " cannot " + moveName + " its own coordinates");
        }
    }

    public static <T extends ItemInterface & AttackableInterface> void checkAttackDistance(T callerItem, int x, int y) throws AgeOfEmpiresException {
        float distance;
        if (callerItem instanceof Catapult) {
            distance = DistanceUtils.getManhattanDistanceBetweenCoordinates(callerItem.getX_WithoutPrinting(), callerItem.getY_WithoutPrinting(), x, y);
        } else {
            distance = DistanceUtils.getEuclideanDistanceBetweenCoordinates(callerItem.getX_WithoutPrinting(), callerItem.getY_WithoutPrinting(), x, y);
        }

        if (distance < callerItem.getLowerAttackDistanceLimit() || distance > callerItem.getUpperAttackDistanceLimit()) {
            throw new AgeOfEmpiresException(callerItem + " cannot attack coordinates x: " + x + ", y: " + y + " (Not within the distance limits)");
        }
    }

    public static Item getTargetItemAtCoordinates(ItemInterface callerItem, int x, int y) throws AgeOfEmpiresException {
        Item targetItem = callerItem.getOwnerPlayer().getCurrentGame().getMap().getItemAtCoordinates(x, y);
        if (targetItem == null) {
            throw new AgeOfEmpiresException("There is no item at coordinates x: " + x + ", y: " + y);
        }
        return targetItem;
    }

    public static boolean isEnemyItem(ItemInterface callerItem, ItemInterface targetItem) {
        return !targetItem.getOwnerPlayer().equals(callerItem.getOwnerPlayer());
    }


    public static void checkMoveDistance(Human callerHuman, int x, int y) throws AgeOfEmpiresException {
        float distance = DistanceUtils.getManhattanDistanceBetweenCoordinates(callerHuman.getX_WithoutPrinting(), callerHuman.getY_WithoutPrinting(), x, y);
        if (distance > callerHuman.getMovementSpeed()) {
            throw new AgeOfEmpiresException(callerHuman + " cannot move to coordinates x: " + x + ", y: " + y + " (Long distance)");
        }
    }

    public static void checkEmptyCoordinates(Human callerHuman, int x, int y) throws AgeOfEmpiresException {
        Item itemOnMap = callerHuman.getOwnerPlayer().getCurrentGame().getMap().getItemAtCoordinates(x, y);
        if (itemOnMap != null && !(itemOnMap instanceof MainBuilding)) {
            throw new AgeOfEmpiresException(callerHuman + " cannot move to the coordinates x: " + x + ", y: " + y + " (Coordinates are not empty)");
        }
    }

}
