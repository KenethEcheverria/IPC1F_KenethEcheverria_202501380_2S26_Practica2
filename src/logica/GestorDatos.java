package logica;

import modelo.Nave;
import modelo.Partida;
import modelo.Piloto;

public class GestorDatos {

    private Piloto[] pilotos;
    private int totalPilotos;

    private Partida[] partidas;
    private int totalPartidas;

    private Nave[] navesDisponibles;

    private static final int MAX_PILOTOS = 50;
    private static final int MAX_PARTIDAS = 200;

    public GestorDatos() {
        pilotos = new Piloto[MAX_PILOTOS];
        totalPilotos = 0;
        partidas = new Partida[MAX_PARTIDAS];
        totalPartidas = 0;


        // 3 naves con sus tiempos de hilo
        navesDisponibles = new Nave[3];
        navesDisponibles[0] = new Nave("Explorador", "Facil", 8, 2000);
        navesDisponibles[1] = new Nave("Caza Estelar", "Normal", 5, 1000);
        navesDisponibles[2] = new Nave("Acorazado", "Dificil", 2, 300);
    }

    // Agregar nuevo piloto
    public boolean agregarPiloto(String nombre, Nave nave) {
        // Reviso que no exista un piloto con ese nombre
        for (int i = 0; i < totalPilotos; i++) {
            if (pilotos[i].getNombre().equalsIgnoreCase(nombre)) {
                return false;
            }
        }
        //Reviso que no esté lleno el vector
        if (totalPilotos >= MAX_PILOTOS) {
            return false;
        }

        pilotos[totalPilotos] = new Piloto(nombre, nave);
        totalPilotos++;
        return true;
    }

    // Buscar piloto
    public Piloto buscarPiloto(String nombre) {
        for (int i = 0; i < totalPilotos; i++) {
            if (pilotos[i].getNombre().equalsIgnoreCase(nombre)) {
                return pilotos[i];
            }
        }
        return null;
    }

    public Piloto[] getPilotos() {
        Piloto[] resultado=new Piloto[totalPilotos];
        for (int i=0; i<totalPilotos; i++) {
            resultado[i]=pilotos[i];
        }
        return resultado;
    }

    public int getTotalPilotos() {
        return totalPilotos;
    }

    // Guarda una partida
     public boolean agregarPartida(Partida p) {
        if (totalPartidas<MAX_PARTIDAS) {
            partidas[totalPartidas]=p;
            totalPartidas++;
            return true;
        }
        return false;
     }

     public Partida[] getPartidas() {
        Partida[] resultado=new Partida[totalPartidas];
        for (int i=0; i<totalPartidas; i++) {
            resultado[i]=partidas[i];
        }
        return resultado;
     }

     public int getTotalPartidas() {
        return totalPartidas;
     }

     // Partidas ordenadas de mayor a menor puntaje
    public Partida[] getTopPuntajes(int cantidad) {
        Partida[] copia=new Partida[totalPartidas];
        for (int i=0; i<totalPartidas; i++) {
            copia[i]=partidas[i];
        }

        for (int i=0; i<totalPartidas-1; i++) {
            for (int j=0; j<totalPartidas-1-i; j++) {
                if (copia[j].getPuntaje()<copia[j+1].getPuntaje()) {
                    Partida temp=copia[j];
                    copia[j]=copia[j+1];
                    copia[j+1]=temp;
                }
            }
        }

        int limite= Math.min(cantidad, totalPartidas);
        Partida[] top=new Partida[limite];
        for (int i=0; i<limite; i++) {
            top[i]=copia[i];
        }
        return top;
    }

    public Nave[] getNavesDisponibles() {
        return navesDisponibles;
    }

    public Nave getNave(int indice) {
        if (indice>=0 && indice<navesDisponibles.length) {
            return navesDisponibles[indice];
        }
        return null;
    }
}
