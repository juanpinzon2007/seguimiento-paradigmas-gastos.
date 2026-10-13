package analisis.controlgastosdeclarativo.util;

import analisis.controlgastosdeclarativo.model.Gasto;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * Utilidad para imprimir resultados formateados en consola de forma tabular organizada.
 */
public class TablaFormatter {

    private static final NumberFormat FORMATO_MONEDA = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    static {
        // Asegurar visualización estándar
        FORMATO_MONEDA.setMaximumFractionDigits(2);
        FORMATO_MONEDA.setMinimumFractionDigits(2);
    }

    public static String formatearMoneda(BigDecimal monto) {
        if (monto == null) return "$0.00";
        return FORMATO_MONEDA.format(monto);
    }

    /**
     * Imprime una tabla estilizada con la lista de gastos recibida.
     */
    public static void imprimirTablaGastos(List<Gasto> gastos, String titulo) {
        System.out.println("\n---------------------------------------------------------------------------------------------------------");
        System.out.println("  " + titulo.toUpperCase());
        System.out.println("---------------------------------------------------------------------------------------------------------");

        if (gastos == null || gastos.isEmpty()) {
            System.out.println("  [!] No hay gastos para mostrar con los criterios seleccionados.");
            System.out.println("---------------------------------------------------------------------------------------------------------\n");
            return;
        }

        // Encabezados
        System.out.printf("| %-5s | %-32s | %-18s | %-16s | %-19s |\n",
                "ID", "CONCEPTO", "CATEGORIA", "MONTO ($)", "FECHA REGISTRO");
        System.out.println("+-------+----------------------------------+--------------------+------------------+---------------------+");

        for (Gasto g : gastos) {
            String idStr = (g.id() != null) ? String.valueOf(g.id()) : "-";
            String conceptoTrunc = truncar(g.concepto(), 32);
            String categoriaTrunc = truncar(g.categoria(), 18);
            String montoStr = formatearMoneda(g.monto());
            String fechaStr = (g.fecha() != null) ? g.fecha().format(FORMATO_FECHA) : "Reciente";

            System.out.printf("| %-5s | %-32s | %-18s | %16s | %-19s |\n",
                    idStr, conceptoTrunc, categoriaTrunc, montoStr, fechaStr);
        }

        System.out.println("---------------------------------------------------------------------------------------------------------");
        System.out.printf("  Total de registros mostrados: %d\n", gastos.size());
        System.out.println("---------------------------------------------------------------------------------------------------------\n");
    }

    private static String truncar(String texto, int longitudMaxima) {
        if (texto == null) return "";
        if (texto.length() <= longitudMaxima) return texto;
        return texto.substring(0, longitudMaxima - 3) + "...";
    }
}
