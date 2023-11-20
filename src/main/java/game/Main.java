package game;

import game.view.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Set up your main menu view and show it
        MainMenu mainMenu = new MainMenu();
        mainMenu.show(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
