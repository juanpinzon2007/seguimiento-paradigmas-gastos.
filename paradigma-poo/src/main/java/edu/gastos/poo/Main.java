package main.java.edu.gastos.poo;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorGastos gestor = new GestorGastos();
        int opcion;

        do {
            System.out.println("\n=== SISTEMA DE CONTROL DE GASTOS (POO) ===");
            System.out.println("1. Registrar gasto");
            System.out.println("2. Mostrar listado de gastos");
            System.out.println("3. Calcular gasto total acumulado");
            System.out.println("4. Mostrar el gasto de mayor valor");
            System.out.println("5. Mostrar gastos mayores a un monto específico");
            System.out.println("6. Buscar gasto por concepto");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, ingrese un número válido.");
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el concepto: ");
                    String concepto = scanner.nextLine();
                    System.out.print("Ingrese el monto: $");
                    double monto = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Ingrese la categoría: ");
                    String categoria = scanner.nextLine();
                    gestor.registrarGasto(concepto, monto, categoria);
                    break;
                case 2:
                    gestor.mostrarListadoGastos();
                    break;
                case 3:
                    gestor.calcularGastoTotal();
                    break;
                case 4:
                    gestor.mostrarGastoMayor();
                    break;
                case 5:
                    System.out.print("Ingrese el monto mínimo (umbral): $");
                    double umbral = scanner.nextDouble();
                    gestor.mostrarGastosMayoresA(umbral);
                    break;
                case 6:
                    System.out.print("Ingrese el concepto o palabra clave a buscar: ");
                    String palabra = scanner.nextLine();
                    gestor.buscarGastoPorConcepto(palabra);
                    break;
                case 7:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 7);

        scanner.close();
    }
}