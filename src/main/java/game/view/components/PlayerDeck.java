package game.view.components;

import game.controller.GameController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlayerDeck extends StackPane {

    private GameController gameController;
    private GridOutline gridOutline; 
    

    public PlayerDeck(double width, double height, double screenWidth, double screenHeight, Color outlineColor, GameController gameController) {
        Rectangle deckOutline = new Rectangle(width, height, Color.TRANSPARENT);
        deckOutline.setStroke(outlineColor);
        deckOutline.setStrokeWidth(2);

        setTranslateX(screenWidth - width / 2);
        setTranslateY(screenHeight - height / 2);

        deckOutline.setMouseTransparent(true);

        this.gameController = gameController;
        Button endTurnButton = createEndTurnButton();

        StackPane.setAlignment(endTurnButton, Pos.CENTER);
        endTurnButton.setTranslateX(675);

        getChildren().addAll(deckOutline, endTurnButton);

        // Initialize the grid outline
        gridOutline = new GridOutline(width, height, Color.RED);
        gridOutline.setVisible(false);
        getChildren().add(gridOutline);

    }

    private Button createEndTurnButton() {
        Button endTurnButton = new Button();
        endTurnButton.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        endTurnButton.setMinSize(70, 70);
        endTurnButton.setMaxSize(70, 70);
        endTurnButton.setText("End\nTurn");
        endTurnButton.setOnAction(e -> endTurnButtonClicked());
        return endTurnButton;
    }

    private void endTurnButtonClicked() {
        System.out.println("Player ended turn");
        gameController.startOpponentTurn();
    }

    
}
