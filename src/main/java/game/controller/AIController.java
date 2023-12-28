package game.controller;

import game.model.Player;
import game.view.components.DrawDeck;
import javafx.application.Platform;

/**
 * Controls the actions of the AI player during its turn.
 */
public class AIController {

    /**
     * Performs the AI player's turn, including drawing a card and initiating the player's turn.
     *
     * @param gameController The game controller.
     * @param player         The AI player.
     * @param drawDeck       The draw deck.
     */
    public static void performTurn(GameController gameController, Player player, DrawDeck drawDeck) {
        player.reduceHealth(3);
        // Draw a card for the AI
        drawDeck.drawCardForAI();
        // Simulate a delay (e.g., Thread.sleep) to make the AI turn visible
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Use Platform.runLater to update the UI after the delay
        Platform.runLater(() -> gameController.startPlayerTurn());
    }
}
