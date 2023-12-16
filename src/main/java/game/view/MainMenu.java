package game.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import game.util.MusicPlayer;
import game.util.PersistentStorage;
import game.view.Backgrounds.AirBackground;
import game.view.Backgrounds.FireBackground;
import game.view.Backgrounds.WaterBackground;

public class MainMenu extends Application {

    private static final String POPUP_SHOWN_KEY = "popupShown";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dynamic Duel");

        if (!PersistentStorage.getBoolean(POPUP_SHOWN_KEY, false)) {
            musicPopUp(primaryStage);
            PersistentStorage.setBoolean(POPUP_SHOWN_KEY, true);
        }

        Button startButton = new Button("Start");
        Button optionsButton = new Button("Options");
        Button exitButton = new Button("Exit");
        
        startButton.setOnAction(e -> {
            Stage gameStage = new Stage();
            GameView.displayGame(gameStage);
            primaryStage.hide(); 
        });

        optionsButton.setOnAction(e -> {
            Options.displayOptions(primaryStage);
        });
        
        exitButton.setOnAction(e -> primaryStage.close());

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(startButton, optionsButton, exitButton);

        BorderPane root = new BorderPane();

        this.selectBackground(root);

        root.setCenter(vbox);

        Scene scene = new Scene(root, 800, 600);
        
        primaryStage.setScene(scene);

        primaryStage.setResizable(false);

        MusicPlayer.playMainMenuMusic();

        primaryStage.setOnHidden(e -> {
            MusicPlayer.resetIsMusicPlaying(); // Reset isMusicPlaying when returning to MainMenu
        });

        primaryStage.setOnCloseRequest(e -> {
        MusicPlayer.stopMusic();
        primaryStage.close();
        });

        primaryStage.show();
        
    }

    private String readBackground() {
        File settingsFile = new File(Settings.getFilePath());

        if (settingsFile.exists()) {
            try (Scanner scanner = new Scanner(settingsFile)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.contains("Background")) {
                        return line.split(":")[1].trim();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return "Fire"; 
    }

    private void selectBackground(BorderPane root) {
        String selectedBackground = readBackground();
        
        switch (selectedBackground) {
            case "Fire":
                FireBackground fireBackground = new FireBackground();
                root.getChildren().add(0, fireBackground);
                root.getStylesheets().add(getClass().getResource("/Styles/fire.css").toExternalForm());
                break;
    
            case "Water":
                WaterBackground waterBackground = new WaterBackground();
                root.getChildren().add(0, waterBackground);
                root.getStylesheets().add(getClass().getResource("/Styles/water.css").toExternalForm());
                break;
    
            case "Earth":
                root.getStylesheets().add(getClass().getResource("/Styles/earth.css").toExternalForm());
                break;
    
            case "Air":
                AirBackground airBackground = new AirBackground();
                root.getChildren().add(0, airBackground);
                root.getStylesheets().add(getClass().getResource("/Styles/air.css").toExternalForm());
                break;
    
            default:
                break;
        }
    }

    private void musicPopUp(Stage primaryStage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Volume Warning");
        alert.setHeaderText("Warning: Game Contains Music");
        alert.setContentText("Please adjust your volume if it's too high.");
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/Styles/Menu/volume_popup.css").toExternalForm());
        alert.setGraphic(null);
        alert.showAndWait();
    }
}
