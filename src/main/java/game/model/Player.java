package game.model;

import game.controller.GameController;
import game.view.components.PlayerSpecial;

public class Player {

    private int health;
    private PlayerSpecial playerSpecialArea;
    private GameController gameController;

    public Player() {
        resetHealth();
    }

    public void setPlayerSpecial(PlayerSpecial playerSpecialArea) {
        this.playerSpecialArea = playerSpecialArea;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(100, health));
    }

    public void reduceHealth(int amount) {
        this.health = Math.max(0, health - amount);
        updateHealth();
        if (gameController != null) {
            gameController.checkDefeatCondition();
        }
    }

    private void updateHealth() {
        if (playerSpecialArea != null) {
            playerSpecialArea.updateHealthBarAndText();
        }
    }

    public void resetHealth() {
        this.health = 95;
    }

    public boolean isDefeated() {
        return health <= 0;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
}
