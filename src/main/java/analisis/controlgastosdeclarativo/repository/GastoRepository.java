package analisis.controlgastosdeclarativo.repository;

import analisis.controlgastosdeclarativo.database.DatabaseConnection;
import analisis.controlgastosdeclarativo.model.Gasto;
import analisis.controlgastosdeclarativo.model.ResumenGastos;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio de acceso a datos para Gastos implementado bajo el PARADIGMA DECLARATIVO.
 *
 * Principio del Paradigma Declarativo:
 * Se describe explícitamente QUÉ información se desea consultar o persistir, delegando
 * al motor de la base de datos relacional (MySQL) el CÓMO llevar a cabo la búsqueda,
 * iteración, filtrado, ordenamiento y agregación física de los datos.
 *
 * No se implementan bucles for/while en Java para calcular acumulados ni para filtrar
 * registros, respetando estrictamente la definición formal del paradigma.
 */
public class GastoRepository {

    /**
     * Mapea una fila actual del ResultSet a un objeto Gasto (Mapeo declarativo de tupla a entidad).
     */
    private Gasto mapearFilaAGasto(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        String concepto = rs.getString("concepto");
        BigDecimal monto = rs.getBigDecimal("monto");
        String categoria = rs.getString("categoria");
        Timestamp ts = rs.getTimestamp("fecha");
        LocalDateTime fecha = (ts != null) ? ts.toLocalDateTime() : null;

        return new Gasto(id, concepto, monto, categoria, fecha);
    }

    /**
     * FUNCIONALIDAD 1: Registrar gasto.
     * Consulta declarativa: INSERT INTO gastos (concepto, monto, categoria) VALUES (?, ?, ?)
     * Se declara la adición de una tupla al conjunto de datos sin programar algoritmos
     * de asignación de memoria o almacenamiento en disco.
     */
    public Gasto registrarGasto(Gasto gasto) throws SQLException {
        String sql = "INSERT INTO gastos (concepto, monto, categoria) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, gasto.concepto());
            ps.setBigDecimal(2, gasto.monto());
            ps.setString(3, gasto.categoria());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    return new Gasto(idGenerado, gasto.concepto(), gasto.monto(), gasto.categoria(), LocalDateTime.now());
                }
            }
        }
        return gasto;
    }

    /**
     * FUNCIONALIDAD 2: Mostrar listado de gastos.
     * Consulta declarativa: SELECT ... FROM gastos ORDER BY id ASC
     * Se expresa qué campos se desean proyectar y el orden de salida. El motor
     * determina la estrategia de escaneo de tabla.
     */
    public List<Gasto> obtenerTodosLosGastos() throws SQLException {
        String sql = "SELECT id, concepto, monto, categoria, fecha FROM gastos ORDER BY id ASC";
        List<Gasto> resultado = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                resultado.add(mapearFilaAGasto(rs));
            }
        }
        return resultado;
    }

    /**
     * FUNCIONALIDAD 3: Calcular gasto total acumulado.
     * Consulta declarativa: SELECT COALESCE(SUM(monto), 0) AS total, COUNT(*) AS cantidad FROM gastos
     *
     * CARACTERÍSTICA DECLARATIVA CLAVE:
     * La sumatoria y el conteo son resueltos exclusivamente por el motor de base de datos
     * mediante la función de agregación matemática SUM(). En Java no existe ningún acumulador
     * (total = total + x) ni bucle de cálculo.
     */
    public ResumenGastos calcularTotalAcumulado() throws SQLException {
        String sql = "SELECT COALESCE(SUM(monto), 0) AS total_acumulado, COUNT(*) AS total_registros FROM gastos";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                BigDecimal total = rs.getBigDecimal("total_acumulado");
                long cantidad = rs.getLong("total_registros");
                return new ResumenGastos(total, cantidad);
            }
        }
        return new ResumenGastos(BigDecimal.ZERO, 0L);
    }

    /**
     * FUNCIONALIDAD 4: Mostrar el gasto de mayor valor.
     * Consulta declarativa:
     *   SELECT id, concepto, monto, categoria, fecha
     *   FROM gastos
     *   WHERE monto = (SELECT MAX(monto) FROM gastos)
     *
     * CARACTERÍSTICA DECLARATIVA CLAVE:
     * Se formula una consulta con subconsulta relacional basada en la función MAX().
     * El SGBD evalúa el predicado sin necesidad de que el programador programe
     * comparadores condicionales ("if (actual > max)") ni ordene arreglos en memoria.
     * Permite además recuperar empates si varios registros comparten el monto más alto.
     */
    public List<Gasto> obtenerGastosDeMayorValor() throws SQLException {
        String sql = """
            SELECT id, concepto, monto, categoria, fecha 
            FROM gastos 
            WHERE monto = (SELECT MAX(monto) FROM gastos)
            ORDER BY id ASC
        """;
        List<Gasto> resultado = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                resultado.add(mapearFilaAGasto(rs));
            }
        }
        return resultado;
    }

    /**
     * FUNCIONALIDAD 5: Mostrar gastos mayores a un monto específico.
     * Consulta declarativa:
     *   SELECT id, concepto, monto, categoria, fecha
     *   FROM gastos
     *   WHERE monto > ?
     *   ORDER BY monto ASC
     *
     * CARACTERÍSTICA DECLARATIVA CLAVE:
     * La restricción relacional (filtrado) se delega completamente al motor SQL mediante
     * la cláusula WHERE. No se traen todos los registros a la memoria RAM de Java para luego
     * descartarlos mediante sentencias 'if'.
     */
    public List<Gasto> obtenerGastosMayoresA(BigDecimal umbral) throws SQLException {
        String sql = """
            SELECT id, concepto, monto, categoria, fecha 
            FROM gastos 
            WHERE monto > ? 
            ORDER BY monto ASC
        """;
        List<Gasto> resultado = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBigDecimal(1, umbral);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultado.add(mapearFilaAGasto(rs));
                }
            }
        }
        return resultado;
    }

    /**
     * FUNCIONALIDAD 6: Buscar gasto por concepto (coincidencia de subcadena).
     * Consulta declarativa:
     *   SELECT id, concepto, monto, categoria, fecha
     *   FROM gastos
     *   WHERE LOWER(concepto) LIKE LOWER(?)
     *   ORDER BY id ASC
     *
     * CARACTERÍSTICA DECLARATIVA CLAVE:
     * El patrón de búsqueda por subcadena se resuelve declarativamente utilizando el operador
     * relacional LIKE y comodines '%', abstrayendo algoritmos de matching de texto (como KMP
     * o Boyer-Moore) al optimizador de la base de datos.
     */
    public List<Gasto> buscarGastosPorConcepto(String subcadena) throws SQLException {
        String sql = """
            SELECT id, concepto, monto, categoria, fecha 
            FROM gastos 
            WHERE LOWER(concepto) LIKE LOWER(?) 
            ORDER BY id ASC
        """;
        List<Gasto> resultado = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Comodines para coincidencia de subcadena en cualquier posición
            String parametro = "%" + subcadena.trim() + "%";
            ps.setString(1, parametro);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultado.add(mapearFilaAGasto(rs));
                }
            }
        }
        return resultado;
    }
}
