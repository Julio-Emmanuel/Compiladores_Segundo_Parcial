package aragon.asdp.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        URL fxmlLocation = HelloApplication.class.getResource("/aragon/asdp/hello-view.fxml");

        if (fxmlLocation == null) {
            throw new IllegalStateException("No se encontró el archivo FXML en la ruta especificada");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setTitle("Analizador SLR");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}