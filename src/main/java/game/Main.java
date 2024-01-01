package game;

import javafx.application.Application;
import javafx.stage.Stage;
import game.view.MainMenu;

/**
 * The main entry point for the Dynamic Duel game.
 * @author Michal Petrán
 * @version 0.8
 */
public class Main extends Application {

     /**
     * Not used in JavaFX application
     * 
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args, Stage primaryStage) {
        // Launch the JavaFX application

        MainMenu mainMenu = new MainMenu();
        // Start the main menu and pass the primary stage
        mainMenu.start(primaryStage);
        launch(args);
    }

    /**
     * Starts the game
     *
     * @param primaryStage The primary stage for the JavaFX application.
     */
    @Override
    public void start(Stage primaryStage) {
        // Create the main menu instance
        MainMenu mainMenu = new MainMenu();
        // Start the main menu and pass the primary stage
        mainMenu.start(primaryStage);
    }
}
