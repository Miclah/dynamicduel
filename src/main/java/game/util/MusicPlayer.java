package game.util;

import javafx.scene.control.Alert;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

import game.view.Settings;

/**
 * Utility class for controlling music playback in the game.
 */
public class MusicPlayer {

    // Paths to music files
    private static final String MAIN_MENU = "src/main/resources/Music/MainMenu.mp3";
    private static final String GAME_1 = "src/main/resources/Music/Game1.mp3";
    //private static final String GAME_2 = "src/main/resources/Music/Game2.mp3";

    private static MediaPlayer mediaPlayer;
    private static Media media; 

    private static boolean isMusicPlaying = false; // Boolean indicating whether music is currently playing
    private static boolean isPopupShown = false; // Boolean indicating whether the music error popup has been shown
    private static boolean isGameMusicLooping = false; // Boolean indicating whether game music is set to loop

    /**
     * Plays the main menu music.
     */
    public static void playMainMenuMusic() {
        stopMusic(false);
        disposeMediaPlayer();
        playMusic(MAIN_MENU);
    }

    /**
     * Plays the first game music.
     */
    public static void playGameMusic1() {
        stopMusic(false);
        disposeMediaPlayer();
        playMusic(GAME_1);
        setGameMusicLooping(true, GAME_1);
    }

   /*public static void playGameMusic2() {
        playMusic(GAME_2);
        setGameMusicLooping(true, GAME_1);
    }*/

    /**
     * Plays music from the specified path.
     *
     * @param musicPath The path of the music file.
     */
    private static void playMusic(String musicPath) {
        if (!isMusicPlaying) {
            try {
                File musicFile = new File(musicPath);

                if (!musicFile.exists()) {
                    handleMusicLoadError();
                    return;
                }

                // Create a new Media object representing the audio file
                media = new Media(musicFile.toURI().toString());
                // Create a new MediaPlayer object for controlling playback of the audio media
                mediaPlayer = new MediaPlayer(media);

                // Set an event handler for when the end of the media is reached
                mediaPlayer.setOnEndOfMedia(() -> {
                    stopMusic(false);
                });

                // Set an event handler for handling errors during media playback
                mediaPlayer.setOnError(() -> {
                    handleMusicLoadError();
                });

                // Set the boolean to indicate that music is now playing
                isMusicPlaying = true;
                // Adjust the music volume based on settings
                setMusicVolume();
                // Start playing the music
                mediaPlayer.play();

            } catch (MediaException e) {
                // Handle exceptions related to media
                handleMusicLoadError();
            }
        }
    }

    /**
     * Sets up music looping if specified.
     *
     * @param loop      Whether to loop the music.
     * @param nextMusic The path of the next music file.
     */
    private static void setGameMusicLooping(boolean loop, String nextMusic) {
        if (!isGameMusicLooping && mediaPlayer != null) {
            isGameMusicLooping = true;

            // Set an event handler for looping the music
            mediaPlayer.setOnEndOfMedia(() -> {
                if (loop) {
                    mediaPlayer.seek(Duration.ZERO);
                    mediaPlayer.play();
                }
            });
        }
    }

    /**
     * Handles errors during music loading and displays an error alert.
     */
    private static void handleMusicLoadError() {
        if (!isPopupShown) {
            isPopupShown = true;
            // Create and display an error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error loading music");
            alert.setContentText("There was an error loading the music file. Please make sure the file exists and try again.");
            // Apply styles from the external CSS file
            alert.getDialogPane().getStylesheets().add(Settings.class.getResource("/Styles/Menu/music_popup.css").toExternalForm());
             // Set graphic to null to remove the default alert icon
            alert.setGraphic(null); 

            alert.showAndWait();
        }
    }

    /**
     * Stops the currently playing music.
     *
     * @param intentionalStop Indicates whether the stop is intentional.
     */
    public static void stopMusic(boolean intentionalStop) {
        if (mediaPlayer != null) {
            // Stop the media playback
            mediaPlayer.stop();
            if (!intentionalStop) {
                // Reset the boolean indicating whether music is playing if the stop was not intentional
                resetIsMusicPlaying();
            }
        }
    }

    /**
     * Disposes of the media player to free resources.
     */
    public static void disposeMediaPlayer() {
        if (mediaPlayer != null) {
            // Dispose of the media player
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }

    /**
     * Sets the music volume based on the user settings.
     */
    public static void setMusicVolume() {
        if (mediaPlayer != null) {
            // Get the music volume setting from user preferences
            int musicVolume = Settings.getMusicVolume();
            // Set the volume of the media player based on the user setting
            mediaPlayer.setVolume(musicVolume / 500.0);
        }
    }

    /**
     * Resets the boolean indicating whether music is currently playing.
     */
    public static void resetIsMusicPlaying() {
        isMusicPlaying = false;
    }
}
