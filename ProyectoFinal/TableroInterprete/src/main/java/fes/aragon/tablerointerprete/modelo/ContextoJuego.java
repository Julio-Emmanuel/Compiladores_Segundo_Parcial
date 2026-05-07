package fes.aragon.tablerointerprete.modelo;

import java.util.HashMap;
import java.util.Map;

public class ContextoJuego {
    private Nave nave;
    private Punto punto;
    private String direccionActual = "derecha";
    private Map<String, Integer> variables = new HashMap<>();
    private boolean exito = false;

    public ContextoJuego(Nave nave, Punto punto) {
        this.nave = nave;
        this.punto = punto;
    }

    public void posicionarNave(int x, int y) {
        nave.setPosition(x, y);
    }

    public void cambiarDireccion(String direccion) {
        this.direccionActual = direccion;
        nave.cambiarDireccion(direccion);
    }

    public void mover(int pasos) {
        // Mover UN PASO a la vez para detectar colisión en el camino
        for (int i = 0; i < pasos; i++) {
            nave.moverUnPaso(direccionActual);
            System.out.println("Paso " + (i+1) + ": Nave en (" + nave.getX() + "," + nave.getY() + ")");

            if (verificarColision()) {
                exito = true;
                punto.setRecolectado(true);
                System.out.println("¡PUNTO ALCANZADO en paso " + (i+1) + "!");
                return;
            }
        }
    }

    private boolean verificarColision() {
        // Verificar por CELDA (más preciso que por píxeles)
        int celdaNaveX = (nave.getX() - 50) / 50;
        int celdaNaveY = (nave.getY() - 50) / 50;
        int celdaPuntoX = (punto.getX() - 50) / 50;
        int celdaPuntoY = (punto.getY() - 50) / 50;

        boolean colision = (celdaNaveX == celdaPuntoX && celdaNaveY == celdaPuntoY);

        if (colision) {
            System.out.println("¡COLISIÓN DETECTADA!");
            System.out.println("Celda nave: (" + (celdaNaveX+1) + "," + (celdaNaveY+1) + ")");
            System.out.println("Celda punto: (" + (celdaPuntoX+1) + "," + (celdaPuntoY+1) + ")");
        }

        return colision;
    }

    public void setVariable(String nombre, int valor) {
        variables.put(nombre, valor);
    }

    public int getVariable(String nombre) {
        return variables.getOrDefault(nombre, 0);
    }

    public boolean isExito() {
        return exito;
    }
}