package game.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import game.view.Backgrounds.AirBackground;
import game.view.Backgrounds.FireBackground;
import game.view.Backgrounds.WaterBackground;

public class MainMenu extends Application {

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

        primaryStage.setResizable(false);
        // Displaying the stage
        primaryStage.show();

        
    }

    private void selectBackgroundAndApplyStyles(BorderPane root) {
        // Generate a random number between 1 and 4
        int randomBackground = 2;

        // Apply styles based on the selected background
        switch (randomBackground) {
            case 1:
                FireBackground fireBackground = new FireBackground();
                root.getChildren().add(0, fireBackground);
                root.setStyle("-fx-background-color: #2c3e50;");
                root.getStylesheets().add(getClass().getResource("/Styles/fire.css").toExternalForm());
                break;
            case 2:
                WaterBackground waterBackground = new WaterBackground();
                root.getChildren().add(0, waterBackground);
                root.setStyle("-fx-background-color: #3498db;");
                root.getStylesheets().add(getClass().getResource("/Styles/water.css").toExternalForm());
                break;
            case 3:
                root.setStyle("-fx-background-color: #8B4513;");
                root.getStylesheets().add(getClass().getResource("/Styles/earth.css").toExternalForm());
                break;
            case 4:
                AirBackground airBackground = new AirBackground();
                root.getChildren().add(0, airBackground);
                root.setStyle("-fx-background-color: #87CEEB;"); 
                root.getStylesheets().add(getClass().getResource("/Styles/air.css").toExternalForm());
                break;
            default:
                break;
        }
    }
}
