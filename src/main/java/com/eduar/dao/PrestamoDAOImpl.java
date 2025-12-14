package com.eduar.dao;

import com.eduar.modelo.Prestamo;
import com.eduar.modelo.Cliente;
import com.eduar.modelo.Empleado;
import com.eduar.util.ConexionDb;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Implementación DAO para Prestamo usando JDBC (MySQL)
 * 
 * @author Eduar Humberto Guerrero Vergel
 * @version 1.0
 */
public class PrestamoDAOImpl implements IDao<Prestamo> {
    
    // DAOs relacionados
    private ClienteDAOImpl clienteDAO;
    private EmpleadoDAOImpl empleadoDAO;
    
    public PrestamoDAOImpl() {
        this.clienteDAO = new ClienteDAOImpl();
        this.empleadoDAO = new EmpleadoDAOImpl();
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    QUERIES SQL
    // ═══════════════════════════════════════════════════════════
    
    private static final String INSERT = 
        "INSERT INTO prestamos (cliente_id, empleado_id, monto, interes, cuotas, fecha_inicio, estado, saldo_pendiente) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SELECT_ALL = 
        "SELECT id, cliente_id, empleado_id, monto, interes, cuotas, fecha_inicio, estado, saldo_pendiente, fecha_creacion " +
        "FROM prestamos";
    
    private static final String SELECT_BY_ID = 
        "SELECT id, cliente_id, empleado_id, monto, interes, cuotas, fecha_inicio, estado, saldo_pendiente, fecha_creacion " +
        "FROM prestamos WHERE id = ?";
    
    private static final String SELECT_BY_CLIENTE = 
        "SELECT id, cliente_id, empleado_id, monto, interes, cuotas, fecha_inicio, estado, saldo_pendiente, fecha_creacion " +
        "FROM prestamos WHERE cliente_id = ?";
    
    private static final String SELECT_BY_ESTADO = 
        "SELECT id, cliente_id, empleado_id, monto, interes, cuotas, fecha_inicio, estado, saldo_pendiente, fecha_creacion " +
        "FROM prestamos WHERE estado = ?";
    
    private static final String UPDATE = 
        "UPDATE prestamos SET cliente_id = ?, empleado_id = ?, monto = ?, interes = ?, cuotas = ?, " +
        "fecha_inicio = ?, estado = ?, saldo_pendiente = ? WHERE id = ?";
    
    private static final String DELETE = 
        "DELETE FROM prestamos WHERE id = ?";
    
    private static final String COUNT = 
        "SELECT COUNT(*) FROM prestamos";
    
    
    // ═══════════════════════════════════════════════════════════
    //                    CREATE (GUARDAR)
    // ═══════════════════════════════════════════════════════════
    
    @Override
    public void guardar(Prestamo prestamo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionDb.getConexion();
            stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setInt(1, prestamo.getCliente().getId());
            stmt.setInt(2, prestamo.getEmpleado().getId());
            stmt.setDouble(3, prestamo.getMonto());
            stmt.setDouble(4, prestamo.getInteres());
            stmt.setInt(5, prestamo.getCuotas());
            stmt.setDate(6, Date.valueOf(prestamo.getFechaInicio()));
            stmt.setString(7, prestamo.getEstado());
            stmt.setDouble(8, prestamo.getSaldoPendiente());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    prestamo.setId(rs.getInt(1));
                }
                System.out.println("✓ Préstamo guardado con ID: " + prestamo.getId());
            }
            
        } catch (SQLException e) {
            System.err.println("Error al guardar préstamo: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarRecursos(conn, stmt, rs);
        }
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    READ (LISTAR TODOS)
    // ═══════════════════════════════════════════════════════════
    
    @Override
    public ArrayList<Prestamo> listar() {
        ArrayList<Prestamo> prestamos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionDb.getConexion();
            stmt = conn.prepareStatement(SELECT_ALL);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Prestamo prestamo = mapearResultSet(rs);
                if (prestamo != null) {
                    prestamos.add(prestamo);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar préstamos: " + e.getMessage());
        } finally {
            cerrarRecursos(conn, stmt, rs);
        }
        
        return prestamos;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    READ (BUSCAR POR ID)
    // ═══════════════════════════════════════════════════════════
    
    public Prestamo buscarPorId(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Prestamo prestamo = null;
        
        try {
            conn = ConexionDb.getConexion();
            stmt = conn.prepareStatement(SELECT_BY_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                prestamo = mapearResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar préstamo por ID: " + e.getMessage());
        } finally {
            cerrarRecursos(conn, stmt, rs);
        }
        
        return prestamo;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    READ (BUSCAR POR CLIENTE)
    // ═══════════════════════════════════════════════════════════
    
    public ArrayList<Prestamo> buscarPorCliente(int clienteId) {
        ArrayList<Prestamo> prestamos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionDb.getConexion();
            stmt = conn.prepareStatement(SELECT_BY_CLIENTE);
            stmt.setInt(1, clienteId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Prestamo prestamo = mapearResultSet(rs);
                if (prestamo != null) {
                    prestamos.add(prestamo);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar préstamos por cliente: " + e.getMessage());
        } finally {
            cerrarRecursos(conn, stmt, rs);
        }
        
        return prestamos;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    READ (BUSCAR POR ESTADO)
    // ═══════════════════════════════════════════════════════════
    
    public ArrayList<Prestamo> buscarPorEstado(String estado) {
        ArrayList<Prestamo> prestamos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionDb.getConexion();
            stmt = conn.prepareStatement(SELECT_BY_ESTADO);
            stmt.setString(1, estado);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Prestamo prestamo = mapearResultSet(rs);
                if (prestamo != null) {
                    prestamos.add(prestamo);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar préstamos por estado: " + e.getMessage());
        } finally {
            cerrarRecursos(conn, stmt, rs);
        }
        
        return prestamos;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    UPDATE (ACTUALIZAR)
    // ═══════════════════════════════════════════════════════════
    
    public boolean actualizar(Prestamo prestamo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean actualizado = false;
        
        try {
            conn = ConexionDb.getConexion();
            stmt = conn.prepareStatement(UPDATE);
            
            stmt.setInt(1, prestamo.getCliente().getId());
            stmt.setInt(2, prestamo.getEmpleado().getId());
            stmt.setDouble(3, prestamo.getMonto());
            stmt.setDouble(4, prestamo.getInteres());
            stmt.setInt(5, prestamo.getCuotas());
            stmt.setDate(6, Date.valueOf(prestamo.getFechaInicio()));
            stmt.setString(7, prestamo.getEstado());
            stmt.setDouble(8, prestamo.getSaldoPendiente());
            stmt.setInt(9, prestamo.getId());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✓ Préstamo actualizado: " + prestamo.getId());
                actualizado = true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar préstamo: " + e.getMessage());
        } finally {
            cerrarRecursos(conn, stmt, null);
        }
        
        return actualizado;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    DELETE (ELIMINAR)
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
                System.out.println("✓ Préstamo eliminado: " + id);
                eliminado = true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar préstamo: " + e.getMessage());
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
            System.err.println("Error al contar préstamos: " + e.getMessage());
        } finally {
            cerrarRecursos(conn, stmt, rs);
        }
        
        return total;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    MÉTODOS AUXILIARES
    // ═══════════════════════════════════════════════════════════
    
    private Prestamo mapearResultSet(ResultSet rs) throws SQLException {
        int clienteId = rs.getInt("cliente_id");
        int empleadoId = rs.getInt("empleado_id");
        
        Cliente cliente = clienteDAO.buscarPorId(clienteId);
        Empleado empleado = empleadoDAO.buscarPorId(empleadoId);
        
        if (cliente == null || empleado == null) {
            System.err.println("Cliente o Empleado no encontrado para préstamo ID: " + rs.getInt("id"));
            return null;
        }
        
        return new Prestamo(
            rs.getInt("id"),
            cliente,
            empleado,
            rs.getDouble("monto"),
            rs.getDouble("interes"),
            rs.getInt("cuotas"),
            rs.getDate("fecha_inicio").toLocalDate(),
            rs.getString("estado"),
            rs.getDouble("saldo_pendiente")
        );
    }
    
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