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


public class FireBackground extends Pane {

    public FireBackground() {
        this.createFlameAnimation();
        this.createFireParticles();
    }

    private void createFlameAnimation() {
        Circle flame = new Circle(150, Color.rgb(255, 140, 0));
        flame.setStroke(Color.rgb(255, 69, 0));
        flame.setStrokeWidth(5);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2), flame); 
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.setCycleCount(Animation.INDEFINITE);
        scaleTransition.setAutoReverse(true);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), flame); 
        fadeTransition.setToValue(0.7);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.setAutoReverse(true);

        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(4), flame); 
        rotateTransition.setByAngle(30);
        rotateTransition.setCycleCount(Animation.INDEFINITE);
        rotateTransition.setAutoReverse(true);

        ParallelTransition parallelTransition = new ParallelTransition(scaleTransition, fadeTransition, rotateTransition);

        parallelTransition.play();

        getChildren().add(flame);
    }

    private void createFireParticles() {
        for (int i = 0; i < 200; i++) {
            Circle particle = new Circle(2, Color.rgb(255, 69, 0)); 

            getChildren().add(particle);

            particle.setCenterX(Math.random() * 800);
            particle.setCenterY(Math.random() * 600);

            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2 + Math.random() * 3), particle);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.2);
            fadeTransition.setCycleCount(Animation.INDEFINITE);
            fadeTransition.setAutoReverse(true);

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

            ParallelTransition parallelTransition = new ParallelTransition(fadeTransition, pathTransition);
            parallelTransition.play();
        }
    }
}
