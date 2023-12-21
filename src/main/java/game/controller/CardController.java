package game.controller;

import game.model.Card;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class CardController {
    private double xOffset, yOffset;

    public ImageView createCardImageView(Card card, double screenHeight) {
        ImageView cardImageView = new ImageView(card.getFrontImage());
        cardImageView.setPreserveRatio(true);

        double originalWidth = card.getFrontImage().getWidth();
        double originalHeight = card.getFrontImage().getHeight();
        double aspectRatio = originalWidth / originalHeight;
        double initialWidth = screenHeight / 4.0 * aspectRatio;
        cardImageView.setFitWidth(initialWidth);

        cardImageView.setUserData(card);

        cardImageView.setOnMousePressed(this::handleMousePressed);
        cardImageView.setOnMouseDragged(this::handleMouseDragged);
        cardImageView.setOnMouseReleased(this::handleMouseReleased);

        return cardImageView;
    }

    private void handleMousePressed(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            ImageView cardImageView = (ImageView) event.getSource();
            xOffset = event.getSceneX() - cardImageView.getTranslateX();
            yOffset = event.getSceneY() - cardImageView.getTranslateY();

            cardImageView.setEffect(new DropShadow());

        } else if (event.getButton() == MouseButton.SECONDARY) {
            System.out.println("Right mouse button clicked");
        }
    }

    private void handleMouseDragged(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            ImageView cardImageView = (ImageView) event.getSource();
            double deltaX = event.getSceneX() - xOffset;
            double deltaY = event.getSceneY() - yOffset;

            cardImageView.setTranslateX(deltaX);
            cardImageView.setTranslateY(deltaY);
        }
    }

    private void handleMouseReleased(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            ((ImageView) event.getSource()).setEffect(null);
        }
    }
}
