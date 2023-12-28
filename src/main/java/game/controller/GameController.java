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

public class GameController {

    private StackPane gamePane;
    private boolean playerTurn = false;
    private Player player; 
    private AI ai;
    private DrawDeck drawDeck;
    private PlayingField playField;

    public GameController(StackPane gamePane, Player player, AI ai) {
        this.gamePane = gamePane;
        this.player = player;
        this.ai = ai;
    }

    public void setDrawDeck(DrawDeck drawDeck) {
        this.drawDeck = drawDeck;
    }

    public void playFireCard(int damage) {
        if (this.playerTurn) {
            this.ai.reduceAIHealth(damage);
        }
    }

    public void playWaterCard(int healing) {
        if (this.playerTurn) {
            this.player.healPlayer(healing);
        }
    }

    public void startPlayerTurn() {
        if (!this.playerTurn) {
            this.resetStyle();
            this.displayTurnMessage("Player's Turn");
            this.playerTurn = true;
            PlayerDeck.resetCardDrawn();
        }
    }

    public void startOpponentTurn() {
        this.resetStyle();
        this.displayTurnMessage("Opponent's Turn");

        Timeline delayTimeline = new Timeline(
                new KeyFrame(Duration.seconds(2), event -> AIController.performTurn(this, this.player, this.drawDeck))
        );
        delayTimeline.play();
    }

    public void displayDrawCardMessage() {
        this.resetStyle();
        this.displayTurnMessage("Draw a card before ending your turn!");
    }

    public void oneCardRestiction() {
        this.resetStyle();
        this.displayTurnMessage("You can only draw one card per turn!");
    }

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

    public void checkVictoryCondition() {
        if (this.ai.getHealth() <= 0) {
            this.displayVictoryMessage();
        }
    }
    
    public void checkDefeatCondition() {
        if (this.player.getHealth() <= 0) {
            this.displayDefeatMessage();
        }
    }
    
    private void displayVictoryMessage() {
        this.displayEndGameMessage("Victory!");
    }
    
    private void displayDefeatMessage() {
        this.displayEndGameMessage("Defeat!");
    }
    
    //TODO: Fix the buttons not working, PlayingField pane responsible for this.
    private void displayEndGameMessage(String message) {
        Label endGameLabel = new Label(message);
        endGameLabel.setStyle("-fx-font-size: 72; -fx-font-weight: bold; -fx-text-fill: black;");

        StackPane.setAlignment(endGameLabel, Pos.CENTER);
        this.gamePane.getChildren().add(endGameLabel);
        endGameLabel.toFront();
        endGameLabel.setVisible(true);

        Button restartButton = new Button("Restart");
        Button exitButton = new Button("Main Menu");

        restartButton.setOnAction(event -> {
            this.restartGame();
        });

        exitButton.setOnAction(event -> {
            this.returnToMainMenu();
        });

        HBox buttonBox = new HBox(20, restartButton, exitButton);
        buttonBox.setAlignment(Pos.CENTER);
        StackPane.setAlignment(buttonBox, Pos.BOTTOM_CENTER);
        buttonBox.setTranslateY(60);
        buttonBox.toFront();
        this.gamePane.getChildren().add(buttonBox);
        //TODO: make the windows elements not interactible with, other than restart and mainmenu buttons.
        //TODO: add victory and defeat music
    }

    private void restartGame() {
        Stage stage = (Stage)this.gamePane.getScene().getWindow();
        
        Stage newGameStage = new Stage();
        GameView.displayGame(newGameStage);
        
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(1), new KeyValue(stage.opacityProperty(), 0)),
            new KeyFrame(Duration.millis(2), event -> stage.close())
        );
        timeline.play();
    }

    private void returnToMainMenu() {
        Stage stage = (Stage)this.gamePane.getScene().getWindow();
        
        MainMenu mainMenu = new MainMenu();
        Stage mainMenuStage = new Stage();
        mainMenu.start(mainMenuStage);

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(1), new KeyValue(stage.opacityProperty(), 0)),
            new KeyFrame(Duration.millis(2), event -> stage.close())
        );
        timeline.play();
    }
    
    private void resetStyle() {
        this.gamePane.getStylesheets().clear();
    }

    public void setPlayingField (PlayingField field) {
        this.playField = field;
    }

    public void addCardsToWaitingList(List<Card> cards) {
        this.playField.getWaitingCards().addAll(cards);
    }
}
