package game.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class About {

    public static final String VERSION = "0.0.1";
    public static final String AUTHOR = "Your Name";

    public static void displayAbout(Stage primaryStage) {
        primaryStage.setTitle("About Dynamic Duel");

        // Create text content for the About page
        Text aboutText = new Text();
        aboutText.setText("Card Game Concept: \"Dynamic Duel\"\n\n" +
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

        aboutText.setWrappingWidth(725); // Set the maximum width for text wrapping
        aboutText.setStyle("-fx-fill: #fff; -fx-font-size: 18; -fx-padding: 20; -fx-text-alignment: justify;");
        
        ScrollPane scrollPane = new ScrollPane(aboutText);
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER); // Disable horizontal scrollbar
        scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED); // Enable vertical scrollbar
        scrollPane.setStyle("-fx-background: #2c3e50; -fx-background-color: #2c3e50;");

        // Find the vertical scrollbar and set its color
        ScrollBar vScrollBar = (ScrollBar) scrollPane.lookup(".scroll-bar:vertical");
        if (vScrollBar != null) {
            vScrollBar.setStyle("-fx-base: #FFA500;");
        }

        // Create a Back button
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button");
        backButton.setOnAction(e -> {
            // Implement logic to go back to the Settings menu
            Settings.displaySettings(primaryStage);
        });

        // Creating a VBox (vertical box) to hold the text and button
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.TOP_CENTER); // Align to the top center
        vbox.setPadding(new Insets(20)); // Add padding to the VBox
        vbox.getChildren().addAll(scrollPane, backButton);

        // Creating a BorderPane to organize the layout
        BorderPane root = new BorderPane();
        root.setCenter(vbox);

        // Apply background color
        root.setStyle("-fx-background-color: #2c3e50;");

        // Creating the scene
        Scene scene = new Scene(root, 800, 600);

        // Set the stylesheet for the scene
        scene.getStylesheets().add(About.class.getResource("/Styles/Menu/about.css").toExternalForm());

        // Setting the scene
        primaryStage.setScene(scene);

        // Displaying the stage
        primaryStage.show();
    }
}
