package com.eduar.servicio;

import com.eduar.dao.PrestamoDAOImpl;
import com.eduar.dao.ClienteDAOImpl;
import com.eduar.dao.EmpleadoDAOImpl;
import com.eduar.modelo.Prestamo;
import com.eduar.modelo.Cliente;
import com.eduar.modelo.Empleado;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Servicio para la lógica de negocio de Préstamos
 * 
 * @author Eduar Humberto Guerrero Vergel
 * @version 1.0
 */
public class PrestamoServicio {
    
    private PrestamoDAOImpl prestamoDAO;
    private ClienteDAOImpl clienteDAO;
    private EmpleadoDAOImpl empleadoDAO;
    
    public PrestamoServicio() {
        this.prestamoDAO = new PrestamoDAOImpl();
        this.clienteDAO = new ClienteDAOImpl();
        this.empleadoDAO = new EmpleadoDAOImpl();
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    CREAR PRÉSTAMO
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Crea un nuevo préstamo con validaciones de negocio
     */
    public boolean crearPrestamo(int clienteId, int empleadoId, double monto, 
                                  double interes, int cuotas, LocalDate fechaInicio) {
        
        // Validación 1: Cliente existe y está activo
        Cliente cliente = clienteDAO.buscarPorId(clienteId);
        if (cliente == null) {
            System.err.println("✗ Error: Cliente no encontrado");
            return false;
        }
        
        // Validación 2: Empleado existe y está activo
        Empleado empleado = empleadoDAO.buscarPorId(empleadoId);
        if (empleado == null) {
            System.err.println("✗ Error: Empleado no encontrado");
            return false;
        }
        
        // Validación 3: Monto válido
        if (!validarMonto(monto)) {
            System.err.println("✗ Error: Monto inválido (debe estar entre $500,000 y $50,000,000)");
            return false;
        }
        
        // Validación 4: Tasa de interés válida
        if (!validarInteres(interes)) {
            System.err.println("✗ Error: Tasa de interés inválida (debe estar entre 1% y 15%)");
            return false;
        }
        
        // Validación 5: Número de cuotas válido
        if (!validarCuotas(cuotas)) {
            System.err.println("✗ Error: Número de cuotas inválido (debe estar entre 3 y 60)");
            return false;
        }
        
        // Validación 6: Fecha válida
        if (!validarFecha(fechaInicio)) {
            System.err.println("✗ Error: Fecha inválida (no puede ser futura)");
            return false;
        }
        
        // Validación 7: Cliente no tiene préstamos pendientes (regla de negocio)
        if (tienePrestamoPendiente(clienteId)) {
            System.err.println("✗ Error: El cliente ya tiene un préstamo pendiente");
            return false;
        }
        
        // Crear préstamo
        try {
            Prestamo prestamo = new Prestamo(
                cliente,
                empleado,
                monto,
                interes,
                cuotas,
                fechaInicio
            );
            
            prestamoDAO.guardar(prestamo);
            
            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("║  ✓ PRÉSTAMO APROBADO Y CREADO        ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.println("Cliente: " + cliente.getNombre());
            System.out.println("Monto: $" + String.format("%,.2f", monto));
            System.out.println("Interés: " + interes + "%");
            System.out.println("Cuotas: " + cuotas + " meses");
            System.out.println("Monto total: $" + String.format("%,.2f", prestamo.calcularMontoTotal()));
            System.out.println("Cuota mensual: $" + String.format("%,.2f", prestamo.calcularCuotaMensual()));
            
            return true;
            
        } catch (Exception e) {
            System.err.println("✗ Error al crear préstamo: " + e.getMessage());
            return false;
        }
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    BUSCAR PRÉSTAMOS
    // ═══════════════════════════════════════════════════════════
    
    public Prestamo buscarPorId(int id) {
        if (id <= 0) {
            System.err.println("✗ Error: ID inválido");
            return null;
        }
        return prestamoDAO.buscarPorId(id);
    }
    
    public ArrayList<Prestamo> listarTodos() {
        return prestamoDAO.listar();
    }
    
    public ArrayList<Prestamo> buscarPorCliente(int clienteId) {
        if (clienteId <= 0) {
            System.err.println("✗ Error: ID de cliente inválido");
            return new ArrayList<>();
        }
        return prestamoDAO.buscarPorCliente(clienteId);
    }
    
    public ArrayList<Prestamo> buscarPorEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            System.err.println("✗ Error: Estado inválido");
            return new ArrayList<>();
        }
        return prestamoDAO.buscarPorEstado(estado);
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    ACTUALIZAR PRÉSTAMO
    // ═══════════════════════════════════════════════════════════
    
    public boolean actualizarEstado(int prestamoId, String nuevoEstado) {
        if (prestamoId <= 0) {
            System.err.println("✗ Error: ID inválido");
            return false;
        }
        
        if (!validarEstado(nuevoEstado)) {
            System.err.println("✗ Error: Estado inválido. Estados válidos: pendiente, pagado, vencido");
            return false;
        }
        
        Prestamo prestamo = prestamoDAO.buscarPorId(prestamoId);
        if (prestamo == null) {
            System.err.println("✗ Error: Préstamo no encontrado");
            return false;
        }
        
        prestamo.setEstado(nuevoEstado);
        boolean actualizado = prestamoDAO.actualizar(prestamo);
        
        if (actualizado) {
            System.out.println("✓ Estado del préstamo actualizado a: " + nuevoEstado);
        }
        
        return actualizado;
    }
    
    public boolean actualizarSaldoPendiente(int prestamoId, double nuevoSaldo) {
        if (prestamoId <= 0 || nuevoSaldo < 0) {
            System.err.println("✗ Error: Datos inválidos");
            return false;
        }
        
        Prestamo prestamo = prestamoDAO.buscarPorId(prestamoId);
        if (prestamo == null) {
            System.err.println("✗ Error: Préstamo no encontrado");
            return false;
        }
        
        prestamo.setSaldoPendiente(nuevoSaldo);
        
        // Si el saldo llega a 0, marcar como pagado
        if (nuevoSaldo == 0) {
            prestamo.setEstado("pagado");
        }
        
        return prestamoDAO.actualizar(prestamo);
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    ELIMINAR PRÉSTAMO
    // ═══════════════════════════════════════════════════════════
    
    public boolean eliminarPrestamo(int id) {
        if (id <= 0) {
            System.err.println("✗ Error: ID inválido");
            return false;
        }
        
        Prestamo prestamo = prestamoDAO.buscarPorId(id);
        if (prestamo == null) {
            System.err.println("✗ Error: Préstamo no encontrado");
            return false;
        }
        
        // Regla de negocio: No eliminar préstamos con pagos
        if (prestamo.getSaldoPendiente() < prestamo.calcularMontoTotal()) {
            System.err.println("✗ Error: No se puede eliminar un préstamo con pagos realizados");
            return false;
        }
        
        boolean eliminado = prestamoDAO.eliminar(id);
        
        if (eliminado) {
            System.out.println("✓ Préstamo eliminado");
        }
        
        return eliminado;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    ESTADÍSTICAS Y REPORTES
    // ═══════════════════════════════════════════════════════════
    
    public int obtenerTotalPrestamos() {
        return prestamoDAO.contar();
    }
    
    public double calcularMontoTotalPrestado() {
        ArrayList<Prestamo> prestamos = prestamoDAO.listar();
        double total = 0;
        
        for (Prestamo p : prestamos) {
            total += p.getMonto();
        }
        
        return total;
    }
    
    public double calcularCarteraTotal() {
        ArrayList<Prestamo> prestamos = prestamoDAO.listar();
        double total = 0;
        
        for (Prestamo p : prestamos) {
            total += p.getSaldoPendiente();
        }
        
        return total;
    }
    
  public ArrayList<Prestamo> obtenerPrestamosVencidos() {
    ArrayList<Prestamo> todos = prestamoDAO.listar();
    ArrayList<Prestamo> vencidos = new ArrayList<>();
    
    for (Prestamo p : todos) {
        // Buscar por estado "vencido" O por el método estaVencido()
        if (p.getEstado().equalsIgnoreCase("vencido") || p.estaVencido()) {
            vencidos.add(p);
        }
    }
    
    return vencidos;
}
    
    public boolean tienePrestamoPendiente(int clienteId) {
        ArrayList<Prestamo> prestamos = prestamoDAO.buscarPorCliente(clienteId);
        
        for (Prestamo p : prestamos) {
            if (p.getEstado().equalsIgnoreCase("pendiente")) {
                return true;
            }
        }
        
        return false;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    VALIDACIONES
    // ═══════════════════════════════════════════════════════════
    
    private boolean validarMonto(double monto) {
        // Monto entre 500,000 y 50,000,000
        return monto >= 500000 && monto <= 50000000;
    }
    
    private boolean validarInteres(double interes) {
        // Interés entre 1% y 15%
        return interes >= 1.0 && interes <= 15.0;
    }
    
    private boolean validarCuotas(int cuotas) {
        // Entre 3 y 60 meses
        return cuotas >= 3 && cuotas <= 60;
    }
    
    private boolean validarFecha(LocalDate fecha) {
        if (fecha == null) {
            return false;
        }
        // No puede ser fecha futura
        return !fecha.isAfter(LocalDate.now());
    }
    
    private boolean validarEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            return false;
        }
        
        String[] estadosValidos = {"pendiente", "pagado", "vencido"};
        
        for (String estadoValido : estadosValidos) {
            if (estadoValido.equalsIgnoreCase(estado.trim())) {
                return true;
            }
        }
        
        return false;
    }
}
