package game.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Options menu of the game.
 */
public class Options {

    /**
     * Displays the Options menu.
     *
     * @param primaryStage The primary stage for the JavaFX application.
     */
    public static void displayOptions(Stage primaryStage) {
        // Set the title of the stage
        primaryStage.setTitle("Dynamic Duel - Options");

        // Create buttons for different options
        Button statsButton = new Button("Player Stats");
        Button settingsButton = new Button("Settings");
        Button aboutButton = new Button("About");
        Button backButton = new Button("Back");

        // Set actions for the buttons
        settingsButton.setOnAction(e -> {
            // Display the settings menu
            Settings.displaySettings(primaryStage);
        });
        statsButton.setOnAction(e -> System.out.println("Player Stats button clicked"));
        aboutButton.setOnAction(e -> {
            // Display the "about" information
            About.displayAbout(primaryStage);
        });

        backButton.setOnAction(e -> {
            // Return to the main menu
            MainMenu mainMenu = new MainMenu();
            mainMenu.start(primaryStage);
        });

        // Create and configure the layout
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(statsButton, settingsButton, aboutButton, backButton);

        BorderPane root = new BorderPane();
        // Apply styles from the external CSS file
        root.getStylesheets().add(Options.class.getResource("/Styles/Menu/options.css").toExternalForm());

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
