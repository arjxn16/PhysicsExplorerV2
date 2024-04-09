package ProjectileMotion;

import javafx.application.Application;
import javafx.scene.Group; // Import the Group class for the root node
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RagdollFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ragdoll Physics Demo");

        // Create a root node for the scene
        Group root = new Group();

        // Create the scene with the root node and specify its dimensions
        Scene scene = new Scene(root, 800, 600); // Width: 800, Height: 600
        scene.setFill(Color.FLORALWHITE);

        // Set the scene to the primary stage
        primaryStage.setScene(scene);

        // Show the primary stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
