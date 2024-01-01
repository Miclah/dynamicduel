package game.view.components;

import game.controller.GameController;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Draw deck in the game view, allowing players to draw cards.
 */
public class DrawDeck extends StackPane {

    // Boolean to check if initial cards have been drawn
    private boolean initialDrawn;
    private StackedDeck stackedDeck;
    private Text cardCount;
    private PlayerDeck playerDeck;

    /**
     * Constructs the DrawDeck object.
     *
     * @param width        The width of the draw deck.
     * @param height       The height of the draw deck.
     * @param screenWidth  The width of the screen.
     * @param screenHeight The height of the screen.
     * @param outlineColor The color of the outline.
     * @param playerDeck   The player's deck associated with the draw deck.
     */
    public DrawDeck(double width, double height, double screenWidth, double screenHeight, Color outlineColor, PlayerDeck playerDeck) {
        // Create a transparent outline
        Rectangle outline = new Rectangle(width, height, Color.TRANSPARENT);
        outline.setStroke(outlineColor);
        outline.setStrokeWidth(2);

        setTranslateX(screenWidth - width / 2);
        setTranslateY(screenHeight / 2);

        this.initialDrawn = false;
        this.stackedDeck = new StackedDeck(width, height, this);
        this.playerDeck = playerDeck;

        StackPane cardsPane = new StackPane(this.stackedDeck);
        cardsPane.setAlignment(Pos.CENTER);

        // Text displaying the number of cards left in the stacked deck
        this.cardCount = new Text("Cards Left: " + this.stackedDeck.getRemainingCardCount());
        this.cardCount.setFill(Color.BLACK);
        HBox textHBox = new HBox(this.cardCount);
        textHBox.setAlignment(Pos.BOTTOM_CENTER);
        textHBox.setTranslateY(-30);

        // StackPane to combine outline, card images, and text
        StackPane wholePane = new StackPane(outline, cardsPane, textHBox);
        wholePane.setAlignment(Pos.CENTER);

        getChildren().add(wholePane);
    }

    /**
     * Gets the StackedDeck associated with the DrawDeck.
     *
     * @return The StackedDeck object.
     */
    public StackedDeck getStackedDeck() {
        return this.stackedDeck;
    }

    /**
     * Draws initial cards for the player.
     *
     * @param gameViewPane  The game view pane.
     * @param isPlayerCard  Indicates if the card is for the player.
     */
    public void drawInitialCards(Pane gameViewPane, boolean isPlayerCard) {
        if (!this.initialDrawn) {
            this.stackedDeck.drawInitialCards(gameViewPane, isPlayerCard);
            this.initialDrawn = true;
            this.updateCardCount();
        }
    }

    /**
     * Draws initial cards for the AI.
     *
     * @param gameViewPane  The game view pane.
     * @param isPlayerCard  Indicates if the card is for the player.
     */
    public void drawInitialCardsForAI(Pane gameViewPane, boolean isPlayerCard) {
        this.stackedDeck.drawInitialCardsForAI(gameViewPane, isPlayerCard);
        this.updateCardCount();
    }

    /**
     * Draws a card for the AI.
     */
    public void drawCardForAI() {
        Pane cardImagesContainer = (Pane)getChildren().get(0);
        double drawX = -230;
        double drawY = -380;
        this.stackedDeck.drawCard(0, cardImagesContainer, drawX, drawY, false);
        this.updateCardCount();
    }

    /**
     * Updates the card count text.
     */
    private void updateCardCount() {
        this.cardCount.setText("Cards Left: " + this.stackedDeck.getRemainingCardCount());
    }

    /**
     * Enables interaction for drawing a card from the draw deck using the primary mouse button (left).
     *
     * @param drawDeck        The DrawDeck object.
     * @param gameViewPane    The game view pane.
     * @param centerContainer The center container of the game view.
     * @param gameController  The game controller.
     */
    public void enableDrawCardInteraction(DrawDeck drawDeck, Pane gameViewPane, StackPane centerContainer, GameController gameController) {
        Pane cardImagesContainer = (Pane)getChildren().get(0);

        setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (!this.playerDeck.hasDrawnCardThisTurn() && this.stackedDeck.getRemainingCardCount() > 0) {
                    double drawX = 600;
                    double drawY = 380;
                    this.handleLeftClick(drawDeck, cardImagesContainer, gameViewPane, centerContainer, drawX, drawY);
                } else {
                    gameController.oneCardRestiction();
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {
                // TODO: Add some functionality for right click
                System.out.println("Right mouse button clicked");
            }
        });
    }

    /**
     * Handles the left mouse click event for drawing a card.
     *
     * @param drawDeck         The DrawDeck object.
     * @param cardImagesContainer The container for card images.
     * @param gameViewPane     The game view pane.
     * @param centerContainer  The center container of the game view.
     * @param drawX            The X-coordinate for drawing the card.
     * @param drawY            The Y-coordinate for drawing the card.
     */
    private void handleLeftClick(DrawDeck drawDeck, Pane cardImagesContainer, Pane gameViewPane, StackPane centerContainer, double drawX, double drawY) {
        this.stackedDeck.drawCard(0, cardImagesContainer, drawX, drawY, true);
        drawDeck.updateCardCount();

        Node drawnCard = cardImagesContainer.getChildren().remove(cardImagesContainer.getChildren().size() - 1);
        centerContainer.getChildren().add(drawnCard);
        
        // Sets card drawn to true after drawing 1 card
        this.playerDeck.setCardDrawn(true);
        PlayerDeck.incCardsDrawnThisTurn();
        drawnCard.toFront();
    }
}
