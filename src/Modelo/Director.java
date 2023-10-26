package Modelo;

import java.sql.*;

public class Director extends Login {
    public static void main(String[] args) {
    Connection conn = null;
    try {
        Class.forName(JDBC_DRIVER);
        System.out.println("Conectando a la base de datos...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);

        // Ejemplo de inicio de sesión
        String tipoUsuario = iniciarSesion(conn, "nombreDirector", "contraseñaDirector");

        if (tipoUsuario != null && tipoUsuario.equals("director")) {
            System.out.println("Inicio de sesión exitoso. Bienvenido, Director.");
            // Funcionalidades del director
            ingresarNuevoUsuario(conn);
        } else {
            System.out.println("Inicio de sesión fallido para el Director. Usuario o contraseña incorrectos.");
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

// Método para iniciar sesión
public static String iniciarSesion(Connection conn, String usuario, String contraseña) {
    String tipoUsuario = null;
    try {
        String sql = "SELECT TipoUsuario FROM Usuarios WHERE Usuario=? AND Contraseña=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, usuario);
        stmt.setString(2, contraseña);
        ResultSet rs = stmt.executeQuery();
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

// Funcionalidades del director: Ingresar nuevo usuario
public static void ingresarNuevoUsuario(Connection conn) {
    try {
        // Aquí debes implementar la lógica para ingresar un nuevo usuario
        String nuevoUsuario = "nuevoUsuario";
        String nuevaContraseña = "nuevaContraseña";
        String tipoUsuario = "nuevoTipoUsuario";

        String sql = "INSERT INTO Usuarios (Usuario, Contraseña, TipoUsuario) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, nuevoUsuario);
        stmt.setString(2, nuevaContraseña);
        stmt.setString(3, tipoUsuario);

        int filasAfectadas = stmt.executeUpdate();
        if (filasAfectadas > 0) {
            System.out.println("Nuevo usuario ingresado exitosamente.");
        } else {
            System.out.println("No se pudo ingresar el nuevo usuario.");
        }

        stmt.close();
    } catch (SQLException se) {
        se.printStackTrace();
    }
}

}
