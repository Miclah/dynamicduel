package game.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;

/**
 * Card in the game.
 */
public class Card {
    private final CardType cardType;
    private final Image frontImage;
    private final Image backImage;
    private final BooleanProperty flipped;

    /**
     * Constructs a card with the specified type and images.
     *
     * @param cardType   The type of the card.
     * @param frontImage The image displayed when the card is facing up.
     * @param backImage  The image displayed when the card is facing down.
     */
    public Card(CardType cardType, Image frontImage, Image backImage) {
        this.cardType = cardType;
        this.frontImage = frontImage;
        this.backImage = backImage;
        this.flipped = new SimpleBooleanProperty(false);
    }

    /**
     * Gets the type of the card.
     *
     * @return The card type.
     */
    public CardType getCardType() {
        return this.cardType;
    }

    /**
     * Gets the image displayed when the card is facing up.
     *
     * @return The front image.
     */
    public Image getFrontImage() {
        return this.frontImage;
    }

    /**
     * Gets the image displayed when the card is facing down.
     *
     * @return The back image.
     */
    public Image getBackImage() {
        return this.backImage;
    }

    /**
     * Checks if the card is flipped.
     *
     * @return True if the card is flipped, false otherwise.
     */
    public boolean isFlipped() {
        return this.flipped.get();
    }

    /**
     * Sets the flipped status of the card.
     *
     * @param flipped True if the card is flipped, false otherwise.
     */
    public void setFlipped(boolean flipped) {
        this.flipped.set(flipped);
    }

    /**
     * Gets the BooleanProperty representing the flipped status of the card.
     *
     * @return The BooleanProperty for the flipped status.
     */
    public BooleanProperty flippedProperty() {
        return this.flipped;
    }
}