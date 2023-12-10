package game.model;

public class Card {
    private CardType type;
    private String imagePath;
    private boolean isFaceUp;

    public Card(CardType type, String imagePath) {
        this.type = type;
        this.imagePath = imagePath;
        this.isFaceUp = false;
    }

    public CardType getType() {
        return this.type;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public boolean isFaceUp() {
        return this.isFaceUp;
    }

    public void flip() {
        this.isFaceUp = !this.isFaceUp;
    }
}
