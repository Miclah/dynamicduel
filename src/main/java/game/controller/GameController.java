package game.controller;

import game.model.Player;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class GameController {

    private StackPane gamePane;
    private boolean playerTurn = false;
    private Player player; 

    public GameController(StackPane gamePane, Player player) {
        this.gamePane = gamePane;
        this.player = player;
    }

    public void startPlayerTurn() {
        if (!playerTurn) {
            resetStyle();
            displayTurnMessage("Player's Turn");
            playerTurn = true;
        }
    }

    public void startOpponentTurn() {
        resetStyle();
        displayTurnMessage("Opponent's Turn");

        Timeline delayTimeline = new Timeline(
                new KeyFrame(Duration.seconds(2), event -> AIController.performTurn(this, player))
        );
        delayTimeline.play();
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

    private void resetStyle() {
        gamePane.getStylesheets().clear();
    }
}
