module com.base.game.retrorampage {
    requires javafx.controls;
    requires javafx.fxml;
    exports com.base.game.physicsexplorer.MainMenu;
    opens com.base.game.physicsexplorer.MainMenu to javafx.fxml;
}