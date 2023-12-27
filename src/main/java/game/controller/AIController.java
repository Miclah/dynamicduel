package game.controller;

import game.model.Player;
import game.view.components.DrawDeck;
import javafx.application.Platform;
public class AIController {

    public static void performTurn(GameController gameController, Player player, DrawDeck drawDeck) {
        System.out.println("AI's Turn");
        player.reduceHealth(10);
        drawDeck.drawCardForAI(); // Add this line to draw a card for the AI
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(() -> gameController.startPlayerTurn());
    }
}