module com.base.game.physicsexplorer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;
    exports com.base.game.physicsexplorer.MainMenu;
    opens com.base.game.physicsexplorer.MainMenu to javafx.fxml;
}