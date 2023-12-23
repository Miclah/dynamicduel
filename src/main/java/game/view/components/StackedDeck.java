package game.view.components;

import game.controller.CardController;
import game.model.Card;
import game.model.CardType;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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

    
    private double overlap;

    private List<Card> stackedCards;

    public StackedDeck(double deckWidth, double deckHeight) {
        this.stackedCards = loadStackedCards();
    
        Pane cardImagesContainer = new Pane();
        cardImagesContainer.setMaxSize(deckWidth, deckHeight);
    
        this.overlap = deckHeight * OVERLAP_FACTOR;
    
        int numCardsToShow = Math.min(4, stackedCards.size());
    
        for (int i = 0; i < numCardsToShow; i++) {
            Card card = stackedCards.get(i);

            ImageView cardImageView = new ImageView(card.getFrontImage()); 
            System.out.println("CardImageView Bounds: " + cardImageView.getBoundsInParent());
            cardImageView.setTranslateY(-i * overlap);
            new CardController().setupCardInteraction(cardImageView);
            cardImagesContainer.getChildren().add(cardImageView);
        }

        
    
        System.out.println("Before adding cardImagesContainer to StackPane: " + getChildren());
        getChildren().add(cardImagesContainer);
        System.out.println("After adding cardImagesContainer to StackPane: " + getChildren());
    
        setMaxHeight(deckHeight);
        setMaxWidth(deckWidth);
    
    }

    public void drawInitialCards(Pane gameViewPane) {
        Pane cardImagesContainer = (Pane) getChildren().get(0);
        for (int i = 0; i < 5; i++) {
            drawCard(i, cardImagesContainer);
            stackedCards.remove(0);
        }
        updateStackedDeck();

        List<Node> copyOfChildren = new ArrayList<>(cardImagesContainer.getChildren());

        copyOfChildren.forEach(cardImageView -> gameViewPane.getChildren().add(cardImageView));
    }

    
    public void drawCard(int index, Pane cardImagesContainer) {
        if (!stackedCards.isEmpty() && index < stackedCards.size()) {
            Card card = stackedCards.get(index);

            ImageView cardImageView = new ImageView(card.getFrontImage());

            cardImageView.setTranslateX(-index * overlap);
            cardImageView.setTranslateY(-index * overlap);
    
            cardImageView.imageProperty().bind(Bindings.when(card.flippedProperty())
                    .then(card.getFrontImage())
                    .otherwise(card.getBackImage()));
    
            new CardController().setupCardInteraction(cardImageView);
    
            cardImageView.setOnMouseClicked(event -> card.setFlipped(!card.isFlipped()));
    
            System.out.println("Before adding cardImageView to cardImagesContainer: " + cardImagesContainer.getChildren());
            cardImagesContainer.getChildren().add(cardImageView);
            System.out.println("After adding cardImageView to cardImagesContainer: " + cardImagesContainer.getChildren());
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
    
                String cardValue = cardDetails[1].trim().toUpperCase();
                CardType cardType = CardType.valueOf(cardDetails[0].toUpperCase() + "_" + cardValue);

                String frontImagePath = "/Cards/card_textures/" + cardType.getElementType().toString().toLowerCase() + "/" + cardValue + "_" + cardType.getElementType().toString().toLowerCase() + ".png";
                Image frontImage = new Image(getClass().getResourceAsStream(frontImagePath));
    
                Image backImage = new Image(getClass().getResourceAsStream(CARD_BACK_IMAGE_PATH));

                System.out.println("Front Image Loaded: " + frontImage);
                System.out.println("Back Image Loaded: " + backImage);
                Card card = new Card(cardType, frontImage, backImage);
    
                for (int i = 0; i < quantity; i++) {
                    cards.add(card);
                }
                System.out.println("Loaded card: " + card.getCardType() + ", Front Image: " + card.getFrontImage());
                System.out.println("Front Image Dimensions: " + card.getFrontImage().getWidth() + " x " + card.getFrontImage().getHeight());
                System.out.println("Back Image Dimensions: " + card.getBackImage().getWidth() + " x " + card.getBackImage().getHeight());

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
