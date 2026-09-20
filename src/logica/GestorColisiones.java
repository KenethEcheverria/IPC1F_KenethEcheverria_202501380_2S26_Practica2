package logica;

import hilos.HiloEnemigo;
import hilos.HiloObjetoEspecial;
import modelo.Piloto;
import vista.GamePanel;

import java.awt.*;

public class GestorColisiones {

    public static void verificar(GamePanel panel, int jugadorX, int jugadorY,
                                 int jugadorAncho, int jugadorAlto, Piloto piloto) {
        Rectangle areaJugador=new Rectangle(jugadorX, jugadorY, jugadorAncho, jugadorAlto);

        for(int i=panel.totalEnemigos-1; i>=0; i--) {
            if (panel.enemigos[i]==null) continue;;
            HiloEnemigo enemigo=panel.enemigos[i];
            Rectangle areaEnemigo= new Rectangle(enemigo.getX(), enemigo.getY(), 40, 25);

            if (areaJugador.intersects(areaEnemigo)) {
                // Si la nave choca, el juego termina
                enemigo.detener();
                for (int j=i; j<panel.totalEnemigos-1; j++) {
                    panel.enemigos[j]=panel.enemigos[j+1];
                }
                panel.enemigos[panel.totalEnemigos-1]=null;
                panel.totalEnemigos--;
                panel.terminarJuego();
                return;
            }
            // Si salió por el borde izquierdo, lo elimino
            if (enemigo.getX()+40<0) {
                enemigo.detener();
                for (int j=i; j<panel. totalEnemigos-1; j++) {
                    panel.enemigos[j]=panel.enemigos[j+1];
                }
                panel.enemigos[panel.totalEnemigos-1]=null;
                panel.totalEnemigos--;
            }
        }

        for (int i=panel.totalObjetos-1; i>=0; i--) {
            if (panel.objetosEspeciales[i]==null) continue;

            HiloObjetoEspecial objeto= panel.objetosEspeciales[i];
            Rectangle areaObjeto=new Rectangle(objeto.getX(), objeto.getY(), 25, 25);

            if (areaJugador.intersects(areaObjeto)) {
                objeto.detener();
                for (int j=i; j<panel.totalObjetos-1; j++) {
                    panel.objetosEspeciales[j]=panel.objetosEspeciales[j+1];
                }
                panel.objetosEspeciales[panel.totalObjetos-1]=null;
                panel.totalObjetos--;

                if (objeto.getTipo()==0) {
                    // Destruye todos los enemigos visibles y suma 150 puntos
                    panel.sumarPuntos(150);
                    panel.destruirTodosLosEnemigos();
                } else if (objeto.getTipo()==1) {
                    // Ralentiza la nave 2 segundos
                    panel.activarRalentizacion();
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                            panel.desactivarRalentizacion();
                        }
                    }).start();
                } else if (objeto.getTipo()==2) {
                    // suma 10 puntos
                    panel.sumarPuntos(10);
                }
            }

            if (objeto.getX()+25<0) {
                objeto.detener();
                for (int j=i; j<panel. totalObjetos-1; j++) {
                    panel.objetosEspeciales[j]= panel.objetosEspeciales[j+1];
                }
                panel.objetosEspeciales[panel.totalObjetos-1]=null;
                panel.totalObjetos--;
            }
        }
    }
}
