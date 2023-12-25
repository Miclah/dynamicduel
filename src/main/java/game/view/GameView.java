package game.view;

import game.controller.GameController;
import game.model.AI;
import game.model.Player;
import game.view.components.DrawDeck;
import game.view.components.OpponentDeck;
import game.view.components.OpponentSpecial;
import game.view.components.PlayerDeck;
import game.view.components.PlayerSpecial;
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

public class GameView {

    private static final double WINDOW_WIDTH = 1920;
    private static final double WINDOW_HEIGHT = 1080;
    

    public static void displayGame(Stage primaryStage) {

        if (!isResolutionSupported()) {
            displayResolutionErrorPopup(primaryStage);
            return;
        }

        primaryStage.setTitle("Dynamic Duel");

        BorderPane root = new BorderPane();

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        

        Pane transparentPane = new Pane();
        root.getChildren().add(transparentPane);
        transparentPane.toFront();


        StackPane centerContainer = new StackPane();
        root.setCenter(centerContainer);

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

        Player player = new Player();
        AI ai = new AI();
        GameController gameController = new GameController(turnMessagePane, player);

        PlayerDeck playerDeckArea = new PlayerDeck(1600, 320, WINDOW_WIDTH, WINDOW_HEIGHT, Color.ORANGE, gameController);
        PlayerSpecial playerSpecialArea = new PlayerSpecial(WINDOW_HEIGHT - 760, WINDOW_WIDTH, WINDOW_HEIGHT, Color.GREEN, player, transparentPane);
        OpponentDeck opponentDeckArea = new OpponentDeck(1600, 320, WINDOW_WIDTH, WINDOW_HEIGHT, Color.ORANGE);
        OpponentSpecial opponentSpecialArea = new OpponentSpecial(WINDOW_HEIGHT - 760, WINDOW_WIDTH, WINDOW_HEIGHT, Color.GREEN, ai, transparentPane);
        DrawDeck drawDeckArea = new DrawDeck(250, 440, WINDOW_WIDTH, WINDOW_HEIGHT, Color.BLUE);

        root.getChildren().addAll(playerDeckArea, playerSpecialArea, opponentDeckArea, opponentSpecialArea, drawDeckArea);
        player.setPlayerSpecial(playerSpecialArea);
        drawDeckArea.drawInitialCards(centerContainer);

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private static boolean isResolutionSupported() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        double screenWidth = bounds.getWidth();
        double screenHeight = bounds.getHeight();

        return screenWidth >= WINDOW_WIDTH && screenHeight >= WINDOW_HEIGHT;
    }

    private static void displayResolutionErrorPopup(Stage primaryStage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Resolution Error");
        alert.setHeaderText("Minimum Resolution Requirement Not Met");
        alert.setContentText("Please make sure your screen resolution is at least 1920x1080 to play the game.");
        alert.setGraphic(null);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(GameView.class.getResource("/Styles/Menu/resolution_popup.css").toExternalForm());

        alert.showAndWait();

        primaryStage.close();
    }

    public static double getWindowWidth() {
        return WINDOW_WIDTH;
    }

    public static double getWindowHeight() {
        return WINDOW_HEIGHT;
    }
}
