package game.view;

import game.controller.GameController;
import game.model.AI;
import game.model.Player;
import game.util.MusicPlayer;
import game.view.components.DrawDeck;
import game.view.components.OpponentDeck;
import game.view.components.OpponentSpecial;
import game.view.components.PlayerDeck;
import game.view.components.PlayerSpecial;
import game.view.components.PlayingField;
import game.view.components.StackedDeck;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;    
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * The GameView class represents the main view of the game.
 */
public class GameView {

    // Attributes for window dimensions
    private static final double WINDOW_WIDTH = 1920;
    private static final double WINDOW_HEIGHT = 1080;

    /**
     * Displays the game view.
     *
     * @param primaryStage The primary stage of the application.
     */
    public static void displayGame(Stage primaryStage) {

        primaryStage.setTitle("Dynamic Duel");   // Set the stage title
        
        // Check if the screen resolution is supported
        if (!isResolutionSupported()) {
            resolutionErrorPopup(primaryStage);
            return;
        }

        // Check if the initial card count is sufficient to start the game
        int remainingCardCount = StackedDeck.getInitialCardCount();
        if (remainingCardCount < 20) {
            minCardCountErrorPopup(primaryStage);
            return;
        }

        MusicPlayer.playGameMusic1();// Start playing game music

        // Initialize root layout
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Initialize transparent pane to handle mouse events
        Pane transparentPane = new Pane();
        root.getChildren().add(transparentPane);
        transparentPane.toFront();

        // Initialize center container for game components
        StackPane centerContainer = new StackPane();
        root.setCenter(centerContainer);

        // Initialize pause overlay with buttons
        StackPane pauseOverlay = new StackPane();
        Button tutorialButton = new Button("Tutorial");
        tutorialButton.setOnAction(e -> System.out.println("Tutorial"));

        Button mainMenuButton = new Button("Return to Main Menu");
        mainMenuButton.setOnAction(e -> {
            MainMenu mainMenu = new MainMenu();
            Stage mainMenuStage = new Stage();
            mainMenu.start(mainMenuStage);
            primaryStage.hide();
        });

        VBox pauseMenu = new VBox(20, tutorialButton, mainMenuButton);
        pauseMenu.setAlignment(Pos.CENTER);
        pauseOverlay.getChildren().add(pauseMenu);
        centerContainer.getChildren().add(pauseOverlay);
        pauseOverlay.setVisible(false);

        // Style the pause overlay
        pauseOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.75); -fx-alignment: center;");

        // Initialize turn message pane
        StackPane turnMessagePane = new StackPane();
        centerContainer.getChildren().add(turnMessagePane);

        // Set event handler for the escape key to toggle the pause overlay
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                pauseOverlay.setVisible(!pauseOverlay.isVisible());
                pauseOverlay.toFront();
                pauseOverlay.getStylesheets().add(GameView.class.getResource("/Styles/Menu/pause_overlay.css").toExternalForm());
            }
        });

        // Set event handler for closing the window to stop game music
        primaryStage.setOnHidden(event -> {
            MusicPlayer.stopMusic(false);
        });

        // Initialize player, AI, and game controller
        Player player = new Player();
        AI ai = new AI();
        GameController gameController = new GameController(turnMessagePane, player, ai);

        // Initialize game components
        PlayerDeck playerDeckArea = new PlayerDeck(1600, 320, WINDOW_WIDTH, WINDOW_HEIGHT, Color.ORANGE, gameController, ai);
        PlayerSpecial playerSpecialArea = new PlayerSpecial(WINDOW_HEIGHT - 760, WINDOW_WIDTH, WINDOW_HEIGHT, Color.GREEN, player, transparentPane);
        OpponentDeck opponentDeckArea = new OpponentDeck(1600, 320, WINDOW_WIDTH, WINDOW_HEIGHT, Color.ORANGE);
        OpponentSpecial opponentSpecialArea = new OpponentSpecial(WINDOW_HEIGHT - 760, WINDOW_WIDTH, WINDOW_HEIGHT, Color.GREEN, ai, transparentPane);
        DrawDeck drawDeckArea = new DrawDeck(250, 440, WINDOW_WIDTH, WINDOW_HEIGHT, Color.BLUE, playerDeckArea);
        PlayingField playingFieldArea = new PlayingField(1350, 440, WINDOW_WIDTH, WINDOW_HEIGHT, Color.PURPLE, gameController);

        // Set up relationships between game components
        playingFieldArea.setStackedDeck(drawDeckArea.getStackedDeck());
        playerDeckArea.setPlayingField(playingFieldArea);

        // Add game components to the root layout
        root.getChildren().addAll(playerDeckArea, playingFieldArea, playerSpecialArea, opponentDeckArea, opponentSpecialArea, drawDeckArea);

        // Set up game controller with necessary components
        gameController.setDrawDeck(drawDeckArea);
        gameController.setPlayingField(playingFieldArea);
        player.setPlayerSpecial(playerSpecialArea);
        ai.setGameController(gameController);
        ai.setOpponentSpecial(opponentSpecialArea);
        player.setGameController(gameController);

        // Draw initial cards for the player and AI
        drawDeckArea.drawInitialCards(centerContainer, true);
        drawDeckArea.drawInitialCardsForAI(centerContainer, false);

        // Enable draw card interaction for the player
        drawDeckArea.enableDrawCardInteraction(drawDeckArea, root, centerContainer, gameController);

        // Set stage properties
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Checks if the current screen resolution is supported.
     *
     * @return True if the resolution is supported, false otherwise.
     */
    private static boolean isResolutionSupported() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        double screenWidth = bounds.getWidth();
        double screenHeight = bounds.getHeight();

        return screenWidth >= WINDOW_WIDTH && screenHeight >= WINDOW_HEIGHT;
    }

    /**
     * Displays an error popup for resolution issues and closes the application.
     *
     * @param primaryStage The primary stage of the application.
     */
    private static void resolutionErrorPopup(Stage primaryStage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Resolution Error");
        alert.setHeaderText("Minimum Resolution Requirement Not Met");
        alert.setContentText("Please make sure your screen resolution is at least 1920x1080 to play the game.");
        alert.setGraphic(null);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(GameView.class.getResource("/Styles/Game/game_error.css").toExternalForm());

        alert.showAndWait();
        primaryStage.close();
    }

    /**
     * Displays an error popup for insufficient cards and closes the application.
     *
     * @param primaryStage The primary stage of the application.
     */
    private static void minCardCountErrorPopup(Stage primaryStage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Insufficient Cards");
        alert.setHeaderText("Not Enough Cards in the Deck");
        alert.setContentText("The deck must have at least 15 cards to start the game.");
        alert.setGraphic(null);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(GameView.class.getResource("/Styles/Game/game_error.css").toExternalForm());

        alert.showAndWait();
        primaryStage.close();
    }

    /**
     * Gets the window width.
     *
     * @return The window width.
     */
    public static double getWindowWidth() {
        return WINDOW_WIDTH;
    }

    /**
     * Gets the window height.
     *
     * @return The window height.
     */
    public static double getWindowHeight() {
        return WINDOW_HEIGHT;
    }
}
