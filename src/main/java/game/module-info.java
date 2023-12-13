module dynamicduel {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media; 
    requires transitive javafx.graphics;

    opens game to javafx.fxml;
    exports game;
}
