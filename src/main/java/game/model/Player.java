package game.model;

public class Player {

    private int health;

    public Player() {
        resetHealth(); 
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(100, health)); 
    }

    public void reduceHealth(int amount) {
        this.health = Math.max(0, health - amount); 
    }

    public void resetHealth() {
        this.health = 95; 
    }
}
