package aragon.mikesharp.controller;

import aragon.mikesharp.model.Lexer;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import java.io.*;

public class HelloController {

    @FXML
    private TextArea resultArea;

    @FXML
    protected void onUploadFileClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            analizadorLexico(selectedFile);
        }
    }

    private void analizadorLexico(File file) {
        resultArea.clear();
        StringBuilder reporteFinal = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int numeroLinea = 1;

            while ((line = br.readLine()) != null) {
                // Saltamos líneas vacías
                if (line.trim().isEmpty()) {
                    reporteFinal.append("Línea ").append(numeroLinea).append(": (Vacía)\n");
                    numeroLinea++;
                    continue;
                }

                // Analizamos la línea actual
                try (Reader lineReader = new StringReader(line)) {
                    Lexer lexer = new Lexer(lineReader);

                    // Capturamos la salida de esta línea específica
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(baos);
                    PrintStream oldOut = System.out;
                    System.setOut(ps);

                    // Ejecutamos el lexer para los tokens de esta línea
                    while (lexer.yylex() != Lexer.YYEOF) { }

                    System.setOut(oldOut);
                    String tokensEncontrados = baos.toString().trim();

                    // Si no hubo errores léxicos, la línea es válida para el Lexer
                    reporteFinal.append("Línea ").append(numeroLinea)
                            .append(": [VÁLIDA] -> ")
                            .append(tokensEncontrados.replace("\n", ", "))
                            .append("\n");

                } catch (Error e) {
                    // Si el lexer lanza un Error (por la regla "."), la línea es inválida
                    reporteFinal.append("Línea ").append(numeroLinea)
                            .append(": [ERROR LÉXICO] -> ")
                            .append(e.getMessage())
                            .append("\n");
                }

                numeroLinea++;
            }

            resultArea.setText("--- Reporte de Validación por Línea ---\n" + reporteFinal.toString());

        } catch (IOException e) {
            resultArea.setText("Error al procesar el archivo: " + e.getMessage());
        }
    }
}