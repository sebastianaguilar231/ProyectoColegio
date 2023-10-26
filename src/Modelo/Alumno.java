package Modelo;
import java.sql.*;

public class Alumno extends Login{
    public static void main(String[] args) {
    Connection conn = null;
    try {
        Class.forName(JDBC_DRIVER);
        System.out.println("Conectando a la base de datos...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);

        // Ejemplo de inicio de sesión para alumno
        String tipoUsuario = iniciarSesion(conn, "nombreAlumno", "contraseñaAlumno");

        if (tipoUsuario != null && tipoUsuario.equals("alumno")) {
            System.out.println("Inicio de sesión exitoso. Bienvenido, Alumno.");
            // Funcionalidades del alumno
            visualizarInformacion(conn, 1); // Ejemplo de visualización de información
        } else {
            System.out.println("Inicio de sesión fallido para el Alumno. Usuario o contraseña incorrectos.");
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

// Funcionalidades del alumno: Visualizar información
public static void visualizarInformacion(Connection conn, int alumnoID) {
    try {
        // Obtener información del alumno desde la base de datos
        String sql = "SELECT * FROM Matriculas WHERE AlumnoID=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, alumnoID);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String nombreAlumno = rs.getString("NombreAlumno");
            String grado = rs.getString("Grado");
            Date fechaMatricula = rs.getDate("FechaMatricula");

            System.out.println("Nombre del Alumno: " + nombreAlumno);
            System.out.println("Grado: " + grado);
            System.out.println("Fecha de Matrícula: " + fechaMatricula);
        } else {
            System.out.println("No se encontró información para el alumno.");
        }

        rs.close();
        stmt.close();
    } catch (SQLException se) {
        se.printStackTrace();
    }
}
public static void visualizarNotas(Connection conn, int alumnoID) {
    try {
        String sql = "SELECT Materia, Nota FROM Notas WHERE AlumnoID=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, alumnoID);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String materia = rs.getString("Materia");
            double nota = rs.getDouble("Nota");

            System.out.println("Materia: " + materia);
            System.out.println("Nota: " + nota);
            System.out.println(nota >= 10.5 ? "Estado: Aprobado" : "Estado: Desaprobado");
            System.out.println("-------------------------");
        }

        rs.close();
        stmt.close();
    } catch (SQLException se) {
        se.printStackTrace();
    }
}

// Funcionalidades del alumno: Visualizar asistencias
public static void visualizarAsistencias(Connection conn, int alumnoID) {
    try {
        String sql = "SELECT Fecha, Asistencia, Justificada FROM Asistencias WHERE AlumnoID=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, alumnoID);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String fecha = rs.getString("Fecha");
            boolean asistencia = rs.getBoolean("Asistencia");
            boolean justificada = rs.getBoolean("Justificada");

            System.out.println("Fecha: " + fecha);
            System.out.println("Asistencia: " + (asistencia ? "Presente" : "Ausente"));
            if (!asistencia) {
                System.out.println(justificada ? "Falta justificada" : "Falta no justificada");
            }
            System.out.println("-------------------------");
        }

        rs.close();
        stmt.close();
    } catch (SQLException se) {
        se.printStackTrace();
    }
}

}
