package game.view.Backgrounds;

import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class WaterBackground extends Pane {

    public WaterBackground() {

        // Create air bubble particles
        createAirBubbleParticles();
    }

    private void createAirBubbleParticles() {
        // Create air bubble particles
        Timeline bubbleTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0), event -> createBubble()),
                new KeyFrame(Duration.seconds(1), event -> createBubble())
        );
        bubbleTimeline.setCycleCount(Animation.INDEFINITE);
        bubbleTimeline.play();
    }

    private void createBubble() {
        // Create a radial gradient for a glossy effect
        RadialGradient gradient = new RadialGradient(
                0, 0,
                0.5, 0.5,
                0.8,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(173, 216, 230, 1.0)),
                new Stop(1, Color.rgb(135, 206, 250, 0.1))
        );

        Circle bubble = new Circle(8, gradient); // Use the radial gradient for the bubble fill
        bubble.setCenterX(Math.random() * 800); // Randomize starting position
        bubble.setCenterY(600); // Set starting position below the window

        // Create a bubble animation (rising from below the window)
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(5 + Math.random() * 5), bubble);
        translateTransition.setToY(-800); // Adjust the ending position
        translateTransition.setCycleCount(1); // Each bubble rises only once

        // Create a fade animation
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(10), bubble);
        fadeTransition.setToValue(0.2); // Adjust the final opacity
        fadeTransition.setCycleCount(1); // Each bubble fades only once

        // Create a parallel transition combining translate and fade animations
        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeTransition);

        // Remove the bubble from the pane after it rises and fades
        parallelTransition.setOnFinished(e -> getChildren().remove(bubble));

        // Play the parallel transition
        parallelTransition.play();

        // Add the bubble to the pane
        getChildren().add(bubble);
    }
}
