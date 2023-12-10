package game.view.Backgrounds;

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

public class WaterBackground extends Pane {

    public WaterBackground() {
        this.createAirBubbleParticles();
    }

    private void createAirBubbleParticles() {
        Timeline bubbleTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0), event -> this.createBubble()),
                new KeyFrame(Duration.seconds(1), event -> this.createBubble())
        );
        bubbleTimeline.setCycleCount(Animation.INDEFINITE);
        bubbleTimeline.play();
    }

    private void createBubble() {
        RadialGradient gradient = new RadialGradient(
                0, 0,
                0.5, 0.5,
                0.8,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(173, 216, 230, 1.0)),
                new Stop(1, Color.rgb(135, 206, 250, 0.1))
        );

        Circle bubble = new Circle(8, gradient); 
        bubble.setCenterX(Math.random() * 800); 
        bubble.setCenterY(600);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(5 + Math.random() * 5), bubble);
        translateTransition.setToY(-800); 
        translateTransition.setCycleCount(1); 

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(10), bubble);
        fadeTransition.setToValue(0.2); 
        fadeTransition.setCycleCount(1); 

        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeTransition);

        parallelTransition.setOnFinished(e -> getChildren().remove(bubble));

        parallelTransition.play();

        getChildren().add(bubble);
    }
}
