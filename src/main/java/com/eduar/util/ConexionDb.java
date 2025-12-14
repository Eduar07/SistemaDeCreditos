package com.eduar.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de utilidad para manejar la conexión a MySQL con Docker
 * 
 * @author Eduar Humberto Guerrero Vergel
 * @version 1.0
 */
public class ConexionDb {
    
    // ═══════════════════════════════════════════════════════════
    //                    CONFIGURACIÓN PARA DOCKER
    // ═══════════════════════════════════════════════════════════
    
    private static final String URL = "jdbc:mysql://localhost:3306/crediya_db";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "crediya123";
    
    private static final String URL_COMPLETA = URL + 
       "?useSSL=false" +
    "&serverTimezone=UTC" +
    "&allowPublicKeyRetrieval=true" +
    "&characterEncoding=UTF-8" +
    "&useUnicode=true";
    
    
    // ═══════════════════════════════════════════════════════════
    //                    OBTENER CONEXIÓN
    // ═══════════════════════════════════════════════════════════
    
    public static Connection getConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL_COMPLETA, USUARIO, CONTRASENA);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver de MySQL no encontrado", e);
        }
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    CERRAR CONEXIÓN
    // ═══════════════════════════════════════════════════════════
    
    public static void cerrarConexion(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("✓ Conexión cerrada");
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    PROBAR CONEXIÓN
    // ═══════════════════════════════════════════════════════════
    
    public static boolean probarConexion() {
        try {
            Connection conn = getConexion();
            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("║  ✓ CONEXIÓN A MYSQL (DOCKER) EXITOSA ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.println("Base de datos: crediya_db");
            System.out.println("Usuario: root");
            System.out.println("Puerto: 3306");
            System.out.println("Contenedor: crediya_mysql");
            cerrarConexion(conn);
            return true;
        } catch (SQLException e) {
            System.err.println("╔═══════════════════════════════════════╗");
            System.err.println("║  ✗ ERROR DE CONEXIÓN                 ║");
            System.err.println("╚═══════════════════════════════════════╝");
            System.err.println("Error: " + e.getMessage());
            System.err.println("\nVerifica:");
            System.err.println("  1. Docker Desktop está corriendo");
            System.err.println("  2. Ejecutaste: docker compose up -d");
            System.err.println("  3. El contenedor está activo: docker ps");
            return false;
        }
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    MAIN DE PRUEBA
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Método main para probar la conexión
     */
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  PRUEBA DE CONEXIÓN A DOCKER         ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        probarConexion();

    

    }
    

}
