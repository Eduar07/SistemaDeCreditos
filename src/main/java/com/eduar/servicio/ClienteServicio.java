package com.eduar.servicio;

import com.eduar.dao.ClienteDAOImpl;
import com.eduar.modelo.Cliente;
import java.util.ArrayList;

/**
 * Servicio para la lógica de negocio de Clientes
 * 
 * @author Eduar Humberto Guerrero Vergel
 * @version 1.0
 */
public class ClienteServicio {
    
    private ClienteDAOImpl clienteDAO;
    
    public ClienteServicio() {
        this.clienteDAO = new ClienteDAOImpl();
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    CREAR CLIENTE
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Registra un nuevo cliente con validaciones de negocio
     * 
     * @param cliente Cliente a registrar
     * @return true si se registró exitosamente
     */
    public boolean registrarCliente(Cliente cliente) {
        // Validación 1: Datos no nulos
        if (cliente == null) {
            System.err.println("✗ Error: El cliente no puede ser nulo");
            return false;
        }
        
        // Validación 2: Nombre no vacío
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            System.err.println("✗ Error: El nombre es obligatorio");
            return false;
        }
        
        // Validación 3: Documento válido
        if (!validarDocumento(cliente.getDocumento())) {
            System.err.println("✗ Error: Documento inválido");
            return false;
        }
        
        // Validación 4: Documento no existe
        if (existeDocumento(cliente.getDocumento())) {
            System.err.println("✗ Error: El documento ya está registrado");
            return false;
        }
        
        // Validación 5: Correo válido
        if (!validarCorreo(cliente.getCorreo())) {
            System.err.println("✗ Error: Correo electrónico inválido");
            return false;
        }
        
        // Validación 6: Teléfono válido
        if (!validarTelefono(cliente.getTelefono())) {
            System.err.println("✗ Error: Teléfono inválido (debe tener 10 dígitos)");
            return false;
        }
        
        // Si todas las validaciones pasan, guardar
        try {
            clienteDAO.guardar(cliente);
            System.out.println("✓ Cliente registrado exitosamente: " + cliente.getNombre());
            return true;
        } catch (Exception e) {
            System.err.println("✗ Error al guardar cliente: " + e.getMessage());
            return false;
        }
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    BUSCAR CLIENTE
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Busca un cliente por ID
     */
    public Cliente buscarPorId(int id) {
        if (id <= 0) {
            System.err.println("✗ Error: ID inválido");
            return null;
        }
        
        Cliente cliente = clienteDAO.buscarPorId(id);
        
        if (cliente == null) {
            System.out.println("ℹ Cliente no encontrado con ID: " + id);
        }
        
        return cliente;
    }
    
    /**
     * Busca un cliente por documento
     */
    public Cliente buscarPorDocumento(String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            System.err.println("✗ Error: Documento inválido");
            return null;
        }
        
        Cliente cliente = clienteDAO.buscarPorDocumento(documento);
        
        if (cliente == null) {
            System.out.println("ℹ Cliente no encontrado con documento: " + documento);
        }
        
        return cliente;
    }
    
    /**
     * Lista todos los clientes activos
     */
    public ArrayList<Cliente> listarTodos() {
        return clienteDAO.listar();
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    ACTUALIZAR CLIENTE
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Actualiza los datos de un cliente
     */
    public boolean actualizarCliente(Cliente cliente) {
        if (cliente == null || cliente.getId() <= 0) {
            System.err.println("✗ Error: Cliente inválido");
            return false;
        }
        
        // Verificar que el cliente existe
        Cliente existente = clienteDAO.buscarPorId(cliente.getId());
        if (existente == null) {
            System.err.println("✗ Error: Cliente no encontrado");
            return false;
        }
        
        // Validar nuevos datos
        if (!validarCorreo(cliente.getCorreo())) {
            System.err.println("✗ Error: Correo inválido");
            return false;
        }
        
        if (!validarTelefono(cliente.getTelefono())) {
            System.err.println("✗ Error: Teléfono inválido");
            return false;
        }
        
        // Actualizar
        boolean actualizado = clienteDAO.actualizar(cliente);
        
        if (actualizado) {
            System.out.println("✓ Cliente actualizado: " + cliente.getNombre());
        }
        
        return actualizado;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    ELIMINAR CLIENTE
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Elimina (desactiva) un cliente
     */
    public boolean eliminarCliente(int id) {
        if (id <= 0) {
            System.err.println("✗ Error: ID inválido");
            return false;
        }
        
        // Verificar que existe
        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente == null) {
            System.err.println("✗ Error: Cliente no encontrado");
            return false;
        }
        
        // TODO: Verificar que no tenga préstamos activos
        
        boolean eliminado = clienteDAO.eliminar(id);
        
        if (eliminado) {
            System.out.println("✓ Cliente eliminado: " + cliente.getNombre());
        }
        
        return eliminado;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    ESTADÍSTICAS
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Obtiene el total de clientes activos
     */
    public int obtenerTotalClientes() {
        return clienteDAO.contar();
    }
    
    /**
     * Verifica si existe un cliente con ese documento
     */
    public boolean existeDocumento(String documento) {
        return clienteDAO.buscarPorDocumento(documento) != null;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    VALIDACIONES
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Valida que el documento tenga el formato correcto
     */
    private boolean validarDocumento(String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            return false;
        }
        
        // Quitar espacios
        documento = documento.trim();
        
        // Debe tener entre 6 y 20 dígitos
        if (documento.length() < 6 || documento.length() > 20) {
            return false;
        }
        
        // Solo números
        return documento.matches("\\d+");
    }
    
    /**
     * Valida que el correo tenga formato válido
     */
    private boolean validarCorreo(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            return false;
        }
        
        // Regex simple para email
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return correo.matches(regex);
    }
    
    /**
     * Valida que el teléfono tenga formato válido
     */
    private boolean validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return false;
        }
        
        // Quitar espacios
        telefono = telefono.trim();
        
        // Debe tener 10 dígitos
        if (telefono.length() != 10) {
            return false;
        }
        
        // Solo números
        return telefono.matches("\\d+");
    }
}