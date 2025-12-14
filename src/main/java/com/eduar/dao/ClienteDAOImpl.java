package com.eduar.dao;

import com.eduar.modelo.Cliente;
import com.eduar.util.ConexionDb;
import java.sql.*;
import java.util.ArrayList;

/**
 * Implementación DAO para Cliente usando JDBC (MySQL)
 * 
 * @author Eduar Humberto Guerrero Vergel
 * @version 1.0
 */
public class ClienteDAOImpl implements IDao<Cliente> {
    
    // ═══════════════════════════════════════════════════════════
    //                    QUERIES SQL
    // ═══════════════════════════════════════════════════════════
    
    private static final String INSERT = 
        "INSERT INTO clientes (nombre, documento, correo, telefono) VALUES (?, ?, ?, ?)";
    
    private static final String SELECT_ALL = 
        "SELECT id, nombre, documento, correo, telefono, fecha_registro, activo FROM clientes WHERE activo = TRUE";
    
    private static final String SELECT_BY_ID = 
        "SELECT id, nombre, documento, correo, telefono, fecha_registro, activo FROM clientes WHERE id = ? AND activo = TRUE";
    
    private static final String SELECT_BY_DOCUMENTO = 
        "SELECT id, nombre, documento, correo, telefono, fecha_registro, activo FROM clientes WHERE documento = ? AND activo = TRUE";
    
    private static final String UPDATE = 
        "UPDATE clientes SET nombre = ?, documento = ?, correo = ?, telefono = ? WHERE id = ?";
    
    private static final String DELETE = 
        "UPDATE clientes SET activo = FALSE WHERE id = ?";
    
    private static final String COUNT = 
        "SELECT COUNT(*) FROM clientes WHERE activo = TRUE";
    
    
    // ═══════════════════════════════════════════════════════════
    //                    CREATE (GUARDAR)
    // ═══════════════════════════════════════════════════════════
    
    @Override
    public void guardar(Cliente cliente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionDb.getConexion();
            stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getDocumento());
            stmt.setString(3, cliente.getCorreo());
            stmt.setString(4, cliente.getTelefono());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    cliente.setId(rs.getInt(1));
                }
                System.out.println("✓ Cliente guardado con ID: " + cliente.getId());
            }
            
        } catch (SQLException e) {
            System.err.println("Error al guardar cliente: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarRecursos(conn, stmt, rs);
        }
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    READ (LISTAR TODOS)
    // ═══════════════════════════════════════════════════════════
    
    @Override
    public ArrayList<Cliente> listar() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionDb.getConexion();
            stmt = conn.prepareStatement(SELECT_ALL);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Cliente cliente = mapearResultSet(rs);
                clientes.add(cliente);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        } finally {
            cerrarRecursos(conn, stmt, rs);
        }
        
        return clientes;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    READ (BUSCAR POR ID)
    // ═══════════════════════════════════════════════════════════
    
    public Cliente buscarPorId(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cliente cliente = null;
        
        try {
            conn = ConexionDb.getConexion();
            stmt = conn.prepareStatement(SELECT_BY_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                cliente = mapearResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente por ID: " + e.getMessage());
        } finally {
            cerrarRecursos(conn, stmt, rs);
        }
        
        return cliente;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    READ (BUSCAR POR DOCUMENTO)
    // ═══════════════════════════════════════════════════════════
    
    public Cliente buscarPorDocumento(String documento) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cliente cliente = null;
        
        try {
            conn = ConexionDb.getConexion();
            stmt = conn.prepareStatement(SELECT_BY_DOCUMENTO);
            stmt.setString(1, documento);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                cliente = mapearResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente por documento: " + e.getMessage());
        } finally {
            cerrarRecursos(conn, stmt, rs);
        }
        
        return cliente;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    UPDATE (ACTUALIZAR)
    // ═══════════════════════════════════════════════════════════
    
    public boolean actualizar(Cliente cliente) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean actualizado = false;
        
        try {
            conn = ConexionDb.getConexion();
            stmt = conn.prepareStatement(UPDATE);
            
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getDocumento());
            stmt.setString(3, cliente.getCorreo());
            stmt.setString(4, cliente.getTelefono());
            stmt.setInt(5, cliente.getId());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✓ Cliente actualizado: " + cliente.getId());
                actualizado = true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
        } finally {
            cerrarRecursos(conn, stmt, null);
        }
        
        return actualizado;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    DELETE (ELIMINAR LÓGICO)
    // ═══════════════════════════════════════════════════════════
    
    public boolean eliminar(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean eliminado = false;
        
        try {
            conn = ConexionDb.getConexion();
            stmt = conn.prepareStatement(DELETE);
            stmt.setInt(1, id);
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✓ Cliente eliminado: " + id);
                eliminado = true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
        } finally {
            cerrarRecursos(conn, stmt, null);
        }
        
        return eliminado;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    CONTAR
    // ═══════════════════════════════════════════════════════════
    
    public int contar() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int total = 0;
        
        try {
            conn = ConexionDb.getConexion();
            stmt = conn.prepareStatement(COUNT);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                total = rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al contar clientes: " + e.getMessage());
        } finally {
            cerrarRecursos(conn, stmt, rs);
        }
        
        return total;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    MÉTODOS AUXILIARES
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Mapea un ResultSet a un objeto Cliente
     */
    private Cliente mapearResultSet(ResultSet rs) throws SQLException {
        return new Cliente(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getString("documento"),
            rs.getString("correo"),
            rs.getString("telefono")
        );
    }
    
    /**
     * Cierra los recursos JDBC de forma segura
     */
    private void cerrarRecursos(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar recursos: " + e.getMessage());
        }
    }
}