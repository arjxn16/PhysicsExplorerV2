package com.base.game.physicsexplorer.MainMenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class ProjectileMotionFXController {
    @FXML
    private ToggleGroup gravityToggleGroup;
    private ToggleGroup massToggleGroup;


    @FXML
    private Canvas simulationCanvas;

    // Add any other UI components here

    // Initialize method (you can use @FXML initialize() or constructor)

    // Method to draw the projectile motion simulation on the canvas
    private void drawSimulation() {
        GraphicsContext gc = simulationCanvas.getGraphicsContext2D();

        // Clear the canvas
        gc.clearRect(0, 0, simulationCanvas.getWidth(), simulationCanvas.getHeight());

        // Set the background color
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, simulationCanvas.getWidth(), simulationCanvas.getHeight());

        // Draw your simulation graphics here
        // Example: draw a line representing the trajectory of the projectile
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);
        gc.strokeLine(50, 200, 750, 200); // Example line from (50, 200) to (750, 200)
    }

    // Method to handle button clicks or other events to start the simulation
    private boolean simulationRunning = false; //defines the simulation state

    @FXML
    private void startSimulation() {
        if (!simulationRunning) {
            simulationRunning = true;
            // Add logic to start the simulation
            System.out.println("Simulation started");
            drawSimulation(); // Call drawSimulation to update the canvas
        } else {
            System.out.println("Simulation is already running");
        }
    }


    @FXML
    private void stopSimulation() {
        if (simulationRunning) {
            simulationRunning = false;
            // Add logic to stop the simulation
            System.out.println("Simulation stopped");
        } else {
            System.out.println("Simulation is not running");
        }
    }

    @FXML
    private void resetSimulation() {
        // Add logic to reset the simulation
        if (simulationRunning) {
            // If the simulation is running, stop it first
            stopSimulation();
        }
        // Then add logic to reset the simulation
        System.out.println("Simulation reset");
    }

    public void onKeyPressed(KeyEvent keyEvent) {
    }

    public void mouseDragged(MouseEvent mouseEvent) {
    }

    public void mousePressed(MouseEvent mouseEvent) {
    }

    public void mouseReleased(MouseEvent mouseEvent) {
    }

    public void onRollbackClick(ActionEvent actionEvent) {
    }

    public void onStartSimulationClick(ActionEvent actionEvent) {
    }

    public void onStepForwardClick(ActionEvent actionEvent) {
    }

    public void onReturnToHomeButtonClick(ActionEvent actionEvent) {
    }
}
