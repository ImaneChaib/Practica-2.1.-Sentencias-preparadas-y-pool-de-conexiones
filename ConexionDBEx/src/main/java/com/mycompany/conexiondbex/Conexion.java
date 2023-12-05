package com.mycompany.conexiondbex;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Conexion {

    // URL, usuario y contraseña de la base de datos
    static final String DB_URL = "jdbc:mysql://localhost:3306/jcvd";
    static final String USER = "dam2";
    static final String PASS = "1234";

    // Define UCP DataSource
    private static PoolDataSource pds;

    // Asegura que pds se inicialice antes de usarlo
    private static void initializeDataSource() throws SQLException {
        if (pds == null) {
            // Inicializa el PoolDataSource
            pds = PoolDataSourceFactory.getPoolDataSource();
            
            // Configuración del DataSource
            pds.setConnectionFactoryClassName("your.connection.factory.class.name");
            pds.setURL(DB_URL);
            pds.setUser(USER);
            pds.setPassword(PASS);

            // Ajuste esta configuración según sus requisitos
            pds.setInitialPoolSize(5);
            pds.setMinPoolSize(5);
            pds.setMaxPoolSize(20);
        }
    }

    // Método para buscar un nombre en la base de datos
    public static boolean buscaNombre(String NOMBRE) throws SQLException {
        initializeDataSource();
        String consulta = "SELECT * FROM videojuegos WHERE NOMBRE = ?";
        try (Connection conn = pds.getConnection();
             PreparedStatement sentencia = conn.prepareStatement(consulta)) {
            sentencia.setString(1, NOMBRE);
            try (ResultSet rs = sentencia.executeQuery()) {
                return rs.next(); // Devuelve true si se encuentra un registro coincidente
            }
        }
    }

    // Método para lanzar una consulta a la base de datos
    public static void lanzaConsulta(String consulta) throws SQLException {
        initializeDataSource();
        try (Connection conn = pds.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(consulta)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nombre: " + rs.getString("NOMBRE"));
                System.out.println("Genero" + rs.getString("GENERO"));
                System.out.println("FechaLanzamiento" + rs.getDate("FECHALANZAMIENTO"));
                System.out.println("Compañia" + rs.getString("COMPAÑIA"));
                System.out.println("Precio" + rs.getFloat("PRECIO"));
                System.out.println("---------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para insertar un nuevo registro en la base de datos
    public static void nuevoRegistro(String NOMBRE, String GENERO, String fechaLanzamiento, String COMPAÑIA, String PRECIO) throws SQLException {
        initializeDataSource();
        try (Connection conn = pds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO videojuegos (NOMBRE, GENERO, FECHALANZAMIENTO, COMPAÑIA, PRECIO) VALUES (?, ?, ?, ?, ?)")) {
            pstmt.setString(1, NOMBRE);
            pstmt.setString(2, GENERO);
            pstmt.setDate(3, Date.valueOf(fechaLanzamiento));
            pstmt.setString(4, COMPAÑIA);
            pstmt.setString(5, PRECIO);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para insertar un nuevo registro desde la entrada estándar
    public static void nuevoRegistroDesdeTeclado() throws SQLException {
        initializeDataSource();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Ingrese el nombre del nuevo juego:");
            String NOMBRE = scanner.nextLine();
            System.out.println("Ingrese el genero del nuevo juego:");
            String GENERO = scanner.nextLine();
            System.out.println("Ingrese la fecha de lanzamiento del nuevo juego (Formato: YYYY-MM-DD):");
            String FECHALANZAMIENTO = scanner.nextLine();
            System.out.println("Ingrese la compañia del nuevo juego:");
            String COMPAÑIA = scanner.nextLine();
            System.out.println("Ingrese el precio del nuevo juego:");
            String PRECIO = scanner.nextLine();

            // Llama al método para insertar un nuevo registro
            nuevoRegistro(NOMBRE, GENERO, FECHALANZAMIENTO, COMPAÑIA, PRECIO);
        }
    }

    // Método para eliminar un registro de la base de datos
    public static boolean eliminarRegistro(String NOMBRE) throws SQLException {
        initializeDataSource();
        try (Connection conn = pds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM videojuegos WHERE NOMBRE= ?")) {
            pstmt.setString(1, NOMBRE);
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}


