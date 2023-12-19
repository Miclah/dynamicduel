package game.view;

import game.model.Card;
import game.model.CardLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.List;

public class GameView {

    public static void displayGame(Stage primaryStage) {
        primaryStage.setTitle("Dynamic Duel");

        Pane gameArea = new Pane();
        gameArea.setStyle("-fx-background-color: #2c3e50;"); // Set background color

        // Load cards using CardLoader
        List<Card> cards = CardLoader.loadCards();

        // Create ImageViews for the first two cards (assuming you have at least two cards in the list)
        if (cards.size() >= 2) {
            ImageView card1ImageView = new ImageView(cards.get(0).getFrontImage());
            card1ImageView.setTranslateX(50); // Set X position
            card1ImageView.setTranslateY(50); // Set Y position

            ImageView card2ImageView = new ImageView(cards.get(1).getFrontImage());
            card2ImageView.setTranslateX(500); // Set X position
            card2ImageView.setTranslateY(50); // Set Y position

            // Add ImageViews to the game area
            gameArea.getChildren().addAll(card1ImageView, card2ImageView);
        }

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

        // Check if fullscreen is enabled
        // Note: You need to implement the Settings.loadFullscreenSetting() method
        // to retrieve the fullscreen setting from your settings.
        if (Settings.loadFullscreenSetting()) {
            primaryStage.setFullScreen(true);
        }

        // Set the scene to the stage
        primaryStage.setScene(scene);

        // Displaying the stage
        primaryStage.show();
    }
}
