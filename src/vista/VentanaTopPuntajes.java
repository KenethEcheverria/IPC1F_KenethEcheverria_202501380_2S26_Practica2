package vista;

import logica.GestorDatos;
import modelo.Partida;
import reportes.GeneradorReporte;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaTopPuntajes extends JDialog {
    private GestorDatos gestorDatos;
    private JTable tablaTop;
    private JTable tablaHistorial;
    private JButton btnCerrar;
    private JButton btnExportar;

    public VentanaTopPuntajes(GestorDatos gestorDatos) {
        this.gestorDatos = gestorDatos;
        configurarVentana();
        crearComponentes();
        cargarDatos();
        agregarEventos();
    }

    private void configurarVentana() {
        setTitle("Top de Puntajes e Historial");
        setSize(650, 500);
        setModal(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(new Color(15, 15, 40));
    }

    private void crearComponentes() {
        JLabel lblTop = new JLabel("TOP 5 MEJORES PUNTAJES", SwingConstants.CENTER);
        lblTop.setFont(new Font("Arial", Font.BOLD, 15));
        lblTop.setForeground(new Color(255, 215, 0));
        lblTop.setBounds(50, 15, 550, 25);
        add(lblTop);

        // Tabla del top 5
        String[] columnasTop = {"#", "Piloto", "Nave", "Puntaje", "Fecha"};
        String[][] datosTop = new String[5][5];
        tablaTop = new JTable(datosTop, columnasTop);
        tablaTop.setBackground(new Color(20, 20, 50));
        tablaTop.setForeground(Color.WHITE);
        tablaTop.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaTop.getTableHeader().setBackground(new Color(0, 80, 150));
        tablaTop.getTableHeader().setForeground(Color.WHITE);
        tablaTop.setRowHeight(22);
        tablaTop.setEnabled(false);

        JScrollPane scrollTop = new JScrollPane(tablaTop);
        scrollTop.setBounds(50, 50, 550, 130);
        add(scrollTop);

        JLabel lblHistorial = new JLabel("HISTORIAL COMPLETO DE PARTIDAS", SwingConstants.CENTER);
        lblHistorial.setFont(new Font("Arial", Font.BOLD, 15));
        lblHistorial.setForeground(new Color(0, 200, 255));
        lblHistorial.setBounds(50, 195, 550, 25);
        add(lblHistorial);

        // Tabla del historial completo
        String[] columnasHistorial = {"Piloto", "Nave", "Puntaje", "Fecha"};
        String[][] datosHistorial = new String[0][4];
        tablaHistorial = new JTable(datosHistorial, columnasHistorial);
        tablaHistorial.setBackground(new Color(20, 20, 50));
        tablaHistorial.setForeground(Color.WHITE);
        tablaHistorial.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaHistorial.getTableHeader().setBackground(new Color(0, 80, 150));
        tablaHistorial.getTableHeader().setForeground(Color.WHITE);
        tablaHistorial.setRowHeight(22);
        tablaHistorial.setEnabled(false);

        JScrollPane scrollHistorial = new JScrollPane(tablaHistorial);
        scrollHistorial.setBounds(50, 230, 550, 170);
        add(scrollHistorial);


        btnExportar = new JButton("Exportar Reporte");
        btnExportar.setBounds(150, 415, 160, 35);
        btnExportar.setBackground(new Color(0, 100, 50));
        btnExportar.setForeground(Color.WHITE);
        btnExportar.setFocusPainted(false);
        add(btnExportar);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(350, 415, 120, 35);
        btnCerrar.setBackground(new Color(100, 20, 20));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        add(btnCerrar);
    }

    private void cargarDatos() {
        cargarTablaTop();
        cargarTablaHistorial();
    }

    private void cargarTablaTop() {
        Partida[] top = gestorDatos.getTopPuntajes(5);
        for (int i = 0; i < 5; i++) {
            if (i < top.length) {
                Partida p = top[i];
                tablaTop.setValueAt(String.valueOf(i + 1), i, 0);
                tablaTop.setValueAt(p.getNombrePiloto(), i, 1);
                tablaTop.setValueAt(p.getTipoNave(), i, 2);
                tablaTop.setValueAt(String.valueOf(p.getPuntaje()), i, 3);
                tablaTop.setValueAt(p.getFecha(), i, 4);
            } else {
                tablaTop.setValueAt(String.valueOf(i + 1), i, 0);
                tablaTop.setValueAt("-", i, 1);
                tablaTop.setValueAt("-", i, 2);
                tablaTop.setValueAt("-", i, 3);
                tablaTop.setValueAt("-", i, 4);
            }
        }
    }

    private void cargarTablaHistorial() {
        Partida[] historial = gestorDatos.getPartidas();

        String[][] datos = new String[historial.length][4];
        for (int i = 0; i < historial.length; i++) {
            Partida p = historial[i];
            datos[i][0] = p.getNombrePiloto();
            datos[i][1] = p.getTipoNave();
            datos[i][2] = String.valueOf(p.getPuntaje());
            datos[i][3] = p.getFecha();
        }

        String[] columnas = {"Piloto", "Nave", "Puntaje", "Fecha"};
        tablaHistorial.setModel(new DefaultTableModel(datos, columnas));
        tablaHistorial.setEnabled(false);
    }

    private void agregarEventos() {

        btnExportar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean exportado = GeneradorReporte.exportar(gestorDatos);
                if (exportado) {
                    JOptionPane.showMessageDialog(null,
                            "Reporte exportado correctamente.",
                            "Exportacion exitosa",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "No se pudo exportar el reporte.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
