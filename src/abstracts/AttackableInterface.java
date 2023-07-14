package abstracts;

import concretes.Game;
import concretes.MoveControlUtils;
import entities.Item;
import entities.Spearman;
import exceptions.AgeOfEmpiresException;

public interface AttackableInterface extends ItemInterface {
    default void attack(int x, int y) throws AgeOfEmpiresException {
        // Check default conditions required to make a move
        MoveControlUtils.checkMoveConditions(getOwnerPlayer());
        MoveControlUtils.checkCoordinates(this, x, y, "attack");
        MoveControlUtils.checkAttackDistance(this, x, y);

        // Check if there is an item
        Item item = MoveControlUtils.getTargetItemAtCoordinates(this, x, y);
        // Check if the item is his own item
        if (MoveControlUtils.isEnemyItem(this, item)) {
            handleAttack(item);
        } else {
            throw new AgeOfEmpiresException(this + " cannot attack his own item: " + item + " at coordinates x: " + x + ", y: " + y);
        }

        // Increase player turn counter after successful move
        getOwnerPlayer().getCurrentGame().increasePlayerTurnCounter();
    }

    default void defend(int x, int y) {
        try {
            if (Game.checkPlayerTurn(getOwnerPlayer())) {
                throw new AgeOfEmpiresException(getOwnerPlayer() + " cannot defend himself without being attacked.");
            }
            // Check distance
            MoveControlUtils.checkAttackDistance(this, x, y);
            // Check if there is an item
            Item item = MoveControlUtils.getTargetItemAtCoordinates(this, x, y);
            // Check if the item is his own item
            if (MoveControlUtils.isEnemyItem(this, item)) {
                item.damage(getAttackPower());
            }
        } catch (AgeOfEmpiresException e) {
            //System.out.println(e.getMessage());
        }
    }

    default void handleAttack(Item targetItem) {
        // Check if the attacked item can attack
        if (targetItem instanceof AttackableInterface) {
            // if priority is on the attacker item
            if (this instanceof Spearman && !(targetItem instanceof Spearman)) {
                targetItem.damage(getAttackPower());
                if (targetItem.checkIfAlive()) {
                    ((AttackableInterface) targetItem).defend(getX_WithoutPrinting(), getY_WithoutPrinting());
                    this.checkIfAlive();
                }
            }
            // else if priority is on the defender item
            else if (!(this instanceof Spearman) && targetItem instanceof Spearman) {
                ((Spearman) targetItem).defend(getX_WithoutPrinting(), getY_WithoutPrinting());
                if (this.checkIfAlive()) {
                    targetItem.damage(getAttackPower());
                    targetItem.checkIfAlive();
                }
            } else {
                targetItem.damage(getAttackPower());
                ((AttackableInterface) targetItem).defend(getX_WithoutPrinting(), getY_WithoutPrinting());
                targetItem.checkIfAlive();
                this.checkIfAlive();
            }
        } else {
            targetItem.damage(getAttackPower());
            targetItem.checkIfAlive();
        }
    }

    float getLowerAttackDistanceLimit();

    float getUpperAttackDistanceLimit();

    int getAttackPower();

}