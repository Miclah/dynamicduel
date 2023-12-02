package game.view.Backgrounds;

import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class AirBackground extends Pane {

    public AirBackground() {
        // Create cloud particles
        createCloudParticles();
    }

    private void createCloudParticles() {
        // Create a timeline for continuous cloud generation
        Timeline cloudTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0), event -> createCloud()),
                new KeyFrame(Duration.seconds(1), event -> createCloud())
        );
        cloudTimeline.setCycleCount(Animation.INDEFINITE);
        cloudTimeline.play();
    }

    private void createCloud() {
        // Create a cartoon-like cloud with three circles
        double smallCircleRadius = 15;
        double biggerCircleRadius = 25;
    
        // Create smaller circles
        Circle smallCircle1 = new Circle(smallCircleRadius);
        Circle smallCircle2 = new Circle(smallCircleRadius);
    
        // Create the bigger circle
        Circle biggerCircle = new Circle(biggerCircleRadius);
    
        // Set the fill color for the entire cloud
        Color cloudColor = Color.rgb(255, 255, 255, Math.random());
        smallCircle1.setFill(cloudColor);
        smallCircle2.setFill(cloudColor);
        biggerCircle.setFill(cloudColor);
    
        // Position the smaller circles on the sides of the bigger circle
        smallCircle1.setTranslateX(-biggerCircleRadius * 1.5);
        smallCircle2.setTranslateX(biggerCircleRadius * 1.5);
    
        // Group the circles together
        Group cloudGroup = new Group(smallCircle1, smallCircle2, biggerCircle);
    
        // Randomize initial position
        double initialX = Math.random() > 0.5 ? -50 : 850; // Start on either side of the window
        double initialY = Math.random() * 400 + 100; // Keep clouds within a specific vertical range
    
        cloudGroup.setTranslateX(initialX);
        cloudGroup.setTranslateY(initialY);
    
        getChildren().add(cloudGroup);
    
        // Randomize speed and direction
        double speed = Math.random() * 2 + 1; // Speed between 1 and 3
    
        // Create a translate transition to move the cloud horizontally
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(20 / speed), cloudGroup);
        translateTransition.setToX((Math.random() > 0.5 ? 900 : -50) - initialX); // Move horizontally across the window
    
        // Create a fade transition for a smooth appearance and disappearance
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(5 + Math.random() * 5), cloudGroup);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.2); // Adjust the final opacity
        fadeTransition.setCycleCount(1); // Fade only once
    
        // Create a parallel transition combining translate and fade animations
        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeTransition);
    
        // Remove the cloud from the pane after it crosses the window
        parallelTransition.setOnFinished(e -> getChildren().remove(cloudGroup));
    
        // Play the parallel transition
        parallelTransition.play();
    }
    
    
    
    
}
