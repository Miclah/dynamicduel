package game.view;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import game.util.MusicPlayer;
import game.util.PersistentStorage;
import game.view.backgrounds.AirBackground;
import game.view.backgrounds.FireBackground;
import game.view.backgrounds.WaterBackground;

/**
 * Main menu of the Dynamic Duel game.
 */
public class MainMenu extends Application {

    // Attribute for checking if the music pop-up has been shown
    private static final String POPUP_SHOWN_KEY = "popupShown";
    // Attribute for checking if the fade in has been shown
    private static boolean hasFadeInPlayed = false;

    /**
     * Starts the main menu.
     * 
     * @param primaryStage The primary stage for the JavaFX application.
     */

    public void start(Stage primaryStage) {
        // Set the title of the stage and a custom icon
        primaryStage.setTitle("Dynamic Duel");
        Image customIcon = new Image(getClass().getResourceAsStream("/Icon/icon2.png"));
        primaryStage.getIcons().add(customIcon);

        // Display music pop-up if not shown before
        if (!PersistentStorage.getBoolean(POPUP_SHOWN_KEY, false)) {
            this.musicPopUp(primaryStage);
            PersistentStorage.setBoolean(POPUP_SHOWN_KEY, true);
        }

        // Create buttons for different menu options
        Button startButton = new Button("Start");
        Button optionsButton = new Button("Options");
        Button exitButton = new Button("Exit");

        // Set actions for the buttons
        startButton.setOnAction(e -> {
            // Stop music and start the game
            MusicPlayer.stopMusic(true);
            Stage gameStage = new Stage();
            GameView.displayGame(gameStage);
            primaryStage.hide();
        });

        optionsButton.setOnAction(e -> {
            // Display the Options menu
            Options.displayOptions(primaryStage);
        });
        // Exit the application
        exitButton.setOnAction(e -> primaryStage.close());

        // Create and configure the layout
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(startButton, optionsButton, exitButton);

        BorderPane root = new BorderPane();
        // Select and set background based on user settings
        this.selectBackground(root);
        root.setCenter(vbox);

        Scene scene = new Scene(root, 800, 600);

        // Play fade-in animation only once
        if (!hasFadeInPlayed) {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), root);
            fadeTransition.setFromValue(0.25);
            fadeTransition.setToValue(1);
            fadeTransition.play();
            hasFadeInPlayed = true;
        }

        primaryStage.setScene(scene);
        // Make the window not resizable
        primaryStage.setResizable(false);
        // Play main menu music
        MusicPlayer.playMainMenuMusic();

        primaryStage.setOnHidden(e -> {
            // Reset music status when the window is hidden
            MusicPlayer.resetIsMusicPlaying();
        });

        primaryStage.setOnCloseRequest(e -> {
            // Stop music and close the application on window close (X button)
            MusicPlayer.stopMusic(true);
            primaryStage.close();
        });

        primaryStage.show();
    }

    /**
     * Reads the selected background from the settings file.
     *
     * @return The selected background name.
     */
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
        // Default background is "Fire" if not specified in settings
        return "Fire";
    }

    /**
     * Selects and sets the background based on user settings.
     *
     * @param root The root BorderPane of the main menu.
     */
    private void selectBackground(BorderPane root) {
        String selectedBackground = this.readBackground();

        switch (selectedBackground) {
            case "Fire":
                // Add FireBackground to the root and apply its style
                FireBackground fireBackground = new FireBackground();
                root.getChildren().add(0, fireBackground);
                root.getStylesheets().add(getClass().getResource("/Styles/Background/fire.css").toExternalForm());
                break;

            case "Water":
                // Add WaterBackground to the root and apply its style
                WaterBackground waterBackground = new WaterBackground();
                root.getChildren().add(0, waterBackground);
                root.getStylesheets().add(getClass().getResource("/Styles/Background/water.css").toExternalForm());
                break;

            case "Earth":
                // Earth background (not implemented)
                root.getStylesheets().add(getClass().getResource("/Styles/Background/earth.css").toExternalForm());
                break;

            case "Air":
                // Add AirBackground to the root and apply its style
                AirBackground airBackground = new AirBackground();
                root.getChildren().add(0, airBackground);
                root.getStylesheets().add(getClass().getResource("/Styles/Background/air.css").toExternalForm());
                break;

            default:
                // Default case if background is not recognized
                break;
        }
    }

    /**
     * Displays a pop-up warning about the game containing music.
     *
     * @param primaryStage The primary stage for the JavaFX application.
     */
    private void musicPopUp(Stage primaryStage) {
        // Create an informational pop-up alert about volume
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Volume Warning");
        alert.setHeaderText("Warning: Game Contains Music");
        alert.setContentText("Please adjust your volume if it's too high.");
        // Apply styles from the external CSS file
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/Styles/Menu/volume_popup.css").toExternalForm());
        // Set graphic to null to remove the default alert icon
        alert.setGraphic(null);
        alert.showAndWait();
    }
}
