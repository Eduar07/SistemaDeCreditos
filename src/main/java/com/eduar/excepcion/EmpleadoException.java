package com.eduar.excepcion;

/**
 * Excepción personalizada para operaciones de Empleados
 * 
 * @author Eduar Humberto Guerrero Vergel
 * @version 1.0
 */
public class EmpleadoException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    public EmpleadoException(String mensaje) {
        super(mensaje);
    }
    
    public EmpleadoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
    // ═══════════════════════════════════════════════════════════
    //           EXCEPCIONES ESPECÍFICAS
    // ═══════════════════════════════════════════════════════════
    
    public static EmpleadoException noEncontrado(int id) {
        return new EmpleadoException("Empleado con ID " + id + " no encontrado");
    }
    
    public static EmpleadoException documentoDuplicado(String documento) {
        return new EmpleadoException(
            "Ya existe un empleado registrado con el documento: " + documento
        );
    }
    
    public static EmpleadoException rolInvalido(String rol) {
        return new EmpleadoException(
            "Rol inválido: '" + rol + "'. Roles válidos: Gerente, Asesor, Contador, Cajero, Auxiliar"
        );
    }
    
    public static EmpleadoException salarioInvalido(double salario) {
        return new EmpleadoException(
            String.format("Salario inválido: $%,.2f. Debe ser mayor a $1,000,000", salario)
        );
    }
}