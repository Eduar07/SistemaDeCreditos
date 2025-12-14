package com.eduar;

import com.eduar.dao.*;
import com.eduar.modelo.*;
import com.eduar.util.ConexionDb;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  CREDIYA - PRUEBA JDBC (MYSQL)       ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        // Probar conexión
        if (!ConexionDb.probarConexion()) {
            System.err.println("No se pudo conectar a la base de datos");
            return;
        }
        System.out.println();
        
        //Inicializar DAOs
        ClienteDAOImpl clienteDAO = new ClienteDAOImpl();
        EmpleadoDAOImpl empleadoDAO = new EmpleadoDAOImpl();
        PrestamoDAOImpl prestamoDAO = new PrestamoDAOImpl();
        PagoDAOImpl pagoDAO = new PagoDAOImpl();
        
        
        // ═══════════════════════════════════════════════════════════
        //           PRUEBA 1: LISTAR CLIENTES
        // ═══════════════════════════════════════════════════════════
        
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  PRUEBA 1: Listar Clientes           ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        ArrayList<Cliente> clientes = clienteDAO.listar();
        System.out.println("Total de clientes: " + clientes.size());
        for (Cliente c : clientes) {
            System.out.println(c);
        }
        System.out.println();
        
        
        // ═══════════════════════════════════════════════════════════
        //           PRUEBA 2: LISTAR EMPLEADOS
        // ═══════════════════════════════════════════════════════════
        
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  PRUEBA 2: Listar Empleados          ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        ArrayList<Empleado> empleados = empleadoDAO.listar();
        System.out.println("Total de empleados: " + empleados.size());
        for (Empleado e : empleados) {
            System.out.println(e);
        }
        System.out.println();
        
        
        // ═══════════════════════════════════════════════════════════
        //           PRUEBA 3: LISTAR PRÉSTAMOS
        // ═══════════════════════════════════════════════════════════
        
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  PRUEBA 3: Listar Préstamos          ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        ArrayList<Prestamo> prestamos = prestamoDAO.listar();
        System.out.println("Total de préstamos: " + prestamos.size());
        for (Prestamo p : prestamos) {
            System.out.println(p);
            System.out.println();
        }
        
        
        // ═══════════════════════════════════════════════════════════
        //           PRUEBA 4: LISTAR PAGOS
        // ═══════════════════════════════════════════════════════════
        
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  PRUEBA 4: Listar Pagos              ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        ArrayList<Pago> pagos = pagoDAO.listar();
        System.out.println("Total de pagos: " + pagos.size());
        for (Pago p : pagos) {
            System.out.println(p);
        }
        System.out.println();
        
        
        // ═══════════════════════════════════════════════════════════
        //           PRUEBA 5: CREAR NUEVO CLIENTE
        // ═══════════════════════════════════════════════════════════
        
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  PRUEBA 5: Crear Nuevo Cliente       ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        Cliente nuevoCliente = new Cliente(
            "Sofía González",
    "5477653456",  
    "sofia.gonzalez@email.com",
    "3002223344"
        );
        
        clienteDAO.guardar(nuevoCliente);
        System.out.println("Cliente creado: " + nuevoCliente);
        System.out.println();
         // ═══════════════════════════════════════════════════════════
    //           RESUMEN
    // ═══════════════════════════════════════════════════════════
    
    System.out.println("╔═══════════════════════════════════════╗");
    System.out.println("║  ✓✓✓ PRUEBAS COMPLETADAS ✓✓✓        ║");
    System.out.println("╠═══════════════════════════════════════╣");
    System.out.println("║                                       ║");
    System.out.println("║  ✓ Conexión a MySQL                  ║");
    System.out.println("║  ✓ ClienteDAOImpl                    ║");
    System.out.println("║  ✓ EmpleadoDAOImpl                   ║");
    System.out.println("║  ✓ PrestamoDAOImpl                   ║");
    System.out.println("║  ✓ PagoDAOImpl                       ║");
    System.out.println("║                                       ║");
    System.out.println("║  Total registros en BD:              ║");
    System.out.println("║    Clientes: " + String.format("%-3d", clienteDAO.contar()) + "                      ║");
    System.out.println("║    Empleados: " + String.format("%-3d", empleadoDAO.contar()) + "                     ║");
    System.out.println("║    Préstamos: " + String.format("%-3d", prestamoDAO.contar()) + "                     ║");
    System.out.println("║    Pagos: " + String.format("%-3d", pagoDAO.contar()) + "                         ║");
    System.out.println("║                                       ║");
    System.out.println("╚═══════════════════════════════════════╝");
}
}