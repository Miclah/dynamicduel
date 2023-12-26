package game.controller;

import game.model.Player;
import javafx.application.Platform;

public class AIController {

    public static void performTurn(GameController gameController, Player player) {
        System.out.println("AI's Turn");
        player.reduceHealth(10);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(() -> gameController.startPlayerTurn());
    }
}
