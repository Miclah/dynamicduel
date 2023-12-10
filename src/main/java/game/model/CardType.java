package game.model;

public enum CardType {
    FIRE_50("fire", 50),
    FIRE_40("fire", 40),
    FIRE_30("fire", 30),
    FIRE_20("fire", 20),
    FIRE_10("fire", 10),

    WATER_15("water", 15),
    WATER_25("water", 25),

    EARTH_15("earth", 15),
    EARTH_20("earth", 20),
    EARTH_25("earth", 25);

    private final String category;
    private final int value;

    CardType(String category, int value) {
        this.category = category;
        this.value = value;
    }

    public String getCategory() {
        return this.category;
    }

    public int getValue() {
        return this.value;
    }
}
