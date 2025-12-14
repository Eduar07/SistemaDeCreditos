package com.eduar;

import com.eduar.util.Validador;
import java.time.LocalDate;

public class TestValidador {
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  PRUEBAS DE VALIDADOR                ║");
        System.out.println("╚═══════════════════════════════════════╝\n");
        
        // Validar correos
        System.out.println("--- Validar Correos ---");
        probarCorreo("usuario@ejemplo.com");          // ✓ Válido
        probarCorreo("nombre.apellido@empresa.co");   // ✓ Válido
        probarCorreo("invalido@");                    // ✗ Inválido
        probarCorreo("sin-arroba.com");               // ✗ Inválido
        System.out.println();
        
        // Validar documentos
        System.out.println("--- Validar Documentos ---");
        probarDocumento("1234567890");    // ✓ Válido
        probarDocumento("123456");        // ✓ Válido
        probarDocumento("12345");         // ✗ Inválido (muy corto)
        probarDocumento("123456789012345678901"); // ✗ Inválido (muy largo)
        System.out.println();
        
        // Validar teléfonos
        System.out.println("--- Validar Teléfonos ---");
        probarTelefono("3001234567");     // ✓ Válido
        probarTelefono("300123456");      // ✗ Inválido (9 dígitos)
        probarTelefono("30012345678");    // ✗ Inválido (11 dígitos)
        System.out.println();
        
        // Validar montos
        System.out.println("--- Validar Montos de Préstamo ---");
        probarMontoPrestamo(1000000);     // ✓ Válido
        probarMontoPrestamo(5000000);     // ✓ Válido
        probarMontoPrestamo(100000);      // ✗ Inválido (muy bajo)
        probarMontoPrestamo(60000000);    // ✗ Inválido (muy alto)
        System.out.println();
        
        // Validar fechas
        System.out.println("--- Validar Fechas ---");
        probarFecha(LocalDate.now());              // ✓ Válido
        probarFecha(LocalDate.now().minusDays(1)); // ✓ Válido
        probarFecha(LocalDate.now().plusDays(1));  // ✗ Futuro
        System.out.println();
    }
    
    private static void probarCorreo(String correo) {
        boolean valido = Validador.validarCorreo(correo);
        System.out.println((valido ? "✓" : "✗") + " " + correo);
    }
    
    private static void probarDocumento(String doc) {
        boolean valido = Validador.validarDocumento(doc);
        System.out.println((valido ? "✓" : "✗") + " " + doc);
    }
    
    private static void probarTelefono(String tel) {
        boolean valido = Validador.validarTelefono(tel);
        System.out.println((valido ? "✓" : "✗") + " " + tel);
    }
    
    private static void probarMontoPrestamo(double monto) {
        boolean valido = Validador.validarMontoPrestamo(monto);
        System.out.println((valido ? "✓" : "✗") + " $" + String.format("%,.0f", monto));
    }
    
    private static void probarFecha(LocalDate fecha) {
        boolean valido = Validador.noEsFechaFutura(fecha);
        System.out.println((valido ? "✓" : "✗") + " " + fecha);
    }
}
