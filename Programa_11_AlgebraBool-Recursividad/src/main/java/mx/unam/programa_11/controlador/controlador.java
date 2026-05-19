package mx.unam.programa_11.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mx.unam.programa_11.modelo.*;

public class controlador {


    @FXML
    private TextField txtInput;
    @FXML
    private TextFlow flowArchivo;
    @FXML
    private TextFlow flowResultado;

    private List<String> lineasArchivo = new ArrayList<>();


    @FXML
    private void cargarArchivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Texto", "*.txt"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            flowArchivo.getChildren().clear();
            lineasArchivo.clear();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    if (!linea.trim().isEmpty()) {
                        lineasArchivo.add(linea.trim());
                        Text text = new Text(linea + "\n");
                        flowArchivo.getChildren().add(text);
                    }
                }
            } catch (IOException e) {
                agregarMensajeError(flowArchivo, "Error al leer archivo.");
            }
        }
    }


    @FXML
    private void Evaluar() {
        flowResultado.getChildren().clear();
        boolean hayEntradaManual = !txtInput.getText().trim().isEmpty();
        boolean hayArchivo = !lineasArchivo.isEmpty();

        if (!hayEntradaManual && !hayArchivo) {
            agregarTitulo("No hay datos para evaluar", Color.GRAY);
            return;
        }


        if (hayEntradaManual) {
            agregarTitulo("--- RESULTADO ENTRADA MANUAL ---", Color.DARKSLATEBLUE);
            validarSintaxis(txtInput.getText().trim());
        }


        if (hayArchivo) {
            agregarTitulo("\n--- RESULTADOS DEL ARCHIVO ---", Color.DARKSLATEBLUE);
            for (String expresion : lineasArchivo) {
                validarSintaxis(expresion);
            }
        }
    }

    private void mostrarEnPantalla(String exp, String msg, Color color) {
        Text tExp = new Text(exp + "\n");

        flowResultado.getChildren().add(tExp);
        Text tMsg = new Text(msg + "\n");
        tMsg.setFill(color);

        flowResultado.getChildren().addAll(tMsg, new Text("------------------------------------------\n"));
    }

    private void agregarTitulo(String titulo, Color color) {
        Text t = new Text(titulo + "\n");
        t.setFill(color);
        flowResultado.getChildren().add(t);
    }

    private void validarSintaxis(String cadenaCompleta) {
        String[] expresiones = cadenaCompleta.split("(?<=;)");

        for (String expresion : expresiones) {
            String expLimpia = expresion.trim();
            if (expLimpia.isEmpty()) continue;

            try {
                Lexer lexer = new Lexer(expLimpia);
                Parser parser = new Parser(lexer.getTokens());
                parser.s();

                mostrarEnPantalla(expLimpia, "Sintaxis Correcta", Color.GREEN);
            } catch (SintaxisException e) {
                mostrarEnPantalla(expLimpia, e.getMessage(), Color.RED);
            } catch (Exception e) {
                mostrarEnPantalla(expLimpia, "Error: " + e.getMessage(), Color.ORANGERED);
            }
        }
    }

    private void agregarMensajeError(TextFlow flow, String msg) {
        Text error = new Text(msg + "\n");
        error.setFill(Color.RED);
        flow.getChildren().add(error);
    }

}