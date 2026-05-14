module mx.unam.programa_8 {
    requires javafx.controls;
    requires javafx.fxml;


    opens mx.unam.programa_8 to javafx.fxml;
    exports mx.unam.programa_8;
    exports mx.unam.programa_8.controlador;
    opens mx.unam.programa_8.controlador to javafx.fxml;
}