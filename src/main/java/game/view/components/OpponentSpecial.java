package game.view.components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class OpponentSpecial extends Rectangle {

    public OpponentSpecial(double height, double screenWidth, double screenHeight, Color outlineColor) {
        super(screenWidth - 1600, height, Color.TRANSPARENT);
        setX(0);
        setY(0);
        setStroke(outlineColor);
        setStrokeWidth(2);

        // Disable mouse events for the OpponentSpecial
        setMouseTransparent(true);
    }
}
