module mx.unam.programa_9 {
    requires javafx.controls;
    requires javafx.fxml;


    opens mx.unam.programa_9 to javafx.fxml;
    exports mx.unam.programa_9;
    exports mx.unam.programa_9.controlador;
    opens mx.unam.programa_9.controlador to javafx.fxml;
}