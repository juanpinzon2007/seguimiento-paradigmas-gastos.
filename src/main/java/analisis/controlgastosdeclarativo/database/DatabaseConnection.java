package analisis.controlgastosdeclarativo.database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Gestor de conexión JDBC hacia el motor de base de datos relacional MySQL.
 * Administra las credenciales y asegura la inicialización del esquema declarativo.
 */
public class DatabaseConnection {

    private static String url;
    private static String user;
    private static String password;

    static {
        loadConfiguration();
    }

    private static void loadConfiguration() {
        Properties props = new Properties();
        try (InputStream in = DatabaseConnection.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (Exception e) {
            System.err.println("Aviso: No se pudo cargar application.properties, usando valores por defecto.");
        }

        url = props.getProperty("db.url", "jdbc:mysql://localhost:3306/control_gastos?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Bogota&characterEncoding=UTF-8");
        user = props.getProperty("db.user", "root");
        password = props.getProperty("db.password", "");
    }

    /**
     * Obtiene una nueva conexión a la base de datos control_gastos.
     *
     * @return Conexión SQL activa
     * @throws SQLException Si ocurre un error al conectar
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Inicializa declarativamente la base de datos y la tabla de gastos si no existen.
     */
    public static void inicializarEsquema() {
        // Conexión base sin especificar BD para asegurar que exista 'control_gastos'
        String baseUrl = "jdbc:mysql://localhost:3306/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Bogota";
        
        try (Connection conn = DriverManager.getConnection(baseUrl, user, password);
             Statement stmt = conn.createStatement()) {

            // Declaración de creación de BD
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS control_gastos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;");

        } catch (SQLException e) {
            // Si no tiene permisos a nivel de servidor o ya existe, intentamos continuar
        }

        // Creación declarativa de la tabla en la base de datos correspondiente
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            String createTableSql = """
                CREATE TABLE IF NOT EXISTS gastos (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    concepto VARCHAR(150) NOT NULL,
                    monto DECIMAL(12, 2) NOT NULL,
                    categoria VARCHAR(50) NOT NULL,
                    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
            """;
            stmt.executeUpdate(createTableSql);

        } catch (SQLException e) {
            System.err.println("Error al verificar/crear la tabla 'gastos': " + e.getMessage());
        }
    }
}
