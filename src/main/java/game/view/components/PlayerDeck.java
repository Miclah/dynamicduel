package game.view.components;

import java.util.List;

import game.controller.GameController;
import game.model.AI;
import game.model.Card;
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
    private PlayingField playingField;

    public PlayerDeck(double width, double height, double screenWidth, double screenHeight, Color outlineColor, GameController gameController, AI opponentAI) {
        Rectangle deckOutline = new Rectangle(width, height, Color.TRANSPARENT);
        deckOutline.setStroke(outlineColor);
        deckOutline.setStrokeWidth(2);

        this.gameController = gameController;
        this.opponentAI = opponentAI; 
        cardsDrawnThisTurn = 0;

        setTranslateX(320);
        setTranslateY(760);

        Button endTurnButton = this.createEndTurnButton();

        StackPane.setAlignment(endTurnButton, Pos.CENTER);
        endTurnButton.setTranslateX(1475);
        endTurnButton.setTranslateY(160);
        
        Pane outlinePane = new Pane(deckOutline);
        outlinePane.setMouseTransparent(true);
   
        this.gridOutline = new GridOutline(width, height, Color.RED);
        this.gridOutline.setVisible(false);
        setMouseTransparent(false);
        setPickOnBounds(false); 
        getChildren().addAll(endTurnButton, outlinePane, this.gridOutline);
    }

    private Button createEndTurnButton() {
        Button endTurnButton = new Button();
        endTurnButton.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        endTurnButton.setMinSize(70, 70);
        endTurnButton.setMaxSize(70, 70);
        endTurnButton.setText("End\nTurn");
        endTurnButton.setOnAction(e -> this.endTurnButtonClicked());
        return endTurnButton;
    }

    private void endTurnButtonClicked() {
        if (this.cardDrawn) {
            System.out.println("Player ended turn");
            this.gameController.startOpponentTurn();
            this.cardDrawn = false;
            this.opponentAI.reduceAIHealth(60); 

            List<Card> cardsInPlayingField = this.playingField.getWaitingCards();

            if (!cardsInPlayingField.isEmpty()) {
                this.gameController.addCardsToWaitingList(cardsInPlayingField);

            }
        } else {
            this.gameController.displayDrawCardMessage();
        }
    }

    public void setPlayingField(PlayingField playingField) {
        this.playingField = playingField;
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
