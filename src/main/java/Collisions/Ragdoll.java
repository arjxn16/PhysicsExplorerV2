package Collisions;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

public class Ragdoll {

    private Map<String, BodyPart> bodyParts;
    private double sceneWidth;
    private double sceneHeight;

    public Ragdoll(double sceneWidth, double sceneHeight) {
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        initializeBodyParts();
    }

    private void initializeBodyParts() {
        bodyParts = new HashMap<>();

        double centerX = sceneWidth / 2;
        double centerY = sceneHeight - 100; // Adjust based on ground height

        // Create head
        Rectangle head = createBodyPart(40, 40, Color.RED);
        head.setTranslateX(centerX - 20);
        head.setTranslateY(centerY - 160); // Adjust for head position
        bodyParts.put("head", new BodyPart(head));

        // Create chest
        Rectangle chest = createBodyPart(80, 80, Color.BLUE);
        chest.setTranslateX(centerX - 40);
        chest.setTranslateY(centerY - 120);
        bodyParts.put("chest", new BodyPart(chest));

        // Create left arm
        Rectangle leftArm = createBodyPart(20, 80, Color.GREEN);
        leftArm.setTranslateX(centerX - 60);
        leftArm.setTranslateY(centerY - 120);
        bodyParts.put("leftArm", new BodyPart(leftArm));

        // Create right arm
        Rectangle rightArm = createBodyPart(20, 80, Color.GREEN);
        rightArm.setTranslateX(centerX + 40);
        rightArm.setTranslateY(centerY - 120);
        bodyParts.put("rightArm", new BodyPart(rightArm));

        // Create left leg
        Rectangle leftLeg = createBodyPart(50, 200, Color.BLACK);
        leftLeg.setTranslateX(centerX - 45);
        leftLeg.setTranslateY(centerY + 60);
        bodyParts.put("leftLeg", new BodyPart(leftLeg));

        // Create right leg
        Rectangle rightLeg = createBodyPart(45, 200, Color.BLACK);
        rightLeg.setTranslateX(centerX + 5);
        rightLeg.setTranslateY(centerY + 60);
        bodyParts.put("rightLeg", new BodyPart(rightLeg));

        // Create a ground
        Rectangle ground = createBodyPart(sceneWidth, 20, Color.GREEN);
        ground.setTranslateY(sceneHeight - 20);
        bodyParts.put("ground", new BodyPart(ground));
    }

    public Map<String, BodyPart> getAllBodyParts() {
        return new HashMap<>(bodyParts);
    }

    public class BodyPart extends Rectangle {

        public BodyPart(Rectangle rectangle) {
            super(rectangle.getWidth(), rectangle.getHeight(), rectangle.getFill());
            setTranslateX(rectangle.getTranslateX());
            setTranslateY(rectangle.getTranslateY());
        }
    }

    private Rectangle createBodyPart(double width, double height, Color color) {
        return new Rectangle(width, height, color);
    }
}
