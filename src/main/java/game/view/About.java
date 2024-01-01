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

/**
 * About displays information about the game, including rules, game elements, version and author.
 * It provides a scrollable view for the detailed information.
 */
public class About {

    private static final String VERSION = "0.8";
    private static final String AUTHOR = "Michal Petrán";

    /**
     * Displays the "About" information in a new window.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     */
    public static void displayAbout(Stage primaryStage) {
        // Set the title for the about window
        primaryStage.setTitle("Dynamic Duel - About");

        // Create a Text object to display the game information
        Text aboutText = new Text();
        aboutText.setText("Dynamic Duel\n\n" +
            "Game Overview:\n" +
            "Dynamic Duel is a tactical 2D card game made for two players. The goal is to lower your opponent's health using various cards, each with its own special abilities.\n\n" +
            "Game Elements:\n" +
            "- Health Cards: At the start, each player gets 100 health points.\n" +
            "- Attack Cards: These cards take away health points from your opponent.\n" +
            "- Defense Cards: Use these cards to block or reduce damage from your opponent.\n" +
            "- Special Cards: Rare and powerful, these cards can change the game. They can do things like skip a turn or let you draw extra cards.\n" +
            "- Draw Rule: Players can only draw one card per turn, so choose wisely!\n" +
            "- Hand Limit: You can have a maximum of 5 cards in your hand at the end of each turn.\n" +
            "- Special Player and AI Cards: Besides regular special cards, players and the computer have unique cards that can change the game. For example, a special player card might save you from losing by restoring 10 health and letting you draw another card.\n\n" +
            "Game Rules:\n" +
            "Players start with 5 cards and draw one card at the beginning of each turn.\n" +
            "During your turn, you can play one card—Attack, Defense, or Special.\n" +
            "If you play an Attack card, your opponent loses health equal to the card's attack value. If he does not block it by playing a defense card.\n" +
            "Defense cards help you block or reduce damage from your opponent's attack.\n" +
            "Special cards add strategic twists and excitement to the game.\n" +
            "The game goes on until one player's health drops to zero, making the other player the winner.\n\n" +
            "Author: " + AUTHOR + "\n" +
            "Version: " + VERSION);

        // Set text properties (wrap width, styles)
        aboutText.setWrappingWidth(725);
        aboutText.setStyle("-fx-fill: #fff; -fx-font-size: 18; -fx-padding: 20; -fx-text-alignment: justify;");
        
        // Create a ScrollPane to allow scrolling through the long text
        ScrollPane scrollPane = new ScrollPane(aboutText);
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED); 
        scrollPane.setStyle("-fx-background: #2c3e50; -fx-background-color: #2c3e50;");

        // Create a "Back" button to return to the options menu
        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button");
        backButton.setOnAction(e -> {
            Options.displayOptions(primaryStage);
        });

        // Create a VBox to organize the content vertically
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20));
        // Add the scrollPane and backButton to the VBox
        vbox.getChildren().addAll(scrollPane, backButton);

        // Create a BorderPane to organize the content
        BorderPane root = new BorderPane();
        root.setCenter(vbox);

        // Create a Scene with the BorderPane as the root and set the style
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(About.class.getResource("/Styles/Menu/about.css").toExternalForm());

        // Set the scene to the primaryStage and show it
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
