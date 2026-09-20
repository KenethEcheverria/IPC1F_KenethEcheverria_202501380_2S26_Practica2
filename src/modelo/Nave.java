// Esta clase representa los modelos de nave que puede elegir el jugador
// Cada nave define la dificultad del juego
package modelo;

public class Nave {

    // Atributos de la nave
    private String tipo; // "Explorador", "Caza Estelar" o "Acorazado"
    private String dificultad; // "Facil", "Normal" o "Dificil"
    private int velocidadMovimiento; // qué tan rápido se mueve la nave en pixeles
    private int tiempoDisparoMs; // tiempo entre disparos en milisegundos

    // Constructor: cuando creo una nave le paso todos sus datos
    public Nave(String tipo, String dificultad, int velocidadMovimiento, int tiempoDisparoMs) {
        this.tipo=tipo;
        this.dificultad=dificultad;
        this.velocidadMovimiento=velocidadMovimiento;
        this.tiempoDisparoMs=tiempoDisparoMs;
    }

    // Métodos getter para leer los atributos desde otras clases
    public String getTipo() {
        return tipo;
    }

    public String getDificultad() {
        return dificultad;
    }

    public int getVelocidadMovimiento() {
        return velocidadMovimiento;
    }

    public int getTiempoDisparoMs() {
        return tiempoDisparoMs;
    }

    // toString para mostrar info de la nave en listas o mensajes
    @Override
    public String toString() {
        return tipo+" ("+dificultad+")";
    }
}
