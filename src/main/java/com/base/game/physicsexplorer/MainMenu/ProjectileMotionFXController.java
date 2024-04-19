package com.base.game.physicsexplorer.MainMenu;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ProjectileMotionFXController {

    private AnimationTimer animationTimer;
    private static final double CANVAS_WIDTH = 720 ;
    private static final double CANVAS_HEIGHT = 480;
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
    private long lastUpdate = 0;
private boolean isSimulationPaused = false;
    private GraphicsContext savedGraphicsContext;
    private double projectileX;
    private double projectileY;
    private double pausedX;
    private double pausedY;
    private EventQueue eventQueue = new EventQueue();

    private void startSimulation() {
        if (!simulationRunning) {
            simulationRunning = true;
            System.out.println("Simulation started");

            // Create an AnimationTimer to update the simulation
            new AnimationTimer() {
                private double time = 0;

                @Override
                public void handle(long now) {
                    if (lastUpdate == 0) {
                        lastUpdate = now;
                    }

                    // Calculate the time elapsed since the last update
                    double elapsedTime = (now - lastUpdate) / 1e9; // Convert nanoseconds to seconds
                    lastUpdate = now;

                    // Update the time
                    time += elapsedTime;

                    // Check if the simulation is paused
                    if (!isSimulationPaused) {
                        // Update the projectile's position
                        double[] position = calculateProjectilePosition(time);
                        projectileX = position[0];
                        projectileY = position[1];

                        // Update the canvas
                        drawSimulation(projectileX, projectileY);
                    }
                }
            }.start();
        } else {
            System.out.println("Simulation is already running");
        }
    }

    private void stopSimulation() {
        if (simulationRunning) {
            simulationRunning = false;
            isSimulationPaused = true;
            savedGraphicsContext = simulationCanvas.getGraphicsContext2D();
            previousXPositions.addLast(projectileX);
            previousYPositions.addLast(projectileY);
            pausedX = projectileX; // Update pausedX with the last known X position
            pausedY = projectileY; // Update pausedY with the last known Y position
            drawSimulation(projectileX, projectileY);
        } else {
            System.out.println("Simulation is not running");
        }
    }

    private void resetSimulation() {
        if (simulationCanvas != null) {
            simulationCanvas.getGraphicsContext2D().clearRect(0, 0, simulationCanvas.getWidth(), simulationCanvas.getHeight());
            simulationCanvas = null; // Sets canvas to null
        }

        simulationRunning = false; // Stop the simulation
        isSimulationPaused = false; // reset the pause state

        // Reset parameters
        projectile.resetToDefault();
        pausedX = 0;
        pausedY = 0;

        // Clear previous positions
        previousXPositions.clear();
        previousYPositions.clear();

        // Stop and reset the AnimationTimer
        if (animationTimer != null) {
            animationTimer.stop();
            animationTimer = null;
        }

        System.out.println("Simulation reset");
    }
    @FXML
    public void onReturnToHomeButtonClick(ActionEvent actionEvent) {
        // Get the current stage
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Create a new instance of the Main class
        Main main = new Main();

        // Call the start() method to restart the application
        try {
            main.start(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private CircularLinkedList<Double> previousXPositions = new CircularLinkedList<>();
    private CircularLinkedList<Double> previousYPositions = new CircularLinkedList<>();

    private void drawSimulation(double projectileX, double projectileY) {
        if (simulationCanvas == null) {
            return;
        }
        GraphicsContext gc = simulationCanvas.getGraphicsContext2D();

        // Clear the canvas
        gc.clearRect(0, 0, simulationCanvas.getWidth(), simulationCanvas.getHeight());

        // Set the background color
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, simulationCanvas.getWidth(), simulationCanvas.getHeight());

        // Draw the projectile
        double projectileSize = 10; // Adjust the size of the projectile as needed
        gc.setFill(Color.BLUE);

        // Use pausedX and pausedY to draw the projectile when the simulation is paused
        if (isSimulationPaused) {
            gc.fillOval(pausedX - projectileSize / 2, pausedY - projectileSize / 2, projectileSize, projectileSize);
        } else {
            gc.fillOval(projectileX - projectileSize / 2, projectileY - projectileSize / 2, projectileSize, projectileSize);
        }

        // Draw the trajectory line only if the simulation is not paused
        if (!isSimulationPaused) {
            // Draw the trajectory line
            gc.setStroke(Color.RED);
            gc.setLineWidth(2);

            // Add the current position to the list
            previousXPositions.add(projectileX);
            previousYPositions.add(projectileY);

            // Draw the trajectory line
            for (int i = 1; i < previousXPositions.size(); i++) {
                gc.strokeLine(previousXPositions.get(i - 1), previousYPositions.get(i - 1), previousXPositions.get(i), previousYPositions.get(i));
            }
        }
    }

    private double[] calculateProjectilePosition(double time) {
        double velocityX = projectile.getInitialVelocity() * Math.cos(Math.toRadians(projectile.getInitialAngle())); // X component of velocity
        double velocityY = projectile.getInitialVelocity() * Math.sin(Math.toRadians(projectile.getInitialAngle())); // Y component of velocity
        double g = getGravityValue(projectile.getGravity()); // Get gravity value based on selected gravity
        double initialX = 0; // Starting X position, adjust as needed
        double initialY = simulationCanvas.getHeight() - projectile.getInitialHeight(); // Initial Y position, set to the bottom of the canvas minus initial height
        double mass = projectile.getMass(); // Get mass value

        double x = initialX + velocityX * time; // Calculate X position
        double y = initialY - velocityY * time + 0.5 * g * time * time / mass; // Calculate Y position, add gravity

        // Check if the projectile has hit the ground or reached the right side of the canvas
        if (y > simulationCanvas.getHeight() || x > simulationCanvas.getWidth()) {
            stopSimulation();
        }

        // Check if the projectile has gone under the ground
        if (y > simulationCanvas.getHeight()) {
            y = simulationCanvas.getHeight(); // Set the Y position to the ground level
            stopSimulation(); // Stop the simulation if the projectile goes under the ground
        }

        return new double[]{x, y};
    }

    // Method to get gravity value based on selected gravity
    private double getGravityValue(String gravity) {
        if (gravity != null) {
            return switch (gravity) {
                case "Earth" -> 9.81; // Standard gravity on Earth (m/s^2)
                case "Mars" -> 3.71; // Gravity on Mars (m/s^2)
                case "Mercury" -> 3.7; // Gravity on Mercury (m/s^2)
                case "Saturn" -> 10.44; // Gravity on Saturn (m/s^2)
                default -> 9.81; // Default to Earth gravity if gravity is not recognized
            };
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

        lightMassRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                projectile.setMass(1.0); // Set light mass
                heavyMassRadioButton.setSelected(false); // Deselect heavy mass button
            }
        });

        heavyMassRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                projectile.setMass(10.0); // Set heavy mass
                lightMassRadioButton.setSelected(false); // Deselect light mass button
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
        initialHeightTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            validateInput(initialHeightTextField, "Initial height");
        });

        initialVelocityTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            validateInput(initialVelocityTextField, "Initial velocity");
        });

        initialAngleTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            validateInput(initialAngleTextField, "Initial angle");
        });

        // Add event queue processing
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                processEvents();
            }
        }.start();
    }

    private void processEvents() {
        while (!eventQueue.isEmpty()) {
            ActionEvent event = eventQueue.remove();
            if (event.getTarget() == startSimulationButton) {
                startSimulation();
            } else if (event.getTarget() == stopSimulationButton) {
                stopSimulation();
            } else if (event.getTarget() == resetSimulationButton) {
                resetSimulation();
            }
        }
    }

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
            } else if (fieldName.equals("Initial height") && (value < 0 || value > 480)) {
                textField.setStyle("-fx-border-color: red;");
                showAlert("Enter a value between 0 and 480 for Initial height");
                textField.clear(); // Clear the text field
            } else if (fieldName.equals("Initial angle") && (value < 0 || value > 90)) {
                textField.setStyle("-fx-border-color: red;");
                showAlert("Enter a value between 0 and 90 for Initial angle");
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
        eventQueue.add(event);
    }

    @FXML
    private void onStopSimulationButtonClick(ActionEvent event) {
        eventQueue.add(event);
    }

    @FXML
    private void onResetSimulationButtonClick(ActionEvent event) {
        eventQueue.add(event);
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
