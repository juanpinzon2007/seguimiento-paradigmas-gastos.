package edu.gastos.funcional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Funciones puras para operar sobre colecciones inmutables de gastos.
 * Esta clase no mantiene estado, no lee datos y no imprime resultados.
 */
public final class GestorFuncionalGastos {

    private static final Function<Gasto, BigDecimal> EXTRAER_MONTO = Gasto::monto;
    private static final BinaryOperator<BigDecimal> SUMAR_MONTOS = BigDecimal::add;

    private GestorFuncionalGastos() {
    }

    /** Crea la coleccion inmutable inicial. */
    public static List<Gasto> listaVacia() {
        return List.of();
    }

    /**
     * Registra un gasto sin modificar la lista recibida.
     *
     * @return una nueva lista inmutable que incluye el gasto
     */
    public static List<Gasto> registrar(List<Gasto> gastos, Gasto nuevoGasto) {
        List<Gasto> listaOriginal = copiarLista(gastos);
        Objects.requireNonNull(nuevoGasto, "El gasto no puede ser nulo");

        return Stream.concat(listaOriginal.stream(), Stream.of(nuevoGasto)).toList();
    }

    /** Devuelve una copia inmutable para listar sin exponer estado modificable. */
    public static List<Gasto> listar(List<Gasto> gastos) {
        return copiarLista(gastos);
    }

    /** Transformacion map explicita de gastos a montos. */
    public static List<BigDecimal> extraerMontos(List<Gasto> gastos) {
        return copiarLista(gastos).stream()
                .map(EXTRAER_MONTO)
                .toList();
    }

    /** Calcula el total con map y reduce, sin ciclos ni acumuladores mutables. */
    public static BigDecimal calcularTotal(List<Gasto> gastos) {
        return copiarLista(gastos).stream()
                .map(EXTRAER_MONTO)
                .reduce(BigDecimal.ZERO.setScale(2), SUMAR_MONTOS);
    }

    /** Busca el gasto de mayor monto sin modificar la coleccion. */
    public static Optional<Gasto> mayorGasto(List<Gasto> gastos) {
        return copiarLista(gastos).stream()
                .max(Comparator.comparing(Gasto::monto));
    }

    /**
     * Funcion de orden superior: recibe un Predicate y devuelve un nuevo
     * subconjunto inmutable mediante filter.
     */
    public static List<Gasto> filtrar(List<Gasto> gastos, Predicate<Gasto> criterio) {
        Objects.requireNonNull(criterio, "El criterio no puede ser nulo");
        return copiarLista(gastos).stream()
                .filter(criterio)
                .toList();
    }

    /** Filtra con una lambda los gastos estrictamente superiores al umbral. */
    public static List<Gasto> mayoresA(List<Gasto> gastos, BigDecimal umbral) {
        Objects.requireNonNull(umbral, "El monto minimo no puede ser nulo");
        if (umbral.signum() < 0) {
            throw new IllegalArgumentException("El monto minimo no puede ser negativo.");
        }

        Predicate<Gasto> superaUmbral = gasto -> gasto.monto().compareTo(umbral) > 0;
        return filtrar(gastos, superaUmbral);
    }

    /** Busca todas las coincidencias parciales sin distinguir mayusculas. */
    public static List<Gasto> buscarPorConcepto(List<Gasto> gastos, String palabraClave) {
        String consulta = textoBusqueda(palabraClave);
        Predicate<Gasto> contieneConcepto = gasto -> gasto.concepto()
                .toLowerCase(Locale.ROOT)
                .contains(consulta);
        return filtrar(gastos, contieneConcepto);
    }

    private static List<Gasto> copiarLista(List<Gasto> gastos) {
        Objects.requireNonNull(gastos, "La lista de gastos no puede ser nula");
        return List.copyOf(gastos);
    }

    private static String textoBusqueda(String palabraClave) {
        if (palabraClave == null || palabraClave.trim().isEmpty()) {
            throw new IllegalArgumentException("La palabra clave es obligatoria.");
        }
        return palabraClave.trim().toLowerCase(Locale.ROOT);
    }
}
