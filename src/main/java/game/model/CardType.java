package game.model;

public enum CardType {
    FIRE_50(ElementType.FIRE, 50),
    FIRE_40(ElementType.FIRE, 40),
    FIRE_30(ElementType.FIRE, 30),
    FIRE_20(ElementType.FIRE, 20),
    FIRE_10(ElementType.FIRE, 10),

    WATER_15(ElementType.WATER, 15),
    WATER_25(ElementType.WATER, 25),

    EARTH_15(ElementType.EARTH, 15),
    EARTH_20(ElementType.EARTH, 20),
    EARTH_25(ElementType.EARTH, 25);

    private final ElementType elementType;
    private final int value;

    CardType(ElementType elementType, int value) {
        this.elementType = elementType;
        this.value = value;
    }

    public ElementType getElementType() {
        return this.elementType;
    }

    public int getValue() {
        return this.value;
    }
}
