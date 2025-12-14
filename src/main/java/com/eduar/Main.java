package com.eduar;

import com.eduar.modelo.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║     CREDIYA S.A.S. - SISTEMA         ║");
        System.out.println("║   Gestión de Préstamos v1.0          ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        
        // ═══════════════════════════════════════════════════════════
        //           PRUEBA 1: CREAR EMPLEADO Y CLIENTE
        // ═══════════════════════════════════════════════════════════
        
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  PRUEBA 1: Crear Empleado y Cliente ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        // Crear empleado
        Empleado emp = new Empleado(
            "Ana García",
            "1234567890",
            "ana.garcia@crediya.com",
            "Gerente",
            4500000
        );
        emp.setId(1);
        
        // Crear cliente
        Cliente cli = new Cliente(
            "Juan Pérez",
            "1111111111",
            "juan.perez@email.com",
            "3001234567"
        );
        cli.setId(1);
        
        System.out.println("✓ Empleado: " + emp.getNombre());
        System.out.println("✓ Cliente: " + cli.getNombre());
        System.out.println();
        
        
        // ═══════════════════════════════════════════════════════════
        //           PRUEBA 2: CREAR PRÉSTAMO (COMPOSICIÓN)
        // ═══════════════════════════════════════════════════════════
        
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  PRUEBA 2: Crear Préstamo            ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        // Crear préstamo con COMPOSICIÓN
        Prestamo prestamo = new Prestamo(
            cli,                    // Cliente
            emp,                    // Empleado
            5000000,                // Monto
            5.5,                    // Interés 5.5%
            12,                     // 12 cuotas
            LocalDate.now()         // Hoy
        );
        prestamo.setId(1);
        
        System.out.println(prestamo);
        System.out.println();
        
        
        // ═══════════════════════════════════════════════════════════
        //           PRUEBA 3: MÉTODOS DE CÁLCULO
        // ═══════════════════════════════════════════════════════════
        
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  PRUEBA 3: Cálculos del préstamo    ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        System.out.println("Monto original: $" + String.format("%,.2f", prestamo.getMonto()));
        System.out.println("Interés: " + prestamo.getInteres() + "%");
        System.out.println("Monto total: $" + String.format("%,.2f", prestamo.calcularMontoTotal()));
        System.out.println("Cuota mensual: $" + String.format("%,.2f", prestamo.calcularCuotaMensual()));
        System.out.println("Número de cuotas: " + prestamo.getCuotas());
        System.out.println();
        
        
        // ═══════════════════════════════════════════════════════════
        //           PRUEBA 4: REGISTRAR PAGOS
        // ═══════════════════════════════════════════════════════════
        
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  PRUEBA 4: Registrar pagos           ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        // Crear y registrar pago 1
        Pago pago1 = new Pago(
            prestamo,
            LocalDate.now(),
            439583.33,              // Una cuota completa
            "Pago cuota 1"
        );
        pago1.setId(1);
        prestamo.registrarPago(pago1);
        
        System.out.println("✓ Pago 1 registrado:");
        System.out.println("  " + pago1);
        System.out.println("  Saldo pendiente: $" + String.format("%,.2f", prestamo.getSaldoPendiente()));
        System.out.println("  Porcentaje pagado: " + String.format("%.2f%%", prestamo.calcularPorcentajePagado()));
        System.out.println();
        
        // Crear y registrar pago 2
        Pago pago2 = new Pago(
            prestamo,
            LocalDate.now(),
            439583.33,
            "Pago cuota 2"
        );
        pago2.setId(2);
        prestamo.registrarPago(pago2);
        
        System.out.println("✓ Pago 2 registrado:");
        System.out.println("  " + pago2);
        System.out.println("  Saldo pendiente: $" + String.format("%,.2f", prestamo.getSaldoPendiente()));
        System.out.println("  Porcentaje pagado: " + String.format("%.2f%%", prestamo.calcularPorcentajePagado()));
        System.out.println();
        
        // Pago parcial
        Pago pago3 = new Pago(
            prestamo,
            LocalDate.now(),
            200000,                 // Pago parcial
            "Abono parcial"
        );
        pago3.setId(3);
        prestamo.registrarPago(pago3);
        
        System.out.println("✓ Pago 3 registrado (parcial):");
        System.out.println("  " + pago3);
        System.out.println("  Saldo pendiente: $" + String.format("%,.2f", prestamo.getSaldoPendiente()));
        System.out.println("  Porcentaje pagado: " + String.format("%.2f%%", prestamo.calcularPorcentajePagado()));
        System.out.println();
        
        
        // ═══════════════════════════════════════════════════════════
        //           PRUEBA 5: ESTADO DEL PRÉSTAMO
        // ═══════════════════════════════════════════════════════════
        
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  PRUEBA 5: Estado del préstamo       ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        System.out.println("Cuotas pagadas: " + prestamo.calcularCuotasPagadas() + 
                         " de " + prestamo.getCuotas());
        System.out.println("Cuotas pendientes: " + prestamo.calcularCuotasPendientes());
        System.out.println("Total pagado: $" + String.format("%,.2f", prestamo.calcularTotalPagado()));
        System.out.println("¿Está al día? " + (prestamo.estaAlDia() ? "Sí ✓" : "No ✗"));
        System.out.println("¿Está vencido? " + (prestamo.estaVencido() ? "Sí ✗" : "No ✓"));
        System.out.println("Estado: " + prestamo.getEstado());
        System.out.println();
        
        
        // ═══════════════════════════════════════════════════════════
        //           PRUEBA 6: LISTAR PAGOS
        // ═══════════════════════════════════════════════════════════
        
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  PRUEBA 6: Listar todos los pagos   ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
        
        System.out.println("Pagos registrados en el préstamo #" + prestamo.getId() + ":");
        int numPago = 1;
        for (Pago pago : prestamo.getPagos()) {
            System.out.println(numPago + ". " + pago);
            numPago++;
        }
        System.out.println();
        
        
        // ═══════════════════════════════════════════════════════════
        //           PRUEBA 7: COMPOSICIÓN (ACCESO A OBJETOS)
        // ═══════════════════════════════════════════════════════════
        
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  PRUEBA 7: Composición - Acceso      ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
          System.out.println("Desde Prestamo, acceder a Cliente:");
    System.out.println("  Cliente: " + prestamo.getCliente().getNombre());
    System.out.println("  Teléfono: " + prestamo.getCliente().getTelefono());
    System.out.println();
      System.out.println("Desde Prestamo, acceder a Empleado:");
    System.out.println("  Empleado: " + prestamo.getEmpleado().getNombre());
    System.out.println("  Rol: " + prestamo.getEmpleado().getRol());
    System.out.println();
      // ═══════════════════════════════════════════════════════════
    //           RESUMEN
    // ═══════════════════════════════════════════════════════════
    
    System.out.println("╔═══════════════════════════════════════╗");
    System.out.println("║  ✓ Issue #7: Prestamo y Pago ✓      ║");
    System.out.println("║                                       ║");
    System.out.println("║  Conceptos aplicados:                ║");
    System.out.println("║  ✓ Composición (TIENE UN)            ║");
    System.out.println("║  ✓ Prestamo TIENE Cliente            ║");
    System.out.println("║  ✓ Prestamo TIENE Empleado           ║");
    System.out.println("║  ✓ Prestamo TIENE Lista de Pagos     ║");
    System.out.println("║  ✓ Pago TIENE Prestamo               ║");
    System.out.println("║  ✓ ArrayList de objetos              ║");
    System.out.println("║  ✓ LocalDate para fechas             ║");
    System.out.println("║  ✓ Métodos de cálculo                ║");
    System.out.println("║  ✓ Relación bidireccional            ║");
    System.out.println("╚═══════════════════════════════════════╝");
}
}