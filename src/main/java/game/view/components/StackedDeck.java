package game.view.components;

import game.controller.CardController;
import game.model.Card;
import game.model.CardType;
import game.view.GameView;
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
import java.util.Collections;
import java.util.List;

/**
 * The stacked deck from which cards are drawn during the game.
 */
public class StackedDeck extends StackPane {

    // File path for the card composition file
    private static final String CARD_COMPOSITION_FILE = "/Settings/cards_composition.txt";
    // File path for the card back image
    private static final String CARD_BACK_IMAGE_PATH = "/Cards/card_textures/card_back.png";
    // Factor for card overlap
    private static final double OVERLAP_FACTOR = 0.03;
    // Initial X position for drawing cards
    private static final double INITIAL_X = -510;
    // X increment for drawing cards
    private static final double X_INCREMENT = 130;

    private List<Card> stackedCards;

    /**
     * Constructs the StackedDeck object.
     *
     * @param deckWidth  The width of the deck.
     * @param deckHeight The height of the deck.
     * @param drawDeck   The associated draw deck.
     */
    public StackedDeck(double deckWidth, double deckHeight, DrawDeck drawDeck) {
        // Load stacked cards, shuffle the deck, and create a container for card images
        this.stackedCards = this.loadStackedCards();
        this.shuffleDeck();

        Pane cardImagesContainer = new Pane();
        cardImagesContainer.setMaxSize(deckWidth, deckHeight);

        getChildren().add(cardImagesContainer);

        setMaxHeight(deckHeight);
        setMaxWidth(deckWidth);
    }

    /**
     * Shuffles the deck.
     */
    public void shuffleDeck() {
        Collections.shuffle(this.stackedCards);
    }

    /**
     * Adds a list of cards to the stacked deck.
     *
     * @param cards The list of cards to be added.
     */
    public void addCardsToStackedDeck(List<Card> cards) {
        this.stackedCards.addAll(cards);
    }

    /**
     * Draws the initial cards for the player or AI, depending on the specified parameters.
     *
     * @param gameViewPane The game view pane where the cards will be displayed.
     * @param isPlayerCard Flag indicating whether the cards are for the player.
     */
    public void drawInitialCards(Pane gameViewPane, boolean isPlayerCard) {
        // Get the container for card images
        Pane cardImagesContainer = (Pane)getChildren().get(0);
        
        // Determine the number of cards to draw, limited to 5
        int numCardsToDraw = Math.min(5, this.stackedCards.size());

        // Loop through the cards to draw
        for (int i = 0; i < numCardsToDraw; i++) {
            double initialY = 380; // Initial Y position for player cards
            this.drawCard(i, cardImagesContainer, INITIAL_X + i * X_INCREMENT, initialY, isPlayerCard);
        }

        // Update the display of the stacked deck
        this.updateStackedDeck();

        // Create a copy of the card images container's children
        List<Node> copyOfChildren = new ArrayList<>(cardImagesContainer.getChildren());

        // Add the drawn cards to the game view pane
        copyOfChildren.forEach(cardImageView -> gameViewPane.getChildren().add(cardImageView));
    }

    /**
     * Draws the initial cards for the AI.
     *
     * @param gameViewPane The game view pane where the cards will be displayed.
     * @param isPlayerCard Flag indicating whether the cards are for the player.
     */
    public void drawInitialCardsForAI(Pane gameViewPane, boolean isPlayerCard) {
        // Create a new container for AI card images
        Pane cardAIContainer = new Pane();
        
        // Determine the number of cards to draw for AI, limited to 5
        int numCardsToDraw = Math.min(5, this.stackedCards.size());

        // Loop through the cards to draw
        for (int i = 0; i < numCardsToDraw; i++) {
            double initialY = -380; // Initial Y position for AI cards
            this.drawCard(i, cardAIContainer, INITIAL_X + i * X_INCREMENT, initialY, isPlayerCard);
        }

        // Update the display of the stacked deck
        this.updateStackedDeck();

        // Create a copy of the AI card container's children
        List<Node> copyOfChildren = new ArrayList<>(cardAIContainer.getChildren());

        // Add the drawn AI cards to the game view pane
        copyOfChildren.forEach(cardImageView -> gameViewPane.getChildren().add(cardImageView));
    }


    /**
     * Draws a single card on the specified container at the given position.
     *
     * @param index               The index of the card in the stacked deck.
     * @param cardImagesContainer The container where card images are stored.
     * @param x                   The X-coordinate for the card's position.
     * @param y                   The Y-coordinate for the card's position.
     * @param isPlayerCard        Flag indicating whether the card is for the player.
     */
    public void drawCard(int index, Pane cardImagesContainer, double x, double y, boolean isPlayerCard) {
        // Check if the stacked deck is not empty and the index is valid
        if (!this.stackedCards.isEmpty() && index < this.stackedCards.size()) {
            // Get the card from the stacked deck
            Card card = this.stackedCards.get(index);
            ImageView cardImageView = new ImageView();

            // Set the card image based on whether it's for the player or not
            cardImageView.setImage(isPlayerCard ? card.getFrontImage() : card.getBackImage());

            // Make the card interactive for the player or non-interactive for AI
            cardImageView.setMouseTransparent(!isPlayerCard);

            // Setup card interaction using the CardController
            new CardController().setupCardInteraction(cardImageView);
            cardImageView.setTranslateX(x);
            cardImageView.setTranslateY(y);

            // Calculate the width of the card based on its aspect ratio
            double cardBackWidth = card.getFrontImage().getWidth();
            double cardBackHeight = card.getFrontImage().getHeight();
            double aspectRatio = cardBackWidth / cardBackHeight;
            double cardWidth = GameView.getWindowHeight() / 4.0 * aspectRatio;
            cardImageView.setFitWidth(cardWidth);

            // Bind the card image property to dynamically switch between front and back images
            cardImageView.imageProperty().bind(Bindings.when(card.flippedProperty())
                    .then(isPlayerCard ? card.getFrontImage() : card.getBackImage())
                    .otherwise(isPlayerCard ? card.getBackImage() : card.getFrontImage()));

            // Add the card image to the container
            cardImagesContainer.getChildren().add(cardImageView);

            // Set the card as flipped and remove it from the stacked deck
            card.setFlipped(true);
            this.stackedCards.remove(card);
        }
    }


    /**
     * Updates the display of the stacked deck by rendering a subset of cards with an overlap effect.
     */
    private void updateStackedDeck() {
        // Clear the existing display
        getChildren().clear();
        double overlap = getMaxHeight() * OVERLAP_FACTOR;

        // Determine the number of cards to show (up to a maximum of x)
        int numCardsToShow = Math.min(5, this.stackedCards.size());

        for (int i = 0; i < numCardsToShow; i++) {
            Card card = this.stackedCards.get(i);

            // Create an ImageView for the card with the back image
            ImageView cardImageView = new ImageView(card.getBackImage());
            cardImageView.setPreserveRatio(true);

            // Calculate the dimensions of the card based on its aspect ratio
            double cardBackWidth = card.getBackImage().getWidth();
            double cardBackHeight = card.getBackImage().getHeight();
            double aspectRatio = cardBackWidth / cardBackHeight;
            double cardWidth = GameView.getWindowHeight() / 4.0 * aspectRatio;
            cardImageView.setFitWidth(cardWidth);

            cardImageView.setTranslateY(-i * overlap);
            cardImageView.setMouseTransparent(false);
            getChildren().add(cardImageView);
        }
    }

    /**
     * Loads the stacked cards from the card composition file.
     *
     * @return The list of loaded cards.
     */
    private List<Card> loadStackedCards() {
        // Initialize an empty list to store the loaded cards
        List<Card> cards = new ArrayList<>();

        try (InputStream inputStream = getClass().getResourceAsStream(CARD_COMPOSITION_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            // Read each line from the card composition file
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Skip empty lines or comments
                if (line.isEmpty() || line.startsWith("#") || line.equalsIgnoreCase("[Card Composition]")) {
                    continue;
                }

                // Split the line into parts separated by ":"
                String[] parts = line.split(":");
                if (parts.length != 2) {
                    // Print an error message for invalid lines, TODO: Add a better error message
                    System.err.println("Invalid line in card composition file: " + line);
                    continue;
                }

                // Extract card information and quantity from the line
                String cardInfo = parts[0].trim();
                int quantity = Integer.parseInt(parts[1].trim());

                // Split card details into type and value
                String[] cardDetails = cardInfo.split("_");
                if (cardDetails.length != 2) {
                    // Print an error message for invalid card details, TODO: Add a better error message
                    System.err.println("Invalid card details: " + cardInfo);
                    continue;
                }

                // Convert card details into a CardType
                String cardValue = cardDetails[1].trim().toUpperCase();
                CardType cardType = CardType.valueOf(cardDetails[0].toUpperCase() + "_" + cardValue);

                // Construct paths for front and back images of the card
                String frontImagePath = "/Cards/card_textures/" + cardType.getElementType().toString().toLowerCase() + "/" + cardValue + "_" + cardType.getElementType().toString().toLowerCase() + ".png";
                Image frontImage = new Image(getClass().getResourceAsStream(frontImagePath));

                Image backImage = new Image(getClass().getResourceAsStream(CARD_BACK_IMAGE_PATH));

                // Create a Card instance with the specified type and images
                Card card = new Card(cardType, frontImage, backImage);

                // Add the card to the list for the specified quantity
                for (int i = 0; i < quantity; i++) {
                    cards.add(card);
                }
            }

        } catch (IOException e) {
            // Print the stack trace for any IOException
            e.printStackTrace();
        }

        // Return the list of loaded cards
        return cards;
    }


    /**
     * Gets the remaining number of cards in the stacked deck.
     *
     * @return The remaining number of cards.
     */
    public int getRemainingCardCount() {
        return this.stackedCards.size();
    }

    /**
     * Gets the initial number of cards in the stacked deck.
     *
     * @return The initial number of cards.
     */
    public static int getInitialCardCount() {
        StackedDeck tempStackedDeck = new StackedDeck(0, 0, null);
        return tempStackedDeck.stackedCards.size();
    }
}
