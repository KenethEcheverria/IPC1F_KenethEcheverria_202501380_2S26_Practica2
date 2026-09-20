package vista;

import logica.GestorDatos;
import modelo.Piloto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaSeleccionarPiloto extends JDialog {

    private GestorDatos gestorDatos;
    private JList<String> listaPilotos;
    private DefaultListModel<String> modeloLista;
    private JButton btnSeleccionar;
    private JButton btnCancelar;
    private JLabel lblInfo;

    public VentanaSeleccionarPiloto(GestorDatos gestorDatos) {
        this.gestorDatos=gestorDatos;
        configurarVentana();
        crearComponentes();
        cargarPilotos();
        agregarEventos();
    }

    private void configurarVentana() {
        setTitle("Seleccionar Piloto");
        setSize(400, 320);
        setModal(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(new Color(15, 15, 40));
    }

    private void crearComponentes() {
        JLabel lblTitulo=new JLabel("¿Quién va a jugar?", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(0, 200, 255));
        lblTitulo.setBounds(50, 15, 300, 30);
        add(lblTitulo);

        modeloLista=new DefaultListModel<String>();
        listaPilotos=new JList<String>(modeloLista);
        listaPilotos.setBackground(new Color(25, 25, 60));
        listaPilotos.setForeground(Color.WHITE);
        listaPilotos.setFont(new Font("Arial", Font.PLAIN, 13));
        listaPilotos.setSelectionBackground(new Color(0, 100, 180));

        JScrollPane scroll=new JScrollPane(listaPilotos);
        scroll.setBounds(50, 60, 300, 150);
        add(scroll);

        lblInfo=new JLabel("", SwingConstants.CENTER);
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 11));
        lblInfo.setBounds(50, 220, 300, 20);
        add(lblInfo);

        btnSeleccionar=new JButton("Jugar con este piloto");
        btnSeleccionar.setBounds(50, 250, 180, 35);
        btnSeleccionar.setBackground(new Color(0, 100, 50));
        btnSeleccionar.setForeground(Color.WHITE);
        btnSeleccionar.setFocusPainted(false);
        add(btnSeleccionar);

        btnCancelar=new JButton("Cancelar");
        btnCancelar.setBounds(250, 250, 100, 35);
        btnCancelar.setBackground(new Color(100, 20, 20));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        add(btnCancelar);
    }

    private void cargarPilotos() {
        modeloLista.clear();
        Piloto[] pilotos=gestorDatos.getPilotos();
        for (int i=0; i< pilotos.length; i++) {
            Piloto p=pilotos[i];
            modeloLista.addElement(p.getNombre()+"  |  "+p.getNave().getTipo()
            +" ("+p.getNave().getDificultad()+")");
        }
    }
    private void agregarEventos() {
        btnSeleccionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int indice=listaPilotos.getSelectedIndex();

                if (indice==-1) {
                    lblInfo.setText("Selecciona un piloto de la lista.");
                    lblInfo.setForeground(new Color(255, 80, 80));
                    return;
                }

                Piloto pilotoElegido=gestorDatos.getPilotos()[indice];
                pilotoElegido.incrementarPartidas();
                dispose();
                VentanaJuego ventanaJuego=new VentanaJuego(gestorDatos, pilotoElegido);
                ventanaJuego.setVisible(true);
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}