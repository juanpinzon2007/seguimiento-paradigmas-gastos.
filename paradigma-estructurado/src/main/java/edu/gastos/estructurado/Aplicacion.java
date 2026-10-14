package edu.gastos.estructurado;

import java.util.Scanner;

/**
 * Sistema de Control de Gastos Personales - Paradigma estructurado.
 * Una sola clase (Java la exige), metodos static y arreglos paralelos.
 */
public class Aplicacion {

    // ---- Variables globales: arreglos paralelos (misma posicion = mismo gasto) ----
    static String[] conceptos = new String[100];
    static double[] montos = new double[100];
    static String[] categorias = new String[100];
    static int cantidadGastos = 0;

    static Scanner teclado;

    // ---- Programa principal: ciclo del menu ----
    public static void main(String[] args) {
        teclado = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            mostrarMenu();
            opcion = Integer.parseInt(teclado.nextLine().trim());

            if (opcion == 1) {
                registrarGasto();
            } else if (opcion == 2) {
                mostrarGastos();
            } else if (opcion == 3) {
                calcularTotal();
            } else if (opcion == 4) {
                mostrarGastoMayor();
            } else if (opcion == 5) {
                filtrarPorMonto();
            } else if (opcion == 6) {
                buscarPorConcepto();
            } else if (opcion == 0) {
                System.out.println("Hasta luego.");
            } else {
                System.out.println("Opcion no valida.");
            }
        }
    }

    // ---- Menu ----
    public static void mostrarMenu() {
        System.out.println();
        System.out.println("===== CONTROL DE GASTOS =====");
        System.out.println("1. Registrar gasto");
        System.out.println("2. Mostrar gastos");
        System.out.println("3. Calcular gasto total");
        System.out.println("4. Mostrar gasto de mayor valor");
        System.out.println("5. Filtrar gastos por monto");
        System.out.println("6. Buscar gasto por concepto");
        System.out.println("0. Salir");
        System.out.println();
        System.out.print("Seleccione una opci\u00f3n: ");
    }

    // ---- 1. Registrar gasto ----
    public static void registrarGasto() {
        System.out.print("Concepto: ");
        String concepto = teclado.nextLine();
        System.out.print("Monto: ");
        double monto = Double.parseDouble(teclado.nextLine());
        System.out.print("Categoria: ");
        String categoria = teclado.nextLine();

        conceptos[cantidadGastos] = concepto;
        montos[cantidadGastos] = monto;
        categorias[cantidadGastos] = categoria;
        cantidadGastos++;
        System.out.println("Gasto registrado.");
    }

    // ---- 2. Mostrar gastos ----
    public static void mostrarGastos() {
        if (cantidadGastos == 0) {
            System.out.println("No hay gastos registrados.");
        } else {
            for (int i = 0; i < cantidadGastos; i++) {
                imprimirGasto(i);
            }
        }
    }

    // Imprime el gasto de la posicion indicada (recibe la posicion como parametro)
    public static void imprimirGasto(int posicion) {
        System.out.println((posicion + 1) + ". " + conceptos[posicion]
                + " | $" + montos[posicion] + " | " + categorias[posicion]);
    }

    // ---- 3. Calcular total ----
    public static void calcularTotal() {
        double total = sumarMontos(montos, cantidadGastos);
        System.out.println("Gasto total: $" + total);
    }

    // Suma los primeros 'cantidad' montos del arreglo
    public static double sumarMontos(double[] lista, int cantidad) {
        double suma = 0;
        for (int i = 0; i < cantidad; i++) {
            suma = suma + lista[i];
        }
        return suma;
    }

    // ---- 4. Gasto mayor ----
    public static void mostrarGastoMayor() {
        if (cantidadGastos == 0) {
            System.out.println("No hay gastos registrados.");
        } else {
            int posicionMayor = buscarPosicionMayor(montos, cantidadGastos);
            System.out.println("Gasto de mayor valor:");
            imprimirGasto(posicionMayor);
        }
    }

    // Devuelve la posicion del monto mas alto
    public static int buscarPosicionMayor(double[] lista, int cantidad) {
        int posicionMayor = 0;
        for (int i = 1; i < cantidad; i++) {
            if (lista[i] > lista[posicionMayor]) {
                posicionMayor = i;
            }
        }
        return posicionMayor;
    }

    // ---- 5. Filtrar por monto ----
    public static void filtrarPorMonto() {
        System.out.print("Mostrar gastos superiores a: ");
        double limite = Double.parseDouble(teclado.nextLine());
        int encontrados = 0;

        for (int i = 0; i < cantidadGastos; i++) {
            if (montos[i] > limite) {
                imprimirGasto(i);
                encontrados++;
            }
        }
        if (encontrados == 0) {
            System.out.println("No hay gastos superiores a $" + limite);
        }
    }

    // ---- 6. Buscar por concepto ----
    public static void buscarPorConcepto() {
        System.out.print("Texto a buscar: ");
        String texto = teclado.nextLine();
        int encontrados = 0;

        for (int i = 0; i < cantidadGastos; i++) {
            if (contieneTexto(conceptos[i], texto)) {
                imprimirGasto(i);
                encontrados++;
            }
        }
        if (encontrados == 0) {
            System.out.println("No se encontraron gastos con ese concepto.");
        }
    }

    // Coincidencia parcial sin distinguir mayusculas y minusculas
    public static boolean contieneTexto(String concepto, String texto) {
        return concepto.toLowerCase().contains(texto.toLowerCase());
    }
}
