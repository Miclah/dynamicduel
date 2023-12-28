package game.view.components;

import javafx.geometry.Pos;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents an unfinished grid for the cards to be displayed on
 * TODO: Finish this
 */
public class GridOutline extends StackPane {

    /**
     * Constructs a grid outline with a specified width, height, and glow color.
     *
     * @param width     The width of the outline.
     * @param height    The height of the outline.
     * @param glowColor The color of the glow effect.
     */
    public GridOutline(double width, double height, Color glowColor) {
        Rectangle outline = new Rectangle(width, height);
        outline.setStroke(glowColor);
        outline.setStrokeWidth(2);
        outline.setFill(Color.TRANSPARENT);

        setMouseTransparent(true); // Make the outline not clickable
        getChildren().add(outline);
        setAlignment(Pos.CENTER);
        setEffect(new Glow(0.8)); // Apply a glow effect to the outline
    }
}
