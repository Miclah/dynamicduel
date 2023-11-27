package game.view.Backgrounds.Fire;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import javafx.scene.text.Text;

import game.view.MainMenu; // Import MainMenu to access AUTHOR, VERSION, and getCurrentDate

public class FireBackground extends Pane {

    public FireBackground() {
        // Create a flame animation
        createFlameAnimation();

        // Create fire particles
        createFireParticles();

        // Create the bottom bar
        HBox bottomBar = new HBox();
        bottomBar.setStyle("-fx-background-color: linear-gradient(to right, #FF4500, #8B0000);"); /* Gradient fire background */
        bottomBar.setMinHeight(20); /* Slimmer bottom bar */
        bottomBar.setAlignment(Pos.CENTER_LEFT);
        bottomBar.setPadding(new Insets(5, 20, 5, 20)); /* Padding for better appearance */

        // Adding author's name, date, and version to the bottom bar
        Text authorText = new Text("Author: " + MainMenu.AUTHOR); // Use the AUTHOR from MainMenu
        authorText.setStyle("-fx-font-size: 14; -fx-fill: white;"); /* White text color */
        Text dateText = new Text("Date: " + MainMenu.getCurrentDate()); // Use getCurrentDate() from MainMenu
        dateText.setStyle("-fx-font-size: 14; -fx-fill: white;");
        Text versionText = new Text("Version: " + MainMenu.VERSION); // Use the VERSION from MainMenu
        versionText.setStyle("-fx-font-size: 14; -fx-fill: white;");

        bottomBar.getChildren().addAll(authorText, dateText, versionText);

        // Add the bottom bar to the FireBackground
        getChildren().add(bottomBar);
    }

    private void createFlameAnimation() {
        // Create a flame shape
        Circle flame = new Circle(175, Color.rgb(255, 140, 0)); // Increase the size to 75
        flame.setStroke(Color.rgb(255, 69, 0));
        flame.setStrokeWidth(5);

        // Create a flame animation (scaling effect)
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), flame);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.setCycleCount(Animation.INDEFINITE);
        scaleTransition.setAutoReverse(true);

        // Create a fade animation
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), flame);
        fadeTransition.setToValue(0.7);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.setAutoReverse(true);

        // Create a parallel transition combining scale and fade animations
        ParallelTransition parallelTransition = new ParallelTransition(scaleTransition, fadeTransition);

        // Play the parallel transition
        parallelTransition.play();

        // Add the flame to the pane
        getChildren().add(flame);
    }

    // ...

    private void createFireParticles() {
        // Create fire particles
        for (int i = 0; i < 100; i++) {
            Circle particle = new Circle(2.5, Color.rgb(255, 69, 0)); // Keep the size at 2.5

            getChildren().add(particle);

            // Randomize initial position
            particle.setCenterX(Math.random() * 800);
            particle.setCenterY(Math.random() * 600);

            // Apply a fade transition for a more subtle effect
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2 + Math.random() * 3), particle);
            fadeTransition.setFromValue(0.8);
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
