package edu.gastos.funcional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Valor inmutable que representa un gasto personal.
 *
 * @param concepto descripcion del gasto
 * @param monto valor monetario positivo con maximo dos decimales
 * @param categoria clasificacion del gasto
 */
public record Gasto(String concepto, BigDecimal monto, String categoria) {

    public Gasto {
        concepto = textoObligatorio(concepto, "El concepto");
        categoria = textoObligatorio(categoria, "La categoria");
        Objects.requireNonNull(monto, "El monto no puede ser nulo");

        if (monto.signum() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero.");
        }

        try {
            monto = monto.setScale(2, RoundingMode.UNNECESSARY);
        } catch (ArithmeticException excepcion) {
            throw new IllegalArgumentException("El monto admite maximo dos decimales.");
        }
    }

    private static String textoObligatorio(String valor, String nombre) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(nombre + " es obligatorio.");
        }
        return valor.trim();
    }
}
