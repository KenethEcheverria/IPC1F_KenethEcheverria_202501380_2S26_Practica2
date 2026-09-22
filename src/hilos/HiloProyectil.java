package hilos;

import vista.GamePanel;

public class HiloProyectil extends Thread {

    private volatile int x;
    private volatile int y;
    private volatile boolean activo;
    private GamePanel panel;


    public HiloProyectil(int x, int y, GamePanel panel) {
        this.x=x;
        this.y=y;
        this.panel=panel;
        this.activo=true;
    }

    @Override
    public void run() {
        while (activo&&x<GamePanel.ANCHO) {
            x+=10;
            revisarColisionConEnemigos();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                break;
            }
        }
        panel.eliminarProyectil(this);
    }

    private void revisarColisionConEnemigos() {
        synchronized (panel) {
            for (int i = panel.totalEnemigos - 1; i >= 0; i--) {
                if (panel.enemigos[i] == null) {
                    continue;
                }

                HiloEnemigo enemigo = panel.enemigos[i];
                boolean chocaEnX = x >= enemigo.getX() && x <= enemigo.getX() + 40;
                boolean chocaEnY = y >= enemigo.getY() && y <= enemigo.getY() + 25;

                if (chocaEnX && chocaEnY) {
                    if (panel.eliminarEnemigo(enemigo)) {
                        panel.sumarPuntos(20);
                        activo = false;
                        break;
                    }
                }
            }
        }
    }

    public void detener() {
        activo=false;
        interrupt();
    }

    public int getX() {return x;}
    public int getY() {return y;}
    public boolean isActivo() {return activo;}
}


