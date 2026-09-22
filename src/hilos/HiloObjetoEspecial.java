package hilos;

public class HiloObjetoEspecial extends Thread {

    private volatile int x;
    private volatile int y;
    private int tipo;
    private volatile boolean vivo;

    public HiloObjetoEspecial(int x, int y, int tipo) {
        this.x=x;
        this.y=y;
        this.tipo=tipo;
        this.vivo=true;
    }

    @Override
    public void run() {
        while (vivo) {
            x-=4;
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
    public int getTipo() {return tipo;}
}
