package dev.iesfranciscodelosrios.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionData {
    // Variable que almacena la conexión a la base de datos
    private static java.sql.Connection conn = null;
    // URL de la base de datos local
    private final static String uri = "jdbc:mysql://localhost:3306/atm";
    // URL de la base de datos en la nube
    // private final static String uri = "jdbc:mysql://34.155.11.50:3306/rythm";
    // Usuario de la base de datos
    private final static String user = "root";
    // Contraseña de la base de datos
    private final static String password = "1234";

    /** Constructor privado para evitar instanciación externa */
    private ConnectionData() { }

    /**
     * Método estático para obtener la conexión a la base de datos.
     * @return La conexión a la base de datos.
     */
    public static java.sql.Connection getConnection() {
        // Si la conexión es nula, se crea una nueva
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(uri, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
                conn = null;
            }
        }
        return conn;
    }

    /**
     * Método estático para cerrar la conexión a la base de datos.
     * Actualmente está desactivado, pero puede ser útil para cerrar la conexión manualmente en un entorno real.
     */
    public static void close() {
        /*if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                conn = null;
            }
        }*/
    }
}
