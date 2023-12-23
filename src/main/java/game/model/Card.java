package game.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;

public class Card {
    private final CardType cardType;
    private final Image frontImage;
    private final Image backImage;
    private final BooleanProperty flipped;

    public Card(CardType cardType, Image frontImage, Image backImage) {
        this.cardType = cardType;
        this.frontImage = frontImage;
        this.backImage = backImage;
        this.flipped = new SimpleBooleanProperty(false);
    }

    public CardType getCardType() {
        return cardType;
    }

    public Image getFrontImage() {
        return frontImage;
    }

    public Image getBackImage() {
        return backImage;
    }

    public boolean isFlipped() {
        return flipped.get();
    }

    public void setFlipped(boolean flipped) {
        this.flipped.set(flipped);
    }

    public BooleanProperty flippedProperty() {
        return flipped;
    }
}
