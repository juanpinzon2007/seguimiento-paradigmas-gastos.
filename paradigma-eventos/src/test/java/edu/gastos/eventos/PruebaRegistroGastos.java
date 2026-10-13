package edu.gastos.eventos;

import java.math.BigDecimal;

/** Pruebas sin dependencias externas: ejecutar con java -ea. */
public final class PruebaRegistroGastos {
    private static int verificaciones = 0;

    private PruebaRegistroGastos() { }

    private static void comprobar(boolean condicion, String mensaje) {
        verificaciones++;
        if (!condicion) {
            throw new AssertionError(mensaje);
        }
    }

    private static void errorEsperado(Runnable accion, String mensaje) {
        boolean lanzoError = false;
        try {
            accion.run();
        } catch (IllegalArgumentException error) {
            lanzoError = true;
        }
        comprobar(lanzoError, mensaje);
    }

    public static void main(String[] args) {
        RegistroGastos registro = new RegistroGastos();
        comprobar(registro.listar().isEmpty(), "Inicia sin gastos");
        comprobar(registro.calcularTotal().compareTo(BigDecimal.ZERO) == 0, "Total inicial cero");
        comprobar(!registro.mayorGasto().isPresent(), "Sin gasto mayor inicialmente");

        registro.registrar(new Gasto("Almuerzo", new BigDecimal("25000.50"), "Alimentación"));
        registro.registrar(new Gasto("Taxi", new BigDecimal("15000"), "Transporte"));
        registro.registrar(new Gasto("ALMUERZO equipo", new BigDecimal("32000"), "Alimentación"));

        comprobar(registro.listar().size() == 3, "Registra y lista tres gastos");
        comprobar(registro.calcularTotal().compareTo(new BigDecimal("72000.50")) == 0,
                "Suma exacta de montos");
        comprobar(registro.mayorGasto().get().getMonto().compareTo(new BigDecimal("32000")) == 0,
                "Encuentra el mayor");
        comprobar(registro.filtrarMayoresA(new BigDecimal("15000")).size() == 2,
                "Filtrado estricto, excluye los iguales al umbral");
        comprobar(registro.filtrarMayoresA(new BigDecimal("100000")).isEmpty(),
                "Filtrado sin coincidencias");
        comprobar(registro.buscarPorConcepto("almuerzo").size() == 2,
                "Busca por subcadena sin distinguir mayúsculas");
        comprobar(registro.buscarPorConcepto("muer").size() == 2, "Coincidencia parcial");
        comprobar(registro.buscarPorConcepto("zzzz").isEmpty(), "Búsqueda sin coincidencias");
        comprobar(registro.buscarPorConcepto("").size() == 3, "Búsqueda vacía lista todo");

        errorEsperado(() -> new Gasto(" ", BigDecimal.ONE, "Otros"), "Concepto vacío inválido");
        errorEsperado(() -> new Gasto("Test", BigDecimal.ZERO, "Otros"), "Monto cero inválido");
        errorEsperado(() -> new Gasto("Test", new BigDecimal("-1"), "Otros"), "Monto negativo inválido");
        errorEsperado(() -> new Gasto("Test", new BigDecimal("1.555"), "Otros"), "Decimales inválidos");
        errorEsperado(() -> new Gasto("Test", BigDecimal.ONE, " "), "Categoría vacía inválida");
        errorEsperado(() -> registro.filtrarMayoresA(new BigDecimal("-1")), "Umbral negativo inválido");
        boolean listaProtegida = false;
        try {
            registro.listar().clear();
        } catch (UnsupportedOperationException error) {
            listaProtegida = true;
        }
        comprobar(listaProtegida, "Listado no modificable");

        System.out.println("CORRECTO: " + verificaciones + " verificaciones de gastos.");
    }
}
