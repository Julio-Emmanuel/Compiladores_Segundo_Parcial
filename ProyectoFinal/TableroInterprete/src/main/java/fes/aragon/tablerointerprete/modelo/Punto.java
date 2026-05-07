package fes.aragon.tablerointerprete.modelo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Punto {
    private int x, y;
    private boolean recolectado = false;

    public Punto(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void pintar(GraphicsContext gc) {
        if(!recolectado) {
            gc.setFill(Color.BLACK);
            gc.fillOval(x + 15, y + 15, 20, 20);
        }
    }

    // Getters y Setters
    public int getX() { return x; }
    public int getY() { return y; }
    public void setRecolectado(boolean recolectado) { this.recolectado = recolectado; }
}