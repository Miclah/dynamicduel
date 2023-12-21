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

public class Settings {

    private static final String SETTINGS_FILE_PATH = "src/main/resources/Settings/settings.ini";

    private static String currentBackground;
    private static int currentMusicVolume;

    private static String selectedBackground;
    private static int musicVolume;

    private static Slider musicSlider;


    public static void displaySettings(Stage primaryStage) {
        primaryStage.setTitle("Dynamic Duel - Settings");

        ComboBox<String> backgroundComboBox = new ComboBox<>();
        backgroundComboBox.getItems().addAll("Fire", "Water", "Earth", "Air");

        musicSlider = new Slider(0, 100, 100);
        Label musicLabel = new Label("Music Volume:");
        Label backgroundLabel = new Label("Background:");
        Button backButton = new Button("Back");

        loadSettings(backgroundComboBox, musicSlider);

        backButton.setOnAction(e -> {
            boolean changesConfirmed = showPopUp(primaryStage, backgroundComboBox);
            if (changesConfirmed) {
                saveSettings(backgroundComboBox.getValue(), (int) musicSlider.getValue());
                loadSettings(backgroundComboBox, musicSlider);
                Options.displayOptions(primaryStage);
            }
        });

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

    public static void loadSettings(ComboBox<String> backgroundComboBox, Slider musicSlider) {
        File settingsFile = new File(SETTINGS_FILE_PATH);

        if (settingsFile.exists()) {
            try (Scanner scanner = new Scanner(settingsFile)) {
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if (line.contains("Background")) {
                            selectedBackground = line.split(":")[1].trim();
                            backgroundComboBox.setValue(selectedBackground);
                            currentBackground = selectedBackground;
                        } else if (line.contains("Music")) {
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
            backgroundComboBox.setValue("Fire");
            musicSlider.setValue(100);
        }
        MusicPlayer.setMusicVolume();
    }

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

    private static boolean showPopUp(Stage primaryStage, ComboBox<String> backgroundComboBox) {
        // Check if any changes are made

        boolean isChanged = (!backgroundComboBox.getValue().equals(currentBackground) || (int) musicSlider.getValue() != currentMusicVolume);

        if (isChanged) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Apply Changes");
            alert.setHeaderText("Are you sure you want to apply the changes?");
            alert.setContentText("Click OK to confirm, or Cancel to revert.");
            alert.getDialogPane().getStylesheets().add(Settings.class.getResource("/Styles/Menu/settings_popup.css").toExternalForm());
            alert.setGraphic(null);
            alert.initOwner(primaryStage);

            return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
        } else {
            // No changes, return true to indicate that no confirmation is needed
            return true;
        }
    }

    public static void readSettings() {
        File settingsFile = new File(SETTINGS_FILE_PATH);

        if (settingsFile.exists()) {
            try (Scanner scanner = new Scanner(settingsFile)) {
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if (line.contains("Music")) {
                            musicVolume = Integer.parseInt(line.split(":")[1].trim());
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static String getFilePath() {
        return SETTINGS_FILE_PATH;
    }

    public static int getMusicVolume() {
        readSettings();
        return musicVolume;
    }
}
