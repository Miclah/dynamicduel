package game.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class About {

    public static final String VERSION = "0.1";
    public static final String AUTHOR = "Michal Petrán";

    public static void displayAbout(Stage primaryStage) {
        primaryStage.setTitle("Dynamic Duel");

        // Placeholder text
        Text aboutText = new Text();
        aboutText.setText("\"Dynamic Duel\"\n\n" +
                "Game Overview:\n" +
                "\"Dynamic Duel\" is a 2D card game where players aim to deplete their opponent's health by playing various cards with unique abilities.\n\n" +
                "Game Elements:\n" +
                "- **Health Cards:** Each player starts with a certain amount of health (e.g., 20 health points).\n" +
                "- **Attack Cards:** Cards with attack values that subtract health points from the opponent.\n" +
                "- **Defense Cards:** Cards that can block or reduce the damage received from an opponent's attack.\n" +
                "- **Special Cards:** Unique cards with special abilities that can alter the game dynamics (e.g., skip opponent's turn, draw extra cards, etc.).\n\n" +
                "Game Rules:\n" +
                "Players start with a hand of cards and draw one card each turn.\n" +
                "On a player's turn, they can play one card (Attack, Defense, or Special).\n" +
                "If a player plays an Attack card, the opponent loses health equal to the card's attack value.\n" +
                "Defense cards can be played to block or reduce the damage from an opponent's attack.\n" +
                "Special cards introduce strategic elements and add excitement to the game.\n" +
                "The game continues until one player's health reaches zero, declaring the other player the winner.\n\n" +
                "**Author:** " + AUTHOR + "\n" +
                "**Version:** " + VERSION);

        aboutText.setWrappingWidth(725);
        aboutText.setStyle("-fx-fill: #fff; -fx-font-size: 18; -fx-padding: 20; -fx-text-alignment: justify;");
        
        ScrollPane scrollPane = new ScrollPane(aboutText);
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED); 
        scrollPane.setStyle("-fx-background: #2c3e50; -fx-background-color: #2c3e50;");

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button");
        backButton.setOnAction(e -> {
            Options.displayOptions(primaryStage);
        });

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20)); 
        vbox.getChildren().addAll(scrollPane, backButton);

        BorderPane root = new BorderPane();
        root.setCenter(vbox);

        Scene scene = new Scene(root, 800, 600);

        scene.getStylesheets().add(About.class.getResource("/Styles/Menu/about.css").toExternalForm());

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
