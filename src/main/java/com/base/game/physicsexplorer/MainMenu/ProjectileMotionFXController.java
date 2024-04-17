package com.base.game.physicsexplorer.MainMenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ProjectileMotionFXController {

    public ToggleGroup massToggleGroup2;
    public ToggleGroup massToggleGroup1;
    public Button startSimulationButton;
    public Button stopSimulationButton;
    public Button resetSimulationButton;
    @FXML
    private TextField initialHeightTextField;

    @FXML
    private TextField initialVelocityTextField;

    @FXML
    private TextField initialAngleTextField;

    @FXML
    private RadioButton lightMassRadioButton;

    @FXML
    private RadioButton heavyMassRadioButton;

    @FXML
    private RadioButton earthGravityRadioButton;

    @FXML
    private RadioButton marsGravityRadioButton;

    @FXML
    private RadioButton mercuryGravityRadioButton;

    @FXML
    private RadioButton saturnGravityRadioButton;

    @FXML
    private Canvas simulationCanvas;

    private Projectile projectile;
    private boolean simulationRunning = false;

    @FXML
    private void startSimulation() {
        if (!simulationRunning) {
            simulationRunning = true;
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
        // Stop the simulation if it's running
        stopSimulation();

        // Reset the projectile to default values
        projectile.resetToDefault();

        // Clear the canvas
        GraphicsContext gc = simulationCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, simulationCanvas.getWidth(), simulationCanvas.getHeight());

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



    public void onStepForwardClick(ActionEvent actionEvent) {
    }

    public void onReturnToHomeButtonClick(ActionEvent actionEvent) {
    }

    private void drawSimulation() {
        GraphicsContext gc = simulationCanvas.getGraphicsContext2D();

        // Clear the canvas
        gc.clearRect(0, 0, simulationCanvas.getWidth(), simulationCanvas.getHeight());

        // Set the background color
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, simulationCanvas.getWidth(), simulationCanvas.getHeight());

        // Draw the trajectory line
    }


    // Method to calculate the X position of the projectile
    private double calculateProjectileXPosition() {
        double canvasWidth = simulationCanvas.getWidth(); // Width of the canvas
        double velocityX = projectile.getInitialVelocity() * Math.cos(Math.toRadians(projectile.getInitialAngle())); // X component of velocity

        // Calculate time based on distance traveled horizontally (X direction)
        double time = canvasWidth / velocityX;

        double initialX = 0; // Starting X position, adjust as needed
        return initialX + velocityX * time;
    }

    private double calculateProjectileYPosition() {
        double initialY = simulationCanvas.getHeight() - projectile.getInitialHeight(); // Initial Y position
        double velocityX = projectile.getInitialVelocity() * Math.cos(Math.toRadians(projectile.getInitialAngle())); // X component of velocity
        double canvasWidth = simulationCanvas.getWidth(); // Width of the canvas
        double time = canvasWidth / velocityX;
        double velocityY = -projectile.getInitialVelocity() * Math.sin(Math.toRadians(projectile.getInitialAngle())); // Y component of velocity
        double g = getGravityValue(projectile.getGravity()); // Get gravity value based on selected gravity
        double angleRadians = Math.toRadians(projectile.getInitialAngle()); // Convert angle to radians
        double velocityXT = projectile.getInitialVelocity() * Math.cos(angleRadians); // Horizontal component of initial velocity
        double velocityYT = projectile.getInitialVelocity() * Math.sin(angleRadians); // Vertical component of initial velocity

        return initialY + velocityYT * time + 0.5 * g * time * time; // Calculate Y position using projectile motion equation with angle
    }


    // Method to get gravity value based on selected gravity
    private double getGravityValue(String gravity) {
        if (gravity != null) {
            switch (gravity) {
                case "Earth":
                    return 9.81; // Standard gravity on Earth (m/s^2)
                case "Mars":
                    return 3.71; // Gravity on Mars (m/s^2)
                case "Mercury":
                    return 3.7; // Gravity on Mercury (m/s^2)
                case "Saturn":
                    return 10.44; // Gravity on Saturn (m/s^2)
                default:
                    return 9.81; // Default to Earth gravity if gravity is not recognized
            }
        } else {
            // Handle the case when gravity is null (e.g., provide a default value)
            return 9.81; // Default to Earth gravity if gravity is null
        }
    }



    @FXML
    public void initialize() {
        projectile = new Projectile();

        // Bind UI components to Projectile properties
        initialHeightTextField.textProperty().bindBidirectional(projectile.initialHeightProperty(), new NumberStringConverter());
        initialVelocityTextField.textProperty().bindBidirectional(projectile.initialVelocityProperty(), new NumberStringConverter());
        initialAngleTextField.textProperty().bindBidirectional(projectile.initialAngleProperty(), new NumberStringConverter());

        // Add listeners to update the projectile when mass and gravity change
        lightMassRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                projectile.setMass(1.0); // Set light mass
            }
        });

        heavyMassRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                projectile.setMass(10.0); // Set heavy mass
            }
        });

        earthGravityRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                projectile.setGravity("Earth"); // Set Earth gravity
            }
        });

        marsGravityRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                projectile.setGravity("Mars"); // Set Mars gravity
            }
        });

        mercuryGravityRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                projectile.setGravity("Mercury"); // Set Mercury gravity
            }
        });

        saturnGravityRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                projectile.setGravity("Saturn"); // Set Saturn gravity
            }
        });

        // Other initialization code...

        // Add event listeners to text fields for input validation
        initialHeightTextField.setOnAction(event -> validateInput(initialHeightTextField, "Initial height"));
        initialVelocityTextField.setOnAction(event -> validateInput(initialVelocityTextField, "Initial velocity"));
        initialAngleTextField.setOnAction(event -> validateInput(initialAngleTextField, "Initial angle"));
    }

    // Method to validate input for a text field
// Method to validate input for a text field
    private void validateInput(TextField textField, String fieldName) {
        String inputText = textField.getText().trim();
        if (inputText.isEmpty()) {
            textField.setStyle("-fx-border-color: red;");
            showAlert("Enter a valid number for " + fieldName);
            textField.clear(); // Clear the text field
            return;
        }

        try {
            double value = Double.parseDouble(inputText);
            // Check if value is negative
            if (value < 0) {
                textField.setStyle("-fx-border-color: red;");
                showAlert("Enter a non-negative value for " + fieldName);
                textField.clear(); // Clear the text field
            } else if (value > 1000) { // Adjust the upper limit as needed
                textField.setStyle("-fx-border-color: red;");
                showAlert("Please enter a smaller value for " + fieldName);
                textField.clear(); // Clear the text field
            } else {
                // Reset border color if input is valid
                textField.setStyle("");
            }
        } catch (NumberFormatException e) {
            textField.setStyle("-fx-border-color: red;");
            showAlert("Enter a valid number for " + fieldName);
            textField.clear(); // Clear the text field
        }
    }





    // Method to show an alert dialog
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void onStartSimulationButtonClick(ActionEvent event) {
        startSimulation();
    }

    @FXML
    private void onStopSimulationButtonClick(ActionEvent event) {
        stopSimulation();
    }

    @FXML
    private void onResetSimulationButtonClick(ActionEvent event) {
        resetSimulation();
    }


    public void openPhysicsClassroom(ActionEvent actionEvent) {
        openURL("https://www.physicsclassroom.com/class/vectors/Lesson-2/Horizontal-and-Vertical-Components-of-Velocity");
    }

    public void openNASA(ActionEvent actionEvent) {
        openURL("https://www.grc.nasa.gov/www/k-12/rocket/rktsimul.html");
    }

    private void openURL(String url) {
        try {
            URI uri = new URI(url);
            java.awt.Desktop.getDesktop().browse(uri);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
