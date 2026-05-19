package mx.unam.programa10.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import mx.unam.programa10.modelo.Parser;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class controlador {
    @FXML
    private TextFlow txtArchivo;
    @FXML
    private TextFlow txtResultado;
    @FXML
    private TextField txtEntrada;
    private List<String> lineasArchivo;

    @FXML
    public void cargarArchivo() {
        try {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(
                            "Archivos de texto",
                            "*.txt"
                    )
            );
            File archivo = fc.showOpenDialog(null);
            if (archivo != null) {
                lineasArchivo = Files.readAllLines(archivo.toPath());
                txtArchivo.getChildren().clear();
                for (String linea : lineasArchivo) {
                    Text texto = new Text(linea + "\n");
                    txtArchivo.getChildren().add(texto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void evaluar() {
        txtResultado.getChildren().clear();
        String manual = txtEntrada.getText();
        if (manual != null && !manual.trim().isEmpty()) {
            evaluarLinea(manual, 0);
        }

        if (lineasArchivo != null) {
            int lineaNumero = 1;
            for (String linea : lineasArchivo) {
                if (linea.trim().isEmpty()) {
                    lineaNumero++;
                    continue;
                }
                evaluarLinea(linea, lineaNumero);
                lineaNumero++;
            }
        }
    }

    private void evaluarLinea(String linea, int numeroLinea) {

        String[] expresiones = linea.split(";");
        int contadorExpr = 1;
        for (String expr : expresiones) {
            expr = expr.trim();
            if (expr.isEmpty()) {
                continue;
            }
            expr = expr + ";";
            Parser parser = new Parser(expr);
            String resultado = parser.analizar();
            Text texto;
            String encabezado;
            if (numeroLinea == 0) {
                encabezado =
                        "[Manual - Expr " + contadorExpr + "] ";
            } else {
                encabezado =
                        "[Línea " + numeroLinea +
                                " - Expr " + contadorExpr + "] ";
            }
            if (resultado.equals("VÁLIDA")) {
                texto = new Text(
                        encabezado +
                                expr +
                                " -> VÁLIDA\n"
                );
                texto.setFill(Color.GREEN);
            }
            else {
                texto = new Text(
                        encabezado +
                                expr +
                                " -> " +
                                resultado +
                                "\n"
                );

                texto.setFill(Color.RED);
            }
            txtResultado.getChildren().add(texto);
            contadorExpr++;
        }
    }
}