package hilos;

public class HiloEnemigo extends Thread {
    private volatile int x;
    private volatile int y;
    private int velocidad;
    private volatile boolean vivo;

    public HiloEnemigo(int x, int y, int velocidad) {
        this.x=x;
        this.y=y;
        this.vivo=true;
        this.velocidad=velocidad;
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
