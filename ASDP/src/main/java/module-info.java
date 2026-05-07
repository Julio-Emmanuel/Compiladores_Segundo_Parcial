module aragon.asdp {
    requires javafx.controls;
    requires javafx.fxml;


    opens aragon.asdp to javafx.fxml;
    opens aragon.asdp.main to javafx.fxml;
    opens aragon.asdp.controller to javafx.fxml;


    opens aragon.asdp.model to javafx.base;


    exports aragon.asdp.main;
    exports aragon.asdp.controller;
    exports aragon.asdp.model;
}