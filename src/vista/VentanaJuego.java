package vista;

import logica.GestorDatos;
import modelo.Piloto;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaJuego extends JFrame {

    private GestorDatos gestorDatos;
    private Piloto pilotoActual;
    private GamePanel gamePanel;

    public VentanaJuego(GestorDatos gestorDatos, Piloto pilotoActual) {
        this.gestorDatos=gestorDatos;
        this.pilotoActual=pilotoActual;
        configurarVentana();
    }

    private void configurarVentana() {
        setTitle("Quetzal Space Defender  |  Piloto: "+pilotoActual.getNombre()
        +"  |  Nave: "+pilotoActual.getNave().getTipo());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        gamePanel=new GamePanel(gestorDatos, pilotoActual);
        add(gamePanel);

        pack();
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                gamePanel.iniciarJuego();
                gamePanel.requestFocusInWindow();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                gamePanel.detenerJuego();
            }
        });
    }
}
