package game.controller;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class CardController {
    private double xOffset, yOffset;

    public void setupCardInteraction(ImageView cardImageView) {
        cardImageView.setPreserveRatio(true);

        cardImageView.setOnMousePressed(this::handleMousePressed);
        cardImageView.setOnMouseDragged(this::handleMouseDragged);
        cardImageView.setOnMouseReleased(this::handleMouseReleased);
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
