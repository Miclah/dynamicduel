package game.view;

import game.controller.CardController;
import game.model.CardLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameView {

    private static final double INITIAL_WIDTH = 1920;
    private static final double INITIAL_HEIGHT = 1080;

    public static void displayGame(Stage primaryStage) {
        primaryStage.setTitle("Dynamic Duel");

        StackPane root = new StackPane();

        Scene scene = new Scene(root, INITIAL_WIDTH, INITIAL_HEIGHT);

        CardController cardController = new CardController();
        var cards = CardLoader.loadCards();
        var card1ImageView = cardController.createCardImageView(cards.get(0), INITIAL_HEIGHT);
        var card2ImageView = cardController.createCardImageView(cards.get(1), INITIAL_HEIGHT);

        card1ImageView.setTranslateX(INITIAL_WIDTH / 4);
        card1ImageView.setTranslateY(INITIAL_HEIGHT / 4);
        card2ImageView.setTranslateX(INITIAL_WIDTH / 2);
        card2ImageView.setTranslateY(INITIAL_HEIGHT / 4);

        root.getChildren().addAll(card1ImageView, card2ImageView);

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
        root.getChildren().add(pauseOverlay);
        pauseOverlay.setVisible(false);

        pauseOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.75); -fx-alignment: center;");

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                pauseOverlay.setVisible(!pauseOverlay.isVisible());
                pauseOverlay.getStylesheets().add(GameView.class.getResource("/Styles/Menu/pause_overlay.css").toExternalForm());
            }
        });

        // Disable resizing
        primaryStage.setResizable(false);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
