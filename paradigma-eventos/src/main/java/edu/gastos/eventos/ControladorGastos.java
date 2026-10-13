package edu.gastos.eventos;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/** Los ActionListeners relacionan eventos de usuario y operaciones del sistema. */
public final class ControladorGastos {
    private final RegistroGastos registro;
    private final VentanaGastos vista;

    public ControladorGastos(RegistroGastos registro, VentanaGastos vista) {
        this.registro = registro;
        this.vista = vista;
        conectarEventos();
        refrescarTotal();
    }

    private void conectarEventos() {
        // Cada lambda es una función de retorno (callback) asociada al clic.
        vista.botonRegistrar.addActionListener(evento -> registrar());
        vista.botonListar.addActionListener(evento -> listar());
        vista.botonTotal.addActionListener(evento -> calcularTotal());
        vista.botonMayor.addActionListener(evento -> mostrarMayor());
        vista.botonFiltrar.addActionListener(evento -> filtrar());
        vista.botonBuscar.addActionListener(evento -> buscar());
        vista.campoConcepto.addActionListener(evento -> registrar());
        vista.campoMonto.addActionListener(evento -> registrar());
        vista.campoUmbral.addActionListener(evento -> filtrar());
        vista.campoBusqueda.addActionListener(evento -> buscar());
    }

    private void registrar() {
        try {
            Gasto gasto = new Gasto(vista.campoConcepto.getText(),
                    leerMonto(vista.campoMonto.getText(), "El monto"),
                    (String) vista.campoCategoria.getSelectedItem());
            registro.registrar(gasto);
            vista.mostrarGastos(registro.listar());
            refrescarTotal();
            vista.mostrarEstado("Gasto registrado: " + gasto.getConcepto());
            vista.limpiarRegistro();
        } catch (IllegalArgumentException excepcion) {
            vista.mostrarError(excepcion.getMessage());
        }
    }

    private void listar() {
        mostrarResultado(registro.listar(), "Listado completo");
    }

    private void calcularTotal() {
        refrescarTotal();
        vista.mostrarEstado("Total acumulado calculado para " + registro.listar().size() + " gasto(s).");
    }

    private void mostrarMayor() {
        if (registro.mayorGasto().isPresent()) {
            mostrarResultado(Collections.singletonList(registro.mayorGasto().get()), "Gasto de mayor valor");
        } else {
            mostrarResultado(Collections.emptyList(), "Todavía no hay gastos registrados");
        }
    }

    private void filtrar() {
        try {
            BigDecimal minimo = leerMonto(vista.campoUmbral.getText(), "El monto mínimo");
            mostrarResultado(registro.filtrarMayoresA(minimo), "Gastos mayores a " + minimo.toPlainString());
        } catch (IllegalArgumentException excepcion) {
            vista.mostrarError(excepcion.getMessage());
        }
    }

    private void buscar() {
        String consulta = vista.campoBusqueda.getText().trim();
        mostrarResultado(registro.buscarPorConcepto(consulta),
                consulta.isEmpty() ? "Listado completo (búsqueda vacía)" : "Búsqueda: " + consulta);
    }

    private void mostrarResultado(List<Gasto> encontrados, String titulo) {
        vista.mostrarGastos(encontrados);
        vista.mostrarEstado(titulo + ": " + encontrados.size() + " resultado(s).");
    }

    private void refrescarTotal() {
        vista.mostrarTotal(registro.calcularTotal());
    }

    private static BigDecimal leerMonto(String entrada, String nombre) {
        String texto = entrada.trim();
        if (texto.isEmpty()) {
            throw new IllegalArgumentException(nombre + " es obligatorio.");
        }
        // No admite separadores de miles; acepta 12500,50 y 12500.50.
        if (!texto.matches("\\d+(?:[.,]\\d{1,2})?")) {
            throw new IllegalArgumentException(nombre + " debe ser positivo y tener máximo dos decimales (sin separadores de miles).");
        }
        try {
            return new BigDecimal(texto.replace(',', '.'));
        } catch (NumberFormatException excepcion) {
            throw new IllegalArgumentException(nombre + " no es válido.");
        }
    }
}
