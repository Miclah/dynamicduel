package game.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;

import java.io.File;

import game.view.Settings;

public class MusicPlayer {

    private static final String MUSIC_FILE_PATH = "src/main/resources/Music/MainMenu.mp3";
    private static MediaPlayer mediaPlayer;

    private static boolean isMusicPlaying = false;

    public static void playMainMenuMusic() {
        if (!isMusicPlaying) {
            try {
                Media media = new Media(new File(MUSIC_FILE_PATH).toURI().toString());
                mediaPlayer = new MediaPlayer(media);

                mediaPlayer.setOnReady(() -> {
                    System.out.println("Media duration: " + media.getDuration());
                    mediaPlayer.play();
                    isMusicPlaying = true;
                });

                mediaPlayer.setOnEndOfMedia(() -> {
                    stopMusic();  // Stop the music when it reaches the end
                });
            } catch (MediaException e) {
                // Handle the exception, e.g., print an error message
                System.err.println("Error loading MainMenu.mp3: " + e.getMessage());
                // Optionally, provide fallback behavior or notify the user
            }
        }
    }

    public static void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();  // Dispose the media player to release resources
        }
    }

    public static void setMusicVolume() {
        if (mediaPlayer != null) {
            int musicVolume = Settings.musicVolume;
            mediaPlayer.setVolume(musicVolume / 100.0);
            System.out.println("Music volume set to: " + musicVolume);
        }
    }

    public static void resetIsMusicPlaying() {
        isMusicPlaying = false;
    }
}
