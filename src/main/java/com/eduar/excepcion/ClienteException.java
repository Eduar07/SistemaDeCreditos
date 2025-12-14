package com.eduar.excepcion;

/**
 * Excepción personalizada para operaciones de Clientes
 * 
 * @author Eduar Humberto Guerrero Vergel
 * @version 1.0
 */
public class ClienteException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    public ClienteException(String mensaje) {
        super(mensaje);
    }
    
    public ClienteException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
    // ═══════════════════════════════════════════════════════════
    //           EXCEPCIONES ESPECÍFICAS
    // ═══════════════════════════════════════════════════════════
    
    public static ClienteException noEncontrado(int id) {
        return new ClienteException("Cliente con ID " + id + " no encontrado");
    }
    
    public static ClienteException documentoDuplicado(String documento) {
        return new ClienteException(
            "Ya existe un cliente registrado con el documento: " + documento
        );
    }
    
    public static ClienteException datosInvalidos(String campo) {
        return new ClienteException("Datos inválidos: " + campo);
    }
    
    public static ClienteException correoInvalido(String correo) {
        return new ClienteException("Formato de correo inválido: " + correo);
    }
    
    public static ClienteException telefonoInvalido(String telefono) {
        return new ClienteException(
            "Teléfono inválido: " + telefono + ". Debe tener 10 dígitos"
        );
    }
    
    public static ClienteException documentoInvalido(String documento) {
        return new ClienteException(
            "Documento inválido: " + documento + ". Debe tener entre 6 y 20 dígitos"
        );
    }
}