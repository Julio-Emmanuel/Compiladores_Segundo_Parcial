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
import java.util.List;

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
            String codigoTexto = txtCodigo.getText();

            // 1. Analizar el código
            ByteArrayInputStream stream = new ByteArrayInputStream(codigoTexto.getBytes());
            AnalizadorLexico lexico = new AnalizadorLexico(stream);
            AnalizadorSintactico parser = new AnalizadorSintactico(lexico);
            parser.parse();

            // 2. Limpiar mensajes y mostrar código intermedio
            txtMensajes.clear();
            txtCodigoIntermedio.clear();
            List<String> lineasIntermedias = parser.getCodigoIntermedio();

            for(String inter : lineasIntermedias) {
                txtCodigoIntermedio.appendText(inter + "\n");
            }

            // 3. INTÉRPRETE: Procesar la lista de Strings
            // IMPORTANTE: No toques la variable 'nave' antes de este ciclo
            for (String linea : lineasIntermedias) {
                String[] partes = linea.split(" ");
                String cmd = partes[0].toLowerCase();



                System.out.println("Comando recibido: " + linea);
                System.out.println("CMD = " + cmd);

                switch (cmd) {

                    case "i":
                    case "inicio":
                        int ix = Integer.parseInt(partes[1]);
                        int iy = Integer.parseInt(partes[2]);

                        nave.setPosition(
                                50 + (ix - 1) * 50,
                                50 + (iy - 1) * 50
                        );

                        txtMensajes.appendText(
                                "Posicionada en inicio: (" + ix + "," + iy + ")\n"
                        );
                        break;

                    case "ar":
                    case "arriba":
                        nave.cambiarDireccion("arriba");
                        break;

                    case "ab":
                    case "abajo":
                        nave.cambiarDireccion("abajo");
                        break;

                    case "iz":
                    case "izquierda":
                        nave.cambiarDireccion("izquierda");
                        break;

                    case "de":
                    case "derecha":
                        nave.cambiarDireccion("derecha");
                        break;

                    case "mv":
                    case "mover":
                        int pasos = Integer.parseInt(partes[1]);
                        nave.mover(nave.getDireccionActual(), pasos);
                        break;
                }
                // Dibujar después de cada comando para que no se quede estática
                dibujarGrid();
            }

            // 4. Verificar si la posición final coincide con el punto
            // Calculamos en qué celda quedó para el mensaje
            int finalCeldaX = (nave.getX() - 50) / 50 + 1;
            int finalCeldaY = (nave.getY() - 50) / 50 + 1;

            if (nave.getX() == punto.getX() && nave.getY() == punto.getY()) {
                txtMensajes.appendText(" ÉXITO Terminaste en: (" + finalCeldaX + "," + finalCeldaY + ")\n");
                mostrarDialogoExito();
            } else {
                txtMensajes.appendText(" No llegaste. Te detuviste en: (" + finalCeldaX + "," + finalCeldaY + ")\n");
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
        txtMensajes.clear();
        txtCodigoIntermedio.clear();
        txtMensajes.appendText("Juego reiniciado\n");
    }

    private void guardarCodigo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos FES", "*.fes"));
        File archivo = fileChooser.showSaveDialog(stage);
        if(archivo != null) {
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                writer.write(txtCodigo.getText());
            } catch(IOException e) { e.printStackTrace(); }
        }
    }

    private void cargarCodigo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos FES", "*.fes"));
        File archivo = fileChooser.showOpenDialog(stage);
        if(archivo != null) {
            try(BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                StringBuilder sb = new StringBuilder();
                String l;
                while((l = reader.readLine()) != null) sb.append(l).append("\n");
                txtCodigo.setText(sb.toString());
            } catch(IOException e) { e.printStackTrace(); }
        }
    }

    public void setStage(Stage stage) { this.stage = stage; }
}