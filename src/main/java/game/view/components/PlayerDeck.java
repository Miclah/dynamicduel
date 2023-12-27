package game.view.components;

import game.controller.GameController;
import game.model.AI;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlayerDeck extends StackPane {

    private GameController gameController;
    private GridOutline gridOutline;
    private boolean cardDrawn;
    private static int cardsDrawnThisTurn;
    private AI opponentAI;

    public PlayerDeck(double width, double height, double screenWidth, double screenHeight, Color outlineColor, GameController gameController, AI opponentAI) {
        Rectangle deckOutline = new Rectangle(width, height, Color.TRANSPARENT);
        deckOutline.setStroke(outlineColor);
        deckOutline.setStrokeWidth(2);

        this.gameController = gameController;
        this.opponentAI = opponentAI; 
        cardsDrawnThisTurn = 0;

        setTranslateX(320);
        setTranslateY(760);

        Button endTurnButton = createEndTurnButton();

        StackPane.setAlignment(endTurnButton, Pos.CENTER);
        endTurnButton.setTranslateX(1475);
        endTurnButton.setTranslateY(160);
        
        Pane outlinePane = new Pane(deckOutline);
        outlinePane.setMouseTransparent(true);
   
        gridOutline = new GridOutline(width, height, Color.RED);
        gridOutline.setVisible(false);
        setMouseTransparent(false);
        setPickOnBounds(false); 
        getChildren().addAll(endTurnButton, outlinePane, gridOutline);
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
        if (cardDrawn) {
            System.out.println("Player ended turn");
            gameController.startOpponentTurn();
            this.cardDrawn = false;
            opponentAI.reduceAIHealth(60); 
        } else {
            gameController.displayDrawCardMessage();
        }
    }

    public void setCardDrawn(boolean cardDrawn) {
        this.cardDrawn = cardDrawn;
    }    

    public static void resetCardDrawn() {
        cardsDrawnThisTurn = 0;
    }

    public static void incrementCardsDrawnThisTurn() {
        cardsDrawnThisTurn++;
    }
    public boolean hasDrawnCardThisTurn() {
        return cardsDrawnThisTurn > 0;
    }
    
}
