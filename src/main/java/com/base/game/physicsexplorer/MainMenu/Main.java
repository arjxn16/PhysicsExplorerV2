package com.base.game.physicsexplorer.MainMenu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Main extends Application {

    private Config config = new Config("Config.txt");

    @Override
    public void start(Stage stage) throws IOException {
        // Create an instance of the MainMenu class
        System.out.println("[Main] Starting main application scene at " + java.time.LocalDateTime.now());

        MainMenu mainMenu = new MainMenu();

        // Call createMainMenuScene to initialize the main menu scene
        Scene mainMenuScene = mainMenu.createMainMenuScene(stage);

        // Show the main menu scene on the provided stage
        showMainMenu(stage, mainMenuScene);

        // Play background audio
        String audioFilePath = getClass().getResource("backgroundmusic.wav").toExternalForm();
        System.out.println("Audio file path: " + audioFilePath); // Print audio file path for debugging
        Media media = new Media(audioFilePath);

        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnError(() -> {
            System.out.println("Media error occurred: " + mediaPlayer.getError());
            // Handle media error here
        });
        mediaPlayer.setVolume(config.loadVolumeSetting() / 100.0); // Convert percentage to 0.0-1.0 range
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO); // Rewind to the beginning
            mediaPlayer.play(); // Restart playback
        });
        mediaPlayer.play();
    }

    // Method to set the provided scene on the given stage and display it
    private void showMainMenu(Stage stage, Scene scene) {
        stage.setScene(scene);
        stage.show();
    }

    // The main method to launch the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }
}
