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

        MainMenu mainMenu = new MainMenu();
        mainMenu.start(primaryStage);
        
    }
}
