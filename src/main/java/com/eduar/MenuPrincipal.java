package com.eduar;

import com.eduar.servicio.*;
import com.eduar.modelo.*;
import com.eduar.util.ConexionDb;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Menú principal del sistema CrediYa
 * 
 * @author Eduar Humberto Guerrero Vergel
 * @version 1.0
 */
public class MenuPrincipal {
    
    private static Scanner scanner = new Scanner(System.in);
    private static ClienteServicio clienteServicio = new ClienteServicio();
    private static EmpleadoServicio empleadoServicio = new EmpleadoServicio();
    private static PrestamoServicio prestamoServicio = new PrestamoServicio();
    private static PagoServicio pagoServicio = new PagoServicio();
    private static ReporteServicio reporteServicio = new ReporteServicio();
    
    
    public static void main(String[] args) {
        mostrarBanner();
        
        if (!ConexionDb.probarConexion()) {
            System.err.println("\n❌ ERROR: No se pudo conectar a la base de datos");
            System.err.println("Verifica que Docker esté corriendo: docker ps");
            System.err.println("Para iniciar: docker compose up -d");
            return;
        }
        
        System.out.println();
        menuPrincipal();
        
        scanner.close();
        System.exit(0);
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    MENÚ PRINCIPAL
    // ═══════════════════════════════════════════════════════════
    
    private static void menuPrincipal() {
        int opcion;
        
        do {
            System.out.println("\n╔═══════════════════════════════════════╗");
            System.out.println("║         MENÚ PRINCIPAL               ║");
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.println("║  1. Gestión de Empleados             ║");
            System.out.println("║  2. Gestión de Clientes              ║");
            System.out.println("║  3. Gestión de Préstamos             ║");
            System.out.println("║  4. Gestión de Pagos                 ║");
            System.out.println("║  5. Reportes                         ║");
            System.out.println("║  0. Salir                            ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.print("Seleccione una opción: ");
            
            opcion = leerEntero();
            
            switch (opcion) {
                case 1:
                    menuEmpleados();
                    break;
                case 2:
                    menuClientes();
                    break;
                case 3:
                    menuPrestamos();
                    break;
                case 4:
                    menuPagos();
                    break;
                case 5:
                    menuReportes();
                    break;
                case 0:
                    System.out.println("\n✓ Gracias por usar CrediYa S.A.S.");
                    System.out.println("✓ ¡Hasta pronto!\n");
                    break;
                default:
                    System.out.println("\n✗ Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    MENÚ EMPLEADOS
    // ═══════════════════════════════════════════════════════════
    
    private static void menuEmpleados() {
        int opcion;
        
        do {
            System.out.println("\n╔═══════════════════════════════════════╗");
            System.out.println("║      GESTIÓN DE EMPLEADOS            ║");
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.println("║  1. Registrar nuevo empleado         ║");
            System.out.println("║  2. Listar empleados                 ║");
            System.out.println("║  3. Buscar empleado por ID           ║");
            System.out.println("║  4. Buscar por documento             ║");
            System.out.println("║  5. Buscar por rol                   ║");
            System.out.println("║  6. Actualizar empleado              ║");
            System.out.println("║  7. Eliminar empleado                ║");
            System.out.println("║  8. Ver nómina total                 ║");
            System.out.println("║  0. Volver al menú principal         ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.print("Seleccione una opción: ");
            
            opcion = leerEntero();
            
            switch (opcion) {
                case 1:
                    registrarEmpleado();
                    break;
                case 2:
                    listarEmpleados();
                    break;
                case 3:
                    buscarEmpleadoPorId();
                    break;
                case 4:
                    buscarEmpleadoPorDocumento();
                    break;
                case 5:
                    buscarEmpleadosPorRol();
                    break;
                case 6:
                    actualizarEmpleado();
                    break;
                case 7:
                    eliminarEmpleado();
                    break;
                case 8:
                    verNominaTotal();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("\n✗ Opción inválida.");
            }
        } while (opcion != 0);
    }
    
    private static void registrarEmpleado() {
        System.out.println("\n--- Registrar Nuevo Empleado ---");
        
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Documento: ");
        String documento = scanner.nextLine();
        
        System.out.print("Correo electrónico: ");
        String correo = scanner.nextLine();
        
        System.out.print("Rol (Gerente/Asesor/Contador/Cajero/Auxiliar): ");
        String rol = scanner.nextLine();
        
        System.out.print("Salario: ");
        double salario = leerDouble();
        scanner.nextLine();
        
        Empleado empleado = new Empleado(nombre, documento, correo, rol, salario);
        
        if (empleadoServicio.registrarEmpleado(empleado)) {
            System.out.println("\n✓ Empleado registrado exitosamente");
            pausar();
        } else {
            System.out.println("\n✗ No se pudo registrar el empleado.");
            pausar();
        }
    }
    
    private static void listarEmpleados() {
        System.out.println("\n--- Lista de Empleados ---");
        ArrayList<Empleado> empleados = empleadoServicio.listarTodos();
        
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
        } else {
            System.out.printf("\n%-5s %-25s %-15s %-15s %-12s%n", 
                "ID", "Nombre", "Rol", "Documento", "Salario");
            System.out.println(repetir("-", 80));
            
            for (Empleado e : empleados) {
                System.out.printf("%-5d %-25s %-15s %-15s ",
                    e.getId(),
                    truncar(e.getNombre(), 25),
                    e.getRol(),
                    e.getDocumento()
                );
                System.out.printf("$%,.0f%n", e.getSalario());
            }
            System.out.println("\nTotal: " + empleados.size() + " empleados");
        }
        pausar();
    }
    
    private static void buscarEmpleadoPorId() {
        System.out.print("\nIngrese ID del empleado: ");
        int id = leerEntero();
        scanner.nextLine();
        
        Empleado empleado = empleadoServicio.buscarPorId(id);
        
        if (empleado != null) {
            System.out.println("\n" + empleado);
        } else {
            System.out.println("\n✗ Empleado no encontrado.");
        }
        pausar();
    }
    
    private static void buscarEmpleadoPorDocumento() {
        System.out.print("\nIngrese documento: ");
        String documento = scanner.nextLine();
        
        Empleado empleado = empleadoServicio.buscarPorDocumento(documento);
        
        if (empleado != null) {
            System.out.println("\n" + empleado);
        } else {
            System.out.println("\n✗ Empleado no encontrado.");
        }
        pausar();
    }
    
    private static void buscarEmpleadosPorRol() {
        System.out.print("\nIngrese rol (Gerente/Asesor/Contador/Cajero/Auxiliar): ");
        String rol = scanner.nextLine();
        
        ArrayList<Empleado> empleados = empleadoServicio.buscarPorRol(rol);
        
        if (empleados.isEmpty()) {
            System.out.println("\n✗ No se encontraron empleados con ese rol.");
        } else {
            System.out.println("\nEmpleados con rol '" + rol + "':");
            for (Empleado e : empleados) {
                System.out.println("  • " + e.getNombre() + " - " + e.getDocumento());
            }
        }
        pausar();
    }
    
    private static void actualizarEmpleado() {
        System.out.print("\nIngrese ID del empleado a actualizar: ");
        int id = leerEntero();
        scanner.nextLine();
        
        Empleado empleado = empleadoServicio.buscarPorId(id);
        
        if (empleado == null) {
            System.out.println("\n✗ Empleado no encontrado.");
            pausar();
            return;
        }
        
        System.out.println("\nDatos actuales:");
        System.out.println(empleado);
        System.out.println("\nIngrese nuevos datos (Enter para mantener):");
        
        System.out.print("Nuevo correo [" + empleado.getCorreo() + "]: ");
        String correo = scanner.nextLine();
        if (!correo.trim().isEmpty()) {
            empleado.setCorreo(correo);
        }
        
        System.out.print("Nuevo rol [" + empleado.getRol() + "]: ");
        String rol = scanner.nextLine();
        if (!rol.trim().isEmpty()) {
            empleado.setRol(rol);
        }
        
        System.out.print("Nuevo salario [" + empleado.getSalario() + "]: ");
        String salarioStr = scanner.nextLine();
        if (!salarioStr.trim().isEmpty()) {
            try {
                double salario = Double.parseDouble(salarioStr);
                empleado.setSalario(salario);
            } catch (NumberFormatException e) {
                System.out.println("✗ Salario inválido, se mantiene el anterior.");
            }
        }
        
        if (empleadoServicio.actualizarEmpleado(empleado)) {
            System.out.println("\n✓ Empleado actualizado exitosamente.");
        } else {
            System.out.println("\n✗ No se pudo actualizar el empleado.");
        }
        pausar();
    }
    
    private static void eliminarEmpleado() {
        System.out.print("\nIngrese ID del empleado a eliminar: ");
        int id = leerEntero();
        scanner.nextLine();
        
        Empleado empleado = empleadoServicio.buscarPorId(id);
        
        if (empleado == null) {
            System.out.println("\n✗ Empleado no encontrado.");
            pausar();
            return;
        }
        
        ArrayList<Prestamo> prestamos = prestamoServicio.listarTodos();
        boolean tienePrestamos = false;
        
        for (Prestamo p : prestamos) {
            if (p.getEmpleado().getId() == id) {
                tienePrestamos = true;
                break;
            }
        }
        
        if (tienePrestamos) {
            System.out.println("\n✗ ERROR: No se puede eliminar este empleado");
            System.out.println("✗ El empleado tiene préstamos asociados");
            System.out.println("✗ Primero debe eliminar o reasignar los préstamos");
            pausar();
            return;
        }
        
        System.out.println("\n" + empleado);
        System.out.print("\n¿Está seguro de eliminar este empleado? (S/N): ");
        String confirmacion = scanner.nextLine();
        
        if (confirmacion.equalsIgnoreCase("S")) {
            if (empleadoServicio.eliminarEmpleado(id)) {
                System.out.println("\n✓ Empleado eliminado exitosamente.");
            } else {
                System.out.println("\n✗ No se pudo eliminar el empleado.");
            }
        } else {
            System.out.println("\n✗ Operación cancelada.");
        }
        pausar();
    }
    
    private static void verNominaTotal() {
        double nomina = empleadoServicio.calcularNominaTotal();
        int total = empleadoServicio.obtenerTotalEmpleados();
        
        System.out.println("\n--- Nómina Total ---");
        System.out.println("Total empleados: " + total);
        System.out.println("Nómina mensual: $" + String.format("%,.2f", nomina));
        System.out.println("Nómina anual: $" + String.format("%,.2f", nomina * 12));
        pausar();
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    MENÚ CLIENTES
    // ═══════════════════════════════════════════════════════════
    
    private static void menuClientes() {
        int opcion;
        
        do {
            System.out.println("\n╔═══════════════════════════════════════╗");
            System.out.println("║       GESTIÓN DE CLIENTES            ║");
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.println("║  1. Registrar nuevo cliente          ║");
            System.out.println("║  2. Listar clientes                  ║");
            System.out.println("║  3. Buscar cliente por ID            ║");
            System.out.println("║  4. Buscar por documento             ║");
            System.out.println("║  5. Actualizar cliente               ║");
            System.out.println("║  6. Eliminar cliente                 ║");
            System.out.println("║  0. Volver al menú principal         ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.print("Seleccione una opción: ");
            
            opcion = leerEntero();
            
            switch (opcion) {
                case 1:
                    registrarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    buscarClientePorId();
                    break;
                case 4:
                    buscarClientePorDocumento();
                    break;
                case 5:
                    actualizarCliente();
                    break;
                case 6:
                    eliminarCliente();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("\n✗ Opción inválida.");
            }
        } while (opcion != 0);
    }
    
    private static void registrarCliente() {
        System.out.println("\n--- Registrar Nuevo Cliente ---");
        
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Documento: ");
        String documento = scanner.nextLine();
        
        System.out.print("Correo electrónico: ");
        String correo = scanner.nextLine();
        
        System.out.print("Teléfono (10 dígitos): ");
        String telefono = scanner.nextLine();
        
        Cliente cliente = new Cliente(nombre, documento, correo, telefono);
        
        if (clienteServicio.registrarCliente(cliente)) {
            System.out.println("\n✓ Cliente registrado exitosamente");
            pausar();
        } else {
            System.out.println("\n✗ No se pudo registrar el cliente.");
            pausar();
        }
    }
    
    private static void listarClientes() {
        System.out.println("\n--- Lista de Clientes ---");
        ArrayList<Cliente> clientes = clienteServicio.listarTodos();
        
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            System.out.printf("\n%-5s %-25s %-15s %-30s %-12s%n", 
                "ID", "Nombre", "Documento", "Correo", "Teléfono");
            System.out.println(repetir("-", 95));
            
            for (Cliente c : clientes) {
                System.out.printf("%-5d %-25s %-15s %-30s %-12s%n",
                    c.getId(),
                    truncar(c.getNombre(), 25),
                    c.getDocumento(),
                    truncar(c.getCorreo(), 30),
                    c.getTelefono()
                );
            }
            System.out.println("\nTotal: " + clientes.size() + " clientes");
        }
        pausar();
    }
    
    private static void buscarClientePorId() {
        System.out.print("\nIngrese ID del cliente: ");
        int id = leerEntero();
        scanner.nextLine();
        
        Cliente cliente = clienteServicio.buscarPorId(id);
        
        if (cliente != null) {
            System.out.println("\n" + cliente);
        } else {
            System.out.println("\n✗ Cliente no encontrado.");
        }
        pausar();
    }
    
    private static void buscarClientePorDocumento() {
        System.out.print("\nIngrese documento: ");
        String documento = scanner.nextLine();
        
        Cliente cliente = clienteServicio.buscarPorDocumento(documento);
        
        if (cliente != null) {
            System.out.println("\n" + cliente);
        } else {
            System.out.println("\n✗ Cliente no encontrado.");
        }
        pausar();
    }
    
    private static void actualizarCliente() {
        System.out.print("\nIngrese ID del cliente a actualizar: ");
        int id = leerEntero();
        scanner.nextLine();
        
        Cliente cliente = clienteServicio.buscarPorId(id);
        
        if (cliente == null) {
            System.out.println("\n✗ Cliente no encontrado.");
            pausar();
            return;
        }
        
        System.out.println("\nDatos actuales:");
        System.out.println(cliente);
        System.out.println("\nIngrese nuevos datos (Enter para mantener):");
        
        System.out.print("Nuevo correo [" + cliente.getCorreo() + "]: ");
        String correo = scanner.nextLine();
        if (!correo.trim().isEmpty()) {
            cliente.setCorreo(correo);
        }
        
        System.out.print("Nuevo teléfono [" + cliente.getTelefono() + "]: ");
        String telefono = scanner.nextLine();
        if (!telefono.trim().isEmpty()) {
            cliente.setTelefono(telefono);
        }
        
        if (clienteServicio.actualizarCliente(cliente)) {
            System.out.println("\n✓ Cliente actualizado exitosamente.");
        } else {
            System.out.println("\n✗ No se pudo actualizar el cliente.");
        }
        pausar();
    }
    
    private static void eliminarCliente() {
        System.out.print("\nIngrese ID del cliente a eliminar: ");
        int id = leerEntero();
        scanner.nextLine();
        
        Cliente cliente = clienteServicio.buscarPorId(id);
        
        if (cliente == null) {
            System.out.println("\n✗ Cliente no encontrado.");
            pausar();
            return;
        }
        
        ArrayList<Prestamo> prestamos = prestamoServicio.buscarPorCliente(id);
        
        if (!prestamos.isEmpty()) {
            System.out.println("\n✗ ERROR: No se puede eliminar este cliente");
            System.out.println("✗ El cliente tiene " + prestamos.size() + " préstamo(s) asociado(s)");
            System.out.println("✗ Primero debe eliminar o completar los préstamos");
            pausar();
            return;
        }
        
        System.out.println("\n" + cliente);
        System.out.print("\n¿Está seguro de eliminar este cliente? (S/N): ");
        String confirmacion = scanner.nextLine();
        
        if (confirmacion.equalsIgnoreCase("S")) {
            if (clienteServicio.eliminarCliente(id)) {
                System.out.println("\n✓ Cliente eliminado exitosamente.");
            } else {
                System.out.println("\n✗ No se pudo eliminar el cliente.");
            }
        } else {
            System.out.println("\n✗ Operación cancelada.");
        }
        pausar();
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    MENÚ PRÉSTAMOS
    // ═══════════════════════════════════════════════════════════
    
    private static void menuPrestamos() {
        int opcion;
        
        do {
            System.out.println("\n╔═══════════════════════════════════════╗");
            System.out.println("║      GESTIÓN DE PRÉSTAMOS            ║");
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.println("║  1. Crear nuevo préstamo             ║");
            System.out.println("║  2. Listar préstamos                 ║");
            System.out.println("║  3. Buscar préstamo por ID           ║");
            System.out.println("║  4. Buscar por cliente               ║");
            System.out.println("║  5. Buscar por estado                ║");
            System.out.println("║  6. Cambiar estado de préstamo       ║");
            System.out.println("║  7. Ver préstamos vencidos           ║");
            System.out.println("║  0. Volver al menú principal         ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.print("Seleccione una opción: ");
            
            opcion = leerEntero();
            
            switch (opcion) {
                case 1:
                    crearPrestamo();
                    break;
                case 2:
                    listarPrestamos();
                    break;
                case 3:
                    buscarPrestamoPorId();
                    break;
                case 4:
                    buscarPrestamosPorCliente();
                    break;
                case 5:
                    buscarPrestamosPorEstado();
                    break;
                case 6:
                    cambiarEstadoPrestamo();
                    break;
                case 7:
                    verPrestamosVencidos();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("\n✗ Opción inválida.");
            }
        } while (opcion != 0);
    }
    
    private static void crearPrestamo() {
        System.out.println("\n--- Crear Nuevo Préstamo ---");
        
        System.out.print("ID del cliente: ");
        int clienteId = leerEntero();
        scanner.nextLine();
        
        Cliente cliente = clienteServicio.buscarPorId(clienteId);
        if (cliente == null) {
            System.out.println("\n✗ Cliente no encontrado.");
            pausar();
            return;
        }
        
        System.out.println("Cliente: " + cliente.getNombre());
        
        if (prestamoServicio.tienePrestamoPendiente(clienteId)) {
            System.out.println("\n✗ El cliente ya tiene un préstamo pendiente.");
            pausar();
            return;
        }
        
        System.out.print("ID del empleado que aprueba: ");
        int empleadoId = leerEntero();
        scanner.nextLine();
        
        Empleado empleado = empleadoServicio.buscarPorId(empleadoId);
        if (empleado == null) {
            System.out.println("\n✗ Empleado no encontrado.");
            pausar();
            return;
        }
        
        System.out.println("Empleado: " + empleado.getNombre());
        
        System.out.print("Monto del préstamo ($500,000 - $50,000,000): ");
        double monto = leerDouble();
        scanner.nextLine();
        
        System.out.print("Tasa de interés (1% - 15%): ");
        double interes = leerDouble();
        scanner.nextLine();
        
        System.out.print("Número de cuotas (3 - 60 meses): ");
        int cuotas = leerEntero();
        scanner.nextLine();
        
        if (prestamoServicio.crearPrestamo(clienteId, empleadoId, monto, interes, cuotas, LocalDate.now())) {
            pausar();
        } else {
            System.out.println("\n✗ No se pudo crear el préstamo.");
            pausar();
        }
    }
    private static void listarPrestamos() {
    System.out.println("\n--- Lista de Préstamos ---");
    ArrayList<Prestamo> prestamos = prestamoServicio.listarTodos();
    
    if (prestamos.isEmpty()) {
        System.out.println("No hay préstamos registrados.");
    } else {
        System.out.printf("\n%-5s %-20s %-15s %-8s %-10s %-15s%n",
            "ID", "Cliente", "Monto", "Cuotas", "Estado", "Saldo");
        System.out.println(repetir("-", 85));
        
        for (Prestamo p : prestamos) {
            System.out.printf("%-5d %-20s $%,14.0f %-8d %-10s $%,14.0f%n",
                p.getId(),
                truncar(p.getCliente().getNombre(), 20),
                p.getMonto(),
                p.getCuotas(),
                p.getEstado(),
                p.getSaldoPendiente()
            );
        }
        System.out.println("\nTotal: " + prestamos.size() + " préstamos");
    }
    pausar();
}
    
    private static void buscarPrestamoPorId() {
        System.out.print("\nIngrese ID del préstamo: ");
        int id = leerEntero();
        scanner.nextLine();
        
        Prestamo prestamo = prestamoServicio.buscarPorId(id);
        
        if (prestamo != null) {
            System.out.println("\n" + prestamo);
        } else {
            System.out.println("\n✗ Préstamo no encontrado.");
        }
        pausar();
    }
    
    private static void buscarPrestamosPorCliente() {
        System.out.print("\nIngrese ID del cliente: ");
        int clienteId = leerEntero();
        scanner.nextLine();
        
        ArrayList<Prestamo> prestamos = prestamoServicio.buscarPorCliente(clienteId);
        
        if (prestamos.isEmpty()) {
            System.out.println("\n✗ No se encontraron préstamos para este cliente.");
        } else {
            System.out.println("\nPréstamos del cliente:");
            for (Prestamo p : prestamos) {
                System.out.println("\n" + p);
            }
        }
        pausar();
    }
    
    private static void buscarPrestamosPorEstado() {
        System.out.print("\nIngrese estado (pendiente/pagado/vencido): ");
        String estado = scanner.nextLine();
        
        ArrayList<Prestamo> prestamos = prestamoServicio.buscarPorEstado(estado);
        
        if (prestamos.isEmpty()) {
            System.out.println("\n✗ No se encontraron préstamos con ese estado.");
        } else {
            System.out.println("\nPréstamos con estado '" + estado + "': " + prestamos.size());
            for (Prestamo p : prestamos) {
                System.out.println("  • Préstamo #" + p.getId() + " - Cliente: " + p.getCliente().getNombre());
            }
        }
        pausar();
    }
    
    private static void cambiarEstadoPrestamo() {
        System.out.print("\nIngrese ID del préstamo: ");
        int id = leerEntero();
        scanner.nextLine();
        
        Prestamo prestamo = prestamoServicio.buscarPorId(id);
        
        if (prestamo == null) {
            System.out.println("\n✗ Préstamo no encontrado.");
            pausar();
            return;
        }
        
        System.out.println("\nEstado actual: " + prestamo.getEstado());
        System.out.print("Nuevo estado (pendiente/pagado/vencido): ");
        String nuevoEstado = scanner.nextLine();
        
        if (prestamoServicio.actualizarEstado(id, nuevoEstado)) {
            pausar();
        } else {
            pausar();
            }
}

private static void verPrestamosVencidos() {
    System.out.println("\n--- Préstamos Vencidos ---");
    ArrayList<Prestamo> vencidos = prestamoServicio.obtenerPrestamosVencidos();
    
    if (vencidos.isEmpty()) {
        System.out.println("✓ No hay préstamos vencidos.");
    } else {
        System.out.println("⚠️  Total préstamos vencidos: " + vencidos.size());
        System.out.println();
        
        for (Prestamo p : vencidos) {
            System.out.println(p);
            System.out.println();
        }
    }
    pausar();
}


// ═══════════════════════════════════════════════════════════
//                    MENÚ PAGOS
// ═══════════════════════════════════════════════════════════

private static void menuPagos() {
    int opcion;
    
    do {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║        GESTIÓN DE PAGOS              ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║  1. Registrar pago                   ║");
        System.out.println("║  2. Listar pagos                     ║");
        System.out.println("║  3. Buscar pago por ID               ║");
        System.out.println("║  4. Buscar pagos por préstamo        ║");
        System.out.println("║  5. Ver total recaudado              ║");
        System.out.println("║  0. Volver al menú principal         ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.print("Seleccione una opción: ");
        
        opcion = leerEntero();
        
        switch (opcion) {
            case 1:
                registrarPago();
                break;
            case 2:
                listarPagos();
                break;
            case 3:
                buscarPagoPorId();
                break;
            case 4:
                buscarPagosPorPrestamo();
                break;
            case 5:
                verTotalRecaudado();
                break;
            case 0:
                break;
            default:
                System.out.println("\n✗ Opción inválida.");
        }
    } while (opcion != 0);
}

private static void registrarPago() {
    System.out.println("\n--- Registrar Pago ---");
    
    System.out.print("ID del préstamo: ");
    int prestamoId = leerEntero();
    scanner.nextLine();
    
    Prestamo prestamo = prestamoServicio.buscarPorId(prestamoId);
    
    if (prestamo == null) {
        System.out.println("\n✗ Préstamo no encontrado.");
        pausar();
        return;
    }
    
    System.out.println("\nPréstamo #" + prestamo.getId());
    System.out.println("Cliente: " + prestamo.getCliente().getNombre());
    System.out.println("Saldo pendiente: $" + String.format("%,.2f", prestamo.getSaldoPendiente()));
    System.out.println("Cuota mensual: $" + String.format("%,.2f", prestamo.calcularCuotaMensual()));
    
    System.out.print("\nMonto del pago: ");
    double monto = leerDouble();
    scanner.nextLine();
    
    System.out.print("Observaciones (opcional): ");
    String observaciones = scanner.nextLine();
    
    if (pagoServicio.registrarPago(prestamoId, monto, LocalDate.now(), observaciones)) {
        pausar();
    } else {
        pausar();
    }
}
private static void listarPagos() {
    System.out.println("\n--- Lista de Pagos ---");
    ArrayList<Pago> pagos = pagoServicio.listarTodos();
    
    if (pagos.isEmpty()) {
        System.out.println("No hay pagos registrados.");
    } else {
        System.out.printf("\n%-5s %-12s %-15s %-30s%n",
            "ID", "Préstamo", "Monto", "Observaciones");
        System.out.println(repetir("-", 70));
        
        for (Pago p : pagos) {
            System.out.printf("%-5d %-12d $%,14.0f %-30s%n",
                p.getId(),
                p.getPrestamo().getId(),
                p.getMonto(),
                truncar(p.getObservaciones() != null ? p.getObservaciones() : "", 30)
            );
        }
        System.out.println("\nTotal: " + pagos.size() + " pagos");
    }
    pausar();
}

private static void buscarPagoPorId() {
    System.out.print("\nIngrese ID del pago: ");
    int id = leerEntero();
    scanner.nextLine();
    
    Pago pago = pagoServicio.buscarPorId(id);
    
    if (pago != null) {
        System.out.println("\n" + pago);
    } else {
        System.out.println("\n✗ Pago no encontrado.");
    }
    pausar();
}

private static void buscarPagosPorPrestamo() {
    System.out.print("\nIngrese ID del préstamo: ");
    int prestamoId = leerEntero();
    scanner.nextLine();
    
    ArrayList<Pago> pagos = pagoServicio.buscarPorPrestamo(prestamoId);
    
    if (pagos.isEmpty()) {
        System.out.println("\n✗ No se encontraron pagos para este préstamo.");
    } else {
        System.out.println("\nPagos del préstamo #" + prestamoId + ":");
        double total = 0;
        
        for (Pago p : pagos) {
            System.out.println("  • Pago #" + p.getId() + " - $" + 
                String.format("%,.2f", p.getMonto()) + " - " + p.getFechaPago());
            total += p.getMonto();
        }
        
        System.out.println("\nTotal pagado: $" + String.format("%,.2f", total));
    }
    pausar();
}

private static void verTotalRecaudado() {
    double total = pagoServicio.calcularTotalRecaudado();
    int totalPagos = pagoServicio.obtenerTotalPagos();
    
    System.out.println("\n--- Total Recaudado ---");
    System.out.println("Total de pagos: " + totalPagos);
    System.out.println("Total recaudado: $" + String.format("%,.2f", total));
    pausar();
}


// ═══════════════════════════════════════════════════════════
//                    MENÚ REPORTES
// ═══════════════════════════════════════════════════════════

private static void menuReportes() {
    int opcion;
    
    do {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║            REPORTES                  ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║  1. Reporte general del sistema      ║");
        System.out.println("║  2. Reporte de clientes              ║");
        System.out.println("║  3. Reporte de préstamos             ║");
        System.out.println("║  4. Reporte por cliente              ║");
        System.out.println("║  5. Reporte de préstamos vencidos    ║");
        System.out.println("║  0. Volver al menú principal         ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.print("Seleccione una opción: ");
        
        opcion = leerEntero();
        
        switch (opcion) {
            case 1:
                reporteServicio.generarReporteGeneral();
                pausar();
                break;
            case 2:
                reporteServicio.generarReporteClientes();
                pausar();
                break;
            case 3:
                reporteServicio.generarReportePrestamos();
                pausar();
                break;
            case 4:
                System.out.print("\nIngrese ID del cliente: ");
                int clienteId = leerEntero();
                scanner.nextLine();
                reporteServicio.generarReportePorCliente(clienteId);
                pausar();
                break;
            case 5:
                reporteServicio.generarReportePrestamosVencidos();
                pausar();
                break;
            case 0:
                break;
            default:
                System.out.println("\n✗ Opción inválida.");
        }
    } while (opcion != 0);
}


// ═══════════════════════════════════════════════════════════
//                    UTILIDADES
// ═══════════════════════════════════════════════════════════

private static void mostrarBanner() {
    System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
    System.out.println("║                                                           ║");
    System.out.println("║             🏦  CREDIYA S.A.S.  🏦                        ║");
    System.out.println("║                                                           ║");
    System.out.println("║          Sistema de Gestión de Préstamos                 ║");
    System.out.println("║                                                           ║");
    System.out.println("║              Versión 1.0 - Diciembre 2025                 ║");
    System.out.println("║                                                           ║");
    System.out.println("╚═══════════════════════════════════════════════════════════╝");
}

private static int leerEntero() {
    while (true) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.print("✗ Entrada inválida. Ingrese un número: ");
        }
    }
}

private static double leerDouble() {
    while (true) {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.print("✗ Entrada inválida. Ingrese un número: ");
        }
    }
}

private static String truncar(String texto, int longitud) {
    if (texto == null) return "";
    if (texto.length() <= longitud) return texto;
    return texto.substring(0, longitud - 3) + "...";
}

private static String repetir(String caracter, int veces) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < veces; i++) {
        sb.append(caracter);
    }
    return sb.toString();
}

private static void pausar() {
    System.out.print("\nPresione Enter para continuar...");
    scanner.nextLine();
}
}