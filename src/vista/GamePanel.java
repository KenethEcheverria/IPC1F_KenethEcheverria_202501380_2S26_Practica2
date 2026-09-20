package vista;

import hilos.HiloDisparo;
import hilos.HiloEnemigo;
import hilos.HiloObjetoEspecial;
import hilos.HiloProyectil;

import logica.GestorColisiones;
import logica.GestorDatos;
import modelo.Partida;
import modelo.Piloto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDate;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    public static final int ANCHO=800;
    public static final int ALTO=500;

    private static final int FPS=60;

    private GestorDatos gestorDatos;
    private Piloto pilotoActual;

    private int jugadorX;
    private int jugadorY;
    private int jugadorAncho=60;
    private int jugadorAlto=30;

    private int velocidadJugador;

    private boolean arribaPresionada;
    private boolean abajoPresionada;

    private int puntaje;

    private boolean jugando;

    private boolean ralentizado;

    public HiloEnemigo[] enemigos;
    public HiloObjetoEspecial[] objetosEspeciales;
    public HiloProyectil[] proyectiles;

    public int totalEnemigos;
    public int totalObjetos;
    public int totalProyectiles;

    private static final int MAX_ENEMIGOS=30;
    private static final int MAX_OBJETOS=10;
    private static final int MAX_PROYECTILES=20;

    private Thread hiloJuego;

    private int contadorGeneracion;

    public GamePanel(GestorDatos gestorDatos, Piloto pilotoActual) {
        this.gestorDatos=gestorDatos;
        this.pilotoActual=pilotoActual;
        this.velocidadJugador=pilotoActual.getNave().getVelocidadMovimiento();
        configurarPanel();
        inicializarJuego();
    }

    private void configurarPanel() {
        setPreferredSize(new Dimension(ANCHO, ALTO));
        setBackground(new Color(5, 5, 20));
        setFocusable(true);
        addKeyListener(this);
    }

    private void inicializarJuego() {
        jugadorX=80;
        jugadorY=ALTO/2-jugadorAlto/2;
        puntaje=0;
        jugando=true;
        ralentizado=false;
        contadorGeneracion=0;
        arribaPresionada=false;
        abajoPresionada=false;

        enemigos=new HiloEnemigo[MAX_ENEMIGOS];
        objetosEspeciales=new HiloObjetoEspecial[MAX_OBJETOS];
        proyectiles=new HiloProyectil[MAX_PROYECTILES];
        totalEnemigos=0;
        totalObjetos=0;
        totalProyectiles=0;

        HiloDisparo hiloDisparo=new HiloDisparo(this,
                pilotoActual.getNave().getTiempoDisparoMs());
        hiloDisparo.start();
    }

    @Override
    public void run() {
        long tiempoPorFrame=1000000000/FPS;

        while (jugando) {
            long ahora=System.nanoTime();

            actualizar();
            repaint();

            long transcurrido=System.nanoTime()-ahora;
            long esperar=tiempoPorFrame-transcurrido;

            if (esperar>0) {
                try {
                    Thread.sleep(esperar/1000000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void actualizar() {
        int velocidadReal=ralentizado?velocidadJugador/2:velocidadJugador;
        if (arribaPresionada&&jugadorY>0) {
            jugadorY -= velocidadReal;
        }
        if (abajoPresionada&&jugadorY<ALTO-jugadorAlto) {
            jugadorY+=velocidadReal;
        }

        contadorGeneracion++;
        if (contadorGeneracion%90==0) {
            generarEnemigo();
        }

        if (contadorGeneracion%180==0) {
            generarObjetoEspecial();
        }

        GestorColisiones.verificar(this, jugadorX, jugadorY,
                jugadorAncho, jugadorAlto, pilotoActual);
    }

    private void generarEnemigo() {
        if (totalEnemigos<MAX_ENEMIGOS) {
            int yAleatorio=(int)(Math.random()*(ALTO-30));
            HiloEnemigo enemigo=new HiloEnemigo(ANCHO, yAleatorio, this);
            enemigos[totalEnemigos]=enemigo;
            totalEnemigos++;
            enemigo.start();
        }
    }

    private void generarObjetoEspecial() {
        if (totalObjetos<MAX_OBJETOS) {
            int tipo=(int)(Math.random()*3);
            int yAleatorio=(int)(Math.random()*(ALTO-30));
            HiloObjetoEspecial objeto=new HiloObjetoEspecial(ANCHO, yAleatorio, tipo, this);
            objetosEspeciales[totalObjetos]=objeto;
            totalObjetos++;
            objeto.start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(0, 180, 255));
        int[] xPuntos={jugadorX, jugadorX+jugadorAncho, jugadorX};
        int[] yPuntos={jugadorY, jugadorY+jugadorAlto/2, jugadorY+jugadorAlto};
        g.fillPolygon(xPuntos, yPuntos, 3);

        g.setColor(new Color(220, 50, 50));
        for (int i=0; i<totalEnemigos; i++) {
            if (enemigos[i]!=null) {
                g.fillRect(enemigos[i].getX(), enemigos[i].getY(), 40, 25);
            }
        }

        for (int i=0; i<totalObjetos; i++) {
            if (objetosEspeciales[i]!=null) {
                if (objetosEspeciales[i].getTipo()==0) {
                    g.setColor(new Color(255, 215, 0)); // Snitch: dorado
                } else if (objetosEspeciales[i].getTipo()==1) {
                    g.setColor(new Color(150, 150, 150)); // Bludger: gris
                } else {
                    g.setColor(new Color(255, 140, 0)); // Quaffle: naranja
                }
                g.fillOval(objetosEspeciales[i].getX(), objetosEspeciales[i].getY(), 25, 25);
            }
        }

        g.setColor(new Color(255, 255, 0));
        for (int i=0; i<totalProyectiles; i++) {
            if (proyectiles[i]!=null) {
                g.fillRect(proyectiles[i].getX(), proyectiles[i].getY(), 12, 4);
            }
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Puntaje: "+puntaje,15,25);

        g.setFont(new Font("Arial", Font.PLAIN, 13));
        g.drawString(pilotoActual.getNombre()+" | "+pilotoActual.getNave().getTipo(),
                ANCHO-230, 25);

        if (ralentizado) {
            g.setColor(new Color(255, 80, 80));
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString("RALENTIZADO", ANCHO/2-50, 50);
        }
    }

    public void terminarJuego() {
        jugando=false;
        String fecha= LocalDate.now().toString();
        Partida partida=new Partida(
                pilotoActual.getNombre(),
                pilotoActual.getNave().getTipo(),
                puntaje,
                fecha
        );
        gestorDatos.agregarPartida(partida);

        JOptionPane.showMessageDialog(this,
                "Juego terminado\nPuntaje final: "+puntaje,
                "Fin de partida",
                        JOptionPane.INFORMATION_MESSAGE);

        SwingUtilities.getWindowAncestor(this).dispose();
    }

    public synchronized void sumarPuntos(int puntos) {
        puntaje += puntos;
    }

    public void activarRalentizacion() {
        ralentizado=true;
    }

    public void desactivarRalentizacion() {
        ralentizado=false;
    }

    public synchronized void destruirTodosLosEnemigos() {
        for (int i=0; i<totalEnemigos; i++) {
            if (enemigos[i]!=null) {
                enemigos[i].detener();
                enemigos[i]=null;
            }
        }
        totalEnemigos=0;
    }

    public synchronized void agregarProyectil(HiloProyectil p) {
        if (totalProyectiles<MAX_PROYECTILES) {
            proyectiles[totalProyectiles]=p;
            totalProyectiles++;
        }
    }

    public synchronized void eliminarProyectil(HiloProyectil p) {
        for (int i=0; i<totalProyectiles; i++) {
            if (proyectiles[i]==p) {
                for (int j=i; j<totalProyectiles-1; j++) {
                    proyectiles[j]=proyectiles[j+1];
                }
                proyectiles[totalProyectiles-1]=null;
                totalProyectiles--;
                break;
            }
        }
    }

    // Getters
    public int getJugadorX(){return jugadorX;}
    public int getJugadorY(){return jugadorY;}
    public int getJugadorAncho(){return jugadorAncho;}
    public int getJugadorAlto(){return jugadorAlto;}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            arribaPresionada = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            abajoPresionada = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            arribaPresionada = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            abajoPresionada = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e){
        // No lo necesito, pero KeyListener me hace implementarlo
    }
}
