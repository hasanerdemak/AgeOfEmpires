package game;

import entities.Item;
import entities.buildings.abstracts.Building;
import entities.humans.abstracts.Human;
import interfaces.MapInterface;

import java.util.ArrayList;


public class Map implements MapInterface {
    private ArrayList<Item> mapItems;

    public Map() {
        mapItems = new ArrayList<>();
    }

    public ArrayList<Item> getMapItems() {
        return mapItems;
    }

    public void addMapItem(Item newItem) {
        if (!mapItems.contains(newItem)) {
            mapItems.add(newItem);
        }
    }

    @Override
    public String status() {
        char[][] map = new char[50][100];

        // Initialize the map with empty spaces
        char emptyMapSlotChar = '_';
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 100; j++) {
                map[i][j] = emptyMapSlotChar;
            }
        }

        for (Item item : mapItems) {
            if (item instanceof Human && map[item.getY() - 1][item.getX() - 1] != emptyMapSlotChar || item == null) {
                continue;
            }
            map[item.getY() - 1][item.getX() - 1] = item.getSymbol().charAt(0);
        }

        // Generate the map string
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 100; j++) {
                sb.append(map[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Item getItemAtCoordinates(int x, int y) {
        Item item = null;
        for (Item mapItem : mapItems) {
            if (mapItem.getX() == x && mapItem.getY() == y) {
                item = mapItem;
                var playerItems = mapItem.getOwnerPlayer().getPlayerItems();
                for (Item playerItem : playerItems) {
                    if (playerItem.getX() == x && playerItem.getY() == y && playerItem instanceof Building) {
                        item = playerItem;
                    }
                }
                break;
            }
        }
        return item;
    }

    public ArrayList<Item> getAllItemsAtCoordinates(int x, int y) {
        ArrayList<Item> itemArrayList = new ArrayList<>();
        for (Item mapItem : mapItems) {
            if (mapItem.getX() == x && mapItem.getY() == y) {
                var playerItems = mapItem.getOwnerPlayer().getPlayerItems();
                for (Item playerItem : playerItems) {
                    if (playerItem.getX() == x && playerItem.getY() == y) {
                        itemArrayList.add(playerItem);
                    }
                }
                break;
            }
        }
        return itemArrayList;
    }
}
