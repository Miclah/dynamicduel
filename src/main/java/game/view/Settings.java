package game.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import game.util.MusicPlayer;

/**
 * The Settings class represents the settings menu in the game.
 */
public class Settings {

    // File path for the settings file
    private static final String SETTINGS_FILE_PATH = "src/main/resources/Settings/settings.ini";

    private static String currentBackground;
    private static int currentMusicVolume;
    private static String selectedBackground;
    private static int musicVolume;
    private static Slider musicSlider;

    /**
     * Displays the settings menu.
     *
     * @param primaryStage The primary stage of the application.
     */
    public static void displaySettings(Stage primaryStage) {
        primaryStage.setTitle("Dynamic Duel - Settings");

        // Initialize UI components
        ComboBox<String> backgroundComboBox = new ComboBox<>();
        backgroundComboBox.getItems().addAll("Fire", "Water", "Earth", "Air");

        musicSlider = new Slider(0, 100, 100);
        Label musicLabel = new Label("Music Volume:");
        Label backgroundLabel = new Label("Background:");
        Button backButton = new Button("Back");

        // Load existing settings or set default values
        loadSettings(backgroundComboBox, musicSlider);

        // Event handler for the back button
        backButton.setOnAction(e -> {
            // Prompt user to confirm changes before navigating back
            boolean changesConfirmed = showPopUp(primaryStage, backgroundComboBox);
            if (changesConfirmed) {
                // Save and apply settings, then navigate back
                saveSettings(backgroundComboBox.getValue(), (int)musicSlider.getValue());
                loadSettings(backgroundComboBox, musicSlider);
                Options.displayOptions(primaryStage);
            }
        });

        // Set up UI layout
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(backgroundLabel, backgroundComboBox, musicLabel, musicSlider, backButton);

        BorderPane root = new BorderPane();
        root.setCenter(vbox);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(Settings.class.getResource("/Styles/Menu/settings.css").toExternalForm());
        primaryStage.show();
    }

    /**
     * Loads settings from the settings file and applies them to UI components.
     *
     * @param backgroundComboBox The ComboBox for selecting background.
     * @param musicSlider       The Slider for adjusting music volume.
     */
    public static void loadSettings(ComboBox<String> backgroundComboBox, Slider musicSlider) {
        File settingsFile = new File(SETTINGS_FILE_PATH);

        if (settingsFile.exists()) {
            try (Scanner scanner = new Scanner(settingsFile)) {
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if (line.contains("Background")) {
                            // Load and set background settings
                            selectedBackground = line.split(":")[1].trim();
                            backgroundComboBox.setValue(selectedBackground);
                            currentBackground = selectedBackground;
                        } else if (line.contains("Music")) {
                            // Load and set music volume settings
                            musicVolume = Integer.parseInt(line.split(":")[1].trim());
                            musicSlider.setValue(musicVolume);
                            currentMusicVolume = musicVolume;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Set default values if the settings file does not exist
            backgroundComboBox.setValue("Fire");
            musicSlider.setValue(100);
        }
        MusicPlayer.setMusicVolume();
    }

    /**
     * Saves the current settings to the settings file.
     *
     * @param selectedBackground The selected background setting.
     * @param musicVolume        The music volume setting.
     */
    private static void saveSettings(String selectedBackground, int musicVolume) {
        try {
            File file = new File(SETTINGS_FILE_PATH);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            writer.write("[Settings]\nBackground : " + selectedBackground + "\nMusic : " + musicVolume);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays a confirmation dialog to the user before applying changes.
     *
     * @param primaryStage       The primary stage of the application.
     * @param backgroundComboBox The ComboBox for selecting background.
     * @return True if changes are confirmed, false otherwise.
     */
    private static boolean showPopUp(Stage primaryStage, ComboBox<String> backgroundComboBox) {
        boolean isChanged = (!backgroundComboBox.getValue().equals(currentBackground) || (int)musicSlider.getValue() != currentMusicVolume);

        if (isChanged) {
            // Display confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Apply Changes");
            alert.setHeaderText("Are you sure you want to apply the changes?");
            alert.setContentText("Click OK to confirm, or Cancel to revert.");
            alert.getDialogPane().getStylesheets().add(Settings.class.getResource("/Styles/Menu/settings_popup.css").toExternalForm());
            alert.setGraphic(null);
            alert.initOwner(primaryStage);

            // Return user's choice
            return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
        } else {
            // If no changes, return true directly
            return true;
        }
    }

    /**
     * Reads music volume settings from the settings file.
     */
    public static void readSettings() {
        File settingsFile = new File(SETTINGS_FILE_PATH);

        if (settingsFile.exists()) {
            try (Scanner scanner = new Scanner(settingsFile)) {
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if (line.contains("Music")) {
                            // Load music volume setting
                            musicVolume = Integer.parseInt(line.split(":")[1].trim());
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets the file path for the settings file.
     *
     * @return The file path for the settings file.
     */
    public static String getFilePath() {
        return SETTINGS_FILE_PATH;
    }

    /**
     * Gets the music volume setting.
     *
     * @return The music volume setting.
     */
    public static int getMusicVolume() {
        // Read and return the music volume setting
        readSettings();
        return musicVolume;
    }
}
