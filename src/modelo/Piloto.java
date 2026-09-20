// Esta clase representa a un jugador (piloto) que puede crear y guardar partidas
package modelo;

public class Piloto {

    // Atributos del piloto
    private String nombre; // nombre único del piloto
    private Nave nave; // la nave que eligió (define la dificultad)
    private int totalPartidas; // cuántas partidas ha jugado en total

    // Constructor: cuando registro un piloto nuevo le asigno su nombre y su nave
    public Piloto(String nombre, Nave nave) {
        this.nombre=nombre;
        this.nave=nave;
        this.totalPartidas=0; // empieza con cero partidas
    }

    // Getter del nombre
    public String getNombre() {
        return nombre;
    }

    // Getter de la nave
    public Nave getNave() {
        return nave;
    }

    // Getter del total de partidas
    public int getTotalPartidas() {
        return totalPartidas;
    }

    // Setter de la nave (por si el piloto quiere cambiar de nave)
    public void setNave(Nave nave) {
        this.nave = nave;
    }

    // Este método suma 1 cada vez que el piloto termina una partida
    public void incrementarPartidas() {
        this.totalPartidas++;
    }

    // toString para mostrar el piloto en listas
    @Override
    public String toString() {
        return nombre+" | Nave: "+nave.getTipo();
    }
}
