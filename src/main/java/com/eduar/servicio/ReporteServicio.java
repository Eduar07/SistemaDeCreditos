package com.eduar.servicio;

import com.eduar.modelo.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Servicio para generar reportes del sistema
 * 
 * @author Eduar Humberto Guerrero Vergel
 * @version 1.0
 */
public class ReporteServicio {
    
    private ClienteServicio clienteServicio;
    private EmpleadoServicio empleadoServicio;
    private PrestamoServicio prestamoServicio;
    private PagoServicio pagoServicio;
    
    public ReporteServicio() {
        this.clienteServicio = new ClienteServicio();
        this.empleadoServicio = new EmpleadoServicio();
        this.prestamoServicio = new PrestamoServicio();
        this.pagoServicio = new PagoServicio();
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    REPORTE GENERAL
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Genera un reporte general del sistema
     */
    public void generarReporteGeneral() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║           CREDIYA S.A.S. - REPORTE GENERAL               ║");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.println("║  Fecha: " + LocalDate.now() + "                                    ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
        
        // Clientes
        int totalClientes = clienteServicio.obtenerTotalClientes();
        System.out.println("📊 CLIENTES");
        System.out.println("   Total de clientes activos: " + totalClientes);
        System.out.println();
        
        // Empleados
        int totalEmpleados = empleadoServicio.obtenerTotalEmpleados();
        double nominaTotal = empleadoServicio.calcularNominaTotal();
        System.out.println("👥 EMPLEADOS");
        System.out.println("   Total de empleados: " + totalEmpleados);
        System.out.println("   Nómina total mensual: $" + String.format("%,.2f", nominaTotal));
        System.out.println();
        
        // Préstamos
        int totalPrestamos = prestamoServicio.obtenerTotalPrestamos();
        double montoPrestado = prestamoServicio.calcularMontoTotalPrestado();
        double carteraTotal = prestamoServicio.calcularCarteraTotal();
        ArrayList<Prestamo> vencidos = prestamoServicio.obtenerPrestamosVencidos();
        
        System.out.println("💰 PRÉSTAMOS");
        System.out.println("   Total de préstamos: " + totalPrestamos);
        System.out.println("   Monto total prestado: $" + String.format("%,.2f", montoPrestado));
        System.out.println("   Cartera total (saldo pendiente): $" + String.format("%,.2f", carteraTotal));
        System.out.println("   Préstamos vencidos: " + vencidos.size());
        System.out.println();
        
        // Pagos
        int totalPagos = pagoServicio.obtenerTotalPagos();
        double totalRecaudado = pagoServicio.calcularTotalRecaudado();
        
        System.out.println("💵 PAGOS");
        System.out.println("   Total de pagos registrados: " + totalPagos);
        System.out.println("   Total recaudado: $" + String.format("%,.2f", totalRecaudado));
        System.out.println();
        
        // Indicadores
        double porcentajeRecuperacion = (montoPrestado > 0) ? (totalRecaudado / montoPrestado) * 100 : 0;
        
        System.out.println("📈 INDICADORES");
        System.out.println("   Porcentaje de recuperación: " + String.format("%.2f%%", porcentajeRecuperacion));
        System.out.println("   Promedio por préstamo: $" + String.format("%,.2f", 
            totalPrestamos > 0 ? montoPrestado / totalPrestamos : 0));
        System.out.println();
        
        System.out.println("═══════════════════════════════════════════════════════════\n");
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    REPORTE DE CLIENTES
    // ═══════════════════════════════════════════════════════════
    
    public void generarReporteClientes() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║      REPORTE DE CLIENTES             ║");
        System.out.println("╚═══════════════════════════════════════╝\n");
        
        ArrayList<Cliente> clientes = clienteServicio.listarTodos();
        
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.\n");
            return;
        }
        
        System.out.printf("%-5s %-25s %-15s %-30s %-12s%n", 
            "ID", "Nombre", "Documento", "Correo", "Teléfono");
        System.out.println("─".repeat(90));
        
        for (Cliente c : clientes) {
            System.out.printf("%-5d %-25s %-15s %-30s %-12s%n",
                c.getId(),
                c.getNombre().length() > 25 ? c.getNombre().substring(0, 22) + "..." : c.getNombre(),
                c.getDocumento(),
                c.getCorreo().length() > 30 ? c.getCorreo().substring(0, 27) + "..." : c.getCorreo(),
                c.getTelefono()
            );
        }
        
        System.out.println("\nTotal: " + clientes.size() + " clientes\n");
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    REPORTE DE PRÉSTAMOS
    // ═══════════════════════════════════════════════════════════
    
    public void generarReportePrestamos() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║      REPORTE DE PRÉSTAMOS            ║");
        System.out.println("╚═══════════════════════════════════════╝\n");
        
        ArrayList<Prestamo> prestamos = prestamoServicio.listarTodos();
        
        if (prestamos.isEmpty()) {
            System.out.println("No hay préstamos registrados.\n");
            return;
        }
        
        System.out.printf("%-5s %-20s %-15s %-8s %-10s %-15s %-15s%n",
            "ID", "Cliente", "Monto", "Cuotas", "Estado", "Saldo", "Fecha");
        System.out.println("─".repeat(95));
        
        for (Prestamo p : prestamos) {
            System.out.printf("%-5d %-20s $%-14,.0f %-8d %-10s $%-14,.0f %s%n",
                p.getId(),
                p.getCliente().getNombre().length() > 20 ? 
                    p.getCliente().getNombre().substring(0, 17) + "..." : 
                    p.getCliente().getNombre(),
                p.getMonto(),
                p.getCuotas(),
                p.getEstado(),
                p.getSaldoPendiente(),
                p.getFechaInicio()
            );
        }
        
        System.out.println("\nTotal: " + prestamos.size() + " préstamos\n");
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    REPORTE POR CLIENTE
    // ═══════════════════════════════════════════════════════════
    
    public void generarReportePorCliente(int clienteId) {
        Cliente
        cliente = clienteServicio.buscarPorId(clienteId);

        if (cliente == null) {
        System.err.println("✗ Cliente no encontrado");
        return;
    }
    
    System.out.println("\n╔═══════════════════════════════════════╗");
    System.out.println("║      REPORTE DE CLIENTE              ║");
    System.out.println("╚═══════════════════════════════════════╝\n");
    
    System.out.println("Cliente: " + cliente.getNombre());
    System.out.println("Documento: " + cliente.getDocumento());
    System.out.println("Correo: " + cliente.getCorreo());
    System.out.println("Teléfono: " + cliente.getTelefono());
    System.out.println();
    
    ArrayList<Prestamo> prestamos = prestamoServicio.buscarPorCliente(clienteId);
    
    if (prestamos.isEmpty()) {
        System.out.println("El cliente no tiene préstamos registrados.\n");
        return;
    }
    
    System.out.println("PRÉSTAMOS:");
    System.out.println("─".repeat(80));
    
    double totalPrestado = 0;
    double totalPendiente = 0;
    
    for (Prestamo p : prestamos) {
        System.out.println("Préstamo #" + p.getId());
        System.out.println("  Monto: $" + String.format("%,.2f", p.getMonto()));
        System.out.println("  Interés: " + p.getInteres() + "%");
        System.out.println("  Cuotas: " + p.getCuotas());
        System.out.println("  Estado: " + p.getEstado());
        System.out.println("  Saldo pendiente: $" + String.format("%,.2f", p.getSaldoPendiente()));
        System.out.println();
        
        totalPrestado += p.getMonto();
        totalPendiente += p.getSaldoPendiente();
    }
    
    System.out.println("RESUMEN:");
    System.out.println("  Total préstamos: " + prestamos.size());
    System.out.println("  Total prestado: $" + String.format("%,.2f", totalPrestado));
    System.out.println("  Total pendiente: $" + String.format("%,.2f", totalPendiente));
    System.out.println();
}


// ═══════════════════════════════════════════════════════════
//                    REPORTE DE PRÉSTAMOS VENCIDOS
// ═══════════════════════════════════════════════════════════

public void generarReportePrestamosVencidos() {
    System.out.println("\n╔═══════════════════════════════════════╗");
    System.out.println("║   REPORTE DE PRÉSTAMOS VENCIDOS      ║");
    System.out.println("╚═══════════════════════════════════════╝\n");
    
    ArrayList<Prestamo> vencidos = prestamoServicio.obtenerPrestamosVencidos();
    
    if (vencidos.isEmpty()) {
        System.out.println("✓ No hay préstamos vencidos.\n");
        return;
    }
    
    System.out.printf("%-5s %-20s %-15s %-15s %-12s%n",
        "ID", "Cliente", "Monto", "Saldo", "Fecha Inicio");
    System.out.println("─".repeat(70));
    
    double totalVencido = 0;
    
    for (Prestamo p : vencidos) {
        System.out.printf("%-5d %-20s $%-14,.0f $%-14,.0f %s%n",
            p.getId(),
            p.getCliente().getNombre().length() > 20 ? 
                p.getCliente().getNombre().substring(0, 17) + "..." : 
                p.getCliente().getNombre(),
            p.getMonto(),
            p.getSaldoPendiente(),
            p.getFechaInicio()
        );
        
        totalVencido += p.getSaldoPendiente();
    }
    
    System.out.println("\n⚠️  Total préstamos vencidos: " + vencidos.size());
    System.out.println("⚠️  Cartera vencida: $" + String.format("%,.2f", totalVencido));
    System.out.println();
}
}

