package game.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import game.view.Backgrounds.FireBackground;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainMenu extends Application {

    public static final String VERSION = "0.0.1";
    public static final String AUTHOR = "Your Name";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dynamic Duel");

        // Creating buttons
        Button startButton = new Button("Start");
        Button settingsButton = new Button("Settings");
        Button exitButton = new Button("Exit");

        // Handling button actions
        startButton.setOnAction(e -> System.out.println("Start button clicked"));
        settingsButton.setOnAction(e -> System.out.println("Settings button clicked"));
        exitButton.setOnAction(e -> System.out.println("Exit button clicked"));

        // Creating a VBox (vertical box) to hold the buttons
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(startButton, settingsButton, exitButton);

        // Creating a BorderPane to organize the layout
        BorderPane root = new BorderPane();

        // Randomly select the background and apply styles
        selectBackgroundAndApplyStyles(root);

        // Set the vbox to the center of the root
        root.setCenter(vbox);

        // Creating the scene
        Scene scene = new Scene(root, 800, 600);
        
        // Setting the scene
        primaryStage.setScene(scene);

        // Displaying the stage
        primaryStage.show();

        
    }

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(new Date());
    }

    private void selectBackgroundAndApplyStyles(BorderPane root) {
        // Generate a random number between 1 and 4
        int randomBackground = 1;

        // Apply styles based on the selected background
        switch (randomBackground) {
            case 1:
                FireBackground fireBackground = new FireBackground();
                // Add a style class to FireBackground
                root.getChildren().add(0, fireBackground);
                break;
            default:
                break;
        }
    }
}
