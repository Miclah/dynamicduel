package game.view.components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlayerSpecial extends Rectangle {

    public PlayerSpecial(double height, double screenWidth, double screenHeight, Color outlineColor) {
        super(screenWidth - 1600, height, Color.TRANSPARENT);
        setX(0);
        setY(screenHeight - height);
        setStroke(outlineColor);
        setStrokeWidth(2);

        // Disable mouse events for the SpecialPlayerCard
        setMouseTransparent(true);
    }
}
