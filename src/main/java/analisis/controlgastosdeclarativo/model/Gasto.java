package analisis.controlgastosdeclarativo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa la entidad inmutable de un Gasto en el sistema.
 * Implementado como un record de Java (enfoque conciso y declarativo de datos).
 *
 * @param id Identificador único autogenerado por el motor relacional.
 * @param concepto Descripción o concepto del gasto.
 * @param monto Valor numérico del gasto (BigDecimal para precisión financiera).
 * @param categoria Categoría a la que pertenece el gasto (ej. Alimentación, Transporte, etc.).
 * @param fecha Marca temporal del registro.
 */
public record Gasto(
        Integer id,
        String concepto,
        BigDecimal monto,
        String categoria,
        LocalDateTime fecha
) {
    /**
     * Constructor de conveniencia para registrar nuevos gastos antes de persistir en BD.
     */
    public Gasto(String concepto, BigDecimal monto, String categoria) {
        this(null, concepto, monto, categoria, null);
    }
}
