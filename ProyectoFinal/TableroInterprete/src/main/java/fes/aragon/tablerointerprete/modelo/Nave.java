package fes.aragon.tablerointerprete.modelo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.io.InputStream;

public class Nave {
    private int x, y;
    private int tamano = 40;
    private Image imagenArriba, imagenAbajo, imagenIzquierda, imagenDerecha;
    private Image imagenActual;
    private String direccion = "derecha";

    public Nave(int xInicial, int yInicial) {
        this.x = xInicial;
        this.y = yInicial;
        cargarImagenes();
        imagenActual = imagenDerecha;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        System.out.println("Nave posicionada en píxeles: (" + x + "," + y +
                ") - Celda: (" + ((x-50)/50 + 1) + "," + ((y-50)/50 + 1) + ")");
    }

    private void cargarImagenes() {
        try {
            InputStream stream = getClass().getResourceAsStream("/fes/aragon/tablerointerprete/derecha.png");
            if (stream != null) imagenDerecha = new Image(stream);

            stream = getClass().getResourceAsStream("/fes/aragon/tablerointerprete/arriba.png");
            if (stream != null) imagenArriba = new Image(stream);

            stream = getClass().getResourceAsStream("/fes/aragon/tablerointerprete/abajo.png");
            if (stream != null) imagenAbajo = new Image(stream);

            stream = getClass().getResourceAsStream("/fes/aragon/tablerointerprete/izquierda.png");
            if (stream != null) imagenIzquierda = new Image(stream);
        } catch (Exception e) {
            System.out.println("Error cargando imágenes: " + e.getMessage());
        }
    }

    public void cambiarDireccion(String direccion) {
        this.direccion = direccion;
        switch(direccion) {
            case "arriba": if(imagenArriba != null) imagenActual = imagenArriba; break;
            case "abajo": if(imagenAbajo != null) imagenActual = imagenAbajo; break;
            case "izquierda": if(imagenIzquierda != null) imagenActual = imagenIzquierda; break;
            case "derecha": if(imagenDerecha != null) imagenActual = imagenDerecha; break;
        }
    }

    // NUEVO MÉTODO: Mover un paso a la vez (50 píxeles)
    public void moverUnPaso(String direccion) {
        switch(direccion) {
            case "arriba": y -= 50; break;
            case "abajo": y += 50; break;
            case "izquierda": x -= 50; break;
            case "derecha": x += 50; break;
        }
        // Limitar dentro del grid
        x = Math.max(50, Math.min(x, 1000));
        y = Math.max(50, Math.min(y, 1000));
    }

    public void mover(String direccion, int pasos) {
        for (int i = 0; i < pasos; i++) {
            moverUnPaso(direccion);
        }
    }

    public void pintar(GraphicsContext gc) {
        if (imagenActual != null) {
            gc.drawImage(imagenActual, x, y, tamano, tamano);
        } else {
            // Fallback: dibujar un rectángulo
            gc.fillRect(x, y, tamano, tamano);
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getTamano() { return tamano; }
}