package game.view.backgrounds;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * Background with water bubble particles for the "Water" element.
 */
public class WaterBackground extends Pane {

    /**
     * Creates an instance of WaterBackground with water bubble particles.
     */
    public WaterBackground() {
        this.createAirBubbleParticles();
    }

    /**
     * Creates a continuous timeline for generating water bubble particles.
     */
    private void createAirBubbleParticles() {
        // Create a timeline with keyframes for generating water bubbles
        Timeline bubbleTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0), event -> this.createBubble()),
                new KeyFrame(Duration.seconds(1), event -> this.createBubble())
        );
        bubbleTimeline.setCycleCount(Animation.INDEFINITE);
        bubbleTimeline.play();
    }

    /**
     * Creates and animates a water bubble particle.
     */
    private void createBubble() {
        // Create a radial gradient for the water bubble color
        RadialGradient gradient = new RadialGradient(
                0, 0,
                0.5, 0.5,
                0.8,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(173, 216, 230, 1.0)),
                new Stop(1, Color.rgb(135, 206, 250, 0.1))
        );

        // Create a circle representing a water bubble with the radial gradient
        Circle bubble = new Circle(8, gradient);
        bubble.setCenterX(Math.random() * 800);
        bubble.setCenterY(600);

        // Create a translate transition to move the water bubble upwards
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(5 + Math.random() * 5), bubble);
        translateTransition.setToY(-800);
        translateTransition.setCycleCount(1);

        // Create a fade transition to make the water bubble gradually disappear
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(10), bubble);
        fadeTransition.setToValue(0.2);
        fadeTransition.setCycleCount(1);

        // Combine translate and fade transitions in parallel
        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeTransition);

        // Set an event handler to remove the water bubble when animations are finished
        parallelTransition.setOnFinished(e -> getChildren().remove(bubble));

        // Start the parallel transition for the water bubble
        parallelTransition.play();

        // Add the water bubble to the background
        getChildren().add(bubble);
    }
}
