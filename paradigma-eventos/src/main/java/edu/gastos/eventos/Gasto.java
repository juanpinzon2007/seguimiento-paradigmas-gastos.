package edu.gastos.eventos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/** Registro de un egreso: concepto, monto y categoría. */
public final class Gasto {
    private final String concepto;
    private final BigDecimal monto;
    private final String categoria;

    public Gasto(String concepto, BigDecimal monto, String categoria) {
        this.concepto = textoObligatorio(concepto, "El concepto");
        this.categoria = textoObligatorio(categoria, "La categoría");
        Objects.requireNonNull(monto, "El monto no puede ser nulo");
        if (monto.signum() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero.");
        }
        try {
            this.monto = monto.setScale(2, RoundingMode.UNNECESSARY);
        } catch (ArithmeticException excepcion) {
            throw new IllegalArgumentException("El monto admite máximo dos decimales.");
        }
    }

    private static String textoObligatorio(String valor, String nombre) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(nombre + " es obligatorio.");
        }
        return valor.trim();
    }

    public String getConcepto() {
        return concepto;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public String getCategoria() {
        return categoria;
    }
}
