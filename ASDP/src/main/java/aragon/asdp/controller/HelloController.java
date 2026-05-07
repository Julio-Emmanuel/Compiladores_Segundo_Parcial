package aragon.asdp.controller;

import aragon.asdp.model.Paso;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Stack;



public class HelloController {
    @FXML private TextField txtEntrada;
    @FXML private TableView<Paso> tablaAnalisis;
    @FXML private TableColumn<Paso, String> colPila;
    @FXML private TableColumn<Paso, String> colSimbolos;
    @FXML private TableColumn<Paso, String> colEntrada;
    @FXML private TableColumn<Paso, String> colAccion;

    private final ObservableList<Paso> listaPasos = FXCollections.observableArrayList();

    private final String[][] tablaACCION = {
            {"D5", "", "", "D4", "", ""},
            {"", "D6", "", "", "", "ACEPTAR"},
            {"", "R2", "D7", "", "R2", "R2"},
            {"", "R4", "R4", "", "R4", "R4"},
            {"D5", "", "", "D4", "", ""},
            {"", "R6", "R6", "", "R6", "R6"},
            {"D5", "", "", "D4", "", ""},
            {"D5", "", "", "D4", "", ""},
            {"", "D6", "", "", "D11", ""},
            {"", "R1", "D7", "", "R1", "R1"},
            {"", "R3", "R3", "", "R3", "R3"},
            {"", "R5", "R5", "", "R5", "R5"}
    };

    private final int[][] tablaGOTO = {
            {1, 2, 3},
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0},
            {8, 2, 3},
            {0, 0, 0},
            {0, 9, 3},
            {0, 0, 10},
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };

    private final String[] reglasLadoIzquierdo = {"", "E", "E", "T", "T", "F", "F"};
    private final int[] reglasLongitud = {0, 3, 1, 3, 1, 3, 1};

    @FXML
    public void initialize() {
        colPila.setCellValueFactory(new PropertyValueFactory<>("pila"));
        colSimbolos.setCellValueFactory(new PropertyValueFactory<>("simbolos"));
        colEntrada.setCellValueFactory(new PropertyValueFactory<>("entrada"));
        colAccion.setCellValueFactory(new PropertyValueFactory<>("accion"));
        tablaAnalisis.setItems(listaPasos);
    }

    @FXML
    protected void analizar() {
        listaPasos.clear();
        String textoOriginal = txtEntrada.getText().trim();
        if (textoOriginal.isEmpty()) return;

        String entrada = textoOriginal + ";";
        Stack<Integer> pilaEstados = new Stack<>();
        Stack<String> pilaSimbolos = new Stack<>();
        pilaEstados.push(0);
        pilaSimbolos.push(";");

        int puntero = 0;
        boolean terminar = false;

        while (!terminar) {
            int estadoActual = pilaEstados.peek();
            String tokenActual = obtenerToken(entrada, puntero);
            int col = obtenerColumna(tokenActual);

            if (col == -1) {
                listaPasos.add(new Paso(pilaEstados.toString(), pilaSimbolos.toString(), entrada.substring(puntero), "ERROR: Símbolo no válido"));
                break;
            }

            String accion = tablaACCION[estadoActual][col];

            if (accion.isEmpty()) {
                listaPasos.add(new Paso(pilaEstados.toString(), pilaSimbolos.toString(), entrada.substring(puntero), "ERROR: Salto vacío"));
                break;
            }

            listaPasos.add(new Paso(pilaEstados.toString(), pilaSimbolos.toString(), entrada.substring(puntero), accion));

            if (accion.startsWith("D")) {
                int nuevoEstado = Integer.parseInt(accion.substring(1));
                pilaEstados.push(nuevoEstado);
                pilaSimbolos.push(tokenActual);
                puntero += tokenActual.length();
            } else if (accion.startsWith("R")) {
                int numRegla = Integer.parseInt(accion.substring(1));
                for (int i = 0; i < reglasLongitud[numRegla]; i++) {
                    pilaEstados.pop();
                    pilaSimbolos.pop();
                }
                int estadoTope = pilaEstados.peek();
                String izq = reglasLadoIzquierdo[numRegla];
                pilaEstados.push(tablaGOTO[estadoTope][obtenerColumnaGOTO(izq)]);
                pilaSimbolos.push(izq);
            } else if (accion.equals("ACEPTAR")) {
                terminar = true;
            } else {
                terminar = true;
            }
        }
    }

    private String obtenerToken(String s, int p) {
        if (s.startsWith("id", p)) return "id";
        return String.valueOf(s.charAt(p));
    }

    private int obtenerColumna(String t) {
        switch (t) {
            case "id": return 0;
            case "+": return 1;
            case "*": return 2;
            case "(": return 3;
            case ")": return 4;
            case ";": return 5;
            default: return -1;
        }
    }

    private int obtenerColumnaGOTO(String s) {
        switch (s) {
            case "E": return 0;
            case "T": return 1;
            case "F": return 2;
            default: return -1;
        }
    }
}