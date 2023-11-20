package game.view;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu {

    public void show(Stage primaryStage) {
        // Create UI components
        Button startButton = new Button("Start Game");
        Button settingsButton = new Button("Settings");
        Button exitButton = new Button("Exit");

        // Set up button actions
        startButton.setOnAction(event -> startGame());
        settingsButton.setOnAction(event -> showSettings());
        exitButton.setOnAction(event -> Platform.exit());

        // Create layout
        VBox layout = new VBox(20);
        layout.getChildren().addAll(startButton, settingsButton, exitButton);

        // Set up the scene and show the stage
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dynamic Duel - Main Menu");
        primaryStage.show();
    }

    private void startGame() {
        // Add logic to start the actual game
        System.out.println("Starting the game!");
    }

    private void showSettings() {
        // Add logic to display settings (if needed)
        System.out.println("Displaying settings!");
    }
}
