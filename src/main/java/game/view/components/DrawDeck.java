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

public class DrawDeck extends StackPane {

    private boolean initialDrawn = false;
    private StackedDeck stackedDeck;
    private Text cardCount;
    private PlayerDeck playerDeck;

    public DrawDeck(double width, double height, double screenWidth, double screenHeight, Color outlineColor, PlayerDeck playerDeck) {
        Rectangle outline = new Rectangle(width, height, Color.TRANSPARENT);
        outline.setStroke(outlineColor);
        outline.setStrokeWidth(2);

        setTranslateX(screenWidth - width / 2);
        setTranslateY(screenHeight / 2);

        this.stackedDeck = new StackedDeck(width, height, this);
        this.playerDeck = playerDeck;

        StackPane cardsPane = new StackPane(stackedDeck);
        cardsPane.setAlignment(Pos.CENTER);

        cardCount = new Text("Cards Left: " + stackedDeck.getRemainingCardCount());
        cardCount.setFill(Color.BLACK);
        HBox textHBox = new HBox(cardCount);
        textHBox.setAlignment(Pos.BOTTOM_CENTER);
        textHBox.setTranslateY(-30);

        StackPane wholePane = new StackPane(outline, cardsPane, textHBox);
        wholePane.setAlignment(Pos.CENTER);

        getChildren().add(wholePane);
    }

    public void drawInitialCards(Pane gameViewPane, boolean isPlayerCard) {
        if (!initialDrawn) {
            stackedDeck.drawInitialCards(gameViewPane, isPlayerCard);
            initialDrawn = true;
            updateCardCount();
        }
    }

    public void drawInitialCardsForAI(Pane gameViewPane, boolean isPlayerCard) {
        stackedDeck.drawInitialCardsForAI(gameViewPane, isPlayerCard);
        updateCardCount();
    }

    public void drawCardForAI() {
        Pane cardImagesContainer = (Pane) getChildren().get(0);
        double drawX = -230;
        double drawY = -380;
        stackedDeck.drawCard(0, cardImagesContainer, drawX, drawY, false); // Pass false to indicate it's an AI card
        updateCardCount();
    }

    public void updateCardCount() {
        cardCount.setText("Cards Left: " + stackedDeck.getRemainingCardCount());
    }

    //TODO: Fix the ability to draw a card by clicking on the AI's drawn card
    public void enableDrawCardInteraction(DrawDeck drawDeck, Pane gameViewPane, StackPane centerContainer, GameController gameController) {
        Pane cardImagesContainer = (Pane) getChildren().get(0);
        
    
        setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (!playerDeck.hasDrawnCardThisTurn() && stackedDeck.getRemainingCardCount() > 0) {
                    double drawX = 600;
                    double drawY = 380;
                    handleLeftClick(drawDeck, cardImagesContainer, gameViewPane, centerContainer, drawX, drawY);
                } else {
                    gameController.oneCardRestiction();
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {
                System.out.println("Right mouse button clicked");
            }
        });
    }
    
    private void handleLeftClick(DrawDeck drawDeck, Pane cardImagesContainer, Pane gameViewPane, StackPane centerContainer, double drawX, double drawY) {
        stackedDeck.drawCard(0, cardImagesContainer, drawX, drawY, true);
        drawDeck.updateCardCount();
    
        Node drawnCard = cardImagesContainer.getChildren().remove(cardImagesContainer.getChildren().size() - 1);
        centerContainer.getChildren().add(drawnCard);
        playerDeck.setCardDrawn(true);
        PlayerDeck.incrementCardsDrawnThisTurn();
        drawnCard.toFront();
    }
}
