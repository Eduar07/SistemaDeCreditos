package com.eduar.servicio;

import com.eduar.dao.EmpleadoDAOImpl;
import com.eduar.modelo.Empleado;
import java.util.ArrayList;

/**
 * Servicio para la lógica de negocio de Empleados
 * 
 * @author Eduar Humberto Guerrero Vergel
 * @version 1.0
 */
public class EmpleadoServicio {
    
    private EmpleadoDAOImpl empleadoDAO;
    
    public EmpleadoServicio() {
        this.empleadoDAO = new EmpleadoDAOImpl();
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    CREAR EMPLEADO
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Registra un nuevo empleado con validaciones de negocio
     */
    public boolean registrarEmpleado(Empleado empleado) {
        // Validación 1: Datos no nulos
        if (empleado == null) {
            System.err.println("✗ Error: El empleado no puede ser nulo");
            return false;
        }
        
        // Validación 2: Nombre no vacío
        if (empleado.getNombre() == null || empleado.getNombre().trim().isEmpty()) {
            System.err.println("✗ Error: El nombre es obligatorio");
            return false;
        }
        
        // Validación 3: Documento válido y único
        if (!validarDocumento(empleado.getDocumento())) {
            System.err.println("✗ Error: Documento inválido");
            return false;
        }
        
        if (existeDocumento(empleado.getDocumento())) {
            System.err.println("✗ Error: El documento ya está registrado");
            return false;
        }
        
        // Validación 4: Correo válido
        if (!validarCorreo(empleado.getCorreo())) {
            System.err.println("✗ Error: Correo electrónico inválido");
            return false;
        }
        
        // Validación 5: Rol válido
        if (!validarRol(empleado.getRol())) {
            System.err.println("✗ Error: Rol inválido. Roles válidos: Gerente, Asesor, Contador, Cajero");
            return false;
        }
        
        // Validación 6: Salario válido
        if (!validarSalario(empleado.getSalario())) {
            System.err.println("✗ Error: Salario inválido (debe ser mayor a $1,000,000)");
            return false;
        }
        
        // Guardar
        try {
            empleadoDAO.guardar(empleado);
            System.out.println("✓ Empleado registrado: " + empleado.getNombre() + " - " + empleado.getRol());
            return true;
        } catch (Exception e) {
            System.err.println("✗ Error al guardar empleado: " + e.getMessage());
            return false;
        }
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    BUSCAR EMPLEADO
    // ═══════════════════════════════════════════════════════════
    
    public Empleado buscarPorId(int id) {
        if (id <= 0) {
            System.err.println("✗ Error: ID inválido");
            return null;
        }
        return empleadoDAO.buscarPorId(id);
    }
    
    public Empleado buscarPorDocumento(String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            System.err.println("✗ Error: Documento inválido");
            return null;
        }
        return empleadoDAO.buscarPorDocumento(documento);
    }
    
    public ArrayList<Empleado> listarTodos() {
        return empleadoDAO.listar();
    }
    
    public ArrayList<Empleado> buscarPorRol(String rol) {
        if (rol == null || rol.trim().isEmpty()) {
            System.err.println("✗ Error: Rol inválido");
            return new ArrayList<>();
        }
        return empleadoDAO.buscarPorRol(rol);
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    ACTUALIZAR EMPLEADO
    // ═══════════════════════════════════════════════════════════
    
    public boolean actualizarEmpleado(Empleado empleado) {
        if (empleado == null || empleado.getId() <= 0) {
            System.err.println("✗ Error: Empleado inválido");
            return false;
        }
        
        // Verificar que existe
        Empleado existente = empleadoDAO.buscarPorId(empleado.getId());
        if (existente == null) {
            System.err.println("✗ Error: Empleado no encontrado");
            return false;
        }
        
        // Validar datos
        if (!validarCorreo(empleado.getCorreo())) {
            System.err.println("✗ Error: Correo inválido");
            return false;
        }
        
        if (!validarRol(empleado.getRol())) {
            System.err.println("✗ Error: Rol inválido");
            return false;
        }
        
        if (!validarSalario(empleado.getSalario())) {
            System.err.println("✗ Error: Salario inválido");
            return false;
        }
        
        boolean actualizado = empleadoDAO.actualizar(empleado);
        
        if (actualizado) {
            System.out.println("✓ Empleado actualizado: " + empleado.getNombre());
        }
        
        return actualizado;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    ELIMINAR EMPLEADO
    // ═══════════════════════════════════════════════════════════
    
    public boolean eliminarEmpleado(int id) {
        if (id <= 0) {
            System.err.println("✗ Error: ID inválido");
            return false;
        }
        
        Empleado empleado = empleadoDAO.buscarPorId(id);
        if (empleado == null) {
            System.err.println("✗ Error: Empleado no encontrado");
            return false;
        }
        
        boolean eliminado = empleadoDAO.eliminar(id);
        
        if (eliminado) {
            System.out.println("✓ Empleado eliminado: " + empleado.getNombre());
        }
        
        return eliminado;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    ESTADÍSTICAS
    // ═══════════════════════════════════════════════════════════
    
    public int obtenerTotalEmpleados() {
        return empleadoDAO.contar();
    }
    
    public boolean existeDocumento(String documento) {
        return empleadoDAO.buscarPorDocumento(documento) != null;
    }
    
    /**
     * Calcula la nómina total
     */
    public double calcularNominaTotal() {
        ArrayList<Empleado> empleados = empleadoDAO.listar();
        double total = 0;
        
        for (Empleado emp : empleados) {
            total += emp.getSalario();
        }
        
        return total;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    VALIDACIONES
    // ═══════════════════════════════════════════════════════════
    
    private boolean validarDocumento(String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            return false;
        }
        documento = documento.trim();
        return documento.length() >= 6 && documento.length() <= 20 && documento.matches("\\d+");
    }
    
    private boolean validarCorreo(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            return false;
        }
        return correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
    
    private boolean validarRol(String rol) {
        if (rol == null || rol.trim().isEmpty()) {
            return false;
        }
        
        String[] rolesValidos = {"Gerente", "Asesor", "Contador", "Cajero", "Auxiliar"};
        
        for (String rolValido : rolesValidos) {
            if (rolValido.equalsIgnoreCase(rol.trim())) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean validarSalario(double salario) {
        // Salario mínimo en Colombia 2025: ~1,300,000
        return salario >= 1000000;
    }
}