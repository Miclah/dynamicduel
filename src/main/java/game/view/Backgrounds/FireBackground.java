package game.view.backgrounds;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

/**
 * Background with flame animation and fire particles for the "Fire" element.
 */
public class FireBackground extends Pane {

    /**
     * Creates an instance of the FireBackground with flame animation and fire particles.
     */
    public FireBackground() {
        this.createFlameAnimation();
        this.createFireParticles();
    }

    /**
     * Creates a flame animation using a scaled, faded, and rotated circle.
     */
    private void createFlameAnimation() {
        // Create a flame circle with specified color and stroke
        Circle flame = new Circle(150, Color.rgb(255, 140, 0));
        flame.setStroke(Color.rgb(255, 69, 0));
        flame.setStrokeWidth(5);

        // Create a scale transition for flame pulsation
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2), flame);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.setCycleCount(Animation.INDEFINITE);
        scaleTransition.setAutoReverse(true);

        // Create a fade transition for flame flickering
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), flame);
        fadeTransition.setToValue(0.7);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.setAutoReverse(true);

        // Create a rotate transition for flame rotation
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(4), flame);
        rotateTransition.setByAngle(30);
        rotateTransition.setCycleCount(Animation.INDEFINITE);
        rotateTransition.setAutoReverse(true);

        // Combine scale, fade, and rotate transitions in parallel
        ParallelTransition parallelTransition = new ParallelTransition(scaleTransition, fadeTransition, rotateTransition);

        // Start the parallel transition
        parallelTransition.play();

        // Add the flame circle to the background
        getChildren().add(flame);
    }

    /**
     * Creates fire particles with fading and random movement along cubic curves.
     */
    private void createFireParticles() {
        // Generate 200 fire particles
        for (int i = 0; i < 200; i++) {
            // Create a small circle as a fire particle with specified color
            Circle particle = new Circle(2, Color.rgb(255, 69, 0));

            // Set random initial position for the fire particle
            particle.setCenterX(Math.random() * 800);
            particle.setCenterY(Math.random() * 600);

            // Create a fade transition for the fire particle
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2 + Math.random() * 3), particle);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.2);
            fadeTransition.setCycleCount(Animation.INDEFINITE);
            fadeTransition.setAutoReverse(true);

            // Create a cubic curve path for the fire particle
            Path path = new Path();
            path.getElements().add(new MoveTo(particle.getCenterX(), particle.getCenterY()));
            path.getElements().add(new CubicCurveTo(
                    Math.random() * 800,
                    Math.random() * 600,
                    Math.random() * 800,
                    Math.random() * 600,
                    particle.getCenterX(),
                    particle.getCenterY()));

            // Create a path transition for the fire particle
            PathTransition pathTransition = new PathTransition(Duration.seconds(5 + Math.random() * 5), path, particle);
            pathTransition.setCycleCount(Animation.INDEFINITE);
            pathTransition.setAutoReverse(true);

            // Combine fade and path transitions in parallel
            ParallelTransition parallelTransition = new ParallelTransition(fadeTransition, pathTransition);

            // Start the parallel transition for the fire particle
            parallelTransition.play();

            // Add the fire particle to the background
            getChildren().add(particle);
        }
    }
}
