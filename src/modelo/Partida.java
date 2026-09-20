// Esta clase guarda el resultado de una partida jugada
package modelo;

public class Partida {

    // Atributos de la partida
    private String nombrePiloto; // qué piloto jugó esta partida
    private String tipoNave; // con qué nave jugó
    private int puntaje; // cuántos puntos hizo
    private String fecha; // cuándo jugó (como texto, ej: "19/09/2026")

    // Constructor: cuando termina una partida guardo todos sus datos
    public Partida(String nombrePiloto, String tipoNave, int puntaje, String fecha) {
        this.nombrePiloto=nombrePiloto;
        this.tipoNave=tipoNave;
        this.puntaje=puntaje;
        this.fecha=fecha;
    }

    // Getters para leer los datos de la partida
    public String getNombrePiloto() {
        return nombrePiloto;
    }

    public String getTipoNave() {
        return tipoNave;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public String getFecha() {
        return fecha;
    }

    // toString para mostrar la partida en le historial
    @Override
    public String toString() {
        return "Piloto: "+nombrePiloto+" | Nave: "+tipoNave+
                " | Puntaje: "+puntaje+" | Fecha: "+fecha;
    }
}
