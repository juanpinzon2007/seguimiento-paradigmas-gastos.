package analisis.controlgastosdeclarativo;

import analisis.controlgastosdeclarativo.database.DatabaseConnection;
import analisis.controlgastosdeclarativo.model.Gasto;
import analisis.controlgastosdeclarativo.model.ResumenGastos;
import analisis.controlgastosdeclarativo.service.GastoService;
import analisis.controlgastosdeclarativo.util.TablaFormatter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * Aplicación de Consola para el Sistema de Control de Gastos Personales.
 *
 * PARADIGMA: 4. Paradigma Declarativo
 * Característica central: Se expresa explícitamente QUÉ datos se desean obtener o modificar
 * mediante sentencias SQL, delegando al motor relacional (MySQL) la optimización, el plan de
 * ejecución y la lógica del CÓMO iterar, filtrar y acumular.
 */
public class ControlGastosDeclarativoApplication {

    private static final Scanner scanner = new Scanner(System.in);
    private static final GastoService service = new GastoService();

    public static void main(String[] args) {
        System.out.println("==========================================================================");
        System.out.println("      SISTEMA DE CONTROL DE GASTOS PERSONALES - PARADIGMA DECLARATIVO     ");
        System.out.println("==========================================================================");
        System.out.println("Verificando conexion con el motor de base de datos relacional MySQL...");

        try {
            DatabaseConnection.inicializarEsquema();
            System.out.println("[OK] Conexion exitosa y tabla 'gastos' lista.\n");
        } catch (Exception e) {
            System.err.println("[ADVERTENCIA] No se pudo inicializar la base de datos automáticamente.");
            System.err.println("Detalle: " + e.getMessage());
            System.err.println("Asegúrese de que el servicio MySQL (XAMPP) este iniciado en el puerto 3306.\n");
        }

        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1" -> opcionRegistrarGasto();
                case "2" -> opcionMostrarListadoGastos();
                case "3" -> opcionCalcularTotalAcumulado();
                case "4" -> opcionMostrarGastoMayorValor();
                case "5" -> opcionMostrarGastosMayoresAUmbral();
                case "6" -> opcionBuscarPorConcepto();
                case "7" -> {
                    System.out.println("\nGracias por utilizar el Sistema de Control de Gastos (Paradigma Declarativo). ¡Hasta pronto!");
                    salir = true;
                }
                default -> System.out.println("\n[!] Opcion no valida. Por favor ingrese un numero del 1 al 7.");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n==========================================================================");
        System.out.println("                             MENU PRINCIPAL                               ");
        System.out.println("==========================================================================");
        System.out.println(" 1. Registrar gasto");
        System.out.println(" 2. Mostrar listado de gastos");
        System.out.println(" 3. Calcular gasto total acumulado");
        System.out.println(" 4. Mostrar el gasto de mayor valor");
        System.out.println(" 5. Mostrar gastos mayores a un monto especifico");
        System.out.println(" 6. Buscar gasto por concepto (subcadena)");
        System.out.println(" 7. Salir");
        System.out.println("==========================================================================");
        System.out.print("Seleccione una opcion (1-7): ");
    }

    /**
     * Funcionalidad 1: Registrar gasto.
     */
    private static void opcionRegistrarGasto() {
        System.out.println("\n--------------------------------------------------------------------------");
        System.out.println("  1. REGISTRAR GASTO [INSERT INTO gastos VALUES (...)]");
        System.out.println("--------------------------------------------------------------------------");
        try {
            System.out.print("Ingrese concepto o descripcion (ej. Almuerzo, Gasolina): ");
            String concepto = scanner.nextLine().trim();
            if (concepto.isEmpty()) {
                System.out.println("[!] El concepto no puede estar vacio.");
                return;
            }

            System.out.print("Ingrese el monto del gasto ($): ");
            String montoStr = scanner.nextLine().trim().replace(",", ".");
            BigDecimal monto;
            try {
                monto = new BigDecimal(montoStr);
                if (monto.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("[!] El monto debe ser mayor que 0.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("[!] Ingrese un valor numerico valido para el monto.");
                return;
            }

            System.out.println("\nCategorias sugeridas: 1. Alimentacion | 2. Transporte | 3. Servicios | 4. Entretenimiento | 5. Otra");
            System.out.print("Seleccione el numero de categoria o escriba una categoria personalizada: ");
            String catEntrada = scanner.nextLine().trim();
            String categoria = switch (catEntrada) {
                case "1" -> "Alimentación";
                case "2" -> "Transporte";
                case "3" -> "Servicios";
                case "4" -> "Entretenimiento";
                default -> catEntrada.isEmpty() ? "Varios" : catEntrada;
            };

            Gasto registrado = service.registrarGasto(concepto, monto, categoria);
            System.out.println("\n[OK] Gasto registrado exitosamente con ID: " + registrado.id());
            System.out.printf("  Concepto: %s | Categoria: %s | Monto: %s\n",
                    registrado.concepto(), registrado.categoria(), TablaFormatter.formatearMoneda(registrado.monto()));
            System.out.println("  (Operación declarativa: la tupla fue delegada al motor relacional).");

        } catch (Exception e) {
            System.err.println("[Error] No se pudo registrar el gasto: " + e.getMessage());
        }
    }

    /**
     * Funcionalidad 2: Mostrar listado de gastos.
     */
    private static void opcionMostrarListadoGastos() {
        try {
            List<Gasto> lista = service.listarGastos();
            TablaFormatter.imprimirTablaGastos(lista, "Listado General de Gastos Registrados (SELECT * FROM gastos)");
            System.out.println("  (Declarativo: La proyección y ordenamiento fueron resueltos por el motor SQL).");
        } catch (Exception e) {
            System.err.println("[Error] No se pudo recuperar el listado de gastos: " + e.getMessage());
        }
    }

    /**
     * Funcionalidad 3: Calcular gasto total acumulado.
     */
    private static void opcionCalcularTotalAcumulado() {
        System.out.println("\n--------------------------------------------------------------------------");
        System.out.println("  3. CALCULAR GASTO TOTAL ACUMULADO [SELECT SUM(monto), COUNT(*) ...]");
        System.out.println("--------------------------------------------------------------------------");
        try {
            ResumenGastos resumen = service.obtenerResumenTotal();
            System.out.println("\n  ===============================================================");
            System.out.printf("   Total de gastos registrados : %d registro(s)\n", resumen.cantidadRegistros());
            System.out.printf("   Gasto Total Acumulado       : %s\n", TablaFormatter.formatearMoneda(resumen.totalAcumulado()));
            System.out.println("  ===============================================================");
            System.out.println("  [Enfoque Declarativo]");
            System.out.println("  - El calculo fue resuelto mediante la funcion de agregacion SUM(monto) en MySQL.");
            System.out.println("  - Java NO ejecuto ningun bucle for/while ni variables acumuladoras.");
        } catch (Exception e) {
            System.err.println("[Error] Error al calcular el total acumulado: " + e.getMessage());
        }
    }

    /**
     * Funcionalidad 4: Mostrar el gasto de mayor valor.
     */
    private static void opcionMostrarGastoMayorValor() {
        try {
            List<Gasto> mayores = service.obtenerGastosDeMayorValor();
            TablaFormatter.imprimirTablaGastos(mayores, "Gasto(s) de Mayor Valor [WHERE monto = (SELECT MAX(monto) FROM gastos)]");
            System.out.println("  [Enfoque Declarativo]");
            System.out.println("  - El registro maximo fue determinado por una subconsulta relacional declarativa MAX().");
            System.out.println("  - El motor SQL se encargo de identificar el maximo sin comparaciones manuales en Java.");
        } catch (Exception e) {
            System.err.println("[Error] Error al obtener el gasto de mayor valor: " + e.getMessage());
        }
    }

    /**
     * Funcionalidad 5: Mostrar gastos mayores a un monto específico.
     */
    private static void opcionMostrarGastosMayoresAUmbral() {
        System.out.println("\n--------------------------------------------------------------------------");
        System.out.println("  5. MOSTRAR GASTOS MAYORES A UN MONTO ESPECIFICO [WHERE monto > ?]");
        System.out.println("--------------------------------------------------------------------------");
        try {
            System.out.print("Ingrese el monto umbral para filtrar (ej. 50 o 50000): ");
            String umbralStr = scanner.nextLine().trim().replace(",", ".");
            BigDecimal umbral;
            try {
                umbral = new BigDecimal(umbralStr);
                if (umbral.compareTo(BigDecimal.ZERO) < 0) {
                    System.out.println("[!] El umbral debe ser un valor positivo.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("[!] Ingrese un valor numerico valido.");
                return;
            }

            List<Gasto> filtrados = service.listarGastosMayoresA(umbral);
            String titulo = "Gastos con monto superior a " + TablaFormatter.formatearMoneda(umbral) + " (WHERE monto > " + umbral + ")";
            TablaFormatter.imprimirTablaGastos(filtrados, titulo);
            System.out.println("  [Enfoque Declarativo]");
            System.out.println("  - El filtrado fue ejecutado por el predicado relacional WHERE en la base de datos.");
            System.out.println("  - Solo los datos que cumplen la condicion viajan desde el motor relacional.");
        } catch (Exception e) {
            System.err.println("[Error] Error al filtrar gastos por monto: " + e.getMessage());
        }
    }

    /**
     * Funcionalidad 6: Buscar gasto por concepto (subcadena).
     */
    private static void opcionBuscarPorConcepto() {
        System.out.println("\n--------------------------------------------------------------------------");
        System.out.println("  6. BUSCAR GASTO POR CONCEPTO [WHERE LOWER(concepto) LIKE LOWER(?)]");
        System.out.println("--------------------------------------------------------------------------");
        try {
            System.out.print("Ingrese texto o palabra clave a buscar en la descripcion: ");
            String termino = scanner.nextLine().trim();
            if (termino.isEmpty()) {
                System.out.println("[!] Debe ingresar un termino de busqueda.");
                return;
            }

            List<Gasto> encontrados = service.buscarGastosPorConcepto(termino);
            String titulo = "Resultados de busqueda para: \"" + termino + "\" (LIKE '%" + termino + "%')";
            TablaFormatter.imprimirTablaGastos(encontrados, titulo);
            System.out.println("  [Enfoque Declarativo]");
            System.out.println("  - El matching de subcadena fue procesado por el operador declarativo LIKE en MySQL.");
        } catch (Exception e) {
            System.err.println("[Error] Error al buscar gastos: " + e.getMessage());
        }
    }
}
