package com.eduar.servicio;

import com.eduar.dao.PagoDAOImpl;
import com.eduar.dao.PrestamoDAOImpl;
import com.eduar.modelo.Pago;
import com.eduar.modelo.Prestamo;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Servicio para la lógica de negocio de Pagos
 * 
 * @author Eduar Humberto Guerrero Vergel
 * @version 1.0
 */
public class PagoServicio {
    
    private PagoDAOImpl pagoDAO;
    private PrestamoDAOImpl prestamoDAO;
    private GestorPrestamos GestorPrestamos;
    
    public PagoServicio() {
        this.pagoDAO = new PagoDAOImpl();
        this.prestamoDAO = new PrestamoDAOImpl();
        this.GestorPrestamos = new GestorPrestamos();
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    REGISTRAR PAGO
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Registra un pago con validaciones y actualiza el saldo del préstamo
     */
    public boolean registrarPago(int prestamoId, double monto, LocalDate fechaPago, String observaciones) {
        
        // Validación 1: Préstamo existe
        Prestamo prestamo = prestamoDAO.buscarPorId(prestamoId);
        if (prestamo == null) {
            System.err.println("✗ Error: Préstamo no encontrado");
            return false;
        }
        
        // Validación 2: Préstamo está activo (pendiente)
        if (!prestamo.getEstado().equalsIgnoreCase("pendiente")) {
            System.err.println("✗ Error: El préstamo no está activo (estado: " + prestamo.getEstado() + ")");
            return false;
        }
        
        // Validación 3: Monto válido
        if (!validarMontoPago(monto, prestamo)) {
            System.err.println("✗ Error: Monto inválido");
            return false;
        }
        
        // Validación 4: Fecha válida
        if (!validarFechaPago(fechaPago, prestamo)) {
            System.err.println("✗ Error: Fecha de pago inválida");
            return false;
        }
        
        // Crear el pago
        try {
            Pago pago = new Pago(prestamo, fechaPago, monto, observaciones);
            pagoDAO.guardar(pago);
            
            // Actualizar saldo del préstamo
            double nuevoSaldo = prestamo.getSaldoPendiente() - monto;
            if (nuevoSaldo < 0) {
                nuevoSaldo = 0;
            }
            
            GestorPrestamos.actualizarSaldoPendiente(prestamoId, nuevoSaldo);
            
            // Mostrar resumen
            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("║  ✓ PAGO REGISTRADO EXITOSAMENTE      ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.println("Préstamo #" + prestamoId);
            System.out.println("Monto pagado: $" + String.format("%,.2f", monto));
            System.out.println("Saldo anterior: $" + String.format("%,.2f", prestamo.getSaldoPendiente()));
            System.out.println("Saldo nuevo: $" + String.format("%,.2f", nuevoSaldo));
            
            if (nuevoSaldo == 0) {
                System.out.println("🎉 ¡PRÉSTAMO PAGADO COMPLETAMENTE!");
            }
            
            return true;
            
        } catch (Exception e) {
            System.err.println("✗ Error al registrar pago: " + e.getMessage());
            return false;
        }
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    BUSCAR PAGOS
    // ═══════════════════════════════════════════════════════════
    
    public Pago buscarPorId(int id) {
        if (id <= 0) {
            System.err.println("✗ Error: ID inválido");
            return null;
        }
        return pagoDAO.buscarPorId(id);
    }
    
    public ArrayList<Pago> listarTodos() {
        return pagoDAO.listar();
    }
    
    public ArrayList<Pago> buscarPorPrestamo(int prestamoId) {
        if (prestamoId <= 0) {
            System.err.println("✗ Error: ID de préstamo inválido");
            return new ArrayList<>();
        }
        return pagoDAO.buscarPorPrestamo(prestamoId);
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    ELIMINAR PAGO
    // ═══════════════════════════════════════════════════════════
    
    public boolean eliminarPago(int id) {
        if (id <= 0) {
            System.err.println("✗ Error: ID inválido");
            return false;
        }
        
        Pago pago = pagoDAO.buscarPorId(id);
        if (pago == null) {
            System.err.println("✗ Error: Pago no encontrado");
            return false;
        }
        
        // Regla de negocio: Al eliminar un pago, restaurar el saldo del préstamo
        Prestamo prestamo = pago.getPrestamo();
        double nuevoSaldo = prestamo.getSaldoPendiente() + pago.getMonto();
        
        boolean eliminado = pagoDAO.eliminar(id);
        
        if (eliminado) {
            GestorPrestamos.actualizarSaldoPendiente(prestamo.getId(), nuevoSaldo);
            System.out.println("✓ Pago eliminado y saldo restaurado");
        }
        
        return eliminado;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    ESTADÍSTICAS
    // ═══════════════════════════════════════════════════════════
    
    public int obtenerTotalPagos() {
        return pagoDAO.contar();
    }
    
    public double calcularTotalRecaudado() {
        ArrayList<Pago> pagos = pagoDAO.listar();
        double total = 0;
        
        for (Pago p : pagos) {
            total += p.getMonto();
        }
        
        return total;
    }
    
    public double calcularTotalRecaudadoPorPrestamo(int prestamoId) {
        ArrayList<Pago> pagos = pagoDAO.buscarPorPrestamo(prestamoId);
        double total = 0;
        
        for (Pago p : pagos) {
            total += p.getMonto();
        }
        
        return total;
    }
    
    public int contarPagosPorPrestamo(int prestamoId) {
        return pagoDAO.buscarPorPrestamo(prestamoId).size();
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    VALIDACIONES
    // ═══════════════════════════════════════════════════════════
    
    private boolean validarMontoPago(double monto, Prestamo prestamo) {
        if (monto <= 0) {
            System.err.println("✗ El monto debe ser mayor a 0");
            return false;
        }
        
        if (monto > prestamo.getSaldoPendiente()) {
            System.err.println("✗ El monto excede el saldo pendiente");
            System.err.println("  Saldo pendiente: $" + String.format("%,.2f", prestamo.getSaldoPendiente()));
            System.err.println("  Monto ingresado: $" + String.format("%,.2f", monto));
            return false;
        }
        
        return true;
    }
    
    private boolean validarFechaPago(LocalDate fecha, Prestamo prestamo) {
        if (fecha == null) {
            System.err.println("✗ La fecha no puede ser nula");
            return false;
        }
        
        // No puede ser anterior a la fecha de inicio del préstamo
        if (fecha.isBefore(prestamo.getFechaInicio())) {
            System.err.println("✗ La fecha de pago no puede ser anterior al inicio del préstamo");
            return false;
        }
        
        // No puede ser futura
        if (fecha.isAfter(LocalDate.now())) {
            System.err.println("✗ La fecha de pago no puede ser futura");
            return false;
        }
        
        return true;
    }
}