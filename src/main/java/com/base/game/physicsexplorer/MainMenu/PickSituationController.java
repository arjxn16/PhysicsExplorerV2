package com.base.game.physicsexplorer.MainMenu;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;

public class PickSituationController {

    // Instances of various scenes and functionalities for the main menu
    private Scene previousScene;
    private Stage stage; // Add a stage variable
    private Scene projectileMotionScene;

    // Method to set the previous scene
    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }

    // Method to set the main stage
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Method to update the title of the main stage
    private void updateTitle(String newTitle) {
        if (stage != null) {
            stage.setTitle(newTitle);
        }
    }


    @FXML
    private void onps2ButtonClick() throws IOException {
        if (projectileMotionScene == null) {
            // Load the Projectile Motion scene if not loaded
            projectileMotionScene = new ProjectileMotionFX().createProjectileMotionScene(stage.getScene(), stage);
        }
        // Create a new Config object
        Config config = new Config("config.txt");
        // Apply settings to the stage
        applySettingsToStage(stage, config);
        updateTitle("Projectile Motion");
        stage.setScene(projectileMotionScene);

    }



    // Event handler for the "Return" button
    @FXML
    public void onReturnButtonClick() {
        // Print the previous scene and stage for debugging purposes
        System.out.println("Previous Scene: " + previousScene);
        System.out.println("Stage: " + stage);

        // Check if both previous scene and stage are not null
        if (previousScene != null && stage != null) {
            // Set the main stage's scene back to the previous scene
            stage.setScene(previousScene);
            updateTitle("Main Menu");
        }
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
}