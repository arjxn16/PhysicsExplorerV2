package com.base.game.physicsexplorer.MainMenu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoadSavedSituation {

    public Scene createLoadSaveScene(Scene previousScene, Stage mainStage) {
        try {
            // Load the LoadSavedSituation-view.fxml file using FXMLLoader
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoadSavedSituation-view.fxml"));
            Parent root = fxmlLoader.load();

            // Create a new stage for the load game scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 640, 480));
            stage.setResizable(true);
            stage.setMinWidth(640);
            stage.setMinHeight(480);

            // Get the controller associated with the LoadSavedSituation-view.fxml
            LoadSavedSituationController loadSavedSituationController = fxmlLoader.getController();

            // Set the previous scene in the LoadGameController
            loadSavedSituationController.setPreviousScene(previousScene);

            // Set the main stage in the LoadGameController
            loadSavedSituationController.setStage(mainStage);

            // Return the scene associated with the new stage
            return stage.getScene();
        } catch (IOException e) {
            // Handle IOException by printing the stack trace
            e.printStackTrace();
            return null;
        }
    }
}