package game.model;

public enum CardType {
    FIRE_50_DAMAGE("Fire", 50),
    FIRE_40_DAMAGE("Fire", 40),
    FIRE_30_DAMAGE("Fire", 30),
    FIRE_20_DAMAGE("Fire", 20),
    FIRE_10_DAMAGE("Fire", 10),

    WATER_15_HEAL("Water", 15),
    WATER_25_HEAL("Water", 25),

    EARTH_15_BLOCK("Earth", 15),
    EARTH_20_BLOCK("Earth", 20),
    EARTH_25_BLOCK("Earth", 25);


    private final String category;
    private final int value;

    CardType(String category, int value) {
        this.category = category;
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public int getValue() {
        return value;
    }
}
