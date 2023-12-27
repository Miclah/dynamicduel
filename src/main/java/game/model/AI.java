package game.model;

import game.controller.GameController;
import game.view.components.OpponentSpecial;

public class AI {

    private int aiHealth;
    private OpponentSpecial opponentSpecialArea;
    private GameController gameController;

    public AI() {
        resetAIHealth();
    }

    public void setOpponentSpecial(OpponentSpecial opponentSpecialArea ) {
        this.opponentSpecialArea = opponentSpecialArea;
    }

    public int getHealth() {
        return aiHealth;
    }

    public void setAIHealth(int aiHealth) {
        this.aiHealth = Math.max(0, Math.min(100, aiHealth));
    }

    public void reduceAIHealth(int amount) {
        this.aiHealth = Math.max(0, aiHealth - amount);
        updateAIHealth();
        if (gameController != null) {
            gameController.checkVictoryCondition();
        }
    }

    private void updateAIHealth() {
        if (opponentSpecialArea != null) {
            opponentSpecialArea.updateAIHealthBarAndText();
        }
    }

    public void resetAIHealth() {
        this.aiHealth = 95;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    
}
