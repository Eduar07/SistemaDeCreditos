package com.eduar.util;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * Clase de utilidad para validaciones del sistema
 * 
 * @author Eduar Humberto Guerrero Vergel
 * @version 1.0
 */
public class Validador {
    
    // ═══════════════════════════════════════════════════════════
    //                    REGEX PATTERNS
    // ═══════════════════════════════════════════════════════════
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    private static final Pattern DOCUMENTO_PATTERN = Pattern.compile(
        "^\\d{6,20}$"
    );
    
    private static final Pattern TELEFONO_PATTERN = Pattern.compile(
        "^\\d{10}$"
    );
    
    private static final Pattern SOLO_NUMEROS_PATTERN = Pattern.compile(
        "^\\d+$"
    );
    
    private static final Pattern SOLO_LETRAS_PATTERN = Pattern.compile(
        "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$"
    );
    
    
    // ═══════════════════════════════════════════════════════════
    //                    VALIDAR STRINGS
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Valida que un String no sea nulo ni vacío
     */
    public static boolean esTextoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
    
    /**
     * Valida longitud de texto
     */
    public static boolean validarLongitud(String texto, int min, int max) {
        if (!esTextoValido(texto)) {
            return false;
        }
        int longitud = texto.trim().length();
        return longitud >= min && longitud <= max;
    }
    
    /**
     * Valida que contenga solo letras
     */
    public static boolean esSoloLetras(String texto) {
        if (!esTextoValido(texto)) {
            return false;
        }
        return SOLO_LETRAS_PATTERN.matcher(texto.trim()).matches();
    }
    
    /**
     * Valida que contenga solo números
     */
    public static boolean esSoloNumeros(String texto) {
        if (!esTextoValido(texto)) {
            return false;
        }
        return SOLO_NUMEROS_PATTERN.matcher(texto.trim()).matches();
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    VALIDAR CORREO
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Valida formato de correo electrónico
     * 
     * Ejemplos válidos:
     * - usuario@ejemplo.com
     * - nombre.apellido@empresa.co
     * - user123@dominio.com.co
     * 
     * @param correo Correo a validar
     * @return true si el formato es válido
     */
    public static boolean validarCorreo(String correo) {
        if (!esTextoValido(correo)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(correo.trim()).matches();
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    VALIDAR DOCUMENTO
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Valida documento de identidad (6 a 20 dígitos)
     * 
     * Ejemplos válidos:
     * - 123456 (6 dígitos mínimo)
     * - 1234567890 (10 dígitos)
     * - 12345678901234567890 (20 dígitos máximo)
     * 
     * @param documento Documento a validar
     * @return true si el formato es válido
     */
    public static boolean validarDocumento(String documento) {
        if (!esTextoValido(documento)) {
            return false;
        }
        return DOCUMENTO_PATTERN.matcher(documento.trim()).matches();
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    VALIDAR TELÉFONO
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Valida teléfono colombiano (10 dígitos)
     * 
     * Ejemplos válidos:
     * - 3001234567
     * - 3209876543
     * - 6015551234
     * 
     * @param telefono Teléfono a validar
     * @return true si el formato es válido
     */
    public static boolean validarTelefono(String telefono) {
        if (!esTextoValido(telefono)) {
            return false;
        }
        return TELEFONO_PATTERN.matcher(telefono.trim()).matches();
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    VALIDAR NÚMEROS
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Valida que un número sea positivo
     */
    public static boolean esNumeroPositivo(double numero) {
        return numero > 0;
    }
    
    /**
     * Valida que un número esté en un rango
     */
    public static boolean estaEnRango(double numero, double min, double max) {
        return numero >= min && numero <= max;
    }
    
    /**
     * Valida que un entero esté en un rango
     */
    public static boolean estaEnRango(int numero, int min, int max) {
        return numero >= min && numero <= max;
    }
    
    /**
     * Valida monto de dinero (mayor a cero)
     */
    public static boolean validarMonto(double monto) {
        return esNumeroPositivo(monto);
    }
    
    /**
     * Valida monto en un rango específico
     */
    public static boolean validarMontoEnRango(double monto, double min, double max) {
        return estaEnRango(monto, min, max);
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    VALIDAR FECHAS
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Valida que una fecha no sea nula
     */
    public static boolean esFechaValida(LocalDate fecha) {
        return fecha != null;
    }
    
    /**
     * Valida que una fecha no sea futura
     */
    public static boolean noEsFechaFutura(LocalDate fecha) {
        if (!esFechaValida(fecha)) {
            return false;
        }
        return !fecha.isAfter(LocalDate.now());
    }
    
    /**
     * Valida que una fecha no sea pasada
     */
    public static boolean noEsFechaPasada(LocalDate fecha) {
        if (!esFechaValida(fecha)) {
            return false;
        }
        return !fecha.isBefore(LocalDate.now());
    }
    
    /**
     * Valida que fecha1 sea anterior a fecha2
     */
    public static boolean esAnterior(LocalDate fecha1, LocalDate fecha2) {
        if (!esFechaValida(fecha1) || !esFechaValida(fecha2)) {
            return false;
        }
        return fecha1.isBefore(fecha2);
    }
    
    /**
     * Valida que fecha1 sea posterior a fecha2
     */
    public static boolean esPosterior(LocalDate fecha1, LocalDate fecha2) {
        if (!esFechaValida(fecha1) || !esFechaValida(fecha2)) {
            return false;
        }
        return fecha1.isAfter(fecha2);
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    VALIDACIONES DE NEGOCIO
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Valida monto de préstamo (entre 500,000 y 50,000,000)
     */
    public static boolean validarMontoPrestamo(double monto) {
        return validarMontoEnRango(monto, 500000, 50000000);
    }
    
    /**
     * Valida tasa de interés (entre 1% y 15%)
     */
    public static boolean validarTasaInteres(double interes) {
        return estaEnRango(interes, 1.0, 15.0);
    }
    
    /**
     * Valida número de cuotas (entre 3 y 60 meses)
     */
    public static boolean validarNumeroCuotas(int cuotas) {
        return estaEnRango(cuotas, 3, 60);
    }
    
    /**
     * Valida salario mínimo en Colombia
     */
    public static boolean validarSalario(double salario) {
        return salario >= 1000000; // Salario mínimo aproximado
    }
    
    /**
     * Valida rol de empleado
     */
    public static boolean validarRolEmpleado(String rol) {
        if (!esTextoValido(rol)) {
            return false;
        }
        
        String[] rolesValidos = {"Gerente", "Asesor", "Contador", "Cajero", "Auxiliar"};
        String rolTrim = rol.trim();
        
        for (String rolValido : rolesValidos) {
            if (rolValido.equalsIgnoreCase(rolTrim)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Valida estado de préstamo
     */
    public static boolean validarEstadoPrestamo(String estado) {
        if (!esTextoValido(estado)) {
            return false;
        }
        
        String[] estadosValidos = {"pendiente", "pagado", "vencido"};
        String estadoTrim = estado.trim().toLowerCase();
        
        for (String estadoValido : estadosValidos) {
            if (estadoValido.equals(estadoTrim)) {
                return true;
            }
        }
        
        return false;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    UTILIDADES
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Limpia un texto (trim y quita espacios múltiples)
     */
    public static String limpiarTexto(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.trim().replaceAll("\\s+", " ");
    }
    
    /**
     * Normaliza un documento (solo dígitos)
     */
    public static String normalizarDocumento(String documento) {
        if (documento == null) {
            return "";
        }
        return documento.replaceAll("\\D", "");
    }
    
    /**
     * Normaliza un teléfono (solo dígitos)
     */
    public static String normalizarTelefono(String telefono) {
        if (telefono == null) {
            return "";
        }
        return telefono.replaceAll("\\D", "");
    }
}