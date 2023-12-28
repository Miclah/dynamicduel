package game.model;

import game.controller.GameController;
import game.view.components.OpponentSpecial;

/**
 * The artificial intelligence (AI) player in the game.
 */
public class AI {

    private int aiHealth;
    private OpponentSpecial opponentSpecialArea;
    private GameController gameController;

    /**
     * Constructs an AI player with default health (100).
     */
    public AI() {
        this.resetAIHealth();
    }

    /**
     * Sets the opponent special area for the AI.
     *
     * @param opponentSpecialArea The opponent special area.
     */
    public void setOpponentSpecial(OpponentSpecial opponentSpecialArea) {
        this.opponentSpecialArea = opponentSpecialArea;
    }

    /**
     * Gets the current health of the AI.
     *
     * @return The AI's health.
     */
    public int getHealth() {
        return this.aiHealth;
    }

    /**
     * Sets the health of the AI player.
     *
     * @param aiHealth The health value to set.
     */
    public void setAIHealth(int aiHealth) {
        this.aiHealth = Math.max(0, Math.min(100, aiHealth));
    }

    /**
     * Reduces the health of the AI player by a specified amount.
     *
     * @param amount The amount to reduce the health by.
     */
    public void reduceAIHealth(int amount) {
        this.aiHealth = Math.max(0, this.aiHealth - amount);
        this.updateAIHealth();
        if (this.gameController != null) {
            this.gameController.checkVictoryCondition();
        }
    }

    /**
     * Updates the AI's health in the UI.
     */
    private void updateAIHealth() {
        if (this.opponentSpecialArea != null) {
            this.opponentSpecialArea.updateAIHealthBarAndText();
        }
    }

    /**
     * Resets the AI player's health to a default value.
     */
    public void resetAIHealth() {
        this.aiHealth = 100;
    }

    /**
     * Sets the game controller for the AI player.
     *
     * @param gameController The game controller.
     */
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    } 
}