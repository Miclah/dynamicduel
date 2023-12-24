package game.model;

public class AI {

    private int aiHealth;

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
    }

    public void resetAIHealth() {
        this.aiHealth = 95;
    }
}
