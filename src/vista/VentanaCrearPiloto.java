package vista;

import logica.GestorDatos;
import modelo.Nave;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaCrearPiloto extends JDialog {

    private GestorDatos gestorDatos;
    private JTextField txtNombre;
    private JComboBox<String> cmbNave;
    private JButton btnRegistrar;
    private JButton btnCancelar;
    private JLabel lblInfo;

    public VentanaCrearPiloto(GestorDatos gestorDatos) {
        this.gestorDatos=gestorDatos;
        configurarVentana();
        crearComponentes();
        agregarEventos();
    }

    private void configurarVentana() {
        setTitle("Crear Piloto");
        setSize(400, 300);
        setModal(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(new Color(15, 15, 40));
    }

    private void crearComponentes() {
        JLabel lblTitulo=new JLabel("Registro de Piloto", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(0, 200, 255));
        lblTitulo.setBounds(50, 20, 300, 30);
        add(lblTitulo);

        JLabel lblNombre=new JLabel("Nombre del piloto:");
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(50, 70, 150, 25);
        add(lblNombre);

        txtNombre=new JTextField();
        txtNombre.setBounds(200, 70, 150, 25);
        add(txtNombre);

        JLabel lblNave=new JLabel("Selecciona tu nave:");
        lblNave.setForeground(Color.WHITE);
        lblNave.setBounds(50, 115, 150, 25);
        add(lblNave);

        Nave[] naves=gestorDatos.getNavesDisponibles();
        String[] opcionesNaves=new String[naves.length];
        for (int i=0; i<naves.length; i++) {
            opcionesNaves[i]=naves[i].getTipo()+" - "+naves[i].getDificultad();
        }
        cmbNave=new JComboBox<String>(opcionesNaves);
        cmbNave.setBounds(200, 115, 150, 25);
        add(cmbNave);

        lblInfo=new JLabel("", SwingConstants.CENTER);
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 11));
        lblInfo.setBounds(50, 155, 300, 20);
        add(lblInfo);

        btnRegistrar=new JButton("Registrar");
        btnRegistrar.setBounds(70, 190, 110, 35);
        btnRegistrar.setBackground(new Color(0, 100, 50));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);
        add(btnRegistrar);

        btnCancelar=new JButton("Cancelar");
        btnCancelar.setBounds(210, 190, 110, 35);
        btnCancelar.setBackground(new Color(100, 20, 20));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        add(btnCancelar);
    }

    private void agregarEventos() {
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre=txtNombre.getText().trim();

                if (nombre.isEmpty()) {
                    lblInfo.setText("El nombre no puede estar vacío");
                    lblInfo.setForeground(new Color(255, 80, 80));
                    return;
                }

                if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                    lblInfo.setText("El nombre solo puede contener letras.");
                    lblInfo.setForeground(new Color(255, 80, 80));
                    return;
                }

                int indiceNave=cmbNave.getSelectedIndex();
                Nave naveElegida=gestorDatos.getNave(indiceNave);

                boolean agregado=gestorDatos.agregarPiloto(nombre, naveElegida);

                if (agregado) {
                    lblInfo.setText("¡Piloto registrado con éxito!");
                    lblInfo.setForeground(new Color(80, 255, 80));
                    txtNombre.setText("");
                } else {
                    lblInfo.setText("Ya existe un piloto con ese nombre.");
                    lblInfo.setForeground(new Color(255, 80, 80));
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
