package game.controller;

import game.model.AI;
import game.model.Player;
import game.view.GameView;
import game.view.MainMenu;
import game.view.components.DrawDeck;
import game.view.components.PlayerDeck;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameController {

    private StackPane gamePane;
    private boolean playerTurn = false;
    private Player player; 
    private AI ai;
    private Pane interactionBlocker;
    private DrawDeck drawDeck;


    public GameController(StackPane gamePane, Player player, AI ai) {
        this.gamePane = gamePane;
        this.player = player;
        this.ai = ai;
    }

    public void setDrawDeck(DrawDeck drawDeck) {
        this.drawDeck = drawDeck;
    }

    

    public void startPlayerTurn() {
        if (!playerTurn) {
            resetStyle();
            displayTurnMessage("Player's Turn");
            playerTurn = true;
            PlayerDeck.resetCardDrawn();
        }
    }

    public void startOpponentTurn() {
        resetStyle();
        displayTurnMessage("Opponent's Turn");

        Timeline delayTimeline = new Timeline(
                new KeyFrame(Duration.seconds(2), event -> AIController.performTurn(this, player, drawDeck))
        );
        delayTimeline.play();
    }

    public void displayDrawCardMessage() {
        resetStyle();
        displayTurnMessage("Draw a card before ending your turn!");
    }

    public void oneCardRestiction() {
        resetStyle();
        displayTurnMessage("You can only draw one card per turn!");
    }

    private void displayTurnMessage(String message) {
        Label turnLabel = new Label(message);
        turnLabel.setStyle("-fx-font-size: 36; -fx-font-weight: bold; -fx-text-fill: black;");

        StackPane.setAlignment(turnLabel, Pos.CENTER);
        gamePane.getChildren().add(turnLabel);
        turnLabel.toFront();
        turnLabel.setVisible(true);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(turnLabel.opacityProperty(), 1)),
                new KeyFrame(Duration.seconds(2), new KeyValue(turnLabel.opacityProperty(), 0))
        );

        timeline.setOnFinished(event -> {
            gamePane.getChildren().remove(turnLabel);
            playerTurn = false;
        });

        timeline.play();
    }

    public void checkVictoryCondition() {
        if (ai.getHealth() <= 0) {
            displayVictoryMessage();
        }
    }
    
    public void checkDefeatCondition() {
        if (player.getHealth() <= 0) {
            displayDefeatMessage();
        }
    }
    
    private void displayVictoryMessage() {
        displayEndGameMessage("Victory!");
    }
    
    private void displayDefeatMessage() {
        displayEndGameMessage("Defeat!");
    }
    
    private void displayEndGameMessage(String message) {
        Label endGameLabel = new Label(message);
        endGameLabel.setStyle("-fx-font-size: 72; -fx-font-weight: bold; -fx-text-fill: black;");

        StackPane.setAlignment(endGameLabel, Pos.CENTER);
        gamePane.getChildren().add(endGameLabel);
        endGameLabel.toFront();
        endGameLabel.setVisible(true);

        Button restartButton = new Button("Restart");
        Button exitButton = new Button("Main Menu");

        restartButton.setOnAction(event -> {
            restartGame();
        });

        exitButton.setOnAction(event -> {
            returnToMainMenu();
        });

        HBox buttonBox = new HBox(20, restartButton, exitButton);
        buttonBox.setAlignment(Pos.CENTER);
        StackPane.setAlignment(buttonBox, Pos.BOTTOM_CENTER);
        buttonBox.setTranslateY(60);
        gamePane.getChildren().add(buttonBox);
        //TODO: make the windows elements not interactible with, other than restart and mainmenu buttons.
        //TODO: add victory and defeat music
    }

    private void restartGame() {
        Stage stage = (Stage) gamePane.getScene().getWindow();
        
        Stage newGameStage = new Stage();
        GameView.displayGame(newGameStage);
        
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(1), new KeyValue(stage.opacityProperty(), 0)),
            new KeyFrame(Duration.millis(2), event -> stage.close())
        );
        timeline.play();

        setInteractionBlocked(true);

        timeline.setOnFinished(event -> {
            setInteractionBlocked(false);
            gamePane.getChildren().removeAll(interactionBlocker);
        });
    }

    private void returnToMainMenu() {
        Stage stage = (Stage) gamePane.getScene().getWindow();
        
        MainMenu mainMenu = new MainMenu();
        Stage mainMenuStage = new Stage();
        mainMenu.start(mainMenuStage);

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(1), new KeyValue(stage.opacityProperty(), 0)),
            new KeyFrame(Duration.millis(2), event -> stage.close())
        );
        timeline.play();

        setInteractionBlocked(true);

        timeline.setOnFinished(event -> {
            setInteractionBlocked(false);
            gamePane.getChildren().removeAll(interactionBlocker);
        });
    }
    
    private void resetStyle() {
        gamePane.getStylesheets().clear();
    }

    private void setInteractionBlocked(boolean blocked) {
        interactionBlocker.setMouseTransparent(blocked);
    }
}
