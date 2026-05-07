package fes.aragon.tablerointerprete.modelo;

public class InstruccionMover extends Instruccion {
    private int pasos;

    public InstruccionMover(int pasos, int linea, int columna) {
        super(linea, columna);
        this.pasos = pasos;
    }

    @Override
    public void ejecutar(ContextoJuego contexto) {
        contexto.mover(pasos);
    }

    @Override
    public String toIntermediateCode() {
        return "MV " + pasos;
    }
}
