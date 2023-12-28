package game.controller;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Controls the interactions with game cards, such as dragging and clicking.
 */
public class CardController {
    private double xOffset;
    private double yOffset;

    /**
     * Sets up interactions for a card image view.
     *
     * @param cardImageView The image view of the card.
     */
    public void setupCardInteraction(ImageView cardImageView) {
        cardImageView.setPreserveRatio(true);

        cardImageView.setOnMousePressed(this::handleMousePressed);
        cardImageView.setOnMouseDragged(this::handleMouseDragged);
        cardImageView.setOnMouseReleased(this::handleMouseReleased);
    }

    /**
     * Handles the event when the mouse is pressed on a card.
     *
     * @param event The mouse event.
     */
    private void handleMousePressed(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            ImageView cardImageView = (ImageView) event.getSource();
            this.xOffset = event.getSceneX() - cardImageView.getTranslateX();
            this.yOffset = event.getSceneY() - cardImageView.getTranslateY();

            cardImageView.setEffect(new DropShadow()); // Creates a shadow effect when the card is clicked

        } else if (event.getButton() == MouseButton.SECONDARY) {
            System.out.println("Right mouse button clicked");
        }
    }

    /**
     * Handles the event when the mouse is dragged while holding down the primary (left) button.
     *
     * @param event The mouse event.
     */
    private void handleMouseDragged(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            ImageView cardImageView = (ImageView) event.getSource();
            double deltaX = event.getSceneX() - xOffset;
            double deltaY = event.getSceneY() - yOffset;

            cardImageView.setTranslateX(deltaX);
            cardImageView.setTranslateY(deltaY);
        }
    }

    /**
     * Handles the event when the mouse is released after dragging a card.
     *
     * @param event The mouse event.
     */
    private void handleMouseReleased(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            ((ImageView) event.getSource()).setEffect(null);
        }
    }
}
