package game.view.backgrounds;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * Background with floating cloud particles for the "Air" element.
 */
public class AirBackground extends Pane {

    /**
     * Creates an instance of the AirBackground with floating cloud particles.
     */
    public AirBackground() {
        this.createCloudParticles();
    }

    /**
     * Creates a timeline to continuously generate cloud particles at intervals.
     */
    private void createCloudParticles() {
        // Timeline to create cloud particles every second
        Timeline cloudTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0), event -> this.createCloud()),
                new KeyFrame(Duration.seconds(1), event -> this.createCloud())
        );
        cloudTimeline.setCycleCount(Animation.INDEFINITE);
        cloudTimeline.play();
    }

    /**
     * Creates a group of cloud particles and animates their movement and fading.
     */
    private void createCloud() {
        // Define circle radii for small and bigger circles
        double smallCircleRadius = 15;
        double biggerCircleRadius = 25;

        // Create small circles
        Circle smallCircle1 = new Circle(smallCircleRadius);
        Circle smallCircle2 = new Circle(smallCircleRadius);

        // Create a bigger circle
        Circle biggerCircle = new Circle(biggerCircleRadius);

        // Set cloud color with random transparency
        Color cloudColor = Color.rgb(255, 255, 255, Math.random());
        smallCircle1.setFill(cloudColor);
        smallCircle2.setFill(cloudColor);
        biggerCircle.setFill(cloudColor);

        // Position small circles on each side of the bigger circle
        smallCircle1.setTranslateX(-biggerCircleRadius * 1.5);
        smallCircle2.setTranslateX(biggerCircleRadius * 1.5);

        // Group small and bigger circles into a cloud
        Group cloudGroup = new Group(smallCircle1, smallCircle2, biggerCircle);

        // Set initial position of the cloud
        double initialX = Math.random() > 0.5 ? -50 : 850;
        double initialY = Math.random() * 400 + 100;

        cloudGroup.setTranslateX(initialX);
        cloudGroup.setTranslateY(initialY);

        // Add the cloud group to the background
        getChildren().add(cloudGroup);

        // Set speed for cloud movement
        double speed = Math.random() * 2 + 1;

        // Create TranslateTransition for cloud movement
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(20 / speed), cloudGroup);
        translateTransition.setToX((Math.random() > 0.5 ? 900 : -50) - initialX);

        // Create FadeTransition for cloud fading
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(5 + Math.random() * 5), cloudGroup);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.2);
        fadeTransition.setCycleCount(1);

        // Combine TranslateTransition and FadeTransition in parallel
        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeTransition);

        // Remove the cloud group from the background after animation finishes
        parallelTransition.setOnFinished(e -> getChildren().remove(cloudGroup));

        // Start the parallel transition
        parallelTransition.play();
    }
}
