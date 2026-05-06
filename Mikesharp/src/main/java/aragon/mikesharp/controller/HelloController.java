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
            analzadorLexico(selectedFile);
        }
    }

    private void analzadorLexico(File file) {
        resultArea.clear();
        try (Reader reader = new FileReader(file)) {

            Lexer lexer = new Lexer(reader);


            PrintStream oldOut = System.out;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));


            while (lexer.yylex() != Lexer.YYEOF) {

            }


            System.setOut(oldOut);
            String output = baos.toString();

            if (output.isEmpty()) {
                resultArea.setText("Análisis completado. No se detectaron tokens o el archivo está vacío.");
            } else {
                resultArea.setText("--- Resultado del Análisis Lexico ---\n" + output);
            }

        } catch (IOException e) {
            resultArea.setText("Error al leer el archivo: " + e.getMessage());
        } catch (Error e) {

            resultArea.setText("ERROR LÉXICO DETECTADO:\n" + e.getMessage());
        }
    }
}