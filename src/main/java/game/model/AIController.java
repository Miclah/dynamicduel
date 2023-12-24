package game.model;

import game.controller.GameController;
import javafx.application.Platform;

public class AIController {

    public static void performTurn(GameController gameController) {
        System.out.println("AI's Turn");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(() -> gameController.startPlayerTurn());
    }
}
