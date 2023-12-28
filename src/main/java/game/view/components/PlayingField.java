package game.view.components;

import java.util.ArrayList;
import java.util.List;

import game.controller.GameController;
import game.model.Card;
import game.model.ElementType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The playing field where cards are played during the game (not yet implemented).
 */
public class PlayingField extends StackPane {

    private GameController gameController;
    private List<Card> waitingCards;
    private StackedDeck stackedDeck;

    /**
     * Constructs the PlayingField object.
     *
     * @param width        The width of the playing field.
     * @param height       The height of the playing field.
     * @param screenWidth  The width of the screen.
     * @param screenHeight The height of the screen.
     * @param outlineColor The color of the outline.
     * @param gameController The game controller associated with the playing field.
     */
    public PlayingField(double width, double height, double screenWidth, double screenHeight, Color outlineColor, GameController gameController) {
        // Create the outline for the playing field
        Rectangle outline = new Rectangle(width, height, Color.TRANSPARENT);
        outline.setStroke(outlineColor);
        outline.setStrokeWidth(2);

        // Set the position of the playing field
        setTranslateX(320);
        setTranslateY(320);

        // Create an outline pane to make the outline transparent for mouse events
        Pane outlinePane = new Pane(outline);
        outlinePane.setMouseTransparent(true);
        getChildren().add(outlinePane);
        setMouseTransparent(true);

        // Initialize the game controller and waiting cards list
        this.gameController = gameController;
        this.waitingCards = new ArrayList<>();
    }

    /**
     * Sets the associated stacked deck for the playing field.
     *
     * @param stackedDeck The stacked deck associated with the playing field.
     */
    public void setStackedDeck(StackedDeck stackedDeck) {
        this.stackedDeck = stackedDeck;
    }

    /**
     * Plays a card on the playing field, triggering its effects.
     *
     * @param card The card to be played.
     */
    public void playCard(Card card) {
        // TODO: Fix this
        if (card.getCardType().getElementType() == ElementType.FIRE) {
            int damage = card.getCardType().getValue();
            this.gameController.playFireCard(damage);
        } else if (card.getCardType().getElementType() == ElementType.WATER) {
            int healing = card.getCardType().getValue();
            this.gameController.playWaterCard(healing);
        }

        // Add the card to the waiting cards list
        this.waitingCards.add(card);

        // Clear the playing field for new cards
        getChildren().clear();

        // Check if the stacked deck is empty
        if (this.stackedDeck.getRemainingCardCount() == 0) {
            // Add waiting cards to the stacked deck, clear the waiting cards, and shuffle the deck
            if (!this.waitingCards.isEmpty()) {
                this.stackedDeck.addCardsToStackedDeck(this.waitingCards);
                this.waitingCards.clear();
                this.stackedDeck.shuffleDeck();
            }
        }
    }

    /**
     * Gets the list of waiting cards on the playing field.
     *
     * @return The list of waiting cards.
     */
    public List<Card> getWaitingCards() {
        return this.waitingCards;
    }

    /**
     * Clears the list of waiting cards on the playing field.
     */
    public void clearWaitingCards() {
        this.waitingCards.clear();
    }
}

