package game.view;

import java.util.List;

import game.controller.GameController;
import game.model.Card;
import game.model.CardLoader;
import game.view.components.DrawDeck;
import game.view.components.OpponentDeck;
import game.view.components.OpponentSpecial;
import game.view.components.PlayerDeck;
import game.view.components.PlayerSpecial;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameView {

    private static final double WINDOW_WIDTH = 1920;
    private static final double WINDOW_HEIGHT = 1080;

    public static void displayGame(Stage primaryStage) {
        primaryStage.setTitle("Dynamic Duel");

        BorderPane root = new BorderPane();

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        StackPane centerContainer = new StackPane();
        root.setCenter(centerContainer);

        StackPane pauseOverlay = new StackPane();

        Button tutorialButton = new Button("Tutorial");
        tutorialButton.setOnAction(e -> System.out.println("Tutorial"));

        Button returnToMainMenuButton = new Button("Return to Main Menu");
        returnToMainMenuButton.setOnAction(e -> {
            MainMenu mainMenu = new MainMenu();
            Stage mainMenuStage = new Stage();
            mainMenu.start(mainMenuStage);
            primaryStage.hide();
        });

        VBox pauseMenu = new VBox(20, tutorialButton, returnToMainMenuButton);
        pauseMenu.setAlignment(Pos.CENTER);

        pauseOverlay.getChildren().add(pauseMenu);
        centerContainer.getChildren().add(pauseOverlay);
        pauseOverlay.setVisible(false);

        pauseOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.75); -fx-alignment: center;");

        StackPane turnMessagePane = new StackPane();
        centerContainer.getChildren().add(turnMessagePane);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                pauseOverlay.setVisible(!pauseOverlay.isVisible());
                pauseOverlay.toFront();
                pauseOverlay.getStylesheets().add(GameView.class.getResource("/Styles/Menu/pause_overlay.css").toExternalForm());
            }
        });

        GameController gameController = new GameController(turnMessagePane);

        PlayerDeck playerDeckArea = new PlayerDeck(1600, 320, WINDOW_WIDTH, WINDOW_HEIGHT, Color.ORANGE, gameController);
        PlayerSpecial playerSpecialArea = new PlayerSpecial(WINDOW_HEIGHT - 760, WINDOW_WIDTH, WINDOW_HEIGHT, Color.GREEN);
        OpponentDeck opponentDeckArea = new OpponentDeck(1600, 320, WINDOW_WIDTH, WINDOW_HEIGHT, Color.ORANGE);
        OpponentSpecial opponentSpecialArea = new OpponentSpecial(WINDOW_HEIGHT - 760, WINDOW_WIDTH, WINDOW_HEIGHT, Color.GREEN);
        DrawDeck drawDeckArea = new DrawDeck(250, 440, WINDOW_WIDTH, WINDOW_HEIGHT, Color.BLUE);
        

        root.getChildren().addAll(playerDeckArea, playerSpecialArea, opponentDeckArea, opponentSpecialArea, drawDeckArea);
        drawDeckArea.drawInitialCards(centerContainer);

        /* test
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
            centerContainer.getChildren().addAll(card1ImageView, card2ImageView);
        }
        */

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static double getWindowWidth() {
        return WINDOW_WIDTH;
    }

    public static double getWindowHeight() {
        return WINDOW_HEIGHT;
    }
}
