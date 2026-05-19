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
            {"D2", "R3", "", "", ""},      // Estado 0: Inicio. Si ve 'a' desplaza, si ve 'b' reduce A->lambda
            {"", "", "", "", "ACEPTAR"},   // Estado 1: S aceptada
            {"", "R2", "", "", ""},        // Estado 2: A -> a. Reduce.
            {"", "D4", "", "", ""},        // Estado 3: S -> A. Espera B.
            {"", "", "D6", "R6", ""},      // Estado 4: B -> b. Espera C o d.
            {"", "", "", "", "R1"},        // Estado 5: S -> AB. Reduce.
            {"", "", "", "R5", ""},        // Estado 6: C -> c. Reduce.
            {"", "", "", "D8", ""},        // Estado 7: B -> bC. Espera d.
            {"", "", "", "", "R4"}         // Estado 8: B -> bCd. Reduce.
    };


    private final int[][] tablaGOTO = {
            {1, 3, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 5, 0},
            {0, 0, 0, 7},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    };

    private final String[] reglasLadoIzquierdo = {"", "S", "A", "A", "B", "C", "C"};
    private final int[] reglasLongitud = {0, 2, 1, 0, 3, 1, 0};

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
            case "a": return 0;
            case "b": return 1;
            case "c": return 2;
            case "d": return 3;
            case ";": return 4;
            default: return -1;
        }
    }

    private int obtenerColumnaGOTO(String s) {
        switch (s) {
            case "S": return 0;
            case "A": return 1;
            case "B": return 2;
            case "C": return 3;
            default: return -1;
        }
    }
}