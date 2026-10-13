package analisis.controlgastosdeclarativo.model;

import java.math.BigDecimal;

/**
 * Representa el resultado agregado del cálculo declarativo del total acumulado.
 *
 * @param totalAcumulado Suma numérica total de todos los gastos (calculada por SUM en SQL).
 * @param cantidadRegistros Número total de registros contabilizados (calculada por COUNT en SQL).
 */
public record ResumenGastos(
        BigDecimal totalAcumulado,
        long cantidadRegistros
) {
}
