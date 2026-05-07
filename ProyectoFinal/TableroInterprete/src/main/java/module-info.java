module fes.aragon.tablerointerprete {
    requires javafx.controls;
    requires javafx.fxml;
    requires jlayer;
    requires java.desktop;
    requires javaCup;

    // Abre los paquetes para JavaFX (¡ESTO ES CRUCIAL!)
    opens fes.aragon.tablerointerprete to javafx.fxml;
    opens fes.aragon.tablerointerprete.inicio to javafx.fxml;
    opens fes.aragon.tablerointerprete.controlador to javafx.fxml;  // ← ¡AGREGA ESTA LÍNEA!
    opens fes.aragon.tablerointerprete.modelo to javafx.base;

    // Exporta los paquetes
    exports fes.aragon.tablerointerprete;
    exports fes.aragon.tablerointerprete.inicio;
    exports fes.aragon.tablerointerprete.analizador;
    exports fes.aragon.tablerointerprete.modelo;
    exports fes.aragon.tablerointerprete.controlador;
    exports fes.aragon.tablerointerprete.extras;
}