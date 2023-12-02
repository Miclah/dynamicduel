package game.view.Backgrounds;

import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;


public class FireBackground extends Pane {

    public FireBackground() {

       
        // Create a flame animation
        createFlameAnimation();

        // Create fire particles
        createFireParticles();

       
    }

    private void createFlameAnimation() {
        // Create a flame shape
        Circle flame = new Circle(150, Color.rgb(255, 140, 0)); // Increase the size to 150
        flame.setStroke(Color.rgb(255, 69, 0));
        flame.setStrokeWidth(5);

        // Create a flame animation (scaling effect)
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2), flame); // Increase duration
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.setCycleCount(Animation.INDEFINITE);
        scaleTransition.setAutoReverse(true);

        // Create a fade animation
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), flame); // Increase duration
        fadeTransition.setToValue(0.7);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.setAutoReverse(true);

        // Create a rotation animation
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(4), flame); // Increase duration
        rotateTransition.setByAngle(30);
        rotateTransition.setCycleCount(Animation.INDEFINITE);
        rotateTransition.setAutoReverse(true);

        // Create a parallel transition combining scale, fade, and rotate animations
        ParallelTransition parallelTransition = new ParallelTransition(scaleTransition, fadeTransition, rotateTransition);

        // Play the parallel transition
        parallelTransition.play();

        // Add the flame to the pane
        getChildren().add(flame);
    }

    private void createFireParticles() {
        // Create fire particles
        for (int i = 0; i < 200; i++) {
            Circle particle = new Circle(2, Color.rgb(255, 69, 0)); // Keep the size at 2

            getChildren().add(particle);

            // Randomize initial position
            particle.setCenterX(Math.random() * 800);
            particle.setCenterY(Math.random() * 600);

            // Apply a fade transition for a more subtle effect
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2 + Math.random() * 3), particle);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.2);
            fadeTransition.setCycleCount(Animation.INDEFINITE);
            fadeTransition.setAutoReverse(true);

            // Apply a translate transition to move the particles in a circular motion
            Path path = new Path();
            path.getElements().add(new MoveTo(particle.getCenterX(), particle.getCenterY()));
            path.getElements().add(new CubicCurveTo(
                    Math.random() * 800,
                    Math.random() * 600,
                    Math.random() * 800,
                    Math.random() * 600,
                    particle.getCenterX(),
                    particle.getCenterY()));
            PathTransition pathTransition = new PathTransition(Duration.seconds(5 + Math.random() * 5), path, particle);
            pathTransition.setCycleCount(Animation.INDEFINITE);
            pathTransition.setAutoReverse(true);

            // Play both transitions simultaneously
            ParallelTransition parallelTransition = new ParallelTransition(fadeTransition, pathTransition);
            parallelTransition.play();
        }
    }
}
