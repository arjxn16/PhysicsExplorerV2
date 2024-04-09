package com.base.game.physicsexplorer.MainMenu;

import javafx.beans.property.*;

public class Projectile {
    // Define properties for the projectile attributes
    private final DoubleProperty initialHeight = new SimpleDoubleProperty();
    private final DoubleProperty initialVelocity = new SimpleDoubleProperty();
    private final DoubleProperty initialAngle = new SimpleDoubleProperty();
    private final DoubleProperty mass = new SimpleDoubleProperty();
    private final StringProperty gravity = new SimpleStringProperty();

    // Constructor
    public Projectile() {
        // Add listeners to update the projectile attributes when UI components change
        initialHeight.addListener((observable, oldValue, newValue) -> {
            System.out.println("Initial height changed: " + newValue);
        });

        initialVelocity.addListener((observable, oldValue, newValue) -> {
            System.out.println("Initial velocity changed: " + newValue);
        });

        initialAngle.addListener((observable, oldValue, newValue) -> {
            System.out.println("Initial angle changed: " + newValue);
        });

        mass.addListener((observable, oldValue, newValue) -> {
            System.out.println("Mass changed: " + newValue);
        });

        gravity.addListener((observable, oldValue, newValue) -> {
            System.out.println("Gravity changed: " + newValue);
        });
    }

    // Getters and setters for the projectile attributes
    public double getInitialHeight() {
        return initialHeight.get();
    }

    public void setInitialHeight(double value) {
        initialHeight.set(value);
    }

    public DoubleProperty initialHeightProperty() {
        return initialHeight;
    }

    public double getInitialVelocity() {
        return initialVelocity.get();
    }

    public void setInitialVelocity(double value) {
        initialVelocity.set(value);
    }

    public DoubleProperty initialVelocityProperty() {
        return initialVelocity;
    }

    public double getInitialAngle() {
        return initialAngle.get();
    }

    public void setInitialAngle(double value) {
        initialAngle.set(value);
    }

    public DoubleProperty initialAngleProperty() {
        return initialAngle;
    }

    public double getMass() {
        return mass.get();
    }

    public void setMass(double value) {
        mass.set(value);
    }

    public DoubleProperty massProperty() {
        return mass;
    }

    public String getGravity() {
        return gravity.get();
    }

    public void setGravity(String value) {
        gravity.set(value);
    }

    public StringProperty gravityProperty() {
        return gravity;
    }

    public void resetToDefault() {
            // Set all properties to their default values
            initialHeight.set(0);
            initialVelocity.set(0);
            initialAngle.set(0);
            mass.set(1.0); // Assuming default mass is 1.0 kg
            gravity.set("Earth"); // Assuming default gravity is Earth
        }

    }

