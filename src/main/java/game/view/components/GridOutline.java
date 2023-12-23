package game.view.components;

import javafx.geometry.Pos;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GridOutline extends StackPane {

    public GridOutline(double width, double height, Color glowColor) {
        Rectangle outline = new Rectangle(width, height);
        outline.setStroke(glowColor);
        outline.setStrokeWidth(2);
        outline.setFill(Color.TRANSPARENT);

        setMouseTransparent(true);
        getChildren().add(outline);
        setAlignment(Pos.CENTER);
        setEffect(new Glow(0.8)); // Add a glow effect
    }
}
