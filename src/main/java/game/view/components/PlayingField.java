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

public class PlayingField extends StackPane {

    private GameController gameController;
    private List<Card> waitingCards;
    private StackedDeck stackedDeck; 

    public PlayingField(double width, double height, double screenWidth, double screenHeight, Color outlineColor, GameController gameController) {
        Rectangle outline = new Rectangle(width, height, Color.TRANSPARENT);
        outline.setStroke(outlineColor);
        outline.setStrokeWidth(2);

        setTranslateX(320);
        setTranslateY(320);

        Pane outlinePane = new Pane(outline);
        outlinePane.setMouseTransparent(true);
        getChildren().add(outlinePane);

        this.gameController = gameController;
        this.waitingCards = new ArrayList<>();
    }

    public void setStackedDeck(StackedDeck stackedDeck) {
        this.stackedDeck = stackedDeck;
    }

    public void playCard(Card card) {
        // Implement the card effects here based on the card type
        if (card.getCardType().getElementType() == ElementType.FIRE) {
            int damage = card.getCardType().getValue();
            this.gameController.playFireCard(damage);
        } else if (card.getCardType().getElementType() == ElementType.WATER) {
            int healing = card.getCardType().getValue();
            this.gameController.playWaterCard(healing);
        }

        this.waitingCards.add(card);

        // Clear the playing field for new cards
        getChildren().clear();

        if (this.stackedDeck.getRemainingCardCount() == 0) {

            if (!this.waitingCards.isEmpty()) {
                this.stackedDeck.addCardsToStackedDeck(this.waitingCards);
                this.waitingCards.clear();
                this.stackedDeck.shuffleDeck();
            }
        }
    }

    public List<Card> getWaitingCards() {
        return this.waitingCards;
    }

    public void clearWaitingCards() {
        this.waitingCards.clear();
    }
}

