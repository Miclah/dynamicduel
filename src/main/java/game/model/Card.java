package game.model;

import javafx.scene.image.Image;

public class Card {
    private final CardType cardType;
    private final Image frontImage;
    private final Image backImage;

    public Card(CardType cardType, Image frontImage, Image backImage) {
        this.cardType = cardType;
        this.frontImage = frontImage;
        this.backImage = backImage;
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
}
