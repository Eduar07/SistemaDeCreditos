package com.eduar.dao;

import com.eduar.util.ArchivoUtil;
import com.eduar.modelo.Empleado;
import com.eduar.util.ConexionDb;
import java.sql.*;
import java.util.ArrayList;

public class EmpleadoDAOImpl implements IDao<Empleado> {
    
    // ═══════════════════════════════════════════════════════════
    //                    QUERIES SQL
    // ═══════════════════════════════════════════════════════════
    
    private static final String INSERT = 
        "INSERT INTO empleados (nombre, documento, correo, rol, salario) VALUES (?, ?, ?, ?, ?)";
    
    private static final String SELECT_ALL = 
        "SELECT id, nombre, documento, correo, rol, salario, fecha_ingreso, activo FROM empleados WHERE activo = TRUE";
    
    private static final String SELECT_BY_ID = 
        "SELECT id, nombre, documento, correo, rol, salario, fecha_ingreso, activo FROM empleados WHERE id = ? AND activo = TRUE";
    
    private static final String SELECT_BY_DOCUMENTO = 
        "SELECT id, nombre, documento, correo, rol, salario, fecha_ingreso, activo FROM empleados WHERE documento = ? AND activo = TRUE";
    
    private static final String SELECT_BY_ROL = 
        "SELECT id, nombre, documento, correo, rol, salario, fecha_ingreso, activo FROM empleados WHERE rol = ? AND activo = TRUE";
    
    private static final String UPDATE = 
        "UPDATE empleados SET nombre = ?, documento = ?, correo = ?, rol = ?, salario = ? WHERE id = ?";
    
    private static final String DELETE = 
        "UPDATE empleados SET activo = FALSE WHERE id = ?";
    
    private static final String COUNT = 
        "SELECT COUNT(*) FROM empleados WHERE activo = TRUE";
    
    
    // ═══════════════════════════════════════════════════════════
    //                    CREATE (GUARDAR)
    // ═══════════════════════════════════════════════════════════
    
    @Override
public void guardar(Empleado empleado) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    try {
        conn = ConexionDb.getConexion();
        stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
        
        stmt.setString(1, empleado.getNombre());
        stmt.setString(2, empleado.getDocumento());
        stmt.setString(3, empleado.getCorreo());
        stmt.setString(4, empleado.getRol());
        stmt.setDouble(5, empleado.getSalario());
        
        int filasAfectadas = stmt.executeUpdate();
        
        if (filasAfectadas > 0) {
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                empleado.setId(rs.getInt(1));
            }
            System.out.println("✓ Empleado guardado con ID: " + empleado.getId());
            
            // ✅ GUARDAR EN ARCHIVO
            String linea = String.format("%d|%s|%s|%s|%s|%.2f",
                empleado.getId(),
                empleado.getNombre(),
                empleado.getDocumento(),
                empleado.getCorreo(),
                empleado.getRol(),
                empleado.getSalario()
            );
            ArchivoUtil.guardarLinea("data/empleados.txt", linea);
        }
        
    } catch (SQLException e) {
        System.err.println("Error al guardar empleado: " + e.getMessage());
        e.printStackTrace();
    } finally {
        cerrarRecursos(conn, stmt, rs);
    }
}
    
    // ═══════════════════════════════════════════════════════════
    //                    READ (LISTAR TODOS)
    // ═══════════════════════════════════════════════════════════
    
    @Override
    public ArrayList<Empleado> listar() {
        ArrayList<Empleado> empleados = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionDb.getConexion();
            stmt = conn.prepareStatement(SELECT_ALL);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Empleado empleado = mapearResultSet(rs);
                empleados.add(empleado);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar empleados: " + e.getMessage());
        } finally {
            cerrarRecursos(conn, stmt, rs);
        }
        
        return empleados;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    READ (BUSCAR POR ID)
    // ═══════════════════════════════════════════════════════════
    
@Override
public Empleado buscarPorId(int id) {
    // SIN WHERE activo = 1 para que encuentre TODOS
    String sql = "SELECT * FROM empleados WHERE id = ?";
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    try {
        conn = ConexionDb.getConexion();
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        rs = pstmt.executeQuery();
        
        if (rs.next()) {
            Empleado empleado = new Empleado(
                rs.getString("nombre"),
                rs.getString("documento"),
                rs.getString("correo"),
                rs.getString("rol"),
                rs.getDouble("salario")
            );
            empleado.setId(rs.getInt("id"));
            return empleado;
        }
        
    } catch (SQLException e) {
        System.err.println("Error al buscar empleado: " + e.getMessage());
    } finally {
        cerrarRecursos(conn, pstmt, rs);
    }
    
    return null;
}
    
    // ═══════════════════════════════════════════════════════════
    //                    READ (BUSCAR POR DOCUMENTO)
    // ═══════════════════════════════════════════════════════════
    
    public Empleado buscarPorDocumento(String documento) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Empleado empleado = null;
        
        try {
            conn = ConexionDb.getConexion();
            stmt = conn.prepareStatement(SELECT_BY_DOCUMENTO);
            stmt.setString(1, documento);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                empleado = mapearResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar empleado por documento: " + e.getMessage());
        } finally {
            cerrarRecursos(conn, stmt, rs);
        }
        
        return empleado;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    READ (BUSCAR POR ROL)
    // ═══════════════════════════════════════════════════════════
    
    public ArrayList<Empleado> buscarPorRol(String rol) {
        ArrayList<Empleado> empleados = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionDb.getConexion();
            stmt = conn.prepareStatement(SELECT_BY_ROL);
            stmt.setString(1, rol);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Empleado empleado = mapearResultSet(rs);
                empleados.add(empleado);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar empleados por rol: " + e.getMessage());
        } finally {
            cerrarRecursos(conn, stmt, rs);
        }
        
        return empleados;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    UPDATE (ACTUALIZAR)
    // ═══════════════════════════════════════════════════════════
    
    @Override
    public boolean actualizar(Empleado empleado) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean actualizado = false;
        
        try {
            conn = ConexionDb.getConexion();
            stmt = conn.prepareStatement(UPDATE);
            
            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getDocumento());
            stmt.setString(3, empleado.getCorreo());
            stmt.setString(4, empleado.getRol());
            stmt.setDouble(5, empleado.getSalario());
            stmt.setInt(6, empleado.getId());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✓ Empleado actualizado: " + empleado.getId());
                actualizado = true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar empleado: " + e.getMessage());
        } finally {
            cerrarRecursos(conn, stmt, null);
        }
        
        return actualizado;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    DELETE (ELIMINAR LÓGICO)
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
                System.out.println("✓ Empleado eliminado: " + id);
                eliminado = true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar empleado: " + e.getMessage());
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
            System.err.println("Error al contar empleados: " + e.getMessage());
        } finally {
            cerrarRecursos(conn, stmt, rs);
        }
        
        return total;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    MÉTODOS AUXILIARES
    // ═══════════════════════════════════════════════════════════
    
    private Empleado mapearResultSet(ResultSet rs) throws SQLException {
        return new Empleado(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getString("documento"),
            rs.getString("correo"),
            rs.getString("rol"),
            rs.getDouble("salario")
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