package game.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

public class Settings {

    public static final String SETTINGS_FILE_PATH = "src/main/resources/Settings/settings.ini";


    public static void displaySettings(Stage primaryStage) {
        primaryStage.setTitle("Settings");

        ComboBox<String> backgroundComboBox = new ComboBox<>();
        backgroundComboBox.getItems().addAll("Fire", "Water", "Earth", "Air");

        Slider musicSlider = new Slider(0, 100, 100);
        Label musicLabel = new Label("Music Volume:");
        Label backgroundLabel = new Label("Background:");
        Button backButton = new Button("Back");

        backButton.setOnAction(e -> {
            boolean changesConfirmed = showConfirmationPopup(primaryStage);
            if (changesConfirmed) {
                saveSettings(backgroundComboBox.getValue(), (int) musicSlider.getValue());
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

        loadSettings(backgroundComboBox, musicSlider);
        primaryStage.show();
    }

    private static void loadSettings(ComboBox<String> backgroundComboBox, Slider musicSlider) {
        File settingsFile = new File(SETTINGS_FILE_PATH);
    
        if (settingsFile.exists()) {
            try (Scanner scanner = new Scanner(settingsFile)) {
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if (line.contains("Background")) {
                            String selectedBackground = line.split(":")[1].trim();
                            backgroundComboBox.setValue(selectedBackground);
                        } else if (line.contains("Music")) {
                            int musicVolume = Integer.parseInt(line.split(":")[1].trim());
                            musicSlider.setValue(musicVolume);
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
    }

    private static void saveSettings(String selectedBackground, int musicVolume) {
        try {
            File file = new File(SETTINGS_FILE_PATH);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            writer.write("[Settings]\nBackground : " + selectedBackground + "\nMusic : " + musicVolume + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean showConfirmationPopup(Stage primaryStage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Apply Changes");
        alert.setHeaderText("Are you sure you want to apply the changes?");
        alert.setContentText("Click OK to confirm, or Cancel to revert.");
    
        alert.initOwner(primaryStage);
    
        return alert.showAndWait().filter(response -> response == javafx.scene.control.ButtonType.OK).isPresent();
    }
}
