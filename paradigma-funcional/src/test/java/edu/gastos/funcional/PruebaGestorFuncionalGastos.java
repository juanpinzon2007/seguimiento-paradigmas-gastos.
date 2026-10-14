package edu.gastos.funcional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Pruebas automaticas sin dependencias externas. */
public final class PruebaGestorFuncionalGastos {

    private static int verificaciones;

    private PruebaGestorFuncionalGastos() {
    }

    public static void main(String[] args) {
        Gasto mercado = new Gasto(
                "Compra en supermercado", new BigDecimal("120000"), "Alimentacion");
        Gasto taxi = new Gasto(
                "Taxi al trabajo", new BigDecimal("25000.50"), "Transporte");
        Gasto cine = new Gasto(
                "Entradas de cine", new BigDecimal("45000"), "Entretenimiento");

        List<Gasto> vacia = GestorFuncionalGastos.listaVacia();
        comprobar(vacia.isEmpty(), "La lista inicial debe estar vacia");
        comprobar(GestorFuncionalGastos.calcularTotal(vacia).compareTo(BigDecimal.ZERO) == 0,
                "El total de una lista vacia debe ser cero");
        comprobar(GestorFuncionalGastos.mayorGasto(vacia).isEmpty(),
                "No debe existir un gasto mayor en una lista vacia");

        List<Gasto> conUno = GestorFuncionalGastos.registrar(vacia, mercado);
        comprobar(conUno.equals(List.of(mercado)), "Debe registrar un gasto");
        comprobar(vacia.isEmpty(), "Registrar no debe modificar la lista original");

        List<Gasto> conDos = GestorFuncionalGastos.registrar(conUno, taxi);
        List<Gasto> conTres = GestorFuncionalGastos.registrar(conDos, cine);
        comprobar(conTres.size() == 3, "Debe registrar varios gastos");
        comprobar(conUno.size() == 1 && conDos.size() == 2,
                "Cada registro debe producir una nueva version de la lista");

        List<Gasto> listado = GestorFuncionalGastos.listar(conTres);
        comprobar(listado.equals(conTres), "El listado debe incluir todos los gastos");
        comprobarNoModificable(listado, "El listado debe ser inmutable");

        List<BigDecimal> montos = GestorFuncionalGastos.extraerMontos(conTres);
        comprobar(montos.equals(List.of(
                        new BigDecimal("120000.00"),
                        new BigDecimal("25000.50"),
                        new BigDecimal("45000.00"))),
                "map debe transformar los gastos en sus montos");
        comprobarNoModificable(montos, "La lista producida por map debe ser inmutable");

        BigDecimal totalEsperado = new BigDecimal("190000.50");
        comprobar(GestorFuncionalGastos.calcularTotal(conTres).compareTo(totalEsperado) == 0,
                "map y reduce deben calcular el total exacto");
        comprobar(GestorFuncionalGastos.mayorGasto(conTres).orElseThrow().equals(mercado),
                "max debe encontrar el gasto de mayor monto");

        List<Gasto> mayores = GestorFuncionalGastos.mayoresA(
                conTres, new BigDecimal("45000"));
        comprobar(mayores.equals(List.of(mercado)),
                "filter debe conservar solo gastos estrictamente mayores al umbral");
        comprobar(conTres.size() == 3, "Filtrar no debe modificar la lista original");
        comprobarNoModificable(mayores, "El resultado del filtro debe ser inmutable");

        List<Gasto> coincidencias = GestorFuncionalGastos.buscarPorConcepto(conTres, "MERC");
        comprobar(coincidencias.equals(List.of(mercado)),
                "La busqueda debe ser parcial e ignorar mayusculas");
        comprobar(GestorFuncionalGastos.buscarPorConcepto(conTres, "in").equals(List.of(cine)),
                "La busqueda debe devolver todas las coincidencias aplicables");
        comprobar(GestorFuncionalGastos.buscarPorConcepto(conTres, "alquiler").isEmpty(),
                "La busqueda sin coincidencias debe devolver una lista vacia");

        List<Gasto> copiaMutable = new ArrayList<>(conTres);
        List<Gasto> nuevaLista = GestorFuncionalGastos.registrar(
                copiaMutable,
                new Gasto("Internet", new BigDecimal("80000"), "Servicios"));
        comprobar(copiaMutable.size() == 3 && nuevaLista.size() == 4,
                "Registrar no debe cambiar ni siquiera una entrada mutable");

        comprobar(GestorFuncionalGastos.calcularTotal(conTres)
                        .equals(GestorFuncionalGastos.calcularTotal(conTres)),
                "Una funcion pura debe devolver el mismo resultado para la misma entrada");
        comprobar(GestorFuncionalGastos.mayoresA(conTres, BigDecimal.ZERO)
                        .equals(GestorFuncionalGastos.mayoresA(conTres, BigDecimal.ZERO)),
                "El filtrado puro debe ser consistente");

        errorEsperado(() -> new Gasto(" ", BigDecimal.ONE, "Otros"),
                "Debe rechazar un concepto vacio");
        errorEsperado(() -> new Gasto("Prueba", BigDecimal.ZERO, "Otros"),
                "Debe rechazar un monto cero");
        errorEsperado(() -> new Gasto("Prueba", new BigDecimal("-1"), "Otros"),
                "Debe rechazar un monto negativo");
        errorEsperado(() -> new Gasto("Prueba", new BigDecimal("1.999"), "Otros"),
                "Debe rechazar un monto con mas de dos decimales");
        errorEsperado(() -> new Gasto("Prueba", BigDecimal.ONE, " "),
                "Debe rechazar una categoria vacia");
        errorEsperado(() -> GestorFuncionalGastos.mayoresA(conTres, new BigDecimal("-1")),
                "Debe rechazar un umbral negativo");
        errorEsperado(() -> GestorFuncionalGastos.buscarPorConcepto(conTres, " "),
                "Debe rechazar una busqueda vacia");

        System.out.println("CORRECTO: " + verificaciones + " verificaciones funcionales superadas.");
    }

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
        } catch (IllegalArgumentException excepcion) {
            lanzoError = true;
        }
        comprobar(lanzoError, mensaje);
    }

    private static void comprobarNoModificable(List<?> lista, String mensaje) {
        boolean protegida = false;
        try {
            lista.clear();
        } catch (UnsupportedOperationException excepcion) {
            protegida = true;
        }
        comprobar(protegida, mensaje);
    }
}
