package fes.aragon.tablerointerprete.controlador;

import fes.aragon.tablerointerprete.analizador.AnalizadorLexico;
import fes.aragon.tablerointerprete.analizador.AnalizadorSintactico;
import fes.aragon.tablerointerprete.modelo.*;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java_cup.runtime.Symbol;

public class ControladorJuego {
    @FXML private Canvas canvasJuego;
    @FXML private TextArea txtCodigo;
    @FXML private TextArea txtCodigoIntermedio;
    @FXML private TextArea txtMensajes;
    @FXML private Button btnEjecutar;
    @FXML private Button btnGuardar;
    @FXML private Button btnCargar;
    @FXML private Button btnReiniciar;

    private Nave nave;
    private Punto punto;
    private GraphicsContext gc;
    private Stage stage;

    @FXML
    public void initialize() {
        gc = canvasJuego.getGraphicsContext2D();
        inicializarJuego();
        dibujarGrid();

        btnEjecutar.setOnAction(e -> ejecutarCodigo());
        btnGuardar.setOnAction(e -> guardarCodigo());
        btnCargar.setOnAction(e -> cargarCodigo());
        btnReiniciar.setOnAction(e -> reiniciarJuego());
    }

    private void inicializarJuego() {
        nave = new Nave(50, 50);
        generarPuntoAleatorio();
        dibujarGrid();
    }

    private void generarPuntoAleatorio() {
        int fila = (int)(Math.random() * 19);
        int columna = (int)(Math.random() * 19);
        punto = new Punto(50 + columna * 50, 50 + fila * 50);
    }

    private void dibujarGrid() {
        gc.clearRect(0, 0, canvasJuego.getWidth(), canvasJuego.getHeight());
        gc.setStroke(Color.BLACK);
        for(int i = 0; i <= 20; i++) {
            gc.strokeLine(50 + i * 50, 50, 50 + i * 50, 1050);
            gc.strokeLine(50, 50 + i * 50, 1050, 50 + i * 50);
        }
        if(punto != null) punto.pintar(gc);
        if(nave != null) nave.pintar(gc);
    }

    private void ejecutarCodigo() {
        try {
            String codigo = txtCodigo.getText();

            // Parsear el código
            ByteArrayInputStream stream = new ByteArrayInputStream(codigo.getBytes());
            AnalizadorLexico lexico = new AnalizadorLexico(stream);
            AnalizadorSintactico parser = new AnalizadorSintactico(lexico);
            parser.parse();

            // Mostrar código intermedio
            txtCodigoIntermedio.clear();
            for(String inter : parser.getCodigoIntermedio()) {
                txtCodigoIntermedio.appendText(inter + "\n");
            }

            // Obtener coordenadas de inicio del parser
            int xInicio = parser.getPosicionXInicio();
            int yInicio = parser.getPosicionYInicio();

            // Posicionar la nave en la celda indicada
            int pixelX = 50 + (xInicio - 1) * 50;
            int pixelY = 50 + (yInicio - 1) * 50;
            nave = new Nave(pixelX, pixelY);

            txtMensajes.appendText("Nave iniciando en celda: (" + xInicio + "," + yInicio + ")\n");

            // El punto ya existe (no regenerar)
            int puntoCeldaX = (punto.getX() - 50) / 50 + 1;
            int puntoCeldaY = (punto.getY() - 50) / 50 + 1;
            txtMensajes.appendText("Objetivo: Punto en celda (" + puntoCeldaX + "," + puntoCeldaY + ")\n");

            // Ejecutar instrucciones (ya no hay POS)
            ContextoJuego contexto = new ContextoJuego(nave, punto);
            for(Instruccion inst : parser.getInstrucciones()) {
                inst.ejecutar(contexto);
                dibujarGrid();
                Thread.sleep(300);
            }

            // Verificar resultado
            if(contexto.isExito()) {
                txtMensajes.appendText("✅ ¡ÉXITO! Llegaste al punto\n");
            } else {
                txtMensajes.appendText("❌ No llegaste al punto\n");
            }

        } catch(Exception e) {
            txtMensajes.appendText("ERROR: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    private void mostrarDialogoExito() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Felicidades!");
        alert.setHeaderText(null);
        alert.setContentText("¡Lograste llegar al punto!\n¡Muy bien programando!");
        alert.showAndWait();
    }

    private void reiniciarJuego() {
        inicializarJuego();
        txtMensajes.appendText("Juego reiniciado\n");
    }

    private void guardarCodigo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos del Compilador", "*.fes"));
        fileChooser.setTitle("Guardar código");
        File archivo = fileChooser.showSaveDialog(stage);

        if(archivo != null) {
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                writer.write(txtCodigo.getText());
                txtMensajes.appendText("Código guardado en: " + archivo.getName() + "\n");
            } catch(IOException e) {
                txtMensajes.appendText("Error al guardar: " + e.getMessage() + "\n");
            }
        }
    }

    private void cargarCodigo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos del Compilador", "*.fes"));
        fileChooser.setTitle("Cargar código");
        File archivo = fileChooser.showOpenDialog(stage);

        if(archivo != null) {
            try(BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                StringBuilder contenido = new StringBuilder();
                String linea;
                while((linea = reader.readLine()) != null) {
                    contenido.append(linea).append("\n");
                }
                txtCodigo.setText(contenido.toString());
                txtMensajes.appendText("Código cargado desde: " + archivo.getName() + "\n");
            } catch(IOException e) {
                txtMensajes.appendText("Error al cargar: " + e.getMessage() + "\n");
            }
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}