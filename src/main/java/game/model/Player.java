package game.model;

import game.controller.GameController;
import game.view.components.PlayerSpecial;

/**
 * Represents the player (you) in the game.
 */
public class Player {

    private int health;
    private PlayerSpecial playerSpecialArea;
    private GameController gameController;

    /**
     * Constructs a player with default health (100).
     */
    public Player() {
        this.resetHealth();
    }

    /**
     * Sets the special area for the player.
     *
     * @param playerSpecialArea The player's special area.
     */
    public void setPlayerSpecial(PlayerSpecial playerSpecialArea) {
        this.playerSpecialArea = playerSpecialArea;
    }

    /**
     * Gets the current health of the player.
     *
     * @return The player's health.
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Sets the health of the player.
     *
     * @param health The health value to set.
     */
    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(100, health));
    }

    /**
     * Reduces the health of the player by a specified amount.
     *
     * @param amount The amount to reduce the health by.
     */
    public void reduceHealth(int amount) {
        this.health = Math.max(0, this.health - amount);
        this.updateHealth();
        if (this.gameController != null) {
            this.gameController.checkDefeatCondition();
        }
    }

    /**
     * Updates the player's health in the UI.
     */
    private void updateHealth() {
        if (this.playerSpecialArea != null) {
            this.playerSpecialArea.updatePlayerInterface();
        }
    }

    /**
     * Resets the player's health to a default value.
     */
    private void resetHealth() {
        this.health = 100;
    }

    /**
     * Heals the player by a specified amount.
     *
     * @param amount The amount to heal the player.
     */
    public void healPlayer(int amount) {
        this.health = Math.max(0, Math.min(100, this.health + amount));
        this.updateHealth();
    }

    /**
     * Sets the game controller for the player.
     *
     * @param gameController The game controller.
     */
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
}