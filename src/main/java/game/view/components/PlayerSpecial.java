package game.view.components;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

import java.util.Random;
import game.model.Player;
import game.view.GameView;

public class PlayerSpecial extends StackPane {

    private static final String SPECIAL_CARD_PATH = "/Cards/card_textures/special/special_%d.png";
    private static final int NUM_SPECIAL_CARDS = 8;

    private Rectangle outline;
    private ImageView specialImageView;
    private ProgressBar healthBar;
    private Player player;
    private Text healthText;

    public PlayerSpecial(double height, double screenWidth, double screenHeight, Color outlineColor, Player player, Pane transparentPane) {
        this.player = player;
        player.resetHealth();
        this.outline = new Rectangle(screenWidth - 1600, height, Color.TRANSPARENT);
        this.outline.setStroke(outlineColor);
        this.outline.setStrokeWidth(2);

        setTranslateX(height / 2);
        setTranslateY(screenHeight - height / 2);
        
        this.loadSpecialCard();
        this.initializeHealthBar(transparentPane);

        StackPane wholePane = new StackPane(this.outline, this.specialImageView);
        wholePane.setAlignment(Pos.CENTER);
        

        getChildren().add(wholePane);
    }

    private void loadSpecialCard() {
        Random random = new Random();
        int randomCardNumber = random.nextInt(NUM_SPECIAL_CARDS) + 1;
        String imagePath = String.format(SPECIAL_CARD_PATH, randomCardNumber);
        Image specialCardImage = new Image(getClass().getResourceAsStream(imagePath));

        this.specialImageView = new ImageView(specialCardImage);
        this.specialImageView.setPreserveRatio(true);
        double cardFrontWidth = specialCardImage.getWidth();
        double cardFrontHeight = specialCardImage.getHeight();
        double aspectRatio = cardFrontWidth / cardFrontHeight;
        double cardWidth = GameView.getWindowHeight() / 4.0 * aspectRatio;

        this.specialImageView.setFitWidth(cardWidth);
    }

    private void initializeHealthBar(Pane transparentPane) {

        StackPane healthBarContainer = new StackPane();
        healthBarContainer.setMaxHeight(20);
        healthBarContainer.setTranslateX(280);
        healthBarContainer.setTranslateY(1030);

        this.healthBar = new ProgressBar(this.player.getHealth() / 100.0);
        healthBarContainer.getTransforms().add(new Rotate(270, 0, 0));

        double adaptedWidth = GameView.getWindowHeight() / 5.0;
        this.healthBar.setPrefWidth(adaptedWidth);

        this.healthBar.setStyle("-fx-accent: #FF3300;");

        healthBarContainer.setStyle(
                "-fx-border-color: #FF6600; " +
                "-fx-border-width: 2; " +
                "-fx-background-color: linear-gradient(to bottom, #FF3300, #FF6600);"
        );
        healthBarContainer.getChildren().add(this.healthBar);

        this.healthText = new Text();
        this.healthText.setFill(Color.BLACK);
        this.healthText.setFont(Font.font(20));
        this.updateHealthText(); 

        this.healthText.setTranslateX(280); 
        this.healthText.setTranslateY(1050); 

        transparentPane.getChildren().addAll(healthBarContainer, this.healthText);
        this.healthBar.setVisible(true);
        healthBarContainer.setVisible(true);
        this.healthText.setVisible(true);
    }

    public void updateHealthBarAndText() {
        Platform.runLater(() -> {
            this.healthBar.setProgress(this.player.getHealth() / 100.0);
            this.updateHealthText();
        });
    }

    public void updateHealthText() {
        Platform.runLater(() -> this.healthText.setText(String.valueOf(this.player.getHealth())));
    }
}
