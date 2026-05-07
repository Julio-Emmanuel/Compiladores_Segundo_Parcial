package fes.aragon.tablerointerprete.modelo;

public class InstruccionMovimiento extends Instruccion {
    private String direccion;

    public InstruccionMovimiento(String direccion, int linea, int columna) {
        super(linea, columna);
        this.direccion = direccion;
    }

    @Override
    public void ejecutar(ContextoJuego contexto) {
        contexto.cambiarDireccion(direccion);
    }

    @Override
    public String toIntermediateCode() {
        switch(direccion) {
            case "arriba": return "ARR";
            case "abajo": return "ABA";
            case "izquierda": return "IZQ";
            case "derecha": return "DER";
            default: return "";
        }
    }
}