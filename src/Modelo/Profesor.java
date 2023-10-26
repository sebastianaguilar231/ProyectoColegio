package Modelo;
import java.sql.*;

public class Profesor extends Login {
    public static void main(String[] args) {
    Connection conn = null;
    try {
        Class.forName(JDBC_DRIVER);
        System.out.println("Conectando a la base de datos...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);

        // Ejemplo de inicio de sesión para profesor
        String tipoUsuario = iniciarSesion(conn, "nombreProfesor", "contraseñaProfesor");

        if (tipoUsuario != null && tipoUsuario.equals("profesor")) {
            System.out.println("Inicio de sesión exitoso. Bienvenido, Profesor.");
            // Funcionalidades del profesor
            registrarNota(conn, 1, "Matemáticas", 8.5); // Ejemplo de registro de nota
            registrarAsistencia(conn, 1, "2023-10-18", true, false); // Ejemplo de registro de asistencia
            modificarAsistencia(conn, 1, "2023-10-18", true, true); // Ejemplo de modificación de asistencia
        } else {
            System.out.println("Inicio de sesión fallido para el Profesor. Usuario o contraseña incorrectos.");
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

// Funcionalidades del profesor: Registrar nota
public static void registrarNota(Connection conn, int alumnoID, String materia, double nota) {
    try {
        String sql = "INSERT INTO Notas (AlumnoID, Materia, Nota) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, alumnoID);
        stmt.setString(2, materia);
        stmt.setDouble(3, nota);

        int filasAfectadas = stmt.executeUpdate();
        if (filasAfectadas > 0) {
            System.out.println("Nota registrada exitosamente.");
            if (nota >= 10.5) {
                System.out.println("Estado: Aprobado");
            } else {
                System.out.println("Estado: Desaprobado");
            }
        } else {
            System.out.println("No se pudo registrar la nota.");
        }

        stmt.close();
    } catch (SQLException se) {
        se.printStackTrace();
    }
}

// Funcionalidades del profesor: Registrar asistencia
public static void registrarAsistencia(Connection conn, int alumnoID, String fecha, boolean presente, boolean justificado) {
    try {
        String sql = "INSERT INTO Asistencias (AlumnoID, Fecha, Asistencia) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, alumnoID);
        stmt.setDate(2, Date.valueOf(fecha));
        stmt.setBoolean(3, presente);

        int filasAfectadas = stmt.executeUpdate();
        if (filasAfectadas > 0) {
            System.out.println("Asistencia registrada exitosamente.");
            if (!presente) {
                System.out.println("Estado: Ausente");
                if (justificado) {
                    System.out.println("Falta justificada");
                } else {
                    System.out.println("Falta no justificada");
                }
            }
        } else {
            System.out.println("No se pudo registrar la asistencia.");
        }

        stmt.close();
    } catch (SQLException se) {
        se.printStackTrace();
    }
}

// Funcionalidades del profesor: Modificar asistencia
public static void modificarAsistencia(Connection conn, int alumnoID, String fecha, boolean nuevaAsistencia, boolean nuevaJustificacion) {
    try {
        String sql = "UPDATE Asistencias SET Asistencia=?, Justificada=? WHERE AlumnoID=? AND Fecha=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setBoolean(1, nuevaAsistencia);
        stmt.setBoolean(2, nuevaJustificacion);
        stmt.setInt(3, alumnoID);
        stmt.setDate(4, Date.valueOf(fecha));

        int filasAfectadas = stmt.executeUpdate();
        if (filasAfectadas > 0) {
            System.out.println("Asistencia modificada exitosamente.");
            if (!nuevaAsistencia) {
                System.out.println("Estado: Ausente");
                if (nuevaJustificacion) {
                    System.out.println("Falta justificada");
                } else {
                    System.out.println("Falta no justificada");
                }
            }
        } else {
            System.out.println("No se pudo modificar la asistencia.");
        }

        stmt.close();
    } catch (SQLException se) {
        se.printStackTrace();
    }
}

}
