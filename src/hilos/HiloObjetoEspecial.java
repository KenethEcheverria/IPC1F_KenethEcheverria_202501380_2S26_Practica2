package hilos;

import vista.GamePanel;

public class HiloObjetoEspecial extends Thread {

    private int x;
    private int y;
    private int tipo;
    private boolean vivo;
    private GamePanel panel;

    public HiloObjetoEspecial(int x, int y, int tipo, GamePanel panel) {
        this.x=x;
        this.y=y;
        this.tipo=tipo;
        this.panel=panel;
        this.vivo=true;
    }

    @Override
    public void run() {
        while (vivo) {
            x-=4;
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void detener() {
        vivo=false;
    }

    public int getX() {return x;}
    public int getY() {return y;}
    public int getTipo() {return tipo;}
}
