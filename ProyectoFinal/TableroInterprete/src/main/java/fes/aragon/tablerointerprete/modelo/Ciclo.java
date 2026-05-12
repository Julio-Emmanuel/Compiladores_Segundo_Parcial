package fes.aragon.tablerointerprete.modelo;

import java.util.List;

public class Ciclo extends Instruccion {
    private String variable;
    private int desde;
    private int hasta;
    private List<Instruccion> bloque;

    public Ciclo(String variable, int desde, List<Instruccion> bloque,
                 String variableFin, int hasta, int linea, int columna) {
        super(linea, columna);
        this.variable = variable;
        this.desde = desde;
        this.hasta = hasta;
        this.bloque = bloque;
    }

    @Override
    public void ejecutar(ContextoJuego contexto) {
        // Obtener el valor actual de la variable o usar el valor por defecto
        int inicio = contexto.getVariable(variable);
        if (inicio == 0 && desde != 0) {
            inicio = desde;
        }

        for(int i = inicio; i <= hasta; i++) {
            contexto.setVariable(variable, i);
            System.out.println("Ciclo " + variable + " = " + i);
            for(Instruccion inst : bloque) {
                inst.ejecutar(contexto);
            }
        }
    }

    @Override
    public String toIntermediateCode() {
        return "REP [" + variable + "=" + desde + ".." + hasta + "]";
    }
}

