package com.base.game.physicsexplorer.MainMenu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        // Create an instance of the MainMenu class
        System.out.println("[Main] Starting main application scene at " + java.time.LocalDateTime.now());

        MainMenu mainMenu = new MainMenu();

        // Call createMainMenuScene to initialize the main menu scene
        Scene mainMenuScene = mainMenu.createMainMenuScene(stage);

        // Show the main menu scene on the provided stage
        showMainMenu(stage, mainMenuScene);
    }

    // Method to set the provided scene on the given stage and display it
    private void showMainMenu(Stage stage, Scene scene) {
        stage.setScene(scene);
        stage.show();
    }
    private static void applySettingsToStage(Stage stage, Config config) {
        // Load the resolution setting from the config and split it into width and height
        String resolution = config.loadResolutionSetting();
        String[] parts = resolution.split(" x ");
        if (parts.length == 2) {
            // Parse and apply width and height to the stage
            stage.setWidth(Integer.parseInt(parts[0]));
            stage.setHeight(Integer.parseInt(parts[1]));
        }
        // Set the full screen exit hint to an empty string (no hint displayed)
        stage.setFullScreenExitHint("");
        // Disable the default full screen exit key combination
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        // Apply the fullscreen setting from the config
        stage.setFullScreen(config.loadFullscreenSetting());
    }

    // The main method to launch the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }
}