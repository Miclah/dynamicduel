package game.view.components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Opponent's (AI) deck in the game.
 */
public class OpponentDeck extends Rectangle {

    /**
     * Constructs an opponent's deck with a specified width, height, screen width, screen height, and outline color.
     *
     * @param width        The width of the deck.
     * @param height       The height of the deck.
     * @param screenWidth  The width of the screen.
     * @param screenHeight The height of the screen.
     * @param outlineColor The color of the outline.
     */
    public OpponentDeck(double width, double height, double screenWidth, double screenHeight, Color outlineColor) {
        super(width, height, Color.TRANSPARENT);
        setX(screenWidth - width); // Position the deck on the right side of the screen
        setY(0);
        setStroke(outlineColor);
        setStrokeWidth(2);

        // Disable mouse events for the OpponentDeck
        setMouseTransparent(true);
    }
}
