package Collisions;

public class Box {
    private double mass;
    private double velocityX;
    private double velocityY;
    private double positionX;
    private double positionY;

    public Box(double mass, double initialVelocityX, double initialVelocityY, double initialPositionX, double initialPositionY) {
        this.mass = mass;
        this.velocityX = initialVelocityX;
        this.velocityY = initialVelocityY;
        this.positionX = initialPositionX;
        this.positionY = initialPositionY;
    }

    public double getMass() {
        return mass;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public void applyForce(double forceX, double forceY) {
        // Assuming constant acceleration for simplicity
        double accelerationX = forceX / mass;
        double accelerationY = forceY / mass;
        velocityX += accelerationX;
        velocityY += accelerationY;
    }

    public void updatePosition() {
        positionX += velocityX;
        positionY += velocityY;
    }
}
