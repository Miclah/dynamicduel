package game.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The Options menu of the game.
 */
public class Options {

    /**
     * Displays the Options menu.
     *
     * @param primaryStage The primary stage of the application.
     */
    public static void displayOptions(Stage primaryStage) {
        // Set the title of the stage
        primaryStage.setTitle("Dynamic Duel - Options");

        // Create buttons for different options
        Button statsButton = new Button("Player Stats");
        Button settingsButton = new Button("Settings");
        Button aboutButton = new Button("About");
        Button backButton = new Button("Back");

        // Set action event for the Settings button to display the Settings menu
        settingsButton.setOnAction(e -> {
            Settings.displaySettings(primaryStage);
        });

        // Set action event for the Player Stats button (Placeholder action)
        statsButton.setOnAction(e -> System.out.println("Player Stats button clicked"));

        // Set action event for the About button to display the About menu
        aboutButton.setOnAction(e -> {
            About.displayAbout(primaryStage);
        });

        // Set action event for the Back button to return to the main menu
        backButton.setOnAction(e -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.start(primaryStage);
        });

        // Create a vertical box to hold the buttons
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(statsButton, settingsButton, aboutButton, backButton);

        // Create a border pane as the root layout
        BorderPane root = new BorderPane();
        root.setCenter(vbox);

        // Add a stylesheet to the root for styling
        root.getStylesheets().add(Options.class.getResource("/Styles/Menu/options.css").toExternalForm());

        // Create the scene with the root layout and set the stage with the scene
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
