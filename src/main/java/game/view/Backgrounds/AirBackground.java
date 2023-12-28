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

public class AirBackground extends Pane {

    public AirBackground() {
        this.createCloudParticles();
    }

    private void createCloudParticles() {
        Timeline cloudTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0), event -> this.createCloud()),
                new KeyFrame(Duration.seconds(1), event -> this.createCloud())
        );
        cloudTimeline.setCycleCount(Animation.INDEFINITE);
        cloudTimeline.play();
    }

    private void createCloud() {
        double smallCircleRadius = 15;
        double biggerCircleRadius = 25;
    
        Circle smallCircle1 = new Circle(smallCircleRadius);
        Circle smallCircle2 = new Circle(smallCircleRadius);

        Circle biggerCircle = new Circle(biggerCircleRadius);

        Color cloudColor = Color.rgb(255, 255, 255, Math.random());
        smallCircle1.setFill(cloudColor);
        smallCircle2.setFill(cloudColor);
        biggerCircle.setFill(cloudColor);

        smallCircle1.setTranslateX(-biggerCircleRadius * 1.5);
        smallCircle2.setTranslateX(biggerCircleRadius * 1.5);
    
        Group cloudGroup = new Group(smallCircle1, smallCircle2, biggerCircle);

        double initialX = Math.random() > 0.5 ? -50 : 850; 
        double initialY = Math.random() * 400 + 100; 
    
        cloudGroup.setTranslateX(initialX);
        cloudGroup.setTranslateY(initialY);
    
        getChildren().add(cloudGroup);
    
        double speed = Math.random() * 2 + 1; 
    
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(20 / speed), cloudGroup);
        translateTransition.setToX((Math.random() > 0.5 ? 900 : -50) - initialX); 
 
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(5 + Math.random() * 5), cloudGroup);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.2); 
        fadeTransition.setCycleCount(1); 
    
        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeTransition);

        parallelTransition.setOnFinished(e -> getChildren().remove(cloudGroup));

        parallelTransition.play();
    }  
}
