module mx.unam.programa10 {
    requires javafx.controls;
    requires javafx.fxml;


    opens mx.unam.programa10 to javafx.fxml;
    exports mx.unam.programa10;
    exports mx.unam.programa10.controlador;
    opens mx.unam.programa10.controlador to javafx.fxml;
}