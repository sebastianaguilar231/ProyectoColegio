package Modelo;

import java.sql.*;

public class Login {
    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=ProyectoColegio";
    static final String USER = "tu_usuario";
    static final String PASS = "tu_contraseña";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // Paso 1: Registrar el controlador JDBC
            Class.forName(JDBC_DRIVER);

            // Paso 2: Establecer la conexión
            System.out.println("Conectando a la base de datos...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Paso 3: Crear y ejecutar consultas SQL
            // Aquí puedes implementar el código para el inicio de sesión y las funcionalidades del sistema

            // Paso 4: Cerrar la conexión
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("¡Adiós!");
    }
    
    
public static void Login(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Conectando a la base de datos...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Implementa aquí la lógica de inicio de sesión
            // Por ejemplo, un método que reciba un usuario y contraseña
            // y devuelva el tipo de usuario (director, profesor, secretario, alumno)

            // Ejemplo de uso
            String tipoUsuario = iniciarSesion(conn, "nombreUsuario", "contraseña");

            // Según el tipo de usuario, ejecutar las funcionalidades correspondientes
            switch (tipoUsuario) {
                case "director":
                    // Implementar funcionalidades del director
                    break;
                case "profesor":
                    // Implementar funcionalidades del profesor
                    break;
                case "secretario":
                    // Implementar funcionalidades del secretario
                    break;
                case "alumno":
                    // Implementar funcionalidades del alumno
                    break;
                default:
                    System.out.println("Tipo de usuario no válido");
            }

            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // ...
        }
        System.out.println("¡Adiós!");
    }

    // Método para iniciar sesión
    public static String iniciarSesion(Connection conn, String usuario, String contraseña) {
        String tipoUsuario = null;
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT TipoUsuario FROM Usuarios WHERE Usuario='" + usuario + "' AND Contraseña='" + contraseña + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                tipoUsuario = rs.getString("TipoUsuario");
            }
            rs.close();
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return tipoUsuario;
    }
}
    


