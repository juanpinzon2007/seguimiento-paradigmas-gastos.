package analisis.controlgastosdeclarativo;

import analisis.controlgastosdeclarativo.database.DatabaseConnection;
import analisis.controlgastosdeclarativo.model.Gasto;
import analisis.controlgastosdeclarativo.model.ResumenGastos;
import analisis.controlgastosdeclarativo.service.GastoService;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ControlGastosDeclarativoApplicationTests {

    private static GastoService service;

    @BeforeAll
    static void setUp() {
        DatabaseConnection.inicializarEsquema();
        service = new GastoService();
    }

    @Test
    @Order(1)
    @DisplayName("Funcionalidad 1: Registrar gasto declarativamente")
    void testRegistrarGasto() throws SQLException {
        Gasto nuevo = service.registrarGasto("Prueba Unitaria Café", new BigDecimal("15000.50"), "Alimentación");
        assertNotNull(nuevo.id(), "El ID autogenerado por el motor relacional no debe ser nulo");
        assertEquals("Prueba Unitaria Café", nuevo.concepto());
        assertEquals(new BigDecimal("15000.50"), nuevo.monto());
        assertEquals("Alimentación", nuevo.categoria());
    }

    @Test
    @Order(2)
    @DisplayName("Funcionalidad 2: Mostrar listado de gastos declarativamente")
    void testListarGastos() throws SQLException {
        List<Gasto> gastos = service.listarGastos();
        assertNotNull(gastos);
        assertFalse(gastos.isEmpty(), "El listado de gastos no debe estar vacío");
    }

    @Test
    @Order(3)
    @DisplayName("Funcionalidad 3: Calcular gasto total acumulado mediante SUM() en SQL")
    void testCalcularTotalAcumulado() throws SQLException {
        ResumenGastos resumen = service.obtenerResumenTotal();
        assertNotNull(resumen);
        assertTrue(resumen.cantidadRegistros() > 0, "Debe haber registros contabilizados");
        assertTrue(resumen.totalAcumulado().compareTo(BigDecimal.ZERO) > 0, "El total acumulado debe ser mayor a 0");
    }

    @Test
    @Order(4)
    @DisplayName("Funcionalidad 4: Mostrar el gasto de mayor valor mediante MAX() en SQL")
    void testObtenerGastoMayorValor() throws SQLException {
        List<Gasto> mayores = service.obtenerGastosDeMayorValor();
        assertNotNull(mayores);
        assertFalse(mayores.isEmpty(), "Debe existir al menos un gasto de mayor valor");

        BigDecimal maxMonto = mayores.get(0).monto();
        // Verificar que ningún gasto registrado en la base de datos supera al retornado
        List<Gasto> todos = service.listarGastos();
        for (Gasto g : todos) {
            assertTrue(g.monto().compareTo(maxMonto) <= 0,
                    "Ningún gasto puede tener un monto superior al gasto de mayor valor detectado por SQL");
        }
    }

    @Test
    @Order(5)
    @DisplayName("Funcionalidad 5: Mostrar gastos mayores a un monto específico con WHERE monto > ?")
    void testFiltrarGastosMayoresAUmbral() throws SQLException {
        BigDecimal umbral = new BigDecimal("20000.00");
        List<Gasto> filtrados = service.listarGastosMayoresA(umbral);
        assertNotNull(filtrados);

        for (Gasto g : filtrados) {
            assertTrue(g.monto().compareTo(umbral) > 0,
                    "Cada gasto filtrado debe tener un monto estrictamente superior al umbral");
        }
    }

    @Test
    @Order(6)
    @DisplayName("Funcionalidad 6: Buscar gasto por concepto con LIKE en SQL")
    void testBuscarGastosPorConcepto() throws SQLException {
        List<Gasto> encontrados = service.buscarGastosPorConcepto("Café");
        assertNotNull(encontrados);
        assertFalse(encontrados.isEmpty(), "Debe encontrar el registro de prueba con 'Café'");

        for (Gasto g : encontrados) {
            assertTrue(g.concepto().toLowerCase().contains("café") || g.concepto().toLowerCase().contains("cafe"),
                    "El concepto encontrado debe contener la subcadena buscada");
        }
    }
}
