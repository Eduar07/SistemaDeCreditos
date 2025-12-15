package com.eduar.util;

/**
 * Utilidad para colores ANSI en consola
 * 
 * @author Eduar Humberto Guerrero Vergel
 * @version 1.0
 */
public class ColoresConsola {
    
    // Reset
    public static final String RESET = "\033[0m";
    
    // Colores de texto
    public static final String NEGRO = "\033[0;30m";
    public static final String ROJO = "\033[0;31m";
    public static final String VERDE = "\033[0;32m";
    public static final String AMARILLO = "\033[0;33m";
    public static final String AZUL = "\033[0;34m";
    public static final String MORADO = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String BLANCO = "\033[0;37m";
    
    // Colores en negrita
    public static final String NEGRO_BOLD = "\033[1;30m";
    public static final String ROJO_BOLD = "\033[1;31m";
    public static final String VERDE_BOLD = "\033[1;32m";
    public static final String AMARILLO_BOLD = "\033[1;33m";
    public static final String AZUL_BOLD = "\033[1;34m";
    public static final String MORADO_BOLD = "\033[1;35m";
    public static final String CYAN_BOLD = "\033[1;36m";
    public static final String BLANCO_BOLD = "\033[1;37m";
    
    // Fondos
    public static final String FONDO_NEGRO = "\033[40m";
    public static final String FONDO_ROJO = "\033[41m";
    public static final String FONDO_VERDE = "\033[42m";
    public static final String FONDO_AMARILLO = "\033[43m";
    public static final String FONDO_AZUL = "\033[44m";
    public static final String FONDO_MORADO = "\033[45m";
    public static final String FONDO_CYAN = "\033[46m";
    public static final String FONDO_BLANCO = "\033[47m";
    
    /**
     * Métodos de utilidad
     */
    public static String exito(String texto) {
        return VERDE_BOLD + "✓ " + texto + RESET;
    }
    
    public static String error(String texto) {
        return ROJO_BOLD + "✗ " + texto + RESET;
    }
    
    public static String advertencia(String texto) {
        return AMARILLO_BOLD + "⚠️  " + texto + RESET;
    }
    
    public static String info(String texto) {
        return CYAN_BOLD + "ℹ " + texto + RESET;
    }
    
    public static String titulo(String texto) {
        return AZUL_BOLD + texto + RESET;
    }
}