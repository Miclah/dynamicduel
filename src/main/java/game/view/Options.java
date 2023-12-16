package game.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Options {

    public static void displayOptions(Stage primaryStage) {
        primaryStage.setTitle("Dynamic Duel - Options");

        Button statsButton = new Button("Player Stats");
        Button settingsButton = new Button("Settings");
        Button aboutButton = new Button("About");
        Button backButton = new Button("Back");

        settingsButton.setOnAction(e -> {
            Settings.displaySettings(primaryStage);
        });
        statsButton.setOnAction(e -> System.out.println("Player Stats button clicked"));
        aboutButton.setOnAction(e -> {
            About.displayAbout(primaryStage);
        });
        
        backButton.setOnAction(e -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.start(primaryStage);
        });

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(statsButton, settingsButton, aboutButton, backButton);

        BorderPane root = new BorderPane();

        root.setCenter(vbox);
        
        root.getStylesheets().add(Options.class.getResource("/Styles/Menu/options.css").toExternalForm());

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
