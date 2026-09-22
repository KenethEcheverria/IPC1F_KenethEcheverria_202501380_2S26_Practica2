package logica;

import hilos.HiloEnemigo;
import hilos.HiloObjetoEspecial;
import vista.GamePanel;

import java.awt.*;

public class GestorColisiones {

    public static void verificar(GamePanel panel, int jugadorX, int jugadorY,
                                 int jugadorAncho, int jugadorAlto) {
        synchronized (panel) {

            Rectangle areaJugador = new Rectangle(jugadorX, jugadorY, jugadorAncho, jugadorAlto);

            for (int i = panel.totalEnemigos - 1; i >= 0; i--) {
                if (panel.enemigos[i] == null) {
                    continue;
                }

                HiloEnemigo enemigo = panel.enemigos[i];
                Rectangle areaEnemigo = new Rectangle(enemigo.getX(), enemigo.getY(), 40, 25);

                // Si la nave choca, el juego termina
                if (areaJugador.intersects(areaEnemigo)) {
                    panel.eliminarEnemigo(enemigo);
                    panel.terminarJuego();
                    return;
                }

                // Si salió por el borde izquierdo, lo elimino
                if (enemigo.getX() + 40 < 0) {
                    panel.eliminarEnemigo(enemigo);
                }
            }

            for (int i = panel.totalObjetos - 1; i >= 0; i--) {
                if (panel.objetosEspeciales[i] == null) {
                    continue;
                }

                HiloObjetoEspecial objeto = panel.objetosEspeciales[i];
                Rectangle areaObjeto = new Rectangle(objeto.getX(), objeto.getY(), 25, 25);

                if (areaJugador.intersects(areaObjeto)) {
                    if (panel.eliminarObjetoEspecial(objeto)) {
                        if (objeto.getTipo() == 0) {
                            // Destruye todos los enemigos visibles y suma 150 puntos
                            panel.sumarPuntos(150);
                            panel.destruirTodosLosEnemigos();

                        } else if (objeto.getTipo() == 1) {
                            // Ralentiza la nave 2 segundos
                            panel.activarRalentizacion();
                            new Thread(new Runnable() {
                                public void run() {
                                    try {
                                        Thread.sleep(2000);
                                    } catch (InterruptedException ex) {
                                        return;
                                    }
                                    panel.desactivarRalentizacion();
                                }
                            }).start();
                        } else if (objeto.getTipo() == 2) {
                            // suma 10 puntos
                            panel.sumarPuntos(10);
                        }
                    }

                    continue;
                }

                if (objeto.getX() + 25 < 0) {
                    panel.eliminarObjetoEspecial(objeto);
                }
            }
        }
    }
}
