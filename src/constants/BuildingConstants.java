package constants;

import entities.Resources;

public class BuildingConstants {
    public static class MainBuildingConstants {
        public static final String SYMBOL = "M";
        public static final int LIFE_POINTS = 100;
        public static final Resources COST = null;
    }

    public static class UniversityConstants {
        public static final String SYMBOL = "U";
        public static final int LIFE_POINTS = 30;
        public static final Resources COST = new Resources(30, 50, 5);
        public static final Resources TRAINING_COST = new Resources(0, 50, 0);
    }

    public static class TowerConstants {
        public static final String SYMBOL = "T";
        public static final int LIFE_POINTS = 50;
        public static final int ATTACK_POWER = 2;
        public static final float LOWER_ATTACK_DISTANCE_LIMIT = 1;
        public static final float UPPER_ATTACK_DISTANCE_LIMIT = 7;
        public static final Resources COST = new Resources(10, 5, 40);
    }
}
