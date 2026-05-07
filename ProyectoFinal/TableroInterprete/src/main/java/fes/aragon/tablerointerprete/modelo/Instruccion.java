package fes.aragon.tablerointerprete.modelo;

public abstract class Instruccion {
    protected int linea;
    protected int columna;

    public Instruccion(int linea, int columna) {
        this.linea = linea;
        this.columna = columna;
    }

    public abstract void ejecutar(ContextoJuego contexto);
    public abstract String toIntermediateCode();
}
