package game.model;

import javafx.scene.image.Image;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import game.view.GameView;

/**
 * Loads cards with their associated images for the game.
 */
public class CardLoader {
    // The factor by which to resize the card images relative to the window height
    private static final double TARGET_HEIGHT_FACTOR = 0.25;

    /**
     * Loads a list of cards with their associated images.
     *
     * @return The list of loaded cards.
     */
    public static List<Card> loadCards() {
        List<Card> cards = new ArrayList<>();

        for (CardType cardType : CardType.values()) {
            try {
                // Generate image path based on card type
                String imageName = String.valueOf(cardType.getValue()).toLowerCase() + "_" + cardType.getElementType().name().toLowerCase() + ".png";
                String imagePath = "/Cards/card_textures/" + cardType.getElementType().name().toLowerCase() + "/" + imageName;

                // Resize the front image and load the back image
                Image frontImage = resizeImage(imagePath, TARGET_HEIGHT_FACTOR);
                Image backImage = new Image(CardLoader.class.getResourceAsStream("/Cards/card_textures/card_back.png"));

                // Create and add the card to the list
                Card card = new Card(cardType, frontImage, backImage);
                cards.add(card);

            } catch (NullPointerException e) {
                System.err.println("Error loading image for card: " + cardType.name());
                e.printStackTrace();
            }
        }

        return cards;
    }

    /**
     * Resizes an image to a specified target height.
     *
     * @param imagePath          The path of the image to resize.
     * @param targetHeightFactor The factor by which to resize the image.
     * @return The resized image.
     */
    public static Image resizeImage(String imagePath, double targetHeightFactor) {
        try {
            // Load the image input stream
            InputStream inputStream = CardLoader.class.getResourceAsStream(imagePath);

            if (inputStream != null) {
                // Resize the image based on the target height factor
                Image originalImage = new Image(inputStream);
                double targetHeight = GameView.getWindowHeight() * targetHeightFactor;
                double aspectRatio = originalImage.getWidth() / originalImage.getHeight();
                double targetWidth = targetHeight * aspectRatio;
                return new Image(inputStream, targetWidth, targetHeight, true, true);
            } else {
                System.err.println("Error loading image at path: " + imagePath);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error loading image at path: " + imagePath);
            e.printStackTrace();
            return null;
        }
    }
}
