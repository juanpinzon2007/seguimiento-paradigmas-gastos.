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
            opcion = leerEntero();

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
        // Validacion: no superar la capacidad de los arreglos
        if (cantidadGastos >= conceptos.length) {
            System.out.println("No hay espacio: se alcanzo el maximo de "
                    + conceptos.length + " gastos.");
            return;
        }

        String concepto = leerTextoNoVacio("Concepto: ");
        double monto = leerMontoNoNegativo("Monto: ");
        String categoria = leerTextoNoVacio("Categoria: ");

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
        double limite = leerMontoNoNegativo("Mostrar gastos superiores a: ");
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
        String texto = leerTextoNoVacio("Texto a buscar: ");
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

    // ---- Metodos de lectura con validacion (repiten hasta recibir un dato valido) ----

    // Lee un texto; no acepta vacio ni solo espacios
    public static String leerTextoNoVacio(String mensaje) {
        String texto = "";
        while (texto.length() == 0) {
            System.out.print(mensaje);
            texto = teclado.nextLine().trim();
            if (texto.length() == 0) {
                System.out.println("El texto no puede estar vacio.");
            }
        }
        return texto;
    }

    // Lee un monto; no acepta letras ni valores negativos (admite coma o punto decimal)
    public static double leerMontoNoNegativo(String mensaje) {
        double monto = -1;
        boolean valido = false;
        while (!valido) {
            System.out.print(mensaje);
            String linea = teclado.nextLine().trim().replace(',', '.');
            try {
                monto = Double.parseDouble(linea);
                if (Double.isNaN(monto) || Double.isInfinite(monto)) {
                    System.out.println("Ingrese un numero valido.");
                } else if (monto < 0) {
                    System.out.println("El monto no puede ser negativo.");
                } else {
                    valido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
            }
        }
        return monto;
    }

    // Lee un numero entero (para la opcion del menu)
    public static int leerEntero() {
        int numero = -1;
        boolean valido = false;
        while (!valido) {
            try {
                numero = Integer.parseInt(teclado.nextLine().trim());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un numero entero: ");
            }
        }
        return numero;
    }

    // Coincidencia parcial sin distinguir mayusculas y minusculas
    public static boolean contieneTexto(String concepto, String texto) {
        return concepto.toLowerCase().contains(texto.toLowerCase());
    }
}
