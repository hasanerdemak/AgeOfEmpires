package utils;

import entities.Resources;
import game.Player;

public class ResourcesUtils {
    public static boolean hasEnoughResources(Player player, Resources cost) {
        return player.getWood() >= cost.wood &&
                player.getGold() >= cost.gold &&
                player.getStone() >= cost.stone;
    }

    public static void deductResources(Player player, Resources cost) {
        player.setWood(player.getWood() - cost.wood);
        player.setGold(player.getGold() - cost.gold);
        player.setStone(player.getStone() - cost.stone);
    }

    public static void increaseResources(Player player, Resources resources) {
        player.setWood(player.getWood() + resources.wood);
        player.setGold(player.getGold() + resources.gold);
        player.setStone(player.getStone() + resources.stone);
    }
}