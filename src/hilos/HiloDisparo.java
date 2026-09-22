package hilos;

import vista.GamePanel;

public class HiloDisparo extends Thread {

    private volatile boolean activo;
    private GamePanel panel;
    private int tiempoRecargaMs;

    public HiloDisparo(GamePanel panel, int tiempoRecargaMs) {
        this.panel=panel;
        this.tiempoRecargaMs=tiempoRecargaMs;
        this.activo=true;
    }

    @Override
    public void run() {
        while (activo) {
           int xProyectil=panel.getJugadorX()+panel.getJugadorAncho();
           int yProyectil=panel.getJugadorY()+panel.getJugadorAlto()/2;

           HiloProyectil proyectil=new HiloProyectil(xProyectil, yProyectil, panel);
           if (panel.agregarProyectil(proyectil)) {
               proyectil.start();
           }

            try {
                Thread.sleep(tiempoRecargaMs);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void detener() {
        activo=false;
        interrupt();
    }
}

