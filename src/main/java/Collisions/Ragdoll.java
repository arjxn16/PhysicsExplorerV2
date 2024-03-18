package Collisions;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Ragdoll {

    private List<BodyPart> bodyParts;
    private BodyPart ground;
    private Box box; // New addition

    public Ragdoll(double sceneWidth, double sceneHeight) {
        this.bodyParts = new ArrayList<>();
        initializeBodyParts(sceneWidth, sceneHeight);

        // Initialize the box
        double boxWidth = 50;
        double boxHeight = 50;
        double boxPositionX = sceneWidth / 2 - 25; // Centered horizontally
        double boxPositionY = sceneHeight / 2 - 25; // Centered vertically
        double boxMass = 10; // Adjust as needed
        box = new Box(boxWidth, boxHeight, boxPositionX, boxPositionY, boxMass);
    }

    private void initializeBodyParts(double sceneWidth, double sceneHeight) {
        double centerX = sceneWidth / 2;
        double centerY = sceneHeight - 100; // Adjust based on ground height

        // Create a ground
        ground = createBodyPart(sceneWidth, 20, Color.GREEN);
        ground.setTranslateY(sceneHeight - 20);
        bodyParts.add(ground);

        // Create head
        BodyPart head = createBodyPart(40, 40, Color.RED);
        head.setTranslateX(centerX - 20);
        head.setTranslateY(centerY - 160);
        bodyParts.add(head);

        // Create chest
        BodyPart chest = createBodyPart(80, 80, Color.BLUE);
        chest.setTranslateX(centerX - 40);
        chest.setTranslateY(centerY - 120);
        bodyParts.add(chest);

        // Create left arm
        BodyPart leftArm = createBodyPart(20, 80, Color.GREEN);
        leftArm.setTranslateX(centerX - 60);
        leftArm.setTranslateY(centerY - 120);
        bodyParts.add(leftArm);

        // Create right arm
        BodyPart rightArm = createBodyPart(20, 80, Color.GREEN);
        rightArm.setTranslateX(centerX + 40);
        rightArm.setTranslateY(centerY - 120);
        bodyParts.add(rightArm);

        // Create left leg
        BodyPart leftLeg = createBodyPart(50, 200, Color.BLACK);
        leftLeg.setTranslateX(centerX - 45);
        leftLeg.setTranslateY(centerY + 60);
        bodyParts.add(leftLeg);

        // Create right leg
        BodyPart rightLeg = createBodyPart(45, 200, Color.BLACK);
        rightLeg.setTranslateX(centerX + 5);
        rightLeg.setTranslateY(centerY + 60);
        bodyParts.add(rightLeg);

        // Create joints
        // For simplicity, connect head to chest, and chest to arms and legs
        connectBodyParts(head, chest);
        connectBodyParts(chest, leftArm);
        connectBodyParts(chest, rightArm);
        connectBodyParts(chest, leftLeg);
        connectBodyParts(chest, rightLeg);
    }

    private BodyPart createBodyPart(double width, double height, Color color) {
        return new BodyPart(width, height, color);
    }

    private void connectBodyParts(BodyPart partA, BodyPart partB) {
        Joint joint = new Joint(1, Color.TRANSPARENT);
        joint.setPivot(partA.getTranslateX() + partA.getWidth() / 2, partA.getTranslateY() + partA.getHeight() / 2);

        partA.addJoint(joint); // Add joint to partA's joints list
        partB.addJoint(joint); // Add joint to partB's joints list

        partB.boundsInParentProperty().addListener((observable, oldValue, newValue) -> {
            partA.getJoints().forEach(j -> j.setPivot(newValue.getMaxX(), newValue.getMaxY()));
        });
    }

    public void checkCollisions() {
        for (BodyPart bodyPart : bodyParts) {
            if (bodyPart != ground && bodyPart.getBoundsInParent().intersects(ground.getBoundsInParent())) {
                // Adjust position to prevent body part from going under the ground
                double overlap = bodyPart.getBoundsInParent().getMaxY() - ground.getBoundsInParent().getMinY();
                bodyPart.setTranslateY(bodyPart.getTranslateY() - overlap);
            }
        }
    }

    public List<BodyPart> getAllBodyParts() {
        return new ArrayList<>(bodyParts);
    }

    public class BodyPart extends Rectangle {

        private List<Joint> joints;
        private double velocityX;
        private double velocityY;

        public BodyPart(double width, double height, Color color) {
            super(width, height, color);
            this.joints = new ArrayList<>();
            this.velocityX = 0;
            this.velocityY = 0;
        }

        public void addJoint(Joint joint) {
            joints.add(joint);
        }

        public List<Joint> getJoints() {
            return new ArrayList<>(joints);
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

        public void updatePosition() {
            setTranslateX(getTranslateX() + velocityX);
            setTranslateY(getTranslateY() + velocityY);
        }

        public void applyForce(double forceX, double forceY) {
            velocityX += forceX;
            velocityY += forceY;
        }
    }

    public class Joint extends Rectangle {

        public Joint(double size, Color color) {
            super(size, size, color);
        }

        public void setPivot(double x, double y) {
            setTranslateX(x - getWidth() / 2);
            setTranslateY(y - getHeight() / 2);
        }

    }

    public class Box extends Rectangle {
        private double mass;
        private double velocityX;
        private double velocityY;

        public Box(double width, double height, double x, double y, double mass) {
            super(width, height);
            setTranslateX(x);
            setTranslateY(y);
            this.mass = mass;
            this.velocityX = 0;
            this.velocityY = 0;
            setFill(Color.GRAY); // You can change the color as needed
        }

        public double getMass() {
            return mass;
        }

        public double getVelocityX() {
            return velocityX;
        }

        public double getVelocityY() {
            return velocityY;
        }

        public void setVelocityX(double velocityX) {
            this.velocityX = velocityX;
        }

        public void setVelocityY(double velocityY) {
            this.velocityY = velocityY;
        }

        public void updatePosition() {
            setTranslateX(getTranslateX() + velocityX);
            setTranslateY(getTranslateY() + velocityY);
        }
    }
}
