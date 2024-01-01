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
import game.model.AI;
import game.view.GameView;

import java.util.Random;

/**
 * Oopponent's special card (not implemented) display, including health bar and image.
 */
public class OpponentSpecial extends StackPane {

    // Attributes for special card path and number of special cards
    private static final String SPECIAL_CARD_PATH = "/Cards/card_textures/special/special_%d.png";
    private static final int NUM_SPECIAL_CARDS = 8;

    private Rectangle outline;
    private ImageView specialImageView;
    private ProgressBar healthBar;
    private AI opponentAI;
    private Text healthText;

    /**
     * Constructs the OpponentSpecial object.
     *
     * @param height           The height of the special card display.
     * @param screenWidth      The width of the screen.
     * @param screenHeight     The height of the screen.
     * @param outlineColor     The color of the outline.
     * @param ai               The AI associated with the opponent.
     * @param transparentPane  The transparent pane to which health components are added.
     */
    public OpponentSpecial(double height, double screenWidth, double screenHeight, Color outlineColor, AI ai, Pane transparentPane) {
        this.opponentAI = ai;

        
        // Create an outline
        this.outline = new Rectangle(screenWidth - 1600, height, Color.TRANSPARENT);
        this.outline.setStroke(outlineColor);
        this.outline.setStrokeWidth(2);

        setTranslateX(height / 2);
        setTranslateY(height / 2);

        this.loadSpecialCard();
        this.initializeHealthBar(transparentPane);

        // StackPane to combine outline and special card image
        StackPane wholePane = new StackPane(this.outline, this.specialImageView);
        wholePane.setAlignment(Pos.CENTER);

        getChildren().add(wholePane);
    }

    /**
     * Loads a random special card image.
     * TODO: Loads a special card chosen by the AI
     */
    private void loadSpecialCard() {
        Random random = new Random();
        int randomCardNumber = random.nextInt(NUM_SPECIAL_CARDS) + 1;
        String imagePath = String.format(SPECIAL_CARD_PATH, randomCardNumber);
        Image specialCardImage = new Image(getClass().getResourceAsStream(imagePath));

        this.specialImageView = new ImageView(specialCardImage);
        this.specialImageView.setPreserveRatio(true);
        double cardBackWidth = specialCardImage.getWidth();
        double cardBackHeight = specialCardImage.getHeight();
        double aspectRatio = cardBackWidth / cardBackHeight;
        double cardWidth = GameView.getWindowHeight() / 4.0 * aspectRatio;

        this.specialImageView.setFitWidth(cardWidth);
    }

    /**
     * Initializes the health bar and text components.
     *
     * @param transparentPane The transparent pane to which health components are added.
     */
    private void initializeHealthBar(Pane transparentPane) {
        StackPane healthBarContainer = new StackPane();
        healthBarContainer.setMaxHeight(20);
        healthBarContainer.setTranslateX(300);
        healthBarContainer.setTranslateY(55);

        // Health bar with a vertical orientation
        this.healthBar = new ProgressBar(this.opponentAI.getAIHealth() / 100.0);
        healthBarContainer.getTransforms().add(new Rotate(90, 0, 0));

        double adaptedWidth = GameView.getWindowHeight() / 5.0;
        this.healthBar.setPrefWidth(adaptedWidth);

        this.healthBar.setStyle("-fx-accent: #FF3300;");

        // Container styling
        healthBarContainer.setStyle(
                "-fx-border-color: #FF6600; " +
                        "-fx-border-width: 2; " +
                        "-fx-background-color: linear-gradient(to bottom, #FF3300, #FF6600);"
        );
        healthBarContainer.getChildren().add(this.healthBar);

        // Creates a text object to display the opponent's health
        this.healthText = new Text();
        this.healthText.setFill(Color.BLACK);
        this.healthText.setFont(Font.font(20));
        this.updateAIHealthText();

        this.healthText.setTranslateX(280);
        this.healthText.setTranslateY(50);

        transparentPane.getChildren().addAll(healthBarContainer, this.healthText);
        this.healthBar.setVisible(true);
        healthBarContainer.setVisible(true);
        this.healthText.setVisible(true);
    }

    /**
     * Updates the opponent's health bar and text.
     */
    public void updateAIInterface() {
        Platform.runLater(() -> {
            this.healthBar.setProgress(this.opponentAI.getAIHealth() / 100.0);
            this.updateAIHealthText();
        });
    }

    /**
     * Updates the opponent's health text.
     */
    private void updateAIHealthText() {
        Platform.runLater(() -> this.healthText.setText(String.valueOf(this.opponentAI.getAIHealth())));
    }
}
