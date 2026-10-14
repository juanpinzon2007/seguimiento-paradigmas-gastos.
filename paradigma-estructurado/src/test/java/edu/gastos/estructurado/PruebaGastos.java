package edu.gastos.estructurado;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Pruebas sencillas (sin JUnit) del Control de Gastos estructurado.
 * Cada prueba compara un resultado esperado y imprime OK o FALLO.
 */
public class PruebaGastos {

    static int pruebas = 0;
    static int fallos = 0;

    public static void main(String[] args) {
        probarSumar();
        probarMayor();
        probarBusqueda();
        probarMenuCompleto();
        probarValidaciones();
        probarCapacidad();

        System.out.println();
        System.out.println("Pruebas: " + pruebas + " | Fallos: " + fallos);
        if (fallos > 0) {
            System.exit(1);
        }
    }

    // Compara un resultado con lo esperado e imprime OK o FALLO
    static void verificar(String nombre, boolean condicion) {
        pruebas++;
        if (condicion) {
            System.out.println("OK    - " + nombre);
        } else {
            fallos++;
            System.out.println("FALLO - " + nombre);
        }
    }

    // Ejecuta el programa con una entrada simulada y devuelve lo que imprimio
    static String ejecutar(String entrada) {
        Aplicacion.cantidadGastos = 0;
        PrintStream salidaOriginal = System.out;
        java.io.InputStream entradaOriginal = System.in;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setIn(new ByteArrayInputStream(entrada.getBytes()));
        System.setOut(new PrintStream(buffer));
        Aplicacion.main(new String[0]);
        System.setOut(salidaOriginal);
        System.setIn(entradaOriginal);
        return buffer.toString();
    }

    // ---- Pruebas de metodos auxiliares ----

    static void probarSumar() {
        double[] datos = {10.5, 20, 30};
        verificar("sumarMontos suma 3 valores", Aplicacion.sumarMontos(datos, 3) == 60.5);
        verificar("sumarMontos con 0 elementos es 0", Aplicacion.sumarMontos(datos, 0) == 0);
    }

    static void probarMayor() {
        double[] datos = {5, 99.9, 40, 99.9};
        verificar("buscarPosicionMayor devuelve la posicion 1",
                Aplicacion.buscarPosicionMayor(datos, 4) == 1);
    }

    static void probarBusqueda() {
        verificar("busqueda parcial", Aplicacion.contieneTexto("Almuerzo en casa", "muerzo"));
        verificar("busqueda sin distinguir mayusculas", Aplicacion.contieneTexto("Almuerzo", "ALMUERZO"));
        verificar("busqueda sin coincidencia", !Aplicacion.contieneTexto("Taxi", "pan"));
    }

    // ---- Pruebas del programa completo con entrada simulada ----

    static void probarMenuCompleto() {
        String entrada = "1\nCafe\n12.5\nComida\n"
                + "1\nTaxi\n30\nTransporte\n"
                + "1\nMercado\n80\nHogar\n"
                + "2\n3\n4\n5\n20\n6\nTAX\n0\n";
        String salida = ejecutar(entrada);

        verificar("el menu muestra el titulo", salida.contains("===== CONTROL DE GASTOS ====="));
        verificar("registra 3 gastos", Aplicacion.cantidadGastos == 3);
        verificar("mostrar gastos lista el Cafe", salida.contains("1. Cafe | $12.5 | Comida"));
        verificar("total = 122.5", salida.contains("Gasto total: $122.5"));
        verificar("gasto mayor es Mercado", salida.contains("Gasto de mayor valor:\n3. Mercado")
                || salida.contains("Gasto de mayor valor:\r\n3. Mercado"));
        verificar("filtro > 20 muestra Taxi", salida.contains("2. Taxi | $30.0 | Transporte"));
        verificar("busqueda 'TAX' encuentra Taxi", salida.lastIndexOf("Taxi") > salida.indexOf("Texto a buscar"));
        verificar("termina con 'Hasta luego'", salida.contains("Hasta luego."));
    }

    static void probarValidaciones() {
        String entrada = "abc\n9\n"                      // opcion no numerica y opcion invalida
                + "1\n\nCafe\n-5\nxyz\n12,5\n\nComida\n" // concepto vacio, monto negativo, no numerico
                + "0\n";
        String salida = ejecutar(entrada);

        verificar("rechaza opcion no numerica", salida.contains("Ingrese un numero entero"));
        verificar("rechaza opcion fuera del menu", salida.contains("Opcion no valida."));
        verificar("rechaza concepto vacio", salida.contains("El texto no puede estar vacio."));
        verificar("rechaza monto negativo", salida.contains("El monto no puede ser negativo."));
        verificar("rechaza monto no numerico", salida.contains("Ingrese un numero valido."));
        verificar("acepta monto con coma", Aplicacion.montos[0] == 12.5);
        verificar("solo se guarda 1 gasto valido", Aplicacion.cantidadGastos == 1);
    }

    static void probarCapacidad() {
        StringBuilder entrada = new StringBuilder();
        for (int i = 0; i < 101; i++) {
            entrada.append("1\nGasto").append(i).append("\n1\nVarios\n");
        }
        entrada.append("0\n");
        String salida = ejecutar(entrada.toString());

        verificar("no supera los 100 gastos", Aplicacion.cantidadGastos == 100);
        verificar("avisa que no hay espacio", salida.contains("No hay espacio"));
    }
}
