package com.base.game.physicsexplorer.MainMenu;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class GraphicsController {
    // Instance variables
    private final Config config; // Configuration manager
    private final String[] resolutions = {"640 x 480", "800 x 600", "1280 x 720", "1920 x 1080"}; // Available resolutions
    private Scene previousScene; // Reference to the previous scene
    private Stage stage; // Reference to the stage
    private int currentResolutionIndex = 0; // Index of the current resolution

    // FXML components
    @FXML private Button resolutionButton; // Button for changing resolution
    @FXML private CheckBox fullscreenCheckBox; // Checkbox for toggling fullscreen mode

    // Constructor
    public GraphicsController() {
        this.config = new Config("config.txt"); // Initialize configuration manager
        System.out.println("[GraphicsController] Constructor called");
    }

    // Initialize method called by JavaFX
    @FXML
    public void initialize() {
        System.out.println("[GraphicsController] Initialize method called");
        loadGraphicsConfig(); // Load graphics settings from config.txt
        applyFullScreen(); // Apply fullscreen setting
        applyResolution(resolutions[currentResolutionIndex]); // Apply resolution setting
        updateResolutionButtonText(); // Update resolution button text
    }

    // Setter for previous scene
    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }

    // Setter for stage
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Event handler for changing resolution
    @FXML
    private void handleChangeResolution() {
        System.out.println("[GraphicsController] handleChangeResolution called");
        currentResolutionIndex = (currentResolutionIndex + 1) % resolutions.length;
        updateResolutionButtonText(); // Update resolution button text
        applyResolution(resolutions[currentResolutionIndex]); // Apply new resolution
    }

    // Update resolution button text
    private void updateResolutionButtonText() {
        resolutionButton.setText("Screen Resolution: " + resolutions[currentResolutionIndex]);
    }

    // Apply selected resolution
    private void applyResolution(String resolution) {
        System.out.println("[GraphicsController] applyResolution called with resolution: " + resolution);
        if (stage != null) {
            String[] parts = resolution.split(" x ");
            if (parts.length == 2) {
                int width = Integer.parseInt(parts[0].trim());
                int height = Integer.parseInt(parts[1].trim());
                stage.setWidth(width); // Set stage width
                stage.setHeight(height); // Set stage height
            }
        }
    }

    // Apply fullscreen setting
    public void applyFullScreen() {
        System.out.println("[GraphicsController] applyFullScreen called");
        if (stage != null) {
            boolean isFullScreen = fullscreenCheckBox.isSelected(); // Get checkbox state
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            stage.setFullScreenExitHint("");
            stage.setFullScreen(isFullScreen); // Apply fullscreen setting
        }
    }

    // Save graphics configuration to file
    private void saveGraphicsConfig() {
        if (config != null) {
            new Thread(() -> {
                System.out.println("[GraphicsController] config is not null, saving settings");
                String resolution = resolutions[currentResolutionIndex];
                boolean isFullscreen = fullscreenCheckBox.isSelected(); // Get checkbox state
                config.saveResolutionSetting(resolution); // Save resolution setting
                config.saveFullScreenSetting(isFullscreen); // Save fullscreen setting
            }).start();
        } else {
            System.out.println("[GraphicsController] config is null");
        }
    }

    // Load graphics configuration from file
    private void loadGraphicsConfig() {
        System.out.println("[GraphicsController] loadGraphicsConfig called");
        boolean isFullscreen = config.loadFullscreenSetting(); // Load fullscreen setting
        String resolution = config.loadResolutionSetting(); // Load resolution setting
        if (fullscreenCheckBox != null) {
            fullscreenCheckBox.setSelected(isFullscreen); // Set checkbox state
        }
        currentResolutionIndex = java.util.Arrays.asList(resolutions).indexOf(resolution); // Find index of resolution
        currentResolutionIndex = currentResolutionIndex == -1 ? 0 : currentResolutionIndex;
        updateResolutionButtonText(); // Update resolution button text
        applyResolution(resolutions[currentResolutionIndex]); // Apply resolution
    }

    // Event handler for return button
    @FXML
    private void onReturnButtonClick() {
        System.out.println("[GraphicsController] onReturnButtonClick called");
        saveGraphicsConfig(); // Save graphics configuration
        if (previousScene != null && stage != null) {
            stage.setScene(previousScene); // Switch to previous scene
        }
    }
}
