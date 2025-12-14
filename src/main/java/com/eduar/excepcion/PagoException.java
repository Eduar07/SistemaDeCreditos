package com.eduar.excepcion;

/**
 * Excepción personalizada para operaciones de Pagos
 * 
 * @author Eduar Humberto Guerrero Vergel
 * @version 1.0
 */
public class PagoException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructor con mensaje
     */
    public PagoException(String mensaje) {
        super(mensaje);
    }
    
    /**
     * Constructor con mensaje y causa
     */
    public PagoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //           EXCEPCIONES ESPECÍFICAS (STATIC FACTORY)
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Pago no encontrado
     */
    public static PagoException noEncontrado(int id) {
        return new PagoException("Pago con ID " + id + " no encontrado");
    }
    
    /**
     * Préstamo no encontrado
     */
    public static PagoException prestamoNoEncontrado(int prestamoId) {
        return new PagoException("Préstamo con ID " + prestamoId + " no encontrado");
    }
    
    /**
     * Monto inválido (menor o igual a cero)
     */
    public static PagoException montoInvalido(double monto) {
        return new PagoException(
            String.format("Monto inválido: $%,.2f. El monto debe ser mayor a cero", monto)
        );
    }
    
    /**
     * Monto excede saldo pendiente
     */
    public static PagoException montoExcedeSaldo(double monto, double saldoPendiente) {
        return new PagoException(
            String.format("El monto $%,.2f excede el saldo pendiente de $%,.2f", 
                monto, saldoPendiente)
        );
    }
    
    /**
     * Préstamo no está activo
     */
    public static PagoException prestamoNoActivo(int prestamoId, String estado) {
        return new PagoException(
            "No se puede registrar pago. El préstamo #" + prestamoId + 
            " no está activo (estado: " + estado + ")"
        );
    }
    
    /**
     * Fecha de pago inválida
     */
    public static PagoException fechaInvalida(String mensaje) {
        return new PagoException("Fecha de pago inválida: " + mensaje);
    }
    
    /**
     * Fecha anterior al inicio del préstamo
     */
    public static PagoException fechaAnteriorInicioPrestamo() {
        return new PagoException(
            "La fecha de pago no puede ser anterior a la fecha de inicio del préstamo"
        );
    }
    
    /**
     * Fecha futura
     */
    public static PagoException fechaFutura() {
        return new PagoException(
            "La fecha de pago no puede ser una fecha futura"
        );
    }
    
    /**
     * Préstamo ya está completamente pagado
     */
    public static PagoException prestamoYaPagado(int prestamoId) {
        return new PagoException(
            "El préstamo #" + prestamoId + " ya está completamente pagado"
        );
    }
    
    /**
     * No se puede eliminar pago
     */
    public static PagoException noSePuedeEliminar(String razon) {
        return new PagoException("No se puede eliminar el pago: " + razon);
    }
    
    /**
     * Error al actualizar saldo del préstamo
     */
    public static PagoException errorActualizarSaldo(int prestamoId) {
        return new PagoException(
            "Error al actualizar el saldo del préstamo #" + prestamoId
        );
    }
}