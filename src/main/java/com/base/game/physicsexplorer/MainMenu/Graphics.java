package com.base.game.physicsexplorer.MainMenu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Graphics {
    public Scene createGraphicsScene(Scene previousScene, Stage mainStage) {
        try {
            // Load the Graphics-view.fxml file using FXMLLoader
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Graphics-view.fxml"));
            Parent root = fxmlLoader.load();

            // Create a new stage for the exit scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 640, 480));
            stage.setResizable(true);
            stage.setMinWidth(640);
            stage.setMinHeight(480);

            // Get the controller associated with the GraphicsController-view.fxml
            GraphicsController graphicsController = fxmlLoader.getController();

            // Set the previous scene in the GraphicsController
            graphicsController.setPreviousScene(previousScene);

            // Set the main stage in the GraphicsController
            graphicsController.setStage(mainStage);

            // Return the scene associated with the new stage
            return stage.getScene();
        } catch (IOException e) {
            // Handle IOException by printing the stack trace
            e.printStackTrace();
            return null;
        }
    }
}