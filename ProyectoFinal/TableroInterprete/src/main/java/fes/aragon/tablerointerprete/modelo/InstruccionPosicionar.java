package fes.aragon.tablerointerprete.modelo;



public class InstruccionPosicionar extends Instruccion {
    private int celdaX;  // Coordenada X en el grid (1-20)
    private int celdaY;  // Coordenada Y en el grid (1-20)

    public InstruccionPosicionar(int celdaX, int celdaY, int linea, int columna) {
        super(linea, columna);
        this.celdaX = celdaX;
        this.celdaY = celdaY;
    }

    @Override
    public void ejecutar(ContextoJuego contexto) {
        // Convertir coordenadas de celda (1-20) a píxeles (50-1050)
        int pixelX = 50 + (celdaX - 1) * 50;
        int pixelY = 50 + (celdaY - 1) * 50;

        contexto.posicionarNave(pixelX, pixelY);
        System.out.println("Nave posicionada en celda: (" + celdaX + "," + celdaY +
                ") -> Píxeles: (" + pixelX + "," + pixelY + ")");
    }

    @Override
    public String toIntermediateCode() {
        return "POS " + celdaX + " " + celdaY;
    }
}