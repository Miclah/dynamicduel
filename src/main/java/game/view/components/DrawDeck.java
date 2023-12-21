package game.view.components;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class DrawDeck extends StackPane {

    private StackedDeck stackedDeck;

    public DrawDeck(double width, double height, double screenWidth, double screenHeight, Color outlineColor) {
        Rectangle outline = new Rectangle(width, height, Color.TRANSPARENT);
        outline.setStroke(outlineColor);
        outline.setStrokeWidth(2);

        setTranslateX(screenWidth - width / 2);
        setTranslateY(screenHeight / 2); 

        this.stackedDeck = new StackedDeck(width, height);

        StackPane cardsPane = new StackPane(stackedDeck);
        cardsPane.setAlignment(Pos.CENTER);

        Text cardCountText = new Text("Cards Left: " + stackedDeck.getRemainingCardCount());
        cardCountText.setFill(Color.BLACK);
        HBox textHBox = new HBox(cardCountText);
        textHBox.setAlignment(Pos.BOTTOM_CENTER);
        textHBox.setTranslateY(-30);

        StackPane wholePane = new StackPane(outline, cardsPane, textHBox);
        wholePane.setAlignment(Pos.CENTER);

        getChildren().add(wholePane);

        setMouseTransparent(true);
    }
}
