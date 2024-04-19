package com.base.game.physicsexplorer.MainMenu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProjectileMotionFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Projectile Motion Simulator");

        // Load the FXML file
        Parent root = FXMLLoader.load(getClass().getResource("ProjectileMotion.fxml"));

        // Create the scene with the root layout container
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);

        // Show the primary stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


        public Scene createProjectileMotionScene(Scene previousScene, Stage stage) throws IOException {
            // Load the Projectile Motion scene FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProjectileMotion.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            // Add any additional scene setup or initialization here
            return scene;
        }
    }


