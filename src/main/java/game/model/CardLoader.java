package game.model;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class CardLoader {

    public static List<Card> loadCards() {
        List<Card> cards = new ArrayList<>();

        for (CardType cardType : CardType.values()) {
            try {
                String imageName = String.valueOf(cardType.getValue()).toLowerCase() + "_" + cardType.getElementType().name().toLowerCase() + ".png";
                String imagePath = "/Cards/card_textures/" + cardType.getElementType().name().toLowerCase() + "/" + imageName;
                Image frontImage = new Image(CardLoader.class.getResourceAsStream(imagePath));
                Image backImage = new Image(CardLoader.class.getResourceAsStream("/Cards/card_textures/card_back.png"));


                Card card = new Card(cardType, frontImage, backImage);
                cards.add(card);
            } catch (NullPointerException e) {
                System.err.println("Error loading image for card: " + cardType.name());
                e.printStackTrace();
            }
        }

        return cards;
    }
}
