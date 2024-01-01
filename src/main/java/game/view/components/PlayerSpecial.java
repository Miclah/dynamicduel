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

/**
 * Special card display (not yet implemented) for the player in the game view, including health bar and card image.
 */
public class PlayerSpecial extends StackPane {

    // Path format for special card images
    private static final String SPECIAL_CARD_PATH = "/Cards/card_textures/special/special_%d.png";
    // Number of available special cards
    private static final int NUM_SPECIAL_CARDS = 8;

    // Components for the player's special card display
    private Rectangle outline;
    private ImageView specialImageView;
    private ProgressBar healthBar;
    private Player player;
    private Text healthText;

    /**
     * Constructs the PlayerSpecial object.
     *
     * @param height          The height of the player's special card display.
     * @param screenWidth     The width of the screen.
     * @param screenHeight    The height of the screen.
     * @param outlineColor    The color of the outline.
     * @param player          The player associated with the special card.
     * @param transparentPane The transparent pane for layout.
     */
    public PlayerSpecial(double height, double screenWidth, double screenHeight, Color outlineColor, Player player, Pane transparentPane) {
        this.player = player;
    

        // Create the outline for the special card area
        this.outline = new Rectangle(screenWidth - 1600, height, Color.TRANSPARENT);
        this.outline.setStroke(outlineColor);
        this.outline.setStrokeWidth(2);

        // Set the position of the special card display
        setTranslateX(height / 2);
        setTranslateY(screenHeight - height / 2);

        // Load the special card image and initialize the health bar
        this.loadSpecialCard();
        this.initializeHealthBar(transparentPane);

        // Create the whole pane for the special card display
        StackPane wholePane = new StackPane(this.outline, this.specialImageView);
        wholePane.setAlignment(Pos.CENTER);

        // Add the whole pane to the PlayerSpecial StackPane
        getChildren().add(wholePane);
    }

    /**
     * Loads a random special card image for the player.
     */
    private void loadSpecialCard() {
        Random random = new Random();
        int randomCardNumber = random.nextInt(NUM_SPECIAL_CARDS) + 1;
        String imagePath = String.format(SPECIAL_CARD_PATH, randomCardNumber);
        Image specialCardImage = new Image(getClass().getResourceAsStream(imagePath));

        // Create the ImageView for the special card
        this.specialImageView = new ImageView(specialCardImage);
        this.specialImageView.setPreserveRatio(true);
        double cardFrontWidth = specialCardImage.getWidth();
        double cardFrontHeight = specialCardImage.getHeight();
        double aspectRatio = cardFrontWidth / cardFrontHeight;
        double cardWidth = GameView.getWindowHeight() / 4.0 * aspectRatio;

        this.specialImageView.setFitWidth(cardWidth);
    }

    /**
     * Initializes the health bar and associated components.
     *
     * @param transparentPane The transparent pane for layout.
     */
    private void initializeHealthBar(Pane transparentPane) {
        // Create a container for the health bar
        StackPane healthBarContainer = new StackPane();
        healthBarContainer.setMaxHeight(20);
        healthBarContainer.setTranslateX(280);
        healthBarContainer.setTranslateY(1030);

        // Create the health bar with proper rotation, vertical
        this.healthBar = new ProgressBar(this.player.getHealth() / 100.0);
        healthBarContainer.getTransforms().add(new Rotate(270, 0, 0));

        // Adapt the width of the health bar
        double adaptedWidth = GameView.getWindowHeight() / 5.0;
        this.healthBar.setPrefWidth(adaptedWidth);

        // Set the color and style of the health bar container
        this.healthBar.setStyle("-fx-accent: #FF3300;");
        healthBarContainer.setStyle(
                "-fx-border-color: #FF6600; " +
                        "-fx-border-width: 2; " +
                        "-fx-background-color: linear-gradient(to bottom, #FF3300, #FF6600);"
        );
        healthBarContainer.getChildren().add(this.healthBar);

        // Create the health text
        this.healthText = new Text();
        this.healthText.setFill(Color.BLACK);
        this.healthText.setFont(Font.font(20));
        this.updateHealthText();

        // Set the position of the health text
        this.healthText.setTranslateX(280);
        this.healthText.setTranslateY(1050);

        // Add health bar and text to the transparent pane
        transparentPane.getChildren().addAll(healthBarContainer, this.healthText);
        this.healthBar.setVisible(true);
        healthBarContainer.setVisible(true);
        this.healthText.setVisible(true);
    }

    /**
     * Updates the health bar and associated text
     */
    public void updatePlayerInterface() {
        Platform.runLater(() -> {
            this.healthBar.setProgress(this.player.getHealth() / 100.0);
            this.updateHealthText();
        });
    }

    /**
     * Updates the health text
     */
    public void updateHealthText() {
        Platform.runLater(() -> this.healthText.setText(String.valueOf(this.player.getHealth())));
    }
}
