package game.model;

import game.controller.GameController;

public class AI {

    private int aiHealth;
    private GameController gameController;

    public AI() {
        resetAIHealth();
    }

    public int getHealth() {
        return aiHealth;
    }

    public void setAIHealth(int aiHealth) {
        this.aiHealth = Math.max(0, Math.min(100, aiHealth));
    }

    public void reduceAIHealth(int amount) {
        this.aiHealth = Math.max(0, aiHealth - amount);
        if (gameController != null) {
            gameController.checkVictoryCondition();
        }
    }

    public void resetAIHealth() {
        this.aiHealth = 95;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
}
