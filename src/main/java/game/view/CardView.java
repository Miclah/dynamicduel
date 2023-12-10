package game.view;

import game.model.Card;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class CardView extends StackPane {
    private final Card card;
    private final ImageView frontImageView;
    private final ImageView backImageView;

    //todo: 
    //fix the loading of cards
    public CardView(Card card) {
        this.card = card;
        this.frontImageView = new ImageView(new Image(card.getImagePath()));
        this.backImageView = new ImageView(new Image("/resources/Cards/card_textures/card_back.png")); 

        this.initializeCardView();

       
        setOnMouseClicked(this::handleMouseClicked);
        setOnMouseDragged(this::handleMouseDragged);
    }

    private void initializeCardView() {

        setPrefWidth(100); 
        setPrefHeight(150); 

        getChildren().addAll(this.backImageView);

        this.frontImageView.setFitWidth(getPrefWidth());
        this.frontImageView.setFitHeight(getPrefHeight());

        this.backImageView.setFitWidth(getPrefWidth());
        this.backImageView.setFitHeight(getPrefHeight());

        this.frontImageView.setVisible(false);

        getChildren().addAll(this.frontImageView);

        setStyle("-fx-background-color: white; -fx-border-color: black;");
    }

    private void handleMouseClicked(MouseEvent event) {
        this.card.flip();
        this.updateCardAppearance();
    }

    private void handleMouseDragged(MouseEvent event) {

        relocate(event.getSceneX() - getWidth() / 2, event.getSceneY() - getHeight() / 2);
    }

    private void updateCardAppearance() {

        this.frontImageView.setVisible(this.card.isFaceUp());
        this.backImageView.setVisible(!this.card.isFaceUp());
    }
}
