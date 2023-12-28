package game.util;

import javafx.scene.control.Alert;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

import game.view.Settings;

public class MusicPlayer {

    private static final String MAIN_MENU = "src/main/resources/Music/MainMenu.mp3";
    private static final String GAME_1 = "src/main/resources/Music/Game1.mp3";
    //private static final String GAME_2 = "src/main/resources/Music/Game2.mp3";
    private static MediaPlayer mediaPlayer;
    private static Media media; 

    private static boolean isMusicPlaying = false;
    private static boolean isPopupShown = false;
    private static boolean isGameMusicLooping = false;

    public static void playMainMenuMusic() {
        stopMusic(false);
        disposeMediaPlayer();
        playMusic(MAIN_MENU);
    }
    
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

    private static void playMusic(String musicPath) {
        System.out.println("Attempting to play music: " + musicPath);
        if (!isMusicPlaying) {
            try {
                File musicFile = new File(musicPath);
    
                if (!musicFile.exists()) {
                    handleMusicLoadError();
                    return;
                }
    
                media = new Media(musicFile.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
    
                mediaPlayer.setOnEndOfMedia(() -> {
                    stopMusic(false);
                });
    
                mediaPlayer.setOnError(() -> {
                    handleMusicLoadError();
                });
    
                isMusicPlaying = true;
                setMusicVolume();
                mediaPlayer.play();
    
            } catch (MediaException e) {
                handleMusicLoadError();
            }
        }
    }
    
    private static void setGameMusicLooping(boolean loop, String nextMusic) {
        if (!isGameMusicLooping && mediaPlayer != null) {
            isGameMusicLooping = true;
    
            mediaPlayer.setOnEndOfMedia(() -> {
                if (loop) {
                    mediaPlayer.seek(Duration.ZERO);
                    mediaPlayer.play();
                }
            });
        }
    }
    
    
    private static void handleMusicLoadError() {
        if (!isPopupShown) {
            isPopupShown = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error loading music");
            alert.setContentText("There was an error loading the music file. Please make sure the file exists and try again.");
            alert.getDialogPane().getStylesheets().add(Settings.class.getResource("/Styles/Menu/music_popup.css").toExternalForm());
            alert.setGraphic(null);

            alert.showAndWait();
        }
    }

    public static void stopMusic(boolean intentionalStop) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            if (!intentionalStop) {
                resetIsMusicPlaying();
            }
        }
    }
    
    public static void disposeMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.dispose();
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
