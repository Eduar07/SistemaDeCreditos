package com.eduar;

import com.eduar.servicio.*;
import com.eduar.modelo.*;
import com.eduar.util.ConexionDb;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Pruebas de integración completas del sistema
 * 
 * @author Eduar Humberto Guerrero Vergel
 * @version 1.0
 */
public class TestIntegracion {
    
    private static ClienteServicio clienteServicio = new ClienteServicio();
    private static EmpleadoServicio empleadoServicio = new EmpleadoServicio();
    private static PrestamoServicio prestamoServicio = new PrestamoServicio();
    private static PagoServicio pagoServicio = new PagoServicio();
    
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║          CREDIYA - PRUEBAS DE INTEGRACIÓN                ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
        
        // Verificar conexión
        if (!ConexionDb.probarConexion()) {
            System.err.println("❌ No se pudo conectar a la base de datos");
            return;
        }
        
        System.out.println();
        
        // Ejecutar todas las pruebas
        boolean todoPasado = true;
        
        todoPasado &= test1_FlujoCompletoRegistroEmpleado();
        todoPasado &= test2_FlujoCompletoRegistroCliente();
        todoPasado &= test3_FlujoCompletoCreacionPrestamo();
        todoPasado &= test4_FlujoCompletoRegistroPago();
        todoPasado &= test5_VerificarPersistenciaBD();
        todoPasado &= test6_ValidacionesDeNegocio();
        todoPasado &= test7_ActualizacionesYEliminaciones();
        todoPasado &= test8_ReportesYEstadisticas();
        
        // Resumen final
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        if (todoPasado) {
            System.out.println("║     ✓✓✓ TODAS LAS PRUEBAS PASARON EXITOSAMENTE ✓✓✓      ║");
        } else {
            System.out.println("║        ✗✗✗ ALGUNAS PRUEBAS FALLARON ✗✗✗                 ║");
        }
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
        
        System.exit(0);
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //     TEST 1: FLUJO COMPLETO REGISTRO DE EMPLEADO
    // ═══════════════════════════════════════════════════════════
    
    private static boolean test1_FlujoCompletoRegistroEmpleado() {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  TEST 1: Registro de Empleado       ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        try {
            // Crear empleado
            Empleado empleado = new Empleado(
                "Laura Martínez",
                "1122334455",
                "laura.martinez@crediya.com",
                "Contador",
                3200000
            );
            
            // Registrar
            System.out.print("→ Registrando empleado... ");
            boolean registrado = empleadoServicio.registrarEmpleado(empleado);
            
            if (!registrado) {
                System.out.println("✗ FALLO");
                return false;
            }
            System.out.println("✓ OK");
            
            // Verificar que se puede buscar
            System.out.print("→ Buscando empleado por documento... ");
            Empleado encontrado = empleadoServicio.buscarPorDocumento("1122334455");
            
            if (encontrado == null) {
                System.out.println("✗ FALLO");
                return false;
            }
            System.out.println("✓ OK");
            
            // Verificar datos
            System.out.print("→ Verificando datos... ");
            if (!encontrado.getNombre().equals("Laura Martínez") ||
                !encontrado.getRol().equals("Contador") ||
                encontrado.getSalario() != 3200000) {
                System.out.println("✗ FALLO");
                return false;
            }
            System.out.println("✓ OK");
            
            System.out.println("\n✓ TEST 1: PASADO\n");
            return true;
            
        } catch (Exception e) {
            System.out.println("\n✗ TEST 1: FALLO - " + e.getMessage() + "\n");
            return false;
        }
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //     TEST 2: FLUJO COMPLETO REGISTRO DE CLIENTE
    // ═══════════════════════════════════════════════════════════
    
    private static boolean test2_FlujoCompletoRegistroCliente() {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  TEST 2: Registro de Cliente        ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        try {
            // Crear cliente
            Cliente cliente = new Cliente(
                "Andrés Gómez",
                "6677889900",
                "andres.gomez@email.com",
                "3101234567"
            );
            
            // Registrar
            System.out.print("→ Registrando cliente... ");
            boolean registrado = clienteServicio.registrarCliente(cliente);
            
            if (!registrado) {
                System.out.println("✗ FALLO");
                return false;
            }
            System.out.println("✓ OK");
            
            // Verificar que se puede buscar
            System.out.print("→ Buscando cliente por documento... ");
            Cliente encontrado = clienteServicio.buscarPorDocumento("6677889900");
            
            if (encontrado == null) {
                System.out.println("✗ FALLO");
                return false;
            }
            System.out.println("✓ OK");
            
            // Verificar datos
            System.out.print("→ Verificando datos... ");
            if (!encontrado.getNombre().equals("Andrés Gómez") ||
                !encontrado.getCorreo().equals("andres.gomez@email.com") ||
                !encontrado.getTelefono().equals("3101234567")) {
                System.out.println("✗ FALLO");
                return false;
            }
            System.out.println("✓ OK");
            
            System.out.println("\n✓ TEST 2: PASADO\n");
            return true;
            
        } catch (Exception e) {
            System.out.println("\n✗ TEST 2: FALLO - " + e.getMessage() + "\n");
            return false;
        }
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //     TEST 3: FLUJO COMPLETO CREACIÓN DE PRÉSTAMO
    // ═══════════════════════════════════════════════════════════
    
    private static boolean test3_FlujoCompletoCreacionPrestamo() {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  TEST 3: Creación de Préstamo       ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        try {
            // Buscar cliente y empleado existentes
            System.out.print("→ Buscando cliente... ");
            Cliente cliente = clienteServicio.buscarPorDocumento("6677889900");
            if (cliente == null) {
                System.out.println("✗ FALLO (Cliente no encontrado)");
                return false;
            }
            System.out.println("✓ OK");
            
            System.out.print("→ Buscando empleado... ");
            ArrayList<Empleado> empleados = empleadoServicio.listarTodos();
            if (empleados.isEmpty()) {
                System.out.println("✗ FALLO (No hay empleados)");
                return false;
            }
            Empleado empleado = empleados.get(0);
            System.out.println("✓ OK");
            
            // Crear préstamo
            System.out.print("→ Creando préstamo... ");
            boolean creado = prestamoServicio.crearPrestamo(
                cliente.getId(),
                empleado.getId(),
                2000000,    // Monto
                5.0,        // Interés
                12,         // Cuotas
                LocalDate.now()
            );
            
            if (!creado) {
                System.out.println("✗ FALLO");
                return false;
            }
            System.out.println("✓ OK");
            
            // Verificar que el préstamo existe
            System.out.print("→ Verificando préstamo creado... ");
            ArrayList<Prestamo> prestamos = prestamoServicio.buscarPorCliente(cliente.getId());
            
            if (prestamos.isEmpty()) {
                System.out.println("✗ FALLO");
                return false;
            }
            
            Prestamo prestamo = prestamos.get(prestamos.size() - 1); // Último creado
            
            if (prestamo.getMonto() != 2000000 ||
                prestamo.getInteres() != 5.0 ||
                prestamo.getCuotas() != 12) {
                System.out.println("✗ FALLO (Datos incorrectos)");
                return false;
            }
            System.out.println("✓ OK");
            
            // Verificar cálculos
            System.out.print("→ Verificando cálculos... ");
            double montoTotal = prestamo.calcularMontoTotal();
            double cuotaMensual = prestamo.calcularCuotaMensual();
            
            if (montoTotal <= prestamo.getMonto() || cuotaMensual <= 0) {
                System.out.println("✗ FALLO (Cálculos incorrectos)");
                return false;
            }
            System.out.println("✓ OK");
            
            System.out.println("\n✓ TEST 3: PASADO\n");
            return true;
            
        } catch (Exception e) {
            System.out.println("\n✗ TEST 3: FALLO - " + e.getMessage() + "\n");
            return false;
        }
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //     TEST 4: FLUJO COMPLETO REGISTRO DE PAGO
    // ═══════════════════════════════════════════════════════════
    
    private static boolean test4_FlujoCompletoRegistroPago() {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  TEST 4: Registro de Pago           ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        try {
            // Buscar un préstamo pendiente
            System.out.print("→ Buscando préstamo pendiente... ");
            ArrayList<Prestamo> pendientes = prestamoServicio.buscarPorEstado("pendiente");
            
            if (pendientes.isEmpty()) {
                System.out.println("✗ FALLO (No hay préstamos pendientes)");
                return false;
            }
            
            Prestamo prestamo = pendientes.get(0);
            double saldoAnterior = prestamo.getSaldoPendiente();
            System.out.println("✓ OK");
            
            // Registrar pago
            System.out.print("→ Registrando pago... ");
            double montoPago = prestamo.calcularCuotaMensual();
            
            boolean registrado = pagoServicio.registrarPago(
                prestamo.getId(),
                montoPago,
                LocalDate.now(),
                "Pago de prueba - Test de integración"
            );
            
            if (!registrado) {
                System.out.println("✗ FALLO");
                return false;
            }
            System.out.println("✓ OK");
            
            // Verificar que el saldo se actualizó
            System.out.print("→ Verificando actualización de saldo... ");
            Prestamo prestamoActualizado = prestamoServicio.buscarPorId(prestamo.getId());
            
            double nuevoSaldo = prestamoActualizado.getSaldoPendiente();
            
            if (nuevoSaldo >= saldoAnterior) {
                System.out.println("✗ FALLO (Saldo no disminuyó)");
                return false;
            }
            System.out.println("✓ OK");
            
            // Verificar que el pago se registró
            System.out.print("→ Verificando registro del pago... ");
            ArrayList<Pago> pagos = pagoServicio.buscarPorPrestamo(prestamo.getId());
            
            if (pagos.isEmpty()) {
                System.out.println("✗ FALLO (Pago no encontrado)");
                return false;
            }
            System.out.println("✓ OK");
            
            System.out.println("\n✓ TEST 4: PASADO\n");
            return true;
            
        } catch (Exception e) {
            System.out.println("\n✗ TEST 4: FALLO - " + e.getMessage() + "\n");
            return false;
        }
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //     TEST 5: VERIFICAR PERSISTENCIA EN BD
    // ═══════════════════════════════════════════════════════════
    
    private static boolean test5_VerificarPersistenciaBD() {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  TEST 5: Persistencia en BD         ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        try {
            // Verificar que hay datos en todas las tablas
            System.out.print("→ Verificando clientes en BD... ");
            int totalClientes = clienteServicio.obtenerTotalClientes();
            if (totalClientes == 0) {
                System.out.println("✗ FALLO");
                return false;
            }
            System.out.println("✓ OK (" + totalClientes + " registros)");
            
            System.out.print("→ Verificando empleados en BD... ");
            int totalEmpleados = empleadoServicio.obtenerTotalEmpleados();
            if (totalEmpleados == 0) {
                System.out.println("✗ FALLO");
                return false;
            }
            System.out.println("✓ OK (" + totalEmpleados + " registros)");
            
            System.out.print("→ Verificando préstamos en BD... ");
            int totalPrestamos = prestamoServicio.obtenerTotalPrestamos();
            if (totalPrestamos == 0) {
                System.out.println("✗ FALLO");
                return false;
            }
            System.out.println("✓ OK (" + totalPrestamos + " registros)");
            
            System.out.print("→ Verificando pagos en BD... ");
            int totalPagos = pagoServicio.obtenerTotalPagos();
            if (totalPagos == 0) {
                System.out.println("✗ FALLO");
                return false;
            }
            System.out.println("✓ OK (" + totalPagos + " registros)");
            
            System.out.println("\n✓ TEST 5: PASADO\n");
            return true;
            
        } catch (Exception e) {
            System.out.println("\n✗ TEST 5: FALLO - " + e.getMessage() + "\n");
            return false;
        }
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //     TEST 6: VALIDACIONES DE NEGOCIO
    // ═══════════════════════════════════════════════════════════
    
    private static boolean test6_ValidacionesDeNegocio() {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  TEST 6: Validaciones de Negocio    ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        try {
            // Test 1: No permitir documento duplicado en clientes
            System.out.print("→ Validando documento duplicado... ");
            Cliente clienteDuplicado = new Cliente(
                "Cliente Duplicado",
                "1111111111", // Documento que ya existe
                "duplicado@email.com",
                "3001234567"
            );
            
            boolean registrado = clienteServicio.registrarCliente(clienteDuplicado);
            if (registrado) {
                System.out.println("✗ FALLO (Permitió duplicado)");
                return false;
            }
            System.out.println("✓ OK");
            
            // Test 2: No permitir préstamo con monto inválido
            System.out.print("→ Validando monto inválido... ");
            ArrayList<Cliente> clientes = clienteServicio.listarTodos();
            ArrayList<Empleado> empleados = empleadoServicio.listarTodos();
            
            if (!clientes.isEmpty() && !empleados.isEmpty()) {
                boolean creado = prestamoServicio.crearPrestamo(
                    clientes.get(0).getId(),
                    empleados.get(0).getId(),
                    100000, // Monto muy bajo (mínimo es 500,000)
                    5.0,
                    12,
                    LocalDate.now()
                );
                
                if (creado) {
                    System.out.println("✗ FALLO (Permitió monto inválido)");
                    return false;
                }
                System.out.println("✓ OK");
            }
            
            // Test 3: No permitir interés inválido
            System.out.print("→ Validando interés inválido... ");
            if (!clientes.isEmpty() && !empleados.isEmpty()) {
                boolean creado = prestamoServicio.crearPrestamo(
                    clientes.get(0).getId(),
                    empleados.get(0).getId(),
                    1000000,
                    20.0, // Interés muy alto (máximo 15%)
                    12,
                    LocalDate.now()
                );
                
                if (creado) {
                    System.out.println("✗ FALLO (Permitió interés inválido)");
                    return false;
                }
                System.out.println("✓ OK");
            }
            
            System.out.println("\n✓ TEST 6: PASADO\n");
            return true;
            
        } catch (Exception e) {
            System.out.println("\n✗ TEST 6: FALLO - " + e.getMessage() + "\n");
            return false;
        }
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //     TEST 7: ACTUALIZACIONES Y ELIMINACIONES
    // ═══════════════════════════════════════════════════════════
    
    private static boolean test7_ActualizacionesYEliminaciones() {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  TEST 7: Actualizaciones             ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        try {
            // Actualizar cliente
            System.out.print("→ Actualizando cliente... ");
            ArrayList<Cliente> clientes = clienteServicio.listarTodos();
            
            if (!clientes.isEmpty()) {
                Cliente cliente = clientes.get(0);
                String correoAnterior = cliente.getCorreo();
                cliente.setCorreo("nuevo.correo@email.com");
                
                boolean actualizado = clienteServicio.actualizarCliente(cliente);
                if (!actualizado) {
                    System.out.println("✗ FALLO");
                    return false;
                }
                
                Cliente verificar = clienteServicio.buscarPorId(cliente.getId());
                if (!verificar.getCorreo().equals("nuevo.correo@email.com")) {
                    System.out.println("✗ FALLO (No se actualizó)");
                    return false;
                }
                
                // Restaurar correo original
                cliente.setCorreo(correoAnterior);
                clienteServicio.actualizarCliente(cliente);
            }
            System.out.println("✓ OK");
            
            // Cambiar estado de préstamo
            System.out.print("→ Cambiando estado de préstamo... ");
            ArrayList<Prestamo> prestamos = prestamoServicio.buscarPorEstado("pendiente");
            
            if (!prestamos.isEmpty()) {
                Prestamo prestamo = prestamos.get(0);
                boolean actualizado = prestamoServicio.actualizarEstado(prestamo.getId(), "vencido");
                
                if (!actualizado) {
                    System.out.println("✗ FALLO");
                    return false;
                }
                
                Prestamo verificar = prestamoServicio.buscarPorId(prestamo.getId());
                if (!verificar.getEstado().equals("vencido")) {
                    System.out.println("✗ FALLO (Estado no cambió)");
                    return false;
                }
                
                // Restaurar estado
                prestamoServicio.actualizarEstado(prestamo.getId(), "pendiente");
            }
            System.out.println("✓ OK");
            
            System.out.println("\n✓ TEST 7: PASADO\n");
            return true;
            
        } catch (Exception e) {
            System.out.println("\n✗ TEST 7: FALLO - " + e.getMessage() + "\n");
            return false;
        }
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //     TEST 8: REPORTES Y ESTADÍSTICAS
    // ═══════════════════════════════════════════════════════════
    
    private static boolean test8_ReportesYEstadisticas() {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  TEST 8: Reportes y Estadísticas    ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        try {
            // Calcular cartera total
            System.out.print("→ Calculando cartera total... ");
            double cartera = prestamoServicio.calcularCarteraTotal();
            if (cartera < 0) {
                System.out.println("✗ FALLO");
                return false;
            }
            System.out.println("✓ OK ($" + String.format("%,.2f", cartera) + ")");
            
            // Calcular total recaudado
            System.out.print("→ Calculando total recaudado... ");
            double recaudado = pagoServicio.calcularTotalRecaudado();
            if (recaudado < 0) {
                System.out.println("✗ FALLO");
                return false;
            }
            System.out.println("✓ OK ($" + String.format("%,.2f", recaudado) + ")");
            
            // Calcular nómina
            System.out.print("→ Calculando nómina total... ");
            double nomina = empleadoServicio.calcularNominaTotal();
            if (nomina <= 0) {
                System.out.println("✗ FALLO");
                return false;
            }
            System.out.println("✓ OK ($" + String.format("%,.2f", nomina) + ")");
            
            // Obtener préstamos vencidos
            System.out.print("→ Obteniendo préstamos vencidos... ");
            ArrayList<Prestamo> vencidos = prestamoServicio.obtenerPrestamosVencidos();
            System.out.println("✓ OK (" + vencidos.size() + " vencidos)");
            
            System.out.println("\n✓ TEST 8: PASADO\n");
            return true;
            
        } catch (Exception e) {
            System.out.println("\n✗ TEST 8: FALLO - " + e.getMessage() + "\n");
            return false;
        }
    }
}