package main.java.edu.gastos.poo;

import java.util.ArrayList;
import java.util.List;

public class GestorGastos {
    private List<Gasto> listaGastos;

    public GestorGastos() {
        this.listaGastos = new ArrayList<>();
    }

    public void registrarGasto(String concepto, double monto, String categoria) {
        Gasto nuevoGasto = new Gasto(concepto, monto, categoria);
        listaGastos.add(nuevoGasto);
        System.out.println("Gasto registrado exitosamente.");
    }

    public void mostrarListadoGastos() {
        if (listaGastos.isEmpty()) {
            System.out.println("No hay gastos registrados.");
            return;
        }
        System.out.println("--- Listado de Gastos ---");
        for (Gasto gasto : listaGastos) {
            System.out.println(gasto.toString());
        }
    }

    public void calcularGastoTotal() {
        double total = 0;
        for (Gasto gasto : listaGastos) {
            total += gasto.getMonto();
        }
        System.out.printf("El gasto total acumulado es: $%.2f\n", total);
    }

    public void mostrarGastoMayor() {
        if (listaGastos.isEmpty()) {
            System.out.println("No hay gastos registrados.");
            return;
        }
        Gasto mayor = listaGastos.get(0);
        for (Gasto gasto : listaGastos) {
            if (gasto.getMonto() > mayor.getMonto()) {
                mayor = gasto;
            }
        }
        System.out.println("--- Gasto de Mayor Valor ---");
        System.out.println(mayor.toString());
    }

    public void mostrarGastosMayoresA(double umbral) {
        System.out.printf("--- Gastos mayores a $%.2f ---\n", umbral);
        boolean encontrados = false;
        for (Gasto gasto : listaGastos) {
            if (gasto.getMonto() > umbral) {
                System.out.println(gasto.toString());
                encontrados = true;
            }
        }
        if (!encontrados) {
            System.out.println("No se encontraron gastos superiores a ese monto.");
        }
    }

    public void buscarGastoPorConcepto(String palabraClave) {
        System.out.println("--- Resultados de Búsqueda para: '" + palabraClave + "' ---");
        boolean encontrados = false;
        for (Gasto gasto : listaGastos) {
            if (gasto.getConcepto().toLowerCase().contains(palabraClave.toLowerCase())) {
                System.out.println(gasto.toString());
                encontrados = true;
            }
        }
        if (!encontrados) {
            System.out.println("No se encontraron coincidencias.");
        }
    }
}