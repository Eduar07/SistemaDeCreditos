package com.eduar;

import com.eduar.modelo.*;
import com.eduar.dao.*;
import com.eduar.util.ArchivoUtil;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Clase principal del sistema CrediYa
 * 
 * @author Eduar Humberto Guerrero Vergel
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║     CREDIYA S.A.S. - SISTEMA         ║");
        System.out.println("║   Gestión de Préstamos v1.0          ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        
        // ═══════════════════════════════════════════════════════════
        //           INICIALIZAR DAOs
        // ═══════════════════════════════════════════════════════════
        
        ClienteDAO clienteDAO = new ClienteDAO();
        EmpleadoDAO empleadoDAO = new EmpleadoDAO();
        PrestamoDAO prestamoDAO = new PrestamoDAO();
        PagoDAO pagoDAO = new PagoDAO();
        
        
        // ═══════════════════════════════════════════════════════════
        //           PRUEBA 1: CREATE - CREAR CLIENTES
        // ═══════════════════════════════════════════════════════════
        
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  PRUEBA 1: CREATE - Clientes         ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        Cliente cli1 = new Cliente(
            "Juan Pérez",
            "1111111111",
            "juan.perez@email.com",
            "3001234567"
        );
        
        Cliente cli2 = new Cliente(
            "María Rodríguez",
            "2222222222",
            "maria.rodriguez@email.com",
            "3007654321"
        );
        
        if (clienteDAO.crear(cli1)) {
            System.out.println("✓ Cliente 1 creado con ID: " + cli1.getId());
        }
        
        if (clienteDAO.crear(cli2)) {
            System.out.println("✓ Cliente 2 creado con ID: " + cli2.getId());
        }
        
        System.out.println("Total de clientes: " + clienteDAO.contar());
        System.out.println();
        
        
        // ═══════════════════════════════════════════════════════════
        //           PRUEBA 2: CREATE - CREAR EMPLEADOS
        // ═══════════════════════════════════════════════════════════
        
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  PRUEBA 2: CREATE - Empleados        ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        Empleado emp1 = new Empleado(
            "Ana García",
            "1234567890",
            "ana.garcia@crediya.com",
            "Gerente",
            4500000
        );
        
        Empleado emp2 = new Empleado(
            "Carlos López",
            "9876543210",
            "carlos.lopez@crediya.com",
            "Asesor",
            2800000
        );
        
        if (empleadoDAO.crear(emp1)) {
            System.out.println("✓ Empleado 1 creado con ID: " + emp1.getId());
        }
        
        if (empleadoDAO.crear(emp2)) {
            System.out.println("✓ Empleado 2 creado con ID: " + emp2.getId());
        }
        
        System.out.println("Total de empleados: " + empleadoDAO.contar());
        System.out.println();
        
        
        // ═══════════════════════════════════════════════════════════
        //           PRUEBA 3: READ - LEER TODOS
        // ═══════════════════════════════════════════════════════════
        
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  PRUEBA 3: READ - Leer todos         ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        System.out.println("--- Clientes ---");
        ArrayList<Cliente> clientes = clienteDAO.obtenerTodos();
        for (Cliente c : clientes) {
            System.out.println(c);
        }
        System.out.println();
        
        System.out.println("--- Empleados ---");
        ArrayList<Empleado> empleados = empleadoDAO.obtenerTodos();
        for (Empleado e : empleados) {
            System.out.println(e);
        }
        System.out.println();
        
        
        // ═══════════════════════════════════════════════════════════
        //           PRUEBA 4: READ - BUSCAR POR ID
        // ═══════════════════════════════════════════════════════════
        
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  PRUEBA 4: READ - Buscar por ID      ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        if (!clientes.isEmpty()) {
            int idBuscar = clientes.get(0).getId();
            Cliente encontrado = clienteDAO.obtenerPorId(idBuscar);
            
            if (encontrado != null) {
                System.out.println("✓ Cliente encontrado:");
                System.out.println(encontrado);
            }
        }
        System.out.println();
        
        
        // ═══════════════════════════════════════════════════════════
        //           PRUEBA 5: UPDATE - ACTUALIZAR
        // ═══════════════════════════════════════════════════════════
        
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  PRUEBA 5: UPDATE - Actualizar       ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        if (!clientes.isEmpty()) {
            Cliente clienteActualizar = clientes.get(0);
            
            System.out.println("ANTES:");
            System.out.println(clienteActualizar);
            System.out.println();
            
            // Modificar datos
            clienteActualizar.setTelefono("3009999999");
            
            // Actualizar en archivo
            if (clienteDAO.actualizar(clienteActualizar)) {
                System.out.println("✓ Cliente actualizado");
                
                // Leer de nuevo para verificar
                Cliente verificar = clienteDAO.obtenerPorId(clienteActualizar.getId());
                System.out.println("DESPUÉS:");
                System.out.println(verificar);
            }
        }
        System.out.println();
        
        
        // ═══════════════════════════════════════════════════════════
        //           PRUEBA 6: CREATE - PRÉSTAMO
        // ═══════════════════════════════════════════════════════════
        
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  PRUEBA 6: CREATE - Préstamo         ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        if (!clientes.isEmpty() && !empleados.isEmpty()) {
            Cliente cliente = clientes.get(0);
            Empleado empleado = empleados.get(0);
            
            Prestamo prestamo = new Prestamo(
                cliente,
                empleado,
                5000000,        // Monto
                5.5,            // Interés
                12,             // Cuotas
                LocalDate.now() // Fecha inicio
            );
            
            if (prestamoDAO.crear(prestamo)) {
                System.out.println("✓ Préstamo creado con ID: " + prestamo.getId());
                System.out.println(prestamo);
            }
        }
        System.out.println();
        
        
        // ═══════════════════════════════════════════════════════════
        //           PRUEBA 7: CREATE - PAGO
        // ═══════════════════════════════════════════════════════════
        
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  PRUEBA 7: CREATE - Pago             ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        ArrayList<Prestamo> prestamos = prestamoDAO.obtenerTodos();
        
        if (!prestamos.isEmpty()) {
            Prestamo prestamo = prestamos.get(0);
            
            Pago pago1 = new Pago(
                prestamo,
                LocalDate.now(),
                439583.33,
                "Pago cuota 1"
            );
            
            Pago pago2 = new Pago(
                prestamo,
                LocalDate.now(),
                439583.33,
                "Pago cuota 2"
            );
            
            if (pagoDAO.crear(pago1)) {
                System.out.println("✓ Pago 1 creado: " + pago1);
            }
            
            if (pagoDAO.crear(pago2)) {
                System.out.println("✓ Pago 2 creado: " + pago2);
            }
            
            System.out.println("Total de pagos: " + pagoDAO.contar());
        }
        System.out.println();
        
        
        // ═══════════════════════════════════════════════════════════
        //           PRUEBA 8: READ - PRÉSTAMOS Y PAGOS
        // ═══════════════════════════════════════════════════════════
        
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  PRUEBA 8: READ - Préstamos y Pagos  ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        System.out.println("--- Préstamos ---");
        prestamos = prestamoDAO.obtenerTodos();
        for (Prestamo p : prestamos) {
            System.out.println(p);
            System.out.println();
        }
        
        System.out.println("--- Pagos ---");
        ArrayList<Pago> pagos = pagoDAO.obtenerTodos();
        for (Pago p : pagos) {
            System.out.println(p);
        }
        System.out.println();
        
        
        // ═══════════════════════════════════════════════════════════
        //           PRUEBA 9: BÚSQUEDAS ESPECÍFICAS
        // ═══════════════════════════════════════════════════════════
        
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  PRUEBA 9: Búsquedas específicas     ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        // Buscar por documento
        Cliente porDocumento = clienteDAO.obtenerPorDocumento("1111111111");
        if (porDocumento != null) {
            System.out.println("✓ Cliente encontrado por documento:");
            System.out.println(porDocumento);
        }
        System.out.println();
        
        // Buscar empleados por rol
        ArrayList<Empleado> gerentes = empleadoDAO.obtenerPorRol("Gerente");
        System.out.println("Gerentes encontrados: " + gerentes.size());
        for (Empleado g : gerentes) {
            System.out.println("  " + g.getNombre() + " - " + g.getRol());
        }
        System.out.println();
        
        // Buscar préstamos por estado
        ArrayList<Prestamo> pendientes = prestamoDAO.obtenerPorEstado("pendiente");
        System.out.println("Préstamos pendientes: " + pendientes.size());
        System.out.println();
        
        
        // ═══════════════════════════════════════════════════════════
        //           PRUEBA 10: DELETE - ELIMINAR
        // ═══════════════════════════════════════════════════════════
        
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  PRUEBA 10: DELETE - Eliminar        ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        // Crear cliente temporal para eliminar
        Cliente cliTemp = new Cliente(
            "Temporal Test",
            "9999999999",
            "temp@test.com",
            "3009999999"
        );
        
        if (clienteDAO.crear(cliTemp)) {
            System.out.println("✓ Cliente temporal creado con ID: " + cliTemp.getId());
            System.out.println("Total antes de eliminar: " + clienteDAO.contar());
            
            // Eliminar
            if (clienteDAO.eliminar(cliTemp.getId())) {
                System.out.println("✓ Cliente eliminado");
                System.out.println("Total después de eliminar: " + clienteDAO.contar());
            }
        }
        System.out.println();
        
        
        // ═══════════════════════════════════════════════════════════
        //           PRUEBA 11: VERIFICAR ARCHIVOS EN data/
        // ═══════════════════════════════════════════════════════════
        
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  PRUEBA 11: Verificar archivos       ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        System.out.println("Archivos en carpeta data/:");
        System.out.println("  clientes.txt: " + 
            (ArchivoUtil.existeArchivo("data/clientes.txt") ? "✓ Existe" : "✗ No existe"));
        System.out.println("  empleados.txt: " + 
            (ArchivoUtil.existeArchivo("data/empleados.txt") ? "✓ Existe" : "✗ No existe"));
        System.out.println("  prestamos.txt: " + 
            (ArchivoUtil.existeArchivo("data/prestamos.txt") ? "✓ Existe" : "✗ No existe"));
        System.out.println("  pagos.txt: " + 
            (ArchivoUtil.existeArchivo("data/pagos.txt") ? "✓ Existe" : "✗ No existe"));
        System.out.println();
        
        System.out.println("Líneas en archivos:");
        System.out.println("  clientes.txt: " + 
            ArchivoUtil.contarLineas("data/clientes.txt") + " líneas");
        System.out.println("  empleados.txt: " + 
            ArchivoUtil.contarLineas("data/empleados.txt") + " líneas");
        System.out.println("  prestamos.txt: " + 
            ArchivoUtil.contarLineas("data/prestamos.txt") + " líneas");
        System.out.println("  pagos.txt: " + 
            ArchivoUtil.contarLineas("data/pagos.txt") + " líneas");
        System.out.println();
        
        
        // ═══════════════════════════════════════════════════════════
        //           RESUMEN FINAL
        // ═══════════════════════════════════════════════════════════
        
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  ✓✓✓ MILESTONE 3 COMPLETADO ✓✓✓     ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║                                       ║");
        System.out.println("║  Issue #8: ArchivoUtil ✓             ║");
        System.out.println("║    - guardarLinea()                  ║");
        System.out.println("║    - leerArchivo()                   ║");
        System.out.println("║    - actualizarArchivo()             ║");
        System.out.println("║    - existeArchivo()                 ║");
        System.out.println("║    - contarLineas()                  ║");
        System.out.println("║                                       ║");
        System.out.println("║  Issue #9: ClienteDAO ✓              ║");
        System.out.println("║    - CRUD completo                   ║");
        System.out.println("║    - Búsqueda por documento          ║");
        System.out.println("║    - Generación automática de IDs    ║");
        System.out.println("║                                       ║");
        System.out.println("║  Issue #10: EmpleadoDAO ✓            ║");
        System.out.println("║    - CRUD completo                   ║");
        System.out.println("║    - Búsqueda por rol                ║");
        System.out.println("║    - Generación automática de IDs    ║");
        System.out.println("║                                       ║");
        System.out.println("║  Issue #11: PrestamoDAO y PagoDAO ✓  ║");
        System.out.println("║    - CRUD completo                   ║");
        System.out.println("║    - Relaciones funcionando          ║");
        System.out.println("║    - Búsqueda por estado             ║");
        System.out.println("║                                       ║");
        System.out.println("║  Persistencia en carpeta data/:      ║");
        System.out.println("║    ✓ Clientes: " + String.format("%-3d", clienteDAO.contar()) + " registros            ║");
        System.out.println("║    ✓ Empleados: " + String.format("%-3d", empleadoDAO.contar()) + " registros           ║");
        System.out.println("║    ✓ Préstamos: " + String.format("%-3d", prestamoDAO.contar()) + " registros           ║");
        System.out.println("║    ✓ Pagos: " + String.format("%-3d", pagoDAO.contar()) + " registros               ║");
        System.out.println("║                                       ║");
        System.out.println("║  Interfaz IDao implementada ✓        ║");
        System.out.println("║  Archivos guardados en data/ ✓       ║");
        System.out.println("║                                       ║");
        System.out.println("╚═══════════════════════════════════════╝");
    }
}