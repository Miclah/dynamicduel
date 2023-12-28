package game;

import javafx.application.Application;
import javafx.stage.Stage;
import game.view.MainMenu;

/**
 * The main entry point for the Dynamic Duel game.
 */
public class Main extends Application {

    /**
     * The main method to launch the JavaFX application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Overrides the start method of Application.
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
