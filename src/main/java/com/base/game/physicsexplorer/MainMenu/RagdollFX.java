package com.base.game.physicsexplorer.MainMenu;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Map;

public class RagdollFX extends Application {

    private Ragdoll ragdoll;
    private Group root;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ragdoll Physics Demo");
        root = new Group();
        Scene scene = new Scene(root, 800, 600, true);
        scene.setFill(Color.FLORALWHITE);

        // Create a Ragdoll instance
        ragdoll = new Ragdoll(scene.getWidth(), scene.getHeight());

        // Add all body parts to the scene
        Map<String, Ragdoll.BodyPart> bodyParts = ragdoll.getAllBodyParts();
        root.getChildren().addAll(bodyParts.values());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
