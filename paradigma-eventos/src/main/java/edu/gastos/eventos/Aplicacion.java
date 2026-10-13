package edu.gastos.eventos;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/** Punto de entrada del proyecto orientado a eventos. */
public final class Aplicacion {
    private Aplicacion() { }

    public static void main(String[] args) {
        // Los componentes Swing y sus callbacks se ejecutan en el Event Dispatch Thread (EDT).
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception excepcion) {
                // Si el tema del sistema no está disponible, se usa el predeterminado.
            }
            VentanaGastos vista = new VentanaGastos();
            new ControladorGastos(new RegistroGastos(), vista);
            vista.setVisible(true);
        });
    }
}
