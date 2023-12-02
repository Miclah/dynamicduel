package game.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Settings {

    public static void displaySettings(Stage primaryStage) {
        primaryStage.setTitle("Dynamic Duel");

        // Creating buttons for the Settings menu
        Button statsButton = new Button("Player Stats");
        Button settingsButton = new Button("Settings");
        Button aboutButton = new Button("About");
        Button backButton = new Button("Back");

        // Handling button actions
        settingsButton.setOnAction(e -> System.out.println("General Settings button clicked"));
        statsButton.setOnAction(e -> System.out.println("Player Stats button clicked"));
        aboutButton.setOnAction(e -> {
            // Display the About page
            About.displayAbout(primaryStage);
        });
        
        backButton.setOnAction(e -> {
            // Implement logic to go back to the Main Menu
            MainMenu mainMenu = new MainMenu();
            mainMenu.start(primaryStage);
        });

        // Creating a VBox (vertical box) to hold the buttons
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(statsButton, settingsButton, aboutButton, backButton);

        // Creating a BorderPane to organize the layout
        BorderPane root = new BorderPane();

        root.setCenter(vbox);
        
        root.getStylesheets().add(Settings.class.getResource("/Styles/Menu/settings.css").toExternalForm());

        // Creating the scene
        Scene scene = new Scene(root, 800, 600);
        root.setStyle("-fx-background-color: #2c3e50;");
        // Setting the scene
        primaryStage.setScene(scene);
        
        // Displaying the stage
        primaryStage.show();
    }
}
