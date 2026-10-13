package edu.gastos.eventos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/** Operaciones de gastos independientes de la interfaz gráfica. */
public final class RegistroGastos {
    private final List<Gasto> gastos = new ArrayList<>();

    public void registrar(Gasto gasto) {
        gastos.add(Objects.requireNonNull(gasto, "El gasto no puede ser nulo"));
    }

    public List<Gasto> listar() {
        return Collections.unmodifiableList(new ArrayList<>(gastos));
    }

    public BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Gasto gasto : gastos) {
            total = total.add(gasto.getMonto());
        }
        return total.setScale(2);
    }

    public Optional<Gasto> mayorGasto() {
        Gasto mayor = null;
        for (Gasto gasto : gastos) {
            if (mayor == null || gasto.getMonto().compareTo(mayor.getMonto()) > 0) {
                mayor = gasto;
            }
        }
        return Optional.ofNullable(mayor);
    }

    /** Comparación estricta: solo valores mayores al umbral, no iguales. */
    public List<Gasto> filtrarMayoresA(BigDecimal umbral) {
        Objects.requireNonNull(umbral, "El umbral es obligatorio");
        if (umbral.signum() < 0) {
            throw new IllegalArgumentException("El monto mínimo no puede ser negativo.");
        }
        List<Gasto> encontrados = new ArrayList<>();
        for (Gasto gasto : gastos) {
            if (gasto.getMonto().compareTo(umbral) > 0) {
                encontrados.add(gasto);
            }
        }
        return encontrados;
    }

    /** Búsqueda por subcadena, sin distinguir mayúsculas y minúsculas. */
    public List<Gasto> buscarPorConcepto(String texto) {
        Objects.requireNonNull(texto, "La búsqueda no puede ser nula");
        String consulta = texto.trim().toLowerCase(Locale.ROOT);
        List<Gasto> encontrados = new ArrayList<>();
        for (Gasto gasto : gastos) {
            if (gasto.getConcepto().toLowerCase(Locale.ROOT).contains(consulta)) {
                encontrados.add(gasto);
            }
        }
        return encontrados;
    }
}
