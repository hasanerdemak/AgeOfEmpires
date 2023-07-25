package utils;

import entities.Item;

public class DistanceUtils {
    public static float getEuclideanDistanceBetweenItems(Item item1, Item item2) {
        double squareDistance = (Math.pow(item2.getX() - item1.getX(), 2) + Math.pow(item2.getY() - item1.getY(), 2));
        return (float) Math.sqrt(squareDistance);
    }

    public static float getEuclideanDistanceBetweenCoordinates(int x1, int y1, int x2, int y2) {
        double squareDistance = (Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        return (float) Math.sqrt(squareDistance);
    }

    public static float getManhattanDistanceBetweenItems(Item item1, Item item2) {
        return Math.abs(item2.getX() - item1.getX()) + Math.abs(item2.getY() - item1.getY());
    }

    public static int getManhattanDistanceBetweenCoordinates(int x1, int y1, int x2, int y2) {
        return Math.abs(x2 - x1) + Math.abs(y2 - y1);
    }
}
