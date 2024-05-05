package constants;

import entities.Resources;

public class HumanConstants {
    public static class WorkerConstants {
        public static final String SYMBOL = "W";
        public static final int LIFE_POINTS = 2;
        public static final int MOVEMENT_SPEED = 3;
        public static final int ATTACK_POWER = 1;
        public static final float LOWER_ATTACK_DISTANCE_LIMIT = 1;
        public static final float UPPER_ATTACK_DISTANCE_LIMIT = (float) Math.sqrt(2);
        public static final Resources COST = new Resources(0, 1, 0);
    }

    public static class SwordmanConstants {
        public static final String SYMBOL = "K";
        public static final int LIFE_POINTS = 5;
        public static final int MOVEMENT_SPEED = 2;
        public static final int ATTACK_POWER = 3;
        public static final float LOWER_ATTACK_DISTANCE_LIMIT = 1;
        public static final float UPPER_ATTACK_DISTANCE_LIMIT = (float) Math.sqrt(2);
        public static final Resources COST = new Resources(2, 2, 0);
    }

    public static class SpearmanConstants {
        public static final String SYMBOL = "S";
        public static final int LIFE_POINTS = 5;
        public static final int MOVEMENT_SPEED = 2;
        public static final int ATTACK_POWER = 2;
        public static final int ATTACK_POWER_TO_CAVALRY = 10;
        public static final float LOWER_ATTACK_DISTANCE_LIMIT = 1;
        public static final float UPPER_ATTACK_DISTANCE_LIMIT = (float) Math.sqrt(2);
        public static final Resources COST = new Resources(2, 3, 0);
    }

    public static class ArcherConstants {
        public static final String SYMBOL = "O";
        public static final int LIFE_POINTS = 5;
        public static final int MOVEMENT_SPEED = 2;
        public static final int BOW_ATTACK_POWER_TO_HUMAN = 2;
        public static final int BOW_ATTACK_POWER_TO_BUILDING = 1;
        public static final float BOW_LOWER_ATTACK_DISTANCE_LIMIT = 2;
        public static final float BOW_UPPER_ATTACK_DISTANCE_LIMIT = 5;
        public static final int SWORD_ATTACK_POWER = 2;
        public static final float SWORD_LOWER_ATTACK_DISTANCE_LIMIT = 1;
        public static final float SWORD_UPPER_ATTACK_DISTANCE_LIMIT = (float) Math.sqrt(2);
        public static final Resources COST = new Resources(5, 2, 0);
    }

    public static class CavalryConstants {
        public static final String SYMBOL = "A";
        public static final int LIFE_POINTS = 20;
        public static final int MOVEMENT_SPEED = 9;
        public static final int ATTACK_POWER = 10;
        public static final int ATTACK_POWER_TO_CAVALRY_AND_BUILDING = 5;
        public static final float LOWER_ATTACK_DISTANCE_LIMIT = 1;
        public static final float UPPER_ATTACK_DISTANCE_LIMIT = (float) Math.sqrt(2);
        public static final Resources COST = new Resources(3, 10, 0);
    }

    public static class CatapultConstants {
        public static final String SYMBOL = "C";
        public static final int LIFE_POINTS = 10;
        public static final int MOVEMENT_SPEED = 1;
        public static final int ATTACK_POWER = 30;
        public static final int ATTACK_POWER_TO_HUMAN = Integer.MAX_VALUE;
        public static final float LOWER_ATTACK_DISTANCE_LIMIT = 6;
        public static final float UPPER_ATTACK_DISTANCE_LIMIT = 10;
        public static final Resources COST = new Resources(30, 20, 5);
    }
}