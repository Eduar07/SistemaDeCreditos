package com.eduar.dao;

import com.eduar.modelo.Pago;
import com.eduar.modelo.Prestamo;
import com.eduar.util.ConexionDb;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Implementación DAO para Pago usando JDBC (MySQL)
 * 
 * @author Eduar Humberto Guerrero Vergel
 * @version 1.0
 */
public class PagoDAOImpl implements IDao<Pago> {
    
    private PrestamoDAOImpl prestamoDAO;
    
    public PagoDAOImpl() {
        this.prestamoDAO = new PrestamoDAOImpl();
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    QUERIES SQL
    // ═══════════════════════════════════════════════════════════
    
    private static final String INSERT = 
        "INSERT INTO pagos (prestamo_id, fecha_pago, monto, observaciones) VALUES (?, ?, ?, ?)";
    
    private static final String SELECT_ALL = 
        "SELECT id, prestamo_id, fecha_pago, monto, observaciones, fecha_creacion FROM pagos";
    
    private static final String SELECT_BY_ID = 
        "SELECT id, prestamo_id, fecha_pago, monto, observaciones, fecha_creacion FROM pagos WHERE id = ?";
    
    private static final String SELECT_BY_PRESTAMO = 
        "SELECT id, prestamo_id, fecha_pago, monto, observaciones, fecha_creacion FROM pagos WHERE prestamo_id = ?";
    
    private static final String UPDATE = 
        "UPDATE pagos SET prestamo_id = ?, fecha_pago = ?, monto = ?, observaciones = ? WHERE id = ?";
    
    private static final String DELETE = 
        "DELETE FROM pagos WHERE id = ?";
    
    private static final String COUNT = 
        "SELECT COUNT(*) FROM pagos";
    
    
    // ═══════════════════════════════════════════════════════════
    //                    CREATE (GUARDAR)
    // ═══════════════════════════════════════════════════════════
    
    @Override
    public void guardar(Pago pago) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionDb.getConexion();
            stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setInt(1, pago.getPrestamo().getId());
            stmt.setDate(2, Date.valueOf(pago.getFechaPago()));
            stmt.setDouble(3, pago.getMonto());
            stmt.setString(4, pago.getObservaciones());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    pago.setId(rs.getInt(1));
                }
                System.out.println("✓ Pago guardado con ID: " + pago.getId());
            }
            
        } catch (SQLException e) {
            System.err.println("Error al guardar pago: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarRecursos(conn, stmt, rs);
        }
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    READ (LISTAR TODOS)
    // ═══════════════════════════════════════════════════════════
    
    @Override
    public ArrayList<Pago> listar() {
        ArrayList<Pago> pagos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionDb.getConexion();
            stmt = conn.prepareStatement(SELECT_ALL);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Pago pago = mapearResultSet(rs);
                if (pago != null) {
                    pagos.add(pago);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar pagos: " + e.getMessage());
        } finally {
            cerrarRecursos(conn, stmt, rs);
        }
        
        return pagos;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    READ (BUSCAR POR ID)
    // ═══════════════════════════════════════════════════════════
    
    @Override
    public Pago buscarPorId(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Pago pago = null;
        
        try {
            conn = ConexionDb.getConexion();
            stmt = conn.prepareStatement(SELECT_BY_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                pago = mapearResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar pago por ID: " + e.getMessage());
        } finally {
            cerrarRecursos(conn, stmt, rs);
        }
        
        return pago;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    READ (BUSCAR POR PRÉSTAMO)
    // ═══════════════════════════════════════════════════════════
    
    public ArrayList<Pago> buscarPorPrestamo(int prestamoId) {
        ArrayList<Pago> pagos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionDb.getConexion();
            stmt = conn.prepareStatement(SELECT_BY_PRESTAMO);
            stmt.setInt(1, prestamoId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Pago pago = mapearResultSet(rs);
                if (pago != null) {
                    pagos.add(pago);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar pagos por préstamo: " + e.getMessage());
        } finally {
            cerrarRecursos(conn, stmt, rs);
        }
        
        return pagos;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    UPDATE (ACTUALIZAR)
    // ═══════════════════════════════════════════════════════════
    
    @Override
    public boolean actualizar(Pago pago) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean actualizado = false;
        
        try {
            conn = ConexionDb.getConexion();
            stmt = conn.prepareStatement(UPDATE);
            
            stmt.setInt(1, pago.getPrestamo().getId());
            stmt.setDate(2, Date.valueOf(pago.getFechaPago()));
            stmt.setDouble(3, pago.getMonto());
            stmt.setString(4, pago.getObservaciones());
            stmt.setInt(5, pago.getId());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✓ Pago actualizado: " + pago.getId());
                actualizado = true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar pago: " + e.getMessage());
        } finally {
            cerrarRecursos(conn, stmt, null);
        }
        
        return actualizado;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    DELETE (ELIMINAR)
    // ═══════════════════════════════════════════════════════════
    
    @Override
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
                System.out.println("✓ Pago eliminado: " + id);
                eliminado = true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar pago: " + e.getMessage());
        } finally {
            cerrarRecursos(conn, stmt, null);
        }
        
        return eliminado;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    CONTAR
    // ═══════════════════════════════════════════════════════════
    
    @Override
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
            System.err.println("Error al contar pagos: " + e.getMessage());
        } finally {
            cerrarRecursos(conn, stmt, rs);
        }
        
        return total;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    MÉTODOS AUXILIARES
    // ═══════════════════════════════════════════════════════════
    
    private Pago mapearResultSet(ResultSet rs) throws SQLException {
        int prestamoId = rs.getInt("prestamo_id");
        Prestamo prestamo = prestamoDAO.buscarPorId(prestamoId);
        
        if (prestamo == null) {
            System.err.println("Préstamo no encontrado para pago ID: " + rs.getInt("id"));
            return null;
        }
        
        return new Pago(
            rs.getInt("id"),
            prestamo,
            rs.getDate("fecha_pago").toLocalDate(),
            rs.getDouble("monto"),
            rs.getString("observaciones")
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