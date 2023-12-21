package game.view.components;

import game.model.Card;
import game.model.CardType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StackedDeck extends StackPane {

    private static final String CARD_COMPOSITION_FILE = "/Settings/cards_composition.txt";
    private static final String CARD_BACK_IMAGE_PATH = "/Cards/card_textures/card_back.png";
    private static final double OVERLAP_FACTOR = 0.03;
    private static final int screenHeight = 1080;

    private List<Card> stackedCards;

    public StackedDeck(double deckWidth, double deckHeight) {
        this.stackedCards = loadStackedCards();

        double overlap = deckHeight * OVERLAP_FACTOR;

        int numCardsToShow = Math.min(4, stackedCards.size());

        for (int i = 0; i < numCardsToShow; i++) {
            Card card = stackedCards.get(i);

            ImageView cardImageView = new ImageView(card.getBackImage());
            cardImageView.setPreserveRatio(true);

            double cardBackWidth = card.getBackImage().getWidth();
            double cardBackHeight = card.getBackImage().getHeight();
            double aspectRatio = cardBackWidth / cardBackHeight;
            double cardWidth = screenHeight / 4.0 * aspectRatio;

            cardImageView.setFitWidth(cardWidth);
            cardImageView.setTranslateY(-i * overlap);

            getChildren().add(cardImageView);
        }

        setMaxHeight(deckHeight);
        setMaxWidth(deckWidth);
    }

    public void drawCard() {
        if (!stackedCards.isEmpty()) {
            stackedCards.remove(0); 
            updateStackedDeck();
        }
    }

    private void updateStackedDeck() {
        getChildren().clear(); 

        double overlap = getMaxHeight() * OVERLAP_FACTOR;

        int numCardsToShow = Math.min(3, stackedCards.size());

        for (int i = 0; i < numCardsToShow; i++) {
            Card card = stackedCards.get(i);

            ImageView cardImageView = new ImageView(card.getBackImage());
            cardImageView.setPreserveRatio(true);

            double cardBackWidth = card.getBackImage().getWidth();
            double cardBackHeight = card.getBackImage().getHeight();
            double aspectRatio = cardBackWidth / cardBackHeight;
            double cardWidth = screenHeight / 4.0 * aspectRatio;

            cardImageView.setFitWidth(cardWidth);
            cardImageView.setTranslateY(-i * overlap);

            getChildren().add(cardImageView);
        }
    }

    private List<Card> loadStackedCards() {
        List<Card> cards = new ArrayList<>();

        try (InputStream inputStream = getClass().getResourceAsStream(CARD_COMPOSITION_FILE);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#") || line.equalsIgnoreCase("[Card Composition]")) {
                    continue;
                }

                String[] parts = line.split(":");
                if (parts.length != 2) {
                    System.err.println("Invalid line in card composition file: " + line);
                    continue;
                }

                String cardInfo = parts[0].trim();
                int quantity = Integer.parseInt(parts[1].trim());

                String[] cardDetails = cardInfo.split("_");
                if (cardDetails.length != 2) {
                    System.err.println("Invalid card details: " + cardInfo);
                    continue;
                }

                String cardValue = cardDetails[1].trim();
                CardType cardType = CardType.valueOf(cardDetails[0].toUpperCase() + "_" + cardValue);

                Image backImage = new Image(getClass().getResourceAsStream(CARD_BACK_IMAGE_PATH));
                Card card = new Card(cardType, null, backImage);

                for (int i = 0; i < quantity; i++) {
                    cards.add(card);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return cards;
    }

    public int getRemainingCardCount() {
        return stackedCards.size();
    }
}
