package vista;

import logica.GestorDatos;
import modelo.Piloto;

import javax.swing.*;

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
    }
}
