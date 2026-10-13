package edu.gastos.eventos;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/** Vista Swing: presenta los controles; no decide qué hacer ante un evento. */
public final class VentanaGastos extends JFrame {
    final JTextField campoConcepto = new JTextField(18);
    final JTextField campoMonto = new JTextField(12);
    final JComboBox<String> campoCategoria = new JComboBox<>(new String[] {
            "Alimentación", "Transporte", "Servicios", "Entretenimiento", "Otros"
    });
    final JTextField campoUmbral = new JTextField(10);
    final JTextField campoBusqueda = new JTextField(16);

    final JButton botonRegistrar = new JButton("Registrar gasto");
    final JButton botonListar = new JButton("Mostrar todos");
    final JButton botonTotal = new JButton("Calcular total");
    final JButton botonMayor = new JButton("Mayor gasto");
    final JButton botonFiltrar = new JButton("Filtrar por monto mínimo");
    final JButton botonBuscar = new JButton("Buscar por concepto");

    private final DefaultTableModel modeloTabla = new DefaultTableModel(
            new Object[] {"Concepto", "Monto", "Categoría"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };
    final JTable tabla = new JTable(modeloTabla);
    final JLabel etiquetaTotal = new JLabel("Total acumulado: $0,00");
    final JLabel etiquetaEstado = new JLabel("Registra un gasto para comenzar.");
    private final NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    public VentanaGastos() {
        super("Control de gastos personales - Paradigma orientado a eventos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(920, 570);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(12, 12));

        JPanel superior = new JPanel(new BorderLayout(8, 8));
        superior.setBorder(BorderFactory.createEmptyBorder(14, 14, 0, 14));
        JPanel formulario = new JPanel(new GridLayout(2, 3, 8, 5));
        formulario.add(new JLabel("Concepto"));
        formulario.add(new JLabel("Monto ($; punto o coma decimal)"));
        formulario.add(new JLabel("Categoría"));
        formulario.add(campoConcepto);
        formulario.add(campoMonto);
        formulario.add(campoCategoria);
        superior.add(formulario, BorderLayout.NORTH);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 10));
        botones.add(botonRegistrar);
        botones.add(botonListar);
        botones.add(botonTotal);
        botones.add(botonMayor);
        superior.add(botones, BorderLayout.SOUTH);
        add(superior, BorderLayout.NORTH);

        tabla.setRowHeight(26);
        tabla.getTableHeader().setReorderingAllowed(false);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Listado de gastos"));
        add(scroll, BorderLayout.CENTER);

        JPanel inferior = new JPanel(new BorderLayout(8, 8));
        inferior.setBorder(BorderFactory.createEmptyBorder(0, 14, 14, 14));
        JPanel consultas = new JPanel(new GridLayout(2, 3, 8, 6));
        consultas.add(new JLabel("Monto mínimo"));
        consultas.add(campoUmbral);
        consultas.add(botonFiltrar);
        consultas.add(new JLabel("Palabra o parte del concepto"));
        consultas.add(campoBusqueda);
        consultas.add(botonBuscar);
        inferior.add(consultas, BorderLayout.NORTH);

        JPanel resultado = new JPanel(new GridLayout(2, 1, 0, 4));
        resultado.add(etiquetaTotal);
        resultado.add(etiquetaEstado);
        inferior.add(resultado, BorderLayout.SOUTH);
        add(inferior, BorderLayout.SOUTH);
    }

    public void mostrarGastos(List<Gasto> gastos) {
        modeloTabla.setRowCount(0);
        for (Gasto gasto : gastos) {
            modeloTabla.addRow(new Object[] {
                    gasto.getConcepto(), formatoMoneda.format(gasto.getMonto()), gasto.getCategoria()
            });
        }
    }

    public void mostrarTotal(java.math.BigDecimal total) {
        etiquetaTotal.setText("Total acumulado: " + formatoMoneda.format(total));
    }

    public void mostrarEstado(String mensaje) {
        etiquetaEstado.setText(mensaje);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Datos inválidos", JOptionPane.WARNING_MESSAGE);
    }

    public void limpiarRegistro() {
        campoConcepto.setText("");
        campoMonto.setText("");
        campoCategoria.setSelectedIndex(0);
        campoConcepto.requestFocusInWindow();
    }
}
