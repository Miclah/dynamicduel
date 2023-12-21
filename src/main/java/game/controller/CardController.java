package game.controller;

import game.model.Card;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CardController {
    private double xOffset, yOffset;

    public ImageView createCardImageView(Card card, double screenHeight) {
        ImageView cardImageView = new ImageView(card.getFrontImage());
        cardImageView.setPreserveRatio(true);

        // Calculate the original aspect ratio of the card image
        double originalWidth = card.getFrontImage().getWidth();
        double originalHeight = card.getFrontImage().getHeight();
        double aspectRatio = originalWidth / originalHeight;

        // Calculate the initial width based on the original aspect ratio and screen height
        double initialWidth = screenHeight / 4.0 * aspectRatio;
        cardImageView.setFitWidth(initialWidth);

        cardImageView.setUserData(card);

        // Set up event handlers for drag-and-drop
        cardImageView.setOnMousePressed(this::handleMousePressed);
        cardImageView.setOnMouseDragged(this::handleMouseDragged);

        return cardImageView;
    }

    private void handleMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    private void handleMouseDragged(MouseEvent event) {
        ImageView cardImageView = (ImageView) event.getSource();
        double deltaX = event.getSceneX() - xOffset;
        double deltaY = event.getSceneY() - yOffset;

        cardImageView.setTranslateX(cardImageView.getTranslateX() + deltaX);
        cardImageView.setTranslateY(cardImageView.getTranslateY() + deltaY);

        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }
}
