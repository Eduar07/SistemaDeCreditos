package com.eduar.servicio;
import java.util.Map;
import java.util.stream.Collectors;
import com.eduar.modelo.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

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
        System.out.println(repetir("-", 95));
        
        for (Prestamo p : prestamos) {
            System.out.printf("%-5d %-20s $%,14.0f %-8d %-10s $%,14.0f %s%n",
                p.getId(),
                truncar(p.getCliente().getNombre(), 20),
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
    //                    MÉTODOS AUXILIARES
    // ═══════════════════════════════════════════════════════════
    
    private String truncar(String texto, int longitud) {
        if (texto == null) return "";
        if (texto.length() <= longitud) return texto;
        return texto.substring(0, longitud - 3) + "...";
    }
    
    private String repetir(String caracter, int veces) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < veces; i++) {
            sb.append(caracter);
        }
        return sb.toString();
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
    System.out.println(repetir("-", 70));
    
    double totalVencido = 0;
    
    for (Prestamo p : vencidos) {
        System.out.printf("%-5d %-20s $%,14.0f $%,14.0f %s%n",
            p.getId(),
            truncar(p.getCliente().getNombre(), 20),
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

// ═══════════════════════════════════════════════════════════
//          REPORTES CON STREAMS Y LAMBDAS (ISSUE #26)
// ═══════════════════════════════════════════════════════════

/**
 * Obtiene préstamos activos (estado = pendiente) usando Stream filter
 */
public ArrayList<Prestamo> obtenerPrestamosActivos() {
    return prestamoServicio.listarTodos().stream()
        .filter(p -> p.getEstado().equalsIgnoreCase("pendiente"))
        .collect(Collectors.toCollection(ArrayList::new));
}

/**
 * Obtiene préstamos vencidos usando Stream filter
 */
public ArrayList<Prestamo> obtenerPrestamosVencidosConStream() {
    return prestamoServicio.listarTodos().stream()
        .filter(p -> p.getEstado().equalsIgnoreCase("vencido") || p.estaVencido())
        .collect(Collectors.toCollection(ArrayList::new));
}

/**
 * Obtiene clientes morosos (con préstamos vencidos)
 */
public ArrayList<Cliente> obtenerClientesMorosos() {
    return prestamoServicio.listarTodos().stream()
        .filter(p -> p.getEstado().equalsIgnoreCase("vencido") || p.estaVencido())
        .map(Prestamo::getCliente)
        .distinct()
        .collect(Collectors.toCollection(ArrayList::new));
}

/**
 * Calcula total prestado por cada empleado usando groupingBy
 */
public Map<String, Double> obtenerTotalPrestadoPorEmpleado() {
    return prestamoServicio.listarTodos().stream()
        .collect(Collectors.groupingBy(
            p -> p.getEmpleado().getNombre(),
            Collectors.summingDouble(Prestamo::getMonto)
        ));
}

/**
 * Genera reporte de préstamos activos
 */
public void generarReportePrestamosActivos() {
    System.out.println("\n╔═══════════════════════════════════════╗");
    System.out.println("║    REPORTE DE PRÉSTAMOS ACTIVOS      ║");
    System.out.println("╚═══════════════════════════════════════╝\n");
    
    ArrayList<Prestamo> activos = obtenerPrestamosActivos();
    
    if (activos.isEmpty()) {
        System.out.println("✓ No hay préstamos activos.\n");
        return;
    }
    
    System.out.printf("%-5s %-20s %-15s %-15s%n",
        "ID", "Cliente", "Monto", "Saldo");
    System.out.println(repetir("-", 60));
    
    double totalCartera = 0;
    
    for (Prestamo p : activos) {
        System.out.printf("%-5d %-20s $%,14.0f $%,14.0f%n",
            p.getId(),
            truncar(p.getCliente().getNombre(), 20),
            p.getMonto(),
            p.getSaldoPendiente()
        );
        totalCartera += p.getSaldoPendiente();
    }
    
    System.out.println("\n✓ Total préstamos activos: " + activos.size());
    System.out.println("✓ Cartera activa: $" + String.format("%,.2f", totalCartera));
    System.out.println();
}

/**
 * Genera reporte de clientes morosos
 */
public void generarReporteClientesMorosos() {
    System.out.println("\n╔═══════════════════════════════════════╗");
    System.out.println("║     REPORTE DE CLIENTES MOROSOS      ║");
    System.out.println("╚═══════════════════════════════════════╝\n");
    
    ArrayList<Cliente> morosos = obtenerClientesMorosos();
    
    if (morosos.isEmpty()) {
        System.out.println("✓ No hay clientes morosos.\n");
        return;
    }
    
    System.out.printf("%-5s %-25s %-15s %-30s%n",
        "ID", "Nombre", "Documento", "Correo");
    System.out.println(repetir("-", 80));
    
    for (Cliente c : morosos) {
        System.out.printf("%-5d %-25s %-15s %-30s%n",
            c.getId(),
            truncar(c.getNombre(), 25),
            c.getDocumento(),
            truncar(c.getCorreo(), 30)
        );
    }
    
    System.out.println("\n⚠️  Total clientes morosos: " + morosos.size());
    System.out.println();
}

/**
 * Genera reporte de total prestado por empleado
 */
public void generarReporteTotalPorEmpleado() {
    System.out.println("\n╔═══════════════════════════════════════╗");
    System.out.println("║   TOTAL PRESTADO POR EMPLEADO        ║");
    System.out.println("╚═══════════════════════════════════════╝\n");
    
    Map<String, Double> totalPorEmpleado = obtenerTotalPrestadoPorEmpleado();
    
    if (totalPorEmpleado.isEmpty()) {
        System.out.println("No hay datos de préstamos.\n");
        return;
    }
    
    System.out.printf("%-30s %-20s%n", "Empleado", "Total Prestado");
    System.out.println(repetir("-", 55));
    
    totalPorEmpleado.forEach((nombre, total) -> 
        System.out.printf("%-30s $%,18.0f%n", truncar(nombre, 30), total)
    );
    
    System.out.println();
}

// ═══════════════════════════════════════════════════════════
//          OPERACIONES CON LAMBDAS (ISSUE #27)
// ═══════════════════════════════════════════════════════════

/**
 * Obtiene préstamos ordenados por monto (mayor a menor)
 */
public ArrayList<Prestamo> obtenerPrestamosOrdenadosPorMonto() {
    return prestamoServicio.listarTodos().stream()
        .sorted((p1, p2) -> Double.compare(p2.getMonto(), p1.getMonto()))
        .collect(Collectors.toCollection(ArrayList::new));
}

/**
 * Calcula el total de la cartera usando mapToDouble + sum
 */
public double calcularTotalCartera() {
    return prestamoServicio.listarTodos().stream()
        .mapToDouble(Prestamo::getSaldoPendiente)
        .sum();
}

/**
 * Obtiene estadísticas de préstamos usando Collectors
 */
public Map<String, Object> obtenerEstadisticasPrestamos() {
    ArrayList<Prestamo> prestamos = prestamoServicio.listarTodos();
    
    Map<String, Object> estadisticas = new HashMap<>();
    
    // Total de préstamos
    estadisticas.put("total", prestamos.size());
    
    // Préstamos por estado
    Map<String, Long> porEstado = prestamos.stream()
        .collect(Collectors.groupingBy(Prestamo::getEstado, Collectors.counting()));
    estadisticas.put("porEstado", porEstado);
    
    // Monto total prestado
    double montoTotal = prestamos.stream()
        .mapToDouble(Prestamo::getMonto)
        .sum();
    estadisticas.put("montoTotal", montoTotal);
    
    // Cartera total
    double carteraTotal = prestamos.stream()
        .mapToDouble(Prestamo::getSaldoPendiente)
        .sum();
    estadisticas.put("carteraTotal", carteraTotal);
    
    // Promedio de préstamos
    double promedio = prestamos.stream()
        .mapToDouble(Prestamo::getMonto)
        .average()
        .orElse(0.0);
    estadisticas.put("promedioMonto", promedio);
    
    return estadisticas;
}

/**
 * Genera reporte de préstamos ordenados por monto
 */
public void generarReportePrestamosOrdenados() {
    System.out.println("\n╔═══════════════════════════════════════╗");
    System.out.println("║  PRÉSTAMOS ORDENADOS POR MONTO       ║");
    System.out.println("╚═══════════════════════════════════════╝\n");
    
    ArrayList<Prestamo> ordenados = obtenerPrestamosOrdenadosPorMonto();
    
    if (ordenados.isEmpty()) {
        System.out.println("No hay préstamos registrados.\n");
        return;
    }
    
    System.out.printf("%-5s %-20s %-15s %-10s%n",
        "ID", "Cliente", "Monto", "Estado");
    System.out.println(repetir("-", 55));
    
    for (Prestamo p : ordenados) {
        System.out.printf("%-5d %-20s $%,13.0f %-10s%n",
            p.getId(),
            truncar(p.getCliente().getNombre(), 20),
            p.getMonto(),
            p.getEstado()
        );
    }
    
    System.out.println("\nTotal: " + ordenados.size() + " préstamos\n");
}

/**
 * Genera reporte de estadísticas generales
 */
public void generarReporteEstadisticas() {
    System.out.println("\n╔═══════════════════════════════════════╗");
    System.out.println("║     ESTADÍSTICAS DE PRÉSTAMOS        ║");
    System.out.println("╚═══════════════════════════════════════╝\n");
    
    Map<String, Object> stats = obtenerEstadisticasPrestamos();
    
    System.out.println("📊 Total de préstamos: " + stats.get("total"));
    System.out.println("💰 Monto total prestado: $" + String.format("%,.2f", stats.get("montoTotal")));
    System.out.println("💵 Cartera total: $" + String.format("%,.2f", stats.get("carteraTotal")));
    System.out.println("📈 Promedio por préstamo: $" + String.format("%,.2f", stats.get("promedioMonto")));
    
    System.out.println("\n📋 Préstamos por estado:");
    @SuppressWarnings("unchecked")
    Map<String, Long> porEstado = (Map<String, Long>) stats.get("porEstado");
    porEstado.forEach((estado, cantidad) -> 
        System.out.println("   • " + estado + ": " + cantidad)
    );
    
    System.out.println();
}

}

