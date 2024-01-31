package com.base.game.physicsexplorer.MainMenu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LearnMore {

    // Method to create the about scene
    public Scene createAboutScene(Scene previousScene, Stage mainStage) {
        try {
            // Load the LearnMore-view.fxml file using FXMLLoader
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LearnMore-view.fxml"));
            Parent root = fxmlLoader.load();

            // Create a new stage for the about scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 640, 480));
            stage.setResizable(true);
            stage.setMinWidth(640);
            stage.setMinHeight(480);

            // Get the controller associated with the LearnMore-view.fxml
            LearnMoreController learnMoreController = fxmlLoader.getController();

            // Set the previous scene in the AboutController
            learnMoreController.setPreviousScene(previousScene);

            // Set the main stage in the AboutController
            learnMoreController.setStage(mainStage);

            // Return the scene associated with the new stage
            return stage.getScene();
        } catch (IOException e) {
            // Handle IOException by printing the stack trace
            e.printStackTrace();
            return null;
        }
    }
}
