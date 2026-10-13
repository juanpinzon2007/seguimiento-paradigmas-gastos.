package edu.gastos.eventos;

import javax.swing.SwingUtilities;

/** Prueba interactiva automatizada de los botones; requiere entorno gráfico. */
public final class PruebaInterfazEventos {
    private static int verificaciones = 0;

    private PruebaInterfazEventos() { }

    private static void comprobar(boolean resultado, String mensaje) {
        verificaciones++;
        if (!resultado) {
            throw new AssertionError(mensaje);
        }
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            VentanaGastos vista = new VentanaGastos();
            try {
                new ControladorGastos(new RegistroGastos(), vista);
                vista.campoConcepto.setText("Almuerzo");
                vista.campoMonto.setText("20000,50");
                vista.botonRegistrar.doClick();
                comprobar(vista.tabla.getRowCount() == 1, "Registrar primer gasto");

                vista.campoConcepto.setText("Bus");
                vista.campoMonto.setText("5000");
                vista.botonRegistrar.doClick();
                comprobar(vista.tabla.getRowCount() == 2, "Registrar segundo gasto");

                vista.botonTotal.doClick();
                comprobar(vista.etiquetaTotal.getText().contains("25.000"), "Calcular total acumulado");

                vista.botonMayor.doClick();
                comprobar(vista.tabla.getRowCount() == 1 &&
                        vista.tabla.getValueAt(0, 0).equals("Almuerzo"), "Mostrar el mayor");

                vista.campoUmbral.setText("5000");
                vista.botonFiltrar.doClick();
                comprobar(vista.tabla.getRowCount() == 1, "Filtrar mayores, no iguales");

                vista.campoBusqueda.setText("bus");
                vista.botonBuscar.doClick();
                comprobar(vista.tabla.getRowCount() == 1 &&
                        vista.tabla.getValueAt(0, 0).equals("Bus"), "Buscar por concepto");

                vista.botonListar.doClick();
                comprobar(vista.tabla.getRowCount() == 2, "Mostrar todos los gastos");
            } finally {
                vista.dispose();
            }
        });
        System.out.println("CORRECTO: " + verificaciones + " comprobaciones de eventos Swing.");
    }
}
