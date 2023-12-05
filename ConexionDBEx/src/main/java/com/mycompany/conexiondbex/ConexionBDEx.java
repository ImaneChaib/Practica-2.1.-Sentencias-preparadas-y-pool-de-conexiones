/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.conexiondbex;

import java.sql.SQLException;



/**
 *
 * @author IMANE
 */
public class ConexionBDEx {

  

    public static void main(String[] args) throws SQLException {
        
        // Buscar un nombre en la base de datos
        String nombreBuscado = "game1";
        boolean existeNombre = Conexion.buscaNombre(nombreBuscado);
        System.out.println("¿Existe el nombre '" + nombreBuscado + "' en la tabla? " + existeNombre);

        // Lanzar una consulta a la base de datos
        Conexion.lanzaConsulta("SELECT * FROM videojuegos");

        // Insertar un nuevo registro en la base de datos
        Conexion.nuevoRegistro("NuevoJuego", "Aventura", "2023-01-01", "CompañiaX", "49.99");

        // Insertar un nuevo registro desde la entrada estándar
        Conexion.nuevoRegistroDesdeTeclado();

        // Eliminar un registro de la base de datos
        String nombreAEliminar = "JuegoEliminar";
        boolean eliminacionExitosa = Conexion.eliminarRegistro(nombreAEliminar);
        System.out.println("¿Se eliminó el juego con nombre '" + nombreAEliminar + "'? " + eliminacionExitosa);
    }
}
