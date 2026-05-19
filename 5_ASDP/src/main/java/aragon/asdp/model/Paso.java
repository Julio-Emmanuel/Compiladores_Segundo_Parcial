package aragon.asdp.model;



public class Paso {
    private String pila;
    private String simbolos;
    private String entrada;
    private String accion;

    public Paso(String pila, String simbolos, String entrada, String accion) {
        this.pila = pila;
        this.simbolos = simbolos;
        this.entrada = entrada;
        this.accion = accion;
    }

    public String getPila() { return pila; }
    public String getSimbolos() { return simbolos; }
    public String getEntrada() { return entrada; }
    public String getAccion() { return accion; }
}