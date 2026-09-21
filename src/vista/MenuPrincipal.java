package vista;

import logica.GestorDatos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame {

    private GestorDatos gestorDatos;

    private JButton btnJugar;
    private JButton btnCrearPiloto;
    private JButton btnTopPuntajes;
    private JButton btnSalir;

    // Constructor
    public MenuPrincipal() {
        gestorDatos=new GestorDatos();
        configurarVentana();
        crearComponentes();
        agregarEventos();
    }

    private void configurarVentana() {
        setTitle("Quetzal Space Defender");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(10, 10, 30));
    }

    private void crearComponentes() {
        setLayout(null);

        JLabel lblTitulo=new JLabel("QUETZAL SPACE DEFENDER", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(0, 200, 255));
        lblTitulo.setBounds(50, 40, 400, 40);
        add(lblTitulo);

        JLabel lblSub=new JLabel("Side Scroller", SwingConstants.CENTER);
        lblSub.setFont(new Font("Arial", Font.ITALIC, 14));
        lblSub.setBounds(50, 80, 400, 25);
        add(lblSub);

        btnJugar=crearBoton("JUGAR", 150, 140);
        btnCrearPiloto=crearBoton("CREAR PILOTO", 150, 200);
        btnTopPuntajes=crearBoton("TOP DE PUNTAJES", 150, 260);
        btnSalir=crearBoton("SALIR", 150, 320);

        add(btnJugar);
        add(btnCrearPiloto);
        add(btnTopPuntajes);
        add(btnSalir);
    }

    private JButton crearBoton(String texto, int x, int y) {
        JButton boton=new JButton(texto);
        boton.setBounds(x, y, 200, 40);
        boton.setBackground(new Color(30, 30, 80));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private void agregarEventos() {
        btnJugar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (gestorDatos.getTotalPilotos()==0) {
                    JOptionPane.showMessageDialog(null,
                            "Debes crear un piloto antes de jugar.",
                            "Sin piloto,",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    VentanaSeleccionarPiloto ventana=new VentanaSeleccionarPiloto(gestorDatos);
                    ventana.setVisible(true);
                }
            }
        });

        btnCrearPiloto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaCrearPiloto ventana=new VentanaCrearPiloto(gestorDatos);
                ventana.setVisible(true);
            }
        });

        btnTopPuntajes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VentanaTopPuntajes ventana=new VentanaTopPuntajes(gestorDatos);
                ventana.setVisible(true);
            }
        });

        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int respuesta=JOptionPane.showConfirmDialog(null,
                        "¿Seguro que quieres salir?",
                        "Salir",
                        JOptionPane.YES_NO_OPTION);
                if (respuesta==JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }
}
