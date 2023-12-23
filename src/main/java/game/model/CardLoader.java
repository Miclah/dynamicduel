package game.model;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class CardLoader {

    private static final double TARGET_HEIGHT_FACTOR = 0.25; // 1/4 of the window height
    
    // Assuming screenHeight is defined somewhere in your code
    private static final int screenHeight = 1080;

    public static List<Card> loadCards() {
        List<Card> cards = new ArrayList<>();

        for (CardType cardType : CardType.values()) {
            try {
                String imageName = String.valueOf(cardType.getValue()).toLowerCase() + "_" + cardType.getElementType().name().toLowerCase() + ".png";
                String imagePath = "/Cards/card_textures/" + cardType.getElementType().name().toLowerCase() + "/" + imageName;
                Image frontImage = resizeImage(imagePath, TARGET_HEIGHT_FACTOR);

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

    private static Image resizeImage(String imagePath, double targetHeightFactor) {
        try {
            Image originalImage = new Image(CardLoader.class.getResourceAsStream(imagePath));
            double targetHeight = screenHeight * targetHeightFactor;
            double aspectRatio = originalImage.getWidth() / originalImage.getHeight();
            double targetWidth = targetHeight * aspectRatio;
            return new Image(CardLoader.class.getResourceAsStream(imagePath), targetWidth, targetHeight, true, true);
        } catch (Exception e) {
            System.err.println("Error loading image at path: " + imagePath);
            e.printStackTrace();
            return null;
        }
    }
    
}
