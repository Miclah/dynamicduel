package game.view.components;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class PlayerSpecial extends StackPane {

    private static final String SPECIAL_CARD_PATH = "/Cards/card_textures/special/special_%d.png";
    private static final int NUM_SPECIAL_CARDS = 8;

    private Rectangle outline;
    private ImageView specialImageView;

    public PlayerSpecial(double height, double screenWidth, double screenHeight, Color outlineColor) {
        outline = new Rectangle(screenWidth - 1600, height, Color.TRANSPARENT);
        outline.setStroke(outlineColor);
        outline.setStrokeWidth(2);

        setTranslateX(height / 2);
        setTranslateY(screenHeight - height / 2);

        loadSpecialCard();

        StackPane wholePane = new StackPane(outline, specialImageView);
        wholePane.setAlignment(Pos.CENTER);

        getChildren().add(wholePane);
    }

    private void loadSpecialCard() {
        Random random = new Random();
        int randomCardNumber = random.nextInt(NUM_SPECIAL_CARDS) + 1; // Random number between 1 and NUM_SPECIAL_CARDS
        String imagePath = String.format(SPECIAL_CARD_PATH, randomCardNumber);
        Image specialCardImage = new Image(getClass().getResourceAsStream(imagePath));

        specialImageView = new ImageView(specialCardImage);
        specialImageView.setPreserveRatio(true);
        double cardBackWidth = specialCardImage.getWidth();
        double cardBackHeight = specialCardImage.getHeight();
        double aspectRatio = cardBackWidth / cardBackHeight;
        double cardWidth = 1080 / 4.0 * aspectRatio;

        specialImageView.setFitWidth(cardWidth);
                
    }
}
