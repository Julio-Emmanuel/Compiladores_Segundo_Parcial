module aragon.mikesharp {
    requires javafx.controls;
    requires javafx.fxml;


    opens aragon.mikesharp to javafx.fxml;

    exports aragon.mikesharp.main;
    opens aragon.mikesharp.main to javafx.fxml;
    exports aragon.mikesharp.controller;
    opens aragon.mikesharp.controller to javafx.fxml;
}