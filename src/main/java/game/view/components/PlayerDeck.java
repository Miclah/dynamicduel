package game.view.components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlayerDeck extends Rectangle {

    public PlayerDeck(double width, double height, double screenWidth, double screenHeight, Color outlineColor) {
        super(width, height, Color.TRANSPARENT);
        setX(screenWidth - width);
        setY(screenHeight - height);
        setStroke(outlineColor);
        setStrokeWidth(2);

        // Disable mouse events for the PlayerDeck
        setMouseTransparent(true);
    }
}
