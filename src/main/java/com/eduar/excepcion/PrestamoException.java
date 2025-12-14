package com.eduar.excepcion;

/**
 * Excepción personalizada para operaciones de Préstamos
 * 
 * @author Eduar Humberto Guerrero Vergel
 * @version 1.0
 */
public class PrestamoException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructor con mensaje
     */
    public PrestamoException(String mensaje) {
        super(mensaje);
    }
    
    /**
     * Constructor con mensaje y causa
     */
    public PrestamoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //           EXCEPCIONES ESPECÍFICAS (STATIC FACTORY)
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Préstamo no encontrado
     */
    public static PrestamoException noEncontrado(int id) {
        return new PrestamoException("Préstamo con ID " + id + " no encontrado");
    }
    
    /**
     * Cliente no encontrado
     */
    public static PrestamoException clienteNoEncontrado(int clienteId) {
        return new PrestamoException("Cliente con ID " + clienteId + " no encontrado");
    }
    
    /**
     * Empleado no encontrado
     */
    public static PrestamoException empleadoNoEncontrado(int empleadoId) {
        return new PrestamoException("Empleado con ID " + empleadoId + " no encontrado");
    }
    
    /**
     * Monto inválido
     */
    public static PrestamoException montoInvalido(double monto, double min, double max) {
        return new PrestamoException(
            String.format("Monto inválido: $%,.2f. Debe estar entre $%,.2f y $%,.2f", 
                monto, min, max)
        );
    }
    
    /**
     * Tasa de interés inválida
     */
    public static PrestamoException interesInvalido(double interes, double min, double max) {
        return new PrestamoException(
            String.format("Tasa de interés inválida: %.2f%%. Debe estar entre %.2f%% y %.2f%%", 
                interes, min, max)
        );
    }
    
    /**
     * Número de cuotas inválido
     */
    public static PrestamoException cuotasInvalidas(int cuotas, int min, int max) {
        return new PrestamoException(
            String.format("Número de cuotas inválido: %d. Debe estar entre %d y %d meses", 
                cuotas, min, max)
        );
    }
    
    /**
     * Cliente ya tiene préstamo pendiente
     */
    public static PrestamoException clienteConPrestamoPendiente(String nombreCliente) {
        return new PrestamoException(
            "El cliente " + nombreCliente + " ya tiene un préstamo pendiente. " +
            "Debe terminar de pagar el préstamo actual antes de solicitar uno nuevo."
        );
    }
    
    /**
     * Préstamo no está activo
     */
    public static PrestamoException prestamoNoActivo(int id, String estadoActual) {
        return new PrestamoException(
            "El préstamo #" + id + " no está activo (estado: " + estadoActual + ")"
        );
    }
    
    /**
     * No se puede eliminar préstamo con pagos
     */
    public static PrestamoException noSePuedeEliminarConPagos(int id) {
        return new PrestamoException(
            "No se puede eliminar el préstamo #" + id + " porque ya tiene pagos registrados"
        );
    }
    
    /**
     * Fecha inválida
     */
    public static PrestamoException fechaInvalida(String mensaje) {
        return new PrestamoException("Fecha inválida: " + mensaje);
    }
    
    /**
     * Estado inválido
     */
    public static PrestamoException estadoInvalido(String estado) {
        return new PrestamoException(
            "Estado inválido: '" + estado + "'. Estados válidos: pendiente, pagado, vencido"
        );
    }
}