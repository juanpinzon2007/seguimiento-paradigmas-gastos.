package edu.gastos.funcional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

/** Punto de entrada de consola para demostrar las seis funcionalidades. */
public final class MainFuncional {

    private static final Function<Gasto, String> FORMATEAR_GASTO = gasto -> String.format(
            "Concepto: %s | Monto: $%s | Categoria: %s",
            gasto.concepto(), gasto.monto().toPlainString(), gasto.categoria());

    private static final Consumer<Gasto> MOSTRAR_GASTO = gasto ->
            System.out.println(FORMATEAR_GASTO.apply(gasto));

    private MainFuncional() {
    }

    public static void main(String[] args) {
        List<Gasto> gastos = GestorFuncionalGastos.listaVacia();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean continuar = true;
            while (continuar) {
                mostrarMenu();
                String opcion = scanner.nextLine().trim();

                try {
                    switch (opcion) {
                        case "1" -> gastos = registrarDesdeConsola(scanner, gastos);
                        case "2" -> mostrarListado(GestorFuncionalGastos.listar(gastos));
                        case "3" -> mostrarTotal(gastos);
                        case "4" -> mostrarMayor(gastos);
                        case "5" -> filtrarPorMonto(scanner, gastos);
                        case "6" -> buscarPorConcepto(scanner, gastos);
                        case "0" -> continuar = false;
                        default -> System.out.println("Opcion no valida. Intenta de nuevo.");
                    }
                } catch (IllegalArgumentException excepcion) {
                    System.out.println("Dato invalido: " + excepcion.getMessage());
                }
            }
        }

        System.out.println("Aplicacion finalizada.");
    }

    private static void mostrarMenu() {
        System.out.println("\n=== CONTROL FUNCIONAL DE GASTOS ===");
        System.out.println("1. Registrar gasto");
        System.out.println("2. Mostrar listado de gastos");
        System.out.println("3. Calcular gasto total acumulado");
        System.out.println("4. Mostrar gasto de mayor valor");
        System.out.println("5. Mostrar gastos mayores a un monto");
        System.out.println("6. Buscar gasto por concepto");
        System.out.println("0. Salir");
        System.out.print("Selecciona una opcion: ");
    }

    private static List<Gasto> registrarDesdeConsola(Scanner scanner, List<Gasto> gastos) {
        System.out.print("Concepto: ");
        String concepto = scanner.nextLine();
        System.out.print("Monto (ejemplo 25000.50): ");
        BigDecimal monto = leerMonto(scanner.nextLine());
        System.out.print("Categoria (Alimentacion, Transporte, Servicios, Entretenimiento): ");
        String categoria = scanner.nextLine();

        Gasto nuevoGasto = new Gasto(concepto, monto, categoria);
        List<Gasto> nuevaLista = GestorFuncionalGastos.registrar(gastos, nuevoGasto);
        System.out.println("Gasto registrado. La coleccion original no fue modificada.");
        return nuevaLista;
    }

    private static void mostrarListado(List<Gasto> gastos) {
        if (gastos.isEmpty()) {
            System.out.println("No hay gastos registrados.");
            return;
        }
        gastos.forEach(MOSTRAR_GASTO);
    }

    private static void mostrarTotal(List<Gasto> gastos) {
        BigDecimal total = GestorFuncionalGastos.calcularTotal(gastos);
        System.out.println("Gasto total acumulado: $" + total.toPlainString());
    }

    private static void mostrarMayor(List<Gasto> gastos) {
        GestorFuncionalGastos.mayorGasto(gastos)
                .ifPresentOrElse(
                        MOSTRAR_GASTO,
                        () -> System.out.println("No hay gastos registrados."));
    }

    private static void filtrarPorMonto(Scanner scanner, List<Gasto> gastos) {
        System.out.print("Monto minimo: ");
        BigDecimal umbral = leerMonto(scanner.nextLine());
        List<Gasto> encontrados = GestorFuncionalGastos.mayoresA(gastos, umbral);
        mostrarResultadoConsulta(encontrados);
    }

    private static void buscarPorConcepto(Scanner scanner, List<Gasto> gastos) {
        System.out.print("Palabra clave: ");
        String palabraClave = scanner.nextLine();
        List<Gasto> encontrados = GestorFuncionalGastos.buscarPorConcepto(gastos, palabraClave);
        mostrarResultadoConsulta(encontrados);
    }

    private static void mostrarResultadoConsulta(List<Gasto> encontrados) {
        if (encontrados.isEmpty()) {
            System.out.println("No se encontraron gastos que cumplan el criterio.");
            return;
        }
        encontrados.forEach(MOSTRAR_GASTO);
    }

    private static BigDecimal leerMonto(String texto) {
        try {
            return new BigDecimal(texto.trim().replace(',', '.'));
        } catch (NumberFormatException excepcion) {
            throw new IllegalArgumentException("El monto debe ser un numero valido.");
        }
    }
}
