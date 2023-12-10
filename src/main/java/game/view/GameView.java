package game.view;

import game.model.Card;
import game.model.CardLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.List;

public class GameView {

    public static void displayGame(Stage primaryStage) {
        primaryStage.setTitle("Dynamic Duel");

        // Load cards
        List<Card> fireCards = CardLoader.loadFireCards();
        List<Card> waterCards = CardLoader.loadWaterCards();
        List<Card> earthCards = CardLoader.loadEarthCards();

        // Create the game area
        Pane gameArea = new Pane();
        gameArea.setStyle("-fx-background-color: #2c3e50;"); // Set background color

        // Get the primary screen dimensions
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        // Create the scene with the user's screen dimensions
        Scene scene = new Scene(gameArea, bounds.getWidth(), bounds.getHeight());

        // Set the position and size to cover the entire screen
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        // Set fullscreen option
        primaryStage.setFullScreen(false);

        // Set the scene to the stage
        primaryStage.setScene(scene);

        // Display loaded cards in the game area
        displayCards(gameArea, fireCards, 100, 100);
        displayCards(gameArea, waterCards, 300, 100);
        displayCards(gameArea, earthCards, 500, 100);

        // Displaying the stage
        primaryStage.show();
    }

    private static void displayCards(Pane gameArea, List<Card> cards, double startX, double startY) {
        double xOffset = 30;

        for (Card card : cards) {
            CardView cardView = new CardView(card);
            cardView.relocate(startX, startY);
            gameArea.getChildren().add(cardView);

            startX += xOffset;
        }
    }
}
