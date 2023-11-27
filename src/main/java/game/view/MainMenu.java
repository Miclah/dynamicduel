package game.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.text.SimpleDateFormat;
import java.util.Date;

import game.view.Backgrounds.Fire.FireBackground;


public class MainMenu extends Application {

    public static final String VERSION = "0.0.1";
    public static final String AUTHOR = "Your Name";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dynamic Duel Main Menu");
        primaryStage.setResizable(false); 

        // Creating buttons
        Button startButton = new Button("Start");
        Button settingsButton = new Button("Settings");
        Button exitButton = new Button("Exit");

        // Adding styles to buttons
        startButton.getStyleClass().add("menu-button");
        settingsButton.getStyleClass().add("menu-button");
        exitButton.getStyleClass().add("menu-button");

        // Handling button actions
        startButton.setOnAction(e -> System.out.println("Start button clicked"));
        settingsButton.setOnAction(e -> System.out.println("Settings button clicked"));
        exitButton.setOnAction(e -> System.out.println("Exit button clicked"));

        // Creating a VBox (vertical box) to hold the buttons
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(startButton, settingsButton, exitButton);

        // Creating a StackPane to hold the VBox and any additional UI elements
        StackPane root = new StackPane();
        int randomBackground = (int) (Math.random() * 4);


        

        switch (randomBackground) {
            case 0:
                FireBackground fireBackground = new FireBackground();
                root.getChildren().addAll(fireBackground, vbox); // Reorder to show the background behind the buttons
                root.setStyle("-fx-background-color: #2c3e50;"); // Set background color for the entire scene
                Scene scene = new Scene(root, 800, 600);
                scene.getStylesheets().add(getClass().getResource("/Styles/Fire.css").toExternalForm());
                primaryStage.setScene(scene);
                break;
            // Add cases for other backgrounds (Water, Earth, Air) as needed
        }
        // Adding background style to the root
        root.setStyle("-fx-background-color: #2c3e50;");

        // Creating the scene
        

        // Adding external CSS for button styling


        // Setting the scene

        // Displaying the stage
        primaryStage.show();
    }

    // Helper method to get the current date
    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(new Date());
    }
}
