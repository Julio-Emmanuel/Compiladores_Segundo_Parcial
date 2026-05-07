package fes.aragon.tablerointerprete.modelo;


public class InstruccionAsignacion extends Instruccion {
    private String variable;
    private int valor;

    public InstruccionAsignacion(String variable, int valor, int linea, int columna) {
        super(linea, columna);
        this.variable = variable;
        this.valor = valor;
    }

    @Override
    public void ejecutar(ContextoJuego contexto) {
        contexto.setVariable(variable, valor);
        System.out.println("Variable " + variable + " = " + valor);
    }

    @Override
    public String toIntermediateCode() {
        return "SET " + variable + " = " + valor;
    }
}