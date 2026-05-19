module mx.unam.programa_11 {
    requires javafx.controls;
    requires javafx.fxml;


    opens mx.unam.programa_11 to javafx.fxml;
    exports mx.unam.programa_11;
    exports mx.unam.programa_11.controlador;
    opens mx.unam.programa_11.controlador to javafx.fxml;
}