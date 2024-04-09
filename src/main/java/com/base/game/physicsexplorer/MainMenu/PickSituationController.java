package com.base.game.physicsexplorer.MainMenu;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PickSituationController {

    // Instances of various scenes and functionalities for the main menu
    private Scene previousScene;
    private Stage stage; // Add a stage variable

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
    private void onps2ButtonClick()  {


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
}
