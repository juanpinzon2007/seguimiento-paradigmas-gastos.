package analisis.controlgastosdeclarativo.service;

import analisis.controlgastosdeclarativo.model.Gasto;
import analisis.controlgastosdeclarativo.model.ResumenGastos;
import analisis.controlgastosdeclarativo.repository.GastoRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * Capa de servicio para la gestión de gastos personales.
 * Aplica validaciones de negocio y delega la ejecución al repositorio declarativo.
 */
public class GastoService {

    private final GastoRepository repository;

    public GastoService() {
        this.repository = new GastoRepository();
    }

    public GastoService(GastoRepository repository) {
        this.repository = repository;
    }

    /**
     * 1. Registrar gasto con validaciones previas.
     */
    public Gasto registrarGasto(String concepto, BigDecimal monto, String categoria) throws SQLException {
        if (concepto == null || concepto.trim().isEmpty()) {
            throw new IllegalArgumentException("El concepto o descripción del gasto no puede estar vacío.");
        }
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser un valor numérico positivo mayor a cero.");
        }
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("La categoría no puede estar vacía.");
        }

        Gasto nuevoGasto = new Gasto(concepto.trim(), monto, categoria.trim());
        return repository.registrarGasto(nuevoGasto);
    }

    /**
     * 2. Mostrar listado de gastos registrados.
     */
    public List<Gasto> listarGastos() throws SQLException {
        return repository.obtenerTodosLosGastos();
    }

    /**
     * 3. Calcular gasto total acumulado mediante agregación declarativa SQL.
     */
    public ResumenGastos obtenerResumenTotal() throws SQLException {
        return repository.calcularTotalAcumulado();
    }

    /**
     * 4. Obtener el gasto o gastos de mayor valor.
     */
    public List<Gasto> obtenerGastosDeMayorValor() throws SQLException {
        return repository.obtenerGastosDeMayorValor();
    }

    /**
     * 5. Filtrar gastos mayores a un monto específico (umbral).
     */
    public List<Gasto> listarGastosMayoresA(BigDecimal umbral) throws SQLException {
        if (umbral == null || umbral.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El umbral de comparación debe ser un valor mayor o igual a cero.");
        }
        return repository.obtenerGastosMayoresA(umbral);
    }

    /**
     * 6. Buscar gastos por coincidencia en el concepto (subcadena).
     */
    public List<Gasto> buscarGastosPorConcepto(String subcadena) throws SQLException {
        if (subcadena == null || subcadena.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar al menos una palabra clave o término de búsqueda.");
        }
        return repository.buscarGastosPorConcepto(subcadena.trim());
    }
}
