package Modelo;
import java.sql.*;

public class Secretario extends Login{
    public static void main(String[] args) {
    Connection conn = null;
    try {
        Class.forName(JDBC_DRIVER);
        System.out.println("Conectando a la base de datos...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);

        // Ejemplo de inicio de sesión para secretario
        String tipoUsuario = iniciarSesion(conn, "nombreSecretario", "contraseñaSecretario");

        if (tipoUsuario != null && tipoUsuario.equals("secretario")) {
            System.out.println("Inicio de sesión exitoso. Bienvenido, Secretario.");
            // Funcionalidades del secretario
            matricularAlumno(conn, "Nuevo Alumno", "Grado 1", "2023-10-18"); // Ejemplo de matriculación
            retirarAlumno(conn, 1); // Ejemplo de retirar alumno
        } else {
            System.out.println("Inicio de sesión fallido para el Secretario. Usuario o contraseña incorrectos.");
        }

        conn.close();
    } catch (SQLException se) {
        se.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (conn != null) conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
    System.out.println("¡Adiós!");
}

// Funcionalidades del secretario: Matricular alumno
public static void matricularAlumno(Connection conn, String nombreAlumno, String grado, String fechaMatricula) {
    try {
        String sql = "INSERT INTO Matriculas (NombreAlumno, Grado, FechaMatricula) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, nombreAlumno);
        stmt.setString(2, grado);
        stmt.setDate(3, Date.valueOf(fechaMatricula));

        int filasAfectadas = stmt.executeUpdate();
        if (filasAfectadas > 0) {
            System.out.println("Alumno matriculado exitosamente.");
        } else {
            System.out.println("No se pudo matricular al alumno.");
        }

        stmt.close();
    } catch (SQLException se) {
        se.printStackTrace();
    }
}

// Funcionalidades del secretario: Retirar alumno
public static void retirarAlumno(Connection conn, int alumnoID) {
    try {
        String sql = "DELETE FROM Matriculas WHERE AlumnoID=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, alumnoID);

        int filasAfectadas = stmt.executeUpdate();
        if (filasAfectadas > 0) {
            System.out.println("Alumno retirado exitosamente.");
        } else {
            System.out.println("No se pudo retirar al alumno.");
        }

        stmt.close();
    } catch (SQLException se) {
        se.printStackTrace();
    }
}

}
