package game.model;

import java.util.ArrayList;
import java.util.List;

//todo: 
//fix the loading of cards
public class CardLoader {

    public static List<Card> loadFireCards() {
        return loadCards("fire", new int[]{10, 20, 30, 40, 50});
    }

    public static List<Card> loadWaterCards() {
        return loadCards("water", new int[]{15, 25});
    }

    public static List<Card> loadEarthCards() {
        return loadCards("earth", new int[]{15, 20, 25});
    }

    private static List<Card> loadCards(String category, int[] values) {
        List<Card> cards = new ArrayList<>();

        for (int value : values) {
            String imagePath = String.format("/Cards/card_textures/%s/%d_%s.png", category, value, category);
            String enumName = String.format("%s_%d", category.toUpperCase(), value);
            CardType cardType = CardType.valueOf(enumName);
            cards.add(new Card(cardType, imagePath));
        }

        return cards;
    }
}
