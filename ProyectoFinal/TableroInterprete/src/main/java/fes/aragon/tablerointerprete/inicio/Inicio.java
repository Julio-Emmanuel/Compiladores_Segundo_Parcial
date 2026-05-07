package fes.aragon.tablerointerprete.inicio;

import fes.aragon.tablerointerprete.controlador.ControladorJuego;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Inicio extends Application {

    private Stage ventanaPrincipal;

    @Override
    public void start(Stage ventana) {
        this.ventanaPrincipal = ventana;

        try {
            // Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fes/aragon/tablerointerprete/juego.fxml"));
            Parent root = loader.load();

            // Obtener el controlador y pasarle el stage
            ControladorJuego controlador = loader.getController();
            controlador.setStage(ventana);

            // Configurar la escena
            Scene scene = new Scene(root, 1400, 1100);
            ventana.setTitle("Compilador - Juego de la Nave");
            ventana.setScene(scene);
            ventana.setResizable(false);
            ventana.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al cargar la interfaz: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}