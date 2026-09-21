package hilos;

import vista.GamePanel;

public class HiloEnemigo extends Thread {
    private int x;
    private int y;
    private int velocidad;
    private volatile boolean vivo;
    private GamePanel panel;

    public HiloEnemigo(int x, int y, GamePanel panel) {
        this.x=x;
        this.y=y;
        this.panel=panel;
        this.vivo=true;
        this.velocidad=3;
    }

    @Override
    public void run() {
        while (vivo) {
            x-=velocidad;
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void detener() {
        vivo=false;
        interrupt();
    }

    public int getX() {return x;}
    public int getY() {return y;}
}
