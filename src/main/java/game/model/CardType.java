package game.model;

/**
 * Types of cards in the game along with their associated values.
 */
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

    /**
     * Constructs a card type with the specified element type and value.
     *
     * @param elementType The element type of the card.
     * @param value       The numerical value associated with the card.
     */
    CardType(ElementType elementType, int value) {
        this.elementType = elementType;
        this.value = value;
    }

    /**
     * Gets the element type of the card.
     *
     * @return The element type.
     */
    public ElementType getElementType() {
        return this.elementType;
    }

    /**
     * Gets the numerical value associated with the card.
     *
     * @return The card's value.
     */
    public int getValue() {
        return this.value;
    }
}