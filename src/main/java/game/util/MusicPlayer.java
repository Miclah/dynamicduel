package game.util;

import javafx.scene.control.Alert;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;

import java.io.File;

import game.view.Settings;

public class MusicPlayer {

    private static final String MUSIC_FILE_PATH = "src/main/resources/Music/MainMenu.mp3";
    private static MediaPlayer mediaPlayer;

    private static boolean isMusicPlaying = false;
    private static boolean isPopupShown = false;

    public static void playMainMenuMusic() {
        if (!isMusicPlaying) {
            try {
                File musicFile = new File(MUSIC_FILE_PATH);
                
                // Check if the file exists before attempting to load
                if (!musicFile.exists()) {
                    handleMusicLoadError();
                    return;
                }
    
                Media media = new Media(musicFile.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
    
                mediaPlayer.setOnReady(() -> {
                    isMusicPlaying = true;
                    setMusicVolume();
                    mediaPlayer.play();
                });
    
                mediaPlayer.setOnEndOfMedia(() -> {
                    stopMusic();  // Stop the music when it reaches the end
                });
    
                mediaPlayer.setOnError(() -> {
                    handleMusicLoadError();
                });
    
            } catch (MediaException e) {
                // Handle the exception, e.g., print an error message
                System.err.println("Error loading MainMenu.mp3: " + e.getMessage());
                handleMusicLoadError();
            }
        }
    }
    
    private static void handleMusicLoadError() {
        // Check if the pop-up has already been shown
        if (!isPopupShown) {
            isPopupShown = true;
            // For example, you can display an alert to the user:
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error loading music");
            alert.setContentText("There was an error loading the music file. Please make sure the file exists and try again.");
            alert.getDialogPane().getStylesheets().add(Settings.class.getResource("/Styles/Menu/music_popup.css").toExternalForm());
            alert.setGraphic(null);
    
            // Promptly show the alert
            alert.showAndWait();
        }
    }

    public static void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();  // Dispose the media player to release resources
            mediaPlayer = null;
        }
    }

    public static void setMusicVolume() {
        if (mediaPlayer != null) {
            int musicVolume = Settings.getMusicVolume();
            mediaPlayer.setVolume(musicVolume / 300.0);
        }
    }

    public static void resetIsMusicPlaying() {
        isMusicPlaying = false;
    }
}
