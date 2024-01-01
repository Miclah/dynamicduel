package game.controller;

import java.util.List;

import game.model.AI;
import game.model.Card;
import game.model.Player;
import game.view.GameView;
import game.view.MainMenu;
import game.view.components.DrawDeck;
import game.view.components.PlayerDeck;
import game.view.components.PlayingField;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The GameController class manages the game logic and interactions between the player, AI, and game components.
 */
public class GameController {

    private StackPane gamePane;
    private boolean playerTurn = false;
    private Player player; 
    private AI ai;
    private DrawDeck drawDeck;
    private PlayingField playField;

    /**
     * Constructs a GameController with the specified game pane, player, and AI.
     *
     * @param gamePane The StackPane representing the game area.
     * @param player The player object.
     * @param ai The AI object.
     */
    public GameController(StackPane gamePane, Player player, AI ai) {
        this.gamePane = gamePane;
        this.player = player;
        this.ai = ai;
    }

    /**
     * Sets the draw deck for the game controller.
     *
     * @param drawDeck The DrawDeck object.
     */
    public void setDrawDeck(DrawDeck drawDeck) {
        this.drawDeck = drawDeck;
    }

    /**
     * Plays a fire card with the specified damage.
     *
     * @param damage The damage value of the fire card.
     */
    public void playFireCard(int damage) {
        if (this.playerTurn) {
            this.ai.reduceAIHealth(damage);
        }
    }

    /**
     * Plays a water card with the specified healing.
     *
     * @param healing The healing value of the water card.
     */
    public void playWaterCard(int healing) {
        if (this.playerTurn) {
            this.player.healPlayer(healing);
        }
    }

    /**
     * Starts the player's turn.
     */
    public void startPlayerTurn() {
        if (!this.playerTurn) {
            this.resetStyle();
            this.displayTurnMessage("Player's Turn");
            this.playerTurn = true;
            PlayerDeck.resetCardDrawn();
        }
    }

    /**
     * Starts the opponent's turn.
     */
    public void startOpponentTurn() {
        this.resetStyle();
        this.displayTurnMessage("Opponent's Turn");

        Timeline delayTimeline = new Timeline(
                new KeyFrame(Duration.seconds(2), event -> AIController.performTurn(this, this.player, this.drawDeck))
        );
        delayTimeline.play();
    }

    /**
     * Displays a message indicating the need to draw a card.
     */
    public void drawCardMessage() {
        this.resetStyle();
        this.displayTurnMessage("Draw a card before ending your turn!");
    }

    /**
     * Displays a message indicating the one-card restriction.
     */
    public void oneCardRestiction() {
        this.resetStyle();
        this.displayTurnMessage("You can only draw one card per turn!");
    }

    /**
     * Displays a turn message with the specified text.
     *
     * @param message The message to display.
     */
    private void displayTurnMessage(String message) {
        Label turnLabel = new Label(message);
        turnLabel.setStyle("-fx-font-size: 36; -fx-font-weight: bold; -fx-text-fill: black;");

        StackPane.setAlignment(turnLabel, Pos.CENTER);
        this.gamePane.getChildren().add(turnLabel);
        turnLabel.toFront();
        turnLabel.setVisible(true);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(turnLabel.opacityProperty(), 1)),
                new KeyFrame(Duration.seconds(2), new KeyValue(turnLabel.opacityProperty(), 0))
        );

        timeline.setOnFinished(event -> {
            this.gamePane.getChildren().remove(turnLabel);
            this.playerTurn = false;
        });

        timeline.play();
    }

    /**
     * Checks the victory condition and displays a victory message if met.
     */
    public void checkVictoryCondition() {
        if (this.ai.getAIHealth() <= 0) {
            this.displayEndGameMessage("Victory!");
        }
    }
    
    /**
     * Checks the defeat condition and displays a defeat message if met.
     */
    public void checkDefeatCondition() {
        if (this.player.getHealth() <= 0) {
            this.displayEndGameMessage("Defeat!");
        }
    }
    
    /**
     * Displays an end game message with the specified text. 
     * //TODO: make the windows elements not interactible with, other than restart and mainmenu buttons.
     * //TODO: add victory and defeat music
     *
     * @param message The message to display.
     */
    private void displayEndGameMessage(String message) {
        Label endGameLabel = new Label(message);
        endGameLabel.setStyle("-fx-font-size: 72; -fx-font-weight: bold; -fx-text-fill: black;");

        StackPane.setAlignment(endGameLabel, Pos.CENTER);
        this.gamePane.getChildren().add(endGameLabel);
        endGameLabel.toFront();
        endGameLabel.setVisible(true);

        // Create restart and main menu buttons
        Button restartButton = new Button("Restart");
        Button exitButton = new Button("Main Menu");

        restartButton.setOnAction(event -> {
            this.restartGame();
        });

        exitButton.setOnAction(event -> {
            this.returnToMainMenu();
        });

        // Arrange buttons in an HBox
        HBox buttonBox = new HBox(20, restartButton, exitButton);
        buttonBox.setAlignment(Pos.CENTER);
        StackPane.setAlignment(buttonBox, Pos.BOTTOM_CENTER);
        buttonBox.setTranslateY(60);
        buttonBox.toFront();
        this.gamePane.getChildren().add(buttonBox);
    }

    /**
     * Restarts the game by opening a new game window and closing the current window.
     */
    private void restartGame() {
        Stage stage = (Stage)this.gamePane.getScene().getWindow();
        
        Stage newGameStage = new Stage();
        GameView.displayGame(newGameStage);
        
        // Transition effect to close the current window
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(1), new KeyValue(stage.opacityProperty(), 0)),
            new KeyFrame(Duration.millis(2), event -> stage.close())
        );
        timeline.play();
    }

    /**
     * Returns to the main menu by opening a new main menu window and closing the current window.
     */
    private void returnToMainMenu() {
        Stage stage = (Stage)this.gamePane.getScene().getWindow();
        
        MainMenu mainMenu = new MainMenu();
        Stage mainMenuStage = new Stage();
        mainMenu.start(mainMenuStage);

        // Transition effect to close the current window
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(1), new KeyValue(stage.opacityProperty(), 0)),
            new KeyFrame(Duration.millis(2), event -> stage.close())
        );
        timeline.play();
    }
    
    /**
     * Resets the styles applied to the game pane.
     */
    private void resetStyle() {
        this.gamePane.getStylesheets().clear();
    }

    /**
     * Sets the playing field for the game controller.
     *
     * @param field The PlayingField object.
     */
    public void setPlayingField (PlayingField field) {
        this.playField = field;
    }

    /**
     * Adds a list of cards to the waiting list of the playing field.
     *
     * @param cards The list of cards to add.
     */
    public void addCardsToWaitingList(List<Card> cards) {
        this.playField.getWaitingCards().addAll(cards);
    }
}
