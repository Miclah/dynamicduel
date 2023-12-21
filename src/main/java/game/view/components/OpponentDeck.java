package game.view.components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class OpponentDeck extends Rectangle {

    public OpponentDeck(double width, double height, double screenWidth, double screenHeight, Color outlineColor) {
        super(width, height, Color.TRANSPARENT);
        setX(screenWidth - width);
        setY(0);
        setStroke(outlineColor);
        setStrokeWidth(2);

        // Disable mouse events for the OpponentDeck
        setMouseTransparent(true);
    }
}
