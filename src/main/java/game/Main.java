package game;

import javafx.application.Application;
import javafx.stage.Stage;
import game.view.MainMenu;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create an instance of the MainMenu class
        MainMenu mainMenu = new MainMenu();

        // Call the start method of the MainMenu instance
        mainMenu.start(primaryStage);
    }
}
