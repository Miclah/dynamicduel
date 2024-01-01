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

/**
 * The player's deck in the game view, allowing the player to end their turn and "manage" cards.
 */
public class PlayerDeck extends StackPane {

    private GameController gameController;
    private GridOutline gridOutline;
    private boolean cardDrawn;
    private static int cardsDrawnThisTurn;
    private AI opponentAI;
    private PlayingField playingField;

    /**
     * Constructs the PlayerDeck object.
     *
     * @param width        The width of the player's deck.
     * @param height       The height of the player's deck.
     * @param screenWidth  The width of the screen.
     * @param screenHeight The height of the screen.
     * @param outlineColor The color of the outline.
     * @param gameController The game controller associated with the player's deck.
     * @param opponentAI The opponent AI associated with the player's deck.
     */
    public PlayerDeck(double width, double height, double screenWidth, double screenHeight, Color outlineColor, GameController gameController, AI opponentAI) {
        // Create an outline for the player's deck
        Rectangle deckOutline = new Rectangle(width, height, Color.TRANSPARENT);
        deckOutline.setStroke(outlineColor);
        deckOutline.setStrokeWidth(2);

        this.gameController = gameController;
        this.opponentAI = opponentAI;
        
        // Set cards drawn at the start of the game to 0
        cardsDrawnThisTurn = 0;

        setTranslateX(320);
        setTranslateY(760);

        // Create the "End Turn" button
        Button endTurnButton = this.createEndTurnButton();

        // Set button alignment and position
        StackPane.setAlignment(endTurnButton, Pos.CENTER);
        endTurnButton.setTranslateX(1475);
        endTurnButton.setTranslateY(160);

        // Create a pane for the outline to make it unresponsive to mouse events
        Pane outlinePane = new Pane(deckOutline);
        outlinePane.setMouseTransparent(true);

        // Create a grid outline for the player's cards
        this.gridOutline = new GridOutline(width, height, Color.RED);
        this.gridOutline.setVisible(false);

        // Set the player's deck to be responsive to mouse events but not pickable on bounds, do not remove!
        setMouseTransparent(false);
        setPickOnBounds(false);

        // Add components to the player's deck
        getChildren().addAll(endTurnButton, outlinePane, this.gridOutline);
    }

    /**
     * Creates the "End Turn" button.
     *
     * @return The created "End Turn" button.
     */
    private Button createEndTurnButton() {
        // TODO: Apply a style to the button
        Button endTurnButton = new Button();
        endTurnButton.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        endTurnButton.setMinSize(70, 70);
        endTurnButton.setMaxSize(70, 70);
        endTurnButton.setText("End\nTurn");
        endTurnButton.setOnAction(e -> this.endTurnButtonClicked());
        return endTurnButton;
    }

    /**
     * Handles the "End Turn" button click event.
     */
    private void endTurnButtonClicked() {
        // If the player has drawn the card
        if (this.cardDrawn) {
            this.gameController.startOpponentTurn(); // Starts opponent's turn
            this.cardDrawn = false;  // Resets the card drawn boolean
            this.opponentAI.reduceAIHealth(3); // Reduce the opponent's health by x, need to be replaced by playing field logic

            // Create a new list for cards in the playing field, TODO: Fix this
            List<Card> cardsInPlayingField = this.playingField.getWaitingCards();
            // If there are cards in the playing field, add them to the waiting list
            if (!cardsInPlayingField.isEmpty()) {
                this.gameController.addCardsToWaitingList(cardsInPlayingField);
            }
        // Else display a message that the player needs to draw a card before ending his turn
        } else {
            this.gameController.drawCardMessage();
        }
    }

    /**
     * Sets the playing field associated with the player's deck.
     *
     * @param playingField The playing field.
     */
    public void setPlayingField(PlayingField playingField) {
        this.playingField = playingField;
    }

    /**
     * Sets the boolean indicating whether a card has been drawn.
     *
     * @param cardDrawn True if a card has been drawn, false otherwise.
     */
    public void setCardDrawn(boolean cardDrawn) {
        this.cardDrawn = cardDrawn;
    }

    /**
     * Resets the count of cards drawn in the current turn.
     */
    public static void resetCardDrawn() {
        cardsDrawnThisTurn = 0;
    }

    /**
     * Increments the count of cards drawn in the current turn.
     */
    public static void incCardsDrawnThisTurn() {
        cardsDrawnThisTurn++;
    }

    /**
     * Checks if a card has been drawn in the current turn.
     *
     * @return True if a card has been drawn, false otherwise.
     */
    public boolean hasDrawnCardThisTurn() {
        return cardsDrawnThisTurn > 0;
    }
}
